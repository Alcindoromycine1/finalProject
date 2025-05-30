package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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

	public static void houseMirror(Graphics2D g2) throws IOException {

		BufferedImage mirror;
		try {
			mirror = ImageIO.read(new File("src/textures/jeffMirror.png"));
			g2.drawImage(mirror, 360, 200, 50, 50, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static boolean carOn = false;
	static int animationFrame = 0;
	static int carWorldX = 1300;
	static int carWorldY = 770;
	static int doctrineWorldX = 6000;
	static int doctrineWorldY = 525;
	static boolean visible = true;
	static boolean carUsed = false;

	public static void doctrine(Graphics2D g2) throws IOException {

		int doctrineX = doctrineWorldX - GamePanel.worldX;
		int doctrineY = doctrineWorldY - GamePanel.worldY;

		BufferedImage doctrine;
		doctrine = ImageIO.read(new File("src/textures/doctrine.png"));
		g2.drawImage(doctrine, doctrineX, doctrineY, 260, 390, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 20));
		g2.drawString("Doctrine", 6080 - GamePanel.worldX, 715 - GamePanel.worldY);

	}

	public static void insideDoctrine(Graphics2D g2) throws IOException {
		ghost(g2, 1110, 120, 125, 98);
		Npc.text(g2, 4);
	}

	static boolean enterBook = false;
	static int nextPage = 0;
	static boolean hoveringNextPage = false;
	public static boolean playGif = false;
	public static boolean staticImageBook = false;
	public static boolean hoveringExitPage = false;

	// Stackoverflow used for GIF:
	// https://stackoverflow.com/questions/12566311/displaying-gif-animation-in-java
	public static void book(Graphics2D g2, Component observer) {
		g2.setFont(new Font("calibri", Font.BOLD, 18));
		if (enterBook) {
			if (!playGif || staticImageBook) {
				BufferedImage book;
				try {
					book = ImageIO.read(new File("src/textures/books.png"));
					g2.drawImage(book, -70, 0, 900, 587, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				ImageIcon pageFlipping = new ImageIcon("src/textures/books.gif");
				g2.drawImage(pageFlipping.getImage(), -70, 0, 900, 587, observer);
			}

			if (!hoveringNextPage) {
				g2.setColor(Color.RED);
				g2.fillRoundRect(530, 445, 150, 40, 10, 10);
			} else {
				g2.setColor(Color.BLACK);
				g2.fillRoundRect(530, 445, 150, 40, 10, 10);
			}
			if (!hoveringExitPage) {
				g2.setColor(Color.RED);
				g2.fillRoundRect(530, 100, 150, 40, 10, 10);
			} else {
				g2.setColor(Color.BLACK);
				g2.fillRoundRect(530, 100, 150, 40, 10, 10);
			}
			g2.setColor(Color.WHITE);
			g2.drawString("Close Book", 562, 125);
			g2.drawString("Next Page", 565, 470);

			if (nextPage == 1) {
				g2.setColor(Color.BLACK);
				g2.drawString("How To Do An Exorcism", 100, 100);
				g2.fillRect(100, 100, 150, 2);
				g2.drawString("When you see a ghost, press E to exorcsie it.", 100, 150);
				g2.drawString("At the Top of the screen you will see a certain shape.", 100, 250);
				g2.drawString("You will need to replicate that shape by dragging your mouse around the screen.", 100,
						300);
				g2.drawString("", 100, 350);
			}
		}
	}

	public void car(Graphics g) throws IOException {
		int carX = carWorldX - GamePanel.worldX;
		int carY = carWorldY - GamePanel.worldY;

		BufferedImage car = ImageIO.read(new File("src/textures/car.png"));
		g.drawImage(car, carX, carY, 96, 192, null);
		// BufferedImage brokenCar = ImageIO.read(new
		// File("src/textures/destroyedCar.png"));
		// g.drawImage(brokenCar, carX, carY, 96, 192, null);
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

	static boolean movementPrompt = false;
	public int instructionsX = 640;
	public int instructionsY = 10;
	public static boolean hoveringInstructions = false;
	public static boolean instructionsPrompt = false;
	public static boolean hoveringKeybind = false;
	public static boolean hoveringMovement = false;
	static Color selected = new Color(144, 50, 50);
	static Color unselected = new Color(193, 45, 57);

	public void instructions(Graphics2D g2) {
		Font calibri = new Font("Calibri", Font.BOLD, 18);
		g2.setFont(calibri);
		if (hoveringInstructions) {
			g2.setColor(selected);
			g2.fillRoundRect(instructionsX, instructionsY, 120, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString("Instructions", 655, 35);

		} else {
			g2.setColor(unselected);
			g2.fillRoundRect(instructionsX, instructionsY, 120, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString("Instructions", 655, 35);
		}
		if (instructionsPrompt) {
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Instructions", 260, 115);
			g2.fillRect(263, 117, 283, 2);

			// Movement
			if (hoveringMovement) {
				g2.setColor(selected);
				g2.fillRoundRect(305, 160, 195, 62, 20, 20);
			} else if (!hoveringMovement) {
				g2.setColor(unselected);
				g2.fillRoundRect(305, 160, 195, 62, 20, 20);
			}
			if (hoveringKeybind) {
				g2.setColor(selected);
				g2.fillRoundRect(305, 250, 195, 62, 20, 20);
			} else if (!hoveringKeybind) {
				g2.setColor(unselected);
				g2.fillRoundRect(305, 250, 195, 62, 20, 20);
			}

			g2.setFont(new Font("Calibri", Font.BOLD, 30));
			g2.setColor(Color.WHITE);
			g2.drawString("Movement", 335, 200);
			g2.drawString("Keybinds", 348, 290);
		}
	}

	public void credits(Graphics2D g2) {

	}

	static boolean backPressed = false;
	static boolean hoveringBack = false;
	static boolean keybindPrompts = false;
	static boolean inHouse = false;

	public void backMenu(Graphics2D g2) {
		if (!hoveringBack) {
			g2.setColor(unselected);
			g2.fillRoundRect(345, 430, 130, 45, 10, 10);
			g2.setFont(new Font("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 367, 460);
		} else {
			g2.setColor(selected);
			g2.fillRoundRect(345, 430, 130, 45, 10, 10);
			g2.setFont(new Font("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 367, 460);
		}
		if (backPressed) {
			if (movementPrompt) {
				movementPrompt = false;
				instructionsPrompt = true;
			} else if (keybindPrompts) {
				keybindPrompts = false;
				instructionsPrompt = true;
			}

			else if (instructionsPrompt) {
				instructionsPrompt = false;
				Input.instructionsPressed = false;
			}
			backPressed = false;
		}
	}

	public void prompts(Graphics2D g2) {
		if (movementPrompt) {
			instructionsPrompt = false;
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
				g2.setFont(new Font("Monospaced", Font.BOLD, 40));
				g2.setColor(Color.BLACK);
				g2.drawString("Movement", 310, 112 + 10);
				g2.fillRect(307, 115 + 10, 195, 2);
				g2.drawString("Left", 210, 305 + 20);
				g2.drawString("Right", 530, 305 + 20);
				g2.drawString("Up", 382, 178 + 20);
				g2.drawString("Down", 351, 368 + 20);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (keybindPrompts) {
			instructionsPrompt = false;
			BufferedImage car;
			BufferedImage door;
			BufferedImage exorcism;
			BufferedImage bed;
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Keybinds", 310, 112 + 10);
			g2.fillRect(310, 116 + 10, 189, 2);
			try {
				car = ImageIO.read(new File("src/textures/car.png"));// https://media.istockphoto.com/id/1193231012/vector/computer-gamer-keyboard-wasd-keys-vector-illustration-wasd-keys-game-control-keyboard-buttons.jpg?s=612x612&w=0&k=20&c=-DJ6CFewXZ_Oofp_BsYya5KniByRkVW3EAHYICWIOaU=
				door = ImageIO.read(new File("src/textures/door.png"));// https://img.freepik.com/premium-vector/open-close-door-pixel-art-style_475147-1239.jpgd
				exorcism = ImageIO.read(new File("src/textures/exorcism.png"));// https://www.creativefabrica.com/wp-content/uploads/2023/03/22/pixel-art-wooden-cross-vector-Graphics-65026120-1.jpg
				bed = ImageIO.read(new File("src/textures/bed.png"));
				g2.drawImage(car, 130 - 10, 30 + 80, 96, 192, null);
				g2.drawImage(door, 300 + 18, 50 + 80, 150, 150, null);
				g2.drawImage(exorcism, 545, 95 + 80, 120, 75, null);
				g2.drawImage(bed, 160, 300, 75, 75, null);
				g2.setFont(new Font("Monospaced", Font.BOLD, 20));
				g2.setColor(Color.BLACK);
				// Car keybind
				g2.drawString("Press C to", 120 - 10, 180 + 80);
				g2.drawString("Enter Car", 124 - 10, 200 + 80);
				// Fading keybind
				g2.drawString("Press F to", 320 + 18, 180 + 80);
				g2.drawString("Walk into Area", 290 + 18, 200 + 80);
				// Exorcism keybind
				g2.drawString("Press E to", 545, 180 + 80);
				g2.drawString("Exorcise Ghosts", 515, 200 + 80);
				// Bed keybind
				g2.drawString("Walk into a bed", 105, 395);
				g2.drawString("to sleep", 150, 415);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	static boolean inGraveYard = false;
	static int graveX = 4600;
	static int graveY = 333;
	static BufferedImage ghost;
	static boolean firstTime = true;
	static int ghostNumber = 0;

	public static void ghostRandomizer() {

		ghostNumber = (int) (Math.random() * 2) + 1;

	}

	public static void ghost(Graphics2D g2, int ghostGraveYardX, int ghostGraveYardY, int width, int height)
			throws IOException {

		int ghostX = ghostGraveYardX - GamePanel.worldX;
		int ghostY = ghostGraveYardY - GamePanel.worldY;

		ghost = ImageIO.read(new File("src/textures/ghost.png"));
		g2.drawImage(ghost, ghostX, ghostY, width, height, null);

	}

	static int level = 1;
	public static void minigameGhost(Graphics2D g2, int ghotsX, int ghotsY, String shape, int width, int height)
			throws IOException {
		int ghostX = ghotsX - GamePanel.worldX;
		int ghostY = ghotsY - GamePanel.worldY;

		ghost = ImageIO.read(new File("src/textures/minigameghost.png"));
		ghost(g2, ghostX, ghostY, width, height);

		for (int i = 0; i < 5; i++) {
			int[] xValues = { ghostX - 230 + i, ghostX - 230 + i, ghostX - 290 - i, ghostX - 290 - i };
			int[] yValues = { ghostY - 360 - i, ghostY - 300 + i, ghostY - 300 + i, ghostY - 360 - i };
			g2.setColor(Color.RED);
			g2.drawPolygon(xValues, yValues, xValues.length);
		}
		if (level == 1) {
			ghostRandomizer();
			level = -1;
		}
		int offsetX = 0;
		if (ghostNumber == 1) {
			offsetX = 0;
		} else if (ghostNumber == 2) {
			offsetX = 30;
		}

		if (shape.equals("Square")) {
			for (int i = 0; i < 4; i++) {
				int[] xSquare = { 90 + i + offsetX, 110 - i + offsetX, 110 - i + offsetX, 90 + i + offsetX };
				int[] ySquare = { 25 + i, 25 + i, 45 - i, 45 - i };
				g2.drawPolygon(xSquare, ySquare, 4);
			}

		} else if (shape.equals("Circle")) {
			for (int i = 0; i < 4; i++) {
				g2.drawOval(85 + i + offsetX, 25 + i, 20 - i, 20 - i);
			}

		} else if (shape.equals("Pentagon")) {

		}
	}

	public static void graveyard(Graphics2D g2) throws IOException {

		int inGraveYardX = graveX - GamePanel.worldX;
		int inGraveYardY = graveY - GamePanel.worldY;
		if (inGraveYard) {
			g2.drawImage(ghost, 480, 280, 250, 196, null);
			Npc.text(g2, 2);
		}

	}

	public static boolean hoveringYes = false;
	public static boolean hoveringNo = false;
	public static boolean inConfirmation = false;
	public static boolean yesPressed = false;
	public static boolean noPressed = false;

	public static void confirmation(Graphics2D g2, String text, int textX) {

		if (!inConfirmation) {
			return;
		}
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(160, 150, 225 * 2, 155 * 2, 10, 10);
		g2.setColor(Color.DARK_GRAY);
		g2.fillRoundRect(160, 150, 226 * 2, 156 * 2, 10, 10);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRoundRect(168, 158, 218 * 2, 148 * 2, 10, 10);
		g2.setFont(new Font("Monospaced", Font.BOLD, 33));
		g2.setColor(Color.BLACK);
		g2.drawString(text, textX, 250);
		g2.setFont(new Font("Calibri", Font.BOLD, 30));

		if (hoveringYes) {
			// Yes box
			g2.setColor(selected);
			g2.fillRoundRect(225, 355, 130, 45, 10, 10);
		} else {
			// Yes box
			g2.setColor(unselected);
			g2.fillRoundRect(225, 355, 130, 45, 10, 10);
		}
		if (hoveringNo) {
			// No box
			g2.setColor(selected);
			g2.fillRoundRect(425, 355, 130, 45, 10, 10);
		} else {
			// No box
			g2.setColor(unselected);
			g2.fillRoundRect(425, 355, 130, 45, 10, 10);
		}
		g2.setColor(Color.WHITE);
		g2.drawString("Yes", 270, 385);
		g2.drawString("No", 472, 385);
	}
}
