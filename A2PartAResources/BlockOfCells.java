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
    
    private Cell[][] cellBlock;
    
    public BlockOfCells() {   
        cellBlock = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLS]; 
        createBlockOfCells(cellBlock);
        
    }
    
    private void createBlockOfCells(Cell[][] cellBlock) {
        int x = GAME_AREA.x;
        int y = GAME_AREA.y;
        
        for(int i = 0; i < cellBlock.length; i++) {
            x = GAME_AREA.x;
            for(int j = 0; j < cellBlock.length; j++) {
                cellBlock[i][j] = new Cell(x, y);
                x = x + CELL_SIZE;
            }
            
            y = y + CELL_SIZE;   
        }  
    }
//-------------------------------------------------------
// Methods to do with the colour index of each cell
//-------------------------------------------------------  
    public int getCellColourIndex(int row, int col) {
        return -1; //may need to be changed
    }
    
    public void setCellColourIndex(int row, int col, int colourIndex) {
        
    }
    
    private void resetCellHasBeenVisited() {  
        
    }
//-------------------------------------------------------
// returns the number of cells connected 
// to the top left cell (position 0, 0)
//-------------------------------------------------------   
    public int getNumberOfConnectedCells() {  
        int colourIndex = getCellColourIndex(0, 0);
        resetCellHasBeenVisited();
        return getNumberOfCellsInUserBlock(0, 0, colourIndex); 
    }
    
    private int getNumberOfCellsInUserBlock(int row, int col, int colourIndex) {  
        
        return 0;  //may need to be changed
    } 
//-------------------------------------------------------
// updates the colour of all cells connected 
// to the top left cell (position 0, 0), i.e.,
// have the same colour index.
//-------------------------------------------------------   
    public void updateConnectedCells(int updateColourIndex) {  
        int colourToChangeIndex = getCellColourIndex(0, 0);
        resetCellHasBeenVisited();
        updateUserAreaColours(0, 0, updateColourIndex, colourToChangeIndex); 
    }
    
    private void updateUserAreaColours(int row, int col, int updateColourIndex, int colourToChangeIndex) {  
        
    }
//-------------------------------------------------------
// returns a String with all the colour indexes
// concatenated, row by row
//------------------------------------------------------- 
    public String colourIndexesToString() {
        String info = "";
        
        
        return info;
    }
//-------------------------------------------------------
// Draw the 2D array of coloured cells
//-------------------------------------------------------
    public void drawCells(Graphics g) { 
        
    }
}
