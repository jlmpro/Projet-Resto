package com.moris.resto.security.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.moris.resto.entity.Role;
import com.moris.resto.enums.TypeDeRole;
import com.moris.resto.repository.RoleRepository;
import com.moris.resto.security.config.JwtService;
import com.moris.resto.security.token.Token;
import com.moris.resto.security.token.TokenRepository;
import com.moris.resto.security.token.TokenType;
import com.moris.resto.user.Utilisateur;
import com.moris.resto.user.UtilisateurRepository;
import com.moris.resto.validator.RegisterRequestValidator;
import com.moris.resto.validator.UtilisateurValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class AuthenticationService {
  private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

  private final RegisterRequestValidator registerRequestValidator;
  private final UtilisateurRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RoleRepository roleRepository;

    public AuthenticationService(UtilisateurValidator utilisateurValidator, RegisterRequestValidator registerRequestValidator, UtilisateurRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.registerRequestValidator = registerRequestValidator;
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

  public AuthenticationResponse register(RegisterRequest request) {

    List<String> errors = RegisterRequestValidator.validate(request);

    if (!errors.isEmpty()) {
      log.error("Enregistrement invalide veuillez vérifier les données ! {}", request);
    }


    boolean existEmail =  this.repository.existsByEmail(request.getEmail());

    if (existEmail){
      throw  new RuntimeException("Cet email est déjà utilisé !");
    }

    var roleString = request.getRole();
    TypeDeRole typeDeRole = TypeDeRole.valueOf(roleString.toUpperCase());

    Role role = roleRepository.findByLibelle(typeDeRole)
            .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + typeDeRole));

    var utilisateur = Utilisateur.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .build();


    var savedUtilisateur = repository.save(utilisateur);

    var jwtToken = jwtService.generateToken(utilisateur);
    var refreshToken = jwtService.generateRefreshToken(utilisateur);

    // Sauvegarder le token
    saveUtilisateurToken(savedUtilisateur, jwtToken);

    // Retourner la réponse
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }


  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
    } catch (BadCredentialsException ex) {
      throw new BadCredentialsException("Identifiants incorrects !");
    } catch (Exception e) {
      throw new RuntimeException("Erreur lors de l'authentification : " + e.getMessage());
    }

    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    revokeAllUserTokens(user);
    saveUtilisateurToken(user, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }



  private void saveUtilisateurToken(Utilisateur utilisateur, String jwtToken) {
    var token = Token.builder()
            .utilisateur(utilisateur)
            . token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Utilisateur user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUtilisateurToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
