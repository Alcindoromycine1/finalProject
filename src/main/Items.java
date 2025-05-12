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

	Input input;

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
			g2.drawString("Are We Cooked Interactive", 60, 280);
			g2.drawString("Presents...", 280, 400);
		}

		if (carWorldX >= 3120 && carWorldX <= 4600) {
			g2.drawString("Are We Cooked 2D", 180, 280);
		}
	}

	int badGuyX = 5500 - GamePanel.worldX;
	int badGuyY = 900 - GamePanel.worldY;
	boolean badGuyMoving = false;

	public void badGuy(Graphics2D g2) throws IOException {

		BufferedImage badguy = ImageIO.read(new File("src/textures/character.png"));

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

	boolean movementPrompt = false;
	public int instructionsX = 640;
	public int instructionsY = 10;
	public static boolean hoveringInstructions = false;

	public void instructions(Graphics2D g2) {
		Font calibri = new Font("Calibri", Font.BOLD, 18);
		g2.setFont(calibri);
		if (hoveringInstructions) {
			g2.setColor(new Color(0, 0, 10));
			g2.fillRoundRect(instructionsX, instructionsY, 120, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString("Keybinds", 666, 35);

		} else {
			g2.setColor(Color.RED);
			g2.fillRoundRect(instructionsX, instructionsY, 120, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString("Keybinds", 666, 35);
		}
	}

	public void prompts(Graphics2D g2) {

		BufferedImage wasdKey;
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
		g2.setColor(Color.DARK_GRAY);
		g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
		g2.setColor(Color.LIGHT_GRAY);  
		g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
		try {  
			wasdKey = ImageIO.read(new File("src/textures/wasdKey.png"));// https://media.istockphoto.com/id/1193231012/vector/computer-gamer-keyboard-wasd-keys-vector-illustration-wasd-keys-game-control-keyboard-buttons.jpg?s=612x612&w=0&k=20&c=-DJ6CFewXZ_Oofp_BsYya5KniByRkVW3EAHYICWIOaU=
			g2.drawImage(wasdKey, 285, 146 + 20, 250, 250, null);
			g2.setFont(new Font("Calibri", Font.BOLD, 45));
			g2.setColor(Color.BLACK);
			g2.drawString("Movement", 310, 112 + 10);
			g2.fillRect(315, 115 + 10, 209, 2);
			g2.drawString("Left", 210, 305 + 20);
			g2.drawString("Right", 530, 305 + 20);
			g2.drawString("Up", 382, 178 + 20);
			g2.drawString("Down", 351, 368 + 20);
			g2.setColor(Color.RED);
			g2.fillRoundRect(345, 430, 130, 45, 10, 10);
			g2.setFont(new Font ("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 367, 460);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public boolean getHoveringInstructions() {
		return hoveringInstructions;
	}

	public void setHoveringInstructions(boolean ins) {
		hoveringInstructions = ins;
	}

}
