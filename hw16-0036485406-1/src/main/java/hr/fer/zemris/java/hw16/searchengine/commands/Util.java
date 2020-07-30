package hr.fer.zemris.java.hw16.searchengine.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.searchengine.Command;

/**
 * Class used to store function for parsing arguments of {@link Command} objects.
 * 
 * @author nikola
 *
 */
public class Util {
	
	/**
	 * Splits arguments in string <code>s</code>.
	 * 
	 * @param s string to split arguments from
	 * @return list of arguments
	 */
	public static List<String> splitArgs(String s) {
		List<String> argList = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			if (Character.isWhitespace(c)) {
				if(sb.length() != 0) {
					argList.add(sb.toString());
					sb = new StringBuilder();
				}
				continue;
			}
			sb.append(c);
		}
		
		if(sb.length() != 0) {
			argList.add(sb.toString());
		}
		
		return argList;
	}

}
