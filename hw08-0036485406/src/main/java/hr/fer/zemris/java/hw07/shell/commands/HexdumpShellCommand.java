package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents hexdump command. It's used for writing hex-output of given file
 * name to console.
 * 
 * @author nikola
 *
 */
public class HexdumpShellCommand extends AbstractShellCommand {

	{
		commandName = "hexdump";
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

		Path file = Paths.get(args.get(0));
		if (!file.isAbsolute()) {
			file = env.getCurrentDirectory().resolve(file);
		}
		
		if (!Files.isRegularFile(file)) {
			env.writeln("Command takes file as argument.");
			return ShellStatus.CONTINUE;
		}

		int i = 0;
		try (InputStream is = Files.newInputStream(file)) {
			outerloop: while (true) {
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				env.write(String.format("%08X: ", i * 16));

				for (int j = 0; j < 16; j++) {
					if (j == 7) {
						sb1.append("|");
					}

					int value = (int) is.read();
					if (value == -1) {
						for (; j < 16; j++) {
							sb1.append("   ");
						}
						break outerloop;
					}

					sb1.append(String.format("%02X ", value));
					if (value >= 32 && value <= 127) {
						sb2.append((char) value);
					} else {
						sb2.append(".");
					}
				}

				env.write(sb1.toString() + "| ");
				env.writeln(sb2.toString());
				i++;
			}
			env.writeln("");

		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

}
