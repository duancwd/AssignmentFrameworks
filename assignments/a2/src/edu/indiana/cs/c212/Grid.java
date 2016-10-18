package edu.indiana.cs.c212;

import java.awt.Dimension;
import java.util.Iterator;

public interface Grid {
	/**
	 * 
	 * @param rowNumber 
	 * @return An array of the tiles of the row number that was
	 * passed in.
	 */
	public abstract Tile[] getRow(int rowNumber);
	/**
	 * 
	 * @param colNumber
	 * @return An array of all the tiles of the column number that was
	 *  passed in.
	 */
	public abstract Tile[] getColumn(int colNumber);

	/**
	 * 
	 * @return An iterator over all the Tiles in the grid, starting with
	 * row 0 col 0, continuing to row 0 col 1, and so on and so forth.
	 */
	public abstract Iterator<Tile> getTiles();
	/**
	 * 
	 * @param row 
	 * @param col
	 * @return The tile at the given row-col coordinates.
	 */
	public abstract Tile getTile(int row, int col);
	/**
	 * 
	 * @return The total number of rows in the grid.
	 */
	public abstract int getRows();
	/**
	 * 
	 * @return Total number of columns in the grid.
	 */
	public abstract int getCols();
	
	/**
	 * 
	 * @return The dimension object specifying grid size
	 */
	public abstract Dimension getDim(); 


}
