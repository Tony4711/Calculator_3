import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SetInterface extends HexUserInterface {

	private JPanel setPanel;
	private boolean isSet = false;

	public SetInterface(CalcEngine engine) {
		super(engine);
		addSetInterface();
		// testSet();
	}
	
	protected void setDropdownSelection() {
		dropdownSelection = new String[5];
		dropdownSelection[0] = "Regular";
		dropdownSelection[1] = "HEX";
		dropdownSelection[2] = "RPN";
		dropdownSelection[3] = "HEX RPN";
		dropdownSelection[4] = "Set";
		
	}

	private void addSetInterface() {
		setPanel = new JPanel(new GridLayout(6, 1));
		addButton(setPanel, "Set1 done");
		addButton(setPanel, "Set2 done");
		addButton(setPanel, ",");
		addButton(setPanel, "add Set");
		addButton(setPanel, "substract Set");
		addButton(setPanel, "intersect Set");
		contentPane.add(boxPanel, BorderLayout.SOUTH);
		contentPane.add(setPanel, BorderLayout.EAST);
		setPanelEnabled(setPanel, false);
		
		operationMode.addItemListener((ItemListener) new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (operationMode.getSelectedItem().toString().equals(dropdownSelection[4])) {
						setPanelEnabled(hexButtonPanel, true);
						setPanelEnabled(setPanel, true);
						displayText1.setText(dropdownSelection[4]+"1");
						displayText2.setText(dropdownSelection[4]+"2");
						isHex = false;
						isRpn = false;
						isDeci = false;
						isHexRpn = false;
						isSet = true;
						command = "DEL";
						checkOperator();
					}else
						setPanelEnabled(setPanel, false);
				}
			}
		});
		frame.pack();
	}
	
	@Override
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
				else if (isSet)
					set(command);
				// */
				
				checkOperator();
				redisplay();
	}
	
	private void set(String command) {
		if (!(command == "Set1 done")) {
			displayString = "{}";
			displayString += displayString.substring(0, displayString.length()-1) + command + "}";
			
		} else {
			
		}
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
}
