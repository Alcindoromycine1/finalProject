package main;

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
	private int beforeCollisionX = 100;
	private int beforeCollisionY = 100;
	Jumpscare j = new Jumpscare();

	private BufferedImage character;

	public boolean collision = false;

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

}
