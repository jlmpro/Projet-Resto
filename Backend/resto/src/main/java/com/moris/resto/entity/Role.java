package com.moris.resto.entity;

import com.moris.resto.enums.TypeDeRole;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeDeRole libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeDeRole getLibelle() {
        return libelle;
    }

    public void setLibelle(TypeDeRole libelle) {
        this.libelle = libelle;
    }
}
