package main;

import javax.imageio.ImageIO;
import javax.swing.*;

import Horror.Jumpscare;

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

	int playerSpeed = 4;

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
	static boolean jumpscare = false; // jumpscare boolean

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
	Jumpscare j = new Jumpscare();
	ChangeScene cs = new ChangeScene(WIDTH, HEIGHT);
	Player p;
	Npc n = new Npc();
	Items it = new Items();

	static int screenX;
	static int screenY;

	// sound
	private Sound footstepSound;
	
	public GamePanel() {
	}

	// Constructor
	public GamePanel(JFrame window) throws IOException {
		Input input = new Input();
		Npc n = new Npc(input);
		Items it = new Items(input);

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
		t.tileCreating();

		// Load character
		p = new Player(input);
		p.loadCharacterImages();

		try {
			footstepSound = new Sound("src/sound/walkingSoundEffect.wav");
			System.out.println("Sound loaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
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
			if (p.keyH.upPressed) {
				direction = "back";
				worldY -= playerSpeed; // move the world up when player presses up
			} else if (p.keyH.downPressed) {
				direction = "front";
				worldY += playerSpeed; // move the world down when player goes down
			} else if (p.keyH.leftPressed) {
				direction = "left";
				worldX -= playerSpeed; // move the world left when player goes left
			} else if (p.keyH.rightPressed) {
				direction = "right";
				worldX += playerSpeed; // move the world right when player goes right
			}

			// footsteps
			if (p.keyH.upPressed && !p.keyH.upReleased || p.keyH.downPressed && !p.keyH.downReleased
					|| p.keyH.rightPressed && !p.keyH.rightReleased || p.keyH.leftPressed && !p.keyH.leftReleased) {
				// System.out.println(footstepSound);
				// footstepSound.play();
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

		BufferedImage jeffFront = ImageIO.read(new File("src/textures/jeffFront.png"));
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

			g.drawImage(character, screenX, screenY, null);// draws the character in the middle of the screen
			// System.out.println(worldX + " " + worldY);
		}
	}

	String direction = "";// stores the direction the player is facing
	static boolean fading = false;
	boolean goOut = false;

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
		try {
			it.badGuy(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Npc.text(g2);
		it.prompts(g2);
		try {
			if (jumpscare) {
				long startTime = System.currentTimeMillis();
				j.playSound();
				while (System.currentTimeMillis() - startTime < 2000) {
					j.drawJumpscare(g2);
				}
			}
			if (p.keyH.changeMapPressed && worldX >= 446 && worldX <= 564 && worldY >= 46 && worldY <= 132) {
				fading = true;
				Player.disableCharacterMovement();
				p.keyH.changeMapPressed = false;
			}
			if (!fading && p.keyH.changeMapPressed && worldX >= 258 && worldX < 258 + 48 && worldY >= 216
					&& worldY < 216 + 72) {
				System.out.println(fading);
				fading = true;
				Player.disableCharacterMovement();
				m.stepCount = 0;
				goOut = true;
			}
			if (fading) {
				m.fading(g2, t, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.dispose();

	}

	public int getTileSize() {
		return tileSize;
	}
}
