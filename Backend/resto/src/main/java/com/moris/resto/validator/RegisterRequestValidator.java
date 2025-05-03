package com.moris.resto.validator;

import com.moris.resto.security.auth.RegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterRequestValidator {

    public static List<String> validate(RegisterRequest request){
        List<String> errors = new ArrayList<>();
        if (request == null) {
            errors.add("Veuiller renseigner le nom de l'request");
            errors.add("Veuiller renseigner le prénom de l'request");
            errors.add("Veuiller renseigner le mot de passe de l'request");
            errors.add("Veuiller renseigner l'adresse de l'request");
            errors.add("Veuiller mentionner votre poste (Serveur / Cuisinier");

            return errors;
        }
        if (!StringUtils.hasLength(request.getLastname())){
            errors.add("Veuiller renseigner le nom de l'request");
        }
        if (!StringUtils.hasLength(request.getFirstname())){
            errors.add("Veuiller renseigner le prénom de l'request");
        }
        if (!StringUtils.hasLength(request.getEmail())){
            errors.add("Veuiller renseigner l'email de l'request");
        }
        if (!StringUtils.hasLength(request.getPassword())){
            errors.add("Veuiller renseigner le mot de passe de l'request");
        }
        if (!StringUtils.hasLength(request.getRole())){
            errors.add("Veuiller mentionner votre poste (Serveur / Cuisinier");
        }

        return errors;
    }
}
