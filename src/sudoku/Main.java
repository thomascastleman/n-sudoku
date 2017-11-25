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
		
		SudokuGrid s = new SudokuGrid(2, grid);
		
		s.initClues();
		
		s.initEmptyPositions();
		
		System.out.println("INITIAL NET CONFLICT: " + s.netConflict);
		
		s.solve();
		
//		System.out.println("NET CONFLICT: " + s.netConflict);
//		
//		ArrayList<ArrayList<EmptyPosition>> set = s.tentativeRowVals;
//		
//		for (int i = 0; i < set.size(); i++) {
//			System.out.println("ROW " + i + ": ");
//			for (int j = 0; j < set.get(i).size(); j++) {
//				EmptyPosition p = set.get(i).get(j);
//				System.out.print(p + ", ");
//				
//				System.out.println("value: " + p.value + ", conflict: " + p.numConflicts);
//
//				
//				
//			}
//			System.out.println("");
//		}
		
		
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
