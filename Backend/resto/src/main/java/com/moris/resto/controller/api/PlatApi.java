package com.moris.resto.controller.api;


import com.moris.resto.dto.PlatDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PlatApi {

    @PostMapping(value = "/save")
    @Operation(summary = "Enregistrer un plat")
    PlatDto save(@RequestBody PlatDto dto);

    @GetMapping("/find/{id}")
    PlatDto findById(@PathVariable Long id);

    @GetMapping("/list")
    List<PlatDto> findAll();




}
