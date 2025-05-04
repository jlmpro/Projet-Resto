package com.moris.resto.validator;

import com.moris.resto.dto.PlatDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatValidator {
    public static List<String> validate(PlatDto platDto){
        List<String> errors = new ArrayList<>();
        if (platDto == null) {
            errors.add("Veuiller renseigner le libellé du plat");
            errors.add("Veuiller renseigner le code du plat");
            errors.add("Veuiller renseigner le prix du plat");

            return errors;
        }
        if (!StringUtils.hasLength(platDto.getLibelle())){
            errors.add("Veuiller renseigner le libellé du plat");
        }
        if (!StringUtils.hasLength(String.valueOf(platDto.getPrix()))){
            errors.add("Veuiller renseigner le prix du plat");
        }
        if (!StringUtils.hasLength(platDto.getCodePlat())){
            errors.add("Veuiller renseigner le code du plat");
        }
        return errors;
    }
}
