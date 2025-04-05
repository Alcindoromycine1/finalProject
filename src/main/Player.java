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
	private int beforeCollisionX = 400;
	private int beforeCollisionY = 400;
	Jumpscare j = new Jumpscare();
	Input input = new Input();

	private BufferedImage character;

	public boolean collision = false;
	public boolean inventoryCollision = false;

	public Player(Input input) {
		this.input = input;
	}

	public Player() {

	}

	public void collisionChecking() {

		collision = false;
		for (int i = 0; i < Maps.treePositions.size(); i++) {
			int treeX = Maps.treePositions.get(i)[0];
			int treeY = Maps.treePositions.get(i)[1];

			if (GamePanel.playerX + 32 > treeX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < treeX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > treeY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < treeY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.housePositions.size(); i++) {
			int houseX = Maps.housePositions.get(i)[0];
			int houseY = Maps.housePositions.get(i)[1];
			if (GamePanel.playerX + 32 > houseX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < houseX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > houseY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < houseY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.bedPositions.size(); i++) {
			int bedX = Maps.bedPositions.get(i)[0];
			int bedY = Maps.bedPositions.get(i)[1];
			if (GamePanel.playerX + 32 > bedX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < bedX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > bedY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < bedY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
				break; // Stop checking after finding the first collision
			}
		}
		for (int i = 0; i < Maps.houseWallPositions.size(); i++) {
			int houseWallX = Maps.houseWallPositions.get(i)[0];
			int houseWallY = Maps.houseWallPositions.get(i)[1];
			if (GamePanel.playerX + 32 > houseWallX // Right side of hitbox is past left side of tree
					&& GamePanel.playerX < houseWallX + 48 // Left side of hitbox is before right side of tree
					&& GamePanel.playerY + 72 > houseWallY // Bottom side of hitbox is below top side of tree
					&& GamePanel.playerY < houseWallY + 48) { // Top side of hitbox is above bottom side of tree
				collision = true;
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
	}

	public boolean getCollision() {

		return collision;

	}

	public void collision() {
		if (!collision) {

			beforeCollisionX = GamePanel.playerX;
			beforeCollisionY = GamePanel.playerY;
		} else {

			GamePanel.playerX = beforeCollisionX;
			GamePanel.playerY = beforeCollisionY;
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
		g3.fillRoundRect(125, 160, 200, 70, 15, 15);
		g3.setColor(Color.black);
		g3.setFont(inventoryHeader);
		g3.setFont(regular);
		g3.drawString("Click me", 175, 200);

		//If the user is hovering over the invetory button
		if (inventoryCollision) {

			g3.setColor(Color.red);
			g3.fillRoundRect(125, 160, 200, 70, 15, 15);
			g3.setFont(regular);
			g3.setColor(Color.black);
			g3.drawString("Click me", 175, 200);

		}

		//If the user has clicked on the inventory button
		if (inventoryCollision && input.mouseClicked) {

			System.out.println("Clicked");

		}

	}

}
