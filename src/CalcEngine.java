/**
 * The main part of the calculator doing the calculations.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class CalcEngine {
	// The calculator's state is maintained in three fields:
	// buildingDisplayValue, haveLeftOperand, and lastOperator.

	// Are we already building a value in the display, or will the
	// next digit be the first of a new one?
	private boolean buildingDisplayValue;
	// Has a left operand already been entered (or calculated)?
	private boolean haveLeftOperand;
	// The most recent operator that was entered.
	private char lastOperator;
	private char errorChar;
	// The current value (to be) shown in the display.
	private int displayValue;
	// The value of an existing left operand.
	private int leftOperand;
	// Keep it simple and a buffer of more then a million to Integer.MAX_VALUE
	private boolean hasResult;

	/**
	 * Create a CalcEngine.
	 */
	public CalcEngine() {
		clear();
	}

	/**
	 * @return The value that should currently be displayed on the calculator
	 *         display.
	 */
	public int getDisplayValue() {
		return displayValue;
	}

	public int getleftOperand() {
		return leftOperand;
	}

	public boolean getHasResult() {
		return hasResult;
	}

	public char getErrorChar() {
		return errorChar;
	}

	public void setErrorChar(char error) {
		errorChar = error;
	}

	/**
	 * A number button was pressed. Either start a new operand, or incorporate this
	 * number as the least significant digit of an existing one.
	 * 
	 * @param number The number pressed on the calculator.
	 */
	public void numberPressed(int number) {
		if (buildingDisplayValue) {
			int display = displayValue;
			long value = Long.valueOf(display);
			value = value* 10 + number;
			if (( value > Integer.MAX_VALUE) || displayValue > Integer.MAX_VALUE) {
				maxIntReached();
			} else
				displayValue = displayValue * 10 + number; // Incorporate this digit.
		} else {
			// Start building a new number.
			displayValue = number;
			buildingDisplayValue = true;
		}
	}

	/**
	 * The 'plus' button was pressed.
	 */
	public void plus() {
		applyOperator('+');
	}

	/**
	 * The 'minus' button was pressed.
	 */
	public void minus() {
		applyOperator('-');
	}

	public void multi() {
		applyOperator('*');
	}

	/**
	 * The '=' button was pressed.
	 */
	public void equals() {
		// This should completes the building of a second operand,
		// so ensure that we really have a left operand, an operator
		// and a right operand.
		if (haveLeftOperand && lastOperator != '?' && buildingDisplayValue) {
			calculateResult();
			lastOperator = '?';
			buildingDisplayValue = false;
			hasResult = true;
		} else {
			keySequenceError();
		}
	}

	/**
	 * The 'C' (clear) button was pressed. Reset everything to a starting state.
	 */
	public void clear() {
		lastOperator = '?';
		haveLeftOperand = false;
		buildingDisplayValue = false;
		displayValue = 0;
		hasResult = false;
	}

	/**
	 * @return The title of this calculation engine.
	 */
	public String getTitle() {
		return "Java Calculator";
	}

	/**
	 * @return The author of this engine.
	 */
	public String getOldAuthorAndVersion() {
		return "Alexej & Tony | Version 2.0";
	}

	/**
	 * @return The version number of this engine.
	 */
	public String getNewAuthorAndVersion() {
		return "Tony & Kenneth | Version 3.0";
	}

	/**
	 * Combine leftOperand, lastOperator, and the current display value. The result
	 * becomes both the leftOperand and the new display value.
	 */
	private void calculateResult() {
		int display = displayValue;
		int left = leftOperand;
		long d = Long.valueOf(display);
		long l = Long.valueOf(left);
		switch (lastOperator) {
		case '+':
			if (l + d > Integer.MAX_VALUE) {
				maxIntReached();
			} else {
				displayValue = leftOperand + displayValue;
				haveLeftOperand = true;
				leftOperand = displayValue;
			}
			break;
		case '-':
			if (l - d < Integer.MIN_VALUE) {
				maxIntReached();
			} else {
				displayValue = leftOperand - displayValue;
				haveLeftOperand = true;
				leftOperand = displayValue;
			}
			break;
		case '*':
			if ((l * d < Integer.MIN_VALUE) || (l * d > Integer.MAX_VALUE)) {
				maxIntReached();
			} else {
				displayValue = leftOperand * displayValue;
				haveLeftOperand = true;
				leftOperand = displayValue;
			}
			break;
		default:
			keySequenceError();
			break;
		}
	}

	/**
	 * Apply an operator.
	 * 
	 * @param operator The operator to apply.
	 */
	private void applyOperator(char operator) {
		// If we are not in the process of building a new operand
		// then it is an error, unless we have just calculated a
		// result using '='.
		if (!buildingDisplayValue && !(haveLeftOperand && lastOperator == '?')) {
			keySequenceError();
			return;
		}
		if (lastOperator != '?') {
			// First apply the previous operator.
			calculateResult();
		} else {
			// The displayValue now becomes the left operand of this
			// new operator.
			haveLeftOperand = true;
			leftOperand = displayValue;
		}
		lastOperator = operator;
		buildingDisplayValue = false;
	}

	protected void negate() {
		displayValue -= displayValue * 2;
	}

	/**
	 * Report an error in the sequence of keys that was pressed.
	 */
	private void keySequenceError() {
		// System.out.println("A key sequence error has occurred.");
		// Reset everything.
		clear();
		errorChar = '!';
	}

	protected void maxIntReached() {
		clear();
		errorChar = '>';
	}
}