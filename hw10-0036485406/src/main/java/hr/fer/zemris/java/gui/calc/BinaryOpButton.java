package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * Represents binary operation button on {@link Calculator}. If operation has
 * inverse, then this button can change its to inverse operation button when
 * checkbox on {@link Calculator} is checked.
 * 
 * @author nikola
 *
 */
public class BinaryOpButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Binary operation
	 */
	private DoubleBinaryOperator op;
	/**
	 * Inverse operation
	 */
	private DoubleBinaryOperator invOp;
	/**
	 * Checkbox
	 */
	private JCheckBox inv;

	/**
	 * Creates button of given binary operation and operation string text.
	 * 
	 * @param op binary operation
	 * @param opStr operation string text
	 */
	public BinaryOpButton(DoubleBinaryOperator op, String opStr) {
		this(op, opStr, null, null, null);
	}

	/**
	 * Creates button of given binary operation, operations inverse, their string text and checkbox.
	 * 
	 * @param op binary operation
	 * @param opStr operations inverse
	 * @param invOp operation string text
	 * @param invOpStr inverse operation string text
	 * @param inv checkbox
	 */
	public BinaryOpButton(DoubleBinaryOperator op, String opStr, DoubleBinaryOperator invOp, String invOpStr,
			JCheckBox inv) {
		super(opStr);
		this.op = op;
		this.invOp = invOp;
		this.inv = inv;
		if (inv != null) {
			inv.addActionListener(a -> {
				if (inv.isSelected()) {
					setText(invOpStr);
				} else {
					setText(opStr);
				}
			});
		}
	}

	/**
	 * Returns binary operation.
	 * 
	 * @return binary operation
	 */
	public DoubleBinaryOperator getOp() {
		if (inv == null) {
			return op;
		}

		if (inv.isSelected()) {
			return invOp;
		} else {
			return op;
		}
	}

}
