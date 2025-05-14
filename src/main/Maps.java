package main;

import Horror.Jumpscare;
import java.awt.Color;
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
	final static int maxWorldCol = 146;
	final static int maxWorldRow = 33;
	final static int worldWidth = 48 * maxWorldCol; // world width in pixels (2400 pixels)
	final static int worldHeight = 48 * maxWorldRow; // world height in pixels (2400 pixels)

	public static int[][] tiles = new int[maxWorldRow][maxWorldCol];
	public static ArrayList<int[]> treePositions = new ArrayList<>();
	public static ArrayList<int[]> housePositions = new ArrayList<>();
	public static ArrayList<int[]> bedPositions = new ArrayList<>();
	public static ArrayList<int[]> houseWallPositions = new ArrayList<>();
	public static ArrayList<int[]> grassPositions = new ArrayList<>();
	public static ArrayList<int[]> waterPositions = new ArrayList<>();

	// Player p = new Player();
	// GamePanel gp = new GamePanel();
	Tiles t = new Tiles();

	public String changeMap(Graphics2D g, int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");// intro map
		} else if (mapToChange == 2) {

			mapIntro("src/maps/mapHouse.txt");// house map
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");// open map
		}
		return "-1";// no map found

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

	// change scene variables
	int fadeValue = 0;
	int stepCount = 0;

	public void fading(Graphics2D g2, Tiles t, GamePanel m, int newMap, int originalMap) throws IOException {

		if (stepCount == 0) {// fade out
			Color fading = new Color(0, 0, 0, fadeValue);
			// System.out.println("fade out: " + fadeValue);
			g2.setColor(fading);
			g2.fillRect(0, 0, m.WIDTH, m.HEIGHT);
			fadeValue += 2;
			if (fadeValue >= 255) {
				fadeValue = 255;
				stepCount = 1;
			}
		}

		else if (stepCount == 1) {// change map
			try {

				// Clear old map data
				treePositions.clear();
				housePositions.clear();
				bedPositions.clear();
				houseWallPositions.clear();
				grassPositions.clear();
				waterPositions.clear();

				// Clear old tile images
				for (int i = 0; i < Maps.maxWorldRow; i++) {
					for (int j = 0; j < Maps.maxWorldCol; j++) {
						Tiles.tileImages[i][j] = null;
					}
				}
				Tiles.tileImages = new BufferedImage[Maps.maxWorldRow][Maps.maxWorldCol]; // Create a new array

				g2.fillRect(0, 0, m.WIDTH + 20000, m.HEIGHT + 20000); // Clear the screen
				// Change to the new map
				if (goOut) {
					changeMap(newMap);
				} else if (!goOut) {
					changeMap(originalMap);
				}
				// Load the new map's tiles
				// Reinitialize the new map's data
				Tiles.tileCreating();
				findIntroHouse();
				findTrees();
				GamePanel.worldX = 288;
				GamePanel.worldY = 216;
			} catch (IOException e) {
				e.printStackTrace();
			}

			stepCount = 2;
		}

		else if (stepCount == 2) {// fade in
			// System.out.println("fade in: " + fadeValue);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, m.WIDTH, m.HEIGHT);
			fadeValue -= 2;
			if (fadeValue <= 0) {
				fading = false;
				Player.disableCharacterMovement();
				stepCount = -1;
				fadeValue = 0;

			}
		}
	}

	static boolean fading = false;
	boolean goOut = false;

	public void fade(int changeMap, int oldMap, Graphics2D g2, int worX, int worY, int width, int height, int oldworX,
			int oldworY, int oldWidth, int oldHeight) throws IOException {

		GamePanel gp = new GamePanel();

		if (Player.keyH.changeMapPressed && GamePanel.worldX >= oldworX && GamePanel.worldX <= oldworX + oldWidth
				&& GamePanel.worldY >= oldworY && GamePanel.worldY <= oldworY + oldHeight) {
			fading = true;
			Player.disableCharacterMovement();
			Player.keyH.changeMapPressed = false;
			GamePanel.j.setJumpscare(true);
		}
		if (!fading && Player.keyH.changeMapPressed && GamePanel.worldX >= worX && GamePanel.worldX <= worX + width
				&& GamePanel.worldY >= worY && GamePanel.worldY <= worY + height) {
			System.out.println(fading);
			fading = true;
			Player.disableCharacterMovement();
			stepCount = 0;
			goOut = true;
		}
		if (fading) {
			fading(g2, t, gp, oldMap, changeMap);
		}
	}

}
