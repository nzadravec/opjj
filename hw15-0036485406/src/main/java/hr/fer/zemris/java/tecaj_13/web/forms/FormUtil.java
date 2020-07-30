package hr.fer.zemris.java.tecaj_13.web.forms;

/**
 * Class contains static methods used in form models of domain objects.
 * 
 * @author nikola
 *
 */
public class FormUtil {

	/**
	 * An ancillary method that converts <code>null</code> strings into empty
	 * strings, which is much more convenient for use on the web.
	 * 
	 * @param s
	 *            string
	 * @return received string if i's not <code>null </code>, an empty string
	 *         otherwise.
	 */
	public static String prepare(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Method takes a byte array and creates its hex-encoding. For each byte of
	 * given array, two characters are returned in string, in big-endian notation.
	 * For zero-length array an empty string is returned. Method use lowercase
	 * letters for creating encoding.
	 * 
	 * @param b
	 *            byte array
	 * @return hex-encoding of given byte array
	 */
	public static String hexEncode(byte[] b) {
		int len = b.length;
		String data = new String();

		for (int i = 0; i < len; i++) {
			data += Integer.toHexString((b[i] >>> 4) & 0xf);
			data += Integer.toHexString(b[i] & 0xf);
		}
		return data;
	}

}
