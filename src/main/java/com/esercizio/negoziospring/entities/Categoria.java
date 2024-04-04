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
@Table(name = "categoria")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codiceCategoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codiceCategoria;

    @Column(name = "nome_categoria", unique = true)
    private String nomeCategoria;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sottocategoria> sottocategoria;
}
