package edu.indiana.cs.c212;

import java.awt.Point;

import javax.swing.JFrame;

/**
 * @author <PUT YOUR NAME HERE>
 * @author <PUT YOUR PARTNERS USERNAME HERE>
 *
 */
@SuppressWarnings("serial")
public class Packages extends JFrame{

	/**
	 * 
	 */
	private PackageViewer packageViewer;
	
	/**
	 * 
	 */
	public Packages(){
		this.setSize(500, 500);
		this.packageViewer = new PackageViewer();
		
		this.setContentPane(this.packageViewer);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
Packages ups = new Packages();
		
		ups.setResizable(false);
		
		ups.repaint();
		
		//FIXME
		
		//Uncomment these lines when you have finished implementing
		//the box class
//		Box b = new Box(new Point(3,2), 4, 4);
//		Box b2 = new Box(new Point(4,4), 7, 5);
//		Box b3 = new Box(new Point( 1,1), 6, 2);
//		
//		Uncomment these lines when you have finished implementing
		//the PackageList class
//		ups.packageViewer.getPackageList().add(b);
//		ups.packageViewer.getPackageList().add(b2);
//		ups.packageViewer.getPackageList().add(b3);
//		
//		//This line should output:
//		//[Box:  Corner: java.awt.Point[x=3,y=2] Width: 4 Height: 4, 
//		//Box:  Corner: java.awt.Point[x=4,y=4] Width: 7 Height: 5, 
//		//Box:  Corner: java.awt.Point[x=1,y=1] Width: 6 Height: 2]
//		System.out.println(ups.packageViewer.getPackageList());
//		
//		//This line should output:
//		//Box:  Corner: java.awt.Point[x=1,y=1] Width: 6 Height: 2
//		System.out.println(ups.packageViewer.getPackageList().getSmallestPackage());
//		
//		//This line should output:
//		//Box:  Corner: java.awt.Point[x=4,y=4] Width: 7 Height: 5
//		System.out.println(ups.packageViewer.getPackageList().getLargestPackage());
		
		ups.setVisible(true);
	}
}
