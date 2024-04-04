package com.esercizio.negoziospring.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "coupon")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codiceIdentificativo")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codiceIdentificativo;

    @Column(name = "codiceSconto")
    private String codiceSconto;

    @Column(name = "sconto")
    private int sconto;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "codiceSottocategoria")
    private Sottocategoria sottocategoria;
}
