package main;

import Horror.Jumpscare;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Items {
	private Input input;
	private Npc npc;
	private Player p;
	private Minigame minigame;
	private Maps m;
	private Jumpscare j;

	private int playerX;
	private int playerY;

	private int worldX;
	private int worldY;

	private BufferedImage mirror;
	private BufferedImage doctrine;
	private BufferedImage book;
	private BufferedImage car;
	private BufferedImage brokenCar;
	private BufferedImage wasdKey;
	private BufferedImage door;
	private BufferedImage exorcism;
	private BufferedImage bed;

	private ImageIcon pageFlipping;
	private Sound bookFlipSound;

	private ArrayList<String> shapesArray = new ArrayList<String>();

	public Items(GamePanel gp) {

		this.input = gp.getId();
		this.npc = gp.getN();
		this.p = gp.getP();
		this.minigame = gp.getMinigame();
		this.m = gp.getM();
		this.j = gp.getJ();

		playerX = gp.getPlayerX();
		playerY = gp.getPlayerY();

		worldX = gp.getWorldX();
		worldY = gp.getWorldY();

		try {
			mirror = ImageIO.read(new File("src/textures/jeffMirror.png"));
			doctrine = ImageIO.read(new File("src/textures/doctrine.png"));
			book = ImageIO.read(new File("src/textures/books.png"));
			car = ImageIO.read(new File("src/textures/car.png"));
			brokenCar = ImageIO.read(new File("src/textures/destroyedCar.png"));
			wasdKey = ImageIO.read(new File("src/textures/wasdKey.png"));// https://media.istockphoto.com/id/1193231012/vector/computer-gamer-keyboard-wasd-keys-vector-illustration-wasd-keys-game-control-keyboard-buttons.jpg?s=612x612&w=0&k=20&c=-DJ6CFewXZ_Oofp_BsYya5KniByRkVW3EAHYICWIOaU=
			door = ImageIO.read(new File("src/textures/door.png"));// https://img.freepik.com/premium-vector/open-close-door-pixel-art-style_475147-1239.jpgd
			exorcism = ImageIO.read(new File("src/textures/exorcism.png"));// https://www.creativefabrica.com/wp-content/uploads/2023/03/22/pixel-art-wooden-cross-vector-Graphics-65026120-1.jpg
			bed = ImageIO.read(new File("src/textures/bed.png"));
			pageFlipping = new ImageIcon("src/textures/books.gif");
			bookFlipSound = new Sound("src/sound/bookFlip.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void playBookFlipSound() {
		if (!bookFlipSound.isPlaying()) {
			bookFlipSound.play();
		}
	}

	public void stopBookFlipSound() {
		bookFlipSound.stop();
	}

	public void updateItemsValues(int worldX, int worldY, int playerX, int playerY) {
		this.worldX = worldX;
		this.worldY = worldY;

		this.playerX = playerX;
		this.playerY = playerY;

	}

	public void houseMirror(Graphics2D g2) throws IOException {
		g2.drawImage(mirror, 360, 200, 50, 50, null);
	}

	private boolean carOn = false;
	private int animationFrame = 0;
	private int carWorldX = 1295;
	private int carWorldY = 770;
	private int doctrineWorldX = 6000;
	private int doctrineWorldY = 525;
	private boolean visible = true;
	private boolean carUsed = false;
	private boolean carSceneDone = false;

	public void doctrine(Graphics2D g2, GamePanel gp) throws IOException {

		int doctrineX = doctrineWorldX - gp.getWorldX();
		int doctrineY = doctrineWorldY - gp.getWorldY();

		g2.drawImage(doctrine, doctrineX, doctrineY, 260, 390, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 20));
		g2.drawString("Doctrine", 5080 - worldX, 715 - worldY);

	}

	private boolean once = false;
	private boolean doingDoctrineGhost = false;
	
	public void insideDoctrine(Graphics2D g2, GamePanel gp) throws IOException {
		if (m.getCurrentMap() == 4) {
			ghost(g2, 1110, 120, 125, 98, gp);
			if (gp.getWorldX() >= 715 && gp.getWorldX() <= 865 && gp.getWorldY() >= -25 && gp.getWorldY() <= 100) {
				if (!once) {
					npc.setTextIndex(0);
					doingDoctrineGhost = true;
					once = true;
				}
				npc.text (g2, 4);
				p.disableCharacterMovement();
			}
		}
	}

	private boolean enterBook = false;
	private int nextPage = 0;
	private boolean hoveringNextPage = false;
	private boolean playGif = false;
	private boolean staticImageBook = false;
	private boolean hoveringExitPage = false;

	// Stackoverflow used for GIF:
	// https://stackoverflow.com/questions/12566311/displaying-gif-animation-in-java
	public void book(Graphics2D g2, Component observer) throws IOException {
		g2.setFont(new Font("calibri", Font.BOLD, 18));
		enterBook = true;// REMOVE LATER
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
			g2.setColor(Color.BLACK);
			Font header = new Font("Arial", Font.BOLD, 35);
			Font subHeading = new Font("Arial", Font.BOLD, 24);
			Font normalText = new Font("Arial", Font.PLAIN, 17);
			BufferedImage dispellingGhosts = ImageIO.read(new File("src/textures/dispellingGhosts.png"));// image from:
			BufferedImage circleExample = ImageIO.read(new File("src/textures/circleExample.png"));
			BufferedImage triangleExample = ImageIO.read(new File("src/textures/triangleExample.png"));
			BufferedImage horizontalExample = ImageIO.read(new File("src/textures/horizontalExample.png"));
			BufferedImage leftGhostExample = ImageIO.read(new File ("src/textures/leftGhostExample.png"));
			BufferedImage rightGhostExample = ImageIO.read(new File ("src/textures/rightGhostExample.png"));
		
			// Researched information from:
			// https://www.usccb.org/beliefs-and-teachings/what-we-believe/catechism/catechism-of-the-catholic-church?p=29-chapter12.xhtml%23para1673
			// and https://www.vatican.va/archive/cod-iuris-canonici/cic_index_en.html
			if (nextPage == 0) {
				g2.setFont(header);

				g2.drawString("Exorcisms", 145, 200);
				g2.setFont(new Font("Calibri", Font.PLAIN, 22));
				g2.drawString("By: Noah Sussman, Rudra Garg", 85, 270);
				g2.drawString("and Akhilan Saravanan", 120, 320);
				g2.setFont(normalText);
			} else if (nextPage == 1) {
				g2.setFont(subHeading);
				g2.drawString("What is an Exorcism?", 100, 140);
				g2.fillRect(100, 142, 245, 3);
				g2.drawString("What are the Different", 415, 170);
				g2.drawString("Types of Exorcisms?", 423, 200);
				g2.fillRect(415, 202, 254, 3);

				g2.setFont(normalText);
				g2.drawString("An exorcism is a specific type of", 90, 180);
				g2.drawString("prayer that the Church uses against", 90, 205);
				g2.drawString("the power of the devil. It is used to", 90, 230);
				g2.drawString("expel any evils that may be infested", 90, 255);
				g2.drawString("or possessed in a person.", 90, 280);
				g2.drawImage(dispellingGhosts, 105, 315, 240, 135, null);

				g2.drawString("There are two major kinds of exorcisms:", 395, 230);
				g2.fillOval(405, 246, 5, 5);// bullet point
				g2.drawString("Simple/minor exorcisms are for", 415, 255);
				g2.drawString("simple matters, such as those", 415, 280);
				g2.drawString("preparing to get Baptised", 415, 305);
				g2.fillOval(405, 322, 5, 5);// bullet point
				g2.drawString("Second is solemn/major exorcism", 415, 330);
				g2.fillOval(420, 347, 5, 5);// bullet point
				g2.drawString("Rite only be able to performed by", 430, 355);
				g2.drawString("a bishop or priest", 430, 380);
				g2.fillOval(420, 397, 5, 5);// bullet point
				g2.drawString("Directed at liberating a person", 430, 405);
				g2.drawString("from a demonic possession", 430, 430);
			} else if (nextPage == 2) {
				g2.setFont(subHeading);
				g2.drawString("Who can Receive", 125, 142);
				g2.drawString("a Major Exorcism?", 116, 170);
				g2.fillRect(115, 172, 211, 3);

				g2.setFont(normalText);
				g2.drawString("According to the Code of Canon Law,", 90, 200);
				g2.drawString("all the following are eligible for exoricms:", 80, 225);

				g2.fillOval(120, 247, 5, 5);// bullet point
				g2.drawString("Catholics", 130, 255);
				g2.fillOval(120, 272, 5, 5);// bullet point
				g2.drawString("Catechumens", 130, 280);
				g2.fillOval(120, 297, 5, 5);// bullet point
				g2.drawString("Non-Catholic Christians", 130, 305);
				g2.fillOval(120, 322, 5, 5);// bullet point
				g2.drawString("Non-Christian believers", 130, 330);
				g2.drawString("In the case that an exorcism needs to", 90, 355);
				g2.drawString("be performed on an non-Catholic, the", 90, 380);
				g2.drawString("case should be brought to the", 90, 405);
				g2.drawString("Diocesan Bishop", 90, 430);

				g2.setFont(subHeading);
				g2.drawString("Performing an Exorcism", 405, 180);
				g2.fillRect(405, 182, 275, 3);
				g2.setFont(normalText);
				g2.drawString("In this game, you are the exorcist,", 410, 210);
				g2.drawString("and you must exoricse the catholic", 410, 235);
				g2.drawString("ghosts. You are performing a", 410, 260);
				g2.drawString("Major exorcism on these ghosts.", 410, 285);
				g2.drawString("To perform an exorcism you must", 410, 320);
				g2.drawString("channel your inner catholicism to", 410, 345);
				g2.drawString("dispel the ghosts away.", 410, 370);

			} else if (nextPage == 3) {
				g2.setFont(subHeading);
				g2.drawString("Performing an Exorcism", 85, 140);
				g2.fillRect(85, 142, 275, 3);

				g2.setFont(normalText);
				g2.drawString("Use your cursor to draw and copy the", 90, 180);
				g2.drawString("shapes above the ghosts heads.", 90, 205);

				g2.drawString("For example, this ghost has a circle", 90, 230);
				g2.drawString("above its head. So you must replicate", 90, 255);
				g2.drawString("its shape by holding down your mouse", 90, 280);
				g2.drawString("and letting go when you have", 90, 305);
				g2.drawString("made the shape.", 90, 330);

				g2.drawImage(circleExample, 100, 340, 235, 142, null);
				g2.drawString("Shape on the left is drawn", 120, 500);

				g2.setFont(subHeading);
				g2.drawString("More Examples", 453, 165);
				g2.fillRect(453, 167, 179, 3);
				g2.drawImage(triangleExample, 420, 175, 235, 132, null);
				g2.drawImage(horizontalExample, 420, 310, 235, 132, null);
			} else if (nextPage == 4) {
				g2.setFont(subHeading);
				g2.drawString("Successful Exorcism", 100, 132);
				g2.fillRect(100, 134, 240, 3);
				g2.setFont(normalText);
				g2.drawString("You’ll know you’ve successfully drawn", 90, 180);
				g2.drawString("a shape when a pentagram appears", 90, 205);
				g2.drawString("where the ghosts previously were.", 90, 230);
				// #1 IMAGE OF GHOST DRAWING
				// #2 IMAGE OF PENTAGRAM ONCE SUCCESSFULLY DRAWING GHOST

				g2.setFont(subHeading);
				g2.drawString("Multiple Ghosts", 450, 170);
				g2.fillRect(450, 172, 180, 3);
				g2.setFont(normalText);
				g2.drawString("It is vital that you remember that", 410, 200);
				g2.setColor(Color.RED);
				g2.drawString("you must exorcise ghosts from left", 410, 225);
				g2.drawString("to right.", 410, 250);
				g2.setColor(Color.BLACK);
				g2.drawString("This means that you must", 470, 250);
				g2.drawString("first exorcise the ghost on the left", 410, 275);
				g2.drawString("side, and then exorcise the ghost", 410, 300);
				g2.drawString("on the right.", 410, 325);
			}else if(nextPage == 5) {
				g2.setFont(subHeading);
				g2.drawString("Multiple Ghost Example", 90, 140);
				g2.fillRect(90, 142, 270, 3);
				
				g2.drawImage(leftGhostExample, 100, 165, 235, 132, null);
				g2.drawImage(rightGhostExample, 100, 320, 235, 132, null);
				
				g2.drawString("Multishape Ghosts", 440, 180);
				g2.fillRect(440, 182, 215, 3);
				
				g2.setFont(normalText);
				g2.drawString("Beware though, some ghosts have", 410, 220);
				g2.drawString("more than 1 shape. This example has", 410, 245);
				g2.drawString("2. To exorcise it, begin drawing", 410, 270);
				g2.drawString("the shape on the left side. Once", 410, 295);
				g2.drawString("that shape is complete, it will", 410, 320);
				g2.drawString("disappear from the top of its head.", 410, 345);
				g2.drawString("Then, draw the other shape, and the", 410, 370);
				g2.drawString("ghosts will be exorcised", 410, 395);
			}
		}
	}

	public void car(Graphics g, GamePanel gp) throws IOException {
		int carX = carWorldX - gp.getWorldX();
		int carY = carWorldY - gp.getWorldY();
		g.drawImage(car, carX, carY, 96, 192, null);
		if (!carOn && !carUsed && gp.getPlayerX() + 32 > carX && gp.getPlayerX() < carX + 96
				&& gp.getPlayerY() + 72 > carY && gp.getPlayerY() < carY + 192 && p.keyH.iscPressed()) {
			carOn = true;
			p.disableCharacterMovement();
			visible = false;
			animationFrame = 0;
		}
	}

	private int transparency = 0;
	private boolean hasFaded = false;
	private boolean reset = false;

	public void titleScreen(Graphics2D g2) {
		if (carOn) {
			if (reset) {
				transparency = 0;
				hasFaded = false;
				reset = false;
			}

			if (!hasFaded) {
				transparency += 1;
				if (transparency >= 250) {
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
		Color title = new Color(255, 255, 255, transparency);
		g2.setColor(title);
		g2.setFont(new Font("calibri", Font.BOLD, 60));
		if (carWorldX < 3000 && carWorldX < 3500) {
			g2.drawString("Are We Cooked Interactive", 60, 250);
			g2.drawString("Presents...", 280, 400);
		}
		if (carWorldX >= 3500 && carWorldX <= 4600) {
			g2.drawString("Whispers Of", 220, 250);
			g2.drawString("The Deceived", 210, 400);
		}
	}

	private boolean movementPrompt = false;
	private int instructionsX = 640;
	private int instructionsY = 10;
	private boolean hoveringInstructions = false;
	private boolean instructionsPrompt = false;
	private boolean hoveringObjective = false;
	private boolean hoveringMovement = false;
	private Color selected = new Color(144, 50, 50);
	private Color unselected = new Color(193, 45, 57);

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
			if (hoveringObjective) {
				g2.setColor(selected);
				g2.fillRoundRect(305, 250, 195, 62, 20, 20);
			} else if (!hoveringObjective) {
				g2.setColor(unselected);
				g2.fillRoundRect(305, 250, 195, 62, 20, 20);
			}

			g2.setFont(new Font("Calibri", Font.BOLD, 30));
			g2.setColor(Color.WHITE);
			g2.drawString("Movement", 338, 200);
			g2.drawString("Objective", 345, 290);
		}
	}

	private boolean backPressed = false;
	private boolean hoveringBack = false;
	private boolean objectivePrompts = false;
	private boolean inHouse = false;

	public void backMenu(Graphics2D g2) {
		if (!hoveringBack) {
			g2.setColor(unselected);
			g2.fillRoundRect(335, 430, 130, 45, 10, 10);
			g2.setFont(new Font("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 357, 460);
		} else {
			g2.setColor(selected);
			g2.fillRoundRect(335, 430, 130, 45, 10, 10);
			g2.setFont(new Font("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 357, 460);
		}
		if (backPressed) {
			if (movementPrompt) {
				movementPrompt = false;
				instructionsPrompt = true;
			} else if (objectivePrompts) {
				objectivePrompts = false;
				instructionsPrompt = true;
			}

			else if (instructionsPrompt) {
				instructionsPrompt = false;
				input.setInstructionsPressed(false);
			}
			backPressed = false;
		}
	}

	public void prompts(Graphics2D g2) {
		if (movementPrompt) {
			instructionsPrompt = false;
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.drawImage(wasdKey, 285, 146 + 20, 250, 250, null);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Movement", 310, 112 + 10);
			g2.fillRect(307, 115 + 10, 195, 2);
			g2.drawString("Left", 210, 305 + 20);
			g2.drawString("Right", 530, 305 + 20);
			g2.drawString("Up", 382, 178 + 20);
			g2.drawString("Down", 351, 368 + 20);
		}
		if (objectivePrompts) {
			instructionsPrompt = false;

			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Current Objective", 180, 112 + 10);
			g2.fillRect(180, 116 + 10, 410, 2);

			if (m.getCurrentMap() == 3 && !carUsed && !m.isDoneNightmare()) {
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(door, 300, 50 + 80, 200, 200, null);
				g2.drawString("Go in the house", 270, 330);
			}
			else if (m.getCurrentMap() == 2 && !m.isDoneNightmare()) {
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(bed, 360, 200, 75, 75, null);
				g2.drawString("Go to sleep", 300, 330);
			}
			else if (m.getCurrentMap() == 3 && m.isDoneNightmare() && !carUsed) {
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(car, 350, 160, 96, 192, null);
				g2.drawString("Get in the car", 280, 330);
			}
			/*
			g2.drawImage(exorcism, 545, 95 + 80, 120, 75, null);
			g2.setFont(new Font("Monospaced", Font.BOLD, 20));
			g2.setColor(Color.BLACK);
			// Exorcism keybind
			g2.drawString("Press E to", 545, 180 + 80);
			g2.drawString("Exorcise Ghosts", 515, 200 + 80);
			*/
		}
	}

	public void animation(GamePanel gp) {
		if (carOn) {
			carWorldX += 3;
			gp.setWorldX(gp.getWorldX() + 3);
			animationFrame++;

			if (carWorldX >= 4700) {
				carWorldX = 4700;
				carOn = false;
				carUsed = true;
				car = brokenCar;
				j.setJumpscare(true);
				visible = true;
				p.disableCharacterMovement();
			}
		}
	}

	private boolean inGraveYard = false;
	private int graveX = 4600;
	private int graveY = 333;
	private BufferedImage ghost;
	private boolean firstTime = true;

	public static int ghostRandomizer() {
		// if (level == 1) {
		return (int) (Math.random() * 5) + 1;
		// } else if (level == 2) {
		// return (int) (Math.random() * 4) + 6;
		// }
		// return 0;
	}

	public void ghost(Graphics2D g2, int ghostGraveYardX, int ghostGraveYardY, int width, int height, GamePanel gp)
			throws IOException {

		int ghostX = ghostGraveYardX - gp.getWorldX();
		int ghostY = ghostGraveYardY - gp.getWorldY();

		ghost = ImageIO.read(new File("src/textures/ghost.png"));
		g2.drawImage(ghost, ghostX, ghostY, width, height, null);

	}

	private int level = 1;
	private String ghostShape = "";

	public void drawGhost(Graphics2D g2, int offsetY, GamePanel gp) throws IOException {
		if (level == 1) {
			if (!destroyCircle) {
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Circle", 250, 196, gp);
			}
			if (!destroyTriangle) {
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Triangle", 250, 196, gp);
			}

		} else if (level == 2) {
			if (!destroyHorizontal) {
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Horizontal", 250, 196, gp);
			}

			if (!destroyZigzag) {
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Zigzag", 250, 196, gp);
			}

		} else if (level == 3) {
			if (!destroyVertical) {
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Vertical", 250, 196, gp);
			}
			if (!destroyHorizontal) {
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Horizontal", 250, 196, gp);
			}

		} else if (level == 4) {
			if (!destroyHorizontal) {
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Horizontal", 250, 196, gp);
			}
			if (!destroyTriangle) {
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Triangle", 250, 196, gp);
			}

		} else if (level == 5) {
			minigameGhost(g2, 1200 - 20, 820 - 0 + offsetY, "duoghost1", 250, 196, gp);
			if (!destroyLeftGhost) {
				minigameGhost(g2, 1200 - 210, 820 + 50 + offsetY, "duoghost2", 250, 196, gp);
			}
		} else if (level == 6) {
			minigameGhost(g2, 1200 - 20, 820 - 0 + offsetY, "duoghost3", 250, 196, gp);
			if (!destroyLeftGhost) {
				minigameGhost(g2, 1200 - 210, 820 + 50 + offsetY, "duoghost4", 250, 196, gp);
			}
		} else if (level == 7) {
			minigameGhost(g2, 1200 - 20, 820 - 0 + offsetY, "trioghost", 250, 196, gp);
		} else if (level == 8) {
			minigameGhost(g2, 1100 - 20, 820 - 0 + offsetY, "bossghost", 250, 196, gp);
		}
		/*
		 * if (ghostNumber == 1 && !Input.isTriangle) { Items.minigameGhost(g2, 1200 +
		 * offsetX, 820 + offsetY, "Triangle", 250, 196); } else if (ghostNumber == 2 &&
		 * !Input.isCircle) { Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY,
		 * "Circle", 250, 196); } else if (ghostNumber == 3 && !Input.isZigzag) {
		 * Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Zigzag", 250, 196); }
		 * else if (ghostNumber == 4 && !Minigame.currentShape.equals("vertical")) {
		 * Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Vertical", 250, 196);
		 * } else if (ghostNumber == 5 && !Minigame.currentShape.equals("horizontal")) {
		 * Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "Horizontal", 250,
		 * 196); } else if (ghostNumber == 6) { Items.minigameGhost(g2, 1200 + offsetX,
		 * 820 + offsetY, "duoGhost", 250, 196); } else if (ghostNumber == 7) {
		 * Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "duoGhost", 250, 196);
		 * } else if (ghostNumber == 8) { Items.minigameGhost(g2, 1200 + offsetX, 820 +
		 * offsetY, "duoGhost", 250, 196); } else if (ghostNumber == 9) {
		 * Items.minigameGhost(g2, 1200 + offsetX, 820 + offsetY, "duoGhost", 250, 196);
		 * }
		 */
	}

	private int ghostNumberLeft;
	private int ghostNumberRight;
	private boolean ghostAppeared = false;
	private int ghostCount = 0;
	private boolean reachedPeak = false;

	private int yVal = 0;

	public void ghostLogic(Graphics2D g2, GamePanel gp) throws IOException {
		// Hovering ghost
		if (!reachedPeak) {
			if (yVal <= 40) {
				yVal++;
			}
			if (yVal == 40) {
				reachedPeak = true;
			}
		}
		if (reachedPeak) {
			if (yVal >= -40) {
				yVal--;
			}
			if (yVal == -40) {
				reachedPeak = false;
			}
		}
		System.out.println(ghostCount);

		if (ghostCount == 2 && !levelShape.equals("duoghost1") && !levelShape.equals("duoghost2")
				&& !levelShape.equals("duoghost3") && !levelShape.equals("duoghost4") && !levelShape.equals("trioghost")
				&& !levelShape.equals("bossghost")) {
			level++;
			ghostCount = 0;
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
		}

		if (ghostCount == 4 && (levelShape.equals("duoghost1") || levelShape.equals("duoghost2")
				|| levelShape.equals("duoghost3") || levelShape.equals("duoghost4"))) {

			level++;
			ghostCount = 0;
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
			destroyLeftGhost = false;
			destroyRightGhost = false;

		}

		if (ghostCount == 3 && destroyTrioGhost) {

			level++;
			ghostCount = 0;
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
			destroyTrioGhost = false;

		}
		if (ghostCount == 5 && destroyBossGhost) {
			level++;
			System.out.println("Human thingy");
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
			destroyBossGhost = false;
		}

		drawGhost(g2, yVal, gp);
	}

	private boolean destroyDuoGhost = false;
	private boolean addedShapes = false;
	public boolean destroyLeftGhost = false;
	public boolean destroyRightGhost = false;
	public boolean destroyTrioGhost = false;
	public boolean destroyBossGhost = false;

	private String levelShape = " ";

	public void randomShape(String shape, int ghostX, int ghostY, Graphics2D g2, int offsetX) {
		if (shape.equalsIgnoreCase("Triangle")) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			int[] xTriangle = { ghostX - 260 + offsetX, ghostX - 230 + offsetX, ghostX - 290 + offsetX };
			int[] yTriangle = { ghostY - 350, ghostY - 290, ghostY - 290 };
			g2.drawPolygon(xTriangle, yTriangle, 3);
		} else if (shape.equalsIgnoreCase("Circle")) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			g2.drawOval(ghostX - 290 + offsetX, ghostY - 350, 60, 60);
		} else if (shape.equalsIgnoreCase("Zigzag")) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			int[] xZigzag = { ghostX - 150 - 140 + offsetX, ghostX - 100 - 140 + offsetX, ghostX - 150 - 140 + offsetX,
					ghostX - 100 - 140 + offsetX };
			int[] yZigzag = { ghostY - 195 - 140, ghostY - 195 - 140, ghostY - 145 - 140, ghostY - 145 - 140 };
			g2.drawPolyline(xZigzag, yZigzag, 4);
		} else if (shape.equalsIgnoreCase("Vertical")) {
			g2.setColor(Color.RED);
			g2.fillRect(ghostX - 260 + offsetX, ghostY - 355, 4, 60);
		} else if (shape.equalsIgnoreCase("Horizontal")) {
			g2.setColor(Color.RED);
			g2.fillRect(ghostX - 290 + offsetX, ghostY - 313, 60, 4);
		} else if (shape.equalsIgnoreCase("duoghost1")) {
			levelShape = shape;
			if (!destroyHorizontal) {
				ghostShape = "Horizontal";
			} else if (destroyHorizontal && !destroyZigzag) {
				ghostShape = "Zigzag";
			}
			if (!addedShapes && !destroyHorizontal) {
				shapesArray.add("Horizontal");
			}
			if (!addedShapes && !destroyZigzag) {
				shapesArray.add("Zigzag");
			}

			for (int i = 0; i < shapesArray.size(); i++) {
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyHorizontal && shapesArray.contains("Horizontal")) {
				shapesArray.remove("Horizontal");
				addedShapes = true;
			}

			shapesArray.clear();
			if (!destroyRightGhost) {
				if (isDestroyHorizontal() && isDestroyZigzag()) {
					destroyRightGhost = true;
				}
			}
		} else if (shape.equalsIgnoreCase("duoghost2")) {
			levelShape = shape;
			if (!destroyVertical) {
				ghostShape = "Vertical";
			} else if (destroyVertical && !destroyCircle) {
				ghostShape = "Circle";
			}
			if (!addedShapes && !destroyVertical) {
				shapesArray.add("Vertical");
			}
			if (!addedShapes && !destroyCircle) {
				shapesArray.add("Circle");
			}

			for (int i = 0; i < shapesArray.size(); i++) {
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyVertical && shapesArray.contains("Vertical")) {
				shapesArray.remove("Vertical");
				addedShapes = true;
			}
			shapesArray.clear();
			if (!destroyLeftGhost) {
				if (isDestroyVertical() && isDestroyCircle()) {
					destroyLeftGhost = true;
				}
			}
		} else if (shape.equalsIgnoreCase("duoghost3")) {
			levelShape = shape;
			if (!destroyHorizontal) {
				ghostShape = "Horizontal";
			} else if (destroyHorizontal && !destroyCircle) {
				ghostShape = "Circle";
			}
			if (!addedShapes && !destroyHorizontal) {
				shapesArray.add("Horizontal");
			}
			if (!addedShapes && !destroyCircle) {
				shapesArray.add("Circle");
			}

			for (int i = 0; i < shapesArray.size(); i++) {
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyHorizontal && shapesArray.contains("Horizontal")) {
				shapesArray.remove("Horizontal");
				addedShapes = true;
			}

			shapesArray.clear();
			if (!destroyRightGhost) {
				if (isDestroyTriangle() && isDestroyZigzag()) {
					destroyRightGhost = true;
				}
			}
		} else if (shape.equalsIgnoreCase("duoghost4")) {
			levelShape = shape;
			if (!destroyTriangle) {
				ghostShape = "Triangle";
			} else if (destroyTriangle && !destroyZigzag) {
				ghostShape = "Zigzag";
			}
			if (!addedShapes && !destroyTriangle) {
				shapesArray.add("Triangle");
			}
			if (!addedShapes && !destroyZigzag) {
				shapesArray.add("Zigzag");
			}

			for (int i = 0; i < shapesArray.size(); i++) {
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyHorizontal && shapesArray.contains("Triangle")) {
				shapesArray.remove("Triangle");
				addedShapes = true;
			}

			shapesArray.clear();
			if (!destroyLeftGhost) {
				if (isDestroyTriangle() && isDestroyZigzag()) {
					destroyLeftGhost = true;
				}
			}
		} else if (shape.equalsIgnoreCase("trioghost")) {
			levelShape = shape;
			if (!destroyTriangle) {
				ghostShape = "Triangle";
			} else if (destroyTriangle && !destroyZigzag) {
				ghostShape = "Zigzag";
			} else if (destroyTriangle && destroyZigzag && !destroyCircle) {
				ghostShape = "Circle";
			}
			if (!destroyTriangle) {
				shapesArray.add("Triangle");
			}
			if (!destroyZigzag) {
				shapesArray.add("Zigzag");
			}
			if (!destroyCircle) {
				shapesArray.add("Circle");
			}

			for (int i = 0; i < shapesArray.size(); i++) {
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyTriangle && shapesArray.contains("Zigzag")) {
				shapesArray.remove("Zigzag");
			}
			if (destroyZigzag && shapesArray.contains("Circle")) {
				shapesArray.remove("Circle");
				destroyTrioGhost = true;
			}
			shapesArray.clear();
		} else if (shape.equalsIgnoreCase("bossghost")) {
			levelShape = shape;
			if (!destroyHorizontal) {
				ghostShape = "Horizontal";
			} else if (destroyHorizontal && !destroyZigzag) {
				ghostShape = "Zigzag";
			} else if (destroyHorizontal && destroyZigzag && !destroyCircle) {
				ghostShape = "Circle";
			} else if (destroyHorizontal && destroyZigzag && destroyCircle && !destroyVertical) {
				ghostShape = "Vertical";
			} else if (destroyHorizontal && destroyZigzag && destroyCircle && destroyVertical && !destroyTriangle) {
				ghostShape = "Triangle";
			}
			if (!destroyHorizontal) {
				shapesArray.add("Horizontal");
			}
			if (!destroyZigzag) {
				shapesArray.add("Zigzag");
			}
			if (!destroyCircle) {
				shapesArray.add("Circle");
			}
			if (!destroyVertical) {
				shapesArray.add("Vertical");
			}
			if (!destroyTriangle) {
				shapesArray.add("Triangle");
				destroyBossGhost = true;
			}
			System.out.println(shapesArray);

			for (int i = 0; i < shapesArray.size(); i++) {
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyTriangle && shapesArray.contains("Zigzag")) {
				shapesArray.remove("Zigzag");
			}
			if (destroyZigzag && shapesArray.contains("Circle")) {
				shapesArray.remove("Circle");
			}
			if (destroyZigzag && shapesArray.contains("Vertical")) {
				shapesArray.remove("Vertical");
			}
			if (destroyZigzag && shapesArray.contains("Triangle")) {
				shapesArray.remove("Triangle");
			}

			shapesArray.clear();
		}

	}

	private int counting = 0;
	private boolean duoGhostInitialized = false;
	private int duoShapeLeft = 0;
	private int duoShapeRight = 0;

	public void shapeRandomizer() {

		String shapes[] = { "Triangle", "Circle", "Zigzag", "Vertical", "Horizontal" };
		int num = (int) (Math.random() * shapes.length) + 1;
	}

	private boolean destroyCircle = false;
	private boolean destroyTriangle = false;
	private boolean destroyZigzag = false;
	private boolean destroyHorizontal = false;
	private boolean destroyVertical = false;

	public void minigameGhost(Graphics2D g2, int ghotsX, int ghotsY, String shape, int width, int height, GamePanel gp)
			throws IOException {
		int ghostX = ghotsX - worldX;
		int ghostY = ghotsY - worldY;
		ghostShape = shape;
		ghost = ImageIO.read(new File("src/textures/minigameghost.png"));
		randomShape(ghostShape, ghostX, ghostY, g2, 0);
		ghost(g2, ghostX, ghostY, width, height, gp);

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

	public void graveyard(Graphics2D g2, GamePanel gp) throws IOException {

		int inGraveYardX = graveX - gp.getWorldX();
		int inGraveYardY = graveY - gp.getWorldY();
		if (inGraveYard) {
			g2.drawImage(ghost, 480, 280, 250, 196, null);
			npc.text(g2, 2);
		}

	}

	private boolean hoveringYes = false;
	private boolean hoveringNo = false;
	private boolean inConfirmation = false;
	private boolean yesPressed = false;
	private boolean noPressed = false;

	public void confirmation(Graphics2D g2, String text1, String text2, int textX1, int textX2) {

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
		g2.drawString(text1, textX1, 250);
		g2.drawString(text2, textX2, 295);
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

	private boolean helpPressed = false;
	private boolean hoveringX = false;

	public void exitMenuOption(Graphics2D g2) {
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

	public void help(Graphics2D g2) {
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

	private boolean creditsPressed = false;

	public void credits(Graphics2D g2) {
		if (creditsPressed) {
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Credits", 315, 115);
			g2.fillRect(315, 117, 167, 2);

			g2.setFont(new Font("Calibri", Font.BOLD, 20));
			g2.drawString("Project Lead", 100 + 100, 175);
			g2.fillRect(100 + 100, 177, 150, 2);
			g2.drawString("Noah Sussman", 100 + 100, 195);
			g2.drawString("Senior Developer", 100 + 100, 230);
			g2.fillRect(100 + 110, 232, 150, 2);
			g2.drawString("Akhilan Saravanan", 100 + 100, 252);
			g2.drawString("Junior Developer", 100 + 100, 280);
			g2.fillRect(100 + 100, 282, 150, 2);
			g2.drawString("Rudra Garg", 100 + 100, 300);
			g2.drawString("UX/UI Designer", 100 + 100, 335);
			g2.fillRect(100 + 100, 337, 150, 2);
			g2.drawString("Rudra Garg", 110 + 100, 355);
			g2.drawString("Playtester", 110 + 100, 385);
			g2.fillRect(100 + 100, 387, 110, 2);
			g2.drawString("Sammy Jiang", 100 + 100, 405);
			g2.drawString("Voice Actors", 300 + 110, 175);
			g2.fillRect(300 + 110, 177, 150, 2);
			g2.drawString("Jeff: Jhonard Ramos", 300 + 100, 195);
			g2.drawString("Ghost Graveyard: Noah Sussman", 300 + 100, 220);
			g2.drawString("Doctor: Rudra Garg", 300 + 100, 245);
			g2.drawString("Ghost Doctrine: Akhilan Saravanan", 300 + 100, 270);
			g2.fillRect(100 + 100, 387, 120, 2);
			exitMenuOption(g2);
		}
	}

	// Timer created from
	// https://stackoverflow.com/questions/1006611/java-swing-timer
	private Timer time;

	public void timer() {
		time = new Timer(723, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playGif = false;
				staticImageBook = true;
				time.stop();
			}
		});
		if (time != null && time.isRunning()) {
			time.stop();
		}
		time.setRepeats(false);
		time.start();
	}

	// Getters and Setters
	public void setInput(Input input) {
		this.input = input;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public void setM(Maps m) {
		this.m = m;
	}

	public void setJ(Jumpscare j) {
		this.j = j;
	}

	public int getWorldX() {
		return worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public boolean isCarOn() {
		return carOn;
	}

	public int getAnimationFrame() {
		return animationFrame;
	}

	public int getCarWorldX() {
		return carWorldX;
	}

	public boolean isInHouse(boolean inHouse) {
		return inHouse;
	}

	public int getCarWorldY() {
		return carWorldY;
	}

	public int getDoctrineWorldX() {
		return doctrineWorldX;
	}

	public int getDoctrineWorldY() {
		return doctrineWorldY;
	}

	public boolean isEnterBook() {
		return enterBook;
	}

	public boolean isHoveringNextPage() {
		return hoveringNextPage;
	}

	public boolean isHoveringExitPage() {
		return hoveringExitPage;
	}

	public boolean isHoveringInstructions() {
		return hoveringInstructions;
	}

	public boolean isInstructionsPrompt() {
		return instructionsPrompt;
	}

	public boolean isHoveringObjective() {
		return hoveringObjective;
	}

	public boolean isHoveringMovement() {
		return hoveringMovement;
	}

	public boolean isBackPressed() {
		return backPressed;
	}

	public boolean isHoveringBack() {
		return hoveringBack;
	}

	public boolean isInHouse() {
		return inHouse;
	}

	public boolean isInGraveYard() {
		return inGraveYard;
	}

	public void setInGraveYard(boolean inGraveYard) {
		this.inGraveYard = inGraveYard;
	}

	public int getGraveX() {
		return graveX;
	}

	public int getGraveY() {
		return graveY;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public String getGhostShape() {
		return ghostShape;
	}

	public int getGhostCount() {
		return ghostCount;
	}

	public boolean isDestroyDuoGhost() {
		return destroyDuoGhost;
	}

	public boolean isDestroyCircle() {
		return destroyCircle;
	}

	public boolean isDestroyTriangle() {
		return destroyTriangle;
	}

	public boolean isDestroyZigzag() {
		return destroyZigzag;
	}

	public boolean isDestroyHorizontal() {
		return destroyHorizontal;
	}

	public boolean isDestroyVertical() {
		return destroyVertical;
	}

	public boolean isHoveringYes() {
		return hoveringYes;
	}

	public boolean isHoveringNo() {
		return hoveringNo;
	}

	public boolean isInConfirmation() {
		return inConfirmation;
	}

	public boolean isHelpPressed() {
		return helpPressed;
	}

	public boolean isHoveringX() {
		return hoveringX;
	}

	public boolean isCreditsPressed() {
		return creditsPressed;

	}

	public void setHoveringInstructions(boolean hoveringInstructions) {
		this.hoveringInstructions = hoveringInstructions;
	}

	public void setHoveringNextPage(boolean hoveringNextPage) {
		this.hoveringNextPage = hoveringNextPage;
	}

	public void setHoveringExitPage(boolean hoveringExitPage) {
		this.hoveringExitPage = hoveringExitPage;
	}

	public void setInstructionsPrompt(boolean instructionsPrompt) {
		this.instructionsPrompt = instructionsPrompt;
	}

	public void setHoveringObjective(boolean hoveringObjective) {
		this.hoveringObjective = hoveringObjective;
	}

	public void setHoveringMovement(boolean hoveringMovement) {
		this.hoveringMovement = hoveringMovement;
	}

	public void setHoveringBack(boolean hoveringBack) {
		this.hoveringBack = hoveringBack;
	}

	public void setHoveringYes(boolean hoveringYes) {
		this.hoveringYes = hoveringYes;
	}

	public void setHoveringNo(boolean hoveringNo) {
		this.hoveringNo = hoveringNo;
	}

	public void setHoveringX(boolean hoveringX) {
		this.hoveringX = hoveringX;
	}

	public void setGhostCount(int ghostCount) {
		this.ghostCount = ghostCount;
	}

	public void setDestroyDuoGhost(boolean destroyDuoGhost) {
		this.destroyDuoGhost = destroyDuoGhost;
	}

	public void setDestroyCircle(boolean destroyCircle) {
		this.destroyCircle = destroyCircle;
	}

	public void setDestroyTriangle(boolean destroyTriangle) {
		this.destroyTriangle = destroyTriangle;
	}

	public void setDestroyZigzag(boolean destroyZigzag) {
		this.destroyZigzag = destroyZigzag;
	}

	public void setDestroyHorizontal(boolean destroyHorizontal) {
		this.destroyHorizontal = destroyHorizontal;
	}

	public void setDestroyVertical(boolean destroyVertical) {
		this.destroyVertical = destroyVertical;
	}

	public void setBackPressed(boolean backPressed) {
		this.backPressed = backPressed;
	}

	public boolean isMovementPrompt() {
		return movementPrompt;
	}

	public void setMovementPrompt(boolean movementPrompt) {
		this.movementPrompt = movementPrompt;
	}

	public boolean isObjectivePrompts() {
		return objectivePrompts;
	}

	public void setObjectivePrompts(boolean objectivePrompts) {
		this.objectivePrompts = objectivePrompts;
	}

	public void setCreditsPressed(boolean creditsPressed) {
		this.creditsPressed = creditsPressed;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setEnterBook(boolean enterBook) {
		this.enterBook = enterBook;
	}

	public void setPlayGif(boolean playGif) {
		this.playGif = playGif;
	}

	public void setStaticImageBook(boolean staticImageBook) {
		this.staticImageBook = staticImageBook;
	}

	public void setYesPressed(boolean yesPressed) {
		this.yesPressed = yesPressed;
	}

	public void setNoPressed(boolean noPressed) {
		this.noPressed = noPressed;
	}

	public void setHelpPressed(boolean helpPressed) {
		this.helpPressed = helpPressed;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isYesPressed() {
		return yesPressed;
	}

	public boolean isNoPressed() {
		return noPressed;
	}

	public void setInHouse(boolean inHouse) {
		this.inHouse = inHouse;
	}

	public void setInConfirmation(boolean inConfirmation) {
		this.inConfirmation = inConfirmation;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public boolean isCarUsed() {
		return carUsed;
	}

	public boolean isCarSceneDone() {
		return carSceneDone;
	}

	public void setCarSceneDone(boolean carSceneDone) {
		this.carSceneDone = carSceneDone;
	}
	
	public void setDoingDoctrineGhost (boolean dg) {
		this.doingDoctrineGhost = dg;
	}

	public boolean isDoingDoctrineGhost() {
		return doingDoctrineGhost;
	}
}
