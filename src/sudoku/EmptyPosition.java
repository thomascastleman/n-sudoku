package sudoku;

public class EmptyPosition {
	
	public int value;				// current numeric value held by this position
	public int row;					// row of position
	public int col;					// column of position
	public int blockID;				// ID of the block to which this position belongs
	public int numConflicts;		// current number of conflicts posed by this position's value
	public int[] possibleValues;	// all possible values this position may hold, which do not violate the clue grid values
	
	public EmptyPosition() {
		
	}
	
	// calculate the number of conflicts given a value of this position
	public int calcConflictWithValue(int value) {
		return 0;
	}
	
	// find another possible value of this empty position with less conflicts
	public int getOtherValueOfLowestConflict() {
		return 0;
	}
	
	// update the conflict values of all other EmptyPositions affected by this position's change in value
	public void updateAffectedConflicts(int previousValue) {
		
	}

}
