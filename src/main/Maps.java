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

	// WORLD SETTINGS
	final static int maxWorldCol = 28; 
	final static int maxWorldRow = 21; 
	final static int worldWidth = 48 * maxWorldCol; // world width in pixels (2400 pixels)
	final static int worldHeight = 48 * maxWorldRow; // world height in pixels (2400 pixels)
	
	public static int[][] tiles = new int[maxWorldRow][maxWorldCol];
	public static ArrayList<int[]> treePositions = new ArrayList<>();
	public static ArrayList<int[]> housePositions = new ArrayList<>();
	public static ArrayList<int[]> bedPositions = new ArrayList<>();
	public static ArrayList<int[]> houseWallPositions = new ArrayList<>();
	public static ArrayList<int[]> grassPositions = new ArrayList<>();
	public static ArrayList<int[]> waterPositions = new ArrayList<>();

	public String changeMap(Graphics2D g, int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");//intro map
		} else if (mapToChange == 2) {
			mapIntro("src/maps/mapHouse.txt");//house map
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");//open map
		}
		return "-1";//no map found

	}

	public String changeMap(int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");
		} else if (mapToChange == 2) {
			mapIntro("src/maps/mapHouse.txt");
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");
		}
		return "-1";

	}

	public void mapIntro(String filePath) {
		try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
			String lines;
			int row = 0;
			while ((lines = r.readLine()) != null) {
				lines = lines.trim(); // removes spaces after and before
				if (lines.isEmpty()) { // skips blank lines
					continue;
				}
				String[] val = lines.split("\\s+");
				for (int col = 0; col < val.length && col < tiles[row].length; col++) {
					tiles[row][col] = Integer.parseInt(val[col]);
				}
				row++;
			}
		} catch (IOException | NumberFormatException e) {
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
																				// are
				} else if (tiles[row][col] == 33) {
					grassPositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles[row][col] == 34) {
					waterPositions.add(new int[] { col * 48, row * 48 });
				}
			}
		}
	}
	
	public void camera(Graphics g2) {
		try {
			for (int worldRow = 0; worldRow < maxWorldRow; worldRow++) {
				for (int worldCol = 0; worldCol < maxWorldCol; worldCol++) {

					// where on the screen the tile will be drawn
					int screenX = worldCol * 48 - GamePanel.worldX;
					int screenY = worldRow * 48 - GamePanel.worldY;

					// only draws the tiles that are on the screen
					if (screenX + 48 > 0 && screenX < 768 && screenY + 48 > 0 && screenY < 576) {
						g2.drawImage(Tiles.tileImages[worldRow][worldCol], screenX, screenY, 48, 48, null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
