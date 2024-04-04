package com.esercizio.negoziospring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dettagli_carrello")
public class DettagliCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Articolo articolo;

    private int articoloQuantita;

    private double prezzoTotale;

    @Column(name = "couponIsUsed", nullable = false)
    private boolean couponIsUsed;

}
