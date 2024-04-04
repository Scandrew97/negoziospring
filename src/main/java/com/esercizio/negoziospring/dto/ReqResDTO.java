package com.esercizio.negoziospring.dto;

import com.esercizio.negoziospring.entities.Carrello;
import com.esercizio.negoziospring.entities.Utente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqResDTO {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String role;
    private String password;
    private String nuovaPassword;
    private int codiceUtente;
    private Utente utente;
    private int codiceCarrello;
}
