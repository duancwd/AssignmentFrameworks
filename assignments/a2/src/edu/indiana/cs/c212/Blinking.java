package edu.indiana.cs.c212;

import java.awt.Color;

import javax.swing.JFrame;

public class Blinking implements Runnable {

	public void create() {

		JFrame frame = new JFrame("Blinker");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controller = new GridController(300,500);
		
		frame.setBackground(Color.CYAN);
		frame.add(controller.getGui());
		frame.pack();
		
		frame.setVisible(true);

	}
	public void run() {
		create();
		try {
			int loopCount = 0;
			while (loopCount < LOOPS_PER_RUN) {
				for (int i = 0; i < CYCLES_PER_LOOP; i++) {
					controller.appear();
					controller.repaint();
					controller.changeColors();
					pauseThread();
				}
				loopCount++;
				controller.vanish();
				controller.drawSmileyFace();
				controller.repaint();
				pauseThread();
			}
		} catch (InterruptedException exception) {
			System.err.println("Thread's sleep is interrupted.");
			exception.printStackTrace();
		}
	}

	private void pauseThread() throws InterruptedException {
		Thread.sleep(THREAD_SLEEP_TIME_IN_MILLISECONDS);
	}

	public static void main(String[] args) {
		(new Thread(new Blinking())).start();
	}

	private GridController controller;

	public static final int UPDATES_PER_SECOND = 5;
	public static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int THREAD_SLEEP_TIME_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
			/ UPDATES_PER_SECOND;
	public static final int CYCLES_PER_LOOP = 5;
	public static final int LOOPS_PER_RUN = 4;

	public static final Color BACKGROUND_COLOR = Color.CYAN;
}