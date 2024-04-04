package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.entities.Categoria;
import com.esercizio.negoziospring.entities.Sottocategoria;
import com.esercizio.negoziospring.repository.ArticoliRepository;
import com.esercizio.negoziospring.repository.CategorieRepository;
import com.esercizio.negoziospring.repository.SottocategorieRepository;
import com.esercizio.negoziospring.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private SottocategorieRepository sottocategorieRepository;

    @Autowired
    private ArticoliRepository articoliRepository;

    @GetMapping("/public/categorie")
    public ResponseEntity<List<Categoria>> findTutteLeCategorie(){
        List<Categoria> response= categorieRepository.findAll();
        return Utility.checkIfCategoriaResponseEntityNotFund(response);
    }

    @GetMapping("/public/categoriabyid/{codiceCategoria}")
    public ResponseEntity<Categoria> findCategoriaById(@PathVariable int codiceCategoria){
        Categoria response= categorieRepository.findById(codiceCategoria).orElse(null);
        return Utility.checkIfCategoriaResponseEntityNotFund(response);
    }

    @GetMapping("/public/categoriabynome/{nomeCategoria}")
    public ResponseEntity<Categoria> findCategoriaByNomeCategoria(@PathVariable String nomeCategoria){
        Categoria response= categorieRepository.findByNomeCategoria(nomeCategoria).orElse(null);
        return Utility.checkIfCategoriaResponseEntityNotFund(response);
    }

    @PostMapping("/admin/categoria/{nomeCategoria}")
    public ResponseEntity<Categoria> createNewCategoria(@PathVariable String nomeCategoria){
        if(categorieRepository.findByNomeCategoria(nomeCategoria).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Categoria creata = Categoria.builder().nomeCategoria(nomeCategoria).build();
        return ResponseEntity.ok(categorieRepository.save(creata));

    }

    @PutMapping("/admin/categoria/{codice_categoria}")
    public ResponseEntity<Categoria> updateCategoria(@RequestBody Categoria categoriaModificata,
                                                     @PathVariable int codice_categoria){
        if (categorieRepository.findByNomeCategoria(categoriaModificata.getNomeCategoria()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Categoria modificata = categorieRepository.findById(codice_categoria).orElse(null);
        Utility.checkIfCategoriaResponseEntityNotFund(modificata);
        modificata.setCodiceCategoria(codice_categoria);
        modificata.setNomeCategoria(categoriaModificata.getNomeCategoria());
        return ResponseEntity.ok(categorieRepository.save(modificata));
    }

    @DeleteMapping("/admin/categoria/{codice_categoria}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable int codice_categoria){
        if(categorieRepository.findById(codice_categoria).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categorieRepository.deleteById(codice_categoria);
        return ResponseEntity.accepted().build();
    }
}
