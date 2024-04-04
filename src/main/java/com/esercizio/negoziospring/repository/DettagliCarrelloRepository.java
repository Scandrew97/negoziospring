package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Articolo;
import com.esercizio.negoziospring.entities.DettagliCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DettagliCarrelloRepository extends JpaRepository<DettagliCarrello,Integer> {

    public Optional<DettagliCarrello> findByArticolo(Articolo articolo);
//
//    public Optional<List<DettagliCarrello>> findByCarrello_Utente_CodiceUtente(int codiceUtente);
}
