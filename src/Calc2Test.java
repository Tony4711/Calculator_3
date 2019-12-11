import org.junit.Test;
import java.awt.event.ActionEvent;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Calc2Test {
    CalcEngine engine = new CalcEngine();
    HexUserInterface hexGui = new HexUserInterface(engine);
    String displayValue = "";

    // binomials

    @Test
    public void testBinomial1(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "(3+5)^2");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);
        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("(3+5)^2=64", displayValue);
    }

    @Test
    public void testBinomial2(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "(10-6)^2");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("(10-6)^2=16", displayValue);
    }

    @Test
    public void testBinomial3(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "(10+6)*(10-6)");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("(10+6)*(10-6)=64", displayValue);
    }


    // deep brackets
    @Test
    public void testBracket1(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "3*(3+(6*9))");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("3*(3+(6*9))=171", displayValue);
    }

    @Test
    public void testBracket2(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "(22 * (1 + 4 *(32 + 64)))");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("(22*(1+4*(32+64)))=8470", displayValue);
    }

    @Test
    public void testBracket3(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "(2+67)*(21*10*(3+5))");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("(2+67)*(21*10*(3+5))=115920", displayValue);
    }

    // exponent precedence
    @Test
    public void testExpPrecedence1(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "((12*3)*(4+5))^2");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("((12*3)*(4+5))^2=104976", displayValue);
    }

    @Test
    public void testExpPrecedence2(){
        ActionEvent event = new ActionEvent(hexGui.displayValue, 0, "23^2+42^2");
        hexGui.actionPerformed(event);
        ActionEvent equals = new ActionEvent(hexGui, 0, "=");
        hexGui.actionPerformed(equals);

        displayValue = hexGui.displayValue.replaceAll(" ", "");
        assertEquals("23^2+42^2=2293", displayValue);
    }


    @Test
	public void testKeySequenceError() {
		ActionEvent event = new ActionEvent(hexGui, 0, "6");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui,0,"+");
		hexGui.actionPerformed(event);
		hexGui.actionPerformed(event);
		assertEquals("Invalid Expression", hexGui.displayValue);
	}
	
	@Test
	public void testKeySequenceError2() {
		ActionEvent event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		hexGui.actionPerformed(event);
		assertEquals("Invalid Expression", hexGui.displayValue);
	}
	
	@Test
	public void testKeySequenceError3() {
		hexGui.negate.doClick();
		assertEquals("Firt insert a digit and if wanted use +/- to negate", hexGui.displayValue);
	}
	
	@Test
	public void testOperationModeHEX() {
		hexGui.operationMode.setSelectedItem("HEX");
		ActionEvent event = new ActionEvent(hexGui, 0, "F");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "F");
		hexGui.actionPerformed(event);
		ActionEvent equals = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(equals);
		
		assertEquals("F+F = 1E", hexGui.displayString);
	}
	
	@Test
	public void testOperationModeRPN() {
		hexGui.operationMode.setSelectedItem("RPN");
		ActionEvent event = new ActionEvent(hexGui, 0, "6");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "6");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "*");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "2");
		hexGui.actionPerformed(event);
		ActionEvent equals = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(equals);
		
		assertEquals("_6_6_2*+ = 18", hexGui.displayString);
	}
	
	@Test
	public void testOperationModeHEX_RPN() {
		hexGui.operationMode.setSelectedItem("HEX RPN");
		ActionEvent event = new ActionEvent(hexGui, 0, "A");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "6");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "*");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "C");
		hexGui.actionPerformed(event);
		ActionEvent equals = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(equals);
		
		assertEquals("_10_6_12*+ = 52", hexGui.displayString);
	}
	
	@Test
	public void testNegativeValueAtBeginning() {
		ActionEvent event = new ActionEvent(hexGui, 0, "15");
		hexGui.actionPerformed(event);
		hexGui.negate.doClick();
		
		assertEquals("-15", hexGui.displayValue);
	}
	
	@Test
	public void testNegativeExpression() {
		ActionEvent event = new ActionEvent(hexGui, 0, "33");
		hexGui.actionPerformed(event);
		hexGui.negate.doClick();
		event = new ActionEvent(hexGui, 0, "-");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "(");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "-");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "3");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, ")");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "*");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "2");
		hexGui.actionPerformed(event);
		ActionEvent equals = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(equals);
		
		assertEquals("-33-(-3)*2 = -27", hexGui.displayValue);
	}
	
	@Test
	public void InputOverflow() {
		ActionEvent event = new ActionEvent(hexGui, 0, "2147483646");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "2");
		hexGui.actionPerformed(event);
		
		assertNotEquals("2147483648" ,hexGui.displayValue);
	}


}