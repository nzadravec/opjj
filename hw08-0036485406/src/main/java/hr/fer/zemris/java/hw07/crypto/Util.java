package hr.fer.zemris.java.hw07.crypto;

/**
 * Class used to store functions for transforming hex-encoded String to byte
 * array and vice versa. This functions are used in {@link Crypto}.
 * 
 * @author nikola
 *
 */
public class Util {

	/**
	 * Method takes hex-encoded String and return appropriate byte array. For
	 * zero-length string, method returns zero-length byte array. Method supports
	 * both uppercase letters and lowercase letters.
	 * 
	 * @param s
	 *            hex-encoded String
	 * @return byte array
	 */
	public static byte[] hextobyte(String s) {
		if (s.length() % 2 != 0) {
			throw new IllegalArgumentException("Input string is odd-sized.");
		}

		s = s.toLowerCase();
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			char c = s.charAt(i);
			char c2 = s.charAt(i + 1);
			if (Character.digit(c, 16) == -1 || Character.digit(c2, 16) == -1) {
				throw new IllegalArgumentException("Input string has invalid characters.");
			}
			data[i / 2] = (byte) ((Character.digit(c, 16) << 4) + Character.digit(c2, 16));
		}

		return data;
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
	public static String bytetohex(byte[] b) {
		int len = b.length;
		String data = new String();

		for (int i = 0; i < len; i++) {
			data += Integer.toHexString((b[i] >>> 4) & 0xf);
			data += Integer.toHexString(b[i] & 0xf);
		}
		return data;
	}

}
