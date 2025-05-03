package com.moris.resto.security.token;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moris.resto.user.Utilisateur;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
public class Token {

  @Id
  @GeneratedValue
  public Integer id;

  @Column(unique = true)
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
//  @com.fasterxml.jackson.annotation.JsonBackReference
  @JsonIgnore
  public Utilisateur utilisateur;


  private Token(){}

  // Constructeur privé pour utiliser le Builder
  private Token(Builder builder) {
    this.id = builder.id;
    this.token = builder.token;
    this.tokenType = builder.tokenType;
    this.revoked = builder.revoked;
    this.expired = builder.expired;
    this.utilisateur = builder.utilisateur;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public TokenType getTokenType() {
    return tokenType;
  }

  public void setTokenType(TokenType tokenType) {
    this.tokenType = tokenType;
  }

  public boolean isRevoked() {
    return revoked;
  }

  public void setRevoked(boolean revoked) {
    this.revoked = revoked;
  }

  public boolean isExpired() {
    return expired;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }

  // Classe statique interne Builder
  public static class Builder {
    private Integer id;
    private String token;
    private TokenType tokenType = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    private Utilisateur utilisateur;

    public Builder() {}

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder token(String token) {
      this.token = token;
      return this;
    }

    public Builder tokenType(TokenType tokenType) {
      this.tokenType = tokenType;
      return this;
    }

    public Builder revoked(boolean revoked) {
      this.revoked = revoked;
      return this;
    }

    public Builder expired(boolean expired) {
      this.expired = expired;
      return this;
    }

    public Builder utilisateur(Utilisateur utilisateur) {
      if (utilisateur == null) {
        throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
      }
      this.utilisateur = utilisateur;
      return this;
    }


    public Token build() {
      return new Token(this);
    }
  }

  // Méthode statique pour obtenir un nouveau Builder
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Token token = (Token) o;
    return id != null && Objects.equals(id, token.id);
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}

