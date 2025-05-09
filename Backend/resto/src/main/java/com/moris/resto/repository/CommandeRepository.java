package com.moris.resto.repository;

import com.moris.resto.entity.CommandeClient;
import com.moris.resto.enums.EtatCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<CommandeClient, Long> {
    List<CommandeClient> findAllByEtatCommande(EtatCommande etat);
}
