package com.moris.resto.dto;


import com.moris.resto.entity.Role;
import com.moris.resto.enums.TypeDeRole;
import lombok.Builder;
import lombok.Data;


public class RolesDto {

    private Long id;
    private TypeDeRole roleName;
    private UtilisateurDto utilisateur;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeDeRole getRoleName() {
        return roleName;
    }

    public void setRoleName(TypeDeRole roleName) {
        this.roleName = roleName;
    }

    public UtilisateurDto getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDto utilisateur) {
        this.utilisateur = utilisateur;
    }

    // =========================
    //        BUILDER MANUEL
    // =========================
    public static class Builder {
        private Long id;
        private TypeDeRole roleName;
        private UtilisateurDto utilisateur;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roleName(TypeDeRole roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder utilisateur(UtilisateurDto utilisateur) {
            this.utilisateur = utilisateur;
            return this;
        }

        public RolesDto build() {
            RolesDto dto = new RolesDto();
            dto.setId(this.id);
            dto.setRoleName(this.roleName);
            dto.setUtilisateur(this.utilisateur);
            return dto;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // =========================
    //       MAPPING LOGIC
    // =========================

    public static RolesDto fromEntity(Role roles) {
        if (roles == null) {
            return null;
        }

        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getLibelle())
                .build();
    }

    public static Role toEntity(RolesDto rolesDto) {
        if (rolesDto == null) {
            return null;
        }

        Role roles = new Role();
        roles.setLibelle(rolesDto.getRoleName());
        return roles;
    }
}

