package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * Represents unary operation button on {@link Calculator}. If operation has
 * inverse, then this button can change its to inverse operation button when
 * checkbox on {@link Calculator} is checked.
 * 
 * @author nikola
 *
 */
public class UnaryOpButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Unary operation
	 */
	private DoubleUnaryOperator op;
	/**
	 * Inverse operation
	 */
	private DoubleUnaryOperator invOp;
	/**
	 * Checkbox
	 */
	private JCheckBox inv;
	
	/**
	 * Creates button of given unary operation and operation string text.
	 * 
	 * @param op unary operation
	 * @param opStr operation string text
	 */
	public UnaryOpButton(DoubleUnaryOperator op, String opStr) {
		this(op, opStr, null, null, null);
	}
	
	/**
	 * Creates button of given unary operation, operations inverse, their string text and checkbox.
	 * 
	 * @param op unary operation
	 * @param opStr operations inverse
	 * @param invOp operation string text
	 * @param invOpStr inverse operation string text
	 * @param inv checkbox
	 */
	public UnaryOpButton(DoubleUnaryOperator op, String opStr, DoubleUnaryOperator invOp, String invOpStr, JCheckBox inv) {
		super(opStr);
		this.op = op;
        this.invOp = invOp;
        this.inv = inv;
        if(inv != null) {
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
	 * Returns unary operation.
	 * 
	 * @return unary operation
	 */
	public DoubleUnaryOperator getOp() {
		if(inv == null) {
			return op;
		}
		
		if(inv.isSelected()) {
			return invOp;
		} else {
			return op;
		}
	}
	
}
