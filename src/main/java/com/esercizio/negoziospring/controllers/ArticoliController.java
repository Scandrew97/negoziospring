package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.dto.ArticoloDTO;
import com.esercizio.negoziospring.entities.Articolo;
import com.esercizio.negoziospring.entities.ImmaginiArticolo;
import com.esercizio.negoziospring.entities.Magazzino;
import com.esercizio.negoziospring.entities.Sottocategoria;
import com.esercizio.negoziospring.repository.ArticoliRepository;
import com.esercizio.negoziospring.repository.ImmaginiArticoloRepository;
import com.esercizio.negoziospring.repository.MagazzinoRepository;
import com.esercizio.negoziospring.repository.SottocategorieRepository;
import com.esercizio.negoziospring.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ArticoliController {

    @Autowired
    private ArticoliRepository articoliRepository;

    @Autowired
    private SottocategorieRepository sottocategorieRepository;

    @Autowired
    private MagazzinoRepository magazzinoRepository;

    @Autowired
    private ImmaginiArticoloRepository immaginiArticoloRepository;

    private ObjectMapper objectMapper;

    @GetMapping("/public/tuttiArticoli")
    public ResponseEntity<List<ArticoloDTO>> findTuttiGliArticoli(){
        List<Articolo> articoliRepositoryAll = articoliRepository.findAll();
        Utility.checkIfArticoloResponseEntityNotFund(articoliRepositoryAll);
        List<ImmaginiArticolo> immaginiRepositoryAll = immaginiArticoloRepository.findAll();
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepositoryAll);
        List<ArticoloDTO> response= new ArrayList<>();
        for (Articolo a : articoliRepositoryAll){
            Sottocategoria sottocategoria = sottocategorieRepository.findByArticolo_NomeArticolo(a.getNomeArticolo()).orElse(null);
            Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategoria);
            Magazzino magazzino = magazzinoRepository.findByArticolo_CodiceArticolo(a.getCodiceArticolo()).orElse(null);
            Utility.checkIfMagazzinoResponseEntityNotFund(magazzino);
            List<String> immaginiArticolo= new ArrayList<>();
            for (ImmaginiArticolo i : immaginiRepositoryAll){
                if (i.getArticolo().getCodiceArticolo()==a.getCodiceArticolo()){
                    immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
                }
            }
            ArticoloDTO articoloDTO = ArticoloDTO.builder() 
                    .codiceArticolo(a.getCodiceArticolo())
                    .nomeArticolo(a.getNomeArticolo())
                    .prezzo(a.getPrezzo())
                    .marcaArticolo(a.getMarcaArticolo())
                    .immaginiArticolo(immaginiArticolo)
                    .codiceSottocategoria(sottocategoria.getCodiceSottocategoria())
                    .codiceMagazzino(magazzino.getCodiceArticoloMagazzino())
                    .giacenzaMagazzino(magazzino.getGiacenzaArticolo())
                    .build();
            response.add(articoloDTO);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/searchArticoli/{searchBar}")
    public ResponseEntity<List<Articolo>> searchArticoloSearchBar(@PathVariable String searchBar){
        List<Articolo> response = articoliRepository.findAllByNomeArticoloContainingIgnoreCase(searchBar).orElse(null);
        return Utility.checkIfArticoloResponseEntityNotFund(response);
    }

    @GetMapping("/public/articolobyid/{codiceArticolo}")
    public ResponseEntity<ArticoloDTO> findArticoloById(@PathVariable int codiceArticolo){
        Articolo articolo= articoliRepository.findById(codiceArticolo).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(articolo);
        List<ImmaginiArticolo> immaginiRepository = immaginiArticoloRepository.findAllByArticolo_CodiceArticolo(codiceArticolo);
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepository);
        Sottocategoria sottocategoria = sottocategorieRepository.findByArticolo_CodiceArticolo(codiceArticolo).orElse(null);
        Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategoria);
        Magazzino magazzino = magazzinoRepository.findByArticolo_CodiceArticolo(codiceArticolo).orElse(null);
        Utility.checkIfMagazzinoResponseEntityNotFund(magazzino);
        List<String> immaginiArticolo= new ArrayList<>();
        for (ImmaginiArticolo i : immaginiRepository){
            immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
        }
        ArticoloDTO articoloDTO = ArticoloDTO.builder()
                .codiceArticolo(articolo.getCodiceArticolo())
                .nomeArticolo(articolo.getNomeArticolo())
                .prezzo(articolo.getPrezzo())
                .marcaArticolo(articolo.getMarcaArticolo())
                .immaginiArticolo(immaginiArticolo)
                .codiceSottocategoria(sottocategoria.getCodiceSottocategoria())
                .codiceMagazzino(magazzino.getCodiceArticoloMagazzino())
                .giacenzaMagazzino(magazzino.getGiacenzaArticolo())
                .build();

        return ResponseEntity.ok(articoloDTO);
    }

    @GetMapping("/public/articolobyname/{nomeArticolo}")
    public ResponseEntity<ArticoloDTO> findArticoloByNome(@PathVariable String nomeArticolo){
        Articolo articolo= articoliRepository.findByNomeArticolo(nomeArticolo).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(articolo);
        List<ImmaginiArticolo> immaginiRepository = immaginiArticoloRepository.findAllByArticolo_NomeArticolo(nomeArticolo);
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepository);
        Sottocategoria sottocategoria = sottocategorieRepository.findByArticolo_NomeArticolo(nomeArticolo).orElse(null);
        Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategoria);
        Magazzino magazzino = magazzinoRepository.findByArticolo_CodiceArticolo(articolo.getCodiceArticolo()).orElse(null);
        Utility.checkIfMagazzinoResponseEntityNotFund(magazzino);
        List<String> immaginiArticolo= new ArrayList<>();
        for (ImmaginiArticolo i : immaginiRepository){
            immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
        }
        ArticoloDTO articoloDTO = ArticoloDTO.builder()
                .codiceArticolo(articolo.getCodiceArticolo())
                .nomeArticolo(articolo.getNomeArticolo())
                .prezzo(articolo.getPrezzo())
                .marcaArticolo(articolo.getMarcaArticolo())
                .immaginiArticolo(immaginiArticolo)
                .codiceSottocategoria(sottocategoria.getCodiceSottocategoria())
                .codiceMagazzino(magazzino.getCodiceArticoloMagazzino())
                .giacenzaMagazzino(magazzino.getGiacenzaArticolo())
                .build();
        return ResponseEntity.ok(articoloDTO);
    }
    
    @GetMapping("/public/articolobymarca/{marcaArticolo}")
    public ResponseEntity<List<ArticoloDTO>> findArticoloByMarca(@PathVariable String marcaArticolo){
        List<Articolo> articoli= articoliRepository.findByMarcaArticolo(marcaArticolo).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(articoli);
        List<ImmaginiArticolo> immaginiRepositoryAll = immaginiArticoloRepository.findAll();
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepositoryAll);
        List<ArticoloDTO> response= new ArrayList<>();
        for (Articolo a : articoli){
            Sottocategoria sottocategoria = sottocategorieRepository.findByArticolo_NomeArticolo(a.getNomeArticolo()).orElse(null);
            Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategoria);
            Magazzino magazzino = magazzinoRepository.findByArticolo_CodiceArticolo(a.getCodiceArticolo()).orElse(null);
            Utility.checkIfMagazzinoResponseEntityNotFund(magazzino);
            List<String> immaginiArticolo= new ArrayList<>();
            for (ImmaginiArticolo i : immaginiRepositoryAll){
                if (i.getArticolo().getCodiceArticolo()==a.getCodiceArticolo()){
                    immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
                }
            }
            ArticoloDTO articoloDTO = ArticoloDTO.builder()
                    .codiceArticolo(a.getCodiceArticolo())
                    .nomeArticolo(a.getNomeArticolo())
                    .prezzo(a.getPrezzo())
                    .marcaArticolo(a.getMarcaArticolo())
                    .immaginiArticolo(immaginiArticolo)
                    .codiceSottocategoria(sottocategoria.getCodiceSottocategoria())
                    .codiceMagazzino(magazzino.getCodiceArticoloMagazzino())
                    .giacenzaMagazzino(magazzino.getGiacenzaArticolo())
                    .build();
            response.add(articoloDTO);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/articolobyrangeprezzo/{prezzoMin}/{prezzoMax}")
    public ResponseEntity<List<ArticoloDTO>> findArticoloByRangeDiPrezzo(@PathVariable double prezzoMin,
                                                                      @PathVariable double prezzoMax){
        List<Articolo> articoli= articoliRepository.findByPrezzoBetween(prezzoMin,prezzoMax).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(articoli);
        List<ImmaginiArticolo> immaginiRepositoryAll = immaginiArticoloRepository.findAll();
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepositoryAll);
        List<ArticoloDTO> response= new ArrayList<>();
        for (Articolo a : articoli){
            Sottocategoria sottocategoria = sottocategorieRepository.findByArticolo_NomeArticolo(a.getNomeArticolo()).orElse(null);
            Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategoria);
            Magazzino magazzino = magazzinoRepository.findByArticolo_CodiceArticolo(a.getCodiceArticolo()).orElse(null);
            Utility.checkIfMagazzinoResponseEntityNotFund(magazzino);
            List<String> immaginiArticolo= new ArrayList<>();
            for (ImmaginiArticolo i : immaginiRepositoryAll){
                if (i.getArticolo().getCodiceArticolo()==a.getCodiceArticolo()){
                    immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
                }
            }
            ArticoloDTO articoloDTO = ArticoloDTO.builder()
                    .codiceArticolo(a.getCodiceArticolo())
                    .nomeArticolo(a.getNomeArticolo())
                    .prezzo(a.getPrezzo())
                    .marcaArticolo(a.getMarcaArticolo())
                    .immaginiArticolo(immaginiArticolo)
                    .codiceSottocategoria(sottocategoria.getCodiceSottocategoria())
                    .codiceMagazzino(magazzino.getCodiceArticoloMagazzino())
                    .giacenzaMagazzino(magazzino.getGiacenzaArticolo())
                    .build();
            response.add(articoloDTO);
        }
        return ResponseEntity.ok(response);
   }

   @GetMapping("/public/articolobysottocategoria/{nomeSottocategoria}")
   public ResponseEntity<List<ArticoloDTO>> findArticoloBySottocategoria(@PathVariable String nomeSottocategoria){
        List<Articolo> articoli= articoliRepository.findAllBySottocategoria_NomeSottocategoria(nomeSottocategoria).orElse(null);
        Utility.checkIfArticoloResponseEntityNotFund(articoli);
        List<ImmaginiArticolo> immaginiRepositoryAll = immaginiArticoloRepository.findAll();
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepositoryAll);
        List<ArticoloDTO> response= new ArrayList<>();
        for (Articolo a : articoli){
            Sottocategoria sottocategoria = sottocategorieRepository.findByArticolo_NomeArticolo(a.getNomeArticolo()).orElse(null);
            Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategoria);
            Magazzino magazzino = magazzinoRepository.findByArticolo_CodiceArticolo(a.getCodiceArticolo()).orElse(null);
            Utility.checkIfMagazzinoResponseEntityNotFund(magazzino);
            List<String> immaginiArticolo= new ArrayList<>();
            for (ImmaginiArticolo i : immaginiRepositoryAll){
                if (i.getArticolo().getCodiceArticolo()==a.getCodiceArticolo()){
                    immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
                }
            }
            ArticoloDTO articoloDTO = ArticoloDTO.builder()
                    .codiceArticolo(a.getCodiceArticolo())
                    .nomeArticolo(a.getNomeArticolo())
                    .prezzo(a.getPrezzo())
                    .marcaArticolo(a.getMarcaArticolo())
                    .immaginiArticolo(immaginiArticolo)
                    .codiceSottocategoria(sottocategoria.getCodiceSottocategoria())
                    .codiceMagazzino(magazzino.getCodiceArticoloMagazzino())
                    .giacenzaMagazzino(magazzino.getGiacenzaArticolo())
                    .build();
            response.add(articoloDTO);
        }
        return ResponseEntity.ok(response);
   }

   @GetMapping("/public/articolobycategoria/{nomeCategoria}")
   public ResponseEntity<List<ArticoloDTO>> findArticoloByCategoria(@PathVariable String nomeCategoria){
        List<Sottocategoria> sottocategorie= sottocategorieRepository.findAllByCategoria_NomeCategoria(nomeCategoria).orElse(null);
        Utility.checkIfSottocategoriaResponseEntityNotFund(sottocategorie);
        List<ImmaginiArticolo> immaginiRepositoryAll = immaginiArticoloRepository.findAll();
        Utility.checkIfImmaginiArticoloResponseEntityNotFund(immaginiRepositoryAll);
        List<Articolo> tuttiArticoli= new ArrayList<>();
        if(sottocategorie.size()>=1){
            for(Sottocategoria s:sottocategorie){
                for(Articolo a:articoliRepository.findAllBySottocategoria_NomeSottocategoria(s.getNomeSottocategoria()).get()){
                    tuttiArticoli.add(a);
                }
            }
        }
        List<ArticoloDTO> response= new ArrayList<>();
        for (Articolo a : tuttiArticoli){
           List<String> immaginiArticolo= new ArrayList<>();
           for (ImmaginiArticolo i : immaginiRepositoryAll){
               if (i.getArticolo().getCodiceArticolo()==a.getCodiceArticolo()){
                   immaginiArticolo.add(Base64.getEncoder().encodeToString(i.getImmagine()));
               }
           }
           ArticoloDTO articoloDTO = ArticoloDTO.builder()
                   .codiceArticolo(a.getCodiceArticolo())
                   .nomeArticolo(a.getNomeArticolo())
                   .prezzo(a.getPrezzo())
                   .marcaArticolo(a.getMarcaArticolo())
                   .immaginiArticolo(immaginiArticolo)
                   .codiceSottocategoria(a.getSottocategoria().getCodiceSottocategoria())
                   .codiceMagazzino(a.getArticoliMagazzino().getCodiceArticoloMagazzino())
                   .giacenzaMagazzino(a.getArticoliMagazzino().getGiacenzaArticolo())
                   .build();
           response.add(articoloDTO);
       }
        return ResponseEntity.ok(response);
   }

    @PostMapping("/admin/addarticolo/{codiceSottocategoria}/{giacenzaArticolo}")
    public ResponseEntity<Articolo> createNewArticolo(@RequestPart String nuovoArticoloJSON,
                                                      @RequestPart ArrayList<MultipartFile> immaginiArticolo,
                                                      @PathVariable int codiceSottocategoria,
                                                      @PathVariable int giacenzaArticolo) throws IOException {

        ArticoloDTO nuovoArticolo = new ObjectMapper().readValue(nuovoArticoloJSON, ArticoloDTO.class);
        Articolo checkConflict = articoliRepository.findByNomeArticolo(nuovoArticolo.getNomeArticolo()).orElse(null);
        if(checkConflict!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Sottocategoria toAssign=sottocategorieRepository.findById(codiceSottocategoria).orElse(null);
        Utility.checkIfSottocategoriaResponseEntityNotFund(toAssign);
        Articolo creato= Articolo.builder()
                .codiceArticolo(nuovoArticolo.getCodiceArticolo())
                .nomeArticolo(nuovoArticolo.getNomeArticolo())
                .prezzo(nuovoArticolo.getPrezzo())
                .marcaArticolo(nuovoArticolo.getMarcaArticolo())
                .sottocategoria(toAssign)
                .build();
        Articolo nuovo = articoliRepository.save(creato);
        List<ImmaginiArticolo> immagini= new ArrayList<>();
        int counter = 1;
        for (MultipartFile e : immaginiArticolo){
            ImmaginiArticolo nuovaImmagine = ImmaginiArticolo.builder()
                    .articolo(nuovo)
                    .nomeImmagine(nuovo.getNomeArticolo()+"_"+counter)
                    .immagine(e.getBytes())
                    .build();
            immagini.add(nuovaImmagine);
            immaginiArticoloRepository.save(nuovaImmagine);
            counter++;
        }
        Magazzino newMagazzino = Magazzino.builder()
                .articolo(nuovo)
                .giacenzaArticolo(giacenzaArticolo)
                .build();
        magazzinoRepository.save(newMagazzino);
        nuovo.setArticoliMagazzino(newMagazzino);
        return ResponseEntity.ok(articoliRepository.save(nuovo));
    }

    @PutMapping("/admin/updatearticolo/{codiceArticolo}")
    public ResponseEntity<Articolo> updateArticolo(@RequestPart String updateArticoloJSON,
                                                   @PathVariable int codiceArticolo) throws IOException {
        ArticoloDTO updateArticolo = new ObjectMapper().readValue(updateArticoloJSON, ArticoloDTO.class);
        Articolo checkIfExists = articoliRepository.findById(codiceArticolo).orElse(null);
        if(checkIfExists==null || checkIfExists.getCodiceArticolo()!=codiceArticolo){
            return ResponseEntity.notFound().build();
        }
        Sottocategoria toAssign=sottocategorieRepository.findById(updateArticolo.getCodiceSottocategoria()).orElse(null);
        Utility.checkIfSottocategoriaResponseEntityNotFund(toAssign);

        Magazzino toRecord= magazzinoRepository.findByArticolo_CodiceArticolo(codiceArticolo).orElse(null);
        Utility.checkIfMagazzinoResponseEntityNotFund(toRecord);
        toRecord.setGiacenzaArticolo(updateArticolo.getGiacenzaMagazzino());

        checkIfExists.setCodiceArticolo(codiceArticolo);
        checkIfExists.setNomeArticolo(updateArticolo.getNomeArticolo());
        checkIfExists.setPrezzo(updateArticolo.getPrezzo());
        checkIfExists.setMarcaArticolo(updateArticolo.getMarcaArticolo());
        checkIfExists.setSottocategoria(toAssign);
        checkIfExists.setArticoliMagazzino(toRecord);
        return ResponseEntity.ok(articoliRepository.save(checkIfExists));
    }

    @Transactional
    @DeleteMapping("/admin/articolo/{codiceArticolo}")
    public ResponseEntity<Void> deleteArticolo(@PathVariable int codiceArticolo){
        Articolo checkIfExists= articoliRepository.findById(codiceArticolo).orElse(null);
        if(checkIfExists==null){
            return ResponseEntity.notFound().build();
        }
        immaginiArticoloRepository.deleteAllByArticolo_CodiceArticolo(codiceArticolo);
        articoliRepository.deleteById(codiceArticolo);
        return ResponseEntity.accepted().build();
    }

}
