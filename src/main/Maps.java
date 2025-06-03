package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

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
	static int maxWorldCol;
	static int maxWorldRow;
	final static int worldWidth = 48 * maxWorldCol; // world width in pixels (2400 pixels)
	final static int worldHeight = 48 * maxWorldRow; // world height in pixels (2400 pixels)

	public static ArrayList<ArrayList<Integer>> tiles = new ArrayList<>();
	public static ArrayList<int[]> treePositions = new ArrayList<>();
	public static ArrayList<int[]> housePositions = new ArrayList<>();
	public static ArrayList<int[]> bedPositions = new ArrayList<>();
	public static ArrayList<int[]> houseWallPositions = new ArrayList<>();
	public static ArrayList<int[]> grassPositions = new ArrayList<>();
	public static ArrayList<int[]> waterPositions = new ArrayList<>();
	public static ArrayList<int[]> bookPositions = new ArrayList<>();
	public static BufferedImage nightmare;
	public static ImageIcon doctor;
	Sound nightmareSound;
	Sound doctrineSound;

	// Player p = new Player();
	// GamePanel gp = new GamePanel();
	Tiles t = new Tiles();
	static int currentMap;
	
	public Maps() {
		try {
			nightmare = ImageIO.read(new File("src/textures/nightmare.png"));
			// https://www.youtube.com/watch?v=X4BPQ65vFzA
			nightmareSound = new Sound("src/sound/hittingMetal.wav");

			doctrineSound = new Sound("src/sound/doctrine.wav");
			doctor = new ImageIcon("src/textures/doctor.gif");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String changeMap(Graphics2D g, int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");// intro map
			currentMap = 1;
		} else if (mapToChange == 2) {

			mapIntro("src/maps/mapHouse.txt");// house map
			currentMap = 2;
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");// open map
			currentMap = 3;
		} else if (mapToChange == 4) {
			mapIntro("src/maps/doctrine.txt");
			currentMap = 4;
		}
		return "-1";// no map found

	}

	public String changeMap(int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");
			currentMap = 1;
		} else if (mapToChange == 2) {
			mapIntro("src/maps/mapHouse.txt");
			currentMap = 2;
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");
			currentMap = 3;
		} else if (mapToChange == 4) {
			mapIntro("src/maps/doctrine.txt");
			currentMap = 4;
		} else if (mapToChange == 5) {
			mapIntro("src/maps/blank.txt");
			currentMap = 5;
		}
		return "-1";

	}

	public static void mapIntro(String filePath) {
		tiles.clear(); // Clear old tile data before loading new map

		try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
			String lines;
			while ((lines = r.readLine()) != null) {
				lines = lines.trim();
				if (lines.isEmpty())
					continue;

				String[] val = lines.split("\\s+");
				ArrayList<Integer> rowList = new ArrayList<>();

				for (String s : val) {
					rowList.add(Integer.parseInt(s));
				}

				tiles.add(rowList);
			}

			// Set world dimensions based on map size
			maxWorldRow = tiles.size();
			maxWorldCol = tiles.get(0).size();

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
		// Count the number of lines in the file
		// Iterate through the 2D array to find occurrences of 1
		for (int row = 0; row < tiles.size(); row++) {
			for (int col = 0; col < tiles.get(row).size(); col++) {
				if (tiles.get(row).get(col) == 63) {
					treePositions.add(new int[] { col * 48, row * 48 });// stores the location of where the trees are
				} else if (tiles.get(row).get(col) == 65 || tiles.get(row).get(col) == 66
						|| tiles.get(row).get(col) == 67 || tiles.get(row).get(col) == 68
						|| tiles.get(row).get(col) == 69 || tiles.get(row).get(col) == 70
						|| tiles.get(row).get(col) == 71 || tiles.get(row).get(col) == 10
						|| tiles.get(row).get(col) == 11 || tiles.get(row).get(col) == 12
						|| tiles.get(row).get(col) == 13 || tiles.get(row).get(col) == 14
						|| tiles.get(row).get(col) == 15 || tiles.get(row).get(col) == 16
						|| tiles.get(row).get(col) == 17 || tiles.get(row).get(col) == 84
						|| tiles.get(row).get(col) == 73 || tiles.get(row).get(col) == 40
						|| tiles.get(row).get(col) == 41 || tiles.get(row).get(col) == 42
						|| tiles.get(row).get(col) == 43 || tiles.get(row).get(col) == 44
						|| tiles.get(row).get(col) == 45 || tiles.get(row).get(col) == 46
						|| tiles.get(row).get(col) == 47 || tiles.get(row).get(col) == 54
						|| tiles.get(row).get(col) == 55 || tiles.get(row).get(col) == 56
						|| tiles.get(row).get(col) == 57 || tiles.get(row).get(col) == 58
						|| tiles.get(row).get(col) == 59 || tiles.get(row).get(col) == 39
						|| tiles.get(row).get(col) == 38) {
					housePositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles.get(row).get(col) == 21 || tiles.get(row).get(col) == 22
						|| tiles.get(row).get(col) == 19 || tiles.get(row).get(col) == 20) {
					bedPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the beds are
				} else if (tiles.get(row).get(col) == 23 || tiles.get(row).get(col) == 24
						|| tiles.get(row).get(col) == 25 || tiles.get(row).get(col) == 26
						|| tiles.get(row).get(col) == 27 || tiles.get(row).get(col) == 28
						|| tiles.get(row).get(col) == 29 || tiles.get(row).get(col) == 30
						|| tiles.get(row).get(col) == 31 || tiles.get(row).get(col) == 32) {
					houseWallPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the walls
																				// are
				} else if (tiles.get(row).get(col) == 33) {
					grassPositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles.get(row).get(col) == 34) {
					waterPositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles.get(row).get(col) == 85) {
					bookPositions.add(new int[] { col * 48, row * 48 });
				}
			}
		}
	}

	public static boolean incrementingUp = true;
	public static int yVal = -5;
	public static void drawExorcismRoom(Graphics2D g2) throws IOException {

		try {

			BufferedImage exorcismRoom = ImageIO.read(new File(
					"src/textures/the_room_where_spiritual_purification_and_demonic_entity_expulsion_through_sacred_rituals_occurs_with_the_presence_of_religion_certified_exorcists_in_white_robes.png"));
			g2.drawImage(exorcismRoom, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.setColor(new Color(87, 44, 19));
		g2.fillRect(0, 0, 280, 71);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.setColor(Color.WHITE);
		g2.drawString("Draw:", 15, 40);
		
		if (yVal == -5) {
			incrementingUp = true;
		} else if (yVal == 30) {
			incrementingUp = false;
		}
		
		if (incrementingUp) {
			yVal++;
		} else {
			yVal--;
		}
		
		
		/*Items.minigameGhost(g2, 1200, 820 + yVal, "Square", 250, 196);
		Items.minigameGhost(g2, 1000, 860 + yVal, "Circle", 250, 196);*/

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
						g2.drawImage(Tiles.tileImages.get(worldRow).get(worldCol), screenX, screenY, 48, 48, null);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	// change scene variables
	int fadeValue = 0;
	static int stepCount = 0;
	static int hasFaded = 0;

	public void fading(Graphics2D g2, Tiles t, int newMap, int originalMap) throws IOException {

		if (stepCount == 0) {// fade out
			Color fadeColor = new Color(0, 0, 0, fadeValue);
			g2.setColor(fadeColor);
			g2.fillRect(0, 0, 768, 576);
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

				Tiles.tileImages.clear();

				for (int i = 0; i < Maps.maxWorldRow; i++) {
					ArrayList<BufferedImage> row = new ArrayList<>();
					for (int j = 0; j < Maps.maxWorldCol; j++) {
						row.add(null); // initialize each cell with null
					}
					Tiles.tileImages.add(row);
				}

				g2.fillRect(-10000, -10000, 768 + 20000, 576 + 20000); // Clear the screen
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
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue -= 2;
			if (fadeValue <= 0) {
				fading = false;
				Player.disableCharacterMovement();
				stepCount = -1;
				fadeValue = 0;
				hasFaded++;

			}
		}
		if (hasFaded == 2) {
			if (stepCount == -1 && !hasJumpscared) {
				hasJumpscared = true;
				stepCount = 0;
			}
			if (stepCount == -1 && !hasDoctrined) {
				hasDoctrined = true;
				stepCount = 0;
			}
		} else if (hasFaded == 1) {
			if (stepCount == -1 && !hasDoctrined && hasJumpscared) {
				hasDoctrined = true;
				stepCount = 0;
			}
		}
	}

	static boolean fading = false;
	boolean goOut = false;
	static boolean hasJumpscared = false;
	static boolean hasDoctrined = false;

	public void fade(int changeMap, int oldMap, Graphics2D g2, int worX, int worY, int width, int height, int oldworX,
			int oldworY, int oldWidth, int oldHeight) throws IOException {

		if (Input.changeMapPressed && GamePanel.worldX >= oldworX && GamePanel.worldX <= oldworX + oldWidth
				&& GamePanel.worldY >= oldworY && GamePanel.worldY <= oldworY + oldHeight) {
			fading = true;
			Player.disableCharacterMovement();
			Input.changeMapPressed = false;
		}
		if (!fading && Input.changeMapPressed && GamePanel.worldX >= worX && GamePanel.worldX <= worX + width
				&& GamePanel.worldY >= worY && GamePanel.worldY <= worY + height) {
			fading = true;
			Player.disableCharacterMovement();
			stepCount = 0;
			goOut = true;
		}
		if (fading) {
			fading(g2, t, oldMap, changeMap);
		}
	}

	public static boolean doneNightmare = false;
	public static boolean inNightmare = false;
	public static boolean usingBed = false;

	static boolean once = false;
	static int fade2Value = 0;
	static boolean faded = false;
	static boolean doneFade = false;
	public static void nightmare(Graphics2D g2, Component observer) throws IOException {
		if (usingBed && !inNightmare && !doneNightmare) {
			Items.inConfirmation = true;
			Items.confirmation(g2, "Do you want to sleep?", 180);

			if (Items.yesPressed) {
				inNightmare = true;
				usingBed = false;
				Items.yesPressed = false;
				Items.inConfirmation = false;
			}
			if (Items.noPressed) {
				inNightmare = false;
				usingBed = false;
				Items.noPressed = false;
				Items.inConfirmation = false;
			}
			return;
		}

		if (inNightmare) {
				if (!faded) {
				Color fadeColor = new Color(0, 0, 0, fade2Value);
				g2.setColor(fadeColor);
				g2.fillRect(0, 0, 768, 576);
				fade2Value += 2;
				if (fade2Value >= 255) {
					fade2Value = 255;
					faded = true;
				}
			}
			if (faded) {
				g2.drawImage(nightmare, 0, 0, 768, 576, null);
				g2.drawImage(doctor.getImage(), 300, 160, 400, 300, observer);
				g2.setColor(new Color(0, 0, 0, fade2Value));
				g2.fillRect(0, 0, 768, 576);
				fade2Value -= 2;
				if (fade2Value <= 0) {
					fade2Value = 0;
					doneFade = true;
				}
			}
			if (doneFade) {
				g2.drawImage(nightmare, 0, 0, 768, 576, null);
				g2.drawImage(doctor.getImage(), 300, 160, 400, 300, observer);
				if (!once) {
					Npc.textIndex = 0;
					once = true;
				}
				Npc.text(g2, 6);
			}
		}

		if (doneNightmare) {
			inNightmare = false;
			Npc.doctor(g2);
		}
	}
	
	public void playNightmareSound() {
		if (inNightmare && doneFade) {
			if (!nightmareSound.isPlaying()) {
				nightmareSound.play();
			}
		} else if (doneNightmare) {
			nightmareSound.stop();
		}
	}

	public void playDoctrineSound() {
		if (currentMap == 4 ) {
			if (!doctrineSound.isPlaying()) {
				doctrineSound.play();
			}
		} else {
			if (doctrineSound.isPlaying()) {
				doctrineSound.stop();
			}
		}
	}

}
