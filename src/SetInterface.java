import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SetInterface extends HexUserInterface{
	
	private JButton flag;
	public String[] dropdownSelection = { "Regular", "HEX", "RPN", "HEX RPN", "Set"};
	
	public SetInterface(CalcEngine engine) {
		super(engine);
		addSetInterface();
		testSet();
	}
	
	private void addSetInterface() {
		flag = new JButton(",");
		buttonPanel.add(flag);
		
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
		
		frame.pack();
		
	}
	
	public void actionPerfromed(ActionEvent event) {
		
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
