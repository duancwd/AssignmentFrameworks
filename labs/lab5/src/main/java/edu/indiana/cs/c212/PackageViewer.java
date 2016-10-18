package edu.indiana.cs.c212;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author <PUT YOUR NAME HERE>
 * @author <PUT YOUR PARTNERS USERNAME HERE>
 *
 */
@SuppressWarnings("serial")
public class PackageViewer extends JPanel{
	
	/**
	 * 
	 */
	private PackageList packageList;

	/**
	 * 
	 */
	public PackageViewer(){
		this.packageList = new PackageList();
	}
	
	public PackageList getPackageList() {
		return packageList;
	}

	public void setPackageList(PackageList packageList) {
		this.packageList = packageList;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		this.setBackground(Color.white);
		Graphics2D g2 = (Graphics2D)g;
		g2.scale(10,10);
		g2.setStroke(new BasicStroke(0.5f));
		
		for(int i = 0; i < this.packageList.count(); i++){
			Box b = this.packageList.get(i);
			
			g.drawRect((int)b.getCorner().getX(), (int)b.getCorner().getY(), b.getWidth(), b.getHeight());
		}
	}
}
