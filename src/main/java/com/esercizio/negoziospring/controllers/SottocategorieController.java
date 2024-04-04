package com.esercizio.negoziospring.controllers;



import com.esercizio.negoziospring.entities.Categoria;
import com.esercizio.negoziospring.entities.Sottocategoria;
import com.esercizio.negoziospring.repository.CategorieRepository;
import com.esercizio.negoziospring.repository.SottocategorieRepository;
import com.esercizio.negoziospring.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class SottocategorieController {

    @Autowired
    private SottocategorieRepository sottocategorieRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/public/sottocategorie")
    public ResponseEntity<List<Sottocategoria>> findTutteLeSottocategorie(){
        List<Sottocategoria> response= sottocategorieRepository.findAll();
        return Utility.checkIfSottocategoriaResponseEntityNotFund(response);
    }

    @GetMapping("/public/sottocategoriabycategoria/{codiceCategoria}")
    public ResponseEntity<List<Sottocategoria>> findSottocategorieByCategoria(@PathVariable int codiceCategoria){
        List<Sottocategoria> response= sottocategorieRepository.findAllByCategoria_CodiceCategoria(codiceCategoria).orElse(null);
        return Utility.checkIfSottocategoriaResponseEntityNotFund(response);
    }

    @GetMapping("/public/sottocategoriabyid/{codice_sottocategoria}")
    public ResponseEntity<Sottocategoria> findSottocategoriaById(@PathVariable int codice_sottocategoria){
        Sottocategoria response= sottocategorieRepository.findById(codice_sottocategoria).orElse(null);
        return Utility.checkIfSottocategoriaResponseEntityNotFund(response);
    }

    @GetMapping("public/sottocategoriabynome/{nome_sottocategoria}")
    public ResponseEntity<Sottocategoria> findSottocategoriaByNomeSottocategoria(
            @PathVariable String nome_sottocategoria){
        Sottocategoria response= sottocategorieRepository.findByNomeSottocategoria(nome_sottocategoria).orElse(null);
        return Utility.checkIfSottocategoriaResponseEntityNotFund(response);
    }

    @PostMapping("/admin/sottocategoria/{nomeSottocategoria}/{codiceCategoria}")
    public ResponseEntity<Sottocategoria> createNewSottocategoria(@PathVariable String nomeSottocategoria,
                                                                  @PathVariable int codiceCategoria){
        if(sottocategorieRepository.findByNomeSottocategoria(nomeSottocategoria).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Categoria toAssign= categorieRepository.findById(codiceCategoria).orElse(null);
        Utility.checkIfCategoriaResponseEntityNotFund(toAssign);

        Sottocategoria creata= Sottocategoria.builder().nomeSottocategoria(nomeSottocategoria).build();
        creata.setCategoria(toAssign);
        return ResponseEntity.ok(sottocategorieRepository.save(creata));
    }

    @PutMapping("/admin/sottocategoria/{codiceSottocategoria}")
    public ResponseEntity<Sottocategoria> updateSottocategoria(@RequestBody Sottocategoria sottocategoriaModificata,
                                                               @PathVariable int codiceSottocategoria){
        Sottocategoria modificata = sottocategorieRepository.findById(codiceSottocategoria).orElse(null);
        Utility.checkIfSottocategoriaResponseEntityNotFund(modificata);
        if(sottocategorieRepository.findByNomeSottocategoria(sottocategoriaModificata.getNomeSottocategoria()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Categoria toAssign= categorieRepository.findBySottocategoria_CodiceSottocategoria(codiceSottocategoria).orElse(null);
        Utility.checkIfCategoriaResponseEntityNotFund(toAssign);
        modificata.setNomeSottocategoria(sottocategoriaModificata.getNomeSottocategoria());
        modificata.setCategoria(toAssign);
        return ResponseEntity.ok(sottocategorieRepository.save(modificata));
    }

    @DeleteMapping("/admin/sottocategoria/{codiceSottocategoria}")
    public ResponseEntity<Void> deleteSottocategoria(@PathVariable int codiceSottocategoria){
        if(sottocategorieRepository.findById(codiceSottocategoria).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        sottocategorieRepository.deleteById(codiceSottocategoria);
        return ResponseEntity.accepted().build();
    }


}
