package sudoku;

import java.util.*;

public class SudokuGrid {

	public int[][] grid;	// actual integer values on grid
	public int gridSize;	// grid is n^2 x n^2
	public int blockSize;	// blocks are n x n

	public int netConflict = 0; // sum of all conflicts
	
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
		
		
		
		
		// initialize all arraylists
		for (int i = 0; i < this.gridSize; i++) {
			this.clueRowVals.add(new ArrayList<Integer>());
			this.clueColVals.add(new ArrayList<Integer>());
			this.clueBlockVals.add(new ArrayList<Integer>());
			
			this.tentativeRowVals.add(new ArrayList<EmptyPosition>());
			this.tentativeColVals.add(new ArrayList<EmptyPosition>());
			this.tentativeBlockVals.add(new ArrayList<EmptyPosition>());
		}	
	}
	
	// returns the block ID this position fits into 
	public int getBlockIDFromPosition(int row, int col) {
		
	}
	
	// get the empty position with the most conflicts (linear search)
	public EmptyPosition getMaxConflictPosition() {
		EmptyPosition max = allEmptyPositions.get(0);
		
		for (EmptyPosition p : allEmptyPositions) {
			if (p.numConflicts > max.numConflicts) {
				max = p;
			}
		}
		
		return max;
	}
	
	// serialize all given clues into row, col, and block collections
	public void recordClues() {
		// for each row
		for (int r = 0; r < this.grid.length; r++) {
			// for each column
			for (int c = 0; c < this.grid[r].length; c++) {
				int num = this.grid[r][c];
				// if clue value
				if (num != 0) {
					this.clueRowVals.get(r).add(num);
					this.clueColVals.get(c).add(num);
					this.clueBlockVals.get(this.getBlockIDFromPosition(r, c)).add(num);
				}
			}
		}
	}
	
	// initialize all EmptyPositions with random but acceptable values, calculate initial conflicts
	public void initEmptyPositions() {
		
		// for each row
		for (int r = 0; r < this.grid.length; r++) {
			// for each column
			for (int c = 0; c < this.grid[r].length; c++) {
				int num = this.grid[r][c];
				// if not a known position
				if (num == 0) {
					
					int blockID = this.getBlockIDFromPosition(r, c);
					
					// add all possible conflicting values into one set
					Set<Integer> R = new HashSet<Integer>(this.clueRowVals.get(r));
					Set<Integer> C = new HashSet<Integer>(this.clueColVals.get(c));
					Set<Integer> B = new HashSet<Integer>(this.clueBlockVals.get(blockID));
					Set<Integer> all = new HashSet<Integer>();
					all.addAll(R);
					all.addAll(C);
					all.addAll(B);
					// get set of all digits in range that are NOT included in these conflicting values
					ArrayList<Integer> possible = new ArrayList<Integer>();
					for (int i = 1; i <= this.gridSize; i++) {
						possible.add(i);
					}
					possible.removeAll(all);
					
					// convert to primitive int[] and choose random initial value
					int[] possibleIntArray = Main.convertToIntPrimitive(possible);
					int value = possibleIntArray[(int) Math.random() * possibleIntArray.length];
					
					// initialize empty position
					EmptyPosition p = new EmptyPosition(r, c, blockID, value, possibleIntArray);
					p.parentGrid = this;
					
					// add to structures
					this.tentativeRowVals.get(r).add(p);
					this.tentativeColVals.get(c).add(p);
					this.tentativeBlockVals.get(blockID).add(p);
					this.allEmptyPositions.add(p);
				}
			}
		}
		
		this.netConflict = 0;
		// calculate all conflicts, sum to net conflict
		for (EmptyPosition p : allEmptyPositions) {
			p.numConflicts = p.calcConflictWithValue(p.value);
			this.netConflict += p.numConflicts;
		}
		
		
		
	}
	
	// solve grid and return filled int[][] array
	public int[][] solve() {
		
		this.recordClues();				// locate and keep track of given values
		this.initEmptyPositions();		// initialize all empty positions
		
		double temperature = 100.0;
		double rate = 0.99;
		
		// NOW DO SIMULATED ANNEALING
		
		while (netConflict > 0) {
			
			
			EmptyPosition maxConflict = this.getMaxConflictPosition();
			int previousValue = maxConflict.value;
			
			if (Math.random() * 100 < temperature) {
				maxConflict.updateToRandomPossibleValue();
			} else {
				maxConflict.updateToLowestConflictValue();
			}
			
			maxConflict.updateAffectedConflicts(previousValue);
			
			temperature *= rate;
			
		}
		
		// NOW FORMAT BACK INTO INT[][] AND RETURN
	}	
}







































