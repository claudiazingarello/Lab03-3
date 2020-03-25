package it.polito.tdp.spellchecker.model;


public class RichWord {
	
	private String parolaInput;
	private boolean isCorrect;
	
	
	
	public RichWord(String parolaInput) {
		this.parolaInput = parolaInput;
	}

	public RichWord(String parolaInput, boolean isCorrect) {
		this.parolaInput = parolaInput;
		this.isCorrect = isCorrect;
	}

	public String getParolaInput() {
		return parolaInput;
	}

	public void setParolaInput(String parolaInput) {
		this.parolaInput = parolaInput;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	
	
}
