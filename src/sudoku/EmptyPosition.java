package sudoku;

import java.util.*;

public class EmptyPosition {
	
	public int value;				// current numeric value held by this position
	public int row;					// row of position
	public int col;					// column of position
	public int blockID;				// ID of the block to which this position belongs
	public int numConflicts;		// current number of conflicts posed by this position's value
	public int[] possibleValues;	// all possible values this position may hold, which do not violate the clue grid values
	
	public SudokuGrid parentGrid;	// reference to grid that contains this position
	
	public EmptyPosition() {
		
	}
	
	// calculate the number of conflicts given a value of this position
	public int calcConflictWithValue(int value) {
		int conflict = 0;
		
		for (ArrayList<EmptyPosition> set : Arrays.asList(
				parentGrid.tentativeRowVals.get(this.row), 
				parentGrid.tentativeColVals.get(this.col), 
				parentGrid.tentativeBlockVals.get(this.blockID))) {
			
			for (EmptyPosition p : set) {
				if (p.value == value) {
					conflict++;
				}
			}
		}
		
		return conflict;
	}
	
	// find another possible value of this empty position with less conflicts
	public void updateToLowestConflictValue() {
		for (int val : this.possibleValues) {
			int conf = this.calcConflictWithValue(val);
			if (conf < this.numConflicts) {
				this.value = val;
				this.numConflicts = conf;
			}
		}
	}
	
	// update value and conflicts to random selection from possible
	public void updateToRandomPossibleValue() {
		this.value = this.possibleValues[(int) (Math.random() * this.possibleValues.length)];
		this.numConflicts = this.calcConflictWithValue(this.value);
	}
	
	// update the conflict values of all other EmptyPositions affected by this position's change in value
	public void updateAffectedConflicts(int previousValue) {
		// if value actually changed
		if (previousValue != this.value) {
		
			for (ArrayList<EmptyPosition> set : Arrays.asList(
					parentGrid.tentativeRowVals.get(this.row), 
					parentGrid.tentativeColVals.get(this.col), 
					parentGrid.tentativeBlockVals.get(this.blockID))) {
				
				for (EmptyPosition p : set) {
					// if position had a conflict with previous value
					if (p.value == previousValue) {
						p.numConflicts--;		// remove that conflict
					}
				}
			}
		}
	}

}
