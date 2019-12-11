import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JButton;

public class SetInterface extends HexUserInterface{
	
	private JButton flag;
	
	public SetInterface(CalcEngine engine) {
		super(engine);
		addSetInterface();
		testSet();
	}
	
	private void addSetInterface() {
		flag = new JButton(",");
		buttonPanel.add(flag);
		
		operationMode.addItem("Set");
		
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
