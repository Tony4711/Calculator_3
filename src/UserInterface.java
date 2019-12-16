import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the calculator. No calculation is being done
 * here. This class is responsible just for putting up the display on screen. It
 * then refers to the "CalcEngine" to do all the real work.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class UserInterface implements ActionListener {
	protected CalcEngine calc;
	private boolean showingAuthor;

	protected JFrame frame;
	protected JTextField display;
	protected JLabel status;
	protected JPanel buttonPanel;
	protected JPanel operatorPanel;
	protected JPanel digitPanel;
	protected JPanel contentPane;
	protected JButton negate;

	/**
	 * Create a user interface.
	 * 
	 * @param engine The calculator engine.
	 */
	public UserInterface(CalcEngine engine) {
		calc = engine;
		showingAuthor = true;
		makeFrame();
		frame.setVisible(true);
	}

	/**
	 * Set the visibility of the interface.
	 * 
	 * @param visible true if the interface is to be made visible, false otherwise.
	 */
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Make the frame for the user interface.
	 */
	private void makeFrame() {
		frame = new JFrame(calc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		display = new JTextField();
		contentPane.add(display, BorderLayout.NORTH);

		buttonPanel = new JPanel(new FlowLayout());
		operatorPanel = new JPanel(new GridLayout(6,1));
		digitPanel = new JPanel(new GridLayout(6,3));
		addButton(digitPanel, "7");
		addButton(digitPanel, "8");
		addButton(digitPanel, "9");
		addButton(operatorPanel, "^");

		addButton(digitPanel, "4");
		addButton(digitPanel, "5");
		addButton(digitPanel, "6");
		addButton(operatorPanel, "*");

		addButton(digitPanel, "1");
		addButton(digitPanel, "2");
		addButton(digitPanel, "3");
		addButton(operatorPanel, "-");

		addButton(digitPanel, "(");
		addButton(digitPanel, "0");
		addButton(digitPanel, ")");

		
		addButton(operatorPanel, "+");
		buttonPanel.add(new JLabel(" "));
		addButton(digitPanel, "?");
		addButton(digitPanel, "DEL");
		addButton(operatorPanel, "=");
		negate = new JButton("+/-");
		operatorPanel.add(negate);
		
		buttonPanel.add(digitPanel);
		buttonPanel.add(operatorPanel);
		
		
		contentPane.add(buttonPanel, BorderLayout.CENTER);

		status = new JLabel(calc.getNewAuthorAndVersion());
		contentPane.add(status, BorderLayout.SOUTH);

		frame.pack();
	}

	/**
	 * Add a button to the button panel.
	 * 
	 * @param panel      The panel to receive the button.
	 * @param buttonText The text for the button.
	 */
	protected void addButton(Container panel, String buttonText) {
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		panel.add(button);
	}
	

	/**
	 * An interface action has been performed. Find out what it was and handle it.
	 * 
	 * @param event The event that has occured.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command.equals("0") || command.equals("1") || command.equals("2") || command.equals("3")
				|| command.equals("4") || command.equals("5") || command.equals("6") || command.equals("7")
				|| command.equals("8") || command.equals("9")) {
			int number = Integer.parseInt(command);
			calc.numberPressed(number);
		} else if (command.equals("+")) {
			calc.plus();
		} else if (command.equals("-")) {
			calc.minus();
		} else if (command.equals("=")) {
			calc.equals();
		} else if (command.equals("DEL")) {
			calc.clear();
		} else if (command.equals("?")) {
			showInfo();
		}
		// else unknown command.

		redisplay();
	}

	/**
	 * Update the interface display to show the current value of the calculator.
	 */
	private void redisplay() {
		display.setText("" + calc.getDisplayValue());
	}

	/**
	 * Toggle the info display in the calculator's status area between the author
	 * and version information.
	 */
	protected void showInfo() {
		if (showingAuthor)
			status.setText(calc.getOldAuthorAndVersion());
		else
			status.setText(calc.getNewAuthorAndVersion());

		showingAuthor = !showingAuthor;
	}
}
