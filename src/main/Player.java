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

/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class Player {

	int worldX;
	int worldY;

	private int screenX;
	private int screenY;

	private int playerX;
	private int playerY;

	// Players initial position
	private int beforeCollisionX = worldX;
	private int beforeCollisionY = worldY;

	Npc n;
	Maps m;
	Items it;
	Input keyH;
	Sound walkingSound;
	Sound carSound;
	private BufferedImage character;

	public boolean collision = false;
	public boolean inventoryCollision = false;

	public Player(GamePanel gp) {
		// keyH = gp.id;
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
		try {
			// https://www.youtube.com/watch?v=6LOm1ZlE39I
			walkingSound = new Sound("src/sound/walkingSoundEffect.wav");

			// https://www.youtube.com/watch?v=O8s6HkPZ3Io
			carSound = new Sound("src/sound/carSound.wav");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading sound file: " + e.getMessage());
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

	public void collisionChecking() {

		collision = false;
		for (int i = 0; i < m.getTreePositions().size(); i++) {
			int treeX = m.getTreePositions().get(i)[0] - worldX;
			int treeY = m.getTreePositions().get(i)[1] - worldY;

			if (screenX + 32 > treeX // Right side of hitbox is past left side of tree
					&& screenX < treeX + 48 // Left side of hitbox is before right side of tree
					&& screenY + 72 > treeY // Bottom side of hitbox is below top side of tree
					&& screenY < treeY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.getHousePositions().size(); i++) {
			int houseX = m.getHousePositions().get(i)[0] - worldX;
			int houseY = m.getHousePositions().get(i)[1] - worldY;
			if (playerX + 32 > houseX // Right side of hitbox is past left side of tree
					&& playerX < houseX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > houseY // Bottom side of hitbox is below top side of tree
					&& playerY < houseY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.getBedPositions().size(); i++) {
			int bedX = m.getBedPositions().get(i)[0] - worldX;
			int bedY = m.getBedPositions().get(i)[1] - worldY;
			if (playerX + 32 > bedX // Right side of hitbox is past left side of tree
					&& playerX < bedX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > bedY // Bottom side of hitbox is below top side of tree
					&& playerY < bedY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				m.setUsingBed(true);
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.getHouseWallPositions().size(); i++) {
			int houseWallX = m.getHouseWallPositions().get(i)[0] - worldX;
			int houseWallY = m.getHouseWallPositions().get(i)[1] - worldY;
			if (playerX + 32 > houseWallX // Right side of hitbox is past left side of tree
					&& playerX < houseWallX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > houseWallY // Bottom side of hitbox is below top side of tree
					&& playerY < houseWallY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.getGrassPositions().size(); i++) {
			int grassX = m.getGrassPositions().get(i)[0] - worldX;
			int grassY = m.getGrassPositions().get(i)[1] - worldY;
			if (screenX + 32 > grassX // Right side of hitbox is past left side of tree
					&& screenX < grassX + 48 // Left side of hitbox is before right side of tree
					&& screenY + 72 > grassY + 30 // Bottom side of hitbox is below top side of tree
					&& screenY < grassY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.getWaterPositions().size(); i++) {
			int waterX = m.getWaterPositions().get(i)[0] - worldX;
			int waterY = m.getWaterPositions().get(i)[1] - worldY;
			if (playerX + 32 > waterX // Right side of hitbox is past left side of tree
					&& playerX < waterX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > waterY + 48 // Bottom side of hitbox is below top side of tree
					&& playerY < waterY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.getBookPositions().size(); i++) {
			int bookX = m.getBookPositions().get(i)[0] - worldX;
			int bookY = m.getBookPositions().get(i)[1] - worldY;
			if (playerX + 32 > bookX // Right side of hitbox is past left side of tree
					&& playerX < bookX + 96 // Left side of hitbox is before right side of tree
					&& playerY + 72 > bookY + 48 // Bottom side of hitbox is below top side of tree
					&& playerY < bookY + 48 && keyH.isUseBookPressed()) { // Top side of hitbox is above
																						// bottom side of tree
				it.setEnterBook(true);
				break; // Stop checking after finding the first collision
			}
		}
		if (keyH.getMouseX() + 32 > 165 // Right side of hitbox is past left side of tree
				&& keyH.getMouseX() < 165 + 200 // Left side of hitbox is before right side of tree
				&& keyH.getMouseY() + 72 > 160 // Bottom side of hitbox is below top side of tree
				&& keyH.getMouseY() < 160 + 70) { // Top side of hitbox is above bottom side of tree
			inventoryCollision = true;
		} else {

			inventoryCollision = false;

		}

		if (worldX + 384 + 32 > it.getCarWorldX() && // Player's right side > car's left side
				worldX + 384 < it.getCarWorldX() + 96 && // Player's left side < car's right side
				worldY + 288 > it.getCarWorldY() && // Player's bottom side > car's top side
				worldY + 288 + 72 < it.getCarWorldY() + 192 + 30) { // Player's bottom side < car's bottom side
			collision = true;
		}
		if (worldX + 384 > it.getDoctrineWorldX() && // Player's right side > doctrine's left side
				worldX + 384 < it.getDoctrineWorldX() + 260 && // Player's left side < doctrine's right side
				worldY + 288 + 45 > it.getDoctrineWorldY() && // Player's bottom side > doctrine's top side
				worldY + 288 < it.getDoctrineWorldY() + 420) { // Player's top side < doctrine's bottom side
			collision = true;
		}
		if (worldX + 384 - 300 > it.getGraveX() && // Right-shifted point > grave's left
				worldX + 384 - 300 < it.getGraveX() + 160 && // Right-shifted point < grave's right
				worldY + 288 - 150 > it.getGraveY() && // Down-shifted point > grave's top
				worldY + 288 - 150 < it.getGraveY() + 120 // Down-shifted point < grave's bottom
				&& it.isFirstTime()) {
			it.setInGraveYard(true);
		} else {
			it.setInGraveYard(false);
		}
	}

	public boolean getCollision() {

		return collision;

	}

	public void collision(GamePanel gp) {
		if (!collision) {
			beforeCollisionX = worldX;
			beforeCollisionY = worldY;
		} else {
			gp.setWorldX(beforeCollisionX);
			gp.setWorldY(beforeCollisionY);
		}
	}

	public void loadCharacterImages() throws IOException {
		character = ImageIO.read(new File("src/textures/character.png"));
	}

	public BufferedImage getCharacter() {
		return character;
	}

	public void inventory(Graphics g) {
		Color inventoryBackground = new Color(58, 61, 66, 250);
		Font inventoryHeader = new Font("arial", Font.BOLD, 45);
		Font regular = new Font("calibri", Font.BOLD, 30);
		Graphics2D g3 = (Graphics2D) g;
		g3.setFont(inventoryHeader);
		g3.setColor(inventoryBackground);
		g3.fillRect(75, 40, 618, 470);
		g3.setColor(Color.black);
		// Outline (frame) around the inventory
		for (int i = 0; i < 4; i++) {
			g3.drawRect(75 + i, 40 + i, 618 - 2 * i, 470 - 2 * i);
		}
		g3.drawString("Inventory", 280, 110);
		g3.setColor(Color.white);
		g3.fillRoundRect(125, 160, 80, 70, 15, 15);
		g3.setColor(Color.black);

	}

	public void footStepSounds() {
		if (keyH.isRightPressed() || keyH.isLeftPressed() || keyH.isUpPressed() || keyH.isDownPressed()) {
			if (!walkingSound.isPlaying()) {
				walkingSound.volume(-10.0f); // Set volume to a lower level
				walkingSound.play();
			}
		} else if (keyH.isRightReleased() || keyH.isLeftReleased() || keyH.isUpReleased() || keyH.isDownReleased()) {
			walkingSound.stop();
		}
	}

	public void carSound() {
		if (it.isCarOn()) {
			if (!carSound.isPlaying()) {
				carSound.play();
			}
		} else if (!it.isCarOn()) {
			carSound.stop();
		}

	}

	public boolean disableCharacterMovement() {

		if (m.fading) {
			return true;
		}
		if (it.isCarOn()) {
			return true;
		}
		if (it.isInGraveYard()) {
			return true;
		} if (m.getCurrentMap() == 5) {
			return true;
		}
		return false; 	
	}
	
	public void setN(Npc n) {
		this.n = n;
	}

	public void setM(Maps m) {
		this.m = m;
	}

	public void setIt(Items it) {
		this.it = it;
	}

	public void setKeyH(Input keyH) {
		this.keyH = keyH;
	}
	
}
