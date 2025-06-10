package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Horror.Jumpscare;
import javax.imageio.ImageIO;

import interfaces.ReadFromFile;

/**
 * @author Noah Sussman, Akhilan Saravanan and Rudra Garg Ms. Krasteva
 * @since April 2, 2025 Final Project ICS4U0
 */
public class Player implements ReadFromFile{

	int worldX; // Player's world X position
	int worldY; // Player's world Y position

	private int screenX; // Player's screen X position
	private int screenY; // Player's screen Y position

	private int playerX; // Player's X position in the game world
	private int playerY; // Player's Y position in the game world

	// Players initial position
	private int beforeCollisionX = worldX;// initial X position before collision occurs
	private int beforeCollisionY = worldY;// initial Y position before collision occurs

	Npc n;// Reference to the Npc class to access Npc methods and properties
	Maps m; // Reference to the Maps class to access maps methods and properties
	Items it; // Reference to the Items class to access Items methods and properties
	Input keyH; // Reference to the Input class to access Input methods and properties
	Sound walkingSound;
	Sound carSound;
	private BufferedImage character;

	private boolean collision = false;// when this variable is true then the user will bounce back to beforeCollision
										// as collision has been detected
	private boolean once = false; // Used to ensure the text only appears once when entering the graveyard

	/**
	 * Constructor for the Player class.
	 * 
	 * @param gp
	 */
	public Player(GamePanel gp) {
		m = gp.getM();
		keyH = gp.getId();
		n = gp.getN();
		it = gp.getIt();

		worldX = gp.getWorldX();
		worldY = gp.getWorldY();

		screenX = gp.getScreenX();
		screenY = gp.getScreenY();

		playerX = gp.getPlayerX();
		playerY = gp.getPlayerY();
		
		readFile(); // Calls the readFile method to load resources
	}
	
	@Override
	public void readFile() {
		try {
			// Audio of footsteps play when the user walks
			// https://www.youtube.com/watch?v=6LOm1ZlE39I
			walkingSound = new Sound("sound/walkingSoundEffect.wav");

			// Audio of car moving when the car moves in the game
			// https://www.youtube.com/watch?v=O8s6HkPZ3Io
			carSound = new Sound("sound/carSound.wav");
			
			character = ImageIO.read(new File("textures/character.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePlayerPosition(int x, int y, int playerX, int playerY, int screenX, int screenY) {
		this.worldX = x;
		this.worldY = y;

		this.playerX = playerX;
		this.playerY = playerY;

		this.screenX = screenX;
		this.screenY = screenY;

	}

	public void collisionChecking(GamePanel gp) {

		collision = false;// resetting collision
		// Checks for collision with all of the trees on the map that are within the
		// current screen
		for (int i = 0; i < m.getTreePositions().size(); i++) {
			int treeX = m.getTreePositions().get(i)[0] - worldX;// this tiles x position
			int treeY = m.getTreePositions().get(i)[1] - worldY;// this tiles y position

			if (screenX + 32 > treeX // Right side of hitbox is past left side of tree
					&& screenX < treeX + 48 // Left side of hitbox is before right side of tree
					&& screenY + 72 > treeY // Bottom side of hitbox is below top side of tree
					&& screenY < treeY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;// collision detected
				break; // Stop checking after finding the first collision
			}
		}
		// Checks for collision with all of the house tiles on the map that are within
		// the current screen
		for (int i = 0; i < m.getHousePositions().size(); i++) {
			int houseX = m.getHousePositions().get(i)[0] - worldX;// this tiles x position
			int houseY = m.getHousePositions().get(i)[1] - worldY;// this tiles y position
			if (playerX + 32 > houseX // Right side of hitbox is past left side of house
					&& playerX < houseX + 48 // Left side of hitbox is before right side of house
					&& playerY + 72 > houseY // Bottom side of hitbox is below top side of house
					&& playerY < houseY + 48) { // Top side of hitbox is above bottom side of house
				collision = true;// collision detected
				break; // Stop checking after finding the first collision
			}
		}
		// Checks for collision with all of the bed tiles on the map that are within the
		// current screen
		for (int i = 0; i < m.getBedPositions().size(); i++) {
			int bedX = m.getBedPositions().get(i)[0] - worldX;// this tiles x position
			int bedY = m.getBedPositions().get(i)[1] - worldY;// this tiles y position
			if (playerX + 32 > bedX // Right side of hitbox is past left side of bed
					&& playerX < bedX + 48 // Left side of hitbox is before right side of bed
					&& playerY + 72 > bedY // Bottom side of hitbox is below top side of bed
					&& playerY < bedY + 48) { // Top side of hitbox is above bottom side of bed
				collision = true;// collision detected
				m.setUsingBed(true);// sets usingBed to true (will go into nightmare)
				break; // Stop checking after finding the first collision
			}
		}
		// Checks for collision with all of the house wall tiles on the map that are
		// within the current screen
		for (int i = 0; i < m.getHouseWallPositions().size(); i++) {
			int houseWallX = m.getHouseWallPositions().get(i)[0] - worldX;// this tiles x position
			int houseWallY = m.getHouseWallPositions().get(i)[1] - worldY;// this tiles y position
			if (playerX + 32 > houseWallX // Right side of hitbox is past left side of house wall
					&& playerX < houseWallX + 48 // Left side of hitbox is before right side of house wall
					&& playerY + 72 > houseWallY // Bottom side of hitbox is below top side of house wall
					&& playerY < houseWallY + 48) { // Top side of hitbox is above bottom side of house wall
				collision = true;// collision detected
				break; // Stop checking after finding the first collision
			}
		}
		// Checks for collision with all of the grass tiles on the map that are within
		// the current screen
		for (int i = 0; i < m.getGrassPositions().size(); i++) {
			int grassX = m.getGrassPositions().get(i)[0] - worldX;// tiles x position
			int grassY = m.getGrassPositions().get(i)[1] - worldY;// tiles y position
			if (screenX + 32 > grassX // Right side of hitbox is past left side of grass
					&& screenX < grassX + 48 // Left side of hitbox is before right side of grass
					&& screenY + 72 > grassY + 30 // Bottom side of hitbox is below top side of grass
					&& screenY < grassY + 48) { // Top side of hitbox is above bottom side of grass
				collision = true;// collision detected
				break; // Stop checking after finding the first collision
			}
		}
		// Checks for collision with all of the water tiles on the map that are within
		// the current screen
		for (int i = 0; i < m.getWaterPositions().size(); i++) {
			int waterX = m.getWaterPositions().get(i)[0] - worldX;// tiles x position
			int waterY = m.getWaterPositions().get(i)[1] - worldY;// tiles y position
			if (playerX + 32 > waterX // Right side of hitbox is past left side of water
					&& playerX < waterX + 48 // Left side of hitbox is before right side of water
					&& playerY + 72 > waterY + 48 // Bottom side of hitbox is below top side of water
					&& playerY < waterY + 48) { // Top side of hitbox is above bottom side of water
				collision = true;// collision detected
				break; // Stop checking after finding the first collision
			}
		}
		// Checks for collision with all of the book tiles on the map that are within
		// the current screen
		for (int i = 0; i < m.getBookPositions().size(); i++) {
			int bookX = m.getBookPositions().get(i)[0] - worldX;// tiles x position
			int bookY = m.getBookPositions().get(i)[1] - worldY;// tiles y position
			if (playerX + 32 > bookX // Right side of hitbox is past left side of book
					&& playerX < bookX + 96 // Left side of hitbox is before right side of book
					&& playerY + 72 > bookY + 48 // Bottom side of hitbox is below top side of book
					&& playerY < bookY + 48 && keyH.isUseBookPressed()) { // Top side of hitbox is above
																			// bottom side of book
				it.setEnterBook(true);// the book has been opened
				break; // Stop checking after finding the first collision
			}
		}

		// Collision of the car
		if (worldX + 384 + 32 > it.getCarWorldX() && // Right side of hitbox is past left side of car
				worldX + 384 < it.getCarWorldX() + 96 && // Left side of hitbox is before right side of car
				worldY + 288 > it.getCarWorldY() && // Bottom side of hitbox is below top side of car
				worldY + 288 + 72 < it.getCarWorldY() + 192 + 30) { // Top side of hitbox is above bottom side of car
			collision = true;// collision is true
		}
		// Collision of doctrine
		if (worldX + 384 > it.getDoctrineWorldX() && // Right side of hitbox is past left side of doctrine
				worldX + 384 < it.getDoctrineWorldX() + 260 && // Left side of hitbox is before right side of doctrine
				worldY + 288 + 45 > it.getDoctrineWorldY() && // Bottom side of hitbox is below top side of doctrine
				worldY + 288 < it.getDoctrineWorldY() + 420) { // Top side of hitbox is above bottom side of doctrine
			collision = true;// collision is true
		}
		if (worldX + 384 - 300 > it.getGraveX() && // Right side of hitbox is past left side of graveyard entrance
				worldX + 384 - 300 < it.getGraveX() + 160 && // Left side of hitbox is before right side of graveyard
																// entrance
				worldY + 288 - 150 > it.getGraveY() && // Bottom side of hitbox is below top side of graveyard entrance
				worldY + 288 - 150 < it.getGraveY() + 120 // Top side of hitbox is above bottom side of graveyard
															// entrance
				&& it.isFirstTime()) {// only runs this collision on first entry
			if (!once) {// will only run once
				n.setTextIndex(0);// resets text index for future use of text index
				once = true;
			}
			it.setInGraveYard(true);// now in graveyard
		} else {
			it.setInGraveYard(false);// otherwise not in the graveyard
		}
		blockOffAreas(gp);// block off all areas that the user is not suppose to be in
							// such as blocking off the doctrine when in the graveyard
	}

	/**
	 * @purpose This method will translate your X and Y position back to before
	 *          collision if collision is true. Otherwise, nothing changes
	 * @param gp
	 */
	public void collision(GamePanel gp) {
		if (!collision) {
			beforeCollisionX = worldX;
			beforeCollisionY = worldY;
		} else {
			gp.setWorldX(beforeCollisionX);
			gp.setWorldY(beforeCollisionY);
		}
	}



	/**
	 * @purpose plays feetstep volumes when a movement key is pressed
	 */
	public void footStepSounds() {
		if (keyH.isRightPressed() || keyH.isLeftPressed() || keyH.isUpPressed() || keyH.isDownPressed()) {
			if (!walkingSound.isPlaying()) {//hasn't played audio yet
				walkingSound.volume(-10.0f); // Set volume to a lower level
				walkingSound.play();//plays the sound
			}
		} else if (keyH.isRightReleased() || keyH.isLeftReleased() || keyH.isUpReleased() || keyH.isDownReleased()) {//movement key is released
			walkingSound.stop();//stops the audio sound once the movement keys are released
		}
	}
	/**
	 * @purpose plays the car sound when in car 
	 */
	public void carSound() {
		if (it.isCarOn()) {//if car is on
			if (!carSound.isPlaying()) {//not playing the audio
				carSound.play();//play the car audio
			}
		} else if (!it.isCarOn()) {//if the car is not on
			carSound.stop();//stop the car audio
		}

	}
/**
 * @purpose blocks of areas that are not meant to be accessible at certain moments
 * @param gp
 */
	public void blockOffAreas(GamePanel gp) {
		if (gp.getWorldX() >= 459 && gp.getWorldX() <= 734 && gp.getWorldY() >= 488 && gp.getWorldY() <= 663
				&& !m.isDoneNightmare()) {//block off car area
			collision = true;//collision is true
		}
		if (gp.getWorldX() >= 4864 && gp.getWorldX() <= 4939 && gp.getWorldY() >= 563 && gp.getWorldY() <= 663
				&& !keyH.isReadBook()) {//block off doctrine area
			collision = true;//collision is true
		}
		if (gp.getWorldX() >= 4000 && gp.getWorldX() <= 4150 && gp.getWorldY() >= -12 && gp.getWorldY() <= 788
				&& !it.isCarOn()) {//block off graveyard area
			collision = true;//collision is true
		}
	}

	/**
	 * @purpose disables character movement in certain areas
	 * @return whether character can move or not (true or false)
	 */
	public boolean disableCharacterMovement() {
		if (m.isLookInMirror()) {//in mirror scene
			return true;
		}
		if (m.isFading()) {//if fading
			return true;
		}
		if (it.isCarOn()) {//in car
			return true;
		}
		if (it.isInGraveYard()) {//in dialogue with ghost in graveyard
			return true;
		}
		if (m.getCurrentMap() == 4 && it.isDoingDoctrineGhost()) {//talking to doctrine ghost
			return true;
		}
		if (m.getCurrentMap() == 5) {//in exorcism true
			return true;
		}
		return false;//can move
	}

	// Getter and setter methods

	/**
	 * 
	 * @return collision
	 */
	public boolean getCollision() {

		return collision;

	}

	/**
	 * 
	 * @param n
	 */
	public void setN(Npc n) {
		this.n = n;
	}

	/**
	 * 
	 * @param m
	 */
	public void setM(Maps m) {
		this.m = m;
	}

	/**
	 * 
	 * @param it
	 */
	public void setIt(Items it) {
		this.it = it;
	}

	/**
	 * 
	 * @return character
	 */
	public BufferedImage getCharacter() {
		return character;
	}

	/**
	 * 
	 * @param keyH
	 */
	public void setKeyH(Input keyH) {
		this.keyH = keyH;
	}

}
