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
				{5, 3, 0, 0, 7, 0, 0, 0, 0},
				{6, 0, 0, 1, 9, 5, 0, 0, 0},
				{0, 9, 8, 0, 0, 0, 0, 6, 0},
				{8, 0, 0, 0, 6, 0, 0, 0, 3},
				{4, 0, 0, 8, 0, 3, 0, 0, 1},
				{7, 0, 0, 0, 2, 0, 0, 0, 6},
				{0, 6, 0, 0, 0, 0, 2, 8, 0},
				{0, 0, 0, 4, 1, 9, 0, 0, 5},
				{0, 0, 0, 0, 8, 0, 0, 7, 9}
		};
		
		logGrid(grid);
		System.out.println("");
		
		SudokuGrid s = new SudokuGrid(3, grid);
		
		int[][] solution = s.solve(false);
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
