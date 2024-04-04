package com.esercizio.negoziospring.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "sottocategoria")
public class Sottocategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codiceSottocategoria;

    @Column(name = "nome_sottocategoria", unique = true)
    private String nomeSottocategoria;

    @OneToMany(mappedBy = "sottocategoria",cascade = CascadeType.ALL)
    private List<Articolo> articolo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "codiceCategoria")
    private Categoria categoria;
}
