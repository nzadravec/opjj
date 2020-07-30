package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementacija strukture koja pohranjuje više vrijednosti za isti ključ i
 * pruža apstrakciju stoga. Ključ je tipa {@link String}, a stog je
 * implementiran kao povezana lista objekata {@link MultistackEntry}.
 * 
 * @author nikola
 *
 */
public class ObjectMultistack {

	/**
	 * Mapira ključ u stog.
	 */
	private Map<String, MultistackEntry> nameToStack;

	public ObjectMultistack() {
		nameToStack = new HashMap<>();
	}

	/**
	 * Stavlja objekt na vrh stoga imena <code>name</code>.
	 * 
	 * @param name
	 *            ime stoga
	 * @param valueWrapper
	 *            objekt koji se stavlja na vrh stoga imena <code>name</code>
	 * @throws NullPointerException
	 *             ako je ime stoga <code>null</code>.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null) {
			throw new NullPointerException();
		}

		MultistackEntry newEntry = null;
		MultistackEntry head = nameToStack.get(name);
		if (head == null) {
			newEntry = new MultistackEntry(valueWrapper, null);
		} else {
			newEntry = new MultistackEntry(valueWrapper, head);
		}
		nameToStack.put(name, newEntry);
	}

	/**
	 * Uklanja zadnji objekt postavljen na stog imena <code>name</code> i vraća ga.
	 * 
	 * @param name
	 *            ime stoga
	 * @return zadnji objekt postavljen na stog imena <code>name</code>
	 * @throws EmptyStackException
	 *             ako je ime stoga <code>null</code> ili ako je stog imena
	 *             <code>name</code> prazan.
	 */
	public ValueWrapper pop(String name) {
		if (name == null) {
			throw new NullPointerException();
		}

		MultistackEntry head = nameToStack.get(name);
		if (head == null) {
			throw new EmptyStackException();
		}

		nameToStack.put(name, head.next);

		return head.value;
	}

	/**
	 * Vraća zadnji objekt postavljen na stog imena <code>name</code>.
	 * 
	 * @param name
	 *            ime stoga
	 * @return zadnji objekt postavljen na stog imena <code>name</code>
	 * @throws EmptyStackException
	 *             ako je ime stoga <code>null</code> ili ako je stog imena
	 *             <code>name</code> prazan.
	 */
	public ValueWrapper peek(String name) {
		if (name == null) {
			throw new NullPointerException();
		}

		MultistackEntry head = nameToStack.get(name);
		if (head == null) {
			throw new EmptyStackException();
		}

		return head.value;
	}

	/**
	 * Ispituje je li stog imena <code>name</code> prazan.
	 * 
	 * @return <code>true</code> ako je stog imena <code>name</code> prazan, inače
	 *         <code>false</code>
	 * @throws EmptyStackException
	 *             ako je ime stoga <code>null</code>
	 */
	public boolean isEmpty(String name) {
		if (name == null) {
			throw new NullPointerException();
		}

		MultistackEntry head = nameToStack.get(name);
		return head == null;
	}

	/**
	 * Reprezentacija jedno povezane liste.
	 * 
	 * @author nikola
	 *
	 */
	public static class MultistackEntry {
		/**
		 * Pohranjena vrijednost u trenutnom čvoru.
		 */
		private ValueWrapper value;
		/**
		 * Pokazivač na sljedeći čvor.
		 */
		private MultistackEntry next;

		/**
		 * Defaultni konstruktor.
		 * 
		 * @param value
		 *            ohranjena vrijednost u trenutnom čvoru
		 * @param next
		 *            pokazivač na sljedeći čvor
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}

	}

}
