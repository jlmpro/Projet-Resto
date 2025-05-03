package com.moris.resto.controller;

import com.moris.resto.dto.UtilisateurDto;
import com.moris.resto.user.Utilisateur;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UtilisateurApi {

    @PostMapping(value = "/save")
    @Operation(summary = "Enregistrer un utilisateur")
    Utilisateur save(@RequestBody UtilisateurDto dto);


    @GetMapping(value = "/find/{id}")
    @Operation(summary = "Retrouver un utilisateur par son ID")
    Utilisateur findById(Integer id);

    @PreAuthorize(value = "hasAnyAuthority('UTILISATEUR_CUISINIER','UTILISATEUR_SERVEUR')")
    @GetMapping(value = "/find/all")
    @Operation(summary = "Liste des utilisateur")
    List<UtilisateurDto> findAll();


}
