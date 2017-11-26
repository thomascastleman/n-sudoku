package sudoku;

import java.util.*;

public class Main {

	public static void main(String[] args) {
//		int[][] grid = {
//				{0, 0, 2, 0},
//				{2, 0, 0, 4},
//				{1, 0, 0, 3},
//				{0, 3, 0, 0}
//		};
		
		int[][] grid = {
				{0, 0, 0, 0, 4, 5, 0, 0, 8},
				{8, 5, 0, 0, 6, 0, 3, 7, 0},
				{0, 6, 0, 0, 0, 7, 1, 2, 0},
				{7, 1, 0, 8, 0, 0, 0, 0, 0},
				{0, 0, 4, 5, 0, 1, 8, 0, 0},
				{0, 0, 0, 0, 0, 4, 0, 1, 2},
				{0, 7, 8, 4, 0, 0, 0, 3, 0},
				{0, 2, 1, 0, 3, 0, 0, 4, 6},
				{3, 0, 0, 9, 1, 0, 0, 0, 0}
		};
		
		logGrid(grid);
		System.out.println("");
		
		SudokuGrid s = new SudokuGrid(3, grid);
		
		int[][] solution = s.solve();
		logGrid(solution);
	}
	
	public static void logGrid(int[][] grid) {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				System.out.print(grid[r][c] + " ");
			}
			System.out.println("");
		}
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
