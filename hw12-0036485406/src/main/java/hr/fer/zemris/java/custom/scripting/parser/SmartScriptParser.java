package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer3;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parser teksta dokumenta.
 * 
 * @author nikola
 *
 */
public class SmartScriptParser {

	/**
	 * Stablo koje predstavlja parsirani dokument.
	 */
	private DocumentNode documentNode;

	/**
	 * Stvara parser koji parsira dani tekst dokumenta.
	 * Parser analiza tekst dokumenta pomoću lexer-a {@link SmartScriptLexer3}.
	 * 
	 * @param text
	 *            tekst dokumenta
	 * @throws NullPointerException
	 *             ako je tekst dokumenta <code>null</code>
	 * @throws SmartScriptParserException
	 *             u slučaju pogreške pri parsiranju
	 */
	public SmartScriptParser(String text) {
		if (text == null) {
			throw new NullPointerException("Tekst dokumenta ne smije biti null.");
		}

		SmartScriptLexer3 lexer = new SmartScriptLexer3(text);
		try {
			parse(lexer);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new SmartScriptParserException("Ulaz ne valja!");
		}
	}

	/**
	 * Dohvat stabla nastalog parsiranjem dokumenta.
	 * 
	 * @return stablo dokumenta
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Pomoćna metoda koja predstavlja implementaciju parsera. Ova metoda
	 * predstavlja vršnu metodu tako implementiranog parsera.
	 *
	 */
	private void parse(SmartScriptLexer3 lexer) {
		ObjectStack stack = new ObjectStack();

		documentNode = new DocumentNode();
		stack.push(documentNode);
		Node lastPushed = documentNode;
		
		while (true) {
			
			Token token = lexer.nextToken();
			if (token.getType() == TokenType.EOF) {
				if (lexer.getState() != LexerState.TEXT || stack.size() != 1) {
					throw new SmartScriptParserException();
				}

				break;
			} else if (token.getType() == TokenType.OPEN_TAG) {
				lexer.setState(LexerState.TAG);
				token = lexer.nextToken();
			}

			if (lexer.getState() == LexerState.TAG) {
				String tagName = (String) token.getValue();
				tagName = tagName.toUpperCase();
				if (tagName.equals("FOR")) {
					ForLoopNode forLoopNode = parseForLoopNode(lexer);
					lastPushed.addChildNode(forLoopNode);
					stack.push(forLoopNode);
					lastPushed = forLoopNode;
				} else if (tagName.equals("=")) {
					EchoNode echoNode = parseEchoNode(lexer);
					lastPushed.addChildNode(echoNode);
				} else if (tagName.equals("END")) {
					if(stack.size() == 1) {
						throw new SmartScriptParserException();
					}
					stack.pop();
					lastPushed = (Node) stack.peek();
				} else {
					throw new SmartScriptParserException();
				}
				
				if(lexer.getState() != LexerState.TEXT) {
					token = lexer.nextToken();
					if (token.getType() != TokenType.CLOSED_TAG) {
						throw new SmartScriptParserException();
					}
					lexer.setState(LexerState.TEXT);
					
				}

			} else {
				String text = (String) token.getValue();
				TextNode textNode = new TextNode(text);
				lastPushed.addChildNode(textNode);
			}
		}

	}

	/**
	 * Pomoćna metoda koja predstavlja implementaciju parsera čvora
	 * {@link ForLoopNode}.
	 *
	 * @return čvor {@link ForLoopNode}
	 */
	private ForLoopNode parseForLoopNode(SmartScriptLexer3 lexer) {
		Token token = lexer.nextToken();
		if (token.getType() != TokenType.IDENT) {
			throw new SmartScriptParserException();
		}
		ElementVariable variable = new ElementVariable((String) token.getValue());

		Element[] elements = new Element[3];

		// najmanje 2 sljedeća tokena moraju predstavljati jedan od podrazreda Element
		final int atLeastTwo = 2;
		// najviše 3 sljedeća tokena mogu predstavljati jedan od podrazreda Element
		final int maxThree = 3;
		int counter = 0;
		while (counter < maxThree) {
			token = lexer.nextToken();
			if (token.getType() == TokenType.IDENT) {
				elements[counter] = new ElementVariable((String) token.getValue());

			} else if (token.getType() == TokenType.NUMBER) {
				Number number = (Number) token.getValue();
				if (number instanceof Integer) {
					elements[counter] = new ElementConstantInteger((Integer) number);
				} else {
					elements[counter] = new ElementConstantDouble((Double) number);
				}

			} else if (token.getType() == TokenType.STRING) {
				elements[counter] = new ElementString((String) token.getValue());
			} else if (counter == atLeastTwo && token.getType() == TokenType.CLOSED_TAG) {
				lexer.setState(LexerState.TEXT);
				break;
			} else {
				throw new SmartScriptParserException();
			}

			counter++;
		}

		if (counter < atLeastTwo) {
			throw new SmartScriptParserException();
		}

		return new ForLoopNode(variable, elements[0], elements[1], elements[2]);
	}

	/**
	 * Pomoćna metoda koja predstavlja implementaciju parsera čvora
	 * {@link EchoNode}.
	 *
	 * @return čvor {@link EchoNode}
	 */
	private EchoNode parseEchoNode(SmartScriptLexer3 lexer) {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();

		while (true) {
			Token token = lexer.nextToken();
			Element element = null;
			if (token.getType() == TokenType.IDENT) {
				element = new ElementVariable((String) token.getValue());

			} else if (token.getType() == TokenType.NUMBER) {
				Number number = (Number) token.getValue();
				if (number instanceof Integer) {
					element = new ElementConstantInteger((Integer) number);
				} else {
					element = new ElementConstantDouble((Double) number);
				}

			} else if (token.getType() == TokenType.STRING) {
				element = new ElementString((String) token.getValue());
			} else if (token.getType() == TokenType.FUNCTION) {
				element = new ElementFunction((String) token.getValue());
			} else if (token.getType() == TokenType.OPERATOR) {
				element = new ElementOperator((String) token.getValue());
			} else if (token.getType() == TokenType.CLOSED_TAG) {
				lexer.setState(LexerState.TEXT);
				break;
			} else {
				throw new SmartScriptParserException();
			}

			coll.add(element);
		}

		Element[] elements = new Element[coll.size()];
		for (int i = 0; i < coll.size(); i++) {
			elements[i] = (Element) coll.get(i);
		}

		return new EchoNode(elements);
	}

}
