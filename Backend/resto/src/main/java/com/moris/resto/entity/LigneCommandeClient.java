package com.moris.resto.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;


@RequiredArgsConstructor
@Entity
@Table(name = "lignecommandeclient")
public class LigneCommandeClient extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "ID_PLAT")
    private Plat plat;
    @ManyToOne
    @JoinColumn(name = "ID_COMMANDE_CLIENT")
    private CommandeClient commandeClient;
    @Column(name = "QUANTITE")
    private Long quantite;



    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LigneCommandeClient that = (LigneCommandeClient) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat article) {
        this.plat = article;
    }

    public CommandeClient getCommandeClient() {
        return commandeClient;
    }

    public void setCommandeClient(CommandeClient commandeClient) {
        this.commandeClient = commandeClient;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LigneCommandeClient{" +
                ", quantite=" + quantite +
                ", article=" + (plat != null ? plat.getLibelle() : "null") +
                '}';
    }

}

