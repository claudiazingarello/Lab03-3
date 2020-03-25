package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario;
	private String language;
	

	public Dictionary() {
		
	}
	

	/**
	 * Carica in memoria il dizionario della lingua desiderata
	 * @param language
	 */
	public void loadDictionary(String language) {
		if(this.language.equals(language) && dizionario != null ) {
			return;
		}
		
		dizionario = new ArrayList<String>();
		this.language = language; //Impostiamo la lingua uguale a quella passata da parametro
		
		try {
			FileReader fr = new FileReader("English.txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
			// Aggiungere parola alla struttura dati
				dizionario.add(word.toLowerCase());
				
			}
			
			Collections.sort(dizionario);
			br.close();
			} catch (IOException e){
			System.out.println("Errore nella lettura del file");
			}
	}
	
	
	/**
	 * Controllo ortografico sul testo in input 
	 * @param inputTextList  (una lista di parole)
	 * @return lista di RichWord
	 */
	public List<RichWord> spellCheckText(List<String> inputTextList){
		
		List<RichWord> listaParole = new ArrayList<RichWord>();
		
		/*for(String input : inputTextList) {
			if(dizionario.contains(input.toLowerCase()))
				listaParole.add(new RichWord(input, true));
			else
				listaParole.add(new RichWord(input, false));
		}
		*/
		
		for(String input : inputTextList) {
			RichWord rw = new RichWord(input);
			
			//Se il dizionario contiene la parola passata in input 
			//setto la rich word come corretta
			if(dizionario.contains(input.toLowerCase()))
				rw.setCorrect(true);
			else
				rw.setCorrect(false);
			
			listaParole.add(rw);
		}
		
		return listaParole;
	}
	
	//************2 ESERCIZIO***************
	/**
	 * Iterare su tutti gli elementi del vocabolario a partire dal primo.
	 * La ricerca termina quando viene trovato l’elemento cercato o si raggiunge l’ultimo,
	 * nel caso in cui l’elemento cercato non sia presente nella lista.
	 * 
	 * @param inputTextList
	 * @return
	 */
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		
		List<RichWord> richWordList = new ArrayList<RichWord>();
		
		//Scandisco la lista delle parole in INPUT
		for(String input : inputTextList) {
			RichWord rw = new RichWord(input);
			boolean found = false;
			
			//Scandisco il dizionario della lingua per vedere se la parola è presente
			for(String word : dizionario){
				
				//equalsIgnoreCase --> Se le due parole sono uguali (non considera le differenze
				//dovute a lower e upper case)
				if(word.equalsIgnoreCase(input)) {
					found = true;
					break;
				}
			}
			
			//In base allo stato del found, settiamo isCorrect
			if(found == true) {
				rw.setCorrect(true);
			}
			else {
				rw.setCorrect(false);
			}
			
			//Aggiungo la richWord alla lista RichWordList
			richWordList.add(rw);
		}
		return richWordList;
	}
		
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		
		List<RichWord> richWordList = new ArrayList<RichWord>();
		
		for(String input : inputTextList) {
			RichWord rw = new RichWord(input);
			
			if(binarySearch(input.toLowerCase())) {
				//se la ricerca ha prodotto un risultato, setto a true
				rw.setCorrect(true);
			}
			else
				rw.setCorrect(false);
			
			richWordList.add(rw);
		}
		
		return richWordList;
	}


	private boolean binarySearch(String stemp) {
		
		int inizio = 0;
		int fine = dizionario.size();
		
		while (inizio != fine ) {
			//trovo il valore medio del dizionario
			int medio = inizio + (fine - inizio)/2 ;
			
			//Vedo se la stringa passata da input è uguale al valore medio del dizionario
			if(stemp.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			}
			else if (stemp.compareToIgnoreCase(dizionario.get(medio)) < 0) { // QUANDO E' < 0 VUOL DIRE CHE LA STRINGA E' SUPERIORE
				//ripeto la ricerca sulla prima metà del dizionario, scartando quelli successivi
				fine = medio; 
				
			}
			else { // è inferiore
				//ripeto la ricerca sulla SECONDA metà del dizionario, scarto quelli che vengono prima
				inizio = medio + 1;
			}
		}
		
		//se alla fine del while non trovo nulla, allora restituisco false
		return false;
	}
	
	}

