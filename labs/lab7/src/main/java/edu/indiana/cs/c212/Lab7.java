package edu.indiana.cs.c212;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Lab7 extends JPanel implements ActionListener{	

	private String option = "Random";
	
	/**
	 * This method creates the Frame and the 3 buttons for Color swapping.
	 * It should allow students to see what is needed in order to give panels components.
	 * They should refer to this link for extra guidance:
	 * http://docs.oracle.com/javase/tutorial/uiswing/index.html
	 */
	public JPanel makeButtons(){				
		JPanel panel = new JPanel();		
		JButton colorSwapBlue = new JButton("Red/Green Gradient");
		colorSwapBlue.setBackground(new Color(255,255,0));
		colorSwapBlue.setEnabled(true);
		
		//FIXME
		//Create two more JButtons like the example above
		//Set the first title to "Green/Blue Gradient" and the second to "Random"
		//Set the colors of the first button to 0, 255, 255
		
		colorSwapBlue.addActionListener(this);
		//FIXME
		//Add this class as an action listener to both of the
		//buttons you created above
		
		panel.add(colorSwapBlue);
		//FIXME
		//Add your new buttons to the panel
		return panel;
				
	}


	private static final int BOX_SIZE = 10;
	@Override
	public void paintComponent(Graphics g){
		int width = this.getWidth();
		int height = this.getHeight();
		int numboxes = Math.min(width/BOX_SIZE, height/BOX_SIZE);
		
		if (option.equals("Red/Green Gradient")){
			paintGradientNoBlue(g,numboxes);
		} else if (option.equals("Green/Blue Gradient")){
			paintGradientNoRed(g,numboxes);
		} else if (option.equals("Random")){
			paintGradientRandom(g,numboxes);
		}		
	}	
	
	/**
	 * 
	 * This method will draw a box of size BOX_SIZE on the screen
	 * This method should be called in a loop somewhere to draw a whole lot of boxes.
	 * @param g the graphics context to draw with
	 * @param i the row number of this box in the grid
	 * @param j the column of this box in the grid
	 * @param color the color to draw the box.
	 */
	private void paintBox(Graphics g, int i, int j, Color color){
		g.setColor(color);
		g.fillRect(j*BOX_SIZE, i*BOX_SIZE, BOX_SIZE, BOX_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(j*BOX_SIZE, i*BOX_SIZE, BOX_SIZE, BOX_SIZE);
	}
	
	/**
	 * This method will draw numboxes^2 number of boxes in the specified gradient
	 * (In this case a gradient with no blue eg red->green)
	 * This will be very similar to your first assignment.
	 * In order to generate the gradient, you should divide the color space (255)
	 * by the number of boxes (numboxes). Then multiply the red by the row and the
	 * green by the column. See the Color documentation (linked from the lab description)
	 * for more information on what green and red parts mean.
	 * @param g the graphics context to draw
	 * @param numboxes the number of boxes per side
	 */
	private void paintGradientNoBlue(Graphics g, int numboxes){	
		//FIXME
		//Loop through your row and column to draw boxes with the appropriate
		//gradiant, and then paint each box
	} 
	
	/**
	 * This method will draw numboxes^2 number of boxes in the specified gradient
	 * (In this case a gradient with no red eg blue->green)
	 * This will be very similar to your first assignment.
	 * In order to generate the gradient, you should divide the color space (255)
	 * by the number of boxes (numboxes). Then multiply the green by the row and the
	 * red by the column. See the Color documentation (linked from the lab description)
	 * for more information on what green and blue "parts" mean.
	 * @param g the graphics context to draw
	 * @param numboxes the number of boxes per side
	 */
	private void paintGradientNoRed(Graphics g, int numboxes){	
		//FIXME
		//Loop through your row and column to draw boxes with the appropriate
		//gradiant, and then paint each box
	}
	
	/**
	 * This method will draw numboxes^2 number of boxes with a random color each time.
	 * This will be very similar to your first assignment.
	 * In order to generate the colors, you should generate 3 random colors in the range
	 * 0-255 and create a new Color Object with them.
	 * To generate random integers, use the Random Class
	 * @param g the grapics context to draw
	 * @param numboxes the number of boxes per side
	 */
	private void paintGradientRandom(Graphics g, int numboxes){	
		//FIXME
		//Loop through your row and column to draw boxes with the appropriate
		//gradiant, and then paint each box
	}
	
	/**
	 * This actionPerformed is supposed to draw a Rectangle Randomly upon Button Press. 
	 * This should result in a large number of multicolored rectangles.
	 * 
	 *  (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {				
		option = arg0.getActionCommand();	
		this.repaint();
	}
	
	

	public static void main(String[] args){
		JFrame frame = new JFrame();
		Lab7 l7 = new Lab7();
		JPanel jpmorgan = l7.makeButtons();
		frame.add(jpmorgan, BorderLayout.PAGE_START);
		frame.add(l7, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setSize(700, 700);
		frame.setVisible(true);			
	}	
}
