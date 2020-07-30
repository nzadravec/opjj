package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Parses expression obtained through the constructor and generates single
 * {@link NameBuilder} object.
 * 
 * @author nikola
 *
 */
public class NameBuilderParser {

	/**
	 * Expression of new name.
	 */
	private String expr;
	/**
	 * Index of current processing character in expression.
	 */
	private int index;
	/**
	 * Expressions length.
	 */
	private int exprLen;
	/**
	 * Name builder of the parsed expression.
	 */
	private NameBuilder nameBuilder;

	/**
	 * Default constructor.
	 * 
	 * @param expr
	 *            expression
	 */
	public NameBuilderParser(String expr) {
		super();
		this.expr = expr;
		exprLen = expr.length();
		parse();
	}

	/**
	 * Parses expression and generates {@link NameBuilder} object.
	 */
	private void parse() {
		List<NameBuilder> nbs = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean parseCommand = false;
		for (; index < exprLen; index++) {
			if (!parseCommand) {
				for (; index < exprLen; index++) {
					char c = expr.charAt(index);
					if (c == '$' && (index + 1) < exprLen && expr.charAt(index + 1) == '{') {
						index++;
						parseCommand = true;
						break;
					}

					sb.append(c);
				}

				nbs.add(new ConstantNameBuilder(sb.toString()));
				sb = new StringBuilder();

			} else {
				// pass whitespaces till digit
				passWhitespacesTill(Character::isDigit);

				int groupIndex = extractInt();

				boolean commandEnd = checkIfCommandEndsAndPassWhitespacesTill(c -> c == ',');
				if (commandEnd) {
					parseCommand = false;
					nbs.add(new GroupNameBuilder(groupIndex));
					sb = new StringBuilder();
					continue;
				}

				// pass ','
				index++;

				// pass whitespaces till digit
				passWhitespacesTill(Character::isDigit);

				Character fillChar = GroupNameBuilder.DEFAULT_FILL_CHAR;
				if (expr.charAt(index) == '0') {
					index++;
					fillChar = '0';
				}

				int minChars = extractInt();

				commandEnd = checkIfCommandEndsAndPassWhitespacesTill(c -> false);
				if (!commandEnd) {
					throw new IllegalArgumentException("Missing command end character '}'.");
				}

				parseCommand = false;
				nbs.add(new GroupNameBuilder(groupIndex, fillChar, minChars));
				sb = new StringBuilder();
			}

		}

		nameBuilder = new ConcatNameBuilder(nbs);
	}

	/**
	 * Extracts int from the expression from the index.
	 * 
	 * @return extracted int
	 */
	private int extractInt() {
		int startIndex = index;
		for (; index < exprLen; index++) {
			char c = expr.charAt(index);
			if (!Character.isDigit(c)) {
				break;
			}
		}

		int endIndex = index;
		int value;
		try {
			value = Integer.parseInt(expr.substring(startIndex, endIndex));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		return value;
	}

	/**
	 * Checks if the expression from the index contains command end (character
	 * <code>'}'</code>) and passes whitespaces till it encounters it or till given
	 * {@link Predicate} object doesn't return <code>true</code> for current
	 * processing character.
	 * 
	 * @param predicate
	 *            predicate object
	 * @return <code>true</code> if during passing characters of the expression
	 *         method encounters command end (character <code>'}'</code>).
	 * @throws IllegalArgumentException
	 *             if it encounters illegal character
	 */
	private boolean checkIfCommandEndsAndPassWhitespacesTill(Predicate<Character> predicate) {
		boolean commandEnd = false;

		for (; index < exprLen; index++) {
			char c = expr.charAt(index);
			if (c == '}') {
				commandEnd = true;
				break;
			} else if (predicate.test(c)) {
				break;
			} else if (Character.isWhitespace(c)) {
				continue;
			}

			throw new IllegalArgumentException("Illegal character " + c + " at index " + index + ".");
		}

		return commandEnd;
	}

	/**
	 * Passes whitespaces till given {@link Predicate} object doesn't return
	 * <code>true</code> for current processing character.
	 * 
	 * @param predicate
	 *            predicate object
	 * @throws IllegalArgumentException
	 *             if it encounters illegal character
	 */
	private void passWhitespacesTill(Predicate<Character> predicate) {
		for (; index < exprLen; index++) {
			char c = expr.charAt(index);
			if (predicate.test(c)) {
				return;
			} else if (Character.isWhitespace(c)) {
				continue;
			}

			throw new IllegalArgumentException("Missing group number in command.");
		}
	}

	/**
	 * Returns name builder.
	 * 
	 * @return name builder
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;
	}

}
