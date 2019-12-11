/**
 * The main class of a simple calculator. Create one of these and you'll
 * get the calculator on screen.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Calculator
{
    private CalcEngine engine;
    //private UserInterface gui;
    private HexUserInterface hexGui;
    private SetInterface setGui;
    
	public static void main(String[] args) 
	{
		Calculator calc = new Calculator();
		calc.calculator();
	}

    /**
     * Create a new calculator and show it.
     */
    public void calculator()
    {
        engine = new CalcEngine();
        //gui = new UserInterface(engine);
        //hexGui = new HexUserInterface(engine);
        setGui = new SetInterface(engine);
    }

    /**
     * In case the window was closed, show it again.
     */
    public void show()
    {
    	//gui.setVisible(true);
        //hexGui.setVisible(true);
    	setGui.setVisible(true);
    }
}
