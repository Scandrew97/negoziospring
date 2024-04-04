package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categoria,Integer> {

    public Optional<Categoria> findByNomeCategoria(String nomeCategoria);

    public Optional<Categoria> findBySottocategoria_CodiceSottocategoria(int codiceSottocategoria);
}
