package com.esercizio.negoziospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan({ "src/main/java/com/esercizio/negoziospring*" })
@SpringBootApplication
public class NegoziospringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NegoziospringApplication.class, args);

//		Creare un'applicazione in Spring la cui funzione è quella di gestire
//		un catalogo di un negozio online,
//		con metodologia CRUD (Inserimento dati, modifica, eliminazione...).
//		L'applicazione deve avere anche le seguenti funzionalità:
//		Filtro di ricerca per:
//		nome,
//		prezzo (prodotti che hanno un prezzo compreso tra un minimo e un massimo,
//		facenti parte o meno di una determina categoria o sottocategoria),
//		categoria,
//		sottocategoria.
//		Se cerco i prodotti per categoria, mi deve restituire tutti i prodotti che
//		fanno parte delle sottocategorie correlate.
//		Possibilità di applicare ad una determinata sottocategoria uno sconto promozionale sul prezzo
//		(ad esempio il 10% di sconto)
//		Gestire anche la giacenza in magazzino.
//
//		Implementare un carrello. (Esempio: un prodotto che non è in giacenza non può essere inserito
//		nel carrello)
//		Implementare un servizio che mi restituisce gli articoli presenti nel carrello e il totale
//		del prezzo.
//		Dal carrello devo avere la possibilità di rimuovere i prodotti o applicare un coupon.
//		(Attenzione, il coupon deve essere esistente e non un dato a caso, pensare a come gestirli)
	}

}
