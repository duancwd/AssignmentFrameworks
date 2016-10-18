package edu.indiana.cs.c212;

import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;

/*
 * An instance of this class is the View for a Calculator. It displays data and
 * commands to the user. It has no knowledge of either the Model or the
 * Controller. It simply knows about the screen and how to lay out controls and
 * data on it. Instructions on how the Controller should tell the View to react
 * when the user clicks each button (these are 'event handlers; also known as
 * 'listeners' in Java) are passed to the View from somewhere outside it on its
 * construction.
 */

@SuppressWarnings("serial")
// this class is for education only. don't serialize.
public class CalculatorView extends JPanel {
	public static final Font FONT = new Font("Times Roman", Font.PLAIN, 20);

	private ActionListener digitButtonListener;
	private ActionListener operatorButtonListener;
	private ActionListener clearButtonListener;
	private JTextField historyField;
	private JTextField displayField;

	public CalculatorView(ActionListener digitButtonListener,
			ActionListener operatorButtonListener,
			ActionListener clearButtonListener) {
		this.digitButtonListener = digitButtonListener;
		this.operatorButtonListener = operatorButtonListener;
		this.clearButtonListener = clearButtonListener;

		JPanel displayPanel = setupDisplayPanel();
		JPanel operatorPanel = setupOperatorPanel();
		JPanel digitAndCommandPanel = setupDigitAndCommandPanel();

		JPanel calculatorPanel = new JPanel();
		calculatorPanel.setLayout(new BorderLayout());
		calculatorPanel.add(displayPanel, BorderLayout.NORTH);
		calculatorPanel.add(operatorPanel, BorderLayout.EAST);
		calculatorPanel.add(digitAndCommandPanel, BorderLayout.CENTER);
 
		add(calculatorPanel);
	}

	public String getHistory() {
		return historyField.getText();
	}

	public void setHistory(String string) {
		historyField.setText(string);
	}

	public String getDisplay() {
		return displayField.getText();
	}

	public void setDisplay(String string) {
		displayField.setText(string);
	}

	private JPanel setupDisplayPanel() {
		historyField = new JTextField("");
		historyField.setHorizontalAlignment(JTextField.RIGHT);
		historyField.setFont(FONT);
		historyField.setBackground(Color.LIGHT_GRAY);
		historyField.setEditable(false);

		displayField = new JTextField("0");
		displayField.setHorizontalAlignment(JTextField.RIGHT);
		displayField.setFont(FONT);
		displayField.setBackground(Color.WHITE);
		displayField.setEditable(false);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(historyField);
		panel.add(displayField);

		return panel;
	}

	private JPanel setupOperatorPanel() {
		String[] operators = { "/", "x", "-", "+" };
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(operators.length, 1));
		for (int i = 0; i < operators.length; i++) {
			JButton button = new JButton(operators[i]);
			button.setFont(FONT);
			button.addActionListener(operatorButtonListener);
			JPanel holder = new JPanel();
			holder.add(button);
			panel.add(holder);
		}

		return panel;
	}

	private JPanel setupDigitAndCommandPanel() {
		String[] digitsAndCommands = { "7", "8", "9", "4", "5", "6", "1", "2",
				"3", "0", "=", "Clear" };
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 3));
		for (int i = 0; i < digitsAndCommands.length; i++) {
			String buttonString = digitsAndCommands[i];
			JButton button = new JButton(buttonString);
			JPanel holder = new JPanel();
			holder.add(button);
			panel.add(holder);

			if (buttonString.equals("Clear")) {
				button.addActionListener(clearButtonListener);
			} else if (buttonString.equals("=")) {
				button.setFont(FONT);
				button.addActionListener(operatorButtonListener);
			} else {
				button.setFont(FONT);
				button.addActionListener(digitButtonListener);
			}
		}

		return panel;
	}
}