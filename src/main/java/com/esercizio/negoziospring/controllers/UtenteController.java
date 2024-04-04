package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.dto.ReqResDTO;
import com.esercizio.negoziospring.entities.Carrello;
import com.esercizio.negoziospring.entities.DettagliCarrello;
import com.esercizio.negoziospring.entities.Utente;
import com.esercizio.negoziospring.repository.CarrelloRepository;
import com.esercizio.negoziospring.repository.UtenteRepository;
import com.esercizio.negoziospring.services.AuthService;
import com.esercizio.negoziospring.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/admin/utenti")
    public ResponseEntity<List<Utente>> findAllUtenti(){
        List<Utente> response = utenteRepository.findAll();
        return Utility.checkIfUtenteResponseEntityNotFund(response);
    }

    @GetMapping("/admin/utentebyid/{codiceUtente}")
    public ResponseEntity<Utente> findUtenteById(@PathVariable int codiceUtente){
        Utente response= utenteRepository.findById(codiceUtente).orElse(null);
        return Utility.checkIfUtenteResponseEntityNotFund(response);
    }

    @GetMapping("/admin/utentebyemail/{emailUtente}")
    public ResponseEntity<Utente> findUtenteByEmail(@PathVariable String emailUtente){
        Utente response= utenteRepository.findByEmail(emailUtente).orElse(null);
        return Utility.checkIfUtenteResponseEntityNotFund(response);
    }

    @PostMapping("/public/registerutente")
    public ResponseEntity<ReqResDTO> createNewUtente(@RequestBody ReqResDTO nuovoUtente){
        Utente checkIfExists= utenteRepository.findByEmail(nuovoUtente.getEmail()).orElse(null);
        if(checkIfExists!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(authService.signUp(nuovoUtente));
    }

    @PostMapping("/public/loginutente")
    public ResponseEntity<ReqResDTO> loginUtente(@RequestBody ReqResDTO daLoggare){
        return ResponseEntity.ok(authService.signIn(daLoggare));
    }

    @PutMapping("/adminuser/updateutente")
    public ResponseEntity<ReqResDTO> updateUtente(@RequestBody ReqResDTO utenteModificato){
        return ResponseEntity.ok(authService.updateUtente(utenteModificato));
    }

    @DeleteMapping("/adminuser/deleteutente/{codiceUtente}")
    public ResponseEntity<Void> deleteUtente(@PathVariable int codiceUtente){
        Utente checkIfExists= utenteRepository.findById(codiceUtente).orElse(null);
        if(checkIfExists==null || checkIfExists.getCodiceUtente()!=codiceUtente){
            return ResponseEntity.notFound().build();
        }
        carrelloRepository.deleteById(carrelloRepository
                .findByUtente_CodiceUtente(codiceUtente)
                .get().getCodiceCarrello());
        utenteRepository.deleteById(codiceUtente);
        return ResponseEntity.accepted().build();
    }
}

