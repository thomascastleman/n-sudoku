package sudoku;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		int[][] grid = {
				{0, 0, 2, 0},
				{2, 0, 0, 4},
				{1, 0, 0, 3},
				{0, 3, 0, 0}
		};
		
		SudokuGrid s = new SudokuGrid(3, null);
		
		// s.recordClues();
		
		
	}
	
	// convert a list of integers into an int[] primitive array
	public static int[] convertToIntPrimitive(List<Integer> list) {
		int[] array = new int[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i).intValue();
		}
		return array;
	}

}
