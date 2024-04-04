package com.esercizio.negoziospring.utility;

import com.esercizio.negoziospring.entities.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Utility {
    public static ResponseEntity<List<Articolo>> checkIfArticoloResponseEntityNotFund(List<Articolo> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Articolo> checkIfArticoloResponseEntityNotFund(Articolo response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<Categoria>> checkIfCategoriaResponseEntityNotFund(List<Categoria> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Categoria> checkIfCategoriaResponseEntityNotFund(Categoria response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<Sottocategoria>> checkIfSottocategoriaResponseEntityNotFund
            (List<Sottocategoria> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Sottocategoria> checkIfSottocategoriaResponseEntityNotFund
            (Sottocategoria response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<Utente>> checkIfUtenteResponseEntityNotFund
            (List<Utente> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Utente> checkIfUtenteResponseEntityNotFund
            (Utente response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<Carrello>> checkIfCarrelloResponseEntityNotFund
            (List<Carrello> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Carrello> checkIfCarrelloResponseEntityNotFund
            (Carrello response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<Magazzino>> checkIfMagazzinoResponseEntityNotFund
            (List<Magazzino> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Magazzino> checkIfMagazzinoResponseEntityNotFund
            (Magazzino response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<Coupon>> checkIfCouponResponseEntityNotFund
            (List<Coupon> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Coupon> checkIfCouponResponseEntityNotFund
            (Coupon response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<List<ImmaginiArticolo>> checkIfImmaginiArticoloResponseEntityNotFund(List<ImmaginiArticolo> response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ImmaginiArticolo> checkIfImmaginiArticoloResponseEntityNotFund(ImmaginiArticolo response) {
        if(response==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
