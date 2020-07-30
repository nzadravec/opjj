package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class represents a program that runs from the command line without any
 * arguments. When opened, a window opens, showing two lists and the "next"
 * button to the bottom edge. Click on the button calls the next() method of
 * model {@link PrimListModel}; As a consequence, both lists display this
 * newcomer.
 * 
 * @author nikola
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * PrimDemo constructor.
	 */
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("PrimDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));
		JButton next = new JButton("next");
		bottomPanel.add(next);

		next.addActionListener(e -> {
			model.next();
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Main function.
	 * 
	 * @param args
	 *            command line arguments - not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}

}
