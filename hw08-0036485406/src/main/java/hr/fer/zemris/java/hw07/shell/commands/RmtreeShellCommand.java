package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents rmtree command. Command erases given directory and its complete
 * content.
 * 
 * @author nikola
 *
 */
public class RmtreeShellCommand extends AbstractShellCommand {

	{
		commandName = "rmtree";
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
		if (!dir.isAbsolute()) {
			dir = env.getCurrentDirectory().resolve(dir);
		}

		if (!Files.isDirectory(dir)) {
			env.writeln("Command takes directory as argument.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException arg1) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

}
