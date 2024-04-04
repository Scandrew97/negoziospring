package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.entities.Magazzino;
import com.esercizio.negoziospring.repository.MagazzinoRepository;
import com.esercizio.negoziospring.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class MagazzinoController {

    @Autowired
    private MagazzinoRepository magazzinoRepository;

    @GetMapping("/admin/giacenze")
    public ResponseEntity<List<Magazzino>> findAllGiacenze(){
        List<Magazzino> response= magazzinoRepository.findAll();
        return Utility.checkIfMagazzinoResponseEntityNotFund(response);
    }

    @GetMapping("/admin/giacenzebyid/{codiceArticolo}")
    public ResponseEntity<Magazzino> findGiacenzaByIdArticolo(@PathVariable int codiceArticolo){
        Magazzino response= magazzinoRepository.findByArticolo_CodiceArticolo(codiceArticolo).orElse(null);
        return Utility.checkIfMagazzinoResponseEntityNotFund(response);
    }
}
