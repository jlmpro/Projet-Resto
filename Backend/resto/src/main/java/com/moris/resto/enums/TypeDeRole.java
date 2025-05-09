package com.moris.resto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.moris.resto.enums.TypePermission.*;

public enum TypeDeRole {

    SERVEUR(
            Set.of(TypePermission.UTILISATEUR_SERVEUR)
    ),
    CUISINIER(
            Set.of(TypePermission.UTILISATEUR_CUISINIER)
    ),

    ADMINISTRATEUR(
            Set.of(
                    ADMINISTRATEUR_CREATE ,
                    ADMINISTRATEUR_READ,
                    ADMINISTRATEUR_UPDATE,
                    ADMINISTRATEUR_DELETE,

                    UTILISATEUR_CUISINIER,
                    UTILISATEUR_SERVEUR




            )
    );

    TypeDeRole(Set<TypePermission> permissions) {
        this.permissions = permissions;
    }


    Set<TypePermission> permissions;

    public Set<TypePermission> getPermissions() {
        return permissions;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.name())
        ).collect(Collectors.toList());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}