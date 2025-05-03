package com.moris.resto.user;

import com.moris.resto.entity.AbstractEntity;
import com.moris.resto.entity.Role;
import com.moris.resto.security.token.Token;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity implements UserDetails{


  private String firstname;

  private String lastname;

  private String email;
  private String password;


  @OneToOne(cascade = CascadeType.ALL)
  private Role role;

  @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @com.fasterxml.jackson.annotation.JsonManagedReference
  private List<Token> tokens;


  public Utilisateur(){}



  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setTokens(List<Token> tokens) {
    this.tokens = tokens;
  }

  private Utilisateur(Builder builder) {
    this.firstname = builder.firstname;
    this.lastname = builder.lastname;
    this.email = builder.email;
    this.password = builder.password;
    this.role = builder.role;
    this.tokens = builder.tokens;
  }

  public static class Builder {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private List<Token> tokens;



    public Builder firstname(String firstname) {
      this.firstname = firstname;
      return this;
    }

    public Builder lastname(String lastname) {
      this.lastname = lastname;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder role(Role role) {
      this.role = role;
      return this;
    }

    public Builder tokens(List<Token> tokens) {
      this.tokens = tokens;
      return this;
    }

    public Utilisateur build() {
      return new Utilisateur(this);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public List<Token> getTokens() {
    return tokens;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.role.getLibelle().getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

}
