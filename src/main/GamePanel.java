package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import Horror.Jumpscare;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

import java.awt.geom.Point2D;

import interfaces.ReadFromFile;

/*
 * Noah Sussman, Akhilan Saravanan, and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class GamePanel extends JPanel implements Runnable, ReadFromFile {

	// These are the settings for the window
	private final int originalTileSize = 16; // 16x16 pixel tile size
	private final int scale = 3; // scales everything to appear much larger on the window
	private final int tileSize = originalTileSize * scale; // scales every tile to appear much larger on the window
															// (48x48)

	private int playerSpeed = 30;//speed of the player

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
	
	private JFrame window;   // The window that the game is displayed in

	// FPS variables
	private long frameCount = 0; // To count frames
	private long lastTime = System.nanoTime(); // Time of the last FPS calculation
	private double fps = 0; // FPS value

	// Game components
	private Tiles t;      // Reference to the Tiles class to access Tiles methods and properties
	private Maps m; // Reference to the Maps class to access Maps methods and properties
	private Jumpscare j; // Reference to the Input class to access Jumpscare methods and properties

	private Player p; // Reference to the Player class to access Player methods and properties
	private Npc n; // Reference to the Npc class to access Npc methods and properties
	private Items it; // Reference to the Items class to access Items methods and properties
	private Input id; // Reference to the Input class to access Input methods and properties
	private MainMenu mainMenu;  // Reference to the Input class to access Input methods and properties
	private Minigame minigame; // Reference to the Input class to access Input methods and properties
	private LoadingScreen ls; // Reference to the Input class to access Input methods and properties

	private String direction = "";  // stores the direction the player is facing

	private int screenX; // The x coordinate of the screen which is used to centre the player
	private int screenY; // The y coordinate of the screen which is used to centre the player
	

	private boolean once = false; // used to check if the text index has been reset

	private Sound ambientAudio;  //Variable to store the ambient audio sound file for the game
	private Sound mainMenuSound; //Variable to store the main menu sound file for the game
	private Sound footstepSound; // Variable to store the footstep sound file for the game

	BufferedImage jeffFront; // BufferedImage to store the front image of the character
	BufferedImage jeffBack;  // BufferedImage to store the back image of the character
	BufferedImage jeffRight; // BufferedImage to store the right image of the character
	BufferedImage jeffLeft;  // BufferedImage to store the left image of the character

	// Constructor
	public GamePanel(JFrame window) throws IOException {
		
		
		mainMenu = new MainMenu();   // Initializes the main menu object
		minigame = new Minigame();   // Initializes the minigame object
		it = new Items(this);		 // Initializes the items object
		id = new Input(this);        // Initializes the input object
		m = new Maps(this);          // Initializes the maps object
		n = new Npc(this);           // Initializes the npc object
		j = new Jumpscare(this);     // Initializes the jumpscare object
		t = new Tiles(this);         // Initializes the tiles object
		ls = new LoadingScreen(this);// Initializes the loading screen object
		p = new Player(this);        // Initializes the player object

		m.setT(t);       			 // Sets the tiles object in the maps object

		this.window = window;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Preferred window size
		this.setDoubleBuffered(true); 			// Improves rendering performance
		this.addKeyListener(p.keyH); 			// Recognizes key inputs
		this.addMouseMotionListener(id);  	    // Recognizes mouse movement
		this.addMouseListener(id); 				// Recognize mouse clicks
		this.setFocusable(true);

		screenX = WIDTH / 2 - (tileSize / 2); 		// centres the player in the middle of the screen
		screenY = HEIGHT / 2 - (tileSize / 2);	    // centres the player in the middle of the screen

	
		m.changeMap(3); //Set the background
		
		
		readFile(); //load files
		
		
		t.readFile();   // load files inside of tiles
		
		m.findTiles();   //assign the correct tiles inside of the maps object

		m.setP(p);          // sets the player object in the maps object
		m.setItems(it);     // sets the items object in the maps object
		m.setNpc(n);        // sets the npc object in the maps object
		m.setJ(j);          // sets the jumpscare object in the maps object
		m.setT(t);          // sets the tiles object in the maps object
		m.setInp(id);       // sets the input object in the maps object
 
		it.setP(p);         // sets the player object in the items object
		it.setInput(id);    // sets the input object in the items object
		it.setNpc(n);       // sets the npc object in the items object
		it.setJ(j);         // sets the jumpscare object in the items object
		it.setM(m);         // sets the maps object in the items object

		n.setInput(id);     // sets the input object in the npc object
		n.setItems(it);     // sets the items object in the npc object

		p.setM(m);          // sets the maps object in the player object
		p.setN(n);          // sets the npc object in the player object
		p.setIt(it);        // sets the items object in the player object
		p.setKeyH(id);      // sets the input object in the player object
 
		id.setNpc(n);       // sets the npc object in the input object
		id.setJumpscare(j); // sets the jumpscare object in the input object
		id.setM(m);         // sets the maps object in the input object

		t.setM(m);          // sets the maps object in the tiles object

		id.setLs(ls);       // sets the loading screen object in the input object
		
		
		
		
	}
	/**
	 * @purpose Reads the sound and image files for the game.
	 * 
	 * @throws IOException if there is an error reading the files
	 * @see {@link https://www.youtube.com/watch?v=tmlZeYnfw7g}  - Ambient audio scene 
	 * @see {@link https://www.youtube.com/watch?v=1a7kscUeItk} - Main menu sound
	 */
	@Override
	public void readFile() {
		try {
			
			ambientAudio = new Sound("src/sound/ambientAudio.wav");            //loads the ambient audio sound file
			mainMenuSound = new Sound("src/sound/mainMenuSound.wav");  		   //loads the main menu sound file
			footstepSound = new Sound("src/sound/walkingSoundEffect.wav");     //loads the footstep sound file

			jeffFront = ImageIO.read(new File("src/textures/charAI.png"));     // loads the front image of the character
			jeffBack = ImageIO.read(new File("src/textures/jeffBack.png"));    // loads the back image of the character
			jeffRight = ImageIO.read(new File("src/textures/jeffRight.png"));  // loads the right image of the character
			jeffLeft = ImageIO.read(new File("src/textures/jeffLeft.png"));    // loads the left image of the character
		} catch (Exception e) {                                                // catches any exception that happens during the file reading operations
			System.out.println("Sound loaded successfully");                   
		}
	}
	
	
	// Start the game thread
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); // starts the game thread (automatically calls the run method)
	}

	@Override
	public void run() {
		//60FPS lock taken from: https://stackoverflow.com/questions/771206/how-do-i-cap-my-framerate-at-60-fps-in-java
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
				double remainingTime = nextDrawTime - System.nanoTime();  // This variable store the remaining time until the next draw sequence
				remainingTime = remainingTime / 1000000;  // Converts it to milliseconds for easy calculation

				if (remainingTime < 0) {    // If Remaining time is negative, the game is running too slow and we set it to 0 to prompt the next draw sequence 
					remainingTime = 0;  
				}

				Thread.sleep((long) remainingTime);      // Sleep for the remaining time until the next draw sequence
				nextDrawTime += drawInterval;       // Update the next draw time by adding the draw interval (60 FPS)
			} catch (InterruptedException e) {       //catches any exception that happens during the sleep operation
				e.printStackTrace();
			}
		}
	}

	public void characterMovement() {
		if (p.disableCharacterMovement() == false) {     // checks if the character movement is disabled
			if (p.keyH.isUpPressed()) {
				direction = "back";
				worldY -= playerSpeed; // move the world up when player presses up

			} else if (p.keyH.isDownPressed()) {
				direction = "front";
				worldY += playerSpeed; // move the world down when player goes down

			} else if (p.keyH.isLeftPressed()) {
				direction = "left";
				worldX -= playerSpeed; // move the world left when player goes left

			} else if (p.keyH.isRightPressed()) {
				direction = "right";
				worldX += playerSpeed; // move the world right when player goes right

			}
		}
	}

	// Update game elements
	public void update() {
		p.updatePlayerPosition(worldX, worldY, playerX, playerY, screenX, screenY);
		m.updateMapValues(worldX, worldY);
		n.updateNpcValues(playerX, playerY, worldX, worldY);
		it.updateItemsValues(playerX, playerY, worldX, worldY);

		window.setTitle("Are we Cooked? FPS: " + fps);
		p.collisionChecking(this);
		p.collision(this);
		characterMovement();
		it.animation(this);
	}

	public void characterImage(Graphics g) throws IOException {

		BufferedImage character = null;
		if (it.isVisible()) {
			if (direction.equals("back")) {

				character = jeffBack;

			} else if (direction.equals("front")) {

				character = jeffFront;

			} else if (direction.equals("left")) {

				character = jeffLeft;

			} else if (direction.equals("right")) {

				character = jeffRight;
			} else {
				character = jeffFront;
			}
			if (m.getCurrentMap() != 5) {
				g.drawImage(character, screenX, screenY, null);// draws the character in the middle of the screen
			}
		}
	}

	public void drawTint(Graphics2D g2) {
		if (it.getAnimationFrame() >= 150) {
			it.titleScreen(g2);
		}

		Point2D centerPoint = new Point2D.Float(playerX, playerY);
		float radiusTint = (float) 210;

		Color transparentColor = new Color(0, 0, 0, 0);
		Color darkColor = new Color(0, 0, 0, 255); // Dark color with alpha for transparency

		RadialGradientPaint gradient = new RadialGradientPaint(centerPoint, radiusTint,
				new float[] { (float) 0.0, (float) 1.0 }, new Color[] { transparentColor, darkColor });

		g2.setPaint(gradient);
		g2.fillRect(playerX - 384, playerY - 288, playerX + 384, playerY + 288); // Fill the entire panel with the
																					// gradient

	}

	// where all the drawing happens
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		System.out.println(m.isEnd());
		try {
			m.camera(g, this);// camera method
			characterImage(g);// draws the character depending on the direction
			it.car(g2, this);
			if (m.getCurrentMap() == 3) {
				m.drawTint(g2, this);
			}
			it.doctrine(g2, this);

			if (m.getCurrentMap() == 5) {
				try {
					m.drawExorcismRoom(g2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			m.confirmationCollision(this, g2);
			if (!m.isHasJumpscared() && !m.isHasDoctrined()) {
				m.fade(2, 3, g2, 248, 196, 82, 48, 414, 48, 145, 126, this);
				it.setInHouse(true);
			}
			if (m.getStepCount() == -1 && it.isInHouse() && !m.isInNightmare() && !m.isDoneNightmare()) {
				n.text(g2, 5);
				it.setInHouse(false);
			}
			if (!m.isHasDoctrined() && m.isHasJumpscared()) {
				m.fade(3, 4, g2, 168, -159, 100, 100, 5550, 520, 150, 100, this);
				id.setChangeMapPressed(false);
			}
			if (m.isHasDoctrined() && !m.isLookInMirror()) { // exorcism room
				m.fade(4, 5, g2, 838, 216, 55, 55, 838, 216, 55, 55, this);
				id.setChangeMapPressed(false);
				p.disableCharacterMovement();
			}
			if (m.getHasFaded() == 2) {
				m.setHasFaded(0);
			}
			it.insideDoctrine(g2, this);

			if (m.getCurrentMap() == 5) {
				minigame.startExorcising();
				minigame.drawPoints(g2);
			}

			it.graveyard(g2, this);
			it.ghost(g2, 5100, 320, 125, 98, this);
			it.book(g2, this);

			if (m.getCurrentMap() == 5) {
				it.ghostLogic(g2, this);
			}

			p.footStepSounds();
			p.carSound();
			m.playNightmareSound();
			m.playDoctrineSound();
			m.nightmare(g2, this, this);
			m.mirrorScene(g2, this, this);

			it.instructions(g2);
			if (id.isInstructionsPressed()) {
				it.prompts(g2);
				it.backMenu(g2);
			}
			if (j.isJumpscare()) {
				j.drawJumpscare(g2);
				j.playSound();
			}
			if (ls.isLoadingScreen()) {
				ls.drawLoadingScreen(g2);
			}

			if (mainMenu.isInMenu()) {
				mainMenu.mainMenu(g2);
				if (!mainMenuSound.isPlaying()) {
					mainMenuSound.play();
					mainMenuSound.volume(-10.0f);
				}
			}

			if (m.getCurrentMap() == 3 && !mainMenu.isInMenu() && !ls.isLoadingScreen()) {
				if (mainMenuSound.isPlaying()) {
					mainMenuSound.stop();
				}
				if (!ambientAudio.isPlaying()) {
					ambientAudio.play();
					ambientAudio.volume(-20.0f);
				}
			}

			if (m.getCurrentMap() != 3) {
				if (ambientAudio.isPlaying()) {
					ambientAudio.stop();
				}
			}

			if (it.isCarUsed() && !it.isCarSceneDone() && !j.isJumpscare()) {
				g2.drawImage(jeffFront, 580, 320, 96, 144, null);
				if (!once) {
					n.setTextIndex(0); // Reset text index to 0
					once = true; // Set once to true to prevent resetting again
				}
				n.text(g2, 7);
			}
			it.help(g2);
			it.credits(g2);
			m.endScreen(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.dispose();

	}

	//Getter and Setter Methods
	
	public int getWorldX() {
		return worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getOriginalTileSize() {
		return originalTileSize;
	}

	public int getScale() {
		return scale;
	}

	public int getPlayerSpeed() {
		return playerSpeed;
	}

	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getFPS_TARGET() {
		return FPS_TARGET;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public JFrame getWindow() {
		return window;
	}

	public long getFrameCount() {
		return frameCount;
	}

	public long getLastTime() {
		return lastTime;
	}

	public double getFps() {
		return fps;
	}

	public Tiles getT() {
		return t;
	}

	public Maps getM() {
		return m;
	}

	public Jumpscare getJ() {
		return j;
	}

	public Player getP() {
		return p;
	}

	public Npc getN() {
		return n;
	}

	public Items getIt() {
		return it;
	}

	public Input getId() {
		return id;
	}

	public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public Sound getFootstepSound() {
		return footstepSound;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public String getDirection() {
		return direction;
	}

	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}

	public void setFrameCount(long frameCount) {
		this.frameCount = frameCount;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public void setFps(double fps) {
		this.fps = fps;
	}

	public void setT(Tiles t) {
		this.t = t;
	}

	public void setM(Maps m) {
		this.m = m;
	}

	public void setJ(Jumpscare j) {
		this.j = j;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public void setN(Npc n) {
		this.n = n;
	}

	public void setIt(Items it) {
		this.it = it;
	}

	public void setId(Input id) {
		this.id = id;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public void setFootstepSound(Sound footstepSound) {
		this.footstepSound = footstepSound;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Sound getAmbientAudio() {
		return ambientAudio;
	}

	public void setAmbientAudio(Sound ambientAudio) {
		this.ambientAudio = ambientAudio;
	}

	public Sound getMainMenuSound() {
		return mainMenuSound;
	}

	public void setMainMenuSound(Sound mainMenuSound) {
		this.mainMenuSound = mainMenuSound;
	}

	public LoadingScreen getLs() {
		return ls;
	}

	public void setLs(LoadingScreen ls) {
		this.ls = ls;
	}

}
