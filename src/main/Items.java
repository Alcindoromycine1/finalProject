package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
	static int carWorldX = 1300;
	static int carWorldY = 770;
	static boolean visible = true;
	static boolean carUsed = false;

	public void car(Graphics g) throws IOException {
		int carX = carWorldX - GamePanel.worldX;
		int carY = carWorldY - GamePanel.worldY; 

		BufferedImage car = ImageIO.read(new File("src/textures/car.png"));
		g.drawImage(car, carX, carY, 96, 192, null);

		if (!carOn && !carUsed && GamePanel.playerX + 32 > carX && GamePanel.playerX < carX + 96
				&& GamePanel.playerY + 72 > carY && GamePanel.playerY < carY + 192 && Player.keyH.ePressed) {

			carOn = true;
			carUsed = true;
			Player.disableCharacterMovement();
			visible = false;
			animationFrame = 0;

		}
	}

	int transparency = 0;
	boolean hasFaded = false;
	boolean reset = false;

	public void titleScreen(Graphics2D g2) {
		if (carOn) {
			if (reset) {
				transparency = 0;
				hasFaded = false;
				reset = false;
			}

			if (!hasFaded) {
				transparency += 1;
				if (transparency >= 255) {
					transparency = 255;
					hasFaded = true;
				}
			} else {
				transparency -= 1;
				if (transparency <= 0) {
					transparency = 0;
					reset = true;
				}
			}
		}
		Color title = new Color(0, 0, 0, transparency);
		g2.setColor(title);
		g2.setFont(new Font("calibri", Font.BOLD, 60));

		if (carWorldX < 3050) {
			g2.drawString("Group Name Presents...", 120, 280);
		}

		if (carWorldX >= 3120 && carWorldX <= 4600) {
			g2.drawString("Are We Cooked 2D", 180, 280);
		}
	}

	int badGuyX = 5500 - GamePanel.worldX;
	int badGuyY = 900 - GamePanel.worldY;
	boolean badGuyMoving = false;
	
	public void badGuy(Graphics2D g2) throws IOException {
		
		BufferedImage badguy = ImageIO.read(new File ("src/textures/character.png"));
		
		if (carWorldX >= 4700) {
			badGuyMoving = true;
		}
		if (badGuyMoving && badGuyX >= 4900) {
			badGuyX -= 2;
		}

		int screenX = badGuyX - GamePanel.worldX;
		int screenY = badGuyY - GamePanel.worldY;

		g2.drawImage(badguy, screenX, screenY, 48, 70, null);
	}



	public static void animation() {
		if (carOn) {
			carWorldX += 3;
			GamePanel.worldX += 3;
			animationFrame++;

			if (carWorldX >= 4700) {
				carWorldX = 4700;
				carOn = false;
				visible = true;
				Player.disableCharacterMovement();
			}
		}
	}

}
