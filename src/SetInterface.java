import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SetInterface extends HexUserInterface {

	private JPanel setPanel;
	private boolean isSet = false;
	private JPanel cards;
	final private String Regular = "Regular";
	final private String HEX = "HEX";
	final private String RPN = "RPN";
	final private String HEX_RPN = "HEX RPN";
	final private String Set = "Set";

	private SetAsList set1 = new SetAsList();
	private SetAsList set2 = new SetAsList();
	private SetAsList set3 = new SetAsList();

	private JTextField finalSet;
	private JLabel finalSetLabel;

	private String finalSetValue;

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
	
	private void SetInterface() {
		//Put the JComboBox in a JPanel to get a nicer look.
        operationMode.addItemListener((ItemListener) new ItemListener() {
        	public void itemStateChanged(ItemEvent evt) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String)evt.getItem());
            }
        });
        
        
        //Create the "cards".
        JPanel card1 = new JPanel();
		card1.add(displayPanel, BorderLayout.NORTH);
		card1.add(buttonPanel, BorderLayout.CENTER);
		JPanel card2 = new JPanel();
		card2.add(displayPanel, BorderLayout.NORTH);
		card2.add(buttonPanel, BorderLayout.CENTER);
		card2.add(hexButtonPanel, BorderLayout.WEST);
		JPanel card3 = new JPanel();
		card3.add(displayPanel, BorderLayout.NORTH);
		card3.add(buttonPanel, BorderLayout.CENTER);
		JPanel card4 = new JPanel();
		card4.add(displayPanel, BorderLayout.NORTH);
		card4.add(buttonPanel, BorderLayout.CENTER);
		JPanel card5 = new JPanel();
		card5.add(displayPanel, BorderLayout.NORTH);
		card5.add(buttonPanel, BorderLayout.CENTER);


        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, Regular);
		cards.add(card2, HEX);
		cards.add(card3, RPN);
		cards.add(card4, HEX_RPN);
		cards.add(card5, Set);

        contentPane.add(boxPanel, BorderLayout.PAGE_START);
        contentPane.add(cards);
    }
	
	private void addSetInterface() {

		finalSetLabel = new JLabel("");
		displayPanel.add(finalSetLabel);
		finalSet = new JTextField();
		displayPanel.add(finalSet);

		setPanel = new JPanel(new GridLayout(6, 2));
		addButton(setPanel, "{");
		addButton(setPanel, "}");
		addButton(setPanel, ",");
		addButton(setPanel, "add to Set1");
		addButton(setPanel, "add Set");
		addButton(setPanel, "clear Set1");
		addButton(setPanel, "subtract Set");
		addButton(setPanel, "add to Set2");
		addButton(setPanel, "intersect Set");
		addButton(setPanel, "clear Set2");
		contentPane.add(boxPanel, BorderLayout.SOUTH);
		contentPane.add(setPanel, BorderLayout.EAST);
		setPanelEnabled(setPanel, false);

		operationMode.addItemListener((ItemListener) new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (operationMode.getSelectedItem().toString().equals(dropdownSelection[4])) {
						setPanelEnabled(hexButtonPanel, true);
						setPanelEnabled(setPanel, true);
						finalSet.setVisible(true);
						displayText1.setText(dropdownSelection[4] + "1");
						displayText2.setText(dropdownSelection[4] + "2");
						finalSetLabel.setText("Final Set");
						isHex = false;
						isRpn = false;
						isDeci = false;
						isHexRpn = false;
						isSet = true;
						command = "DEL";
						checkOperator();
					} else {
						finalSetLabel.setText("");
						setPanelEnabled(setPanel, false);
						finalSet.setVisible(false);
					}

				}
			}
		});
		
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// /* If the negate button gets pressed do something
		if (event.getSource() == this.negate) {
			// Can't negate nothing
			if (displayValue.length() == 0) {
				displayValue = "First insert a digit and if want use +/- to negate";
				redisplay();
				// to reset the display when the next button gets pressed
				done = true;
				return;
			} else {
				try {
					negate();
				} catch (Exception e) {
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
			displayValue = "Invalid Expression";
			done = true;
			redisplay();
			// Key sequence error occurred, stop calculating
			return;
		}
		// If the previous operator was an operator and the current is again one do
		// something
		if (hasOperator && isOperator(c)) {
			displayValue = "Invalid Expression";
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
			set(c);

		checkOperator();
		redisplay();
	}

	private void set(Character c) {
		if (!(c == '=')) {
			if (!(command == "DEL") && !(command == "?") && !(command == "add to Set1") && !(command == "add to Set2")
					&& !(command == "add Set") && !(command == "subtract Set") && !(command == "intersect Set")
					&& !(command == "clear Set1") && !(command == "clear Set2") && !(command == "clear final Set")) {
				finalSetValue += command;
			}

			if (command == "add to Set1") {
				Scanner scn = new Scanner(finalSetValue.replaceAll(",", " ").replace("{", " ").replace("}", " "));
				while (scn.hasNextInt()) {
					int i = scn.nextInt();
					set1.insert(i);
				}
				scn.close();
				displayString = set1.print();
				finalSetValue = "";
			} else if (command == "add to Set2") {
				Scanner scn = new Scanner(finalSetValue.replaceAll(",", " ").replace("{", " ").replace("}", " "));
				while (scn.hasNextInt()) {
					set2.insert(scn.nextInt());
				}
				scn.close();
				displayValue = set2.print();
				finalSetValue = "";

			} else if (command == "add Set") {
				set3 = set3.addAll(set1, set2);
				finalSetValue = set3.print();
			} else if (command == "subtract Set") {
				set3 = set3.subtract(set1, set2);
				finalSetValue = set3.print();
			} else if (command == "intersect Set") {
				set3 = set3.intersection(set1, set2);
				if (set3.isEmpty())
					finalSetValue = "There is no intersection between these two Sets";
				else
					finalSetValue = set3.print();
			} else if (command == "clear Set1") {
				set1 = new SetAsList();
				displayString = "";
			} else if (command == "clear Set2") {
				set2 = new SetAsList();
				displayValue = "";
			} else if (command == "clear final Set") {
				set3 = new SetAsList();
				finalSetValue = "";
			} else if (command == "DEL") {
				displayString = "";
				displayValue = "";
				finalSetValue = "";

				set1 = new SetAsList();
				set2 = new SetAsList();
				set3 = new SetAsList();
			}
		} else {

		}

	}

//	public SetAsList addAll(SetAsList list1, SetAsList list2) 
//	{
//		Object listCurrent;
//		boolean found = false;
//		
//		SetAsList newList = copy(list1);
//		
//		list2.reset();
//		newList.reset();
//		
//		for (int i = 0; i < list2.size(); i++) 
//		{
//				listCurrent = list2.currentElement();
//			
//			for(int j = 0; j < newList.size(); j++) 
//			{
//				if (newList.currentElement().equals(listCurrent)) 
//				{
//					found = true;
//					break;
//				}
//				else
//					found = false;
//				
//				if (!newList.isLastMember())
//					newList.moveOn();
//				
//				if (!found)
//					newList.insert(listCurrent);
//			}
//			
//			if (!list2.isLastMember())
//				list2.moveOn();
//		}
//		System.out.println("New complete List: " + newList.print());
//		return newList;
//		
//	}
//	
//	
//	public SetAsList copy(SetAsList list) 
//	{
//		SetAsList newList = new SetAsList();
//		Object listCurrent;
//		
//		list.reset();
//		
//		for (int i = 0; i < list.size(); i++ ) 
//		{
//			listCurrent = list.currentElement();
//			newList.insert(listCurrent);
//			
//			if (!list.isLastMember())
//				list.moveOn();
//			else
//				break;
//		}
//		System.out.println("List 1: " + list.print());
//		System.out.println("New List: " + newList.print());
//		return newList;
//	}

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

	protected void clear() {
		super.clear();
		finalSetValue = "";
	}

	protected void redisplay() {
		super.redisplay();
		finalSet.setText(finalSetValue);

	}
}
