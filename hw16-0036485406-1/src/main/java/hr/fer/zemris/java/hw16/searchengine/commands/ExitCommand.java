package hr.fer.zemris.java.hw16.searchengine.commands;

import java.util.List;

import hr.fer.zemris.java.hw16.searchengine.Command;
import hr.fer.zemris.java.hw16.searchengine.CommandStatus;
import hr.fer.zemris.java.hw16.searchengine.SearchEngine;

/**
 * Exits from console.
 * 
 * @author nikola
 *
 */
public class ExitCommand implements Command {

	@Override
	public CommandStatus executeCommand(SearchEngine searchEngine, String arguments) {
		List<String> argList = Util.splitArgs(arguments);
		if(!argList.isEmpty()) {
			System.out.println("Očekivano 0 argumenata.");
			return CommandStatus.CONTINUE;
		}
			
		return CommandStatus.TERMINATE;
	}

}
