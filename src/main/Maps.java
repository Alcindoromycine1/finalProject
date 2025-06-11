/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
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
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Maps {

	// WORLD SETTINGS
	private int maxWorldCol;// the number of columns in the current map
	private int maxWorldRow;// the number of row in the current map

	private ArrayList<ArrayList<Integer>> tiles = new ArrayList<>();// storing all the numbers that are being read from
																	// the map files
	private ArrayList<int[]> treePositions = new ArrayList<>();// all numbers that correspond to the trees
	private ArrayList<int[]> housePositions = new ArrayList<>();// all numbers that correspond to the house
	private ArrayList<int[]> bedPositions = new ArrayList<>();// all numbers that correspond to the bed
	private ArrayList<int[]> houseWallPositions = new ArrayList<>();// all numbers that correspond to the house walls
	private ArrayList<int[]> grassPositions = new ArrayList<>();// all numbers that correspond to the grass
	private ArrayList<int[]> waterPositions = new ArrayList<>();// all numbers that correspond to the water
	private ArrayList<int[]> bookPositions = new ArrayList<>();// all numbers that correspond to the book

	private BufferedImage nightmare;// image of the nightmare
	private ImageIcon doctor;// GIF of the doctor

	private Tiles t;// Reference to the Tiles class to access Tiles methods and properties
	private Npc npc;// Reference to the Npc class to access Npc methods and properties
	private Jumpscare j;// Reference to the Jumpscare class to access Jumpscare methods and properties
	private Player p;// Reference to the Jumpscare class to access Jumpscare methods and properties
	private Items items;// Reference to the Jumpscare class to access Jumpscare methods and properties
	private Input inp;// Reference to the Input class to access Input methods and properties

	private Sound nightmareSound;// sound for the nightmare
	private Sound doctrineSound;// sound for the doctrine

	private int worldX;// player x position relative to the world
	private int worldY;// player Y position relative to the world
	private int currentMap;// current map the user is playing (e.g. openMap is 3)

	private boolean fading = false;// fading inwards
	private boolean goOut = false;// has not gone outside once teleported inside the area (used for the fading
									// system)
	private boolean hasJumpscared = false;// has the user jumpscared or not
	private boolean hasDoctrined = false;// has the user been in the doctrine or not

	private boolean doneNightmare = false;// has the user finished the nightmare scene
	private boolean inNightmare = false;// is the user currently in the nightmare or not
	private boolean usingBed = false;// is the user using the bed (in the collision area)
	private boolean end = false; // for the end screen

	private boolean once = false;// resets the text index to 0 for different areas
	private boolean once2 = false;// resets the text index to 0 for different areas
	private boolean once3 = false;// resets the text index to 0 for different areas
	private int fade2Value = 0;// fade value that is responsible for changing the transparency
	private boolean faded = false;// has completing fading in
	private boolean doneFade = false;// has completed both fading in and out

	// change scene variables
	private int fadeValue = 0;// fade value that is responsible for changing the transparency
	private int stepCount = 0;// the current step number in the process of fading. For example, when you begin
								// fading you step count is 0, once you have are fading out it becomes 2)
	private int hasFaded = 0;// whether you've went into a place and also left that place: fade becomes 2

	private boolean incrementingUp = true;// used for ghosts moving up and down. When true, the ghost will move up, else
											// it will move down
	private int yVal = -5;// decreases and increases the yValue for the ghost y displacement

	private boolean doneDoctorDead = false;// the doctor has passwed away when true
	private boolean triggerTransition = false;// used to check whether the user has transitioned from the exorcism room
												// to the mirror scene

	private boolean enteredPlace = false;// checks whether you have entered anywhere in map 3 (besides the car)
	private boolean enteredDoctrine = false;// checks whether you have entered the doctrine
	private boolean exitHouse = false;// checks whether you have exited the house
	private boolean disableConfirmation = false;// checks whether the confirmation menu is disabled or not
	private boolean enteredCar = false;// checks whether you have entered the car
	private boolean lookInMirror = false;// checks whether you are in the mirror scene or not (true when in scene)

	private int fade3Value = 0;// fade value that is responsible for changing the transparency
	private boolean faded2 = false;// has faded
	private boolean hoveringQuit = false;// hovering over the quit button in the end screen
	private Color selected = new Color(144, 50, 50);// color for when any button is hovered over with a cursor
	private Color unselected = new Color(193, 45, 57);// color for when any button is not hovered over with a cursor

	/**
	 * Constructor for the Maps Class
	 * 
	 * @param gp
	 */
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
			nightmare = ImageIO.read(new File("textures/nightmare.png"));
			// https://www.youtube.com/watch?v=X4BPQ65vFzA
			nightmareSound = new Sound("sound/hittingMetal.wav");

			doctrineSound = new Sound("sound/doctrine.wav");
			doctor = new ImageIcon("textures/doctor.gif");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @purpose updates the worldX and worldY value of map
	 * @param worldX
	 * @param worldY
	 */
	public void updateMapValues(int worldX, int worldY) {
		this.worldX = worldX;
		this.worldY = worldY;
	}

	/**
	 * @purpose this will take input to detect which map is
	 * @param mapToChange
	 * @return the map that is being changed to
	 */
	public String changeMap(int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("maps/mapIntro.txt");//intro scene
			currentMap = 1;
		} else if (mapToChange == 2) {
			mapIntro("maps/mapHouse.txt");//house
			currentMap = 2;
		} else if (mapToChange == 3) {
			mapIntro("maps/openMap.txt");//open map
			currentMap = 3;
		} else if (mapToChange == 4) {
			mapIntro("maps/doctrine.txt");//doctrine
			currentMap = 4;
		} else if (mapToChange == 5) {
			mapIntro("maps/blank.txt");//exorcism room
			currentMap = 5;
		}
		return "-1";// no change map found

	}

	/**
	 * @purpose reads the numbers from the file and stores into arrayList
	 * @param filePath
	 */
	public void mapIntro(String filePath) {
		tiles.clear(); // Clear old tile data before loading new map

		try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
			String lines;
			while ((lines = r.readLine()) != null) {// while the file is not empty
				lines = lines.trim();
				if (lines.isEmpty())
					continue;

				String[] val = lines.split("\\s+");// gets rid of spacing
				ArrayList<Integer> rowList = new ArrayList<>();

				for (String s : val) {
					rowList.add(Integer.parseInt(s));// stores number
				}

				tiles.add(rowList);// adds to arrayList
			}

			// Set world dimensions based on map size
			maxWorldRow = tiles.size();// size of row
			maxWorldCol = tiles.get(0).size();// size of col

		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @purpose stores the numbers from the 2D arrayList into their respective
	 *          arrayList
	 * @throws IOException
	 */
	public void findTiles() throws IOException {

		BufferedReader r = new BufferedReader(new FileReader("maps/mapIntro.txt"));
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
					housePositions.add(new int[] { col * 48, row * 48 });// stores the location of where the houses are
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
					grassPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the grass
					// is
				} else if (tiles.get(row).get(col) == 34) {
					waterPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the water
					// is
				} else if (tiles.get(row).get(col) == 85) {
					bookPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the books
					// are
				}
			}
		}
	}

	/**
	 * @purpose Draws the exorcism room background and also controls the movement of
	 *          the ghosts going up and down
	 * @param g2
	 * @throws IOException
	 */
	public void drawExorcismRoom(Graphics2D g2) throws IOException {

		try {

			BufferedImage exorcismRoom = ImageIO.read(new File("textures/exorcist.png"));
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

		if (yVal == -5) {// lowest value possible in terms offset
			incrementingUp = true;// ghost moves down
		} else if (yVal == 30) {// reached its peak height
			incrementingUp = false;// ghost goes back up
		}

		if (incrementingUp) {// if the ghost is suppose to go down
			yVal++;// increase y value
		} else {// ghost is suppose to go up
			yVal--;// decrease y value
		}
	}

	/**
	 * @purpose displays the tiles from tileImages 2D arrayList to only appear on
	 *          the screen (not outside of the screen)
	 * @param g2
	 * @param gp
	 */
	public void camera(Graphics g2, GamePanel gp) {
		try {
			// Searching through the 2D arrayList
			for (int worldRow = 0; worldRow < maxWorldRow; worldRow++) {
				for (int worldCol = 0; worldCol < maxWorldCol; worldCol++) {

					// where on the screen the tile will be drawn
					int screenX = worldCol * 48 - gp.getWorldX();// converts the x position of the tile to world
																	// coordinates
					int screenY = worldRow * 48 - gp.getWorldY();// converts the Y position of the tile to world
																	// coordinates

					// only draws the tiles that are on the screen
					if (screenX + 48 > 0 && screenX < 768 && screenY + 48 > 0 && screenY < 576) {
						g2.drawImage(t.tileImages.get(worldRow).get(worldCol), screenX, screenY, 48, 48, null);// draws
																												// the
																												// tiles
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @purpose fades in and out of the screen, and teleports you to a new map
	 * @param g2
	 * @param t
	 * @param newMap
	 * @param originalMap
	 * @param gp
	 * @throws IOException
	 */
	public void fading(Graphics2D g2, Tiles t, int newMap, int originalMap, GamePanel gp) throws IOException {

		if (stepCount == 0) {// fade out
			Color fadeColor = new Color(0, 0, 0, fadeValue);
			g2.setColor(fadeColor);
			g2.fillRect(0, 0, 768, 576);
			if (lookInMirror) {// mirror scene
				fadeValue += 1;// slow down fade
			} else {
				fadeValue += 2;// anything but mirror scene increase fade
			}
			if (fadeValue >= 255) {// can't go exceed 255 transparency
				fadeValue = 255;
				stepCount = 1;// go to the next step (change map)
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

			// Iterating through the 2D arrayList
			for (int i = 0; i < maxWorldRow; i++) {
				ArrayList<BufferedImage> row = new ArrayList<>();
				for (int j = 0; j < maxWorldCol; j++) {
					row.add(null); // clears everything to null before adding tiles again
				}
				t.tileImages.add(row);// clears the tile images to null before adding tiles again
			}

			g2.fillRect(-10000, -10000, 768 + 20000, 576 + 20000); // Clear the screen
			// Change to the new map
			if (goOut) {// goes to the new map
				changeMap(newMap);// changes map to the new map
			} else if (!goOut) {// goes back to the old map
				changeMap(originalMap);// changes map to the old map
			}
			// Load the new map's tiles
			// Reinitialize the new map's data
			t.readFile();// fills tiles again
			findTiles();// storing values into the 2D arrayLists
			if (currentMap == 2 && !lookInMirror) {// if in house (not mirror scene)
				// Teleport to these coordinates
				gp.setWorldX(287);
				gp.setWorldY(200);
				gp.setDirection("back");// face this direction
			} else if (currentMap == 3) {// if in the open map (exiting house)
				// Teleport to these coordinates
				gp.setWorldX(509);
				gp.setWorldY(63);
				gp.setDirection("front");// face this direction
			} else if (currentMap == 4) {// if entering doctrine
				// Teleport to these coordinates
				gp.setWorldX(190);
				gp.setWorldY(-125);
				gp.setDirection("front");// face this direction
			} else if (currentMap == 5) {// if entering exorcism room
				// Teleport to these coordinates
				gp.setWorldX(384);
				gp.setWorldY(288);
			} else if (currentMap == 2 && lookInMirror) {// If in the mirror scene
				// Teleport to these coordinates
				gp.setWorldX(230);
				gp.setWorldY(-182);
				gp.setDirection("back");// face this direction
			}
			stepCount = 2;// now fade in
		}

		else if (stepCount == 2)

		{// fade in
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			if (lookInMirror) {// mirror scene (slower fade)
				fadeValue -= 1;
			} else {// anything but mirror scene (faster fade)
				fadeValue -= 2;
			}
			if (fadeValue <= 0) {// done fading in
				fading = false;// stop fading
				p.disableCharacterMovement();// enable character movement again
				stepCount = -1;// avoid the fade from occuring again
				fadeValue = 0;// resetting values
				hasFaded++;// has entered one location
			}
			if (triggerTransition) {// if exitting the exorcism room
				npc.setSurprisedText(true);// display the ghost text
			}
		}
		if (hasFaded == 2) {// if entered the area and exited
			if (stepCount == -1 && !isHasJumpscared()) {// exited house
				setHasJumpscared(true);// jumpscare has occured
				stepCount = 0;// reset step count
			}
			if (stepCount == -1 && !isHasDoctrined()) {
				setHasDoctrined(true);// has been in doctrine
				stepCount = 0;// reset step count
			}
		} else if (hasFaded == 1) {// only entered (not exited area)
			if (stepCount == -1 && !isHasDoctrined() && isHasJumpscared()) {// entered the doctrine but not exited yet
				setHasDoctrined(true);// doctrine set to true
				stepCount = 0;// reset step count
			}
		}
	}

	/**
	 * @purpose this method is responsible for setting the variables that check
	 *          whether the user is entering an area based on changeMap being
	 *          Pressed
	 * @param changeMap
	 * @param oldMap
	 * @param g2
	 * @param worX
	 * @param worY
	 * @param width
	 * @param height
	 * @param oldworX
	 * @param oldworY
	 * @param oldWidth
	 * @param oldHeight
	 * @param gp
	 * @throws IOException
	 */
	public void fade(int changeMap, int oldMap, Graphics2D g2, int worX, int worY, int width, int height, int oldworX,
			int oldworY, int oldWidth, int oldHeight, GamePanel gp) throws IOException {

		if (currentMap == 5) {// In exorcism room
			inp.setChangeMapPressed(true);// no confirmation menu in exorcism room so automatically enable
											// changeMapPressed
		} else if (currentMap == 3 && lookInMirror) {
			inp.setChangeMapPressed(true);// no confirmation menu in mirror scene so automatically enable
											// changeMapPressed
		}

		// entering the new area for the first time
		if (inp.isChangeMapPressed() && gp.getWorldX() >= oldworX && gp.getWorldX() <= oldworX + oldWidth
				&& gp.getWorldY() >= oldworY && gp.getWorldY() <= oldworY + oldHeight) {
			fading = true;// begin the fading in and out
			p.disableCharacterMovement();// disable character movement
			inp.setChangeMapPressed(false);// reset changeMapPressed
		} else if (currentMap == 2 && lookInMirror && inp.isChangeMapPressed()) {// if in mirror scene
			fading = true;// also fade
			p.disableCharacterMovement();// disable character movement
			inp.setChangeMapPressed(false);// reset changeMapPressed
		}
		// exiting the area you originally teleported to
		if (!fading && inp.isChangeMapPressed() && gp.getWorldX() >= worX && gp.getWorldX() <= worX + width
				&& gp.getWorldY() >= worY && gp.getWorldY() <= worY + height) {
			if (doneNightmare) {// in the house
				fading = true;// fade in and out
				p.disableCharacterMovement();// disable character movement
				stepCount = 0;// reset step count
				goOut = true;// you have now gone back outside
			}
		}
		if (fading) {// if you any of the above if statements are true
			fading(g2, t, oldMap, changeMap, gp);// fade in and out and change map
		}
	}

	/**
	 * @purpose this method contains all the logic and graphics that occur in the
	 *          nightmare
	 * @param g2
	 * @param observer
	 * @param gp
	 * @throws IOException
	 */
	public void nightmare(Graphics2D g2, Component observer, GamePanel gp) throws IOException {

		if (usingBed && !inNightmare && !doneNightmare && !doneDoctorDead) {// on bed
			items.setInConfirmation(true);// pop up the confirmation menu option
			items.confirmation(g2, "Do you want to", "sleep? ", 245, 330);// confirmation menu graphics
			if (items.isYesPressed()) {// yes pressed in confirmation menu
				// Begin the nightmare
				inNightmare = true;
				usingBed = false;
				items.setYesPressed(false);
				items.setInConfirmation(false);
			}
			if (items.isNoPressed()) {// No pressed in confirmation menu
				// Reset everything until attempted again
				inNightmare = false;
				usingBed = false;
				items.setNoPressed(false);
				items.setInConfirmation(false);
			}
			return;
		}

		if (inNightmare) {// once in nightmare
			if (!faded) {// fade in
				Color fadeColor = new Color(0, 0, 0, fade2Value);
				g2.setColor(fadeColor);
				g2.fillRect(0, 0, 768, 576);
				fade2Value += 2;
				if (fade2Value >= 255) {// prevents the transparency from exceeding 255 (not possible)
					fade2Value = 255;
					faded = true;// has faded in
				}
			}
			if (faded) {// has faded in already
				g2.drawImage(nightmare, 0, 0, 768, 576, null);
				g2.drawImage(doctor.getImage(), 300, 160, 400, 300, observer);
				g2.setColor(new Color(0, 0, 0, fade2Value));
				g2.fillRect(0, 0, 768, 576);
				fade2Value -= 2;// fade back out
				if (fade2Value <= 0) {// prevents the transparency from becoming a negative value (not possible)
					fade2Value = 0;
					doneFade = true;// done fading in and out
				}
			}
			if (doneFade) {// display the nightmare
				g2.drawImage(nightmare, 0, 0, 768, 576, null);// nightmare graphics
				g2.drawImage(doctor.getImage(), 300, 160, 400, 300, observer);// doctor GIF using metal pipe
				if (!once) {// only reset text index once
					npc.setTextIndex(0);// reset text index
					once = true;
				}
				npc.text(g2, 6);// display the nightmare text
			}
		}
		if (doneNightmare && currentMap == 2 && !lookInMirror) {// exited the nightmare
			inNightmare = false;// no longer in nightmare
			if (!once2) {// outside of nightmare
				npc.setTextIndex(0);// reset text index
				once2 = true;
			}
			npc.text(g2, 3);// doctor text
			npc.doctor(g2, gp);// doctor graphics
		}
	}

	/**
	 * @purpose plays the sounds that appear in the nightmare
	 */
	public void playNightmareSound() {
		if (inNightmare && doneFade) {// in the nightmare
			if (!nightmareSound.isPlaying()) {
				nightmareSound.play();// metal pipe sound
			}
		} else if (doneNightmare) {// outside of nightmare
			nightmareSound.stop();// stop playing the audio
		}
	}

	/**
	 * @purpose plays the sounds that appear in the doctrine
	 */
	public void playDoctrineSound() {
		if (currentMap == 4) {// in the doctrine
			if (!doctrineSound.isPlaying()) {// hasn't played audio yet
				doctrineSound.play();// play the ambient audio
			}
		} else {
			if (doctrineSound.isPlaying()) {// if it has finished playing
				doctrineSound.stop();// stop the audio
			}
		}
	}

	/**
	 * @purpose this is the method that will say that you can enter a certain area
	 *          if the confirmation menu has been selected as yes
	 * @param gp
	 * @param g2
	 */
	public void confirmationCollision(GamePanel gp, Graphics2D g2) {

		if (items.isInConfirmation()) {// in confirmation menu

			if (!isInConfirmationArea(gp)) {// if not in confirmation menu
				items.setInConfirmation(false);// get out of confirmation menu
				return;// close method
			}

			if (items.isYesPressed()) {// if yes was pressed
				items.setYesPressed(false);// reset yes
				items.setInConfirmation(false);// get out of confirmation menu
				inp.setChangeMapPressed(true);// change to a new area
				if (currentMap == 3 && doneNightmare) {// if you are at the car
					inp.setCPressed(true);// go in the car
				}
				disableConfirmation = true;

				if (gp.getWorldX() >= 450 && gp.getWorldX() <= 600 && gp.getWorldY() >= 38 && gp.getWorldY() <= 138) {// house
																														// coordinates
					enteredPlace = true;// entered house
				} else if (gp.getWorldX() >= 5584 && gp.getWorldX() <= 5684 && gp.getWorldY() >= 550
						&& gp.getWorldY() <= 650) {// doctrine coordinates
					enteredDoctrine = true;// entered doctrine
				} else if (gp.getWorldX() >= 248 && gp.getWorldX() <= 330 && gp.getWorldY() >= 216
						&& gp.getWorldY() <= 264) {// house coordinates
					exitHouse = true;// exited house
				} else if (gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
						&& gp.getWorldY() <= 638) {// car coordinates
					enteredCar = true;// entered car
				}
				return;
			}

			if (items.isNoPressed()) {// if no is pressed
				items.setNoPressed(false);// reset no pressed
				items.setInConfirmation(false);// get out of confirmation menu
				disableConfirmation = true;// disable confirmation so you actually get out of the menu
				return;// leave the method
			}
			// These change the text that appear depending on what region you are in
			if (currentMap == 3) {// open map
				items.confirmation(g2, "Do you want to", "enter this place?", 250, 225);// this confirmation menu
																						// appears
			} else if (currentMap == 2) {// in house
				items.confirmation(g2, "Do you want to", "exit the house?", 250, 240);// this confirmation menu appears
			} else if (currentMap == 4) {// in doctrine
				items.confirmation(g2, "Do you want to enter", "the exorcism Room?", 190, 210);// This confirmation menu
																								// appears
			}
			return;// leave method
		}

		if (disableConfirmation) {// leaves the method when you move outside of the boundaries
			if (!isInConfirmationArea(gp)) {// not in area
				disableConfirmation = false;// turn off confirmation menu
			}
			return;// leave method
		}

		if (currentMap == 3) {// open map
			if (!enteredPlace && gp.getWorldX() >= 450 && gp.getWorldX() <= 600 && gp.getWorldY() >= 38
					&& gp.getWorldY() <= 138) {// entrance of house
				items.setInConfirmation(true);// enable confirmation menu
			} else if (!enteredDoctrine && gp.getWorldX() >= 5584 && gp.getWorldX() <= 5684 && gp.getWorldY() >= 550
					&& gp.getWorldY() <= 650) {// entrance of doctrine
				items.setInConfirmation(true);// enable confirmation menu
			} else if (!enteredCar && gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
					&& gp.getWorldY() <= 638) {// car area
				items.setInConfirmation(true);// enable confirmation menu
			}
		} else if (currentMap == 2) {// in house
			if (!exitHouse && gp.getWorldX() >= 248 && gp.getWorldX() <= 330 && gp.getWorldY() >= 196
					&& gp.getWorldY() <= 264 && doneNightmare) {// outside of nightmare at exit of house (door)
				items.setInConfirmation(true);// enable confirmation menu
			}

		} else if (currentMap == 4) {// in doctrine
			if (gp.getWorldX() >= 838 && gp.getWorldX() <= 893 && gp.getWorldY() >= 216 && gp.getWorldY() <= 271) {// at
																													// exorcism
																													// room
																													// door
				items.setInConfirmation(true);// enable confirmation menu
			}
		}
	}

	/**
	 * @purpose checks whether you are in the area where the confirmation menu is
	 *          suppose to appear
	 * @param gp
	 * @return true or false
	 */
	private boolean isInConfirmationArea(GamePanel gp) {
		if (currentMap != 3 && currentMap != 2 && currentMap != 4)// if in none of the areas
			return false;// can't be true

		// Returns true if you are in the correct area
		return (currentMap == 3 && gp.getWorldX() >= 450 && gp.getWorldX() <= 600 && gp.getWorldY() >= 38
				&& gp.getWorldY() <= 138)// entrance house
				|| (currentMap == 3 && gp.getWorldX() >= 5584 && gp.getWorldX() <= 5684 && gp.getWorldY() >= 550
						&& gp.getWorldY() <= 650)// doctrine
				|| (currentMap == 3 && gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
						&& gp.getWorldY() <= 638)// car
				|| (currentMap == 2 && gp.getWorldX() >= 248 && gp.getWorldX() <= 330 && gp.getWorldY() >= 196
						&& gp.getWorldY() <= 264)// exit of house
				|| (currentMap == 4 && gp.getWorldX() >= 838 && gp.getWorldX() <= 893 && gp.getWorldY() >= 216
						&& gp.getWorldY() <= 271);// exorcism room
	}

	/**
	 * @purpose this is the logic and the graphics for the mirror scene
	 * @param g2
	 * @param observer
	 * @param gp
	 * @throws IOException
	 */
	public void mirrorScene(Graphics2D g2, Component observer, GamePanel gp) throws IOException {
		if (triggerTransition) {// if the user has finished the text in the exorcism room
			lookInMirror = true;// go to the mirror scene
			if (!once3) {
				npc.setTextIndex(0);// reset text index for the text in the mirror scene
				once3 = true;
			}
			fade(5, 2, g2, 384, 288, 100, 100, 1000, 1000, 100, 100, gp);// fade from the exorcism room to the mirror
																			// scene (house)
			// Teleport to these coordinates
			gp.setWorldX(212);
			gp.setWorldY(-180);
		}
		if (npc.isSurprisedText()) {// in the mirror scene
			items.houseMirror(g2);// the graphics for the mirror
			npc.text(g2, 9);// mirror scene text
		}
	}

	/**
	 * @purpose This is the method that displays the end screen and the fade into
	 *          the end screen
	 * @param g2
	 * @throws IOException
	 */
	public void endScreen(Graphics2D g2) throws IOException {
		if (end) {// in the end screen
			if (!faded2) {// hasn't faded yet
				Color fadeColor2 = new Color(0, 0, 0, fade3Value);
				g2.setColor(fadeColor2);
				g2.fillRect(0, 0, 768, 576);
				fade3Value += 2;
				if (fade3Value >= 255) {// prevents the user from exceeding the maximum transparency (255)
					fade3Value = 255;
					faded2 = true;// has faded in
				}
			}
			if (faded2) {// if has faded in, display the exit button and the thanks for playing text
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
			if (inp.isPressedQuit()) {// if the exit button is pressed
				System.exit(0);// close the program
			}
		}
	}

	/**
	 * @purpose makes the game very moody with a black tint on the screen
	 * @param g2
	 * @param gp
	 */
	public void drawTint(Graphics2D g2, GamePanel gp) {
		// How to use gradients:
		// https://docs.oracle.com/javase/7/docs/api/java/awt/RadialGradientPaint.html
		Point2D centerPoint = new Point2D.Float(gp.getPlayerX(), gp.getPlayerY());
		float tint = 350;

		Color transparentColor = new Color(0, 0, 0, 200);
		Color darkColor = new Color(0, 0, 0, 255); // Dark color with transparency

		RadialGradientPaint gradient = new RadialGradientPaint(centerPoint, tint,
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

	// Getter and Setter Methods

	/**
	 * 
	 * @return doneNightmare
	 */
	public boolean isDoneNightmare() {
		return doneNightmare;
	}

	/**
	 *
	 * @return lookInMirror
	 */
	public boolean isLookInMirror() {
		return lookInMirror;
	}

	/**
	 * 
	 * @param lookInMirror
	 */
	public void setLookInMirror(boolean lookInMirror) {
		this.lookInMirror = lookInMirror;
	}

	/**
	 * 
	 * @param t
	 */
	public void setT(Tiles t) {
		this.t = t;
	}

	/**
	 * 
	 * @param npc
	 */
	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	/**
	 * 
	 * @param j
	 */
	public void setJ(Jumpscare j) {
		this.j = j;
	}

	/**
	 * 
	 * @param p
	 */
	public void setP(Player p) {
		this.p = p;
	}

	/**
	 * 
	 * @param items
	 */
	public void setItems(Items items) {
		this.items = items;
	}

	/**
	 * 
	 * @param inp
	 */
	public void setInp(Input inp) {
		this.inp = inp;
	}

	/**
	 * 
	 * @return tiles
	 */
	public ArrayList<ArrayList<Integer>> getTiles() {
		return tiles;
	}

	/**
	 * 
	 * @return treePositions
	 */
	public ArrayList<int[]> getTreePositions() {
		return treePositions;
	}

	/**
	 * 
	 * @return housePositions
	 */
	public ArrayList<int[]> getHousePositions() {
		return housePositions;
	}

	/**
	 * 
	 * @return bedPositions
	 */
	public ArrayList<int[]> getBedPositions() {
		return bedPositions;
	}

	/**
	 * 
	 * @return houseWallPositions
	 */
	public ArrayList<int[]> getHouseWallPositions() {
		return houseWallPositions;
	}

	/**
	 * 
	 * @return grassPositions
	 */
	public ArrayList<int[]> getGrassPositions() {
		return grassPositions;
	}

	/**
	 * 
	 * @return waterPositions
	 */
	public ArrayList<int[]> getWaterPositions() {
		return waterPositions;
	}

	/**
	 * 
	 * @return bookPositions
	 */
	public ArrayList<int[]> getBookPositions() {
		return bookPositions;
	}

	/**
	 * 
	 * @return currentMap
	 */
	public int getCurrentMap() {
		return currentMap;
	}

	/**
	 * 
	 * @return stepCount
	 */
	public int getStepCount() {
		return stepCount;
	}

	/**
	 * 
	 * @param nightmare
	 */
	public void setNightmare(BufferedImage nightmare) {
		this.nightmare = nightmare;
	}

	/**
	 * 
	 * @param doneNightmare
	 */
	public void setDoneNightmare(boolean doneNightmare) {
		this.doneNightmare = doneNightmare;
	}

	/**
	 * 
	 * @param hasFaded
	 */
	public void setHasFaded(int hasFaded) {
		this.hasFaded = hasFaded;
	}

	/**
	 * 
	 * @return hasFaded
	 */
	public int getHasFaded() {
		return hasFaded;
	}

	/**
	 * 
	 * @param usingBed
	 */
	public void setUsingBed(boolean usingBed) {
		this.usingBed = usingBed;
	}

	/**
	 * 
	 * @return inNightmare
	 */
	public boolean isInNightmare() {
		return inNightmare;
	}

	/**
	 * 
	 * @return fading
	 */
	public boolean isFading() {
		return fading;
	}

	/**
	 * 
	 * @param fading
	 */
	public void setFading(boolean fading) {
		this.fading = fading;
	}

	/**
	 * 
	 * @return doneDoctorDead
	 */
	public boolean isDoneDoctorDead() {
		return doneDoctorDead;
	}

	/**
	 * 
	 * @param doneDoctorDead
	 */
	public void setDoneDoctorDead(boolean doneDoctorDead) {
		this.doneDoctorDead = doneDoctorDead;
	}

	/**
	 * 
	 * @return triggerTransition
	 */
	public boolean isTriggerTransition() {
		return triggerTransition;
	}

	/**
	 * 
	 * @param triggerTransition
	 */
	public void setTriggerTransition(boolean triggerTransition) {
		this.triggerTransition = triggerTransition;
	}

	/**
	 * 
	 * @param end
	 */
	public void setEnd(boolean end) {
		this.end = end;
	}

	/**
	 * 
	 * @return end
	 */
	public boolean isEnd() {
		return end;
	}

	/**
	 * 
	 * @return hasJumpscared
	 */
	public boolean isHasJumpscared() {
		return hasJumpscared;
	}

	/**
	 * 
	 * @param hasJumpscared
	 */
	public void setHasJumpscared(boolean hasJumpscared) {
		this.hasJumpscared = hasJumpscared;
	}

	/**
	 * 
	 * @return hasDoctrined
	 */
	public boolean isHasDoctrined() {
		return hasDoctrined;
	}

	/**
	 * 
	 * @param hasDoctrined
	 */
	public void setHasDoctrined(boolean hasDoctrined) {
		this.hasDoctrined = hasDoctrined;
	}
}
