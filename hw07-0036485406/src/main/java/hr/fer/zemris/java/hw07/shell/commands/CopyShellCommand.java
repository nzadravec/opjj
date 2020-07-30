package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents copy command. It's used for copying file.
 * 
 * @author nikola
 *
 */
public class CopyShellCommand extends AbstractShellCommand {

	{
		commandName = "copy";

		/*
		 * The copy command expects two arguments: source file name and destination file
		 * name (i.e. paths and names). Is destination file exists, shell asks user is
		 * it allowed to overwrite it. Copy command works only with files (no
		 * directories). If the second argument is directory, shell assumes that user
		 * wants to copy the original file into that directory using the original file
		 * name.
		 */
		commandDescription.add("The copy command expects two arguments: source file name and destination file");
		commandDescription.add("name (i.e. paths and names). Is destination file exists, shell asks user is");
		commandDescription.add("it allowed to overwrite it. Copy command works only with files (no");
		commandDescription.add("directories). If the second argument is directory, shell assumes that user");
		commandDescription.add("wants to copy the original file into that directory using the original file");
		commandDescription.add("name.");
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

		Path file = Paths.get(args.get(0));
		if (!Files.isRegularFile(file)) {
			env.writeln("Command ls takes file as first argument.");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(args.get(1));

		if (Files.isDirectory(path)) {
			path = path.resolve(file.getFileName());
		}

		if (file.toAbsolutePath().equals(path.toAbsolutePath())) {
			env.writeln("Source file name and destination file are the same (ie their absolute path is the same).");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(path)) {
			env.writeln(path + " exists. Is it ok to overwrite it?");
			env.write("Default is NO, write y for YES: ");
			String l = env.readLine();
			if (!l.equals("y")) {
				return ShellStatus.CONTINUE;
			}
		}

		try {
			copyFileUsingStream(file.toFile(), path.toFile());
		} catch (IOException e) {
			env.writeln("I/O error occured while executing command.");
		}

		return ShellStatus.CONTINUE;
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
		dest.createNewFile();
		
	    try(InputStream is = new BufferedInputStream(new FileInputStream(source));
	    		OutputStream os = new BufferedOutputStream(new FileOutputStream(dest))) {
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}

}
