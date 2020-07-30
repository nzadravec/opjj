package hr.fer.zemris.java.hw05.db.lexer;

import hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * Enumeracija za {@link Lexer} i {@link QueryParser}.
 * 
 * @author nikola
 *
 */
public enum TokenType {
	EOF,
	ATTRIBUTE_NAME,
	OPERATOR,
	STRING_LITERAL,
	AND
}
