package com.moris.resto.service;

import com.moris.resto.dto.UtilisateurDto;
import com.moris.resto.user.Utilisateur;
import com.moris.resto.user.UtilisateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<UtilisateurDto> findAll() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

}
