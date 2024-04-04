package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Integer> {

    public Optional<Utente> findByUsername(String username);

    public Optional<Utente> findByUsernameAndPassword(String username, String password);

    public Optional<Utente> deleteByUsername(String username);

    boolean existsByUsername(String username);

    public Optional<Utente> findByEmail(String email);

}
