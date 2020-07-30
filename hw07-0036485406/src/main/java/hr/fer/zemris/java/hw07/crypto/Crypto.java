package hr.fer.zemris.java.hw07.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Crypto allows the user to encrypt/decrypt given file using the AES crypto-
 * algorithm and the 128-bit encryption key or calculate and check the SHA-256
 * file digest.
 * 
 * @author nikola
 *
 */
public class Crypto {

	public static void main(String[] args) {

		if (args.length != 2 && args.length != 3) {
			System.err.println("Missing arguments.");
			return;
		}

		if (args.length == 2 && args[0].equals("checksha")) {
			checksha(Paths.get(args[1]));
		} else if (args.length == 3 && (args[0].equals("encrypt") || args[0].equals("decrypt"))) {
			if (args[0].equals("encrypt")) {
				encrypt_decrypt(true, Paths.get(args[1]), Paths.get(args[2]));
			} else {
				encrypt_decrypt(false, Paths.get(args[1]), Paths.get(args[2]));
			}
		} else {
			System.err.println("Arguments are invalid.");
			return;
		}

	}

	/**
	 * Method encrypts/decrypts given file using the AES crypto-algorithm.
	 * 
	 * @param encrypt
	 *            if <code>true</code> method will encrypt file, else method will
	 *            decrypt file
	 * @param inputPath
	 *            file that method encrypts/decrypts
	 * @param outputPath
	 *            encrypted/decrypted file
	 */
	private static void encrypt_decrypt(boolean encrypt, Path inputPath, Path outputPath) {
		if (!Files.isRegularFile(inputPath)) {
			System.err.println(inputPath + " is not file.");
			return;
		}

		Scanner sc = new Scanner(System.in);

		String textToUser = "Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):";
		String errorText = "Password has to be 32 chars long.";
		byte[] keyText;
		while(true) {
			try {
				String password = getStringOfCertainLengthFromUser(sc, textToUser, errorText, 32);
				keyText = Util.hextobyte(password);				
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		textToUser = "Please provide initialization vector as hex-encoded text (32 hex-digits):";
		errorText = "Initialization vector has to be 32 chars long.";
		byte[] ivText = null;
		while(true) {
			try {
				String initVector = getStringOfCertainLengthFromUser(sc, textToUser, errorText, 32);
				keyText = Util.hextobyte(initVector);				
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		sc.close();

		SecretKeySpec keySpec = new SecretKeySpec(keyText, "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivText);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.err.println(e.getMessage());
			return;
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			System.err.println(e.getMessage());
			return;
		}

		try (InputStream is = Files.newInputStream(inputPath); OutputStream os = Files.newOutputStream(outputPath)) {

			byte[] ibuff = new byte[4096];
			byte[] obuff = new byte[4096];
			while (true) {
				int r = is.read(ibuff);
				if (r < 1) {
					try {
						byte[] bs = cipher.doFinal();
						os.write(bs);
					} catch (IllegalBlockSizeException | BadPaddingException e) {
						System.err.println(e.getMessage());
						return;
					}
					break;
				}

				int w;
				try {
					w = cipher.update(ibuff, 0, r, obuff);
				} catch (ShortBufferException e) {
					System.err.println(e.getMessage());
					return;
				}
				os.write(obuff, 0, w);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
	}

	/**
	 * Method calculates and checks the SHA-256 file digest for given file.
	 * 
	 * @param file
	 *            for which method calculates and checks the SHA-256 file digest
	 */
	private static void checksha(Path file) {
		if (!Files.isRegularFile(file)) {
			System.err.println(file + " is not file.");
			return;
		}

		Scanner sc = new Scanner(System.in);

		String textToUser = "Please provide expected sha-256 digest for " + file + ":";
		String errorText = "Digest has to be 64 chars long.";
		byte[] digest;
		while(true) {
			try {
				String digestString = getStringOfCertainLengthFromUser(sc, textToUser, errorText, 64);
				digest = Util.hextobyte(digestString);				
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
		
		sc.close();

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			return;
		}

		byte[] mdSHA256;
		try (InputStream is = Files.newInputStream(file)) {
			byte[] buf = new byte[1024];
			int read;
			while ((read = is.read(buf)) >= 0) {
				md.update(buf, 0, read);
			}
			mdSHA256 = md.digest();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}

		if (Arrays.equals(digest, mdSHA256)) {
			System.out.println("Digesting completed. Digest of " + file.getFileName() + " matches expected digest.");
		} else {
			System.out.println(
					"Digesting completed. Digest of " + file.getFileName() + " does not match expected digest.");
		}
	}

	/**
	 * Returns hex-encoded text of size <code>length</code> read from standard input.
	 * 
	 * @param sc
	 *            object for getting user input
	 * @param textToUser
	 *            string which prints to users screen
	 * @param errorText
	 *            error message which prints to users screen if the given input is
	 *            not of size <code>length</code>
	 * @param length required string size
	 * @return hex-encoded text of size <code>length</code>
	 */
	private static String getStringOfCertainLengthFromUser(Scanner sc, String textToUser, String errorText,
			int length) {
		String input = null;
		while (true) {
			System.out.println(textToUser);
			System.out.print("> ");
			input = sc.nextLine();
			if (input.length() == length) {
				break;
			}
			System.out.println(errorText);
		}

		return input;
	}

}
