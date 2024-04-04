package com.esercizio.negoziospring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "articolo")
public class Articolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codiceArticolo;

    @Column(name = "nome_articolo", unique = true, nullable = false)
    private String nomeArticolo;

    @Column(name = "prezzo", nullable = false)
    private double prezzo;

    @Column(name = "marca_articolo", nullable = false)
    private String marcaArticolo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "codiceSottocategoria")
    private Sottocategoria sottocategoria;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "codiceArticoloMagazzino")
    private Magazzino articoliMagazzino;

}
