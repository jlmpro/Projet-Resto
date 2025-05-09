package com.moris.resto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moris.resto.enums.EtatCommande;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "commandeclient")
public class CommandeClient extends AbstractEntity{


    @ToString.Include
    @Column(name = "dateCommande")
    private Instant dateCommande;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_commande")
    private EtatCommande etatCommande;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "utilisateur_id")
    private Long utilisateurId;


    @OneToMany(mappedBy = "commandeClient")
    @ToString.Exclude
    @JsonIgnore
    private List<LigneCommandeClient> ligneCommandeClients;


    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "CommandeClient{" +
                ", dateCommande=" + dateCommande +
                ", etatCommande=" + etatCommande +
                ", lignes=" + (ligneCommandeClients != null ? ligneCommandeClients.size() : 0) +
                '}';
    }

    public CommandeClient(){}




    public CommandeClient( Instant dateCommande, EtatCommande etatCommande, List<LigneCommandeClient> ligneCommandeClients) {
        this.dateCommande = dateCommande;
        this.etatCommande = etatCommande;
        this.ligneCommandeClients = ligneCommandeClients;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }


    public List<LigneCommandeClient> getLigneCommandeClients() {
        return ligneCommandeClients;
    }

    public void setLigneCommandeClients(List<LigneCommandeClient> ligneCommandeClients) {
        this.ligneCommandeClients = ligneCommandeClients;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CommandeClient that = (CommandeClient) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
