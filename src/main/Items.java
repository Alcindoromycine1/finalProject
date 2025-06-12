/**
 * @author Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * @since April 2, 2025
 * @version 2.0
 * Final Project ICS4U0
 * Whispers of the Deceived
 */
package main;

import Horror.Jumpscare;
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
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import interfaces.ReadFromFile;

/**
 * This class handles the items in the game, including reading from files,
 * displaying images, and managing game mechanics related to items.
 * 
 */
public class Items implements ReadFromFile {
	private Input input; // Reference to the Input class to access Input methods and properties
	private Npc npc; // Reference to the Npc class to access Npc methods and properties
	private Player p; // Reference to the Player class to access Player methods and properties
	private Maps m; // Reference to the Maps class to access Maps methods and properties
	private Jumpscare j; // Reference to the Jumpscare class to access Jumpscare methods and properties

	private int worldX; // Player X position relative to the world
	private int worldY; // Player Y position relative to the world

	private BufferedImage mirror; // BufferedImage variable to store the mirror image
	private BufferedImage doctrine; // BufferedImage variable to store the doctrine image
	private BufferedImage book; // BufferedImage variable to store the book image
	private BufferedImage car; // BufferedImage variable to store the car image
	private BufferedImage brokenCar;// BufferedImage variable to store the brokenCar image
	private BufferedImage wasdKey; // BufferedImage variable to store the wasdKey image
	private BufferedImage door; // BufferedImage variable to store the door image
	private BufferedImage exorcism; // BufferedImage variable to store the exorcism image
	private BufferedImage bed; // BufferedImage variable to store the bed image
	private BufferedImage verticalMultishape; // BufferedImage variable to store the verticalMultishape image
	private BufferedImage circleMultishape; // BufferedImage variable to store the circleMultishape image
	private BufferedImage dispellingGhosts; // BufferedImage variable to store the dispellingGhosts image
	private BufferedImage circleExample; // BufferedImage variable to store the circleExample image
	private BufferedImage triangleExample; // BufferedImage variable to store the triangleExample image
	private BufferedImage horizontalExample; // BufferedImage variable to store the horizontalExample image
	private BufferedImage leftGhostExample; // BufferedImage variable to store the leftGhostExample image
	private BufferedImage rightGhostExample; // BufferedImage variable to store the rightGhostExample image
	private BufferedImage multiShapeGhost1; // BufferedImage variable to store the multiShapeGhost1 image
	private Font subHeading; // Font variable to store the font for the sub heading
	private Font header; // Font variable to store the font for the header
	private Font normalText; // Font variable to store the font for normal text
	private BufferedImage orderFullFrame; // BufferedImage variable to store the orderFullFrame image
	private BufferedImage triangleRemovedOrder; // BufferedImage variable to store the triangleRemovedOrder image
	private BufferedImage orderCloseUp; // BufferedImage variable to store the orderCloseUp image
	private BufferedImage human; // BufferedImage variable to store the human image

	private ImageIcon pageFlipping; // ImageIcon variable to store the pageFlipping GIF
	private Sound bookFlipSound; // Sound variable to store the bookFlipSound sound

	private boolean doneDoctrineGhost = false; // boolean variable to store the the value for if the doctrine ghost has
												// been passed
	private boolean movementPrompt = false;// checks if you are in the movement menu
	private int instructionsX = 640;// x coordinate of the instructions menu that appears always in-game
	private int instructionsY = 10;// y coordinate of the instructions menu that appears always in-game
	private boolean hoveringInstructions = false;// checks if you are hovering over the instructions menu
	private boolean instructionsPrompt = false;// checks if you are in the instructions menu
	private boolean hoveringObjective = false;// checks if you are hovering over the objective button
	private boolean hoveringMovement = false;// checks if you are hovering over the movement button
	private int transparency = 0;// used for fading the text in the title screen scene
	private boolean hasFaded = false;// if the text has faded in
	private boolean reset = false;// rest the fading so you can use it for the next text
	private Color selected = new Color(144, 50, 50);// colour of the buttons when they are being hovered by the cursor
	private Color unselected = new Color(193, 45, 57);// colour of the buttons when they are not being hovered by the
														// cursor
	private int level = 1;// the level in the exorcism room
	private String ghostShape = "";// the shape that the ghosts have in that current level
	private boolean inGraveYard = false;// checks if you are in the graveyard
	private int graveX = 4600;// the x coordinate of the collision when you enter the graveyard
	private int graveY = 333;// the y coordinate of the collision when you enter the graveyard
	private BufferedImage ghost;// ghost image
	private boolean firstTime = true;// first time in the graveyard (prevents the scene from occuring twice)
	private ArrayList<String> shapesArray = new ArrayList<String>(); // ArrayList to store shapes.
	private boolean carOn = false;// checks whether the car is on (being used)
	private int animationFrame = 0;// how long the car is on for
	private int carWorldX = 1295;// the x coordinate of the car (used for collision)
	private int carWorldY = 770;// the y coordinate of the car (used for collision)
	private int doctrineWorldX = 6000;// the x coordinate of the doctrine (used for collision)
	private int doctrineWorldY = 525;// the y coordinate of the doctrine (used for collision)
	private boolean visible = true;// makes it so that the character disappears when you go into the car
	private boolean carUsed = false;// when you've already used the car
	private boolean carSceneDone = false;// when you've finished the car scene
	private boolean once = false;
	private boolean doingDoctrineGhost = false;
	private boolean enterBook = false;
	private int nextPage = 0;
	private boolean hoveringNextPage = false;
	private boolean playGif = false;
	private boolean staticImageBook = false;
	private boolean hoveringExitPage = false;
	private boolean backPressed = false;
	private boolean hoveringBack = false;
	private boolean objectivePrompts = false;
	private boolean inHouse = false;
	private int ghostCount = 0;
	private boolean reachedPeak = false;
	private int yVal = 0;
	private boolean destroyDuoGhost = false;
	private boolean addedShapes = false;
	public boolean destroyLeftGhost = false;
	public boolean destroyRightGhost = false;
	public boolean destroyTrioGhost = false;
	public boolean destroyBossGhost = false;
	private String levelShape = " ";
	private boolean destroyCircle = false;
	private boolean destroyTriangle = false;
	private boolean destroyZigzag = false;
	private boolean destroyHorizontal = false;
	private boolean destroyVertical = false;
	private boolean hoveringYes = false;
	private boolean hoveringNo = false;
	private boolean inConfirmation = false;
	private boolean yesPressed = false;
	private boolean noPressed = false;
	private boolean helpPressed = false;
	private boolean hoveringX = false;
	private boolean creditsPressed = false;
	private Timer time;

	/**
	 * Constructor for the Items Class
	 * 
	 * @param gp
	 */
	public Items(GamePanel gp) {

		this.input = gp.getId(); // sets the local Input object to the GamePanel Input object
		this.npc = gp.getN(); // sets the local Npc object to the GamePanel Npc object
		this.p = gp.getP(); // sets the local Player object to the GamePanel Player object
		this.m = gp.getM(); // sets the local Maps object to the GamePanel Maps object
		this.j = gp.getJ(); // sets the local Jumpscare object to the GamePanel Jumpscare object

		worldX = gp.getWorldX(); // sets the local worldX variable to the GamePanel worldX variable
		worldY = gp.getWorldY(); // sets the local worldY variable to the GamePanel worldY variable

		readFile(); // calls the readFile method to set the correct values for the file.
	}

	@Override
	/**
	 * @see WASD Keys. (2025). Istockphoto.com.
	 *      {@link https://media.istockphoto.com/id/1193231012/vector/computer-gamer-keyboard-wasd-keys-vector-illustration-wasd-keys-game-control-keyboard-buttons.jpg?s=612x612&w=0&k=20&c=-DJ6CFewXZ_Oofp_BsYya5KniByRkVW3EAHYICWIOaU={@link https://media.istockphoto.com/id/1193231012/vector/computer-gamer-keyboard-wasd-keys-vector-illustration-wasd-keys-game-control-keyboard-buttons.jpg?s=612x612&w=0&k=20&c=-DJ6CFewXZ_Oofp_BsYya5KniByRkVW3EAHYICWIOaU=}
	 * @see Open Close Door Pixel Art. (2025). Freepik.com.
	 *      {@link https://img.freepik.com/premium-vector/open-close-door-pixel-art-style_475147-1239.jpg}
	 * @see Cross. (2025). Creativefabrica.com.
	 *      {@link https://www.creativefabrica.com/wp-content/uploads/2023/03/22/pixel-art-wooden-cross-vector-Graphics-65026120-1.jpg}
	 * @see Free Flip Sound Effects Download - Pixabay,
	 *      {@link https://www.pixabay.com/sound-effects/search/flip/}.
	 * 
	 */
	public void readFile() {
		try {
			header = new Font("Arial", Font.BOLD, 35); // intializes the header font
			subHeading = new Font("Arial", Font.BOLD, 24); // initalizes the sub heading font
			normalText = new Font("Arial", Font.PLAIN, 17); // initializes the normal text font
			dispellingGhosts = ImageIO.read(new File("src/textures/dispellingGhosts.png"));
			circleExample = ImageIO.read(new File("src/textures/circleExample.png")); // Initializes the circleExample
																						// image
			triangleExample = ImageIO.read(new File("src/textures/triangleExample.png"));
			horizontalExample = ImageIO.read(new File("src/textures/horizontalExample.png"));
			leftGhostExample = ImageIO.read(new File("src/textures/leftGhostExample.png"));
			rightGhostExample = ImageIO.read(new File("src/textures/rightGhostExample.png"));
			multiShapeGhost1 = ImageIO.read(new File("src/textures/multiShapeGhost1.png"));
			circleMultishape = ImageIO.read(new File("src/textures/circleMultishape.png"));
			verticalMultishape = ImageIO.read(new File("src/textures/verticalMultishape.png"));
			mirror = ImageIO.read(new File("src/textures/jeffMirror.png"));
			doctrine = ImageIO.read(new File("src/textures/doctrine.png"));
			book = ImageIO.read(new File("src/textures/books.png"));
			car = ImageIO.read(new File("src/textures/car.png"));
			brokenCar = ImageIO.read(new File("src/textures/destroyedCar.png"));
			wasdKey = ImageIO.read(new File("src/textures/wasdKey.png")); // see first link above
			door = ImageIO.read(new File("src/textures/door.png"));// see second link above
			exorcism = ImageIO.read(new File("src/textures/exorcism.png"));// see third link above
			bed = ImageIO.read(new File("src/textures/bed.png"));
			pageFlipping = new ImageIcon("src/textures/books.gif");
			bookFlipSound = new Sound("src/sound/bookFlip.wav");// see fourth link above
			orderFullFrame = ImageIO.read(new File("src/textures/orderFullFrame.png"));
			triangleRemovedOrder = ImageIO.read(new File("src/textures/removedTriangleOrder.png"));
			orderCloseUp = ImageIO.read(new File("src/textures/orderCloseUp.png"));
			human = ImageIO.read(new File("src/textures/character.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the book flipping sound
	 */
	public void playBookFlipSound() {
		if (!bookFlipSound.isPlaying()) {
			bookFlipSound.play();
		}
	}

	/**
	 * Stops the book flipping sound
	 */
	public void stopBookFlipSound() {
		bookFlipSound.stop();
	}

	/**
	 * The image of the mirror in the mirror scene
	 * 
	 * @param g2
	 * @throws IOException
	 */
	public void houseMirror(Graphics2D g2) throws IOException {
		g2.drawImage(mirror, 360, 200, 50, 50, null);
	}

	/**
	 * Graphics of the doctrine
	 * 
	 * @param g2
	 * @param gp
	 * @throws IOException
	 */
	public void doctrine(Graphics2D g2, GamePanel gp) throws IOException {

		int doctrineX = doctrineWorldX - gp.getWorldX();// x position of the doctrine
		int doctrineY = doctrineWorldY - gp.getWorldY();// y position of the doctrine

		g2.drawImage(doctrine, doctrineX, doctrineY, 260, 390, null);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 20));
		g2.drawString("Doctrine", 6080 - gp.getWorldX(), 705 - gp.getWorldY());// white text that appears on the
																				// doctrine

	}

	/**
	 * Displays the ghost and the text it says inside the doctrine
	 * 
	 * @param g2
	 * @param gp
	 * @throws IOException
	 */
	public void insideDoctrine(Graphics2D g2, GamePanel gp) throws IOException {
		if (m.getCurrentMap() == 4) {
			ghost(g2, 1110, 120, 125, 98, gp);
			if (gp.getWorldX() >= 715 && gp.getWorldX() <= 865 && gp.getWorldY() >= -25 && gp.getWorldY() <= 100) {
				if (!once) {
					npc.setTextIndex(0);
					doingDoctrineGhost = true;
					once = true;
				}
				npc.text(g2, 4);
				p.disableCharacterMovement();
			}
		}
	}

	/**
	 * @see Swing - Displaying GIF Animation in Java - Stack Overflow,
	 *      {@link https://www.stackoverflow.com/questions/12566311/displaying-gif-animation-in-java}.
	 * 
	 * @param g2
	 * @param observer
	 * @throws IOException
	 */
	public void book(Graphics2D g2, Component observer) throws IOException {
		// Stackoverflow used for to understand how to use GIFs in Java swing (see link
		// above)
		g2.setFont(new Font("calibri", Font.BOLD, 18));
		if (enterBook) {// opened the book
			if (!playGif || staticImageBook) {// gif is not playing or its done
				g2.drawImage(book, -70, 0, 900, 587, null);// static book image
			} else {
				g2.drawImage(pageFlipping.getImage(), -70, 0, 900, 587, observer);// play the gif
			}
			if (!hoveringNextPage) {// not hovering over the next page button
				g2.setColor(unselected);
				g2.fillRoundRect(530, 445, 150, 40, 10, 10);
			} else {// hovering over the next page button
				g2.setColor(selected);
				g2.fillRoundRect(530, 445, 150, 40, 10, 10);
			}
			if (!hoveringExitPage) {// not hovering over the close book button
				g2.setColor(unselected);
				g2.fillRoundRect(530, 100, 150, 40, 10, 10);
			} else {// hovering over the close book button
				g2.setColor(selected);
				g2.fillRoundRect(530, 100, 150, 40, 10, 10);
			}
			g2.setColor(Color.WHITE);
			g2.drawString("Close Book", 562, 125);
			g2.drawString("Next Page", 565, 470);
			g2.setColor(Color.BLACK);

			/**
			 * @see United States Conference Of Catholic Bishops. (2025). Catechism of the
			 *      Catholic Church. USCCB.
			 *      {@link https://www.usccb.org/beliefs-and-teachings/what-we-believe/catechism/catechism-of-the-catholic-church?p=29-chapter12.xhtml%23para1673}
			 *      and https://www.vatican.va/archive/cod-iuris-canonici/cic_index_en.ht
			 **/
			if (nextPage == 0) {// first page in the book
				g2.setFont(header);

				g2.drawString("Exorcisms", 145, 200);
				g2.setFont(new Font("Calibri", Font.PLAIN, 22));
				g2.drawString("By: Noah Sussman, Rudra Garg", 85, 270);
				g2.drawString("and Akhilan Saravanan", 120, 320);
				g2.setFont(normalText);
			} else if (nextPage == 1) {// second page in the book
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
			} else if (nextPage == 2) {// third page in the book
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

			} else if (nextPage == 3) {// fourth page in the book
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
			} else if (nextPage == 4) {// fifth page in the book
				g2.setFont(header);
				g2.drawString("Advanced", 140, 270);
				g2.drawString("Exorcisms", 135, 320);

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
			} else if (nextPage == 5) {// sixth page in the book
				g2.setFont(subHeading);
				g2.drawString("Multiple Ghost Example", 90, 140);
				g2.fillRect(90, 142, 270, 3);

				g2.drawImage(leftGhostExample, 100, 165, 235, 132, null);
				g2.setFont(normalText);
				g2.drawString("Left Ghost Exorcised", 135, 316);
				g2.drawString("Right Ghost Exorcised", 135, 470);
				g2.drawImage(rightGhostExample, 100, 320, 235, 132, null);
				g2.setFont(subHeading);
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

			} else if (nextPage == 6) {// seventh page in the book
				g2.setFont(subHeading);
				g2.drawString("Multishape Ghosts", 115, 140);
				g2.fillRect(115, 142, 215, 3);
				g2.drawImage(multiShapeGhost1, 105, 150, 235, 132, null);
				g2.setFont(normalText);
				g2.drawString("Multishape Ghosts", 155, 300);
				g2.drawImage(verticalMultishape, 105, 310, 235, 132, null);
				g2.drawString("Completing the first shape on the left", 90, 460);
				g2.drawImage(circleMultishape, 425, 170, 235, 132, null);
				g2.drawString("Completing the right shape of the ghost", 395, 325);

			} else if (nextPage == 7) {// eigth page in the book

				g2.setFont(subHeading);
				g2.drawString("Order of Shapes", 125, 140);
				g2.fillRect(125, 142, 185, 3);
				g2.setFont(normalText);
				g2.drawString("If you are ever confused, the top left", 90, 170);
				g2.drawString("includes a list of shapes that need to", 90, 195);
				g2.drawString("be replicated in proper order.", 90, 220);
				g2.drawImage(orderFullFrame, 100, 235, 235, 142, null);
				g2.drawString("In the example above, the triangle", 90, 425);
				g2.drawString("must be drawn first then the circle.", 90, 450);
				g2.drawString("Top Left has correct order", 120, 396);
				g2.drawImage(orderCloseUp, 425, 150, 235, 112, null);
				g2.drawString("Close Up of Order", 475, 280);
				g2.drawImage(triangleRemovedOrder, 425, 290, 235, 132, null);
				g2.drawString("Left Ghost Exorcised (new order)", 420, 440);
			} else if (nextPage == 8) {// ninth page in the book
				g2.setFont(header);
				g2.drawString("THE END", 140, 290);
			}
		}
	}

	/**
	 * Car graphics
	 * 
	 * @param g
	 * @param gp
	 * @throws IOException
	 */
	public void car(Graphics g, GamePanel gp) throws IOException {
		int carX = carWorldX - gp.getWorldX();// car x position
		int carY = carWorldY - gp.getWorldY();// car y position
		g.drawImage(car, carX, carY, 96, 192, null);// image of car
		if (!carOn && !carUsed && gp.getWorldX() >= 834 && gp.getWorldX() <= 880 && gp.getWorldY() >= 563
				&& gp.getWorldY() <= 638 && p.getKeyH().iscPressed()) {// user gets in car
			carOn = true;// in car
			p.disableCharacterMovement();// disable character movement
			visible = false;// remove image of character
			animationFrame = 0;// begin scene at frame 0
		}
	}

	/**
	 * Scene where the title of the game appears in a fading animation
	 * 
	 * @param g2
	 */
	public void titleScreen(Graphics2D g2) {
		if (carOn) {// in the car
			if (reset) {// resets the fading for the next text
				transparency = 0;
				hasFaded = false;
				reset = false;
			}

			if (!hasFaded) {// hasn't faded yet
				transparency += 1;// fade
				if (transparency >= 250) {
					transparency = 255;
					hasFaded = true;// ended fading the text
				}
			} else {// has completed fading
				transparency -= 1;
				if (transparency <= 0) {
					transparency = 0;
					reset = true;// reset the fading for the next text
				}
			}
		}
		Color title = new Color(255, 255, 255, transparency);// colour of the text that changes
		g2.setColor(title);
		g2.setFont(new Font("calibri", Font.BOLD, 60));
		if (carWorldX < 3000 && carWorldX < 3500) {// displays this text between these coordinates
			g2.drawString("Are We Cooked Interactive", 60, 250);
			g2.drawString("Presents...", 280, 400);
		}
		if (carWorldX >= 3500 && carWorldX <= 4600) {// displays this text between these coordinates
			g2.drawString("Whispers Of", 220, 250);
			g2.drawString("The Deceived", 210, 400);
		}
	}

	/**
	 * Graphics for the instructions menu
	 * 
	 * @param g2
	 */
	public void instructions(Graphics2D g2) {
		Font calibri = new Font("Calibri", Font.BOLD, 18);
		g2.setFont(calibri);
		if (hoveringInstructions) {// hovering over the instructions button
			g2.setColor(selected);
			g2.fillRoundRect(instructionsX, instructionsY, 120, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString("Instructions", 655, 35);

		} else {// not hovering over the instructions button
			g2.setColor(unselected);
			g2.fillRoundRect(instructionsX, instructionsY, 120, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.drawString("Instructions", 655, 35);
		}
		if (instructionsPrompt) {// in instructions menu
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
			if (hoveringMovement) {// hovering over the movement button
				g2.setColor(selected);
				g2.fillRoundRect(305, 160, 195, 62, 20, 20);
			} else if (!hoveringMovement) {// not hovering over the movement button
				g2.setColor(unselected);
				g2.fillRoundRect(305, 160, 195, 62, 20, 20);
			}
			if (hoveringObjective) {// hovering over the objective button
				g2.setColor(selected);
				g2.fillRoundRect(305, 250, 195, 62, 20, 20);
			} else if (!hoveringObjective) {// not hovering over the objective button
				g2.setColor(unselected);
				g2.fillRoundRect(305, 250, 195, 62, 20, 20);
			}

			g2.setFont(new Font("Calibri", Font.BOLD, 30));
			g2.setColor(Color.WHITE);
			g2.drawString("Movement", 338, 200);
			g2.drawString("Objective", 345, 290);
		}
	}

	/**
	 * This method is responsible for the back button that appears in the
	 * instructions menu
	 * 
	 * @param g2
	 */
	public void backMenu(Graphics2D g2) {
		if (!hoveringBack) {// not hovering over the back button
			g2.setColor(unselected);
			g2.fillRoundRect(335, 430, 130, 45, 10, 10);
			g2.setFont(new Font("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 357, 460);
		} else {// hovering over the back button
			g2.setColor(selected);
			g2.fillRoundRect(335, 430, 130, 45, 10, 10);
			g2.setFont(new Font("Calibri", Font.BOLD, 25));
			g2.setColor(Color.WHITE);
			g2.drawString("Go Back", 357, 460);
		}
		if (backPressed) {// pressed the back button
			if (movementPrompt) {// in the movement menu
				movementPrompt = false;// no longer in the movement menu
				instructionsPrompt = true;// go back to the instructions menu
			} else if (objectivePrompts) {// in objective menu
				objectivePrompts = false;// no longer in the objective menu
				instructionsPrompt = true;// go back to the objective menu
			} else if (instructionsPrompt) {// in instructions menu
				instructionsPrompt = false;// no longer in instructions menu
				input.setInstructionsPressed(false);// reset the instructions pressed
			}
			backPressed = false;// reset the back button pressed
		}
	}

	/**
	 * This method creates the graphics for the prompts in the instructions menu
	 * 
	 * @param g2
	 */
	public void prompts(Graphics2D g2) {
		if (movementPrompt) {// in the movement menu
			instructionsPrompt = false;// no longer in the instructions menu
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.drawImage(wasdKey, 275, 146 + 20, 250, 250, null);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Movement", 307, 112 + 10);
			g2.fillRect(300, 115 + 10, 195, 2);
			g2.drawString("Left", 190, 305 + 20);
			g2.drawString("Right", 520, 305 + 20);
			g2.drawString("Up", 375, 178 + 20);
			g2.drawString("Down", 341, 368 + 20);
		}
		if (objectivePrompts) {// in the objective menu
			instructionsPrompt = false;// no longer in the instructions menu

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
			if (m.getCurrentMap() == 3 && !carUsed && !m.isDoneNightmare()) {// just started the game (hasn't been in
																				// house before)
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(door, 300, 50 + 80, 200, 200, null);
				g2.drawString("Go in the house", 270, 330);
			} else if (m.getCurrentMap() == 2 && !m.isDoneNightmare()) {// in the house
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(bed, 360, 200, 75, 75, null);
				g2.drawString("Go to sleep", 300, 330);
			} else if (m.getCurrentMap() == 2 && m.isDoneNightmare() && !m.isLookInMirror()) {// finished the nightmare
																								// in the house
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(door, 300, 50 + 80, 200, 200, null);
				g2.drawString("Leave the house", 270, 330);
			} else if (m.getCurrentMap() == 3 && m.isDoneNightmare() && !carUsed) {// outside of the house and hasn't
																					// gone in the car yet
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(car, 350, 160, 96, 192, null);
				g2.drawString("Get in the car", 280, 330);
			} else if (m.getCurrentMap() == 3 && carUsed && !input.isReadBook()) {// has finished the car scene but
																					// hasn't gone to the graveyard yet
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(exorcism, 280, 160, 240, 160, null);
				g2.drawString("Open the book in the graveyard", 120, 360);
				g2.drawString("Press B to do so", 250, 400);
			} else if (m.getCurrentMap() == 3 && carUsed && input.isReadBook()) {// has read the graveyard book but
																					// hasn't gone into the doctrine yet
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(doctrine, 350, 160, 96, 192, null);
				g2.drawString("Go into the doctrine", 220, 400);
			} else if (m.getCurrentMap() == 4 && !doneDoctrineGhost) {// hasn't talked to the doctrine ghost yet
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(ghost, 300, 160, 200, 192, null);
				g2.drawString("Go up to the ghost", 230, 400);
			} else if (m.getCurrentMap() == 4 && doneDoctrineGhost) {// talked to the doctrine ghost, but hasn't gone in
																		// the exorcism room
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(door, 300, 50 + 80, 200, 200, null);
				g2.drawString("Go through the door", 220, 360);
			} else if (m.getCurrentMap() == 5) {// in the exorcism room
				g2.setFont(new Font("Monospaced", Font.BOLD, 30));
				g2.drawImage(ghost, 300, 160, 200, 192, null);
				g2.drawString("Exorcise the ghosts", 230, 400);
			} else {
				g2.drawString("None", 350, 300);
			}
		}
	}

	/**
	 * This method is responsible for the animation of the car scene in the
	 * beginning of the game
	 * 
	 * @param gp
	 */
	public void animation(GamePanel gp) {
		if (carOn) {// user in car
			carWorldX += 3;// go to the right
			gp.setWorldX(gp.getWorldX() + 3);
			animationFrame++;

			if (carWorldX >= 4700) {// reached the graveyard area
				carWorldX = 4700;
				carOn = false;// stop the car
				carUsed = true;// used the car
				car = brokenCar;// change the perfect condition car to the broken car
				j.setJumpscare(true);// start the jumpscare
				visible = true;// charcater is now visible again
				p.disableCharacterMovement();// enable character movement
			}
		}
	}

	/**
	 * This is the method responsible for all the graphics of the ghost in the game
	 * 
	 * @param g2
	 * @param ghostGraveYardX
	 * @param ghostGraveYardY
	 * @param width
	 * @param height
	 * @param gp
	 * @throws IOException
	 * @see Pixel Art Animated Film Ghost, Ghost, Angle, Text PNG | Pngegg,
	 *      {@link https://www.pngegg.com/en/png-mrsui}.
	 */
	public void ghost(Graphics2D g2, int ghostGraveYardX, int ghostGraveYardY, int width, int height, GamePanel gp)
			throws IOException {

		int ghostX = ghostGraveYardX - gp.getWorldX();// x coordinate of the ghost
		int ghostY = ghostGraveYardY - gp.getWorldY();// y coordinate of the ghost

		ghost = ImageIO.read(new File("src/textures/ghost.png"));// image of ghost
		g2.drawImage(ghost, ghostX, ghostY, width, height, null);// draws the ghost

	}

	/**
	 * This method is responsible for the appearing the ghosts in every level in the
	 * exorcism room
	 * 
	 * @param g2
	 * @param offsetY
	 * @param gp
	 * @throws IOException
	 */
	public void drawGhost(Graphics2D g2, int offsetY, GamePanel gp) throws IOException {
		if (level == 1) {
			if (!destroyCircle) {// hasn't exorcised the circle ghost
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Circle", 250, 196, gp);// circle ghost appears
			}
			if (!destroyTriangle) {// hasn't exorcised the triangle ghost
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Triangle", 250, 196, gp);// triangle ghost appears
			}

		} else if (level == 2) {
			if (!destroyHorizontal) {// hasn't exorcised the horizontal ghost
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Horizontal", 250, 196, gp);// horizontal ghost appears
			}

			if (!destroyZigzag) {// hasn't exorcised the zigzag ghost
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Zigzag", 250, 196, gp);// zigzag ghost appears
			}

		} else if (level == 3) {
			if (!destroyVertical) {// hasn't exorcised the vertical ghost
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Vertical", 250, 196, gp);// vertical ghost appears
			}
			if (!destroyHorizontal) {// hasn't exorcised the horizontal ghost
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Horizontal", 250, 196, gp);// horizontal ghost appears
			}

		} else if (level == 4) {
			if (!destroyHorizontal) {// hasn't exorcised the horizontal ghost
				minigameGhost(g2, 1200 - 20, 820 + 50 + offsetY, "Horizontal", 250, 196, gp);// horizontal ghost appears
			}
			if (!destroyTriangle) {// hasn't exorcised the triangle ghost
				minigameGhost(g2, 1200 - 210, 820 - 0 + offsetY, "Triangle", 250, 196, gp);// triangle ghost appears
			}

		} else if (level == 5) {
			minigameGhost(g2, 1200 - 20, 820 - 0 + offsetY, "duoghost1", 250, 196, gp);// the right ghost appears
			if (!destroyLeftGhost) {// hasn't exorcised both shapes of the ghost
				minigameGhost(g2, 1200 - 210, 820 + 50 + offsetY, "duoghost2", 250, 196, gp);// the left ghost appears
			}
		} else if (level == 6) {
			minigameGhost(g2, 1200 - 20, 820 - 0 + offsetY, "duoghost3", 250, 196, gp);// the right ghost appears
			if (!destroyLeftGhost) {// hasn't exorcised both shapes of the ghost
				minigameGhost(g2, 1200 - 210, 820 + 50 + offsetY, "duoghost4", 250, 196, gp);// the left ghost appears
			}
		} else if (level == 7) {
			minigameGhost(g2, 1100 - 20, 820 - 0 + offsetY, "trioghost", 250, 196, gp);// the three shape ghost appears
		} else if (level == 8) {
			npc.setTextIndex(0);// reset text index for the text the ghost says
			minigameGhost(g2, 1100 - 20, 820 - 0 + offsetY, "bossghost", 250, 196, gp);// the five shape ghost appears
		}
	}

	/**
	 * This method is responsible for the movement of the ghosts (going up and down)
	 * and going to the next level once the current level is fully exorcised
	 * 
	 * @param g2
	 * @param gp
	 * @throws IOException
	 */
	public void ghostLogic(Graphics2D g2, GamePanel gp) throws IOException {
		// Hovering ghost
		// Y values of the ghosts
		if (!reachedPeak) {// hasn't gone upwards to its peak height
			if (yVal <= 40) {// less than the limit it can go up
				yVal++;// keep going up
			}
			if (yVal == 40) {// has gone as far as it should go
				reachedPeak = true;// has reached its peak
			}
		}
		if (reachedPeak) {// has reached its peak height
			if (yVal >= -40) {// while its less than the minimum height
				yVal--;// go back down
			}
			if (yVal == -40) {// has reached its minimum height
				reachedPeak = false;// go back up
			}
		}

		if (ghostCount == 2 && !levelShape.equals("duoghost1") && !levelShape.equals("duoghost2")
				&& !levelShape.equals("duoghost3") && !levelShape.equals("duoghost4") && !levelShape.equals("trioghost")
				&& !levelShape.equals("bossghost")) {// exorcising the single shape ghosts
			level++;// increase the level
			ghostCount = 0;// reset the ghost count
			// Reset all the shape detection
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
		}

		if (ghostCount == 4 && (levelShape.equals("duoghost1") || levelShape.equals("duoghost2")
				|| levelShape.equals("duoghost3") || levelShape.equals("duoghost4"))) {// exorcising the duo shape
																						// ghosts

			level++;// increase the level
			ghostCount = 0;// reset the ghost count
			// Reset all the shape detection
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
			destroyLeftGhost = false;
			destroyRightGhost = false;

		}

		if (ghostCount == 3 && destroyTrioGhost) {// exorcising the trio shape ghosts

			level++;// increase the level
			ghostCount = 0;// reset the ghost count
			// Reset all the shape detection
			destroyCircle = false;
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
			destroyTrioGhost = false;

		}
		if (ghostCount == 5 && destroyBossGhost) {// exorcising the boss ghost (five shapes)
			level++;// increase the level
			destroyCircle = false;// Reset all the shape detection
			destroyTriangle = false;
			destroyZigzag = false;
			destroyHorizontal = false;
			destroyVertical = false;
			destroyBossGhost = false;
		}

		if (level == 9) {// the level when the human transformation occurs
			g2.drawImage(human, 350, 230, 192, 280, null);
			npc.text(g2, 8);// jeff and human conversation
		}

		drawGhost(g2, yVal, gp);// draws the ghosts in the exorcism room
	}

	/**
	 * This method is responsible for creating the shapes that appear above the
	 * exorcism ghosts and also the top right order menu
	 * 
	 * @param shape
	 * @param ghostX
	 * @param ghostY
	 * @param g2
	 * @param offsetX
	 */
	public void randomShape(String shape, int ghostX, int ghostY, Graphics2D g2, int offsetX) {
		if (shape.equalsIgnoreCase("Triangle")) {// ghost should have a triangle above its head
			// draw the triangle
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			int[] xTriangle = { ghostX - 260 + offsetX, ghostX - 230 + offsetX, ghostX - 290 + offsetX };
			int[] yTriangle = { ghostY - 350, ghostY - 290, ghostY - 290 };
			g2.drawPolygon(xTriangle, yTriangle, 3);

			if (level == 1 || level == 4 || level == 6 || level == 7) {// draws triangle in the top left order as the
																		// first one
				// Frame triangle
				int[] xFrame = { 100, 85, 115 };
				int[] yFrame = { 20, 50, 50 };
				g2.drawPolygon(xFrame, yFrame, 3);
			} else if (level == 8) {
				int[] xFrame = { 240, 232, 247 };
				int[] yFrame = { 27, 42, 42 };
				g2.drawPolygon(xFrame, yFrame, 3);
			}
		} else if (shape.equalsIgnoreCase("Circle")) {// ghost should have a circle above its head
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			g2.drawOval(ghostX - 290 + offsetX, ghostY - 350, 60, 60);// oval

			if (level == 1) {
				// Frame Circle
				g2.drawOval(130, 17, 35, 35);// top left order circle
			} else if (level == 5) {
				g2.drawOval(110, 17, 35, 35);
			} else if (level == 6) {
				g2.drawOval(230, 17, 35, 35);
			} else if (level == 7) {
				g2.drawOval(200, 17, 35, 35);
			} else if (level == 8) {
				g2.drawOval(170, 20, 25, 25);
			}
		} else if (shape.equalsIgnoreCase("Zigzag")) {// zigzag ghost
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.RED);
			// Draw the zigzag shape
			int[] xZigzag = { ghostX - 150 - 140 + offsetX, ghostX - 100 - 140 + offsetX, ghostX - 150 - 140 + offsetX,
					ghostX - 100 - 140 + offsetX };
			int[] yZigzag = { ghostY - 195 - 140, ghostY - 195 - 140, ghostY - 145 - 140, ghostY - 145 - 140 };
			g2.drawPolyline(xZigzag, yZigzag, 4);

			if (level == 2) {
				// Zigzag top left order
				// Frame Zigzag
				int[] xFrame = { 7 + 125, -30 + 125, 7 + 125, -30 + 125 };
				int[] yFrame = { 41 + 15, 41 + 15, 3 + 15, 3 + 15 };
				g2.drawPolyline(xFrame, yFrame, 4);
			} else if (level == 5) {
				int[] xFrame = { 7 + 125 + 120, -30 + 125 + 120, 7 + 125 + 120, -30 + 125 + 120 };
				int[] yFrame = { 41 + 15, 41 + 15, 3 + 15, 3 + 15 };
				g2.drawPolyline(xFrame, yFrame, 4);
			} else if (level == 6 || level == 7) {
				int[] xFrame = { 7 + 125 + 40, -30 + 125 + 40, 7 + 125 + 40, -30 + 125 + 40 };
				int[] yFrame = { 41 + 15, 41 + 15, 3 + 15, 3 + 15 };
				g2.drawPolyline(xFrame, yFrame, 4);
			} else if (level == 8) {
				int[] xFrame = { 156, 137, 156, 137 };
				int[] yFrame = { 43, 43, 24, 24 };
				g2.drawPolyline(xFrame, yFrame, 4);
			}

		} else if (shape.equalsIgnoreCase("Vertical")) {// vertical ghost
			// Draw vertical line above ghost head
			g2.setColor(Color.RED);
			g2.fillRect(ghostX - 260 + offsetX, ghostY - 355, 4, 60);

			if (level == 3) {
				// Vertical line top left order
				// Frame Vertical
				g2.fillRect(180, 10, 4, 50);
			} else if (level == 5) {
				g2.fillRect(80, 15, 4, 40);
			} else if (level == 8) {
				g2.fillRect(210, 19, 4, 30);
			}
		} else if (shape.equalsIgnoreCase("Horizontal")) {// horizontal line ghost
			// Draw horizontal line above ghost head
			g2.setColor(Color.RED);
			g2.fillRect(ghostX - 290 + offsetX, ghostY - 313, 60, 4);

			// Frame Horizontal
			if (level == 2 || level == 4) {// top left order horizontal line
				g2.fillRect(160, 32, 50, 4);
			} else if (level == 3) {
				g2.fillRect(90, 32, 50, 4);
			} else if (level == 5) {
				g2.fillRect(160, 32, 40, 4);
			} else if (level == 6) {
				g2.fillRect(180, 32, 40, 4);
			} else if (level == 8) {
				g2.fillRect(80, 32, 40, 4);
			}

		} else if (shape.equalsIgnoreCase("duoghost1")) {// first two shape ghost
			levelShape = shape;// store shape in global variable for other methods
			if (!destroyHorizontal) {// hasn't destroyed the horizontal line yet
				ghostShape = "Horizontal";// still show the horizontal shape
			} else if (destroyHorizontal && !destroyZigzag) {// has destroyed the first shape (horizontal line)
				ghostShape = "Zigzag";// horizontal line dissappears
			}
			if (!addedShapes && !destroyHorizontal) {// stores current active shapes in arrayList
				shapesArray.add("Horizontal");
			}
			if (!addedShapes && !destroyZigzag) {// stores current active shapes in arrayList
				shapesArray.add("Zigzag");
			}

			for (int i = 0; i < shapesArray.size(); i++) {// draws the shapes on top of the ghosts heads
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyHorizontal && shapesArray.contains("Horizontal")) {// horizontal line detected
				shapesArray.remove("Horizontal");// no longer show the horizontal line above the ghosts head
				addedShapes = true;
			}

			shapesArray.clear();
			if (!destroyRightGhost) {// hasn't destroyed the first ghost
				if (isDestroyHorizontal() && isDestroyZigzag()) {// exorcised both shapes of the singular ghost
					destroyRightGhost = true;// make the ghost dissappear
				}
			}
		} else if (shape.equalsIgnoreCase("duoghost2")) {// second two shape ghost
			levelShape = shape;// store shape in global variable for other methods
			if (!destroyVertical) {// hasn't destroyed the vertical line yet
				ghostShape = "Vertical";// still show the vertical shape
			} else if (destroyVertical && !destroyCircle) {// has destroyed the first shape (vertical line)
				ghostShape = "Circle";// horizontal line dissappears
			}
			if (!addedShapes && !destroyVertical) {// stores current active shapes in arrayList
				shapesArray.add("Vertical");
			}
			if (!addedShapes && !destroyCircle) {// stores current active shapes in arrayList
				shapesArray.add("Circle");
			}

			for (int i = 0; i < shapesArray.size(); i++) {// draws the shapes on top of the ghosts heads
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyVertical && shapesArray.contains("Vertical")) {// vertical line detected
				shapesArray.remove("Vertical");// no longer show the vertical line above the ghosts head
				addedShapes = true;
			}
			shapesArray.clear();
			if (!destroyLeftGhost) {// hasn't destroyed the second ghost
				if (isDestroyVertical() && isDestroyCircle()) {// exorcised both shapes of the singular ghost
					destroyLeftGhost = true;// make the ghost dissappear
				}
			}
		} else if (shape.equalsIgnoreCase("duoghost3")) {// third two shape ghost
			levelShape = shape;// store shape in global variable for other methods
			if (!destroyHorizontal) {// hasn't destroyed the horizontal line yet
				ghostShape = "Horizontal";// still show the horizontal shape
			} else if (destroyHorizontal && !destroyCircle) {// has destroyed the first shape (horizontal line)
				ghostShape = "Circle";// horizontal line dissappears
			}
			if (!addedShapes && !destroyHorizontal) {// stores current active shapes in arrayList
				shapesArray.add("Horizontal");
			}
			if (!addedShapes && !destroyCircle) {// stores current active shapes in arrayList
				shapesArray.add("Circle");
			}

			for (int i = 0; i < shapesArray.size(); i++) {// draws the shapes on top of the ghosts heads
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyHorizontal && shapesArray.contains("Horizontal")) {// horizontal line detected
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
			levelShape = shape;// store shape in global variable for other methods
			if (!destroyTriangle) {// hasn't destroyed the triangle yet
				ghostShape = "Triangle";// still show the triangle shape
			} else if (destroyTriangle && !destroyZigzag) {// has destroyed the first shape (triangle line)
				ghostShape = "Zigzag";
			}
			if (!addedShapes && !destroyTriangle) {// stores current active shapes in arrayList
				shapesArray.add("Triangle");
			}
			if (!addedShapes && !destroyZigzag) {// stores current active shapes in arrayList
				shapesArray.add("Zigzag");
			}

			for (int i = 0; i < shapesArray.size(); i++) {// draws the shapes on top of the ghosts heads
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyHorizontal && shapesArray.contains("Triangle")) {// triangle detected
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
			levelShape = shape;// store shape in global variable for other methods
			if (!destroyTriangle) {// hasn't destroyed the triangle yet
				ghostShape = "Triangle";// still show the triangle shape
			} else if (destroyTriangle && !destroyZigzag) {// has destroyed the first shape (triangle line)
				ghostShape = "Zigzag";
			} else if (destroyTriangle && destroyZigzag && !destroyCircle) {
				ghostShape = "Circle";
			}
			if (!destroyTriangle) {
				shapesArray.add("Triangle");// stores current active shapes in arrayList
			}
			if (!destroyZigzag) {
				shapesArray.add("Zigzag");// stores current active shapes in arrayList
			}
			if (!destroyCircle) {
				shapesArray.add("Circle");// stores current active shapes in arrayList
			}

			for (int i = 0; i < shapesArray.size(); i++) {// draws the shapes on top of the ghosts heads
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyTriangle && shapesArray.contains("Zigzag")) {// zigzag detected
				shapesArray.remove("Zigzag");
			}
			if (destroyZigzag && shapesArray.contains("Circle")) {// circle detected
				shapesArray.remove("Circle");
				destroyTrioGhost = true;
			}
			shapesArray.clear();
		} else if (shape.equalsIgnoreCase("bossghost")) {
			levelShape = shape;// store shape in global variable for other methods
			if (!destroyHorizontal) {// hasn't destroyed the horizontal line yet
				ghostShape = "Horizontal";// still show the horizontal shape
			} else if (destroyHorizontal && !destroyZigzag) {// has destroyed the first shape (horizontal line)
				ghostShape = "Zigzag";
			} else if (destroyHorizontal && destroyZigzag && !destroyCircle) {
				ghostShape = "Circle";
			} else if (destroyHorizontal && destroyZigzag && destroyCircle && !destroyVertical) {
				ghostShape = "Vertical";
			} else if (destroyHorizontal && destroyZigzag && destroyCircle && destroyVertical && !destroyTriangle) {
				ghostShape = "Triangle";
			}
			if (!destroyHorizontal) {// stores current active shapes in arrayList
				shapesArray.add("Horizontal");
			}
			if (!destroyZigzag) {// stores current active shapes in arrayList
				shapesArray.add("Zigzag");
			}
			if (!destroyCircle) {// stores current active shapes in arrayList
				shapesArray.add("Circle");
			}
			if (!destroyVertical) {// stores current active shapes in arrayList
				shapesArray.add("Vertical");
			}
			if (!destroyTriangle) {// stores current active shapes in arrayList
				shapesArray.add("Triangle");
				destroyBossGhost = true;
			}

			for (int i = 0; i < shapesArray.size(); i++) {// draws the shapes on top of the ghosts heads
				randomShape(shapesArray.get(i), ghostX, ghostY, g2, offsetX - 40 + (80 * i));
			}

			if (destroyTriangle && shapesArray.contains("Zigzag")) {// zigzag detected
				shapesArray.remove("Zigzag");
			}
			if (destroyZigzag && shapesArray.contains("Circle")) {// circle detected
				shapesArray.remove("Circle");
			}
			if (destroyZigzag && shapesArray.contains("Vertical")) {// vertical line detected
				shapesArray.remove("Vertical");
			}
			if (destroyZigzag && shapesArray.contains("Triangle")) {// triangle detected
				shapesArray.remove("Triangle");
			}

			shapesArray.clear();
		}

	}

	/**
	 * This method draws the ghosts in the exorcism room and puts their respective
	 * shapes above their heads
	 * 
	 * @param g2
	 * @param ghotsX
	 * @param ghotsY
	 * @param shape
	 * @param width
	 * @param height
	 * @param gp
	 * @throws IOException
	 */
	public void minigameGhost(Graphics2D g2, int ghotsX, int ghotsY, String shape, int width, int height, GamePanel gp)
			throws IOException {
		int ghostX = ghotsX - worldX;// ghost x position
		int ghostY = ghotsY - worldY;// ghost y position
		ghostShape = shape;// updates the shape that needs to be exorcised
		ghost = ImageIO.read(new File("src/textures/minigameghost.png"));
		randomShape(ghostShape, ghostX, ghostY, g2, 0);// draw the shape above the ghosts head
		ghost(g2, ghostX, ghostY, width, height, gp);// draw the ghost
	}

	/**
	 * This method is responsible for the graveyard ghost and the text it says
	 * 
	 * @param g2
	 * @param gp
	 * @throws IOException
	 */
	public void graveyard(Graphics2D g2, GamePanel gp) throws IOException {

		if (inGraveYard) {// inside the graveyard
			g2.drawImage(ghost, 480, 280, 250, 196, null);// image of ghost
			npc.text(g2, 2);// text he says
		}
	}

	/**
	 * This method is the graphics for the confirmation menu
	 * 
	 * @param g2
	 * @param text1
	 * @param text2
	 * @param textX1
	 * @param textX2
	 */
	public void confirmation(Graphics2D g2, String text1, String text2, int textX1, int textX2) {

		if (!inConfirmation) {// exit confirmation menu
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

		if (hoveringYes) {// hovering over the yes button
			// Yes box
			g2.setColor(selected);
			g2.fillRoundRect(225, 355, 130, 45, 10, 10);
		} else {// not hovering over the yes button
			// Yes box
			g2.setColor(unselected);
			g2.fillRoundRect(225, 355, 130, 45, 10, 10);
		}
		if (hoveringNo) {// hovering over the no button
			// No box
			g2.setColor(selected);
			g2.fillRoundRect(425, 355, 130, 45, 10, 10);
		} else {// not hovering over the no button
			// No box
			g2.setColor(unselected);
			g2.fillRoundRect(425, 355, 130, 45, 10, 10);
		}
		g2.setColor(Color.WHITE);
		g2.drawString("Yes", 270, 385);
		g2.drawString("No", 472, 385);
	}

	/**
	 * This method is for the graphics of the exit menu
	 * 
	 * @param g2
	 */
	public void exitMenuOption(Graphics2D g2) {
		if (hoveringX) {// hovering over the x button in the top right
			g2.setColor(selected);

		} else {// not hovering over the x button in the top right
			g2.setColor(unselected);
		}
		g2.fillRoundRect(595 + 90, 100 - 40, 30, 30, 10, 10);
		g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.WHITE);
		g2.drawLine(604 + 90 - 5, 105 - 40, 620 + 90, 120 - 40 + 5);
		g2.drawLine(620 + 90, 105 - 40, 604 + 90 - 5, 120 - 40 + 5);
	}

	/**
	 * This method is for the graphics of the help menu
	 * 
	 * @param g2
	 */
	public void help(Graphics2D g2) {
		if (helpPressed) {// help button has been pressed
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
			g2.drawString("Ensure that your audio is enabled before you begin the game.", 130, 175 + 10);
			g2.drawString("It is a vital component to the horror aspect of the game.", 150, 205 + 10);
			g2.drawString("If you are ever stuck, and don't know what to do, you can use your cursor", 100 - 20,
					260 + 23);
			g2.drawString("to hover and click on the instructions menu in the top right. You can then", 100 - 20,
					290 + 23);
			g2.drawString("navigate through the 2 different topics to find necessary information.", 100 - 5, 320 + 23);
			g2.drawString("For example, if you don't know what the keybinds are to move, go to", 100 - 5, 350 + 23);
			g2.drawString("the movement section. Otherwise go to objective to see what to do.", 100, 380 + 20);

			g2.drawString("When you enter the house, after you've slept in the bed, you should", 100, 435 + 20);
			g2.drawString("go back to the entrance again to go back outside of the house.", 120, 465 + 20);

			// Subtitles
			g2.setFont(new Font("Calibri", Font.BOLD, 22));
			g2.drawString("Guide House", 335, 425);
			g2.fillRect(335, 427, 117, 2);
			g2.drawString("Stuck", 370, 253);
			g2.fillRect(370, 255, 52, 2);
			g2.drawString("Audio", 370, 155);
			g2.fillRect(370, 157, 54, 2);
			exitMenuOption(g2);// x button in the top right
		}
	}

	/**
	 * This method is the graphics for the credit menu
	 * 
	 * @param g2
	 */
	public void credits(Graphics2D g2) {
		if (creditsPressed) {// the credits button has been pressed
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.DARK_GRAY);
			g2.fillRoundRect(50, 50, 225 * 3, 155 * 3, 10, 10);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRoundRect(60, 60, 218 * 3, 148 * 3, 10, 10);
			g2.setFont(new Font("Monospaced", Font.BOLD, 40));
			g2.setColor(Color.BLACK);
			g2.drawString("Credits", 315, 115);// title
			g2.fillRect(315, 117, 167, 2);
			// Names of people who helped work on this game
			g2.setFont(new Font("Calibri", Font.BOLD, 20));
			g2.drawString("Project Lead", 100 + 100, 175);
			g2.fillRect(100 + 100, 177, 150, 2);
			g2.drawString("Noah Sussman", 100 + 100, 195);
			g2.drawString("Developer", 100 + 100, 230);
			g2.fillRect(100 + 100, 232, 150, 2);
			g2.drawString("Akhilan Saravanan", 100 + 100, 252);
			g2.drawString("Senior Developer", 100 + 100, 280);
			g2.fillRect(100 + 100, 282, 150, 2);
			g2.drawString("Rudra Garg", 100 + 100, 300);
			g2.drawString("UX/UI Designer", 100 + 100, 335);
			g2.fillRect(100 + 100, 337, 150, 2);
			g2.drawString("Rudra Garg", 100 + 100, 355);
			g2.drawString("Playtester", 100 + 100, 385);
			g2.fillRect(100 + 100, 337, 150, 2);
			g2.drawString("Sammy Jiang", 100 + 100, 405);
			g2.drawString("Voice Actors", 300 + 100, 175);
			g2.fillRect(300 + 100, 177, 110, 2);
			g2.drawString("Jeff: Jhonard Ramos", 300 + 100, 195);
			g2.drawString("Graveyard Ghost: Noah Sussman", 300 + 100, 220);
			g2.drawString("Doctor: Rudra Garg", 300 + 100, 245);
			g2.drawString("Doctrine Ghost: Akhilan Saravanan", 300 + 100, 270);
			g2.fillRect(100 + 100, 387, 150, 2);
			exitMenuOption(g2);// x option in the top right
		}
	}

	/**
	 * This method is used for counting how long the book flipping GIF should play
	 * for
	 * 
	 * @see Java Swing Timer - stack overflow. (n.d.-f).
	 *      {@link https://stackoverflow.com/questions/1006611/java-swing-timer/1006640}
	 */
	public void timer() {
		time = new Timer(723, new ActionListener() {// how long in milliseconds the timer will run (723 milliseconds)
			@Override
			public void actionPerformed(ActionEvent e) {
				playGif = false;// not playing the gif
				staticImageBook = true;// use a static book image (not GIF)
				time.stop();// stop the timer
			}
		});
		if (time != null && time.isRunning()) {// once the timer is done
			time.stop();// stop it
		}
		time.setRepeats(false);// don't repeat the timer
		time.start();// begin the timer
	}

	// Getters and Setters

	/**
	 * 
	 * @param input
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 
	 * @param npc
	 */
	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	/**
	 * 
	 * @param p
	 */
	public void setP(Player p) {
		this.p = p;
	}

	/**
	 * 
	 * @param m
	 */
	public void setM(Maps m) {
		this.m = m;
	}

	/**
	 * 
	 * @param j
	 */
	public void setJ(Jumpscare j) {
		this.j = j;
	}

	/**
	 * 
	 * @return worldX
	 */
	public int getWorldX() {
		return worldX;
	}

	/**
	 * 
	 * @return worldY
	 */
	public int getWorldY() {
		return worldY;
	}

	/**
	 * 
	 * @return carOn
	 */
	public boolean isCarOn() {
		return carOn;
	}

	/**
	 * 
	 * @return animationFrame
	 */
	public int getAnimationFrame() {
		return animationFrame;
	}

	/**
	 * 
	 * @return carWorldX
	 */
	public int getCarWorldX() {
		return carWorldX;
	}

	/**
	 * 
	 * @param inHouse
	 * @return inHouse
	 */
	public boolean isInHouse(boolean inHouse) {
		return inHouse;
	}

	/**
	 * 
	 * @return carWorldY
	 */
	public int getCarWorldY() {
		return carWorldY;
	}

	/**
	 * 
	 * @return doctrineWorldX
	 */
	public int getDoctrineWorldX() {
		return doctrineWorldX;
	}

	/**
	 * 
	 * @return doctrineWorldY
	 */
	public int getDoctrineWorldY() {
		return doctrineWorldY;
	}

	/**
	 * 
	 * @return enterBook
	 */
	public boolean isEnterBook() {
		return enterBook;
	}

	/**
	 * 
	 * @return hoveringNextPage
	 */
	public boolean isHoveringNextPage() {
		return hoveringNextPage;
	}

	/**
	 * 
	 * @return hoveringExitPage
	 */
	public boolean isHoveringExitPage() {
		return hoveringExitPage;
	}

	/**
	 * 
	 * @return hoveringInstructions
	 */
	public boolean isHoveringInstructions() {
		return hoveringInstructions;
	}

	/**
	 * 
	 * @return instructionsPrompt
	 */
	public boolean isInstructionsPrompt() {
		return instructionsPrompt;
	}

	/**
	 * 
	 * @return hoveringObjective
	 */
	public boolean isHoveringObjective() {
		return hoveringObjective;
	}

	/**
	 * 
	 * @return hoveringMovement
	 */
	public boolean isHoveringMovement() {
		return hoveringMovement;
	}

	/**
	 * 
	 * @return backPressed
	 */
	public boolean isBackPressed() {
		return backPressed;
	}

	/**
	 * 
	 * @return hoveringBack
	 */
	public boolean isHoveringBack() {
		return hoveringBack;
	}

	/**
	 * 
	 * @return inHouse
	 */
	public boolean isInHouse() {
		return inHouse;
	}

	/**
	 * 
	 * @return inGraveYard
	 */
	public boolean isInGraveYard() {
		return inGraveYard;
	}

	/**
	 * 
	 * @param inGraveYard
	 */
	public void setInGraveYard(boolean inGraveYard) {
		this.inGraveYard = inGraveYard;
	}

	/**
	 * 
	 * @return graveX
	 */
	public int getGraveX() {
		return graveX;
	}

	/**
	 * 
	 * @return graveY
	 */
	public int getGraveY() {
		return graveY;
	}

	/**
	 * 
	 * @return firstTime
	 */
	public boolean isFirstTime() {
		return firstTime;
	}

	/**
	 * 
	 * @return ghostShape
	 */
	public String getGhostShape() {
		return ghostShape;
	}

	/**
	 * 
	 * @return ghostCount
	 */
	public int getGhostCount() {
		return ghostCount;
	}

	/**
	 * 
	 * @return destroyDuoGhost
	 */
	public boolean isDestroyDuoGhost() {
		return destroyDuoGhost;
	}

	/**
	 * 
	 * @return destroyCircle
	 */
	public boolean isDestroyCircle() {
		return destroyCircle;
	}

	/**
	 * 
	 * @return destroyTriangle
	 */
	public boolean isDestroyTriangle() {
		return destroyTriangle;
	}

	/**
	 * 
	 * @return destroyZigzag
	 */
	public boolean isDestroyZigzag() {
		return destroyZigzag;
	}

	/**
	 * 
	 * @return destroyHorizontal
	 */
	public boolean isDestroyHorizontal() {
		return destroyHorizontal;
	}

	/**
	 * 
	 * @return destroyVertical
	 */
	public boolean isDestroyVertical() {
		return destroyVertical;
	}

	/**
	 * 
	 * @return hoveringYes
	 */
	public boolean isHoveringYes() {
		return hoveringYes;
	}

	/**
	 * 
	 * @return hoveringNo
	 */
	public boolean isHoveringNo() {
		return hoveringNo;
	}

	/**
	 * 
	 * @return inConfirmation
	 */
	public boolean isInConfirmation() {
		return inConfirmation;
	}

	/**
	 * 
	 * @return helpPressed
	 */
	public boolean isHelpPressed() {
		return helpPressed;
	}

	/**
	 * 
	 * @return hoveringX
	 */
	public boolean isHoveringX() {
		return hoveringX;
	}

	/**
	 * 
	 * @return creditsPressed
	 */
	public boolean isCreditsPressed() {
		return creditsPressed;

	}

	/**
	 * 
	 * @param hoveringInstructions
	 */
	public void setHoveringInstructions(boolean hoveringInstructions) {
		this.hoveringInstructions = hoveringInstructions;
	}

	/**
	 * 
	 * @param hoveringNextPage
	 */
	public void setHoveringNextPage(boolean hoveringNextPage) {
		this.hoveringNextPage = hoveringNextPage;
	}

	/**
	 * 
	 * @param hoveringExitPage
	 */
	public void setHoveringExitPage(boolean hoveringExitPage) {
		this.hoveringExitPage = hoveringExitPage;
	}

	/**
	 * 
	 * @param instructionsPrompt
	 */
	public void setInstructionsPrompt(boolean instructionsPrompt) {
		this.instructionsPrompt = instructionsPrompt;
	}

	/**
	 * 
	 * @param hoveringObjective
	 */
	public void setHoveringObjective(boolean hoveringObjective) {
		this.hoveringObjective = hoveringObjective;
	}

	/**
	 * 
	 * @param hoveringMovement
	 */
	public void setHoveringMovement(boolean hoveringMovement) {
		this.hoveringMovement = hoveringMovement;
	}

	/**
	 * 
	 * @param hoveringBack
	 */
	public void setHoveringBack(boolean hoveringBack) {
		this.hoveringBack = hoveringBack;
	}

	/**
	 * 
	 * @param hoveringYes
	 */
	public void setHoveringYes(boolean hoveringYes) {
		this.hoveringYes = hoveringYes;
	}

	/**
	 * 
	 * @param hoveringNo
	 */
	public void setHoveringNo(boolean hoveringNo) {
		this.hoveringNo = hoveringNo;
	}

	/**
	 * 
	 * @param hoveringX
	 */
	public void setHoveringX(boolean hoveringX) {
		this.hoveringX = hoveringX;
	}

	/**
	 * 
	 * @param ghostCount
	 */
	public void setGhostCount(int ghostCount) {
		this.ghostCount = ghostCount;
	}

	/**
	 * 
	 * @param destroyDuoGhost
	 */
	public void setDestroyDuoGhost(boolean destroyDuoGhost) {
		this.destroyDuoGhost = destroyDuoGhost;
	}

	/**
	 * 
	 * @param destroyCircle
	 */
	public void setDestroyCircle(boolean destroyCircle) {
		this.destroyCircle = destroyCircle;
	}

	/**
	 * 
	 * @param destroyTriangle
	 */
	public void setDestroyTriangle(boolean destroyTriangle) {
		this.destroyTriangle = destroyTriangle;
	}

	/**
	 * 
	 * @param destroyZigzag
	 */
	public void setDestroyZigzag(boolean destroyZigzag) {
		this.destroyZigzag = destroyZigzag;
	}

	/**
	 * 
	 * @param destroyHorizontal
	 */
	public void setDestroyHorizontal(boolean destroyHorizontal) {
		this.destroyHorizontal = destroyHorizontal;
	}

	/**
	 * 
	 * @param destroyVertical
	 */
	public void setDestroyVertical(boolean destroyVertical) {
		this.destroyVertical = destroyVertical;
	}

	/**
	 * 
	 * @param backPressed
	 */
	public void setBackPressed(boolean backPressed) {
		this.backPressed = backPressed;
	}

	/**
	 * 
	 * @return movementPrompt
	 */
	public boolean isMovementPrompt() {
		return movementPrompt;
	}

	/**
	 * 
	 * @param movementPrompt
	 */
	public void setMovementPrompt(boolean movementPrompt) {
		this.movementPrompt = movementPrompt;
	}

	/**
	 * 
	 * @return objectivePrompts
	 */
	public boolean isObjectivePrompts() {
		return objectivePrompts;
	}

	/**
	 * 
	 * @param objectivePrompts
	 */
	public void setObjectivePrompts(boolean objectivePrompts) {
		this.objectivePrompts = objectivePrompts;
	}

	/**
	 * 
	 * @param creditsPressed
	 */
	public void setCreditsPressed(boolean creditsPressed) {
		this.creditsPressed = creditsPressed;
	}

	/**
	 * 
	 * @return nextPage
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * 
	 * @param nextPage
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * 
	 * @param enterBook
	 */
	public void setEnterBook(boolean enterBook) {
		this.enterBook = enterBook;
	}

	/**
	 * 
	 * @param playGif
	 */
	public void setPlayGif(boolean playGif) {
		this.playGif = playGif;
	}

	/**
	 * 
	 * @param staticImageBook
	 */
	public void setStaticImageBook(boolean staticImageBook) {
		this.staticImageBook = staticImageBook;
	}

	/**
	 * 
	 * @param yesPressed
	 */
	public void setYesPressed(boolean yesPressed) {
		this.yesPressed = yesPressed;
	}

	/**
	 * 
	 * @param noPressed
	 */
	public void setNoPressed(boolean noPressed) {
		this.noPressed = noPressed;
	}

	/**
	 * 
	 * @param helpPressed
	 */
	public void setHelpPressed(boolean helpPressed) {
		this.helpPressed = helpPressed;
	}

	/**
	 * 
	 * @return visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * 
	 * @return yesPressed
	 */
	public boolean isYesPressed() {
		return yesPressed;
	}

	/**
	 * 
	 * @return noPressed
	 */
	public boolean isNoPressed() {
		return noPressed;
	}

	/**
	 * 
	 * @param inHouse
	 */
	public void setInHouse(boolean inHouse) {
		this.inHouse = inHouse;
	}

	/**
	 * 
	 * @param inConfirmation
	 */
	public void setInConfirmation(boolean inConfirmation) {
		this.inConfirmation = inConfirmation;
	}

	/**
	 * 
	 * @param firstTime
	 */
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	/**
	 * 
	 * @return carUsed
	 */
	public boolean isCarUsed() {
		return carUsed;
	}

	/**
	 * 
	 * @return carSceneDone
	 */
	public boolean isCarSceneDone() {
		return carSceneDone;
	}

	/**
	 * 
	 * @param carSceneDone
	 */
	public void setCarSceneDone(boolean carSceneDone) {
		this.carSceneDone = carSceneDone;
	}

	/**
	 * 
	 * @param dg
	 */
	public void setDoingDoctrineGhost(boolean dg) {
		this.doingDoctrineGhost = dg;
	}

	/**
	 * 
	 * @return doingDoctrineGhost
	 */
	public boolean isDoingDoctrineGhost() {
		return doingDoctrineGhost;
	}

	/**
	 * 
	 * @param doneDoctrineGhost
	 */
	public void setDoneDoctrineGhost(boolean doneDoctrineGhost) {
		this.doneDoctrineGhost = doneDoctrineGhost;
	}
}
