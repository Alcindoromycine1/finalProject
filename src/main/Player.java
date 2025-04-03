package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class Player {

	public static Input keyH = new Input();// Creates an object to call
	// Players initial position
	private int beforeColMoveX = 100;
	private int beforeColMoveY = 100;

	private BufferedImage character;

	public boolean collision = false;

	public void collisionChecking() {

		for (int i = 0; i < 24; i++) {
			if (beforeColMoveX + 32 > tileX[i]// Checks if right side of hitbox is to the left of the right side of the
												// wall
					&& beforeColMoveX < tileX2[i]// Checks if left side of hitbox is to left of the left side of the
													// wall
					&& beforeColMoveY + 72 > tileY1[i]// Checks if bottom side of hitbox is below top side of wall
					&& beforeColMoveY < tileY2[i]) {// Checks if top side of hitbox is above bottom side of wall
				collision = true;
			}
		}

	}

	public boolean getCollision() {

		return collision;

	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public void loadCharacterImages() throws IOException {

		character = ImageIO.read(new File("src/textures/character.png"));

	}

	public BufferedImage getCharacter() {
		return character;
	}

}
