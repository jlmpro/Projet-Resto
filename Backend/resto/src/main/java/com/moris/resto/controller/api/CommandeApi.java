package com.moris.resto.controller.api;

import com.moris.resto.dto.CommandeClientDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommandeApi {

    @PostMapping("/save")
    CommandeClientDto save(@RequestBody CommandeClientDto dto);

    @GetMapping("/find/id")
    CommandeClientDto findById(Long id);

    @GetMapping("/list")
    List<CommandeClientDto> findAll();
}
