package hr.fer.zemris.java.custom.scripting.lexer;

public enum TokenType {
	/** Označava da više nema tokena **/
	EOF,
	/** Niz znakova **/
	STRING, 
	/** Broj **/
	NUMBER, 
	/** Identifikator **/
	IDENT, 
	/** Otvoreni tag **/
	OPEN_TAG, 
	/** Zatvoreni tag **/
	CLOSED_TAG, 
	/** Operator **/
	OPERATOR,
	/** Funkcija **/
	FUNCTION
}
