package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.entities.Articolo;
import com.esercizio.negoziospring.entities.ImmaginiArticolo;
import com.esercizio.negoziospring.repository.ArticoliRepository;
import com.esercizio.negoziospring.repository.ImmaginiArticoloRepository;
import com.esercizio.negoziospring.utility.Utility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ImmaginiArticoloController {

    @Autowired
    private ImmaginiArticoloRepository immaginiArticoloRepository;

    @Autowired
    private ArticoliRepository articoliRepository;


    @PostMapping("/admin/addImmagine")
    public ResponseEntity<String> addImmagineArticolo(@RequestPart MultipartFile immagineToAdd,
                                                      @RequestPart String codiceArticolo) throws IOException {
        Articolo articolo = articoliRepository.findById(Integer.parseInt(codiceArticolo)).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(articolo);
        List<ImmaginiArticolo> immaginiArticoloList= immaginiArticoloRepository.findAllByArticolo_CodiceArticolo(Integer.parseInt(codiceArticolo));
        ImmaginiArticolo nuovaImmagine = ImmaginiArticolo.builder()
                .articolo(articolo)
                .immagine(immagineToAdd.getBytes())
                .nomeImmagine(articolo.getNomeArticolo()+"_"+(immaginiArticoloList.size()+1))
                .build();
        immaginiArticoloList.add(nuovaImmagine);
        immaginiArticoloRepository.save(nuovaImmagine);
        return ResponseEntity.accepted().body("Immagine Aggiunta Con Successo");
    }

    @PutMapping("/admin/updateImmagine")
    public ResponseEntity<String> updateImmagineArticolo(@RequestPart MultipartFile immagineUpToDate,
                                                         @RequestPart String codiceArticolo,
                                                         @RequestPart String nomeImmagineToUpdate) throws IOException {
        ImmaginiArticolo immagineArticolo = immaginiArticoloRepository.findByNomeImmagineAndArticolo_CodiceArticolo(nomeImmagineToUpdate, Integer.parseInt(codiceArticolo)).orElse(null);
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immagineArticolo);
        immagineArticolo.setImmagine(immagineUpToDate.getBytes());
        immagineArticolo.setNomeImmagine(immagineArticolo.getNomeImmagine());
        immagineArticolo.setArticolo(immagineArticolo.getArticolo());
        immaginiArticoloRepository.save(immagineArticolo);
        return ResponseEntity.accepted().body("Immagine Aggiornata Con Successo");
    }

    @Transactional
    @DeleteMapping("/admin/deleteImmagine/{codiceArticolo}/{nomeImmagineToDelete}")
    public ResponseEntity<String> deleteImmagineArticolo(@PathVariable int codiceArticolo,
                                                         @PathVariable String nomeImmagineToDelete) throws IOException {
        ImmaginiArticolo immagineArticolo = immaginiArticoloRepository.findByNomeImmagineAndArticolo_CodiceArticolo(nomeImmagineToDelete, codiceArticolo).orElse(null);
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immagineArticolo);
        immaginiArticoloRepository.deleteById(immagineArticolo.getCodiceImmagine());
        return ResponseEntity.accepted().body("Immagine Eliminata Con Successo");
    }
}
