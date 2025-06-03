package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import Horror.Jumpscare;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

/*
 * Noah Sussman, Akhilan Saravanan, and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class GamePanel extends JPanel implements Runnable {

	// These are the settings for the window
	final int originalTileSize = 16; // 16x16 pixel tile size
	final int scale = 3; // scales everything to appear much larger on the window
	public final int tileSize = originalTileSize * scale; // scales every tile to appear much larger on the window
															// (48x48)

	int playerSpeed = 25;

	// Window dimensions
	final int maxScreenCol = 16; // window is 16 tiles wide
	final int maxScreenRow = 12; // window is 12 tiles long
	final int WIDTH = tileSize * maxScreenCol; // screen width in pixels (768 pixels)
	final int HEIGHT = tileSize * maxScreenRow; // screen height in pixels (576 pixels)
	final int FPS_TARGET = 60; // Frame rate target // Character position
	// Character position
	static int playerX = 768 / 2;
	static int playerY = 576 / 2;
	static int worldX = 768 / 2; // world position
	static int worldY = 576 / 2; // world position
	// Game Thread
	Thread gameThread; // keeps the program running until closed

	private JFrame window;

	// FPS variables
	private long frameCount = 0; // To count frames
	private long lastTime = System.nanoTime(); // Time of the last FPS calculation
	private double fps = 0; // FPS value

	// Game components
	Tiles t = new Tiles();
	Maps m = new Maps();
	static Jumpscare j = new Jumpscare();
	// ChangeScene cs = new ChangeScene(WIDTH, HEIGHT);
	Player p;
	Npc n = new Npc();
	Items it = new Items();
	Input id;
	Sound ambientAudio;
	Sound mainMenuSound;

	static int screenX;
	static int screenY;

	// sound
	private Sound footstepSound;

	private MainMenu mainMenu = new MainMenu();
	LoadingScreen loadingScreen = new LoadingScreen();
	private Minigame minigame = new Minigame();

	public GamePanel() {
	}

	// Constructor
	public GamePanel(JFrame window) throws IOException {
		Input input = new Input();
		id = new Input(n);
		Npc n = new Npc(input);

		this.window = window;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Preferred window size
		this.setDoubleBuffered(true); // Improves rendering performance
		this.addKeyListener(Player.keyH); // Recognizes key inputs
		this.addMouseMotionListener(input); // Recognizes mouse movement
		this.addMouseListener(input); // Recognize mouse clicks
		this.setFocusable(true);

		screenX = WIDTH / 2 - (tileSize / 2); // centers the player in the middle of the screen
		screenY = HEIGHT / 2 - (tileSize / 2); // centers the player in the middle of the screen

		// Background
		m.changeMap(3);
		// Find trees in the map
		m.findTrees();
		Tiles.tileCreating();

		// Load character
		p = new Player(input);
		p.loadCharacterImages();

		try {
			// https://www.youtube.com/watch?v=tmlZeYnfw7g
			ambientAudio = new Sound("src/sound/ambientAudio.wav");

			// https://www.youtube.com/watch?v=1a7kscUeItk
			mainMenuSound = new Sound("src/sound/mainMenuSound.wav");
		} catch (Exception e) {
			footstepSound = new Sound("src/sound/walkingSoundEffect.wav");
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
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void characterMovement() {
		// Character movement
		if (Player.disableCharacterMovement() == false) {
			if (Player.keyH.upPressed) {
				direction = "back";
				worldY -= playerSpeed; // move the world up when player presses up
			} else if (Player.keyH.downPressed) {
				direction = "front";
				worldY += playerSpeed; // move the world down when player goes down
			} else if (Player.keyH.leftPressed) {
				direction = "left";
				worldX -= playerSpeed; // move the world left when player goes left
			} else if (Player.keyH.rightPressed) {
				direction = "right";
				worldX += playerSpeed; // move the world right when player goes right
			}
		}
	}

	// Update game elements
	public void update() {
		window.setTitle("Are we Cooked? FPS: " + fps);
		p.collisionChecking();
		p.collision();
		characterMovement();
		Items.animation();
	}

	public void characterImage(Graphics g) throws IOException {

		BufferedImage jeffFront = ImageIO.read(new File("src/textures/charAI.png"));
		BufferedImage jeffBack = ImageIO.read(new File("src/textures/jeffBack.png"));
		BufferedImage jeffRight = ImageIO.read(new File("src/textures/jeffRight.png"));
		BufferedImage jeffLeft = ImageIO.read(new File("src/textures/jeffLeft.png"));

		BufferedImage character = null;
		if (Items.visible) {
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
			if (Maps.currentMap != 5) {
				g.drawImage(character, screenX, screenY, null);// draws the character in the middle of the screen
			}
		}
	}

	String direction = "";// stores the direction the player is facing

	// where all the drawing happens
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			m.camera(g);// camera method
			characterImage(g);// draws the character depending on the direction
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			it.car(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Items.animationFrame >= 150) {
			it.titleScreen(g2);
		}
		// Npc.text(g2);
		it.instructions(g2);
		if (Input.instructionsPressed) {
			it.prompts(g2);
			it.backMenu(g2);
		}
		System.out.println(Jumpscare.jumpscare);
		if (Jumpscare.jumpscare) {
			
			j.playSound();
			j.drawJumpscare(g2);
		} // Render the jumpscare image

		if (Maps.currentMap == 5) {
				try {
					m.drawExorcismRoom(g2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

		try {
			if (!Maps.hasJumpscared && !Maps.hasDoctrined) {
				m.fade(2, 3, g2, 248, 216, 82, 48, 414, 48, 145, 126);
				Input.changeMapPressed = false;
				Items.inHouse = true;
			}
			if (Maps.stepCount == -1 && Items.inHouse && !Maps.inNightmare) {
				Npc.text(g2, 5);
				Items.inHouse = false;
			}
			if (!Maps.hasDoctrined && Maps.hasJumpscared) {
				m.fade(3, 4, g2, 168, -159, 100, 100, 5550, 520, 150, 100);
				Input.changeMapPressed = false;
			}
			if (Maps.hasDoctrined) { // exorcism room
				m.fade(4, 5, g2, 838, 216, 55, 55, 838, 216, 55, 55);
				Input.changeMapPressed = false;
				Player.disableCharacterMovement();
			}
			if (Maps.hasFaded == 2) {
				if (Maps.hasJumpscared && !Maps.hasDoctrined) {
					worldX = 384;
					worldY = 288;
				}
				if (Maps.hasJumpscared && Maps.hasDoctrined) {
					worldX = 0;
					worldY = 0;
				}
				Maps.hasFaded = 0;
			}
			// Npc.text(g2, 3);
			// Items.insideDoctrine(g2);
			// Items.houseMirror(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Minigame.startExorcising();
		Minigame.drawPoints(g2);

		try {
			Items.doctrine(g2);
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			Items.graveyard(g2);
			Items.ghost(g2, 5100, 320, 125, 98);
			Items.book(g2, this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Maps.nightmare(g2, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Items.ghostLogic(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		p.footStepSounds();
		p.carSound();
		m.playNightmareSound();
		m.playDoctrineSound();

		if (LoadingScreen.loadingScreen) {
			loadingScreen.drawLoadingScreen(g2);
		}

		if (MainMenu.inMenu) {
			mainMenu.mainMenu(g2);
			if (!mainMenuSound.isPlaying()) {
				mainMenuSound.play();
				mainMenuSound.volume(-10.0f);
			}
		}

		if (Maps.currentMap == 3 && !MainMenu.inMenu && !LoadingScreen.loadingScreen) {
			if (mainMenuSound.isPlaying()) {
				mainMenuSound.stop();
			}
			if (!ambientAudio.isPlaying()) {
				ambientAudio.play();
				ambientAudio.volume(-20.0f);
			}
		}

		if (Maps.currentMap != 3) {
			if (ambientAudio.isPlaying()) {
				ambientAudio.stop();
			}
		}

		Items.help(g2);
		Items.credits(g2);

		g2.dispose();

	}

	public int getTileSize() {
		return tileSize;
	}
}
