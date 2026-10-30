package com.github.tabreubr.musiclass.security;

import com.github.tabreubr.musiclass.TestcontainersConfiguration;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Isolamento de dados entre instrutores (achados S1, S2 e S3 da auditoria).
 *
 * Cenário: o instrutor B é dono de um aluno e de uma aula; o instrutor A tenta
 * acessar esses recursos pelo id. O comportamento esperado é 404 (o mesmo padrão
 * já usado em LessonService e nos services de metas: não revelar que o recurso existe).
 *
 * Os testes sobem a aplicação numa porta real e fazem chamadas HTTP de verdade,
 * passando por todo o filtro de segurança, como o frontend faria.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
class InstructorDataIsolationTest {

    private final HttpClient http = HttpClient.newHttpClient();

    @Autowired
    private Environment environment;

    private String tokenA;
    private String tokenB;
    private long studentOfB;
    private long classOfB;

    @BeforeEach
    void setUp() throws Exception {
        tokenA = registerInstructor("Instrutor A");
        tokenB = registerInstructor("Instrutor B");

        long instrumentId = createInstrument(tokenB);
        studentOfB = createStudent(tokenB, "Aluno do B", instrumentId);
        classOfB = createClass(tokenB, studentOfB);
    }

    @Nested
    @DisplayName("Aulas (/classes)")
    class Classes {

        @Test
        @DisplayName("controle: o dono consegue ler a própria aula")
        void ownerCanReadOwnClass() throws Exception {
            assertThat(send("GET", "/classes/" + classOfB, tokenB, null).statusCode()).isEqualTo(200);
        }

        @Test
        @DisplayName("S1: outro instrutor não consegue ler a aula")
        void otherInstructorCannotReadClass() throws Exception {
            assertThat(send("GET", "/classes/" + classOfB, tokenA, null).statusCode()).isEqualTo(404);
        }

        @Test
        @DisplayName("S1: outro instrutor não consegue alterar a aula")
        void otherInstructorCannotUpdateClass() throws Exception {
            var response = send("PUT", "/classes/" + classOfB, tokenA, """
                    {"observations": "alterado pelo instrutor A"}
                    """);

            assertThat(response.statusCode()).isEqualTo(404);
            String observations = JsonPath.read(send("GET", "/classes/" + classOfB, tokenB, null).body(), "$.observations");
            assertThat(observations).isNotEqualTo("alterado pelo instrutor A");
        }

        @Test
        @DisplayName("S1: outro instrutor não consegue excluir a aula")
        void otherInstructorCannotDeleteClass() throws Exception {
            var response = send("DELETE", "/classes/" + classOfB, tokenA, null);

            assertThat(response.statusCode()).isEqualTo(404);
            List<Integer> idsOfB = JsonPath.read(send("GET", "/classes", tokenB, null).body(), "$[*].id");
            assertThat(idsOfB).contains((int) classOfB);
        }

        @Test
        @DisplayName("S3: instrutor não consegue criar aula para aluno de outro instrutor")
        void cannotCreateClassForOtherInstructorsStudent() throws Exception {
            var response = send("POST", "/classes", tokenA, """
                    {"date": "2026-12-01T10:00:00", "studentId": %d}
                    """.formatted(studentOfB));

            assertThat(response.statusCode()).isEqualTo(404);
        }
    }

    @Nested
    @DisplayName("Alunos (/students)")
    class Students {

        @Test
        @DisplayName("controle: o dono consegue ler o próprio aluno")
        void ownerCanReadOwnStudent() throws Exception {
            assertThat(send("GET", "/students/" + studentOfB, tokenB, null).statusCode()).isEqualTo(200);
        }

        @Test
        @DisplayName("S2: outro instrutor não consegue ler o aluno")
        void otherInstructorCannotReadStudent() throws Exception {
            assertThat(send("GET", "/students/" + studentOfB, tokenA, null).statusCode()).isEqualTo(404);
        }

        @Test
        @DisplayName("S2: outro instrutor não consegue alterar o aluno")
        void otherInstructorCannotUpdateStudent() throws Exception {
            long instrumentId = createInstrument(tokenA);
            var response = send("PUT", "/students/" + studentOfB, tokenA, """
                    {"name": "renomeado pelo instrutor A", "instrumentId": %d}
                    """.formatted(instrumentId));

            assertThat(response.statusCode()).isEqualTo(404);
            String name = JsonPath.read(send("GET", "/students/" + studentOfB, tokenB, null).body(), "$.name");
            assertThat(name).isEqualTo("Aluno do B");
        }

        @Test
        @DisplayName("S2: outro instrutor não consegue excluir o aluno")
        void otherInstructorCannotDeleteStudent() throws Exception {
            var response = send("DELETE", "/students/" + studentOfB, tokenA, null);

            assertThat(response.statusCode()).isEqualTo(404);
            assertThat(send("GET", "/students/" + studentOfB, tokenB, null).statusCode()).isEqualTo(200);
        }
    }

    // ---------- helpers ----------

    private String registerInstructor(String name) throws Exception {
        String email = "it-" + UUID.randomUUID() + "@musiclass.test";
        var response = send("POST", "/auth/register", null, """
                {"name": "%s", "email": "%s", "password": "senha-de-teste"}
                """.formatted(name, email));
        assertThat(response.statusCode()).as("registro de %s", name).isEqualTo(201);
        return JsonPath.read(response.body(), "$.token");
    }

    /** Cada teste cria os próprios dados: a seed de dev não roda no perfil "test". */
    private long createInstrument(String token) throws Exception {
        var response = send("POST", "/instruments", token, """
                {"name": "Instrumento %s"}
                """.formatted(UUID.randomUUID()));
        assertThat(response.statusCode()).as("criação do instrumento").isEqualTo(201);
        Number id = JsonPath.read(response.body(), "$.id");
        return id.longValue();
    }

    private long createStudent(String token, String name, long instrumentId) throws Exception {
        var response = send("POST", "/students", token, """
                {"name": "%s", "instrumentId": %d}
                """.formatted(name, instrumentId));
        assertThat(response.statusCode()).as("criação do aluno").isEqualTo(201);
        Number id = JsonPath.read(response.body(), "$.id");
        return id.longValue();
    }

    private long createClass(String token, long studentId) throws Exception {
        var response = send("POST", "/classes", token, """
                {"date": "2026-12-01T10:00:00", "observations": "aula do B", "studentId": %d}
                """.formatted(studentId));
        assertThat(response.statusCode()).as("criação da aula").isEqualTo(201);
        Number id = JsonPath.read(response.body(), "$.id");
        return id.longValue();
    }

    private HttpResponse<String> send(String method, String path, String token, String jsonBody)
            throws IOException, InterruptedException {
        String port = environment.getRequiredProperty("local.server.port");
        var builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path));
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        if (jsonBody != null) {
            builder.header("Content-Type", "application/json");
            builder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody));
        } else {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }
        return http.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
