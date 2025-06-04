package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import Horror.Jumpscare;

public class Items {

	private int inventoryBoxX = 300;
	private int inventoryBoxY = 300;
	static BufferedImage book;
	static ImageIcon pageFlipping;
	static BufferedImage doctrine;
	static BufferedImage mirror;
	static BufferedImage brokenCar;
	static BufferedImage car;

	Input input;
	Sound bookFlip;

	public Items(Input input) {

		this.input = input;

	}

	public Items() {
		try {
			bookFlip = new Sound("src/sound/bookFlip.wav");
			book = ImageIO.read(new File("src/textures/books.png"));
			pageFlipping = new ImageIcon("src/textures/books.gif");
			doctrine = ImageIO.read(new File("src/textures/doctrine.png"));
			mirror = ImageIO.read(new File("src/textures/jeffMirror.png"));
			brokenCar = ImageIO.read(new File("src/textures/destroyedCar.png"));
			car = ImageIO.read(new File("src/textures/car.png"));
		} catch (Exception e) {
			System.out.println("Error loading file: " + e.getMessage());
		}
	}

	public static void houseMirror(Graphics2D g2) {
		g2.drawImage(mirror, 360, 200, 50, 50, null);
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

		g2.drawImage(doctrine, doctrineX, doctrineY, 260, 390, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 20));
		g2.drawString("Doctrine", 6080 - GamePanel.worldX, 715 - GamePanel.worldY);

	}

	public static void insideDoctrine(Graphics2D g2) throws IOException {
		if (Maps.currentMap == 4) {
			ghost(g2, 1110, 120, 125, 98);
		}
		//Npc.text(g2, 4);
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

				g2.drawImage(book, -70, 0, 900, 587, null);
			} else {
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

			if (nextPage == 0) {
				g2.setColor(Color.BLACK);
				g2.drawString("How To Do An Exorcism", 100, 100);
				g2.fillRect(100, 100, 150, 2);
				g2.drawString("When you see a ghost, press E to exorcise it.", 100, 150);
				g2.drawString("At the Top of the screen you will see a certain shape.", 100, 250);
				g2.drawString("You will need to replicate that shape by dragging your mouse around the screen.", 100,
						300);
				g2.drawString("", 100, 350);
			}
		}
	}

	public static void car(Graphics g) throws IOException {
		int carX = carWorldX - GamePanel.worldX;
		int carY = carWorldY - GamePanel.worldY;
		g.drawImage(car, carX, carY, 96, 192, null);
		if (!carOn && !carUsed && GamePanel.playerX + 32 > carX && GamePanel.playerX < carX + 96
				&& GamePanel.playerY + 72 > carY && GamePanel.playerY < carY + 192 && Player.keyH.cPressed) {

			carOn = true;
			Player.disableCharacterMovement();
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
			g2.drawString("Whispers Of The Decieved", 180, 280);
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
				carUsed = true;
				car = brokenCar;
				Jumpscare.jumpscare = true;
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

	public static int ghostRandomizer() {
		// if (level == 1) {
		return (int) (Math.random() * 5) + 1;
		// } else if (level == 2) {
		// return (int) (Math.random() * 4) + 6;
		// }
		// return 0;

	}

	public static void ghost(Graphics2D g2, int ghostGraveYardX, int ghostGraveYardY, int width, int height)
			throws IOException {

		int ghostX = ghostGraveYardX - GamePanel.worldX;
		int ghostY = ghostGraveYardY - GamePanel.worldY;

		ghost = ImageIO.read(new File("src/textures/ghost.png"));
		g2.drawImage(ghost, ghostX, ghostY, width, height, null);

	}

	static int level = 1;
	static String ghostShape = " ";

	public static void drawGhost(Graphics2D g2, int ghostNumber, int offsetX, int offsetY) throws IOException {
		if (ghostNumber == 1 && !Input.isTriangle) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Triangle", 250, 196);
		} else if (ghostNumber == 2 && !Input.isCircle) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Circle", 250, 196);
		} else if (ghostNumber == 3 && !Input.isZigzag) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Zigzag", 250, 196);
		} else if (ghostNumber == 4 && !Minigame.currentShape.equals("vertical")) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Vertical", 250, 196);
		} else if (ghostNumber == 5 && !Minigame.currentShape.equals("horizontal")) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Horizontal", 250, 196);
		} else if (ghostNumber == 6) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "duoGhost", 250, 196);
		} else if (ghostNumber == 7) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "duoGhost", 250, 196);
		} else if (ghostNumber == 8) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "duoGhost", 250, 196);
		} else if (ghostNumber == 9) {
			Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "duoGhost", 250, 196);
		}
	}

	public static int ghostNumberLeft;
	public static int ghostNumberRight;
	public static boolean ghostAppeared = false;
	static int ghostCount = 1;

	public static void ghostLogic(Graphics2D g2) throws IOException {
		if (level == 1) {
			if (!ghostAppeared) {
				ghostNumberLeft = ghostRandomizer();
				ghostNumberRight = ghostRandomizer();
				ghostAppeared = true;
			}
		}
		// System.out.println(level + " : " + ghostCount + " : " + ghostAppeared);
		if (level == 2) {
			if (!ghostAppeared) {
				duoShapeLeft = ghostRandomizer();
				duoShapeRight = ghostRandomizer();
				ghostAppeared = true;
			}
		}
		if (ghostCount == 1) {
			if (level == 1) {
				drawGhost(g2, ghostNumberLeft, -160, 30);
			} else if (level == 2) {
				drawGhost(g2, duoShapeLeft, -160, -30);
				drawGhost(g2, duoShapeRight, -120, -30);
			}
		}
		if (ghostCount == 2) {
			drawGhost(g2, ghostNumberRight, 0, 0);
		}
		if (ghostCount == 3) {
			level++;
			ghostCount = 1;
			ghostAppeared = false;
		}
	}

	public static void randomShape(String shape, int ghostX, int ghostY, Graphics2D g2, int offsetX) {

		if (shape.equals("Triangle")) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			int[] xTriangle = { ghostX - 260 + offsetX, ghostX - 230 + offsetX, ghostX - 290 + offsetX };
			int[] yTriangle = { ghostY - 350, ghostY - 290, ghostY - 290 };
			g2.drawPolygon(xTriangle, yTriangle, 3);
		} else if (shape.equals("Circle")) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			g2.drawOval(ghostX - 290 + offsetX, ghostY - 350, 60, 60);
		} else if (shape.equals("Zigzag")) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			int[] xZigzag = { ghostX - 150 - 140 + offsetX, ghostX - 100 - 140 + offsetX, ghostX - 150 - 140 + offsetX,
					ghostX - 100 - 140 + offsetX };
			int[] yZigzag = { ghostY - 195 - 140, ghostY - 195 - 140, ghostY - 145 - 140, ghostY - 145 - 140 };
			g2.drawPolyline(xZigzag, yZigzag, 4);
		} else if (shape.equals("Vertical")) {
			g2.setColor(Color.RED);
			g2.fillRect(ghostX - 260 + offsetX, ghostY - 355, 4, 60);
		} else if (shape.equals("Horizontal")) {
			g2.setColor(Color.RED);
			g2.fillRect(ghostX - 290 + offsetX, ghostY - 290, 60, 4);
		}
	}

	static int counting = 0;
	static boolean duoGhostInitialized = false;
	static int duoShapeLeft = 0;
	static int duoShapeRight = 0;

	public void shapeRandomizer() {

		String shapes[] = { "Triangle", "Circle", "Zigzag", "Vertical", "Horizontal" };
		int num = (int) (Math.random() * shapes.length) + 1;
	}

	public static void minigameGhost(Graphics2D g2, int ghotsX, int ghotsY, String shape, int width, int height)
			throws IOException {
		int ghostX = ghotsX - GamePanel.worldX;
		int ghostY = ghotsY - GamePanel.worldY;
		ghostShape = shape;
		ghost = ImageIO.read(new File("src/textures/minigameghost.png"));
		ghost(g2, ghostX, ghostY, width, height);
		if (level == 2) {
			ghostShape = "Triangle";
			randomShape(ghostShape, ghostX, ghostY, g2, 40);
			ghostShape = "Circle";
			randomShape(ghostShape, ghostX, ghostY, g2, -40);
		} else {
			randomShape(ghostShape, ghostX, ghostY, g2, 0);
		}

		/*
		 * else if (shape.equals("Circle")) { for (int i = 0; i < 4; i++) {
		 * g2.drawOval(85 + i, 25 + i, 20 - i, 20 - i); }
		 * 
		 * }
		 */

		/*
		 * if (shape.equals("Square")) { for (int i = 0; i < 4; i++) { int[] xSquare = {
		 * 90 + i + offsetX, 110 - i + offsetX, 110 - i + offsetX, 90 + i + offsetX };
		 * int[] ySquare = { 25 + i, 25 + i, 45 - i, 45 - i }; g2.drawPolygon(xSquare,
		 * ySquare, 4); }
		 */
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

	public static boolean helpPressed = false;
	public static boolean hoveringX = false;

	public static void exitMenuOption(Graphics2D g2) {
		if (hoveringX) {
			g2.setColor(selected);

		} else {
			g2.setColor(unselected);
		}
		g2.fillRoundRect(595 + 90, 100 - 40, 30, 30, 10, 10);
		g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.WHITE);
		g2.drawLine(604 + 90 - 5, 105 - 40, 620 + 90, 120 - 40 + 5);
		g2.drawLine(620 + 90, 105 - 40, 604 + 90 - 5, 120 - 40 + 5);
	}

	public static void help(Graphics2D g2) {
		if (helpPressed) {
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Help", 345, 115);
			g2.fillRect(344, 117, 98, 2);
			g2.setFont(new Font("Calibri", Font.PLAIN, 20));
			g2.drawString("Ensure that your audio is enabled before you begin the game.", 100 - 10, 175 + 10);
			g2.drawString("It is a vital component to the game.", 100 - 10, 205 + 10);
			g2.drawString("If you are ever stuck, and don't know what to do, you can use your cursor", 100 - 10,
					260 + 23);
			g2.drawString("to hover and click on the instructions menu in the top right. You can then", 100 - 10,
					290 + 23);
			g2.drawString("navigate through different topics to find what you are having troubles", 100 - 10, 320 + 23);
			g2.drawString("For example, if you don't know what the keybinds are to move, go to", 100 - 10, 350 + 23);
			g2.drawString("the movement section.", 100 - 10, 380 + 20);

			g2.drawString("When you enter the house, after you've slept in the bed, you should", 100 - 10, 435 + 20);
			g2.drawString("go back to the entrance again to go back outside of the house.", 100 - 10, 465 + 20);

			// Subtitles
			g2.setFont(new Font("Calibri", Font.BOLD, 22));
			g2.drawString("Guide House", 335, 425);
			g2.fillRect(335, 427, 117, 2);
			g2.drawString("Stuck", 370, 253);
			g2.fillRect(370, 255, 52, 2);
			g2.drawString("Audio", 370, 155);
			g2.fillRect(370, 157, 54, 2);
			exitMenuOption(g2);
		}
	}

	public static boolean creditsPressed = false;

	public static void credits(Graphics2D g2) {
		if (creditsPressed) {
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Credits", 325, 115);
			g2.fillRect(325, 117, 167, 2);

			g2.setFont(new Font("Calibri", Font.BOLD, 20));
			g2.drawString("Project Lead", 100, 175);
			g2.fillRect(100, 177, 150, 2);
			g2.drawString("Noah Sussman", 100, 180);
			g2.drawString("Senior Developer: Akhilan Saravanan", 100, 205);
			g2.drawString("Junior Developer", 100, 235);
			g2.drawString("UX/UI Designer: Rudra Garg", 100, 235);
			g2.drawString("Voice Actor", 100, 265);
			g2.drawString("Playtester", 100, 270);
			g2.fillRect(100, 267, 150, 2);
			g2.drawString("Sammy Jiang", 100, 270);
		}
	}

	public void playBookSound() {
		bookFlip.play();
	}

	// Timer created from
	// https://stackoverflow.com/questions/1006611/java-swing-timer
	static Timer time;

	public static void timer() {
		time = new Timer(723, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Items.playGif = false;
				Items.staticImageBook = true;
				time.stop();
			}
		});
		if (time != null && time.isRunning()) {
			time.stop();
		}
		time.setRepeats(false);
		time.start();
	}
}
