/**
 * @author <PUT YOUR USERNAME HERE>
 */
package edu.indiana.cs.c212;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Basic implementation of Grid interface.
 * Methods for getting rows, columns and tiles for the underlying grid.
 * 
 * In order to implement the getRow and getColumn methods, you will need to encapsulate the given code in 
 * a for loop. You might also want to take a look at the getCols and getRows methods and their associated
 * javadoc comments, in Grid.java.
 * 
 * For the getTiles method, you want to start at row 0 and increment from there. Your homework 1, should
 * help with this.
 * 
 * THIS IS THE ASSIGNMENT. YOUR CODE GOES IN THIS FILE!
 * 
 *
 */
public class GridImpl extends AbstractGrid {

	public GridImpl(Dimension dim){
		super(dim);
		gridArray = super.tiles;
	}
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Tile[] getRow(int rowNumber) {
		//FIXME
	    gridArray[rowNumber][col];

	}
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Tile[] getColumn(int colNumber) {
		//FIXME
	    gridArray[row][colNumber];
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public Iterator<Tile> getTiles() {
		
	   List<Tile> tiles = new ArrayList<Tile>();
	  //FIXME Please populate the ArrayList!
	  //Use the tiles ArrayList to store the intermediate data.
	  //You will be returning an iterator over the ArrayList.
	  //You can add things to an ArrayList by calling tiles.add(value);
	  //For more info check out: http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
	    return tiles.iterator();
	}
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Tile getTile(int row, int col) {
		//FIXME
	}
	/**
	 * {@inheritDoc}
	 */
    @Override
	public int getRows() {
		return dim.height;
	}
	/**
	 * {@inheritDoc}
	 */
    @Override
	public int getCols() {
		return dim.height;
	}
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Dimension getDim() {
		return dim;
	}
	Tile[][] gridArray;
}

