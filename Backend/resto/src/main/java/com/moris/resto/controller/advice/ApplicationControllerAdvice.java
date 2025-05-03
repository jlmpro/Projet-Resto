package com.moris.resto.controller.advice;

import com.moris.resto.exception.ErrorEntity;
import com.moris.resto.security.auth.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@ControllerAdvice
public class ApplicationControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public @ResponseBody
    ProblemDetail badCredentialsExceptions(BadCredentialsException exception) {
        ApplicationControllerAdvice.log.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                UNAUTHORIZED,
                "identifiants invalides"
        );
        problemDetail.setProperty("erreur", "nous n'avons pas pu vous identifier");
        return problemDetail;
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
    public @ResponseBody
    ProblemDetail badCredentialsException(final org.springframework.security.access.AccessDeniedException exception) {
        ApplicationControllerAdvice.log.error(exception.getMessage(), exception);
        return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                "Vos droits ne vous permettent pas d'effectuer cette action"
        );
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value =RuntimeException.class)
    public @ResponseBody
    ProblemDetail badCredentialsException(RuntimeException exception) {
        ApplicationControllerAdvice.log.error(exception.getMessage(), exception);
        return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                exception.getMessage()
        );
    }
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = SignatureException.class)
    public @ResponseBody
    ProblemDetail signatureException(SignatureException exception) {
        ApplicationControllerAdvice.log.error(exception.getMessage(), exception);
        return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                "Token invalide"
        );
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ErrorEntity handleException(EntityNotFoundException e){
        return new ErrorEntity(null, e.getMessage());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> exceptionHandler(){
        return  Map.of("erreur","description");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé");
    }




}
