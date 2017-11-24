package sudoku;

public class EmptyPosition {
	
	public int value;				// current numeric value held by this position
	public int blockID;				// ID of the block to which this position belongs
	public int[] possibleValues;	// all possible values this position may hold, which do not violate the clue grid values
	public int numConflicts;		// current number of conflicts posed by this position's value
	
	public EmptyPosition() {
		
	}
	
	// calculate the number of conflicts given a value of this position
	public int calcConflictWithValue(int value) {
		return 0;
	}

}
