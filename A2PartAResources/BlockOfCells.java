/*
 * Purpose: creates 
 * a Block of Cell objects for
 * the ColourMe game
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
 */

import java.awt.*;

public class BlockOfCells {
	public static final int NUMBER_OF_ROWS = A2Constants.NUMBER_OF_ROWS;
	public static final int NUMBER_OF_COLS = A2Constants.NUMBER_OF_COLS;
	public static final int CELL_SIZE = A2Constants.CELL_SIZE;
	public static final Rectangle GAME_AREA = A2Constants.GAME_AREA;
	public static final Color[] COLOURS = A2Constants.COLOURS;

	private Cell[][] cellBlock;

	public BlockOfCells() {
		cellBlock = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLS];
		createBlockOfCells(cellBlock);

	}

	private void createBlockOfCells(Cell[][] cellBlock) {
		int x = GAME_AREA.x;
		int y = GAME_AREA.y;

		// take care of the row and the colume here
		for (int i = 0; i < cellBlock.length; i++) {
			x = GAME_AREA.x;
			for (int j = 0; j < cellBlock.length; j++) {
				cellBlock[i][j] = new Cell(x, y); // i row, j clu
				x = x + CELL_SIZE;
			}

			y = y + CELL_SIZE;
		}
	}

	// -------------------------------------------------------
	// Methods to do with the colour index of each cell
	// -------------------------------------------------------
	// -------------------------------------------------------
	// Stage 2 (4 marks) Methods to do with the colour index of
	// each cell
	// -------------------------------------------------------

	public int getCellColourIndex(int row, int col) {
		// TODO not sure about the order
		return cellBlock[row][col].getColourIndex(); // may need to be changed
	}

	public void setCellColourIndex(int row, int col, int colourIndex) {
		cellBlock[row][col].setColourIndex(colourIndex);
	}

	// -------------------------------------------------------
	// Stage 3 (4 marks) Reset hasBeenVisited for all cells
	// -------------------------------------------------------
	private void resetCellHasBeenVisited() {
		for (int i = 0; i < cellBlock.length; i++) {
			for (int j = 0; j < cellBlock[i].length; j++) {
				cellBlock[i][j].setHasBeenVisited(false);
			}
		}
	}

	// -------------------------------------------------------
	// Stage 5 (10 marks) Recursive method which returns the number of
	// cells connected (i.e., with the same colour index as the
	// top left cell (0, 0))
	// -------------------------------------------------------
	// -------------------------------------------------------
	// returns the number of cells connected
	// to the top left cell (position 0, 0)
	// -------------------------------------------------------

	/*
	 * Stage 5 getNumberOfCellsInUserBlock() – 10 marks This instance method is
	 * called after the colour in the cells has been changed (i.e., after the
	 * user has pressed one of the Colour buttons). This method returns the
	 * number of cells which have the same colour as the top left cell (0, 0)
	 * and are connected to the top left cell. Note that each cell has 4
	 * adjacent (i.e., connected) neighbours (except for the border cells). Note
	 * that each cell stores the index of its colour (index of one of the
	 * colours in the COLOURS array). To complete this method you should use a
	 * recursive algorithm. Once you have completed stage 5, you should see the
	 * current number of connected cells in the JPanel on the right of the grid
	 * of cells. This number should change depending on how many connected cells
	 * there are.
	 */

	// why that we must use recursive algorithm since we can use for loop in a
	// much simpler way
	public int getNumberOfConnectedCells() {
		int colourIndex = getCellColourIndex(0, 0);
		resetCellHasBeenVisited();
		return getNumberOfCellsInUserBlock(0, 0, colourIndex);
	}

	private int getNumberOfCellsInUserBlock(int row, int col, int colourIndex) {
		if (cellBlock[row][col].getHasBeenVisited())
			return 0;
		cellBlock[row][col].setHasBeenVisited(true);
		int rowCounter = 0, colCounter = 0;
		if (cellBlock[row][col].getColourIndex() == colourIndex) {
			if (row < NUMBER_OF_ROWS - 1)
				rowCounter = getNumberOfCellsInUserBlock(row + 1, col,
						colourIndex);
			if (col < NUMBER_OF_COLS - 1)
				colCounter = getNumberOfCellsInUserBlock(row, col + 1,
						colourIndex);
			return rowCounter + colCounter + 1;
		}// may need to be changed
		return 0;
	}

	// -------------------------------------------------------
	// updates the colour of all cells connected
	// to the top left cell (position 0, 0), i.e.,
	// have the same colour index.
	// -------------------------------------------------------
	public void updateConnectedCells(int updateColourIndex) {
		int colourToChangeIndex = getCellColourIndex(0, 0);
		resetCellHasBeenVisited();
		updateUserAreaColours(0, 0, updateColourIndex, colourToChangeIndex);
	}

	/*
	 * Stage 4 updateUserAreaColours() – 10 marks
	 * 
	 * This instance method is called whenever the user chooses a new colour by
	 * pressing one of the Colour buttons. This method should update the colour
	 * of all the cells connected to the top left cell (0, 0) to the new colour
	 * selected by the user. Note that each cell has 4 adjacent (i.e.,
	 * connected) neighbours (except for the border cells). Note that each cell
	 * stores the index of its fill colour (index of one of the colours in the
	 * COLOURS array). To complete this method you should use a recursive
	 * algorithm. Once you have completed stage 4, the user should be able to
	 * fill the grid with colour by pressing the Colour buttons.
	 */
	private void updateUserAreaColours(int row, int col, int updateColourIndex,
			int colourToChangeIndex) {
		if (cellBlock[row][col].getColourIndex() == colourToChangeIndex) {
			if (row < NUMBER_OF_ROWS - 1)
				updateUserAreaColours(row + 1, col, updateColourIndex,
						colourToChangeIndex);
			// if(row > 0) updateUserAreaColours(row-1, col, updateColourIndex,
			// colourToChangeIndex);
			if (col < NUMBER_OF_COLS - 1)
				updateUserAreaColours(row, col + 1, updateColourIndex,
						colourToChangeIndex);
			// if(col > 0) updateUserAreaColours(row, col-1, updateColourIndex,
			// colourToChangeIndex);
			cellBlock[row][col].setColourIndex(updateColourIndex);
		}
	}

	// -------------------------------------------------------
	// returns a String with all the colour indexes
	// concatenated, row by row
	// -------------------------------------------------------
	public String colourIndexesToString() {
		String info = "";

		return info;
	}

	// -------------------------------------------------------
	// Draw the 2D array of coloured cells
	// -------------------------------------------------------
	public void drawCells(Graphics g) {
		// g.fillRect(x, y, width, height)
		for (int i = 0; i < cellBlock.length; i++) {
			for (int j = 0; j < cellBlock[i].length; j++) {
				g.setColor(COLOURS[cellBlock[i][j].getColourIndex()]);
				g.fillRect(cellBlock[i][j].getArea().x,
						cellBlock[i][j].getArea().y,
						cellBlock[i][j].getArea().width,
						cellBlock[i][j].getArea().height);
			}
		}
	}
}
