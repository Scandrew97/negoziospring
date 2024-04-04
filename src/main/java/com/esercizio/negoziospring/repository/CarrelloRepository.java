package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Carrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {

    public Optional<Carrello> findByUtente_CodiceUtente(int codiceUtente);

    public Optional<Carrello> findByUtente_CodiceUtenteAndDettagliCarrello_Articolo_CodiceArticolo(int codiceUtente, int codiceArticolo);

}
