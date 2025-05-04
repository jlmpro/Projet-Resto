package com.moris.resto.enums;



public enum EtatCommande {
    EN_ATTENTE,
    EN_COURS,
    PRET;

    public static EtatCommande fromValue(String value) {
        try {
            return EtatCommande.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid EtatCommande value: " + value);
        }
    }


}
