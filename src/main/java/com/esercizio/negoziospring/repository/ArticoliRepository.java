package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticoliRepository extends JpaRepository<Articolo,Integer> {

    public Optional<Articolo> findByNomeArticolo(String nomeArticolo);

    public Optional<List<Articolo>> findAllByNomeArticoloContainingIgnoreCase(String string);


    public Optional<List<Articolo>> findByMarcaArticolo(String marcaArticolo);

    public Optional<List<Articolo>> findByPrezzoBetween(double prezzoMin, double prezzoMax);

    public Optional<List<Articolo>> findAllBySottocategoria_NomeSottocategoria(String sottocategoria);

    public Optional<List<Articolo>> findAllBySottocategoria_CodiceSottocategoria(int codiceSottocategoria);

    public Void deleteAllBySottocategoria_CodiceSottocategoria(int codiceSottocategoria);


}
