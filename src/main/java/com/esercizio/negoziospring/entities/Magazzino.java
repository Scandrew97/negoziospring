package com.esercizio.negoziospring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "magazzino")
public class Magazzino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codiceArticoloMagazzino;

    @OneToOne(mappedBy = "articoliMagazzino",cascade = CascadeType.ALL, orphanRemoval = true)
    private Articolo articolo;

    @Column(name = "giacenzaArticolo")
    private int giacenzaArticolo;

    @Column(name = "quantitaArticolo")
    private int quantitaArticolo;
}
