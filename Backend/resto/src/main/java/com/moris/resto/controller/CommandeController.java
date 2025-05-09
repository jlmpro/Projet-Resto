package com.moris.resto.controller;

import com.moris.resto.controller.api.CommandeApi;
import com.moris.resto.dto.CommandeClientDto;
import com.moris.resto.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commande")
public class CommandeController implements CommandeApi {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @Override
    public ResponseEntity<?> save(CommandeClientDto dto) {
        return commandeService.creerCommande(dto);
    }

    @Override
    public ResponseEntity<?>findById(Long id) {
        return commandeService.findById(id);
    }

    @Override
    public ResponseEntity<?> findAll() {
        return commandeService.findAll();
    }
}
