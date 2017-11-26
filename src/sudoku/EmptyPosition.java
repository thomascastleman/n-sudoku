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
		
		for (ArrayList<EmptyPosition> set : Arrays.asList(
				parentGrid.tentativeRowVals.get(this.row), 
				parentGrid.tentativeColVals.get(this.col), 
				parentGrid.tentativeBlockVals.get(this.blockID))) {
			
			for (EmptyPosition p : set) {
				// if same value but not this position
				if (p.value == value && (p.row != this.row || p.col != this.col)) {
					conflict++;
				}
			}
		}
		
		return conflict;
	}
	
	// find another possible value of this empty position with less conflicts
	public void updateToLowestConflictValue() {
		
		int initConf = this.numConflicts;	// store initial conflict
		
		System.out.println("\nValue " + this.value + " has initial conf " + initConf);
		
		System.out.println("Num possible values: " + this.possibleValues.length);
		for (int val : this.possibleValues) {
			int conf = this.calcConflictWithValue(val);
			System.out.println("Testing against " + val + " with conf " + conf);
			
			if (conf < this.numConflicts) {
				
				System.out.println("CHANGING VALUE FROM " + this.value + " TO " + val);
				
				
				this.value = val;
				this.numConflicts = conf;
			}
		}
		
		
		
		
		// debug
		System.out.println("Subtracting " + (initConf - this.numConflicts) + " from netconflict which is " + this.parentGrid.netConflict);
		
		
		// update net conflicts
		this.parentGrid.netConflict -= (initConf - this.numConflicts);
	}
	
	// update value and conflicts to random selection from possible
	public void updateToRandomPossibleValue() {
		int initConf = this.numConflicts;
		this.value = this.possibleValues[(int) (Math.random() * this.possibleValues.length)];
		this.numConflicts = this.calcConflictWithValue(this.value);
		
		// update net conflict
		this.parentGrid.netConflict -= initConf;
		this.parentGrid.netConflict += this.numConflicts;
		
		System.out.println("RANDOM CHOICE OUT OF " + this.possibleValues.length + " CHOICES: Net conflict now = " + this.parentGrid.netConflict);
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
						this.parentGrid.netConflict--;
					}
					// if now a conflict, update
					else if (p.value == this.value) {
						p.numConflicts++;
						this.parentGrid.netConflict++;
					}
				}
			}
		}
	}

}
