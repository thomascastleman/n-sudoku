package sudoku;

import java.util.*;

public class SudokuGrid {

	public int[][] grid;	// actual integer values on grid
	public int gridSize;	// grid is n^2 x n^2
	public int blockSize;	// blocks are n x n
	
	public int[][] blockRepresentation;

	public int netConflict = 0; // sum of all conflicts
	
	public ArrayList<EmptyPosition> allEmptyPositions = new ArrayList<EmptyPosition>();	// manage empty positions
	
	// structures for keeping track of clue (given) values per row, col, block
	public ArrayList<ArrayList<Integer>> clueRowVals = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> clueColVals = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> clueBlockVals = new ArrayList<ArrayList<Integer>>();
	
	// structures for keeping track of tentative (guess) values per row, col, block
	public ArrayList<ArrayList<EmptyPosition>> tentativeRowVals = new ArrayList<ArrayList<EmptyPosition>>();
	public ArrayList<ArrayList<EmptyPosition>> tentativeColVals = new ArrayList<ArrayList<EmptyPosition>>();
	public ArrayList<ArrayList<EmptyPosition>> tentativeBlockVals = new ArrayList<ArrayList<EmptyPosition>>();
	
	public SudokuGrid(int n, int[][] _grid) {
		
		this.blockSize = n;
		this.gridSize = n * n;
		
		this.blockRepresentation = new int[n][n];
		int i = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				this.blockRepresentation[r][c] = i;
				i++;
			}
		}
		
		if (_grid != null) {
			if (this.gridSize == _grid.length) {
				this.grid = _grid;
			} else {
				System.out.println("INCOMPATIBLE N AND GRID SIZE (SudokuGrid constructor)");
			}
		} else {
			// otherwise construct empty grid of proper size
			this.grid = new int[this.gridSize][this.gridSize];
		}
		
		// initialize all arraylists
		for (i = 0; i < this.gridSize; i++) {
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
		return this.blockRepresentation[row / this.blockSize][col / this.blockSize];
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
	public void initClues() {
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
		
		this.initClues();				// locate and keep track of given values
		this.initEmptyPositions();		// initialize all empty positions
		
		double temperature = 100.0;
		double rate = 0.99;
		
		
		// debug
		int debug = 0;
		
		while (netConflict > 0) {
			
			System.out.println("\nIteration " + debug + " Temp: " + temperature + ", net conflict: " + this.netConflict);
			
			
//			EmptyPosition maxConflict = this.getMaxConflictPosition();
//			int previousValue = maxConflict.value;
//			
//			
//			System.out.println("MAX CONFLICT VALUE AT (" + maxConflict.row + ", " + maxConflict.col + ")");
//			
//			if (Math.random() * 100 < temperature) {
//				maxConflict.updateToRandomPossibleValue();
//			} else {
//				maxConflict.updateToLowestConflictValue();
//			}
//			
//			System.out.println("After single update, net is " + this.netConflict);
//			
//			maxConflict.updateAffectedConflicts(previousValue);
			
			
			EmptyPosition positionOfChange;
			
			if (Math.random() * 100 < temperature) {
				System.out.println("TOTAL RANDOMIZATION");
				
				positionOfChange = this.allEmptyPositions.get((int) (Math.random() * this.allEmptyPositions.size()));
				int previousValue = positionOfChange.value;
				
				positionOfChange.updateToRandomPossibleValue();
				positionOfChange.updateAffectedConflicts(previousValue);
			} else {
				positionOfChange = this.getMaxConflictPosition();
				
				int previousValue = positionOfChange.value;
				
				positionOfChange.updateToLowestConflictValue();
				positionOfChange.updateAffectedConflicts(previousValue);
			}
			
			System.out.println("CHOOSING (" + positionOfChange.row + ", " + positionOfChange.col + ")");
			
			
			
			
			
			
			
			temperature *= rate;
			
			if (temperature < 0.01) {
				temperature = 100.0;
				System.out.println("TEMP RESET");
			}
			
//			// debug
//			if (debug > 1000) {
//				System.out.println("\n\nFAILURE");
//				break;
//			}
			
			debug++;
			
		}
		
		if (this.netConflict == 0) {
			System.out.println("\n\n\n\nSOLUTION FOUND: NET CONFLICT = " + this.netConflict);
		} else {
			System.out.println("\n\n\n\nFailure with net conflict of " + this.netConflict);
		}
		
		// update actual grid values to reflect solution
		for (EmptyPosition p : this.allEmptyPositions) {
			this.grid[p.row][p.col] = p.value;
		}
		
		return this.grid;
	}

}







































