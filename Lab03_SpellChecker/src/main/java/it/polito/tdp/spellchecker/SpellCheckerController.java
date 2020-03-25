package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class SpellCheckerController {
	
	private Dictionary dizionario;
	private List<String> inputTextList;

	//Flag to select dichotomic search
	private final static boolean dichotomicSearch = false;
	private final static boolean linearSearch = false;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> boxLanguage;

    @FXML
    private TextArea txtWrite;

    @FXML
    private Button btnSpell;

    @FXML
    private TextArea txtDaCorreggere;

    @FXML
    private Label lblError;

    @FXML
    private Button btnClear;

    @FXML
    private Label txtTime;

    /**
     * Cliccando su combo box 
     * attivo una serie di cose
     * @param event
     */
    @FXML
    void doActivation(MouseEvent event) {
    	
    	if(boxLanguage.getValue() != null) {
        	txtWrite.setDisable(false);
        	txtDaCorreggere.setDisable(false);
        	btnClear.setDisable(false);
        	btnSpell.setDisable(false);
        	txtDaCorreggere.clear();
        	txtWrite.clear();
    	}
    	else {
    		txtWrite.setText("Devi selezionare una lingua!");
    		txtWrite.setDisable(true);
        	txtDaCorreggere.setDisable(true);
        	btnClear.setDisable(true);
        	btnSpell.setDisable(true);
    	}
    	
    	
    }

    @FXML
    void doClearText(ActionEvent event) {
    	txtDaCorreggere.clear();
    	txtWrite.clear();
    	lblError.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	txtWrite.clear();
    	
    	//Creo una lista in cui inserire le singole parole che costituiscono il
    	//testo in ingresso (inputText)
    	inputTextList = new LinkedList<String>();
    	
    	//Controllo che l'utente abbia effettuato una scelta nella choise box
    	if(boxLanguage.getValue() == null) {
    		txtWrite.setText("Devi selezionare una lingua");
    		return;
    	}
    	
    	//Carico il dizionario relativo alla lingua selezionata
    	//e controllo che esista il dizionario relativo a quella lingua
    	dizionario.loadDictionary(boxLanguage.getValue());
    	if(dizionario == null) {
    		txtWrite.setText("Non è stato possibile caricare il dizionario associato alla lingua scelta");
    		return;
    	}
    	
    	
    	String inputText = txtWrite.getText();
    	//Controllo sul testo di input
    	if(inputText.isEmpty()) {
    		System.out.println("Devi inserire un testo da correggere!");
    		return;
    	}
  
    	inputText.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	inputText.replaceAll("/n"," "); //scambio gli invii a capo con uno spazio tra una parola e l'altra
  
    	
    	//Usiamo ST per scandire il testo in parole differenti
    	StringTokenizer st = new StringTokenizer(inputText, " ");
		
    	while (st.hasMoreTokens()) {
    		//scandisco il testo fino a quando abbiamo next token
    		//e li aggiungiamo alla lista di inputTextList
			inputTextList.add(st.nextToken());
		}
		
    	//Conto quanto tempo impiega a eseguire lo spell
    	long start = System.nanoTime();
    	
    	
    	//Creo una lista di RichWord che rappresenta il nostro output (parole errate)
    	List<RichWord> outputTextList ; 	
    	
    //*********ESERCIZIO 2***************
    	if(linearSearch) {
    		dizionario.spellCheckTextLinear(inputTextList);
    	}
    	else if (dichotomicSearch) {
    		dizionario.spellCheckTextDichotomic(inputTextList);
    	}
    	else {
    		outputTextList = dizionario.spellCheckText(inputTextList); //Eseguo lo spell che ci da in output le parole che non sono presenti nel dizionario
    	}
    	
    	long end = System.nanoTime();
    	
    	//Conto NUMERO DI ERRORI e riporto quali sono le parole sbagliate
    	int numErrori = 0;
    	StringBuilder sb = new StringBuilder();
    	
    	for(RichWord rw : outputTextList) {
    		if(!rw.isCorrect()) {
    			numErrori++;
    			sb.append(rw.getParolaInput()+"\n");
    		}
    	}
    	
    	//Trasformo tutto in una stringa da inserire nel campo
    	/*METODO FATTO DA ME, NON SO SE SIA CORRETTO
    	 * for(RichWord rw : outputTextList) {
    		if(!rw.isCorrect()) {
    			numErrori++;
    			txtDaCorreggere.appendText(rw.getParolaInput() + "/n");
    		}
    	}
    	*/
    	
    	txtDaCorreggere.setText(sb.toString());
    	long tempo = end - start;
    	txtTime.setText("Spell completato in "+tempo/ 1E9 +" secondi");
    	lblError.setText("Il testo contiene "+ numErrori+ " errori");
    	
    }
    

	void setModel(Dictionary model) {
		
		//Disabilito le aree di testo
		txtWrite.setDisable(true);
		txtWrite.setText("Selezionare una lingua");
		
		txtDaCorreggere.setDisable(true);
		
		//Disabilito i bottoni (devo prima scegliere una lingua
		btnClear.setDisable(true);
		btnSpell.setDisable(true);
		
		//Pulisco le label
		lblError.setText("");
		txtTime.setText(""); //e' una label
		
		//Popolo la combo box
		boxLanguage.getItems().addAll("English","Italian");
		
		
		this.dizionario = model;
		
	}

    @FXML
    void initialize() {
        assert boxLanguage != null : "fx:id=\"boxLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtWrite != null : "fx:id=\"txtWrite\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
