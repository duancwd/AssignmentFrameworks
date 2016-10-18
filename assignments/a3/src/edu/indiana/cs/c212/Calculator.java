package edu.indiana.cs.c212;


import javax.swing.JFrame;

/**
 * This application creates the classes for a simple 4-function calculator.
*/

@SuppressWarnings("serial")
// this class is for education only. don't serialize.
public class Calculator extends JFrame { 
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		CalculatorModel model = new CalculatorModel();
		CalculatorController controller = new CalculatorController(model);
		CalculatorView view = controller.getView();	
		calc.setSize(300, 300);
		calc.setVisible(true);
		calc.add(view);
	}
}