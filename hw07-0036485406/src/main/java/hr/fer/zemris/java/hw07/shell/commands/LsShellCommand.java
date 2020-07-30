package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents ls command. It's used for writing a directory listing (not
 * recursive) to console.
 * 
 * @author nikola
 *
 */
public class LsShellCommand extends AbstractShellCommand {

	{
		commandName = "ls";

		/*
		 * Command ls takes a single argument – directory – and writes a directory
		 * listing (not recursive).
		 */
		commandDescription.add("Command ls takes a single argument – directory – and writes a directory");
		commandDescription.add("listing (not recursive).");
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
			env.writeln("Command ls takes directory as argument.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.list(dir).forEach(p -> {
				try {
					env.writeln(pathInfo(p));
				} catch (IOException e) {
					env.writeln("I/O error occured executing command.");
				}
			});
		} catch (IOException e) {
			env.writeln("I/O error occured while opening the directory " + dir + ".");
		}

		return ShellStatus.CONTINUE;
	}

	private String pathInfo(Path path) throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append(Files.isDirectory(path) ? 'd' : '-');
		sb.append(Files.isReadable(path) ? 'r' : '-');
		sb.append(Files.isWritable(path) ? 'w' : '-');
		sb.append(Files.isExecutable(path) ? 'x' : '-');
		sb.append(" ");

		sb.append(String.format("%10d", Files.size(path)));
		sb.append(" ");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		sb.append(formattedDateTime);
		sb.append(" ");

		sb.append(path.getFileName());

		return sb.toString();
	}

}
