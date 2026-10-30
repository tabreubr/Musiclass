package com.github.tabreubr.musiclass.security;

import com.github.tabreubr.musiclass.TestcontainersConfiguration;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * E-mail de instrutor único e normalizado (achado S6 da auditoria).
 *
 * Antes: dois instrutores podiam ter o mesmo e-mail; se A trocasse o próprio
 * e-mail para o de B, o login de B deixava de funcionar.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
class InstructorEmailUniquenessTest {

    private static final String PASSWORD = "senha-de-teste";

    private final HttpClient http = HttpClient.newHttpClient();

    @Autowired
    private Environment environment;

    private String emailA;
    private String emailB;
    private String tokenA;

    @BeforeEach
    void setUp() throws Exception {
        emailA = uniqueEmail();
        emailB = uniqueEmail();
        tokenA = JsonPath.read(register("Instrutor A", emailA).body(), "$.token");
        register("Instrutor B", emailB);
    }

    @Test
    @DisplayName("cadastro com e-mail já usado responde 409")
    void registerWithExistingEmailReturnsConflict() throws Exception {
        assertThat(register("Outro", emailB).statusCode()).isEqualTo(409);
    }

    @Test
    @DisplayName("e-mail com outra caixa é o mesmo e-mail")
    void registerWithSameEmailInDifferentCaseReturnsConflict() throws Exception {
        assertThat(register("Outro", emailB.toUpperCase()).statusCode()).isEqualTo(409);
    }

    @Test
    @DisplayName("e-mail com espaços nas pontas é recusado pela validação (@Email)")
    void registerWithSurroundingSpacesIsRejectedAsInvalid() throws Exception {
        assertThat(register("Outro", "  " + emailB + " ").statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("login aceita o e-mail com outra caixa")
    void loginIsCaseInsensitive() throws Exception {
        assertThat(login(emailB.toUpperCase()).statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("instrutor não consegue trocar o próprio e-mail para o de outro")
    void cannotChangeOwnEmailToAnotherInstructorsEmail() throws Exception {
        var response = updateMe(tokenA, emailB);

        assertThat(response.statusCode()).isEqualTo(409);
        assertThat(login(emailB).statusCode()).as("login do instrutor B continua funcionando").isEqualTo(200);
    }

    @Test
    @DisplayName("controle: instrutor consegue atualizar o perfil mantendo o próprio e-mail")
    void canUpdateProfileKeepingOwnEmail() throws Exception {
        assertThat(updateMe(tokenA, emailA).statusCode()).isEqualTo(200);
    }

    // ---------- helpers ----------

    private static String uniqueEmail() {
        return "it-" + UUID.randomUUID() + "@musiclass.test";
    }

    private HttpResponse<String> register(String name, String email) throws Exception {
        return send("POST", "/auth/register", null, """
                {"name": "%s", "email": "%s", "password": "%s"}
                """.formatted(name, email, PASSWORD));
    }

    private HttpResponse<String> login(String email) throws Exception {
        return send("POST", "/auth/login", null, """
                {"email": "%s", "password": "%s"}
                """.formatted(email, PASSWORD));
    }

    private HttpResponse<String> updateMe(String token, String email) throws Exception {
        return send("PUT", "/instructors/me", token, """
                {"name": "Instrutor A", "email": "%s", "password": "%s", "role": "INSTRUCTOR"}
                """.formatted(email, PASSWORD));
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
