package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Demonstracijski program uporabe statičke metode {@link #factorial}.
 * 
 * Korisnik naredbenog retka unosi brojeve, a na zaslonu se ispisuju faktorijele
 * danih brojeva. Unosom niza znakova <code>"kraj"</code> program se zaustavlja.
 * 
 * @author nikola
 *
 */
public class Factorial {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Argumenti iz naredbenog retka.
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.print("Unesite broj > ");

			String input = sc.next();
			if (input.equals("kraj")) {
				break;
			}

			try {
				int n = Integer.parseInt(input);

				if (n < 0 || n > 20) {
					System.out.println("'" + n + "' nije broj u dozvoljenom rasponu.");
				} else {
					System.out.println(n + "! = " + factorial(n));
				}
			} catch (NumberFormatException ex) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}

		}

		System.out.println("Doviđenja.");

		sc.close();
	}

	/**
	 * Vraća faktorijelu dane vrijednosti n.
	 * 
	 * 
	 * @param n
	 *            vrijednost čija faktorijela će se vratiti.
	 * @return faktorijela argumenta.
	 * @throws IllegalArgumentException
	 *             ako je dana vrijednost manja od 0 ili veća od 20.
	 */
	public static long factorial(int n) {
		if (n < 0 || n > 20) {
			throw new IllegalArgumentException("Faktorijela broja '" + n + "' nije moguće izračunati.");
		}

		long fact = 1;
		for (int i = 1; i <= n; i++) {
			fact = fact * i;
		}

		return fact;
	}

}
