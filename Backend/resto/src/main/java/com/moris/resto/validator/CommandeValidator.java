package com.moris.resto.validator;

import com.moris.resto.dto.CommandeClientDto;
import com.moris.resto.enums.TypeDeRole;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeValidator {


    public  static List<String> validate(CommandeClientDto dto){
        List <String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("Veuiller renseigner un plat pour éffectuer la commande");
            return errors;
        }

        if (dto.getLigneCommandeClients() == null || dto.getLigneCommandeClients().isEmpty()) {
            errors.add("Veuillez ajouter au moins un plat pour effectuer la commande.");
        } else {
            dto.getLigneCommandeClients().forEach(ligne -> {
                if (ligne.getIdPlat() == null) {
                    errors.add("Chaque ligne de commande doit contenir un plat valide.");
                }
                if (ligne.getQuantite() == null || ligne.getQuantite() <= 0) {
                    errors.add("Chaque ligne de commande doit avoir une quantité valide (> 0).");
                }
            });
        }

        if (!StringUtils.hasLength(String.valueOf(dto.getEtatCommande()))){
            errors.add("Veuiller renseigner l'état de la commande'");
        }

        return errors;
    }
}
