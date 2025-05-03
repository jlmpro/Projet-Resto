package com.moris.resto.dto;

import com.moris.resto.enums.TypeDeRole;
import com.moris.resto.security.token.Token;
import com.moris.resto.user.Utilisateur;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;


public class UtilisateurDto {

    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private TypeDeRole role;

    private Set<String> permissions;

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public TypeDeRole getRole() {
        return role;
    }

    public void setRole(TypeDeRole role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setId(utilisateur.getId());
        utilisateurDto.setLastname(utilisateur.getLastname());
        utilisateurDto.setFirstname(utilisateur.getFirstname());
        utilisateurDto.setEmail(utilisateur.getEmail());
        utilisateurDto.setPassword(utilisateur.getPassword());

        // Rôle
        TypeDeRole typeDeRole = utilisateur.getRole().getLibelle();
        utilisateurDto.setRole(typeDeRole);

        // Permissions
        Set<String> permissions = typeDeRole.getPermissions().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
        utilisateurDto.setPermissions(permissions);

        return utilisateurDto;
    }


    private static Token.Builder builder() {
        return null;
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setFirstname(utilisateurDto.getFirstname());
        utilisateur.setLastname(utilisateurDto.getLastname());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setPassword(utilisateurDto.getPassword());
        return utilisateur;
    }



}
