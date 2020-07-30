package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
	
	private static final int MAX_LEN = 308;
	
	private String number;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	
	List<CalcValueListener> listeners = new ArrayList<CalcValueListener>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	private void notifyCalcValueListeners() {
		List<CalcValueListener> listenersCopy = new ArrayList<>(listeners);
		for(CalcValueListener l : listenersCopy) {
			l.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		if(number != null) {
			return Double.parseDouble(number);
		}
		
		return 0.0;
	}

	@Override
	public void setValue(double value) {
		if(Double.isInfinite(value) || Double.isNaN(value)) {
			//throw new IllegalArgumentException();
			return;
		}
		
		number = String.valueOf(value);
		notifyCalcValueListeners();
	}

	@Override
	public void clear() {
		number = null;
		notifyCalcValueListeners();
	}

	@Override
	public void clearAll() {
		number = null;
		activeOperand = null;
		pendingOperation = null;
		notifyCalcValueListeners();
	}

	@Override
	public void swapSign() {
		if(number == null) {
			return;
		}
		if(number.startsWith("-")) {
			number = number.substring(1);
		} else {
			number = "-" + number;
		}
		notifyCalcValueListeners();
	}

	@Override
	public void insertDecimalPoint() {
		if(number == null) {
			number = "0";
		}
		if(number.length() >= MAX_LEN) {
			return;
		}
		if(number.contains(".")) {
			return;
		}
		number += ".";
		notifyCalcValueListeners();
	}

	@Override
	public void insertDigit(int digit) {
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit must be between 0 and 9.");
		}
		
		if(number == null) {
			number = "";
		}
		if(number.length() >= MAX_LEN) {
			return;
		}
		if(number.equals("0")) {
			number = "" + digit;
		} else {
			number += digit;
		}
		notifyCalcValueListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() {
		if(activeOperand == null) {
			throw new IllegalStateException();
		}
		
		double activeOperandCopy = activeOperand;
		activeOperand = null;
		return activeOperandCopy;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		if(pendingOperation == null) {
			throw new IllegalStateException();
		}
		
		DoubleBinaryOperator pendingOperationCopy = pendingOperation;
		//activeOperand = null;
		return pendingOperationCopy;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	@Override
	public String toString() {
		if(number != null) {
			return number;
		}

		return "0";
	}

}
