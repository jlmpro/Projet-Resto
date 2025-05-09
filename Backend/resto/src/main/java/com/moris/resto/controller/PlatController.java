package com.moris.resto.controller;

import com.moris.resto.controller.api.PlatApi;
import com.moris.resto.dto.PlatDto;
import com.moris.resto.service.PlatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/plat")
@RestController
public class PlatController implements PlatApi {
    private final PlatService platService;

    public PlatController(PlatService platService) {
        this.platService = platService;
    }

    @Override
    public ResponseEntity<?> save(PlatDto dto) {
        return platService.save(dto);
    }

    @Override
    public PlatDto findById(Long id) {
        return platService.findById(id);
    }

    @Override
    public ResponseEntity<?>findAll() {
        return platService.listPlats();
    }
}
