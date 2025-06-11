/**
 * @author Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * @since April 2, 2025
 * @version 2.0
 * Final Project ICS4U0
 * Whispers of the Deceived
 */
package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import Horror.Jumpscare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.awt.geom.Point2D;

import interfaces.ReadFromFile;

/**
 * The hub for all other classes to interact with main
 */
public class GamePanel extends JPanel implements Runnable, ReadFromFile {

	// These are the settings for the window
	private final int originalTileSize = 16; // 16x16 pixel tile size
	private final int scale = 3; // scales everything to appear much larger on the window
	private final int tileSize = originalTileSize * scale; // scales every tile to appear much larger on the window
															// (48x48)

	private int playerSpeed = 4;// speed of the player

	// Window dimensions
	private final int maxScreenCol = 16; // window is 16 tiles wide
	private final int maxScreenRow = 12; // window is 12 tiles long
	private final int WIDTH = tileSize * maxScreenCol; // screen width in pixels (768 pixels)
	private final int HEIGHT = tileSize * maxScreenRow; // screen height in pixels (576 pixels)
	private final int FPS_TARGET = 60; // Frame rate target // Character position
	// Character position
	private int playerX = 768 / 2;
	private int playerY = 576 / 2;
	private int worldX = 768 / 2; // world position
	private int worldY = 576 / 2; // world position
	// Game Thread
	private Thread gameThread; // keeps the program running until closed

	private JFrame window; // The window that the game is displayed in

	// FPS variables
	private long frameCount = 0; // To count frames
	private long lastTime = System.nanoTime(); // Time of the last FPS calculation
	private double fps = 0; // FPS value

	// Game components
	private Tiles t; // Reference to the Tiles class to access Tiles methods and properties
	private Maps m; // Reference to the Maps class to access Maps methods and properties
	private Jumpscare j; // Reference to the Input class to access Jumpscare methods and properties

	private Player p; // Reference to the Player class to access Player methods and properties
	private Npc n; // Reference to the Npc class to access Npc methods and properties
	private Items it; // Reference to the Items class to access Items methods and properties
	private Input id; // Reference to the Input class to access Input methods and properties
	private MainMenu mainMenu; // Reference to the Input class to access Input methods and properties
	private Minigame minigame; // Reference to the Input class to access Input methods and properties
	private LoadingScreen ls; // Reference to the Input class to access Input methods and properties

	private String direction = ""; // stores the direction the player is facing

	private int screenX; // The x coordinate of the screen which is used to centre the player
	private int screenY; // The y coordinate of the screen which is used to centre the player

	private boolean once = false; // used to check if the text index has been reset

	private Sound ambientAudio; // Variable to store the ambient audio sound file for the game
	private Sound mainMenuSound; // Variable to store the main menu sound file for the game
	private Sound footstepSound; // Variable to store the footstep sound file for the game

	BufferedImage jeffFront; // BufferedImage to store the front image of the character
	BufferedImage jeffBack; // BufferedImage to store the back image of the character
	BufferedImage jeffRight; // BufferedImage to store the right image of the character
	BufferedImage jeffLeft; // BufferedImage to store the left image of the character

	/**
	 * Constructor for the GamePanel class
	 * 
	 * @param window
	 * @throws IOException
	 */
	public GamePanel(JFrame window) throws IOException {

		mainMenu = new MainMenu(); // Initializes the main menu object
		minigame = new Minigame(); // Initializes the minigame object
		it = new Items(this); // Initializes the items object
		id = new Input(this); // Initializes the input object
		m = new Maps(this); // Initializes the maps object
		n = new Npc(this); // Initializes the npc object
		j = new Jumpscare(this); // Initializes the jumpscare object
		t = new Tiles(this); // Initializes the tiles object
		ls = new LoadingScreen(this);// Initializes the loading screen object
		p = new Player(this); // Initializes the player object

		m.setT(t); // Sets the tiles object in the maps object

		this.window = window;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Preferred window size
		this.setDoubleBuffered(true); // Improves rendering performance
		this.addKeyListener(p.keyH); // Recognizes key inputs
		this.addMouseMotionListener(id); // Recognizes mouse movement
		this.addMouseListener(id); // Recognize mouse clicks
		this.setFocusable(true);

		screenX = WIDTH / 2 - (tileSize / 2); // centres the player in the middle of the screen
		screenY = HEIGHT / 2 - (tileSize / 2); // centres the player in the middle of the screen

		m.changeMap(3); // Set the background

		readFile(); // load files

		t.readFile(); // load files inside of tiles

		m.findTiles(); // assign the correct tiles inside of the maps object

		m.setP(p); // sets the player object in the maps object
		m.setItems(it); // sets the items object in the maps object
		m.setNpc(n); // sets the npc object in the maps object
		m.setJ(j); // sets the jumpscare object in the maps object
		m.setT(t); // sets the tiles object in the maps object
		m.setInp(id); // sets the input object in the maps object

		it.setP(p); // sets the player object in the items object
		it.setInput(id); // sets the input object in the items object
		it.setNpc(n); // sets the npc object in the items object
		it.setJ(j); // sets the jumpscare object in the items object
		it.setM(m); // sets the maps object in the items object

		n.setInput(id); // sets the input object in the npc object
		n.setItems(it); // sets the items object in the npc object

		p.setM(m); // sets the maps object in the player object
		p.setN(n); // sets the npc object in the player object
		p.setIt(it); // sets the items object in the player object
		p.setKeyH(id); // sets the input object in the player object

		id.setNpc(n); // sets the npc object in the input object
		id.setJumpscare(j); // sets the jumpscare object in the input object
		id.setM(m); // sets the maps object in the input object

		t.setM(m); // sets the maps object in the tiles object

		id.setLs(ls); // sets the loading screen object in the input object

	}

	/**
	 * Reads the sound and image files for the game.
	 * 
	 * @throws IOException if there is an error reading the files
	 * @see Dávid, L. (2022, January 10). Is this a horror game main-menu
	 *      soundtrack?! . Is this a horror game main-menu soundtrack?! - YouTube.
	 *      {@link https://www.youtube.com/watch?v=1a7kscUeItk}
	 * 
	 * @see Mystical Soundscapes. (2022, October 21). Night Of The Dead | HORROR
	 *      AMBIENCE | 3 Hours. Night Of The Dead | HORROR AMBIENCE | 3 Hours -
	 *      YouTube. {@link https://www.youtube.com/watch?v=tmlZeYnfw7g}
	 */
	@Override
	public void readFile() {
		try {

			ambientAudio = new Sound("src/sound/ambientAudio.wav"); // loads the ambient audio sound file
			mainMenuSound = new Sound("src/sound/mainMenuSound.wav"); // loads the main menu sound file
			footstepSound = new Sound("src/sound/walkingSoundEffect.wav"); // loads the footstep sound file

			jeffFront = ImageIO.read(new File("src/textures/charAI.png")); // loads the front image of the character
			jeffBack = ImageIO.read(new File("src/textures/jeffBack.png")); // loads the back image of the character
			jeffRight = ImageIO.read(new File("src/textures/jeffRight.png")); // loads the right image of the character
			jeffLeft = ImageIO.read(new File("src/textures/jeffLeft.png")); // loads the left image of the character
		} catch (Exception e) { // catches any exception that happens during the file reading operations
			System.out.println("Sound loaded successfully");
		}
	}

	/**
	 * This method is responsible for beginning the game thread
	 */
	// Start the game thread
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); // starts the game thread (automatically calls the run method)
	}

	/**
	 * This methods runs on the game thread
	 * 
	 * @see CPU - how do I cap my framerate at 60 fps in Java? - stack overflow.
	 *      (n.d.).
	 *      {@link https://stackoverflow.com/questions/771206/how-do-i-cap-my-framerate-at-60-fps-in-java/14421479}
	 */
	@Override
	public void run() {
		// 60FPS lock taken from: link above
		double drawInterval = 1000000000 / FPS_TARGET;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {
			update(); // updates the information
			repaint(); // draws the screen with the updated information

			// Calculate FPS
			frameCount++;
			long now = System.nanoTime();
			if (now - lastTime >= 1000000000) { // 1 second has passed
				fps = frameCount; // Set the FPS
				frameCount = 0; // Reset the frame counter
				lastTime = now; // Reset the last time
			}

			// 60 FPS LOCK
			try {
				double remainingTime = nextDrawTime - System.nanoTime(); // This variable store the remaining time until
																			// the next draw sequence
				remainingTime = remainingTime / 1000000; // Converts it to milliseconds for easy calculation

				if (remainingTime < 0) { // If Remaining time is negative, the game is running too slow and we set it to
											// 0 to prompt the next draw sequence
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime); // Sleep for the remaining time until the next draw sequence
				nextDrawTime += drawInterval; // Update the next draw time by adding the draw interval (60 FPS)
			} catch (InterruptedException e) { // catches any exception that happens during the sleep operation
				e.printStackTrace();
			}
		}
	}

	/**
	 * Controls the movement of the character and what direction the image
	 * (character) will face
	 */
	public void characterMovement() {
		if (p.disableCharacterMovement() == false) { // checks if the character movement is disabled
			if (p.keyH.isUpPressed()) {// up key is pressed
				direction = "back";
				worldY -= playerSpeed; // move the world up when player presses up

			} else if (p.keyH.isDownPressed()) {// down key is pressed
				direction = "front";
				worldY += playerSpeed; // move the world down when player goes down

			} else if (p.keyH.isLeftPressed()) {// left key is pressed
				direction = "left";
				worldX -= playerSpeed; // move the world left when player goes left

			} else if (p.keyH.isRightPressed()) {// right key is pressed
				direction = "right";
				worldX += playerSpeed; // move the world right when player goes right

			}
		}
	}

	/**
	 * Updates the game elements such as player position, map values, NPC values,
	 * and item values. Also updates the window title with the current FPS.
	 */
	public void update() {

		p.updatePlayerPosition(worldX, worldY, playerX, playerY, screenX, screenY); // updates the variable values of
																					// the Player object
		m.updateMapValues(worldX, worldY); // updates the variable values of the Maps object
		n.updateNpcValues(playerX, playerY, worldX, worldY); // updates the variable values of the Npc object
		it.updateItemsValues(playerX, playerY, worldX, worldY); // updates the variable values of the Items object

		window.setTitle("Whispers Of The Decieved, FPS: " + fps); // sets the title of the window and displays the
																	// current FPS
		p.collisionChecking(this); // checks for collision
		p.collision(this); // checks for collision
		characterMovement(); // calls the character movement method to update the character position
		it.animation(this); // updates the animations within the Items object
	}

	/**
	 * This method draws the character image based on the direction the character is
	 * facing.
	 * 
	 * @param g
	 * @throws IOException
	 */
	public void characterImage(Graphics g) throws IOException {

		BufferedImage character = null; // Initalizes the charcater image variable
		if (it.isVisible()) { // checks if the player is suposed to be visible
			if (direction.equals("back")) { // checks if the player is facing back and sets the character image variable
											// to back image

				character = jeffBack;

			} else if (direction.equals("front")) { // checks if the player is facing forwards and sets the character
													// image variable to forward image

				character = jeffFront;

			} else if (direction.equals("left")) { // checks if the player is facing left and sets the character image
													// variable to left image

				character = jeffLeft;

			} else if (direction.equals("right")) { // checks if the player is facing right and sets the character image
													// variable to right image

				character = jeffRight;
			} else {
				character = jeffFront;
			}
			if (m.getCurrentMap() != 5) { // checks if the current map is not the exorcism room, and if so, draws the
											// character image in the middle of the screen
				g.drawImage(character, screenX, screenY, null);// draws the character in the middle of the screen
			}
		}
	}

	/**
	 * This method draws a radial gradient tint on the screen for the darkness
	 * effect.
	 * 
	 * @param g2
	 */
	public void drawTint(Graphics2D g2) {
		if (it.getAnimationFrame() >= 150) { // If the animation frame is greater than or equal to 150, it draws the
												// title screen
			it.titleScreen(g2);
		}

		Point2D centerPoint = new Point2D.Float(playerX, playerY); // Calculates the center point of the radial gradient
																	// based on the player's position
		float radiusTint = (float) 210; // Calculates the radius of the radial gradient

		Color transparentColor = new Color(0, 0, 0, 0); // Transparent color with alpha for transparency
		Color darkColor = new Color(0, 0, 0, 255); // Dark color with alpha for transparency

		RadialGradientPaint gradient = new RadialGradientPaint(centerPoint, radiusTint,
				new float[] { (float) 0.0, (float) 1.0 }, new Color[] { transparentColor, darkColor }); // Creates a
																										// radial
																										// gradient
																										// paint with
																										// the center
																										// point, and
																										// radius

		g2.setPaint(gradient); // Sets the paint of the Graphics2D object to the radial gradient
		g2.fillRect(playerX - 384, playerY - 288, playerX + 384, playerY + 288); // Fill the entire screen with the
																					// gradient

	}

	/**
	 * This method is responsible for drawing all the game elements on the screen.
	 * It uses the Graphics2D object to draw the character, items, NPCs, and other
	 * game elements.
	 * 
	 * @param g The Graphics object used for drawing
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // calls the parent class's paintComponent method
		Graphics2D g2 = (Graphics2D) g; // casts the Graphics object to Graphics2D for better drawing capabilities
		try {
			m.camera(g, this);// camera method
			characterImage(g);// draws the character depending on the direction
			it.car(g2, this); // draws the car if it is used
			if (m.getCurrentMap() == 3) { // if the current map is the open world map, it draws the house
				m.drawTint(g2, this); // draws the tint on the screen for the darkness effect
			}
			it.doctrine(g2, this); // draws the doctrine if it is used

			if (m.getCurrentMap() == 5) { // if the current map is the exorcism room, it draws the exorcism room
				try {
					m.drawExorcismRoom(g2);
				} catch (IOException e) { // catches any exception that happens during the drawing of the exorcism room
					e.printStackTrace();
				}
			}
			m.confirmationCollision(this, g2); // checks for collision with the confirmation box
			if (!m.isHasJumpscared() && !m.isHasDoctrined()) { // if the player has not been jumpscared or doctrined, it
																// draws the house
				m.fade(2, 3, g2, 248, 196, 82, 48, 414, 48, 145, 126, this); // fades the screen to the house
				it.setInHouse(true); // sets the in house variable to true
			}
			if (m.getStepCount() == -1 && it.isInHouse() && !m.isInNightmare() && !m.isDoneNightmare()) { // if the
																											// player is
																											// in the
																											// house and
																											// has
																											// finished
																											// the
																											// nightmare,
																											// it draws
																											// the text
				n.text(g2, 5); // draws the text
				it.setInHouse(false); // sets the in house variable to false
			}
			if (!m.isHasDoctrined() && m.isHasJumpscared()) { // if the player has been jumpscared but not doctrined, it
																// fades the screen to the doctrine
				m.fade(3, 4, g2, 168, -159, 100, 100, 5550, 520, 150, 100, this); // fades the screen to the doctrine
				id.setChangeMapPressed(false); // sets the change map pressed variable to false
			}
			if (m.isHasDoctrined() && !m.isLookInMirror()) { // if the player has been doctrined but not looked in the
																// mirror, it fades the screen to the nightmare
				m.fade(4, 5, g2, 838, 216, 55, 55, 838, 216, 55, 55, this); // fades the screen to the minigame
				id.setChangeMapPressed(false); // sets the change map pressed variable to false
				p.disableCharacterMovement(); // disables character movement
			}
			if (m.getHasFaded() == 2) { // resets the has faded variable to 0 if it is 2 to prevent players from
										// entering the same place again.
				m.setHasFaded(0);
			}
			it.insideDoctrine(g2, this); // draws the inside of the doctrine

			if (m.getCurrentMap() == 5) { // starts the exorcism minigame if the current map is the exorcism room
				minigame.startExorcising();
				minigame.drawPoints(g2);
			}

			it.graveyard(g2, this); // draws the graveyard
			it.ghost(g2, 5100, 320, 125, 98, this); // draws the ghost in the graveyard
			it.book(g2, this); // draws the book in the graveyard

			if (m.getCurrentMap() == 5) { // if the current map is the exorcism room, it draws the ghost logic
				it.ghostLogic(g2, this);
			}

			p.footStepSounds(); // plays the footstep sound if the player is moving
			p.carSound(); // plays the car sound if the player is in the car
			m.playNightmareSound(); // plays the nightmare sound if the player is in the nightmare
			m.playDoctrineSound(); // plays the doctrine sound if the player is in the doctrine
			m.nightmare(g2, this, this); // draws the nightmare if the player is in the nightmare
			m.mirrorScene(g2, this, this); // draws the mirror scene if the player is in the mirror scene

			it.instructions(g2); // draws the instructions if the player is viewing the instructions screen
			if (id.isInstructionsPressed()) { // checks if the instructions button is pressed and draws the instructions
												// screen
				it.prompts(g2);
				it.backMenu(g2);
			}
			if (j.isJumpscare()) { // checks if the player is jumpscared and draws the jumpscare screen
				j.drawJumpscare(g2);
				j.playSound();
			}
			if (ls.isLoadingScreen()) { // checks if the loading screen is active and draws the loading screen
				ls.drawLoadingScreen(g2);
			}

			if (mainMenu.isInMenu()) { // checks if the main menu is active and draws the main menu
				mainMenu.mainMenu(g2);
				if (!mainMenuSound.isPlaying()) { // checks if the main menu sound is not playing and plays it if it is
													// in the main menu
					mainMenuSound.play();
					mainMenuSound.volume(-10.0f); // sets the volume of the main menu sound
				}
			}

			if (m.getCurrentMap() == 3 && !mainMenu.isInMenu() && !ls.isLoadingScreen()) { // checks if the current map
																							// is the open world map and
																							// the main menu is not
																							// active and the loading
																							// screen is not active
				if (mainMenuSound.isPlaying()) { // checks if the main menu sound is playing and stops it if it is in
													// the open world map
					mainMenuSound.stop();
				}
				if (!ambientAudio.isPlaying()) { // checks if the ambient audio is not playing and plays it if it is in
													// the open world map
					ambientAudio.play();
					ambientAudio.volume(-20.0f);
				}
			}

			if (m.getCurrentMap() != 3) { // checks if the current map is not the open world map and stops the ambient
											// audio if it is not
				if (ambientAudio.isPlaying()) {
					ambientAudio.stop();
				}
			}

			if (it.isCarUsed() && !it.isCarSceneDone() && !j.isJumpscare()) { // checks if the car is used and the car
																				// scene is not done and the player is
																				// not jumpscared
				g2.drawImage(jeffFront, 580, 320, 96, 144, null); // draws the character in the car scene
				if (!once) {
					n.setTextIndex(0); // Reset text index to 0
					once = true; // Set once to true to prevent resetting again
				}
				n.text(g2, 7);
			}
			it.help(g2); // draws the help screen if the player is viewing the help screen
			it.credits(g2); // draws the credits screen if the player is viewing the credits screen
			m.endScreen(g2); // draws the end screen if the player has completed the game
		} catch (IOException e) { // catches any exception that happens during the drawing operations
			e.printStackTrace();
		}
		g2.dispose(); // disposes the Graphics2D object to free up resources

	}

	// Getter and Setter Methods

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
	 * @return tileSize
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * 
	 * @return originalTileSize
	 */
	public int getOriginalTileSize() {
		return originalTileSize;
	}

	/**
	 * 
	 * @return scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * 
	 * @return playerSpeed
	 */
	public int getPlayerSpeed() {
		return playerSpeed;
	}

	/**
	 * 
	 * @return maxScreenCol
	 */
	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	/**
	 * 
	 * @return maxScreenRow
	 */
	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	/**
	 * 
	 * @return WIDTH
	 */
	public int getWIDTH() {
		return WIDTH;
	}

	/**
	 * 
	 * @return HEIGHT
	 */
	public int getHEIGHT() {
		return HEIGHT;
	}

	/**
	 * 
	 * @return FPS_TARGET
	 */
	public int getFPS_TARGET() {
		return FPS_TARGET;
	}

	/**
	 * 
	 * @return playerX
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * 
	 * @return playerY
	 */
	public int getPlayerY() {
		return playerY;
	}

	/**
	 * 
	 * @return gameThread
	 */
	public Thread getGameThread() {
		return gameThread;
	}

	/**
	 * 
	 * @return window
	 */
	public JFrame getWindow() {
		return window;
	}

	/**
	 * 
	 * @return frameCount
	 */
	public long getFrameCount() {
		return frameCount;
	}

	/**
	 * 
	 * @return lastTime
	 */
	public long getLastTime() {
		return lastTime;
	}

	/**
	 * 
	 * @return fps
	 */
	public double getFps() {
		return fps;
	}

	/**
	 * 
	 * @return t
	 */
	public Tiles getT() {
		return t;
	}

	/**
	 * 
	 * @return m
	 */
	public Maps getM() {
		return m;
	}

	/**
	 * 
	 * @return j
	 */
	public Jumpscare getJ() {
		return j;
	}

	/**
	 * 
	 * @return p
	 */
	public Player getP() {
		return p;
	}

	/**
	 * 
	 * @return n
	 */
	public Npc getN() {
		return n;
	}

	/**
	 * 
	 * @return it
	 */
	public Items getIt() {
		return it;
	}

	/**
	 * 
	 * @return id
	 */
	public Input getId() {
		return id;
	}

	/**
	 * 
	 * @return screenX
	 */
	public int getScreenX() {
		return screenX;
	}

	/**
	 * 
	 * @return screenY
	 */
	public int getScreenY() {
		return screenY;
	}

	/**
	 * 
	 * @return footstepSound
	 */
	public Sound getFootstepSound() {
		return footstepSound;
	}

	/**
	 * 
	 * @return mainMenu
	 */
	public MainMenu getMainMenu() {
		return mainMenu;
	}

	/**
	 * 
	 * @return minigame
	 */
	public Minigame getMinigame() {
		return minigame;
	}

	/**
	 * 
	 * @return direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * 
	 * @param playerSpeed
	 */
	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	/**
	 * 
	 * @param playerX
	 */
	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	/**
	 * 
	 * @param playerY
	 */
	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	/**
	 * 
	 * @param worldX
	 */
	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	/**
	 * 
	 * @param worldY
	 */
	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	/**
	 * 
	 * @param gameThread
	 */
	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	/**
	 * 
	 * @param window
	 */
	public void setWindow(JFrame window) {
		this.window = window;
	}

	/**
	 * 
	 * @param frameCount
	 */
	public void setFrameCount(long frameCount) {
		this.frameCount = frameCount;
	}

	/**
	 * 
	 * @param lastTime
	 */
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * 
	 * @param fps
	 */
	public void setFps(double fps) {
		this.fps = fps;
	}

	/**
	 * 
	 * @param t
	 */
	public void setT(Tiles t) {
		this.t = t;
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
	 * @param p
	 */
	public void setP(Player p) {
		this.p = p;
	}

	/**
	 * 
	 * @param n
	 */
	public void setN(Npc n) {
		this.n = n;
	}

	/**
	 * 
	 * @param it
	 */
	public void setIt(Items it) {
		this.it = it;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Input id) {
		this.id = id;
	}

	/**
	 * 
	 * @param mainMenu
	 */
	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	/**
	 * 
	 * @param minigame
	 */
	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	/**
	 * 
	 * @param screenX
	 */
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	/**
	 * 
	 * @param screenY
	 */
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	/**
	 * 
	 * @param footstepSound
	 */
	public void setFootstepSound(Sound footstepSound) {
		this.footstepSound = footstepSound;
	}

	/**
	 * 
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * 
	 * @return ambientAudio
	 */
	public Sound getAmbientAudio() {
		return ambientAudio;
	}

	/**
	 * 
	 * @param ambientAudio
	 */
	public void setAmbientAudio(Sound ambientAudio) {
		this.ambientAudio = ambientAudio;
	}

	/**
	 * 
	 * @return mainMenuSound
	 */
	public Sound getMainMenuSound() {
		return mainMenuSound;
	}

	/**
	 * 
	 * @param mainMenuSound
	 */
	public void setMainMenuSound(Sound mainMenuSound) {
		this.mainMenuSound = mainMenuSound;
	}

	/**
	 * 
	 * @return ls
	 */
	public LoadingScreen getLs() {
		return ls;
	}

	/**
	 * 
	 * @param ls
	 */
	public void setLs(LoadingScreen ls) {
		this.ls = ls;
	}
}
