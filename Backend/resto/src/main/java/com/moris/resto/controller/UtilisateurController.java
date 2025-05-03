package com.moris.resto.controller;

import com.moris.resto.dto.UtilisateurDto;
import com.moris.resto.security.PermissionHandle;
import com.moris.resto.service.UtilisateurService;
import com.moris.resto.user.Utilisateur;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController implements UtilisateurApi{
    private final UtilisateurService utilisateurService;
    private final PermissionHandle permissionHandle;

    public UtilisateurController(UtilisateurService utilisateurService, PermissionHandle permissionHandle) {
        this.utilisateurService = utilisateurService;
        this.permissionHandle = permissionHandle;
    }

    @Override
    public Utilisateur save(UtilisateurDto dto) {
        return null;
    }

    @Override
    public Utilisateur findById(Integer id) {
        return null;
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    @GetMapping("/me")
    @Operation(summary = "Retourne les infos de l'utilisateur connecté")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        return permissionHandle.getAuthentication();
    }
}
