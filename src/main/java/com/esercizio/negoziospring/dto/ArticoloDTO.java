package com.esercizio.negoziospring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticoloDTO {

    private int codiceArticolo;
    private String nomeArticolo;
    private double prezzo;
    private String marcaArticolo;
    private List<MultipartFile> immaginiArticoloMultipartFile;
    private List<String> immaginiArticolo;
    private int codiceSottocategoria;
    private int codiceMagazzino;
    private int giacenzaMagazzino;

}
