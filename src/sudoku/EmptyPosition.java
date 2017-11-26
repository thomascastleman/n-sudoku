package sudoku;

import java.util.*;

public class EmptyPosition {
	
	public int value;				// current numeric value held by this position
	public int row;					// row of position
	public int col;					// column of position
	public int blockID;				// ID of the block to which this position belongs
	public int[] possibleValues;	// all possible values this position may hold, which do not violate the clue grid values
	public int numConflicts;		// current number of conflicts posed by this position's value
	
	public SudokuGrid parentGrid;	// reference to grid that contains this position
	
	public EmptyPosition(int _row, int _col, int _blockID, int _value, int[] _possibleValues) {
		this.row = _row;
		this.col = _col;
		this.blockID = _blockID;
		this.value = _value;
		this.possibleValues = _possibleValues;
	}
	
	// calculate the number of conflicts given a value of this position
	public int calcConflictWithValue(int value) {
		int conflict = 0;
		
		// for every set of positions that affect this position (same row, col, block)
		for (ArrayList<EmptyPosition> set : Arrays.asList(
				parentGrid.tentativeRowVals.get(this.row), 
				parentGrid.tentativeColVals.get(this.col), 
				parentGrid.tentativeBlockVals.get(this.blockID))) {
			
			// for every position in those sets
			for (EmptyPosition p : set) {
				// if same value but not this position
				if (p.value == value && (p.row != this.row || p.col != this.col)) {
					conflict++;
				}
			}
		}
		
		return conflict;
	}
	
	// find another possible value of this empty position with less conflicts, and update to it
	public void updateToLowestConflictValue() {
		int initConf = this.numConflicts;	// store initial conflict
		// for every possible value of this position
		for (int val : this.possibleValues) {
			int conf = this.calcConflictWithValue(val);	// calculate conflict
			
			// if less conflicts
			if (conf < this.numConflicts) {
				// update to that value
				this.value = val;
				this.numConflicts = conf;
			}
		}
		
		// update net conflict
		this.parentGrid.netConflict -= (initConf - this.numConflicts);
	}
	
	// update value and conflicts to random selection from possible
	public void updateToRandomPossibleValue() {
		int initConf = this.numConflicts;	// store initial conflict
		this.value = this.possibleValues[(int) (Math.random() * this.possibleValues.length)];	// randomly select a value
		this.numConflicts = this.calcConflictWithValue(this.value);	// calculate conflict of that value
		
		// update net conflict
		this.parentGrid.netConflict -= initConf;
		this.parentGrid.netConflict += this.numConflicts;
	}
	
	// update the conflict values of all other EmptyPositions affected by this position's change in value
	public void updateAffectedConflicts(int previousValue) {
		// if value actually changed
		if (previousValue != this.value) {
			// for every set of positions that affect this position (same row, col, block)
			for (ArrayList<EmptyPosition> set : Arrays.asList(
					parentGrid.tentativeRowVals.get(this.row), 
					parentGrid.tentativeColVals.get(this.col), 
					parentGrid.tentativeBlockVals.get(this.blockID))) {
				// for every position in those sets
				for (EmptyPosition p : set) {
					// if not this position
					if (p.row != this.row || p.col != this.col) {
						// if position had a conflict with previous value
						if (p.value == previousValue) {
							p.numConflicts--;		// remove that conflict
							this.parentGrid.netConflict--;	// maintain net conflict
						}
						// if now a conflict, update
						else if (p.value == this.value) {
							p.numConflicts++;		// add conflict
							this.parentGrid.netConflict++;	// maintain net conflict
						}
					}
				}
			}
		}
	}

}