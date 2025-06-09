package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
import java.awt.geom.Point2D;
import Horror.Jumpscare;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Maps {

	// WORLD SETTINGS
	private int maxWorldCol;//the number of columns in the current map
	private int maxWorldRow;//the number of row in the current map

	private ArrayList<ArrayList<Integer>> tiles = new ArrayList<>();//storing all the numbers that are being read from the map files
	private ArrayList<int[]> treePositions = new ArrayList<>();//all numbers that correspond to the trees
	private ArrayList<int[]> housePositions = new ArrayList<>();//all numbers that correspond to the house
	private ArrayList<int[]> bedPositions = new ArrayList<>();//all numbers that correspond to the bed
	private ArrayList<int[]> houseWallPositions = new ArrayList<>();//all numbers that correspond to the house walls
	private ArrayList<int[]> grassPositions = new ArrayList<>();//all numbers that correspond to the grass
	private ArrayList<int[]> waterPositions = new ArrayList<>();//all numbers that correspond to the water
	private ArrayList<int[]> bookPositions = new ArrayList<>();//all numbers that correspond to the book

	private BufferedImage nightmare;
	private ImageIcon doctor;

	private Tiles t;
	private Npc npc;
	private Jumpscare j;
	private Player p;
	private Items items;
	private Input inp;

	private Sound nightmareSound;
	private Sound doctrineSound;

	private int worldX;
	private int worldY;
	private int currentMap;

	boolean fading = false;
	boolean goOut = false;
	boolean hasJumpscared = false;
	boolean hasDoctrined = false;

	private boolean doneNightmare = false;
	private boolean inNightmare = false;
	private boolean usingBed = false;
	private boolean end = false; // for the end screen

	private boolean once = false;
	private boolean once2 = false;
	private boolean once3 = false;
	private int fade2Value = 0;
	private boolean faded = false;
	private boolean doneFade = false;

	// change scene variables
	private int fadeValue = 0;
	private int stepCount = 0;
	private int hasFaded = 0;

	private boolean incrementingUp = true;
	private int yVal = -5;

	public Maps(GamePanel gp) {
		this.worldX = gp.getWorldX();
		this.worldY = gp.getWorldY();

		this.inp = gp.getId();
		this.items = gp.getIt();
		this.npc = gp.getN();
		this.t = gp.getT();
		this.j = gp.getJ();
		this.p = gp.getP();

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

	public void updateMapValues(int worldX, int worldY) {
		this.worldX = worldX;
		this.worldY = worldY;
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

	public void mapIntro(String filePath) {
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

	public void drawExorcismRoom(Graphics2D g2) throws IOException {

		try {

			BufferedImage exorcismRoom = ImageIO.read(new File("src/textures/exorcist.png"));
			g2.drawImage(exorcismRoom, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.setColor(new Color(87, 44, 19));
		g2.fillRoundRect(0, 0, 280, 71, 7, 7);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(0, 0, 280, 71, 7, 7);
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
	}

	public void camera(Graphics g2, GamePanel gp) {
		try {
			for (int worldRow = 0; worldRow < maxWorldRow; worldRow++) {
				for (int worldCol = 0; worldCol < maxWorldCol; worldCol++) {

					// where on the screen the tile will be drawn
					int screenX = worldCol * 48 - gp.getWorldX();
					int screenY = worldRow * 48 - gp.getWorldY();

					// only draws the tiles that are on the screen
					if (screenX + 48 > 0 && screenX < 768 && screenY + 48 > 0 && screenY < 576) {
						g2.drawImage(t.tileImages.get(worldRow).get(worldCol), screenX, screenY, 48, 48, null);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void fading(Graphics2D g2, Tiles t, int newMap, int originalMap, GamePanel gp) throws IOException {

		if (stepCount == 0) {// fade out
			Color fadeColor = new Color(0, 0, 0, fadeValue);
			g2.setColor(fadeColor);
			g2.fillRect(0, 0, 768, 576);
			if (lookInMirror) {
				fadeValue += 1;
			} else {
				fadeValue += 2;
			}
			if (fadeValue >= 255) {
				fadeValue = 255;
				stepCount = 1;
			}
			if (lookInMirror) {
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Calibri", Font.BOLD, 40));
				g2.drawString("Later that Night...", 250, 250);
			}
		}

		else if (stepCount == 1) {// change map

			// Clear old map data
			treePositions.clear();
			housePositions.clear();
			bedPositions.clear();
			houseWallPositions.clear();
			grassPositions.clear();
			waterPositions.clear();

			t.tileImages.clear();

			for (int i = 0; i < maxWorldRow; i++) {
				ArrayList<BufferedImage> row = new ArrayList<>();
				for (int j = 0; j < maxWorldCol; j++) {
					row.add(null); // initialize each cell with null
				}
				t.tileImages.add(row);
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
			t.tileCreating();
			findIntroHouse();
			findTrees();
			if (currentMap == 2 && !lookInMirror) {
				gp.setWorldX(287);
				gp.setWorldY(200);
				gp.setDirection("back");
			} else if (currentMap == 3) {
				gp.setWorldX(509);
				gp.setWorldY(63);
				gp.setDirection("front");
			} else if (currentMap == 4) {
				gp.setWorldX(190);
				gp.setWorldY(-125);
				gp.setDirection("front");
			} else if (currentMap == 5) {
				gp.setWorldX(384);
				gp.setWorldY(288);
			} else if (currentMap == 2 && lookInMirror) {
				gp.setWorldX(230);
				gp.setWorldY(-182);
				gp.setDirection("back");
			}
			stepCount = 2;
		}

		else if (stepCount == 2)

		{// fade in
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			if (lookInMirror) {
				fadeValue -= 1;
			} else {
				fadeValue -= 2;
			}
			if (fadeValue <= 0) {
				fading = false;
				p.disableCharacterMovement();
				stepCount = -1;
				fadeValue = 0;
				hasFaded++;
			}
			if (triggerTransition) {
				npc.setSurprisedText(true);
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

	public void fade(int changeMap, int oldMap, Graphics2D g2, int worX, int worY, int width, int height, int oldworX,
			int oldworY, int oldWidth, int oldHeight, GamePanel gp) throws IOException {

		if (currentMap == 5) {
			inp.setChangeMapPressed(true);
		} else if (currentMap == 3 && lookInMirror) {
			inp.setChangeMapPressed(true);
		}

		if (inp.isChangeMapPressed() && gp.getWorldX() >= oldworX && gp.getWorldX() <= oldworX + oldWidth
				&& gp.getWorldY() >= oldworY && gp.getWorldY() <= oldworY + oldHeight) {
			fading = true;
			p.disableCharacterMovement();
			inp.setChangeMapPressed(false);
		} else if (currentMap == 2 && lookInMirror && inp.isChangeMapPressed()) {
			fading = true;
			p.disableCharacterMovement();
			inp.setChangeMapPressed(false);
		}
		if (!fading && inp.isChangeMapPressed() && gp.getWorldX() >= worX && gp.getWorldX() <= worX + width
				&& gp.getWorldY() >= worY && gp.getWorldY() <= worY + height) {
			if (doneNightmare) {
				fading = true;
				p.disableCharacterMovement();
				stepCount = 0;
				goOut = true;
			}
		}
		if (fading) {
			fading(g2, t, oldMap, changeMap, gp);
		}

	}

	private boolean doneDoctorDead = false;

	public void nightmare(Graphics2D g2, Component observer, GamePanel gp) throws IOException {

		if (usingBed && !inNightmare && !doneNightmare && !doneDoctorDead) {
			items.setInConfirmation(true);
			items.confirmation(g2, "Do you want to", "sleep? ", 245, 330);
			if (items.isYesPressed()) {
				inNightmare = true;
				usingBed = false;
				items.setYesPressed(false);
				items.setInConfirmation(false);
			}
			if (items.isNoPressed()) {
				inNightmare = false;
				usingBed = false;
				items.setNoPressed(false);
				items.setInConfirmation(false);
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
					npc.setTextIndex(0);
					once = true;
				}
				npc.text(g2, 6);
			}
		}
		if (doneNightmare && currentMap == 2 && !lookInMirror) {
			inNightmare = false;
			if (!once2) {
				npc.setTextIndex(0);
				once2 = true;
			}
			npc.text(g2, 3);
			npc.doctor(g2, gp);
		}
	}

	private boolean triggerTransition = false;

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
		if (currentMap == 4) {
			if (!doctrineSound.isPlaying()) {
				doctrineSound.play();
			}
		} else {
			if (doctrineSound.isPlaying()) {
				doctrineSound.stop();
			}
		}
	}

	public boolean isDoneNightmare() {
		return doneNightmare;
	}

	private boolean enteredPlace = false;
	private boolean enteredDoctrine = false;
	private boolean exitHouse = false;
	private boolean disableConfirmation = false;
	private boolean enteredCar = false;

	public void confirmationCollision(GamePanel gp, Graphics2D g2) {

		if (items.isInConfirmation()) {

			if (!isInConfirmationArea(gp)) {
				items.setInConfirmation(false);
				return;
			}

			if (items.isYesPressed()) {
				items.setYesPressed(false);
				items.setInConfirmation(false);
				inp.setChangeMapPressed(true);
				if (currentMap == 3 && doneNightmare) {
					inp.setCPressed(true);
				}
				disableConfirmation = true;

				if (gp.getWorldX() >= 450 && gp.getWorldX() <= 600 && gp.getWorldY() >= 38 && gp.getWorldY() <= 138) {
					enteredPlace = true;
				} else if (gp.getWorldX() >= 5584 && gp.getWorldX() <= 5684 && gp.getWorldY() >= 550
						&& gp.getWorldY() <= 650) {
					enteredDoctrine = true;
				} else if (gp.getWorldX() >= 248 && gp.getWorldX() <= 330 && gp.getWorldY() >= 216
						&& gp.getWorldY() <= 264) {
					exitHouse = true;
				} else if (gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
						&& gp.getWorldY() <= 638) {
					enteredCar = true;
				}
				return;
			}

			if (items.isNoPressed()) {
				items.setNoPressed(false);
				items.setInConfirmation(false);
				disableConfirmation = true;
				return;
			}
			if (currentMap == 3) {
				items.confirmation(g2, "Do you want to", "enter this place?", 250, 225);
			} else if (currentMap == 2) {
				items.confirmation(g2, "Do you want to", "exit the house?", 250, 240);
			} else if (currentMap == 4) {
				items.confirmation(g2, "Do you want to enter", "the exorcism Room?", 190, 210);
			}
			return;
		}

		if (disableConfirmation) {
			if (!isInConfirmationArea(gp)) {
				disableConfirmation = false;
			}
			return;
		}

		if (currentMap == 3) {
			if (!enteredPlace && gp.getWorldX() >= 450 && gp.getWorldX() <= 600 && gp.getWorldY() >= 38
					&& gp.getWorldY() <= 138) {
				items.setInConfirmation(true);
			} else if (!enteredDoctrine && gp.getWorldX() >= 5584 && gp.getWorldX() <= 5684 && gp.getWorldY() >= 550
					&& gp.getWorldY() <= 650) {
				items.setInConfirmation(true);
			} else if (!enteredCar && gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
					&& gp.getWorldY() <= 638) {
				items.setInConfirmation(true);
			}
		} else if (currentMap == 2) {
			if (!exitHouse && gp.getWorldX() >= 248 && gp.getWorldX() <= 330 && gp.getWorldY() >= 196
					&& gp.getWorldY() <= 264 && doneNightmare) {
				items.setInConfirmation(true);
			}

		} else if (currentMap == 4) {
			if (gp.getWorldX() >= 838 && gp.getWorldX() <= 893 && gp.getWorldY() >= 216 && gp.getWorldY() <= 271) {
				items.setInConfirmation(true);
			}
		}
	}

	private boolean isInConfirmationArea(GamePanel gp) {
		if (currentMap != 3 && currentMap != 2 && currentMap != 4)
			return false;

		return (currentMap == 3 && gp.getWorldX() >= 450 && gp.getWorldX() <= 600 && gp.getWorldY() >= 38
				&& gp.getWorldY() <= 138)
				|| (currentMap == 3 && gp.getWorldX() >= 5584 && gp.getWorldX() <= 5684 && gp.getWorldY() >= 550
						&& gp.getWorldY() <= 650)
				|| (currentMap == 3 && gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
						&& gp.getWorldY() <= 638)
				|| (currentMap == 2 && gp.getWorldX() >= 248 && gp.getWorldX() <= 330 && gp.getWorldY() >= 196
						&& gp.getWorldY() <= 264)
				|| (currentMap == 4 && gp.getWorldX() >= 838 && gp.getWorldX() <= 893 && gp.getWorldY() >= 216
						&& gp.getWorldY() <= 271);
	}

	private boolean lookInMirror = false;

	public void mirrorScene(Graphics2D g2, Component observer, GamePanel gp) throws IOException {
		System.out.println(end);
		if (triggerTransition) {
			lookInMirror = true;
			if (!once3) {
				npc.setTextIndex(0);
				once3 = true;
			}
			fade(5, 2, g2, 384, 288, 100, 100, 1000, 1000, 100, 100, gp);
			gp.setWorldX(212);
			gp.setWorldY(-180);
		}
		if (npc.isSurprisedText()) {
			items.houseMirror(g2);
			npc.text(g2, 9);
		}
	}

	private int fade3Value = 0;
	private boolean faded2 = false;
	private boolean hoveringQuit = false;
	private Color selected = new Color(144, 50, 50);
	private Color unselected = new Color(193, 45, 57);

	public void endScreen(Graphics2D g2) throws IOException {
		if (end) {
			if (!faded2) {
				Color fadeColor2 = new Color(0, 0, 0, fade3Value);
				g2.setColor(fadeColor2);
				g2.fillRect(0, 0, 768, 576);
				fade3Value += 2;
				if (fade3Value >= 255) {
					fade3Value = 255;
					faded2 = true;
				}
			}
			if (faded2) {
				g2.setColor(new Color(0, 0, 0));
				g2.fillRect(0, 0, 768, 576);
				g2.setColor(Color.WHITE);
				g2.setFont(new Font("Monospaced", Font.BOLD, 60));
				g2.drawString("THE END", 270, 250);
				g2.setFont(new Font("Calibri", Font.PLAIN, 40));
				g2.drawString("Thank you for playing!", 210, 300);
				if (!inp.isHoveringQuit()) {
					g2.setColor(unselected);
				} else if (inp.isHoveringQuit()) {
					g2.setColor(selected);
				}
				g2.fillRoundRect(325, 430, 130, 45, 10, 10);
				g2.setFont(new Font("Calibri", Font.BOLD, 25));
				g2.setColor(Color.WHITE);
				g2.drawString("Exit", 370, 460);
			}
			if (inp.isPressedQuit()) {
				System.exit(0);
			}
		}

	}

	public void drawTint(Graphics2D g2, GamePanel gp) {

		Point2D centerPoint = new Point2D.Float(gp.getPlayerX(), gp.getPlayerY());
		float radiusTint = (float) 350;

		Color transparentColor = new Color(0, 0, 0, 200);
		Color darkColor = new Color(0, 0, 0, 255); // Dark color with alpha for transparency

		RadialGradientPaint gradient = new RadialGradientPaint(centerPoint, radiusTint,
				new float[] { (float) 0.0, (float) 1.0 }, new Color[] { transparentColor, darkColor });

		g2.setPaint(gradient);
		g2.fillRect(gp.getPlayerX() - 384, gp.getPlayerY() - 288, gp.getPlayerX() + 384, gp.getPlayerY() + 288); // Fill
																													// the
																													// entire
																													// panel
																													// with
																													// the
		// gradient
		if (items.getAnimationFrame() >= 150) {
			items.titleScreen(g2);
		}
	}

	public boolean isLookInMirror() {
		return lookInMirror;
	}

	public void setLookInMirror(boolean lookInMirror) {
		this.lookInMirror = lookInMirror;
	}

	public void setT(Tiles t) {
		this.t = t;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public void setJ(Jumpscare j) {
		this.j = j;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public void setInp(Input inp) {
		this.inp = inp;
	}

	public ArrayList<ArrayList<Integer>> getTiles() {
		return tiles;
	}

	public ArrayList<int[]> getTreePositions() {
		return treePositions;
	}

	public ArrayList<int[]> getHousePositions() {
		return housePositions;
	}

	public ArrayList<int[]> getBedPositions() {
		return bedPositions;
	}

	public ArrayList<int[]> getHouseWallPositions() {
		return houseWallPositions;
	}

	public ArrayList<int[]> getGrassPositions() {
		return grassPositions;
	}

	public ArrayList<int[]> getWaterPositions() {
		return waterPositions;
	}

	public ArrayList<int[]> getBookPositions() {
		return bookPositions;
	}

	public int getCurrentMap() {
		return currentMap;
	}

	public int getStepCount() {
		return stepCount;
	}

	public void setNightmare(BufferedImage nightmare) {
		this.nightmare = nightmare;
	}

	public void setDoneNightmare(boolean doneNightmare) {
		this.doneNightmare = doneNightmare;
	}

	public void setHasFaded(int hasFaded) {
		this.hasFaded = hasFaded;
	}

	public int getHasFaded() {
		return hasFaded;
	}

	public void setUsingBed(boolean usingBed) {
		this.usingBed = usingBed;
	}

	public boolean isInNightmare() {
		return inNightmare;
	}

	public boolean isDoneDoctorDead() {
		return doneDoctorDead;
	}

	public void setDoneDoctorDead(boolean doneDoctorDead) {
		this.doneDoctorDead = doneDoctorDead;
	}

	public boolean isTriggerTransition() {
		return triggerTransition;
	}

	public void setTriggerTransition(boolean triggerTransition) {
		this.triggerTransition = triggerTransition;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public boolean isEnd() {
		return end;
	}

}
