package com.esercizio.negoziospring.services;

import com.esercizio.negoziospring.entities.*;
import com.esercizio.negoziospring.repository.*;
import com.esercizio.negoziospring.utility.Utility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrelloService {

    @Autowired
    private ArticoliRepository articoliRepository;

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private MagazzinoRepository magazzinoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private DettagliCarrelloRepository dettagliCarrelloRepository;

    private Utente getUtenteByIdUtenteAndCheckIfExists(int codiceUtente) {
        Utente check= utenteRepository.findById(codiceUtente).orElse(null);
        Utility.checkIfUtenteResponseEntityNotFund(check);
        return check;
    }

    private Articolo getArticoloByIdAndCheckIfExists(int daAggiungere) {
        Articolo checkIfExists= articoliRepository.findById(daAggiungere).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(checkIfExists);
        return checkIfExists;
    }

    private ResponseEntity<Carrello> getCarrelloByIdUtenteAndArticoloAndCheckIfExistsAggungi(int codiceUtente, int codiceArticolo) {
        Articolo daAggiungere= getArticoloByIdAndCheckIfExists(codiceArticolo);
        Optional<Carrello> check= carrelloRepository.findByUtente_CodiceUtenteAndDettagliCarrello_Articolo_CodiceArticolo(codiceUtente, codiceArticolo);
        if(check.isEmpty()){
            Carrello carrello = carrelloRepository.findByUtente_CodiceUtente(codiceUtente).get();
            carrello.getDettagliCarrello().add(dettagliCarrelloRepository.save(DettagliCarrello.builder()
                    .articolo(daAggiungere)
                    .build()));
            return ResponseEntity.ok(carrelloRepository.save(carrello));
        }
        return ResponseEntity.ok(check.get());
    }

    private ResponseEntity<Carrello> getCarrelloByIdUtenteAndArticoloAndCheckIfExists(int codiceUtente, int codiceArticolo) {
//        Articolo daRimuovere= getArticoloByIdAndCheckIfExists(codiceArticolo);
        Optional<Carrello> check= carrelloRepository.findByUtente_CodiceUtenteAndDettagliCarrello_Articolo_CodiceArticolo(codiceUtente, codiceArticolo);
        if(check.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(check.get());
    }

    public ResponseEntity<Carrello> aggiungiArticolo(int codiceUtente, int daAggiungere, int quantita){
        Articolo articolo= getArticoloByIdAndCheckIfExists(daAggiungere);
        Carrello carrello = getCarrelloByIdUtenteAndArticoloAndCheckIfExistsAggungi(codiceUtente,daAggiungere).getBody();
        Magazzino giacenza= magazzinoRepository.findByArticolo_CodiceArticolo(daAggiungere).get();
        if(giacenza.getGiacenzaArticolo()<=0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        giacenza.setGiacenzaArticolo(giacenza.getGiacenzaArticolo()-quantita);
        for(DettagliCarrello dettagli: carrello.getDettagliCarrello()){
            if (dettagli.getArticolo().equals(articolo)){
                dettagli.setArticoloQuantita(dettagli.getArticoloQuantita()+quantita);
                dettagli.setCouponIsUsed(false);
                dettagli.setPrezzoTotale(dettagli.getArticolo().getPrezzo()*dettagli.getArticoloQuantita());
                calcolaTotale(dettagli, null);
                break;
            }
        }
        totaleCarrelli(carrello.getDettagliCarrello());
        carrelloRepository.save(carrello);
        return ResponseEntity.ok(carrello);
    }

    @Transactional
    public ResponseEntity<Carrello> rimuoviArticolo(int codiceUtente, int daRimuovere, int quantita){
        Articolo articolo= getArticoloByIdAndCheckIfExists(daRimuovere);
        Carrello carrello = getCarrelloByIdUtenteAndArticoloAndCheckIfExists(codiceUtente,daRimuovere).getBody();
        Magazzino giacenza= magazzinoRepository.findByArticolo_CodiceArticolo(daRimuovere).get();
        for(DettagliCarrello dettagli: carrello.getDettagliCarrello()){
            if (dettagli.getArticolo().equals(articolo)){
                if (dettagli.getArticoloQuantita()<quantita) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                } else {
                    dettagli.setArticoloQuantita(dettagli.getArticoloQuantita()-quantita);
                    giacenza.setGiacenzaArticolo(giacenza.getGiacenzaArticolo()+quantita);
                    calcolaTotale(dettagli,null);
                    totaleCarrelli(carrello.getDettagliCarrello());
                    if(dettagli.getArticoloQuantita()==0){
                        carrello.getDettagliCarrello().remove(dettagli);
                        totaleCarrelli(carrello.getDettagliCarrello());
                        dettagliCarrelloRepository.delete(dettagli);
                        carrelloRepository.save(carrello);
                        return ResponseEntity.accepted().build();
                    }
                }
            }
        }
        return ResponseEntity.ok(carrelloRepository.save(carrello));
    }

    public DettagliCarrello calcolaTotale(DettagliCarrello carrello, String codiceCoupon){
        double totale;
        double sconto=1;
        Coupon check= couponRepository.findByCodiceSconto(codiceCoupon).orElse(null);
        if(check!=null && !carrello.isCouponIsUsed()) {
            if(couponRepository.findByCodiceIdentificativoAndSottocategoria_CodiceSottocategoria(check.getCodiceIdentificativo(),
                    carrello.getArticolo().getSottocategoria().getCodiceSottocategoria()).isPresent()){
                sconto = (double) (100-check.getSconto()) / 100;
                carrello.setCouponIsUsed(true);
            }
        }
        totale=(carrello.getArticolo().getPrezzo()*carrello.getArticoloQuantita())*sconto;
        carrello.setPrezzoTotale(totale);
        return carrello;
    }

    public double totaleCarrelli(List<DettagliCarrello> carrelloList){
        double totale = 0;
        for (DettagliCarrello c: carrelloList){
            totale +=c.getPrezzoTotale();
        }
        return totale;
    }
}
