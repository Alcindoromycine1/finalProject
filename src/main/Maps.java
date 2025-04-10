package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Maps {

	public static int[][] tiles = new int[12][16];
	public static ArrayList<int[]> treePositions = new ArrayList<>();
	public static ArrayList<int[]> housePositions = new ArrayList<>();
	public static ArrayList<int[]> bedPositions = new ArrayList<>();
	public static ArrayList<int[]> houseWallPositions = new ArrayList<>();
	public static ArrayList<int[]> grassPositions = new ArrayList<>();
	public static ArrayList<int[]> waterPositions = new ArrayList<>();
	

	public void mapIntro() {

		try {
			try {
				// Storing the map from the file mapIntro.txt into the 2D array tiles
				BufferedReader r = new BufferedReader(new FileReader("src/maps/mapHouse.txt"));// opens the file
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

	public void findIntroHouse() throws IOException {

		BufferedReader r = new BufferedReader(new FileReader("src/maps/mapIntro.txt"));

	}

	public void findTrees() throws IOException {

		BufferedReader r = new BufferedReader(new FileReader("src/maps/mapIntro.txt"));
		String lines = "";
		int numberOfLines = 0;
		// Count the number of lines in the file
		// Iterate through the 2D array to find occurrences of 1
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[row].length; col++) {
				if (tiles[row][col] == 1) {
					treePositions.add(new int[] { col * 48, row * 48 });// stores the location of where the trees are
				} else if (tiles[row][col] == 3 || tiles[row][col] == 4 || tiles[row][col] == 5 || tiles[row][col] == 6
						|| tiles[row][col] == 7 || tiles[row][col] == 8 || tiles[row][col] == 9 || tiles[row][col] == 10
						|| tiles[row][col] == 11 || tiles[row][col] == 12 || tiles[row][col] == 13
						|| tiles[row][col] == 14 || tiles[row][col] == 15 || tiles[row][col] == 16
						|| tiles[row][col] == 17) {
					housePositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles[row][col] == 21 || tiles[row][col] == 22 || tiles[row][col] == 19
						|| tiles[row][col] == 20) {
					bedPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the beds are
				} else if (tiles[row][col] == 23 || tiles[row][col] == 24 || tiles[row][col] == 25
						|| tiles[row][col] == 26 || tiles[row][col] == 27 || tiles[row][col] == 28
						|| tiles[row][col] == 29 || tiles[row][col] == 30 || tiles[row][col] == 31
						|| tiles[row][col] == 32) {
					houseWallPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the walls
																				// are // are
				} else if (tiles[row][col] == 33) {
					grassPositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles[row][col] == 34) {
					waterPositions.add(new int[] { col * 48, row * 48 });
				}
			}
		}
	}
}
