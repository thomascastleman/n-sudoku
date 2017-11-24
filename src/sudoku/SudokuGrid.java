package sudoku;

import java.util.*;

public class SudokuGrid {

	public int[][] grid;	// actual integer values on grid
	public int gridSize;	// grid is n^2 x n^2
	public int blockSize;	// blocks are n x n
	
	public ArrayList<EmptyPosition> allEmptyPositions;	// manage empty positions
	
	// structures for keeping track of clue (given) values per row, col, block
	public ArrayList<ArrayList<Integer>> clueRowVals = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> clueColVals = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> clueBlockVals = new ArrayList<ArrayList<Integer>>();
	
	// structures for keeping track of tentative (guess) values per row, col, block
	public ArrayList<ArrayList<EmptyPosition>> tentativeRowVals = new ArrayList<ArrayList<EmptyPosition>>();
	public ArrayList<ArrayList<EmptyPosition>> tentativeColVals = new ArrayList<ArrayList<EmptyPosition>>();
	public ArrayList<ArrayList<EmptyPosition>> tentativeBlockVals = new ArrayList<ArrayList<EmptyPosition>>();
	
	public SudokuGrid() {
		
	}
	
	// get the emptyposition with the most conflicts
	public EmptyPosition getMaxConflictPosition() {
		return new EmptyPosition();
	}
	
	// serialize all given clues into row, col, and block collections
	public void recordClues() {
		
	}
	
	// initialize all EmptyPositions with random but acceptable values, calculate initial conflicts
	public void initEmptyPositionsAndPQ() {
		
	}
	
	// solve grid and return filled int[][] array
	public int[][] solve() {
		return new int[0][0];
	}
	
}
