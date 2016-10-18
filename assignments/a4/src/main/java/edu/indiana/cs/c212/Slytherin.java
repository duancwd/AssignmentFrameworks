package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;


@SuppressWarnings("serial")
// this class is for education only. don't serialize.
public class Slytherin extends JFrame {
	private boolean threadIsRunning;
	public static final int STATE_CHANGES_PER_SECOND = 5;
	public static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int THREAD_SLEEP_TIME_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
			/ STATE_CHANGES_PER_SECOND;

	public static final Color BACKGROUND_COLOR = Color.CYAN;

	public static final int TILE_SIZE = Grid.TILE_SIZE;

	private SnakePit snakePit;

	public Slytherin() {
		this.setSize(675, 675);
		int rowCount = this.getHeight() / TILE_SIZE;
		int columnCount = this.getWidth() / TILE_SIZE;
		Grid grid = new Grid(0, 0, rowCount, columnCount, BACKGROUND_COLOR);
		snakePit = new SnakePit(grid);
		MouseListener listener = new MouseClickListener();
		this.addMouseListener(listener);
	}

	public static void main(String[] args) {
		Slytherin basilisk = new Slytherin();
		basilisk.setResizable(false);
		basilisk.setLayout(new FlowLayout());
		basilisk.setVisible(true);
		basilisk.setContentPane(basilisk.snakePit);
		basilisk.revalidate();
		basilisk.threadIsRunning = true;
		basilisk.snakePit.restart();
		basilisk.snakePit.repaint();
		while (basilisk.threadIsRunning){
			basilisk.snakePit.repaint();
			basilisk.snakePit.changeState();			
			
			
			try {
				Thread.sleep(THREAD_SLEEP_TIME_IN_MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class MouseClickListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent mouseEvent) {
			Point point = mouseEvent.getPoint();
			int row = (int) (point.getY() / TILE_SIZE) - 1;
			int column = (int) (point.getX() / TILE_SIZE) ;
			Point gridPoint = new Point(row, column);

			snakePit.takeActionAt(gridPoint);
			snakePit.repaint();
		}
	}
}
