package com.moris.resto.controller.api;

import com.moris.resto.dto.CommandeClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface CommandeApi {

    @PostMapping("/save")
    ResponseEntity<?> save(@RequestBody CommandeClientDto dto);

    @GetMapping("/find/id")
    ResponseEntity<?> findById(Long id);

    @GetMapping("/list")
    ResponseEntity<?> findAll();
}
