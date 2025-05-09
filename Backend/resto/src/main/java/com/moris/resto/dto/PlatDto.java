package com.moris.resto.dto;




import com.moris.resto.entity.Plat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class PlatDto {
    private Long id ;
    private String codePlat;
    private  String libelle ;
    private Long prix ;

    public String getCodePlat() {
        return codePlat;
    }

    public void setCodePlat(String codePlat) {
        this.codePlat = codePlat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public static PlatDto fromEntity(Plat plat){
        if (plat == null){
            return null ;
        }
           PlatDto platDto = new PlatDto();
                platDto.setId(plat.getId());
                platDto.setLibelle(plat.getLibelle());
                platDto.setPrix(plat.getPrix());
                platDto.setCodePlat(plat.getCodePlat());

                return platDto;
    }

    public static Plat toEntity(PlatDto dto) {
        if (dto == null) {
            return null;
        }
            Plat plat = new Plat();
            plat.setId(dto.getId());
            plat.setLibelle(dto.getLibelle());
            plat.setPrix(dto.getPrix());
            plat.setCodePlat(dto.getCodePlat());

        return plat;
    }

}


