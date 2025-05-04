package com.moris.resto.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import com.moris.resto.entity.LigneCommandeClient;
import com.moris.resto.entity.Plat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


public class LigneCommandeClientDto {

    @JsonIgnore
    private Long id;
    private Long idPlat;
    private Long quantite;
    @JsonIgnore
    private CommandeClientDto commandeClient;

    // Getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Long idPlat) {
        this.idPlat = idPlat;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public CommandeClientDto getCommandeClient() {
        return commandeClient;
    }

    public void setCommandeClient(CommandeClientDto commandeClient) {
        this.commandeClient = commandeClient;
    }


    public static class Builder {
        private Long id;
        private Long idPlat;
        private Long quantite;
        private CommandeClientDto commandeClient;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder idPlat(Long idPlat) {
            this.idPlat = idPlat;
            return this;
        }

        public Builder quantite(Long quantite) {
            this.quantite = quantite;
            return this;
        }

        public Builder commandeClient(CommandeClientDto commandeClient) {
            this.commandeClient = commandeClient;
            return this;
        }

        public LigneCommandeClientDto build() {
            LigneCommandeClientDto dto = new LigneCommandeClientDto();
            dto.setId(this.id);
            dto.setIdPlat(this.idPlat);
            dto.setQuantite(this.quantite);
            dto.setCommandeClient(this.commandeClient);
            return dto;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // =========================
    //       MAPPING LOGIC
    // =========================

    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient) {
        if (ligneCommandeClient == null) {
            return null;
        }

        Long articleDto = null;
        if (ligneCommandeClient.getPlat() != null) {
            articleDto = ligneCommandeClient.getPlat().getId();
        }

        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .quantite(ligneCommandeClient.getQuantite())
                .idPlat(articleDto)
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto dto) {
        if (dto == null) {
            return null;
        }

        LigneCommandeClient entity = new LigneCommandeClient();
        entity.setQuantite(dto.getQuantite());

        if (dto.getIdPlat() != null) {
            Plat plat = new Plat();
            plat.setId(dto.getIdPlat());
            entity.setPlat(plat);
        }

        return entity;
    }
}

