package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Aplikacija naredbenog retka koja prihvaća jedan argument naredbenog retka:
 * izraz koji treba evaluirati. Izraz mora biti u postfix prikazu. Prilikom
 * pokretanja programa s konzole, cijeli izraz se mora priložiti u navodnike,
 * tako da program uvijek dobiva samo jedan argument. Sve u izrazu mora biti
 * odvojeno jednim (ili više) prostora. Program podržava samo +, -, /, * i %
 * (ostatak cjelobrojnog dijeljenja).
 * 
 * Primjer 1: "8 2 /" znači primijeniti / na 8 i 2, pa 8/2 = 4. Primjer 2: "-1 8
 * 2 / +" znači da se primjenjuje na 8 i 2, pa 8/2 = 4, zatim se primjenjuje +
 * na -1 i 4, pa je rezultat 3.
 * 
 * @author nikola
 *
 */
public class StackDemo {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Argumenti iz naredbenog retka.
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Fali argument iz naredbenog retka.");
			System.exit(1);
		}

		String expression = args[0];
		ObjectStack stack = new ObjectStack();
		for (String element : expression.split(" +")) {
			if (isNumber(element)) {
				int number = Integer.parseInt(element);
				stack.push(number);

			} else {
				int firstElement = 0;
				int secondElement = 0;
				try {
					if (!stack.isEmpty()) {
						secondElement = (int) stack.pop();
					}
					if (!stack.isEmpty()) {
						firstElement = (int) stack.pop();
					}
				} catch (ClassCastException e) {
					System.out.println("Element " + firstElement + "ili" + secondElement + "nije cijeli broj.");
					System.exit(1);
				}

				try {
					int result = performOperation(element, firstElement, secondElement);
					stack.push(result);
				} catch (IllegalArgumentException e) {
					System.out.println("Izraz '" + element + "' nije valjan operator.");
					System.exit(1);
				} catch (DivideByZeroException e) {
					System.out.println("Djelitelj je nula.");
					System.exit(1);
				}
			}
		}

		if (stack.size() != 1) {
			System.out.println("Izraz nije važeći.");
			System.exit(1);
		}

		System.out.println("Expression evaluates to " + stack.pop() + ".");
	}

	/**
	 * Izvodi binarnu operaciju definiranu operatorom nad prvim i drugim elementom.
	 * 
	 * @param operator binarni operator.
	 * @param firstElement prvi element operacije.
	 * @param secondElement drugi element operacije.
	 * @return rezultat binarne operacije.
	 */
	private static int performOperation(String operator, int firstElement, int secondElement) {
		int result;

		switch (operator) {
		case "+":
			result = firstElement + secondElement;
			break;
		case "-":
			result = firstElement - secondElement;
			break;
		case "/":
			if (secondElement == 0) {
				throw new DivideByZeroException();
			}
			result = firstElement / secondElement;
			break;
		case "*":
			result = firstElement * secondElement;
			break;
		case "%":
			result = firstElement % secondElement;
			break;
		default:
			throw new IllegalArgumentException();
		}

		return result;
	}

	/**
	 * Ispituje je li dani niz znakova predstavlja broj.
	 * 
	 * @param element niz znakova koji se ispituje.
	 * @return <code>true</code> ako niz znakova predstavlja broj, inače <code>false</code>.
	 */
	private static boolean isNumber(String element) {
		try {
			Integer.parseInt(element);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
