package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected = NullPointerException.class)
	public void testNullInput() {
		// must throw!
		new SmartScriptLexer(null);
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected = LexerException.class)
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testSpacesInTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ END 	$}");

		// checkToken(lexer.nextToken(), new Token(TokenType.STRING, ""));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}

	@Test
	public void testForLoop() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1 10 1 $}");

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));
		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, -1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

		// =======================================================================

		lexer = new SmartScriptLexer("{$" + "FOR " + " sco_re " + "\"-1\" 10 \" 1 \" $}");

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));
		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "sco_re"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "-1"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, " 1 "));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}

	@Test
	public void testEscapingInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}");

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "A tag follows "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));
		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Joe \"Long\" Smith"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}

	@Test
	public void testEscapingInText() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Example {$=1$}. Now actually write one "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));
		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}

	@Test
	public void sampleText() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\n" + "{$ FOR i 1 10 1 $}\n"
				+ "		This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}"
				+ "		sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}");

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "This is sample text.\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\n		This is "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "-th time this message is generated.\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 0));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 2));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "		sin("));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "^2) = "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "*"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "sin"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "0.000"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "decfmt"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG, null));

		lexer.setState(LexerState.TAG);

		checkToken(lexer.nextToken(), new Token(TokenType.IDENT, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_TAG, null));

		lexer.setState(LexerState.TEXT);

		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

	}

	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		// System.out.println(actual + ", " + expected);
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}

}
