package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;

/**
 * Represents button of one digit on {@link Calculator}.
 * 
 * @author nikola
 *
 */
public class DigitButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * One digit
	 */
	private int digit;
	
	/**
	 * Creates button of given digit
	 * 
	 * @param digit given digit
	 */
	public DigitButton(int digit) {
        if(digit < 0 || digit > 9) {
        	throw new IllegalArgumentException();
        }
        this.digit = digit;
        setText(String.valueOf(digit));
    }
	
	/**
	 * Returns digit
	 * 
	 * @return digit
	 */
	public int getDigit() {
		return digit;
	}
		
}
