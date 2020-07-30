package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Demonstracijski program uporabe pomoćne strukture {@link TreeNode}.
 * 
 * Program od korisnika s tipkovnice čita broj po broj i dodaje ih u stablo ako
 * tamo već ne postoje, a na zaslonu se ispisuje rezultate unosa. Korisnik unos
 * prekida utipkavanjem <code>"kraj"</code>. Za kraj se na zaslon ispisuju
 * brojevi sortirani uzlazno i silazno.
 * 
 * @author nikola
 *
 */
public class UniqueNumbers {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 *            Argumenti iz naredbenog retka.
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		TreeNode head = null;

		while (true) {

			System.out.print("Unesite broj > ");

			String input = sc.next();
			if (input.equals("kraj")) {
				break;
			}

			try {
				int n = Integer.parseInt(input);
				if (containsValue(head, n)) {
					System.out.println("Broj već postoji. Preskačem.");
					continue;
				}

				head = addNode(head, n);
				System.out.println("Dodano.");

			} catch (NumberFormatException ex) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}

		}

		System.out.print("Ispis od najmanjeg: ");
		printInorder(head);
		System.out.println();
		System.out.print("Ispis od najvećeg: ");
		printReversedInorder(head);

		sc.close();
	}

	/**
	 * Pomoćna struktura uređeno binarno stablo.
	 * 
	 * @author nikola
	 *
	 */
	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Dodaje čvor u uređeno binarno stablo. Dodaju čvor u uređeno binarno stablo
	 * (lijevo manji, desno veći), samo ako čvor sa vrijednosti koja se dodaje već
	 * ne postoji.
	 * 
	 * @param head
	 *            korijen binarnog stabla.
	 * @param value
	 *            vrijednost koja se dodaje u stablo.
	 * @return korijen binarnog stabla.
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			TreeNode newHead = new TreeNode();
			newHead.left = newHead.right = null;
			newHead.value = value;
			return newHead;
		}

		if (head.value > value) {
			head.left = addNode(head.left, value);
		} else if (head.value < value) {
			head.right = addNode(head.right, value);
		}

		return head;
	}

	/**
	 * Vraća veličinu stabla.
	 * 
	 * @param head
	 *            korijen binarnog stabla.
	 * @return veličina stabla.
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}

		return treeSize(head.left) + treeSize(head.right) + 1;
	}

	/**
	 * Vraća nalazi li se traženi element u stablu.
	 * 
	 * @param head
	 *            korijen binarnog stabla.
	 * @param value
	 *            vrijednost koja se ispituje.
	 * @return <code>true</code> ako se vrijednost nalazi u stablu, inače
	 *         <code>false</code>.
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}

		if (head.value > value) {
			return containsValue(head.left, value);
		} else if (head.value < value) {
			return containsValue(head.right, value);
		} else {
			return true;
		}
	}

	/**
	 * Ispisuje brojeve binarnog stabla sortirane uzlazno.
	 * 
	 * @param head
	 *            korijen binarnog stabla.
	 */
	public static void printInorder(TreeNode head) {
		if (head == null) {
			return;
		}

		printInorder(head.left);
		System.out.print(head.value + " ");
		printInorder(head.right);
	}

	/**
	 * Ispisuje brojeve binarnog stabla sortirane silazno.
	 * 
	 * @param head
	 *            korijen binarnog stabla.
	 */
	public static void printReversedInorder(TreeNode head) {
		if (head == null) {
			return;
		}

		printReversedInorder(head.right);
		System.out.print(head.value + " ");
		printReversedInorder(head.left);
	}

}
