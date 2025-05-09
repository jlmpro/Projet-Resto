package com.moris.resto.service;

import com.moris.resto.dto.CommandeClientDto;
import com.moris.resto.dto.LigneCommandeClientDto;
import com.moris.resto.entity.CommandeClient;
import com.moris.resto.entity.LigneCommandeClient;
import com.moris.resto.entity.Plat;
import com.moris.resto.enums.EtatCommande;
import com.moris.resto.repository.CommandeRepository;
import com.moris.resto.repository.LigneCommandeRepository;
import com.moris.resto.repository.PlatRepository;
import com.moris.resto.user.Utilisateur;
import com.moris.resto.user.UtilisateurRepository;
import com.moris.resto.validator.CommandeValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeService {
    private static final Logger log = LoggerFactory.getLogger(PlatService.class);


    private final CommandeRepository commandeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PlatRepository platRepository;
    private final LigneCommandeRepository ligneCommandeRepository;

    public CommandeService(CommandeRepository commandeRepository, UtilisateurRepository utilisateurRepository, PlatRepository platRepository, LigneCommandeRepository ligneCommandeRepository) {
        this.commandeRepository = commandeRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.platRepository = platRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    public ResponseEntity<?> creerCommande(CommandeClientDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Validation de la commande
        List<String> errors = CommandeValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Erreur lors de la création de la commande : {}", errors);
            Map<String, List<String>> err = Map.of("message", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }

        // Récupération de l'utilisateur
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Création de la commande
        CommandeClient commande = new CommandeClient();
        commande.setDateCommande(Instant.now());
        commande.setEtatCommande(EtatCommande.EN_ATTENTE);
        commande.setUtilisateurId(utilisateur.getId());

        // Sauvegarde initiale de la commande pour générer l'ID (nécessaire si les lignes la référencent)
        commande = commandeRepository.save(commande);

        List<LigneCommandeClient> lignes = new ArrayList<>();

        if (dto.getLigneCommandeClients() != null) {
            for (LigneCommandeClientDto ligneDto : dto.getLigneCommandeClients()) {

                Plat plat = platRepository.findById(ligneDto.getIdPlat())
                        .orElseThrow(() -> new EntityNotFoundException("Plat avec l'ID " + ligneDto.getIdPlat() + " introuvable"));

                LigneCommandeClient ligne = new LigneCommandeClient();
                ligne.setCommandeClient(commande);
                ligne.setPlat(plat);
                ligne.setQuantite(ligneDto.getQuantite());

                lignes.add(ligne);
            }
            ligneCommandeRepository.saveAll(lignes);
        }

        commande.setLigneCommandeClients(lignes);
        return ResponseEntity.status(HttpStatus.CREATED).body(commande);
    }


    public CommandeClient changerEtatCommande(Long commandeId, EtatCommande nouvelEtat) {
        CommandeClient commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        commande.setEtatCommande(nouvelEtat);
        return commandeRepository.save(commande);
    }

    public List<CommandeClient> getCommandesParEtat(EtatCommande etat) {
        return commandeRepository.findAllByEtatCommande(etat);
    }

    public ResponseEntity<?> findAll(){
        List<CommandeClient> list = commandeRepository.findAll();

        List<CommandeClientDto> cmd = list.stream()
                .map(CommandeClientDto::fromEntity)
                .toList();

        return ResponseEntity.ok(cmd);
    }

    public ResponseEntity<?> findById(Long id){
        if (id == null){
            throw new IllegalArgumentException("L'ID est nécessaire pour la recherche du commande");
        }
        Optional<CommandeClient> cmd = commandeRepository.findById(id);
        if (cmd.isEmpty()){
            log.error("Aucune commande avec l'id "+ id +"n'est trouvée");
            Map<String, String> err = Map.of("error", "Aucune commande avec l'id \"+ id +\"n'est trouvée");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
        }

        return ResponseEntity.ok(cmd);
    }


}
