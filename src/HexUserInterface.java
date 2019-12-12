import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class HexUserInterface extends UserInterface implements ActionListener {

	// JPanel for the displays
	private JPanel displayPanel;
	// JPanel for the hex buttons A-F
	protected JPanel hexButtonPanel;
	// JPanel for the JComboBox
	private JPanel boxPanel;
	// JComboBox for the operation mode
	protected JComboBox<String> operationMode;
	// /* Descriptive text for the displays
	protected JLabel displayText1;
	protected JLabel displayText2;
	// */
	// First display showing values from the selected operation mode
	protected JTextField displayOperationMode;
	// Variable to store the value from the event
	protected String command;
	// String Array that stores the selections of the JComboBox
	protected String[] dropdownSelection = { "Regular", "HEX", "RPN", "HEX RPN"};
	// int Array that stores the complementary values of A-F
	final private int[] hexNumber = { 10, 11, 12, 13, 14, 15 };
	// Character Array that stores the operators that can be used
	final private Character[] operator = { '+', '-', '*', '/', '^' };
	// variable for converted hex digit
	private int deci = 0;
	// String for the first display showing the selected mode
	protected String displayString = "";
	// String for the second display showing decimal
	protected String displayValue = "";
	private PostfixCalcEngine postfixCalcEngine;
	// left side of expression
	private String lhs = "";
	// right side of expression
	private String rhs = "";
	// last used operator
	private char lastOp;
	// /* booleans to check selected operation mode
	protected boolean isDeci = true;
	protected boolean isHex = false;
	protected boolean isRpn = false;
	protected boolean isHexRpn = false;
	// */
	private boolean done = false;
	private boolean hasOperator = false;

	protected HexUserInterface(CalcEngine engine) {
		super(engine);
		postfixCalcEngine = new PostfixCalcEngine();
		addHexInterface();
	}

	private void addHexInterface() {

		displayPanel = new JPanel(new GridLayout(4, 1));
		displayOperationMode = new JTextField();
		// Blank descriptive text because it changes when an operation mode is selected
		displayText1 = new JLabel("");
		// Setting the descriptive text to Regular because it will always show regular
		// decimal expression
		displayText2 = new JLabel(dropdownSelection[0]);
		// /* Adding the displays and the descriptive texts to the displayPanel
		displayPanel.add(displayText1);
		displayPanel.add(displayOperationMode);
		displayPanel.add(displayText2);
		displayPanel.add(display);
		// */

		// /* Creating and adding the hex buttons to the hexButtonPanel
		hexButtonPanel = new JPanel(new GridLayout(6, 1));
		addButton(hexButtonPanel, "F");
		addButton(hexButtonPanel, "E");
		addButton(hexButtonPanel, "D");
		addButton(hexButtonPanel, "C");
		addButton(hexButtonPanel, "B");
		addButton(hexButtonPanel, "A");
		// */
		// Disable the hexButtonPanel on launch
		setPanelEnabled(hexButtonPanel, false);

		// /* Creating JComboBox for the operation modes and adding it to the boxPanel
		boxPanel = new JPanel();
		operationMode = new JComboBox<>(dropdownSelection);
		boxPanel.add(operationMode);
		// */

		// /* ItemListener for the JComboBox
		operationMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// If the event is equal to selecting an item do something
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// /* Selecting the operation mode by setting the complementary boolean to true
					// if the selected item was "Regular" set operation mode to regular
					if (operationMode.getSelectedItem().toString().equals(dropdownSelection[0])) {
						setPanelEnabled(hexButtonPanel, true);
						displayText1.setText("");
						isHex = false;
						isRpn = false;
						isDeci = true;
						isHexRpn = false;
						command = "DEL";
						checkOperator();
						// if the selected item was "HEX" set operation mode to hex
					} else if (operationMode.getSelectedItem().toString().equals(dropdownSelection[1])) {
						setPanelEnabled(hexButtonPanel, true);
						displayText1.setText(dropdownSelection[1]);
						isHex = true;
						isRpn = false;
						isDeci = false;
						isHexRpn = false;
						command = "DEL";
						checkOperator();
						// if the selected item was "RPN" set operation mode to rpn
					} else if (operationMode.getSelectedItem().toString().equals(dropdownSelection[2])) {
						setPanelEnabled(hexButtonPanel, false);
						displayText1.setText(dropdownSelection[2]);
						isHex = false;
						isRpn = true;
						isDeci = false;
						isHexRpn = false;
						command = "DEL";
						checkOperator();
						// if the selected item was "HEX RPN" set operation mode to hexRpn
					} else if (operationMode.getSelectedItem().toString().equals(dropdownSelection[3])) {
						setPanelEnabled(hexButtonPanel, true);
						displayText1.setText(dropdownSelection[3]);
						isHex = false;
						isRpn = false;
						isDeci = false;
						isHexRpn = true;
						command = "DEL";
						checkOperator();
					}
					// */
				}
			}
		});
		// */
		
		// ActionListener for the negate button
		negate.addActionListener(this);

		// /* Adding everything to the contentPane
		contentPane.add(displayPanel, BorderLayout.NORTH);
		contentPane.add(hexButtonPanel, BorderLayout.WEST);
		contentPane.add(boxPanel, BorderLayout.EAST);
		// */

		frame.pack();
	}

	public void actionPerformed(ActionEvent event) {
		
		
		// /* If the negate button gets pressed do something
		if(event.getSource() == this.negate) {
			// Can't negate nothing
			if(displayValue.length() == 0) {
				displayValue = "Firt insert a digit and if wanted use +/- to negate";
				redisplay();
				// to reset the display when the next button gets pressed
				done = true;
				return;
			}else {
				try {
					negate();
				}catch (Exception e) {
					// If the displayValue contains a term negate would throw an exception
					displayValue = "You can only negate digits";
					redisplay();
					// to reset the display when the next button gets pressed
					done = true;
				}
				redisplay();
				return;
			}
		}
		// */
		
		command = event.getActionCommand();
		Character c = command.charAt(0);

		// /* store last result as new lhs value and display it
		if (done) {
			clear();
			displayValue = lhs;
			if (!isDeci)
				displayString = lhs;
			done = false;
		}
		// */

		// /* check for key sequence error
		// If the first input is an operator do something
		if (displayValue.length() == 0 && isOperator(c)) {
			displayValue="Invalid Expression";
			done = true;
			redisplay();
			// Key sequence error occurred, stop calculating
			return;
		}
		// If the previous operator was an operator and the current is again one do something
		if (hasOperator && isOperator(c)) {
			displayValue="Invalid Expression";
			done = true;
			redisplay();
			// Key sequence error occurred, stop calculating
			return;
		}
		// If the current char is an operator keep track of it
		if (isOperator(c))
			hasOperator = true;
		else
			hasOperator = false;
		// */
		
		
			
		// /* check operation mode
		if (isDeci)
			deci(c);
		else if (isRpn)
			rpn(c);
		else if (isHexRpn || isHex)
			hexRpn(c);
		// */
		
		checkOperator();
		redisplay();
	}

	/*
	 * display regular decimal expressions calculation uses rpn
	 */
	private void deci(Character c) {
		if (!(c == '='))
			displayValue += command;
		else {
			try {
				// store the result as lhs for reusing
				lhs = String.valueOf(postfixCalcEngine.evaluate(postfixCalcEngine.infixToPostfix(displayValue)));
				// display for decimal
				displayValue += " = " + lhs;
			} catch (StackUnderflow e) {
				displayValue="Invalid Expression";
				redisplay();
			} catch (StackOverflow e) {
				displayValue="Invalid Expression";
				redisplay();
			}
			done = true;
		}
	}

	/*
	 * display rpn and regular expression
	 */
	private void rpn(Character c) {
		if (!(c == '=')) {
			displayString += command;
			displayValue = displayString;
		} else {
			try {
				// store the result as lhs for reusing
				lhs = String.valueOf(postfixCalcEngine.evaluate(postfixCalcEngine.infixToPostfix(displayString)));
				// display for rpn
				displayString = postfixCalcEngine.infixToPostfix(displayString) + " = " + lhs;
				// display for decimal
				displayValue += " = " + lhs;
			} catch (StackUnderflow e) {
				displayValue="Invalid Expression";
				redisplay();
				clear();
			} catch (StackOverflow e) {
				displayValue="Invalid Expression";
				redisplay();
				clear();
			}
			done = true;
		}

	}

	/*
	 * display regular hex and decimal expression or display rpn hex and regular
	 * decimal expression
	 */
	private void hexRpn(Character c) {
		if (!(c == '=')) {
			int value = 0;
			// If ActionListener catches "DEL" do not add it to the displayString because it
			// is not calculable
			if (!(command == "DEL"))
				displayString += command;
			if (Character.isDigit(c)) {
				value = Character.getNumericValue(c);
			}
			// /* Converting hex letters to their complementary decimal digit
			if (Character.isLetter(c)) {
				switch (c) {
				case 'A':
					value = hexNumber[0];
					break;
				case 'B':
					value = hexNumber[1];
					break;
				case 'C':
					value = hexNumber[2];
					break;
				case 'D':
					value = hexNumber[3];
					break;
				case 'E':
					value = hexNumber[4];
					break;
				case 'F':
					value = hexNumber[5];
					break;
				}
				// */
			}
			if (isOperator(c)) {
				// Stop keeping track of the current value from the input because it is done
				deci = 0;
				// Shift everything to lhs because rhs changes when a new value gets inserted
				lhs += lastOp + rhs;
				rhs = "";
				// add the new operand to the displayValue
				displayValue += c;
				// keep track of the last operand
				lastOp = c;
			} else {
				// /* convert deci to hex
				value = value + deci * 16;
				deci = value;
				// */
				rhs = String.valueOf(deci);
				// create the term
				displayValue = lhs + lastOp + rhs;
			}
		} else {
			try {
				// store the result as lhs for reusing
				lhs = String.valueOf(postfixCalcEngine.evaluate(postfixCalcEngine.infixToPostfix(displayValue)));
				if (isHexRpn)
					// display for hex rpn
					displayString = postfixCalcEngine.infixToPostfix(displayValue) + " = "
							+ Integer.toHexString(Integer.parseInt(lhs)).toUpperCase();
				else if (isHex)
					// display for hex
					displayString += " = " + Integer.toHexString(Integer.parseInt(lhs)).toUpperCase();

				// display for decimal
				displayValue += " = " + Integer.parseInt(lhs);
			} catch (StackUnderflow e) {
				displayValue="Invalid Expression";
				redisplay();
			} catch (StackOverflow e) {
				displayValue="Invalid Expression";
				redisplay();
			}
			done = true;
		}
	}
	
	/*
	 * Take the value of the display and negate it
	 */
	private void negate() {
		int neg = Integer.parseInt(displayValue);
		neg -= 2 * neg;
		displayValue = String.valueOf(neg);
	}
	
	protected void checkOperator() {
		if (command.equals("DEL")) {
			clear();
			lhs = "";
			command = "";
		} else if (command.equals("?"))
			showInfo();
	}

	private void clear() {
		displayValue = "";
		displayString = "";
		rhs = "";
		deci = 0;
		lastOp = Character.MIN_VALUE;
	}

	private boolean isOperator(Character c) {

		for (int i = 0; i < operator.length; i++)
			if (c == operator[i]) {
				switch (c) {
				case '+':
					return true;

				case '-':
					return true;

				case '*':
					return true;

				case '/':
					return true;

				case '^':
					return true;
				}
			}
		return false;
	}
	
	private void testSet() {
		SetAsList set = new SetAsList();
		SetAsList set2 = new SetAsList();
		set.insert(11);
		set.insert(12);
		set.insert(13);
		set.insert(13);
		set.insert(11);
		set.insert(13);
		set.insert(50);
		set.insert(11);
		set2.insert(11);
		set2.insert(14);
		set2.insert(20);
		set2.insert(13);
		set2.insert(12);
		System.out.println();
		set.addAll(set2);
		System.out.println(set.print());
	}

	private void redisplay() {
		displayOperationMode.setText(displayString);
		display.setText(displayValue);

	}

	/*
	 * From Stack Overflow
	 * https://stackoverflow.com/questions/10985734/java-swing-enabling-disabling-
	 * all-components-in-jpanel
	 * 
	 * @author Kesavamoorthi
	 */
	void setPanelEnabled(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelEnabled((JPanel) components[i], isEnabled);
			}
			components[i].setEnabled(isEnabled);
		}
	}
}