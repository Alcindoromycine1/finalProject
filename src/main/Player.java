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

	public static Input keyH = new Input();// Creates an object to call
	Maps m = new Maps();
	// Players initial position
	private int beforeCollisionX = GamePanel.worldX;
	private int beforeCollisionY = GamePanel.worldY;
	Jumpscare j = new Jumpscare();
	Input input = new Input();
	Npc n = new Npc(input);
	Items it = new Items();

	private BufferedImage character;
	public static BufferedImage buisnessMan;

	public static boolean collision = false;
	public boolean inventoryCollision = false;

	public Player(Input input) {
		this.input = input;
	}

	public Player() {

	}

	public void collisionChecking() {

		collision = false;
		for (int i = 0; i < Maps.treePositions.size(); i++) {
			int treeX = Maps.treePositions.get(i)[0] - GamePanel.worldX;
			int treeY = Maps.treePositions.get(i)[1] - GamePanel.worldY;

			if (GamePanel.screenX + 32 > treeX // Right side of hitbox is past left side of tree
					&& GamePanel.screenX < treeX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.screenY + 72 > treeY // Bottom side of hitbox is below top side of tree
					&& GamePanel.screenY < treeY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.housePositions.size(); i++) {
			int houseX = Maps.housePositions.get(i)[0] - GamePanel.worldX;
			int houseY = Maps.housePositions.get(i)[1] - GamePanel.worldY;
			if (GamePanel.playerX + 32 > houseX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < houseX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > houseY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < houseY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.bedPositions.size(); i++) {
			int bedX = Maps.bedPositions.get(i)[0] - GamePanel.worldX;
			int bedY = Maps.bedPositions.get(i)[1] - GamePanel.worldY;
			if (GamePanel.playerX + 32 > bedX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < bedX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > bedY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < bedY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				Maps.usingBed = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.houseWallPositions.size(); i++) {
			int houseWallX = Maps.houseWallPositions.get(i)[0] - GamePanel.worldX;
			int houseWallY = Maps.houseWallPositions.get(i)[1] - GamePanel.worldY;
			if (GamePanel.playerX + 32 > houseWallX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < houseWallX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > houseWallY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < houseWallY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.grassPositions.size(); i++) {
			int grassX = Maps.grassPositions.get(i)[0] - GamePanel.worldX;
			int grassY = Maps.grassPositions.get(i)[1] - GamePanel.worldY;
			if (GamePanel.screenX + 32 > grassX // Right side of hitbox is past left side of tree
					&& GamePanel.screenX < grassX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.screenY + 72 > grassY + 30 // Bottom side of hitbox is below top side of tree
					&& GamePanel.screenY < grassY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.waterPositions.size(); i++) {
			int waterX = Maps.waterPositions.get(i)[0] - GamePanel.worldX;
			int waterY = Maps.waterPositions.get(i)[1] - GamePanel.worldY;
			if (GamePanel.playerX + 32 > waterX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < waterX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > waterY + 48 // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < waterY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.bookPositions.size(); i++) {
			int bookX = Maps.bookPositions.get(i)[0] - GamePanel.worldX;
			int bookY = Maps.bookPositions.get(i)[1] - GamePanel.worldY;
			if (GamePanel.playerX + 32 > bookX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < bookX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > bookY + 48 // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < bookY + 48 && Player.keyH.useBookPressed) { // Top side of hitbox is above
																						// bottom side of tree
				Items.enterBook = true;
				break; // Stop checking after finding the first collision
			}
		}
		if (input.mouseX + 32 > 165 // Right side of hitbox is past left side of tree
				&& input.mouseX < 165 + 200 // Left side of hitbox is before right side of tree
				&& input.mouseY + 72 > 160 // Bottom side of hitbox is below top side of tree
				&& input.mouseY < 160 + 70) { // Top side of hitbox is above bottom side of tree
			inventoryCollision = true;
		} else {

			inventoryCollision = false;

		}

		if (GamePanel.worldX + 384 + 32 > Items.carWorldX && // Player's right side > car's left side
				GamePanel.worldX + 384 < Items.carWorldX + 96 && // Player's left side < car's right side
				GamePanel.worldY + 288 > Items.carWorldY && // Player's bottom side > car's top side
				GamePanel.worldY + 288 + 72 < Items.carWorldY + 192 + 30) { // Player's bottom side < car's bottom side
			collision = true;
		}
		if (GamePanel.worldX + 384 > Items.doctrineWorldX && // Player's right side > doctrine's left side
				GamePanel.worldX + 384 < Items.doctrineWorldX + 260 && // Player's left side < doctrine's right side
				GamePanel.worldY + 288 + 45 > Items.doctrineWorldY && // Player's bottom side > doctrine's top side
				GamePanel.worldY + 288 < Items.doctrineWorldY + 420) { // Player's top side < doctrine's bottom side
			collision = true;
		}
		if (GamePanel.worldX + 384 - 300 > Items.graveX && // Right-shifted point > grave's left
				GamePanel.worldX + 384 - 300 < Items.graveX + 160 && // Right-shifted point < grave's right
				GamePanel.worldY + 288 - 150 > Items.graveY && // Down-shifted point > grave's top
				GamePanel.worldY + 288 - 150 < Items.graveY + 120 // Down-shifted point < grave's bottom
				&& Items.firstTime) {
			Items.inGraveYard = true;
		} else {
			Items.inGraveYard = false;
		}

		n.npcs();
	}

	public boolean getCollision() {

		return collision;

	}

	public void collision() {
		if (!collision && !Npc.collisionNpc) {
			beforeCollisionX = GamePanel.worldX;
			beforeCollisionY = GamePanel.worldY;
		} else {
			GamePanel.worldX = beforeCollisionX;
			GamePanel.worldY = beforeCollisionY;
		}
	}

	public void loadCharacterImages() throws IOException {

		character = ImageIO.read(new File("src/textures/character.png"));
		buisnessMan = ImageIO.read(new File("src/textures/unknownBuisnessMan.png"));

	}

	public void drawNpcs(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		// Buisness man
		g2.drawImage(Player.buisnessMan, 550, 120, 50, 78, null);

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

	public static boolean disableCharacterMovement() {

		if (Maps.fading) {
			return true;
		}

		if (Items.carOn) {
			return true;
		}
		if (Items.carWorldX == 4700) {
			return true;
		}
		if (Items.inGraveYard) {
			return true;
		}
		return false;

	}
}
