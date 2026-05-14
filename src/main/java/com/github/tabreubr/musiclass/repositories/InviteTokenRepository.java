package com.github.tabreubr.musiclass.repositories;

import com.github.tabreubr.musiclass.entities.InviteToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteTokenRepository extends JpaRepository<InviteToken, Long> {

    Optional<InviteToken> findByToken(String token);
}
