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
 * Represents cptree command. Command copys the tree of path PATH1.
 * 
 * @author nikola
 *
 */
public class CptreeShellCommand extends AbstractShellCommand {

	{
		commandName = "cptree";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 2);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Path srcDir = Paths.get(args.get(0));
		if (!srcDir.isAbsolute()) {
			srcDir = env.getCurrentDirectory().resolve(srcDir);
		}

		if (!Files.isDirectory(srcDir)) {
			env.writeln("Command takes directory as second argument.");
			return ShellStatus.CONTINUE;
		}

		Path destDir = Paths.get(args.get(1));
		if (!destDir.isAbsolute()) {
			destDir = env.getCurrentDirectory().resolve(destDir);
		}

		if (Files.isDirectory(destDir)) {
			srcDir = srcDir.getParent();
		} else if (Files.isDirectory(destDir.getParent())) {
			try {
				Files.createDirectories(destDir);
			} catch (IOException e) {
				env.writeln(e.getMessage());
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Command takes directory as second argument.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(srcDir, new CopyFileVisitor(destDir));
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	public class CopyFileVisitor extends SimpleFileVisitor<Path> {
		private final Path targetPath;
		private Path sourcePath = null;

		public CopyFileVisitor(Path targetPath) {
			this.targetPath = targetPath;
		}

		@Override
		public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
			if (sourcePath == null) {
				sourcePath = dir;
			} else {
				Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
			Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));
			return FileVisitResult.CONTINUE;
		}
	}

}
