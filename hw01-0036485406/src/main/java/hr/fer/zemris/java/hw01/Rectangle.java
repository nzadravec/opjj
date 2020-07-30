package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Demonstracijski program izračunavanja površine i opsega pravokutnika.
 * 
 * Program pita korisnika preko naredbenog retka da unese širinu pa visinu
 * pravokutnika. Program korisniku ispisuje površinu i opseg pravokutnika. Ako
 * program pri pokretanju dobije argumente preko naredbenog retka, onda ništa ne
 * pita korisnika, već odmah računa i ispisuje površinu i opseg.
 * 
 * @author nikola
 *
 */
public class Rectangle {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Argumenti iz naredbenog retka.
	 */
	public static void main(String[] args) {

		if (args.length != 0) {

			if (args.length != 2) {
				System.out.println("Broj argumenata naredbenog retka mora biti 2.");
				System.exit(1);
			}

			double width = 0;
			double height = 0;

			double surface = 0;
			double perimeter = 0;

			try {

				width = Double.parseDouble(args[0]); // širina
				height = Double.parseDouble(args[1]); // visina

				surface = rectangleSurface(width, height); // površina
				perimeter = rectanglePerimeter(width, height); // opseg

			} catch (NumberFormatException e) {
				System.out.println("'" + args[0] + "' ili '" + args[1] + "' nije cijeli broj.");
			}

			printSurfaceAndPerimeter(width, height, surface, perimeter);
			System.exit(0);
		}

		Scanner sc = new Scanner(System.in);

		double width = getPositiveNumberFromInput(sc, "Unesite širinu > ");
		double height = getPositiveNumberFromInput(sc, "Unesite visinu > ");

		double surface = rectangleSurface(width, height); // površina
		double perimeter = rectanglePerimeter(width, height); // opseg

		printSurfaceAndPerimeter(width, height, surface, perimeter);

		sc.close();
	}

	/**
	 * Ispisuje dane podatke o pravokutniku na standardni izlaz.
	 * 
	 * @param width
	 *            širina pravokutnika.
	 * @param height
	 *            visina pravokutnika.
	 * @param surface
	 *            površina pravokutnika.
	 * @param perimeter
	 *            opseg pravokutnika.
	 */
	public static void printSurfaceAndPerimeter(double width, double height, double surface, double perimeter) {
		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + surface
				+ " te opseg " + perimeter + ".");
	}

	/**
	 * Vraća pozitivan broj učitan sa standardnog ulaza. Argument
	 * <code>textToUser</code> služi objašnjenju što se od korisnika očekuje da
	 * unese.
	 * 
	 * @param sc
	 *            objekt pomoću kojeg se dohvaća korisnikov unos.
	 * @param textToUser
	 *            niz znakova koji se ispisuje na zaslon.
	 * @return pozitivan broj.
	 */
	public static double getPositiveNumberFromInput(Scanner sc, String textToUser) {
		double number;
		String input = null;

		while (true) {
			try {
				System.out.print(textToUser);

				input = sc.next();
				number = Double.parseDouble(input);
				if (number < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}

				break;

			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' se ne može protumačiti kao broj.");
			}

		}

		return number;
	}

	/**
	 * Vraća površinu dane širine i visine pravokutnika.
	 * 
	 * @param width
	 *            širina pravokutnika.
	 * @param height
	 *            visina pravokutnika.
	 * @return površina pravokutnika.
	 */
	public static double rectangleSurface(double width, double height) {
		checkRectangleParams(width, height);

		return width * height;
	}

	/**
	 * Vraća opseg dane širine i visine pravokutnika.
	 * 
	 * @param width
	 *            širina pravokutnika.
	 * @param height
	 *            visina pravokutnika.
	 * @return opseg pravokutnika.
	 */
	public static double rectanglePerimeter(double width, double height) {
		checkRectangleParams(width, height);

		return 2 * (width + height);
	}

	/**
	 * Ispituje jesu li širina i visina pravokutnika pozitivni brojevi.
	 * 
	 * @param width
	 *            širina pravokutnika.
	 * @param height
	 *            visina pravokutnika.
	 * @throws IllegalArgumentException
	 *             ako je širina ili visina manja ili jednaka nuli.
	 */
	public static void checkRectangleParams(double width, double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Širina i visina pravokutnika moraju biti pozitivni brojevi.");
		}
	}

}
