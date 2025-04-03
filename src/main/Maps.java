package main;

/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
import java.io.*;
import java.util.ArrayList;

public class Maps {

	public static int[][] tiles = new int[12][16];

	public void mapIntro() {

		try {
			try {
				// Storing the map from the file mapIntro.txt into the 2D array tiles
				BufferedReader r = new BufferedReader(new FileReader("src/maps/mapIntro.txt"));// opens the file
				String lines = "";// reads the information in the file
				int row = 0;
				while ((lines = r.readLine()) != null) {// while the file is not empty

					String[] val = lines.split(" ");// seperates the numbers by spaces and stores each individual number
													// into the array as a string

					// Converts the String [] val into an Integer and stores it in its correct
					// position
					for (int col = 0; col < val.length && col < tiles[row].length; col++) {
						tiles[row][col] = Integer.parseInt(val[col]); // stores the numbers into the 2D array
					}
					row++;// go to the next row

				}
				r.close();// closes the file
			} catch (NumberFormatException e) {

				e.printStackTrace();

			}
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void findTrees() throws IOException {

		BufferedReader r = new BufferedReader(new FileReader("src/maps/mapIntro.txt"));
		String lines = "";
		int numberOfLines = 0;

		ArrayList<int[]> treePositions = new ArrayList<>();
		// Count the number of lines in the file
		// Iterate through the 2D array to find occurrences of 1
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[row].length; col++) {
				if (tiles[row][col] == 1) {
					treePositions.add(new int[] { row, col });// stores the location of where the ones are
				}
			}
		}
		
		for(int[] line : treePositions) {
			for (int i = 0; i < line.length; i++) {
				System.out.print(line[i] + ", ");
				System.out.println(";");
			}
		}
		
	}
}
