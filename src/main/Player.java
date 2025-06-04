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
	public BufferedImage buisnessMan;

	public boolean collision = false;
	public boolean inventoryCollision = false;

	public Player(GamePanel gp) {
		// keyH = gp.id;
		m = gp.m;
		keyH = gp.id;
		n = gp.n;
		it = gp.it;

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
		for (int i = 0; i < m.treePositions.size(); i++) {
			int treeX = m.treePositions.get(i)[0] - worldX;
			int treeY = m.treePositions.get(i)[1] - worldY;

			if (screenX + 32 > treeX // Right side of hitbox is past left side of tree
					&& screenX < treeX + 48 // Left side of hitbox is before right side of tree
					&& screenY + 72 > treeY // Bottom side of hitbox is below top side of tree
					&& screenY < treeY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.housePositions.size(); i++) {
			int houseX = m.housePositions.get(i)[0] - worldX;
			int houseY = m.housePositions.get(i)[1] - worldY;
			if (playerX + 32 > houseX // Right side of hitbox is past left side of tree
					&& playerX < houseX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > houseY // Bottom side of hitbox is below top side of tree
					&& playerY < houseY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.bedPositions.size(); i++) {
			int bedX = m.bedPositions.get(i)[0] - worldX;
			int bedY = m.bedPositions.get(i)[1] - worldY;
			if (playerX + 32 > bedX // Right side of hitbox is past left side of tree
					&& playerX < bedX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > bedY // Bottom side of hitbox is below top side of tree
					&& playerY < bedY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				m.usingBed = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.houseWallPositions.size(); i++) {
			int houseWallX = m.houseWallPositions.get(i)[0] - worldX;
			int houseWallY = m.houseWallPositions.get(i)[1] - worldY;
			if (playerX + 32 > houseWallX // Right side of hitbox is past left side of tree
					&& playerX < houseWallX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > houseWallY // Bottom side of hitbox is below top side of tree
					&& playerY < houseWallY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.grassPositions.size(); i++) {
			int grassX = m.grassPositions.get(i)[0] - worldX;
			int grassY = m.grassPositions.get(i)[1] - worldY;
			if (screenX + 32 > grassX // Right side of hitbox is past left side of tree
					&& screenX < grassX + 48 // Left side of hitbox is before right side of tree
					&& screenY + 72 > grassY + 30 // Bottom side of hitbox is below top side of tree
					&& screenY < grassY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.waterPositions.size(); i++) {
			int waterX = m.waterPositions.get(i)[0] - worldX;
			int waterY = m.waterPositions.get(i)[1] - worldY;
			if (playerX + 32 > waterX // Right side of hitbox is past left side of tree
					&& playerX < waterX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > waterY + 48 // Bottom side of hitbox is below top side of tree
					&& playerY < waterY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < m.bookPositions.size(); i++) {
			int bookX = m.bookPositions.get(i)[0] - worldX;
			int bookY = m.bookPositions.get(i)[1] - worldY;
			if (playerX + 32 > bookX // Right side of hitbox is past left side of tree
					&& playerX < bookX + 48 // Left side of hitbox is before right side of tree
					&& playerY + 72 > bookY + 48 // Bottom side of hitbox is below top side of tree
					&& playerY < bookY + 48 && keyH.useBookPressed) { // Top side of hitbox is above
																						// bottom side of tree
				it.enterBook = true;
				break; // Stop checking after finding the first collision
			}
		}
		if (keyH.mouseX + 32 > 165 // Right side of hitbox is past left side of tree
				&& keyH.mouseX < 165 + 200 // Left side of hitbox is before right side of tree
				&& keyH.mouseY + 72 > 160 // Bottom side of hitbox is below top side of tree
				&& keyH.mouseY < 160 + 70) { // Top side of hitbox is above bottom side of tree
			inventoryCollision = true;
		} else {

			inventoryCollision = false;

		}

		if (worldX + 384 + 32 > it.carWorldX && // Player's right side > car's left side
				worldX + 384 < it.carWorldX + 96 && // Player's left side < car's right side
				worldY + 288 > it.carWorldY && // Player's bottom side > car's top side
				worldY + 288 + 72 < it.carWorldY + 192 + 30) { // Player's bottom side < car's bottom side
			collision = true;
		}
		if (worldX + 384 > it.doctrineWorldX && // Player's right side > doctrine's left side
				worldX + 384 < it.doctrineWorldX + 260 && // Player's left side < doctrine's right side
				worldY + 288 + 45 > it.doctrineWorldY && // Player's bottom side > doctrine's top side
				worldY + 288 < it.doctrineWorldY + 420) { // Player's top side < doctrine's bottom side
			collision = true;
		}
		if (worldX + 384 - 300 > it.graveX && // Right-shifted point > grave's left
				worldX + 384 - 300 < it.graveX + 160 && // Right-shifted point < grave's right
				worldY + 288 - 150 > it.graveY && // Down-shifted point > grave's top
				worldY + 288 - 150 < it.graveY + 120 // Down-shifted point < grave's bottom
				&& it.firstTime) {
			it.inGraveYard = true;
		} else {
			it.inGraveYard = false;
		}

		n.npcs();
	}

	public boolean getCollision() {

		return collision;

	}

	public void collision(GamePanel gp) {
		if (!collision && !n.collisionNpc) {
			beforeCollisionX = worldX;
			beforeCollisionY = worldY;
		} else {
			gp.setWorldX(beforeCollisionX);
			gp.setWorldY(beforeCollisionY);
		}
	}

	public void loadCharacterImages() throws IOException {

		character = ImageIO.read(new File("src/textures/character.png"));
		buisnessMan = ImageIO.read(new File("src/textures/unknownBuisnessMan.png"));

	}

	public void drawNpcs(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		// Buisness man
		g2.drawImage(buisnessMan, 550, 120, 50, 78, null);

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
		if (keyH.rightPressed || keyH.leftPressed || keyH.upPressed || keyH.downPressed) {
			if (!walkingSound.isPlaying()) {
				walkingSound.volume(-10.0f); // Set volume to a lower level
				walkingSound.play();
			}
		} else if (keyH.rightReleased || keyH.leftReleased || keyH.upReleased || keyH.downReleased) {
			walkingSound.stop();
		}
	}

	public void carSound() {
		if (it.carOn) {
			if (!carSound.isPlaying()) {
				carSound.play();
			}
		} else if (!it.carOn) {
			carSound.stop();
		}

	}

	public boolean disableCharacterMovement() {

		if (m.fading) {
			return true;
		}
		
		if (it.carOn) {
			return true;
		}
		if (it.carWorldX == 4700) {
			return true;
		}
		if (it.inGraveYard) {
			return true;
		} if (m.currentMap == 5) {
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
