package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * This interface defines model of one calculator. This model is used in
 * {@link Calculator}.
 * 
 * Calculator needs to remember current number that is being defined using
 * {@link #insertDigit(int)} and {@link #insertDecimalPoint()}. Current number
 * can also be defined with {@link #setValue(double)}.
 * 
 * The model contains two more data: active operand (activeOperand) and pending
 * operation (pendingOperation).
 * 
 * Current number, active operand and pending operations interact as follows.
 * Imagine we just turned on the calculator, and we want to add 58 and 14. When
 * we send the digits 5 and 8, the current number will be 58; now whhen user
 * presses button +, the value 58 has to be written to the active operand, the
 * summation operation must be pended (it is still not posible to carry out
 * the operation because secund argument is missing) and the value has to be
 * cleaned so that the user can enter another number. The user will then send
 * the digits 1 and 4 to the value of 14. If the user now presses the button =,
 * the active operand (58), the current number 14 and the scheduled operation
 * (+) will be retrieved; it will be executed and result 72 will be set as a
 * current number (the active operand will be deleted, the pending operation
 * will be deleted).
 * 
 * In a more complex case, when user inputs 58 + 14 - 2 =, when processing the
 * "-" sign we can see what is generally occurring with binary operations. When
 * the user presses "-", the active operand is 58, the pending operation is
 * "+", the value is 14 and the new desired operation is "-"; as there is
 * already a pending operation, it must first be performed (so we will add 58
 * + 14); the result is set as a new active operand (72), a new operation is
 * pended (subtraction) and the value is cleared so that the user can start
 * entering 2.
 * 
 * @author nikola
 *
 */
public interface CalcModel {

	/**
	 * Adds listener.
	 * 
	 * @param l
	 *            listener
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Removes listener.
	 * 
	 * @param l
	 *            listener
	 */
	void removeCalcValueListener(CalcValueListener l);

	/**
	 * Returns current number as string.
	 * 
	 * @return current number as string
	 */
	String toString();

	/**
	 * Returns value of current number.
	 * 
	 * @return value of current number
	 */
	double getValue();

	/**
	 * Sets current number to given value.
	 * 
	 * @param value
	 *            given value
	 */
	void setValue(double value);

	/**
	 * Clears current number.
	 */
	void clear();

	/**
	 * Clears current number, active operand and pending binary operation.
	 */
	void clearAll();

	/**
	 * Swaps sign of current number.
	 */
	void swapSign();

	/**
	 * Inserts decimal point at the end of current number. If current number already
	 * has decimal point, method does nothing.
	 */
	void insertDecimalPoint();

	/**
	 * Inserts one digit at the end of current number.
	 * 
	 * @param digit
	 *            one digit
	 */
	void insertDigit(int digit);

	/**
	 * Checks if active operand is set.
	 * 
	 * @return <code>true</code> if active operand is set, else <code>false</code>
	 */
	boolean isActiveOperandSet();

	/**
	 * Returns active operand.
	 * 
	 * @return active operand
	 * @throws IllegalStateException
	 *             is active operand is not set.
	 */
	double getActiveOperand();

	/**
	 * Sets active operand to given value.
	 * 
	 * @param activeOperand
	 *            given value
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Clears active operand.
	 */
	void clearActiveOperand();

	/**
	 * Returns pending binary operation.
	 * 
	 * @return pending binary operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Sets pending binary operation to given binary operation.
	 * 
	 * @param op
	 *            given binary operation
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}
