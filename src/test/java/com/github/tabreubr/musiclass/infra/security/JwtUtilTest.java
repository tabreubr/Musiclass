package com.github.tabreubr.musiclass.infra.security;

import com.github.tabreubr.musiclass.entities.Instructor;
import com.github.tabreubr.musiclass.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtUtilTest {

    private static final String VALID_SECRET = "a-valid-secret-with-at-least-32-bytes!!";
    private static final long ONE_HOUR = 3_600_000L;

    @Test
    void shouldRejectMissingSecret() {
        assertThatThrownBy(() -> new JwtUtil(null, ONE_HOUR))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("JWT_SECRET");
    }

    @Test
    void shouldRejectSecretShorterThan32Bytes() {
        assertThatThrownBy(() -> new JwtUtil("too-short", ONE_HOUR))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldGenerateTokenThatCanBeValidatedAndRead() {
        JwtUtil jwtUtil = new JwtUtil(VALID_SECRET, ONE_HOUR);
        Instructor instructor = new Instructor(1L, "Ana", "ana@musiclass.test", "hash", UserRole.INSTRUCTOR);

        String token = jwtUtil.generateToken(instructor);

        assertThat(jwtUtil.isTokenValid(token)).isTrue();
        assertThat(jwtUtil.extractEmail(token)).isEqualTo("ana@musiclass.test");
        assertThat(jwtUtil.extractClaims(token).get("role", String.class)).isEqualTo("INSTRUCTOR");
    }

    @Test
    void shouldRejectTokenSignedWithAnotherSecret() {
        JwtUtil issuer = new JwtUtil("another-secret-with-at-least-32-bytes!!", ONE_HOUR);
        JwtUtil verifier = new JwtUtil(VALID_SECRET, ONE_HOUR);
        Instructor instructor = new Instructor(1L, "Ana", "ana@musiclass.test", "hash", UserRole.INSTRUCTOR);

        String forgedToken = issuer.generateToken(instructor);

        assertThat(verifier.isTokenValid(forgedToken)).isFalse();
    }
}
