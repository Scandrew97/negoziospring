package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.entities.Articolo;
import com.esercizio.negoziospring.entities.Carrello;
import com.esercizio.negoziospring.entities.Coupon;
import com.esercizio.negoziospring.entities.DettagliCarrello;
import com.esercizio.negoziospring.repository.CarrelloRepository;
import com.esercizio.negoziospring.repository.CouponRepository;
import com.esercizio.negoziospring.services.CarrelloService;
import com.esercizio.negoziospring.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class CarrelloController {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private CarrelloService carrelloService;

    @Autowired
    private CouponRepository couponRepository;

    @GetMapping("/adminuser/carrellobyidutente/{codiceUtente}")
    public ResponseEntity<Carrello> findCarrelloByIdUtenteAndApplyCoupon(@PathVariable int codiceUtente){
        Carrello carrello = carrelloRepository.findByUtente_CodiceUtente(codiceUtente).orElse(null);
        Utility.checkIfCarrelloResponseEntityNotFund(carrello);
        return ResponseEntity.ok(carrello);
    }

    @GetMapping("/adminuser/carrellobyidutenteandapplycoupon/{codiceUtente}/{codiceCoupon}")
    public ResponseEntity<Carrello> findCarrelloByIdUtenteAndApplyCoupon(@PathVariable int codiceUtente,
                                                                 @PathVariable String codiceCoupon){
        Carrello carrello = carrelloRepository.findByUtente_CodiceUtente(codiceUtente).orElse(null);
        Utility.checkIfCarrelloResponseEntityNotFund(carrello);
        Coupon coupon = couponRepository.findByCodiceSconto(codiceCoupon).orElse(null);
        Utility.checkIfCouponResponseEntityNotFund(coupon);
        for(DettagliCarrello d : carrello.getDettagliCarrello()){
            if(d.getArticolo().getSottocategoria().equals(coupon.getSottocategoria())){
                carrelloService.calcolaTotale(d, coupon.getCodiceSconto());
            }
        }
        carrello.setTotaleCarrello(carrelloService.totaleCarrelli(carrello.getDettagliCarrello()));
        return ResponseEntity.ok(carrelloRepository.save(carrello));
    }

    @PutMapping("/adminuser/aggiungiArticolo/{codiceUtente}/{daAggiungere}/{quantita}")
    public ResponseEntity<Carrello> aggiungiArticolo(@PathVariable int codiceUtente,
                                                     @PathVariable int daAggiungere,
                                                     @PathVariable int quantita){
        return carrelloService.aggiungiArticolo(codiceUtente, daAggiungere, quantita);
    }

    @PutMapping("/adminuser/rimuoviArticolo/{codiceUtente}/{daRimuovere}/{quantita}")
    public ResponseEntity<Carrello> rimuoviArticolo(@PathVariable int codiceUtente,
                                                     @PathVariable int daRimuovere,
                                                     @PathVariable int quantita){
        return carrelloService.rimuoviArticolo(codiceUtente, daRimuovere, quantita);
    }
}
