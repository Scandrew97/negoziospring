package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Categoria;
import com.esercizio.negoziospring.entities.Sottocategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SottocategorieRepository extends JpaRepository<Sottocategoria,Integer> {

    public Optional<Sottocategoria> findByNomeSottocategoria(String nomeSottocategoria);

    public Optional<List<Sottocategoria>> findAllByCategoria_NomeCategoria(String categoria);

    public Optional<List<Sottocategoria>> findAllByCategoria_CodiceCategoria(int codiceCategoria);
    public Optional<Sottocategoria> findByArticolo_NomeArticolo(String nomeArticolo);

    public Optional<Sottocategoria> findByArticolo_CodiceArticolo(int codiceArticolo);
}
