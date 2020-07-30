package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.LexerException;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Parser teksta upita. Upit se sastoji od jednog ili više uvjetnih izraza
 * {@link ConditionalExpression}. Uvjetni izrazi se kombiniraju binarnim
 * operatorom I (AND; &). Postoje dvije vrste upita: direktni upiti koji se
 * sastoje od samo jedne usporedbe jednakosti (po jmbagu), i ostale koje nisu
 * direktne.
 * 
 * @author nikola
 *
 */
public class QueryParser {

	/**
	 * Zastavica je li upit direktan ili ne.
	 */
	private boolean directQuery;
	/**
	 * Lista uvjetnih izraza.
	 */
	private List<ConditionalExpression> query;

	/**
	 * Defaultni konstruktor.
	 * 
	 * @param queryString
	 *            tekst upita
	 */
	public QueryParser(String queryString) {
		if (queryString == null) {
			throw new NullPointerException();
		}

		Lexer lexer = new Lexer(queryString);
		try {
			parse(lexer);
		} catch (LexerException e) {
			throw new ParserException(e.getMessage());
		}
	}

	/**
	 * Parsira upit.
	 * 
	 * @param lexer
	 */
	private void parse(Lexer lexer) {

		query = new ArrayList<>();
		boolean firstQuery = true;
		while (true) {

			Token token = lexer.nextToken();
			if (token.getType() == TokenType.EOF) {
				if (firstQuery) {
					throw new ParserException("Ulaz ne valja!");
				} else {
					break;
				}
			}

			if (!firstQuery) {
				if (token.getType() != TokenType.AND) {
					throw new ParserException("Ulaz ne valja!");
				}
				token = lexer.nextToken();
			}

			if (token.getType() != TokenType.ATTRIBUTE_NAME) {
				throw new ParserException("Ulaz ne valja!");
			}
			String attributeName = (String) token.getValue();

			IFieldValueGetter fieldGetter = null;
			switch (attributeName) {
			case "firstName":
				fieldGetter = FieldValueGetters.FIRST_NAME;
				break;
			case "lastName":
				fieldGetter = FieldValueGetters.LAST_NAME;
				break;
			case "jmbag":
				fieldGetter = FieldValueGetters.JMBAG;
				break;
			default:
				break;
			}

			token = lexer.nextToken();
			if (token.getType() != TokenType.OPERATOR) {
				throw new ParserException("Ulaz ne valja!");
			}
			String operator = (String) token.getValue();

			IComparisonOperator comparisonOperator = null;
			switch (operator) {
			case ">":
				comparisonOperator = ComparisonOperators.GREATER;
				break;
			case ">=":
				comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "<":
				comparisonOperator = ComparisonOperators.LESS;
				break;
			case "<=":
				comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case "=":
				comparisonOperator = ComparisonOperators.EQUALS;
				break;
			case "!=":
				comparisonOperator = ComparisonOperators.NOT_EQUALS;
				break;
			case "LIKE":
				comparisonOperator = ComparisonOperators.LIKE;
				break;
			default:
				break;
			}

			if (firstQuery) {
				firstQuery = false;
				if (comparisonOperator == ComparisonOperators.EQUALS)
					directQuery = true;
			} else {
				directQuery = false;
			}

			token = lexer.nextToken();
			if (token.getType() != TokenType.STRING_LITERAL) {
				throw new ParserException("Ulaz ne valja!");
			}
			String stringLiteral = (String) token.getValue();

			if (comparisonOperator == ComparisonOperators.LIKE) {
				int wildcardCount = 0;
				for(int i = 0; i < stringLiteral.length(); i++) {
					if(stringLiteral.charAt(i) == '*') {
						wildcardCount++;
						if(wildcardCount > 1) {
							throw new ParserException("Previše zamjenskog znaka '*'.");
						}
					}
				}
			}

			query.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
		}

	}

	/**
	 * Vraća je li upit izravan. Upit je izravan ako se sastoji od jedne usporedbe
	 * jednakosti (po jmbagu).
	 * 
	 * @return vraća <code>true</code> ako je upit izravan, inače <code>false</code>
	 */
	public boolean isDirectQuery() {
		return directQuery;
	}

	/**
	 * Vraća jmbag koji je dan u usporedbi jednakosti u izravnom upitu.
	 * 
	 * @return jmbag studenta
	 * @throws IllegalStateException
	 *             ako upit nije izravan
	 */
	public String getQueriedJMBAG() {
		if (!directQuery) {
			throw new IllegalStateException();
		}

		return query.get(0).getStringLiteral();
	}

	/**
	 * Vraća upit u obliku liste uvjetnih izraza.
	 * 
	 * @return
	 */
	public List<ConditionalExpression> getQuery() {
		return query;
	}

}
