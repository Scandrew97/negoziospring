package com.esercizio.negoziospring.services;

import com.esercizio.negoziospring.dto.ReqResDTO;
import com.esercizio.negoziospring.entities.Carrello;
import com.esercizio.negoziospring.entities.Utente;
import com.esercizio.negoziospring.repository.CarrelloRepository;
import com.esercizio.negoziospring.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqResDTO signUp(ReqResDTO registrationRequest){
        ReqResDTO resp= new ReqResDTO();
        try {
            Utente utente = new Utente();
            Carrello newCarrello = new Carrello();
            utente.setNome(registrationRequest.getNome());
            utente.setCognome(registrationRequest.getCognome());
            utente.setUsername(registrationRequest.getUsername());
            utente.setEmail(registrationRequest.getEmail());
            utente.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            utente.setRole(registrationRequest.getRole());
            Utente risultato = utenteRepository.save(utente);
            newCarrello.setUtente(risultato);
            carrelloRepository.save(newCarrello);
            if (risultato.getCodiceUtente()>0){
                resp.setUtente(risultato);
                resp.setCodiceUtente(risultato.getCodiceUtente());
                resp.setCodiceCarrello(newCarrello.getCodiceCarrello());
                resp.setMessage("Utente Registrato Correttamente");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        System.out.println(resp.getMessage());
        return resp;
    }

    public ReqResDTO updateUtente(ReqResDTO updateRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updateRequest.getEmail(), updateRequest.getPassword()));
            var user = utenteRepository.findByEmail(updateRequest.getEmail()).orElseThrow();
            user.setCodiceUtente(user.getCodiceUtente());
            user.setNome(updateRequest.getNome());
            user.setCognome(updateRequest.getCognome());
            user.setUsername(updateRequest.getUsername());
            if (!updateRequest.getNuovaPassword().isEmpty() && updateRequest.getPassword()!=null){
                updateRequest.setNuovaPassword(updateRequest.getNuovaPassword().trim());
                if (!updateRequest.getNuovaPassword().isBlank()){
                    user.setPassword(passwordEncoder.encode(updateRequest.getNuovaPassword()));
                }else {
                    updateRequest.setStatusCode(500);
                    updateRequest.setMessage("Password Cannot Be Blank/Empty");
                }
            }
            utenteRepository.save(user);
            updateRequest.setStatusCode(200);
            updateRequest.setMessage("Credenziali Aggiornate Correttamente");
        }catch (Exception e){
            updateRequest.setStatusCode(500);
            updateRequest.setError(e.getMessage());
        }
        System.out.println(updateRequest.getMessage());
        return updateRequest;
    }

    public ReqResDTO signIn(ReqResDTO signInRequest){
        ReqResDTO resp= new ReqResDTO();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            var user = utenteRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
            var carrello = carrelloRepository.findByUtente_CodiceUtente(user.getCodiceUtente()).orElseThrow();
            System.out.println("Utente : "+user);
            var jwt= jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24h");
            resp.setMessage("Accesso Eseguito Correttamente");
            resp.setNome(user.getNome());
            resp.setCognome(user.getCognome());
            resp.setEmail(user.getEmail());
            resp.setCodiceCarrello(carrello.getCodiceCarrello());
            resp.setCodiceUtente(user.getCodiceUtente());
            resp.setRole(user.getRole());
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        System.out.println(resp.getMessage());
        return resp;
    }

    public ReqResDTO refreshToken(ReqResDTO refreshTokenRequest){
        ReqResDTO resp= new ReqResDTO();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        Utente utente = utenteRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), utente)){
            var jwt = jwtUtils.generateToken(utente);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshTokenRequest.getToken());
            resp.setExpirationTime("24h");
            resp.setMessage("Token Aggiornato Correttamente");
        }
        resp.setStatusCode(500);
        return resp;
    }
}
