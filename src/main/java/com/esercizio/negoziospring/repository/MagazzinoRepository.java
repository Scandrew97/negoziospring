package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Articolo;
import com.esercizio.negoziospring.entities.Magazzino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagazzinoRepository extends JpaRepository<Magazzino,Integer> {

    public Optional<Magazzino> findByArticolo_CodiceArticolo(int codiceArticolo);
}
