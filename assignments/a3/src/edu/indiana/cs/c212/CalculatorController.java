package edu.indiana.cs.c212;

//fix the FIXME in this file!

/* The Calculator is managed by its Controller, which is its brain. The
 * Controller controls both the Calculator's Model (which holds the Calculator's
 * data) and the Calculator's View (which displays the Calculator controls and
 * its output to the user).
 *
 * The Controller keeps track of its own state via two variables, the boolean
 * isExpectingTheFirstDigitOfAnOperand and the String previousOperator. (Note:
 * If the Controller is *not* expecting the first digit of an operand, then it
 * *must* be expecting an operator, which can only be one of the 4 above, or
 * '='. So there's no need for a separate boolean for that condition. Early
 * calculators were designed this way to cut down on the logic gates needed to
 * build them, so they could be designed easily and built cheaply.)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
	private CalculatorModel model;
	private CalculatorView view;

	private String previousOperator;
	private boolean isExpectingTheFirstDigitOfAnOperand;

	public CalculatorController(CalculatorModel model) {
		this.model = model;

		ActionListener clearButtonListener = new ClearButtonListener();
		ActionListener digitButtonListener = new DigitButtonListener();
		ActionListener operatorButtonListener = new OperatorButtonListener();

		view = new CalculatorView(digitButtonListener, operatorButtonListener,
				clearButtonListener);

		reset();
	}

	public CalculatorView getView() {
		return view;
	}

	private class ClearButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			reset();
		}
	}

	private class DigitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			String digit = actionEvent.getActionCommand();

			if (isExpectingTheFirstDigitOfAnOperand) {
				// user just entered a digit,
				// and the controller was expecting it,
				// so this is the first digit of an operand

				isExpectingTheFirstDigitOfAnOperand = false;
				view.setDisplay(digit);
			} else {
				// user just entered a digit,
				// and the controller was expecting either:
				// an operator or another digit,
				// so keep ingesting digits until an operator is seen

				view.setDisplay(view.getDisplay() + digit);
			}

			view.setHistory(view.getHistory() + digit);
		}
	}

	private class OperatorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			String operator = actionEvent.getActionCommand();

			String history = view.getHistory();
			if (operator.equals("="))
				history += " ";
			else
				history += operator;
			view.setHistory(history);

			if (!isExpectingTheFirstDigitOfAnOperand) {
				// user just entered an operator,
				// and the controller was expecting either:
				// an operator or another digit,
				// so do what the previousOperator specified

				isExpectingTheFirstDigitOfAnOperand = true;

				String operand = view.getDisplay();
				updateModel(operand);

				previousOperator = operator;
				view.setDisplay(model.getMemory());
			} else {
				// user just entered an operator,
				// but the controller was expecting a digit

				reset();
				view.setDisplay("Error - expected a digit");
			}
		}		
	}
	
	/* updateModel takes a String and checks to see what the previous 
	 * operator was.  Depending on what that operator was, updateModel calls
	 * the appropriate method on the operand.
	 * 
	 * In MVC style code the Controller class (C) will interact with other
	 * classes, namely the Model class (M) and the View class (V).  Please see 
	 * the course Wiki for more information on MVC and this assignment at:
	 * https://github.iu.edu/Fall2013-C212/AssignmentFrameworks/wiki
	 */
	protected void updateModel(String operand) {
		
		//FIXME
		
	}

	private void reset() {
		previousOperator = "=";
		isExpectingTheFirstDigitOfAnOperand = true;
		model.setMemory("0");
		view.setDisplay("0");
		view.setHistory("");
	}
	
	protected void setPrevOperator(String op){
		this.previousOperator = op;
	}
}