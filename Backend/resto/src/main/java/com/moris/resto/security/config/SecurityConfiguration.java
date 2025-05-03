package com.moris.resto.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, LogoutHandler logoutHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }

    @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) // Désactiver CSRF
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers(
                                    "/auth/**",
                                    "/healthy",
                                    "/authenticate",
                                    "/auth/authenticate")
                            .permitAll() // Routes publiques
                            .requestMatchers("/api/serveur/**").hasRole("SERVEUR")
                            .requestMatchers("/api/cuisine/**").hasRole("CUISINIER")
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated() // Toutes les autres routes nécessitent une authentification
            )
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Mode sans session
            )
            .authenticationProvider(authenticationProvider) // Provider d'authentification
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Filtre JWT
            .logout(logout ->
                    logout.logoutUrl("/auth/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) ->
                                    SecurityContextHolder.clearContext()
                            )
            );

    return http.build();
  }
}
