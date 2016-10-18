package edu.indiana.cs.c212;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;


public class FruitList {
	private List<Fruit> fruits;
	//FIXME
	/**
	 * Write a FruitList constructor that initializes the fruits field to an ArrayList<Fruit>. 
	 * 
	 * Remember that to initialize a class, use the new keyword.
	 */
	

	/**
	 * Write a public method add() that adds the given fruit to the list of fruits. It does not return anything. 
	 * @param fruit to be added
	 */
	public void add(Fruit fruit) {
		fruits.add(fruit);
	}

	/**
	 * This method clears the list of fruits
	 */
	public void clear() {
		fruits.clear();
	}

	/**
	 * Write a public method hasFruit() that returns a true or false value representing the emptiness of the fruit list.
	 * @return whether or not the Fruit List is empty
	 */
	

	/**
	 * Write a method getFruit() that returns the first fruit in the list.
	 * If it doesn't have any fruit, return null;
	 * @return the first fruit in the list.
	 */
	

	/**
	 *  The getFruits() method returns the entire fruit List.
	 * @return the entire List<Fruit>
	 */
	public List<Fruit> getFruits() {
		return fruits;
	}

	
	/**
	 * Write a public method hasAFruitAt() that returns if any of the current fruits in the list are at the given point.
	 * @param point to check the fruits in the list against
	 * @return true or false if there is a fruit at that point
	 */
	

	/**
	 *Write a public method removeFruitAt to remove a fruit at the given point from the grid, and from the list.
	 * @param point to remove fruit from
	 */
	

	/**
	 * This draw methods send the fruit to the screen when called.
	 */
	public void draw() {
		for (Fruit fruit : fruits) {
			fruit.draw();
		}
	}
}
