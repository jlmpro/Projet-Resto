package com.moris.resto.validator;


import com.moris.resto.dto.UtilisateurDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UtilisateurValidator {
    public static List<String> validate(UtilisateurDto utilisateur){
        List<String> errors = new ArrayList<>();
        if (utilisateur == null) {
            errors.add("Veuiller renseigner le nom de l'utilisateur");
            errors.add("Veuiller renseigner le prénom de l'utilisateur");
            errors.add("Veuiller renseigner le mot de passe de l'utilisateur");
            errors.add("Veuiller renseigner l'adresse de l'utilisateur");
            errors.add("Veuiller mentionner votre poste (Serveur / Cuisinier");

            return errors;
        }
        if (!StringUtils.hasLength(utilisateur.getLastname())){
            errors.add("Veuiller renseigner le nom de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateur.getFirstname())){
            errors.add("Veuiller renseigner le prénom de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateur.getEmail())){
            errors.add("Veuiller renseigner l'email de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateur.getPassword())){
            errors.add("Veuiller renseigner le mot de passe de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateur.getRole().toString())){
            errors.add("Veuiller mentionner votre poste (Serveur / Cuisinier");
        }

        return errors;
        }

    }






