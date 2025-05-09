package com.moris.resto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
@Entity
@Table(name = "PLAT")
public class Plat extends AbstractEntity{

    private String libelle ;
    private Long prix ;
    private String codePlat;
    @OneToMany(mappedBy = "plat")
    @JsonIgnore
    private List<LigneCommandeClient> lignesCommande;

    public String getCodePlat() {
        return codePlat;
    }

    public void setCodePlat(String codePlat) {
        this.codePlat = codePlat;
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

    public List<LigneCommandeClient> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<LigneCommandeClient> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }
}
