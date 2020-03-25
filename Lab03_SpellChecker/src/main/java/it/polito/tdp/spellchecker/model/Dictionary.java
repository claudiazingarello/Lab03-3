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
			if(dizionario.contains(input.toLowerCase()))
				rw.setCorrect(true);
			else
				rw.setCorrect(false);
			
			listaParole.add(rw);
		}
		
		return listaParole;
	}
}
