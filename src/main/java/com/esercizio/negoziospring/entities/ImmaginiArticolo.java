package com.esercizio.negoziospring.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "immagini_articolo")
public class ImmaginiArticolo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codiceImmagine;

    @Column (name = "nome_immagine", unique = true)
    private String nomeImmagine;

    @Lob
    @Column (name = "immagine", length = 999999999)
    private byte[] immagine;

    @ManyToOne
    private Articolo articolo;

}
