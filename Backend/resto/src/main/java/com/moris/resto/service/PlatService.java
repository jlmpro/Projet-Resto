package com.moris.resto.service;

import com.moris.resto.dto.PlatDto;
import com.moris.resto.entity.Plat;
import com.moris.resto.repository.PlatRepository;
import com.moris.resto.validator.PlatValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatService {
    private static final Logger log = LoggerFactory.getLogger(PlatService.class);

    private final PlatRepository platRepository;

    public PlatService(PlatRepository platRepository) {
        this.platRepository = platRepository;
    }

    public PlatDto findById(Long id){
        if (id == null){
            throw new IllegalArgumentException("Veuilez enter l'ID du plat à rechercher");
        }
        Optional<Plat> plat = platRepository.findById(id);
        if (plat.isEmpty()){
            throw new EntityNotFoundException("Aucun plat trouvé avec l'ID : "+id);
        }
        return PlatDto.fromEntity(plat.get());

    }

    public ResponseEntity<?> listPlats(){
        List<Plat> plats = platRepository.findAll();

        if(plats.isEmpty()){
            Map<String, String> response = Map.of("message", "Aucun plat trouvé dans la base de données");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<PlatDto> platDtos = plats.stream()
                .map(PlatDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(platDtos);
    }

    public ResponseEntity<?> save(PlatDto platDto) {
        List<String> errors = PlatValidator.validate(platDto);

        if (!errors.isEmpty()) {
            log.error("Erreur de validation lors de l'enregistrement du plat : {}", errors);
            Map<String, List<String>> err = Map.of("message", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }

        boolean isNew = platDto.getId() == null;

        if (isNew && platRepository.existsByCodePlat(platDto.getCodePlat())) {
            log.error("Un plat avec ce code existe déjà !");
            Map<String, String> err = Map.of("message", "Un plat avec ce code existe déjà");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
        }

        if (!isNew && !platRepository.existsById(platDto.getId())) {
            log.error("Aucun plat trouvé avec l'ID fourni pour la mise à jour !");
            Map<String, String> err = Map.of("message", "Plat non trouvé pour mise à jour");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }

        Plat saved = platRepository.save(PlatDto.toEntity(platDto));
        return ResponseEntity.ok(PlatDto.fromEntity(saved));
    }

}
