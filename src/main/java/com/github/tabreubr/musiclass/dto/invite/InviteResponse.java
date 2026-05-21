package com.github.tabreubr.musiclass.dto.invite;

import java.time.LocalDateTime;

public record InviteResponse(
        String token,
        String inviteLink,
        LocalDateTime expiresAt
) {
}
