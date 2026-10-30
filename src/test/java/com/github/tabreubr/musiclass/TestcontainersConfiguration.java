package com.github.tabreubr.musiclass;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

/**
 * Sobe um PostgreSQL real e descartável (via Docker) para os testes de integração.
 * O @ServiceConnection faz o Spring Boot configurar o datasource com a URL,
 * o usuário e a senha do container, sem precisar de propriedades manuais.
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        return new PostgreSQLContainer("postgres:16-alpine");
    }
}
