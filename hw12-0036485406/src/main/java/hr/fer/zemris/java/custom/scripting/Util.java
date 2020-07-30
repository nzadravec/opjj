package hr.fer.zemris.java.custom.scripting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class contains single static method - readFromDisk.
 * 
 * @author nikola
 *
 */
public class Util {

	/**
	 * Reads given file name from <code>"./src/main/resources/"</code> directory and
	 * returns its context as {@link String} object.
	 * 
	 * @param fileName
	 *            fiven file name
	 * @return given file name context as {@link String} object
	 * @throws IOException
	 */
	public static String readFromDisk(String fileName) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
	}

}
