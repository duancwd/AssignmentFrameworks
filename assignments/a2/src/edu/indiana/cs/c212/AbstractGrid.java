package edu.indiana.cs.c212;
import java.awt.Dimension;

public abstract class AbstractGrid implements Grid {

	public AbstractGrid(Dimension dim){

		int rows = (int) dim.getHeight();
		int cols = (int) dim.getWidth();

		this.dim = dim;
		//Create the structure to hold the grid
		tiles = new Tile[rows][cols];
		//Seed all of the tiles into the grid holder
		for(int row  = 0; row < rows; row++){
			for(int col = 0; col  < cols; col++) {
				tiles[row][col] = new Tile();
			}
		}
	}

	protected final Dimension dim;
	protected final Tile[][] tiles;
}
