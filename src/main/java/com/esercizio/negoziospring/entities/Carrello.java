package com.esercizio.negoziospring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "carrello")
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codiceCarrello;

    @OneToOne
    @JoinColumn(name = "codice_utente")
    private Utente utente;

    @OneToMany
    private List<DettagliCarrello> dettagliCarrello;

    private double totaleCarrello;



}
