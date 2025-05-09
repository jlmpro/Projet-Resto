package com.moris.resto.dto;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moris.resto.entity.CommandeClient;
import com.moris.resto.entity.LigneCommandeClient;
import com.moris.resto.enums.EtatCommande;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;



@Data
@NoArgsConstructor
public class CommandeClientDto {
    private Long id ;
    private Instant dateCommande;
    @JsonIgnore
    private EtatCommande etatCommande;
    private String commentaire;


    private List<LigneCommandeClientDto> ligneCommandeClients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }


    public List<LigneCommandeClientDto> getLigneCommandeClients() {
        return ligneCommandeClients;
    }

    public void setLigneCommandeClients(List<LigneCommandeClientDto> ligneCommandeClients) {
        this.ligneCommandeClients = ligneCommandeClients;
    }

    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {
        if (commandeClient == null) {
            return null;
        }

        CommandeClientDto dto = new CommandeClientDto();
        dto.setId(commandeClient.getId());
        dto.setDateCommande(commandeClient.getDateCommande());
        dto.setEtatCommande(commandeClient.getEtatCommande());
        dto.setCommentaire(commandeClient.getCommentaire());

        dto.setLigneCommandeClients(
                commandeClient.getLigneCommandeClients() != null
                        ? commandeClient.getLigneCommandeClients().stream()
                        .map(LigneCommandeClientDto:: fromEntity)
                        .collect(Collectors.toList())
                        : null
        );

        return dto;
    }


    public static CommandeClient toEntity(CommandeClientDto dto) {
        if (dto == null) {
            return null;
        }

        CommandeClient entity = new CommandeClient();
        entity.setDateCommande(dto.getDateCommande());
        entity.setEtatCommande(dto.getEtatCommande());
        entity.setCommentaire(dto.getCommentaire());


        if (dto.getLigneCommandeClients() != null) {
            List<LigneCommandeClient> lignes = dto.getLigneCommandeClients().stream()
                    .map(LigneCommandeClientDto::toEntity)
                    .peek(ligne -> ligne.setCommandeClient(entity))
                    .collect(Collectors.toList());
            entity.setLigneCommandeClients(lignes);
        }

        return entity;
    }









  /*  public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }*/




}
