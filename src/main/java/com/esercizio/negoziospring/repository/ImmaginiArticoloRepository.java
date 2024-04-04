package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.ImmaginiArticolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImmaginiArticoloRepository extends JpaRepository<ImmaginiArticolo,Integer> {

    public ImmaginiArticolo findByNomeImmagine(String nomeImmagine);

    public Optional<ImmaginiArticolo> findByNomeImmagineAndArticolo_CodiceArticolo(String nomeImmagine, int codiceArticolo);

    public List<ImmaginiArticolo> findAllByArticolo_CodiceArticolo(int codiceArticolo);

    public List<ImmaginiArticolo> findAllByArticolo_NomeArticolo(String nomeArticolo);

    public void deleteAllByArticolo_CodiceArticolo(int codiceArticolo);
}
