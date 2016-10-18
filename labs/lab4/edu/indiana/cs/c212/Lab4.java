package edu.indiana.cs.c212;

/**
 * In this class, you will define the method at the bottom of the class.
 * THIS IS THE CLASS YOU WILL RUN WHEN YOU'RE FINISHED WITH YOUR WORK.
 * @author 
 * @author <PARTNERNAME>
 */
{

    /**
     * This is the given main method. 
     * If you fill in all of the other class files that have been given to you, you should be able to run this file.
     * RUN THIS FILE WHEN YOU'VE COMPLETED THE OTHER CLASSES
     * @param args
     */
    public static void main(String []args){
    	
    	//feel free to change these constructors to see if you can get the various messages to display when run.
    	Circle a = new Circle(5, new Point(2,3));
    	Circle b = new Circle();
	  
	  /*
	   *This block checks to see whether the created Circles are touching, overlap or separate. 
	   *The expected result of running this class is They Overlap!
	   *EXPECTED:  They Overlap!
	   */
	  if( distanceBetweenCircles(a,b)>0)
		  System.out.println("They do not touch!");
	  else if(distanceBetweenCircles(a,b) ==0)
		  System.out.println("They are tangential!");
	  else
		  System.out.println("They Overlap!!");
    }
    
    
    /**
     * *NOTE**THIS METHOD MUST BE STATIC TO RUN, PLEASE ASK YOUR UI IF YOU DO NOT UNDERSTAND WHY.
     * This method will return a number representing the distance between the two Circles. 
     * Mathematically, this can be done by finding the distance between the two centers, and 
     * then subtracting the radiuses of the two circles.
     * In formula form :        distanceBetweenCenters - radiusA - radiusB
     * @param a Circle Type
     * @param b Circle Type
     * @return the distance between the two Circles
     * Link to the Math package page in the Java API: http://docs.oracle.com/javase/6/docs/api/java/lang/Math.html
     */

}