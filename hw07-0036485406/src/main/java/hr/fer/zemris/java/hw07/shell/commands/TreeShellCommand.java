package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents tree command. It's used for writing a directory tree to console.
 * 
 * @author nikola
 *
 */
public class TreeShellCommand extends AbstractShellCommand {

	{
		commandName = "tree";

		commandDescription.add(
				"The tree command expects a single argument: directory name and prints a tree (each directory level shifts)");
		commandDescription.add("output two charatcers to the right).");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 1);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Path dir = Paths.get(args.get(0));
		if (!Files.isDirectory(dir)) {
			env.writeln("Command tree takes directory as argument.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(dir, new FileVisitorImpl(env));
		} catch (IOException e) {
			env.writeln("I/O error occured during executing command.");
		}

		return ShellStatus.CONTINUE;
	}

	private static class FileVisitorImpl implements FileVisitor<Path> {

		private int indent;
		private Environment env;

		private FileVisitorImpl(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < indent; i++) {
				sb.append(" ");
			}
			sb.append(arg0.getFileName());
			env.writeln(sb.toString());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

}
