package com.moris.resto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum TypePermission {

    ADMINISTRATEUR_CREATE,
    ADMINISTRATEUR_READ,
    ADMINISTRATEUR_UPDATE,
    ADMINISTRATEUR_DELETE,

    UTILISATEUR_CUISINIER,
    UTILISATEUR_SERVEUR;


    @Getter
    private String libelle;


}