package com.github.tabreubr.musiclass.controllers;

import com.github.tabreubr.musiclass.dto.invite.InviteResponse;
import com.github.tabreubr.musiclass.dto.student.StudentInviteRequest;
import com.github.tabreubr.musiclass.services.InviteTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invites")
public class InviteTokenController {

    private final InviteTokenService inviteService;

    public InviteTokenController(InviteTokenService inviteService) {
        this.inviteService = inviteService;
    }

    // Instrutor gera o convite — rota protegida (precisa de token JWT)
    @PostMapping("/student/{studentId}")
    public ResponseEntity<InviteResponse> generateInvite(@PathVariable Long studentId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inviteService.generateInviteLink(studentId));
    }

    @PostMapping("/{token}/register")
    public ResponseEntity<Void> register(@PathVariable String token,
                                         @RequestBody @Valid StudentInviteRequest request) {
        inviteService.register(token, request);
        return ResponseEntity.ok().build();
    }
}
