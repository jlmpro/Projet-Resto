package com.moris.resto.security;

import com.moris.resto.user.Utilisateur;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class PermissionHandle {

    public ResponseEntity<Map<String, Object>> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Échec de l'authentification !"));
        }

        Utilisateur user = (Utilisateur) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getEmail());
        response.put("prenom", user.getFirstname());
        response.put("nom", user.getLastname());
        response.put("role", user.getRole().getLibelle().name());
        response.put("permissions", authentication.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList());

        return ResponseEntity.ok(response);
    }
}



