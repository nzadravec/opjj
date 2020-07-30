package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

import static java.lang.Math.*;

/**
 * The calculator works like the standard Windows (from Windows XP) calculator:
 * the number is entered by clicking on the digits buttons.
 * 
 * This calculator does not support any advanced features or respects operator
 * priorities - the term is calculated directly as it is typed. Also, unlike
 * modern calculators, here is not possible to enter a complete expression
 * (which would be displayed on the screen) which whould get calculated after
 * pressing "="; the calculation is carried out immediately by pressing "=" or
 * the next operator (if the previous operator and two arguments are defined).
 * 
 * The "clr" key deletes only the current number (but does not cancel the
 * operation). The "res" key resets to initial state. The "push" button puts the
 * number displayed on the stack. The "pop" key changes the current number with
 * the number at the top of the stack (or indicates that the stack is empty).
 * Checkbox "Inv" changes operations with their inverse. Trigonometric functions
 * imply that angles are in radians.
 * 
 * @author nikola
 *
 */
public class Calculator extends JFrame implements CalcValueListener {

	private static final long serialVersionUID = 1L;

	private final float displayFontSize = 40;
	private final float buttonFontSize = 20;

	/**
	 * Calculators model
	 */
	private CalcModel model;
	
	/**
	 * Calculators display
	 */
	private JLabel display;
	
	/**
	 * Stack
	 */
	private Stack<Double> stack = new Stack<>();

	/**
	 * Flag which indicates when to clear screen
	 */
	private boolean clearScreen;
	
	/**
	 * Flag which indicates when number is defined by user
	 */
	private boolean numberDefined;

	/**
	 * Calculators constructor.
	 */
	public Calculator() {
		setLocation(20, 50);
		setSize(700, 500);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		model = new CalcModelImpl();
		model.addCalcValueListener(this);

		initGUI();
	}

	/**
	 * Checks if screen should be cleared.
	 * 
	 * @return <code>true</code> if screen should ne cleared, else <code>false</code>
	 */
	public boolean isClearScreen() {
		return clearScreen;
	}

	/**
	 * Sets clearScreen attribute to given value.
	 * 
	 * @param clearScreen given value
	 */
	public void setClearScreen(boolean clearScreen) {
		this.clearScreen = clearScreen;
	}

	/**
	 * Checks if number is defined by user.
	 * 
	 * @return <code>true</code> if number is defined by user, else <code>false</code>
	 */
	public boolean isNumberDefined() {
		return numberDefined;
	}

	/**
	 * Sets numberDefined attribute to given value.
	 * 
	 * @return numberDefined given value
	 */
	public void setNumberDefined(boolean numberDefined) {
		this.numberDefined = numberDefined;
	}

	/**
	 * Initialize calculators GUI.
	 */
	private void initGUI() {
		setLayout(new CalcLayout(5));
		Container cp = getContentPane();

		ActionListener digitButtonAction = a -> {
			DigitButton b = (DigitButton) a.getSource();
			if (isClearScreen()) {
				model.clear();
				setClearScreen(false);
			}

			setNumberDefined(true);
			model.insertDigit(b.getDigit());
		};

		ActionListener unaryOpAction = a -> {
			if(!isNumberDefined()) {
				return;
			}
			
			UnaryOpButton b = (UnaryOpButton) a.getSource();
			DoubleUnaryOperator op = b.getOp();
			double operand = model.getValue();
			double value = op.applyAsDouble(operand);
			model.setValue(value);
		};

		ActionListener binaryOpAction = a -> {
			if(!isNumberDefined()) {
				return;
			}
			
			if (!model.isActiveOperandSet()) {
				model.setActiveOperand(model.getValue());
				setNumberDefined(false);
				setClearScreen(true);
			} else {
				double left = model.getActiveOperand();
				double right = model.getValue();
				DoubleBinaryOperator op = model.getPendingBinaryOperation();
				double value = op.applyAsDouble(left, right);
				model.setActiveOperand(value);
				model.setValue(value);
				setNumberDefined(false);
				setClearScreen(true);
			}

			BinaryOpButton b = (BinaryOpButton) a.getSource();
			model.setPendingBinaryOperation(b.getOp());
		};

		JCheckBox inv = new JCheckBox("Inv");
		inv.setFont(inv.getFont().deriveFont(buttonFontSize));
		cp.add(inv, "5,7");

		display = new JLabel(model.toString(), SwingConstants.RIGHT);
		display.setFont(display.getFont().deriveFont(displayFontSize));
		display.setOpaque(true);
		display.setBackground(Color.yellow);
		cp.add(display, "1,1");

		JButton equals = new JButton("=");
		equals.setFont(equals.getFont().deriveFont(buttonFontSize));
		cp.add(equals, "1,6");
		equals.addActionListener(a -> {
			if (!model.isActiveOperandSet()) {
				return;
			}
			
			DoubleBinaryOperator op = model.getPendingBinaryOperation();
			double left = model.getActiveOperand();
			double right = model.getValue();
			model.setValue(op.applyAsDouble(left, right));
			setNumberDefined(false);
			setClearScreen(true);
		});

		JButton clr = new JButton("clr");
		clr.setFont(clr.getFont().deriveFont(buttonFontSize));
		cp.add(clr, "1,7");
		clr.addActionListener(a -> {
			model.clear();
		});

		JButton oneOverX = new UnaryOpButton(x -> 1 / x, "1/x");
		oneOverX.setFont(oneOverX.getFont().deriveFont(buttonFontSize));
		cp.add(oneOverX, "2,1");
		oneOverX.addActionListener(unaryOpAction);

		JButton sin = new UnaryOpButton(x -> sin(x), "sin", x -> asin(x), "asin", inv);
		sin.setFont(sin.getFont().deriveFont(buttonFontSize));
		cp.add(sin, "2,2");
		sin.addActionListener(unaryOpAction);

		JButton seven = new DigitButton(7);
		seven.setFont(seven.getFont().deriveFont(buttonFontSize));
		cp.add(seven, "2,3");
		seven.addActionListener(digitButtonAction);

		JButton eight = new DigitButton(8);
		eight.setFont(eight.getFont().deriveFont(buttonFontSize));
		cp.add(eight, "2,4");
		eight.addActionListener(digitButtonAction);

		JButton nine = new DigitButton(9);
		nine.setFont(nine.getFont().deriveFont(buttonFontSize));
		cp.add(nine, "2,5");
		nine.addActionListener(digitButtonAction);

		JButton div = new BinaryOpButton((x, y) -> x / y, "/");
		div.setFont(div.getFont().deriveFont(buttonFontSize));
		cp.add(div, "2,6");
		div.addActionListener(binaryOpAction);

		JButton res = new JButton("res");
		res.setFont(res.getFont().deriveFont(buttonFontSize));
		cp.add(res, "2,7");
		res.addActionListener(a -> {
			model.clearAll();
		});

		JButton log = new UnaryOpButton(x -> log10(x), "log", x -> pow(10, x), "10^x", inv);
		log.setFont(log.getFont().deriveFont(buttonFontSize));
		cp.add(log, "3,1");
		log.addActionListener(unaryOpAction);

		JButton cos = new UnaryOpButton(x -> cos(x), "cos", x -> acos(x), "acos", inv);
		cos.setFont(cos.getFont().deriveFont(buttonFontSize));
		cp.add(cos, "3,2");
		cos.addActionListener(unaryOpAction);

		JButton four = new DigitButton(4);
		four.setFont(four.getFont().deriveFont(buttonFontSize));
		cp.add(four, "3,3");
		four.addActionListener(digitButtonAction);

		JButton five = new DigitButton(5);
		five.setFont(five.getFont().deriveFont(buttonFontSize));
		cp.add(five, "3,4");
		five.addActionListener(digitButtonAction);

		JButton six = new DigitButton(6);
		six.setFont(six.getFont().deriveFont(buttonFontSize));
		cp.add(six, "3,5");
		six.addActionListener(digitButtonAction);

		JButton mul = new BinaryOpButton((x, y) -> x * y, "*");
		mul.setFont(mul.getFont().deriveFont(buttonFontSize));
		cp.add(mul, "3,6");
		mul.addActionListener(binaryOpAction);

		JButton push = new JButton("push");
		push.setFont(push.getFont().deriveFont(buttonFontSize));
		cp.add(push, "3,7");
		push.addActionListener(a -> {
			double value = model.getValue();
			stack.push(value);
		});

		JButton ln = new UnaryOpButton(x -> log(x), "ln", x -> exp(x), "e^x", inv);
		ln.setFont(ln.getFont().deriveFont(buttonFontSize));
		cp.add(ln, "4,1");
		ln.addActionListener(unaryOpAction);

		JButton tan = new UnaryOpButton(x -> tan(x), "tan", x -> atan(x), "atan", inv);
		tan.setFont(tan.getFont().deriveFont(buttonFontSize));
		cp.add(tan, "4,2");
		tan.addActionListener(unaryOpAction);

		JButton one = new DigitButton(1);
		one.setFont(one.getFont().deriveFont(buttonFontSize));
		cp.add(one, "4,3");
		one.addActionListener(digitButtonAction);

		JButton two = new DigitButton(2);
		two.setFont(two.getFont().deriveFont(buttonFontSize));
		cp.add(two, "4,4");
		two.addActionListener(digitButtonAction);

		JButton three = new DigitButton(3);
		three.setFont(three.getFont().deriveFont(buttonFontSize));
		cp.add(three, "4,5");
		three.addActionListener(digitButtonAction);

		JButton sub = new BinaryOpButton((x, y) -> x - y, "-");
		sub.setFont(sub.getFont().deriveFont(buttonFontSize));
		cp.add(sub, "4,6");
		sub.addActionListener(binaryOpAction);

		JButton pop = new JButton("pop");
		pop.setFont(pop.getFont().deriveFont(buttonFontSize));
		cp.add(pop, "4,7");
		pop.addActionListener(a -> {
			if (stack.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Stack is empty.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				double value = stack.pop();
				model.setValue(value);
			}
		});

		JButton xPowerN = new BinaryOpButton((x, n) -> pow(x, n), "x^n", (x, n) -> pow(x, 1.0 / n), "x^{1/n}", inv);
		xPowerN.setFont(xPowerN.getFont().deriveFont(buttonFontSize));
		cp.add(xPowerN, "5,1");
		xPowerN.addActionListener(binaryOpAction);

		JButton ctg = new UnaryOpButton(x -> 1 / tan(x), "ctg", x -> atan(1 / x), "actg", inv);
		ctg.setFont(ctg.getFont().deriveFont(buttonFontSize));
		cp.add(ctg, "5,2");
		ctg.addActionListener(unaryOpAction);

		JButton zero = new DigitButton(0);
		zero.setFont(zero.getFont().deriveFont(buttonFontSize));
		cp.add(zero, "5,3");
		zero.addActionListener(digitButtonAction);

		JButton plusMinus = new JButton("+/-");
		plusMinus.setFont(plusMinus.getFont().deriveFont(buttonFontSize));
		cp.add(plusMinus, "5,4");
		plusMinus.addActionListener(a -> {
			model.swapSign();
		});

		JButton dot = new JButton(".");
		dot.setFont(dot.getFont().deriveFont(buttonFontSize));
		cp.add(dot, "5,5");
		dot.addActionListener(a -> {
			if (isClearScreen()) {
				model.clear();
				setClearScreen(false);
			}

			setNumberDefined(true);
			model.insertDecimalPoint();
		});

		JButton add = new BinaryOpButton((x, y) -> x + y, "+");
		add.setFont(add.getFont().deriveFont(buttonFontSize));
		cp.add(add, "5,6");
		add.addActionListener(binaryOpAction);
	}

	@Override
	public void valueChanged(CalcModel model) {
		display.setText(model.toString());
		repaint();
	}

	/**
	 * Main function.
	 * 
	 * @param args command line arguments - not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

}
