package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Items {

	private int inventoryBoxX = 300;
	private int inventoryBoxY = 300;

	private Input input;

	public Items(Input input) {

		this.input = input;

	}

	public Items() {

	}

	public void baseballBat(Graphics g, boolean inventoryCollision) throws IOException {

		BufferedImage baseballBat = ImageIO.read(new File("src/textures/baseballBat.png"));// https://as2.ftcdn.net/jpg/03/13/10/75/1000_F_313107572_KTaHs8vB8IOSkKiC9DE7yhBIO3w7e3mo.jpg
		// g.drawImage(baseballBat, 126, 160, 70, 70, null);
		// If the user is hovering over the inventory button
		/*
		 * if (inventoryCollision) {
		 * 
		 * g.setColor(Color.red); g.drawImage(baseBallBat, 160, 80, 70, 15, 15);
		 * 
		 * }
		 */

		// If the user has clicked on the inventory button

		if (input.mouseDragging && input.mouseHolding) {
			inventoryBoxX = input.mouseX - 40;
			inventoryBoxY = input.mouseY - 40;
		}
		g.setColor(Color.BLACK);
		g.drawImage(baseballBat, inventoryBoxX, inventoryBoxY, 70, 70, null);
	}

	public void communicate(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.fillRect(400, 400, 100, 400);

	}

	static boolean carOn = false;
	static int animationFrame = 0;
	static int carWorldX = 1100;
	static int carWorldY = 700;
	static boolean visible = true;
	public void car(Graphics g) throws IOException {
		int carX = carWorldX - GamePanel.worldX;
		int carY = carWorldY - GamePanel.worldY;

		BufferedImage car = ImageIO.read(new File("src/textures/car.png"));
		g.drawImage(car, carX, carY, 96, 192, null);

		if (!carOn && GamePanel.playerX + 32 > carX && GamePanel.playerX < carX + 96
				&& GamePanel.playerY + 72 > carY && GamePanel.playerY < carY + 192 && Player.keyH.ePressed) {

			carOn = true;
			Player.disableCharacterMovement();
			visible = false;

			animationFrame = 0;
		}
	}

	public static void animation() {
		if (carOn) {
			carWorldX += 3;
			GamePanel.worldX += 3;
			animationFrame++;

			if (animationFrame >= 200) {
				carOn = false;
				visible = true; 
			}
		}
	}

}
