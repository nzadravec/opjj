package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.AbstractShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.Util;

/**
 * Represents massrename command. The command is used for mass-renaming/moving files.
 * 
 * @author nikola
 *
 */
public class MassrenameShellCommand extends AbstractShellCommand {

	{
		commandName = "massrename";
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = Util.splitArgs(arguments, 4, 5);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Path dir1 = Paths.get(args.get(0));
		if (!dir1.isAbsolute()) {
			dir1 = env.getCurrentDirectory().resolve(dir1);
		}

		if (!Files.isDirectory(dir1)) {
			env.writeln("Command takes directory as first argument.");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(args.get(1));
		if (!path.isAbsolute()) {
			path = env.getCurrentDirectory().resolve(path);
		}
		Path dir2 = path;

		if (!Files.isDirectory(dir2)) {
			env.writeln("Command takes directory as second argument.");
			return ShellStatus.CONTINUE;
		}

		CmdSubcommand subcommand;
		try {
			subcommand = CmdSubcommand.valueOf(args.get(2).toUpperCase());
		} catch (IllegalArgumentException e) {
			env.writeln(args.get(2) + " is not valid subcommand name.");
			return ShellStatus.CONTINUE;
		}

		String mask = args.get(3);
		Pattern pattern = Pattern.compile(mask, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		if (args.size() == 4) {
			if (subcommand == CmdSubcommand.FILTER) {

				BiConsumer<File, Matcher> biConsumer = new BiConsumer<>() {
					@Override
					public void accept(File file, Matcher matcher) {
						env.writeln(file.getName());
					}
				};
				executeSubcommand(env, dir1, dir2, pattern, biConsumer);

			} else if (subcommand == CmdSubcommand.GROUPS) {

				BiConsumer<File, Matcher> biConsumer = new BiConsumer<>() {
					@Override
					public void accept(File file, Matcher matcher) {
						StringBuilder sb = new StringBuilder();
						sb.append(file.getName());
						for (int i = 0; i <= matcher.groupCount(); i++) {
							sb.append(" " + i + ": " + matcher.group(i));
						}
						env.writeln(sb.toString());
					}
				};
				executeSubcommand(env, dir1, dir2, pattern, biConsumer);

			} else {
				env.writeln("Too few arguments for subcommand " + subcommand + ".");
			}
		}

		if (args.size() == 5) {
			String expr = args.get(4);
			NameBuilderParser parser = new NameBuilderParser(expr);
			NameBuilder builder = parser.getNameBuilder();
			if (subcommand == CmdSubcommand.SHOW) {
				try {

					BiConsumer<File, Matcher> biConsumer = new BiConsumer<>() {
						@Override
						public void accept(File file, Matcher matcher) {
							NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
							builder.execute(info);
							String newName = info.getStringBuilder().toString();
							env.writeln(file.getName() + " => " + newName);
						}
					};
					executeSubcommand(env, dir1, dir2, pattern, biConsumer);

				} catch (IllegalArgumentException e) {
					env.writeln(e.getMessage());
				}
			} else if (subcommand == CmdSubcommand.EXECUTE) {
				try {

					BiConsumer<File, Matcher> biConsumer = new BiConsumer<>() {
						@Override
						public void accept(File file, Matcher matcher) {
							NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
							builder.execute(info);
							String newName = info.getStringBuilder().toString();
							try {
								Files.move(file.toPath(), dir2.resolve(Paths.get(newName)));
							} catch (IOException e) {
								env.writeln(e.getMessage());
							}
						}
					};
					executeSubcommand(env, dir1, dir2, pattern, biConsumer);

				} catch (IllegalArgumentException e) {
					env.writeln(e.getMessage());
				}
			} else {
				env.writeln("Too many arguments for subcommand " + subcommand + ".");
			}
		}

		return ShellStatus.CONTINUE;
	}

	private void executeSubcommand(Environment env, Path dir1, Path dir2, Pattern pattern,
			BiConsumer<File, Matcher> biConsumer) {
		for (File file : dir1.toFile().listFiles()) {
			if (file.isDirectory()) {
				continue;
			}

			Matcher matcher = pattern.matcher(file.getName());
			if (!matcher.matches()) {
				continue;
			}

			biConsumer.accept(file, matcher);
		}
	}

	private enum CmdSubcommand {
		FILTER("FILTER"), GROUPS("GROUPS"), SHOW("SHOW"), EXECUTE("EXECUTE");

		private final String name;

		/**
		 * @param name
		 *            subcommand name
		 */
		CmdSubcommand(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

}
