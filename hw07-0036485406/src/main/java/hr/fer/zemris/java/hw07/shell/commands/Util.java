package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

public class Util {

	/**
	 * Parses argument inside quotes in string <code>s</code> from offset <code>off</code>.
	 * 
	 * @param s string to parse
	 * @param off offset from where to parse
	 * @param argList list of arguments to add parsed argument
	 * @return new offset from where to continue to parse
	 */
	private static int parseQuotes(String s, int off, List<String> argList) {
		StringBuilder sb = new StringBuilder();
		// pass start quotes
		off++;
		boolean foundEndQuotes = false;
		for (int n = s.length(); off < n; off++) {
			char c = s.charAt(off);
			if (c == '"') {
				foundEndQuotes = true;
				break;
			}
			if (c != '\\') {
				sb.append(c);
				continue;
			}

			off++;
			if (off >= n) {
				break;
			}
			c = s.charAt(off);
			if (c == '"' || c == '\\') {
				sb.append(c);
			} else {
				sb.append('\\');
				sb.append(c);
			}
		}
		
		if(!foundEndQuotes) {
			throw new IllegalArgumentException("Missing end quotes for " + (argList.size() + 1) + ". argument.");
		}
		
		// pass end quotes
		off++;
		argList.add(sb.toString());
		return off;
	}
	
	/**
	 * Parses argument in string <code>s</code> from offset <code>off</code>.
	 * 
	 * @param s string to parse
	 * @param off offset from where to parse
	 * @param argList list of arguments to add parsed argument
	 * @return new offset from where to continue to parse
	 */
	private static int parseArg(String s, int off, List<String> argList) {
		StringBuilder sb = new StringBuilder();
		for (int n = s.length(); off < n; off++) {
			char c = s.charAt(off);
			// c == ' ' || c == '\t'
			if (Character.isWhitespace(c)) {
				break;
			}
			sb.append(c);
		}

		argList.add(sb.toString());
		return off;
	}

	/**
	 * Splits arguments in string <code>s</code>.
	 * 
	 * @param s string to split arguments from
	 * @param minArgs minimum number of arguments
	 * @param maxArgs maximum number of arguments
	 * @return list of arguments
	 */
	public static List<String> splitArgs(String s, int minArgs, int maxArgs) {
		List<String> argList = new ArrayList<>();

		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			// c == ' ' || c == '\t'
			if (Character.isWhitespace(c)) {
				continue;
			}
			
			if(maxArgs <= argList.size()) {
				throw new IllegalArgumentException("Too many arguments. Maximum is " + maxArgs + ".");
			}

			if (c != '"') {
				i = parseArg(s, i, argList);
			} else {
				
				i = parseQuotes(s, i, argList);
				// either no more characters must be present or
				// at least one space character must be present
				if (i < n && !Character.isWhitespace(s.charAt(i))) {
					throw new IllegalArgumentException(
							"Missing at least one space character after " + (argList.size() + 1) + ". argument.");
				}
			}
		}
		
		if(minArgs > argList.size()) {
			throw new IllegalArgumentException("Too few arguments. Minimum is " + minArgs + ".");
		}

		return argList;
	}
	
	/**
	 * Splits arguments in string <code>s</code>.
	 * 
	 * @param s string to split arguments from
	 * @param numOfArgs number of arguments
	 * @return list of arguments
	 */
	public static List<String> splitArgs(String s, int numOfArgs) {
		List<String> argList = new ArrayList<>();

		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			// c == ' ' || c == '\t'
			if (Character.isWhitespace(c)) {
				continue;
			}
			
			if(numOfArgs <= argList.size()) {
				throw new IllegalArgumentException("Too many arguments. Expected " + numOfArgs + ".");
			}

			if (c != '"') {
				i = parseArg(s, i, argList);
			} else {
				i = parseQuotes(s, i, argList);
				// either no more characters must be present or
				// at least one space character must be present
				if (i < n && !Character.isWhitespace(s.charAt(i))) {
					throw new IllegalArgumentException(
							"Missing at least one space character after " + (argList.size() + 1) + ". argument.");
				}
			}
		}
		
		if(numOfArgs > argList.size()) {
			throw new IllegalArgumentException("Too few arguments. Expected " + numOfArgs + ".");
		}

		return argList;
	}
	
	/**
	 * Throws exception if given string <code>s</code> contains arguments.
	 */
	public static void noArgs(String s) {
		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			// c == ' ' || c == '\t'
			if (Character.isWhitespace(c)) {
				continue;
			} else {
				throw new IllegalArgumentException("Expected 0 arguments.");
			}
		}
	}

}
