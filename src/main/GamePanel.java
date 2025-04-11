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

		// Background
		m.changeMap(3);
		t.tileCreating();

		// Load character
		p = new Player(input);
		p.loadCharacterImages();

		// Find trees in the map
		m.findTrees();
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

	// Update game elements
	public void update() {
		window.setTitle("Are we Cooked? FPS: " + fps);
		p.collisionChecking();
		p.collision();
	}

	String direction = "";

	// Paint the game elements to the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // to utilize Graphics2D class (has more functions)
		int tilePlaceX = 0;
		int tilePlaceY = 0;
		int[] tileCoordsX = new int[50];
		try {
			for (int i = 0; i < Maps.tiles.length; i++) {
				tilePlaceX = 0; // resetting tilePlaceX position
				for (int j = 0; j < Maps.tiles[i].length; j++) {
					g2.drawImage(Tiles.tileImages[i][j], tilePlaceX, tilePlaceY, 48, 48, null);
					tileCoordsX[i] = tilePlaceX;
					tilePlaceX += tileSize;
				}
				tilePlaceY += tileSize;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		g2.setColor(Color.white);
		try {
			BufferedImage jeffFront = ImageIO.read(new File("src/textures/jeffFront.png"));
			BufferedImage jeffBack = ImageIO.read(new File("src/textures/jeffBack.png"));
			BufferedImage jeffRight = ImageIO.read(new File("src/textures/jeffLeft.png"));
			BufferedImage jeffLeft = ImageIO.read(new File("src/textures/jeffRight.png"));
			m.camera(g2, this);
			// Character movement
			if (p.keyH.upPressed) {
				playerY -= playerSpeed; // go up by player speed amount of pixels
				g2.drawImage(jeffBack, playerX, playerY, null);
				direction = "back";
			} else if (p.keyH.downPressed) {
				playerY += playerSpeed; // go down by player speed amount of pixels
				g2.drawImage(jeffFront, playerX, playerY, null);
				direction = "front";
			} else if (p.keyH.leftPressed) {
				playerX -= playerSpeed; // go to the left by player speed amount of pixels
				g2.drawImage(jeffRight, playerX, playerY, null);
				direction = "right";
			} else if (p.keyH.rightPressed) {
				playerX += playerSpeed; // go to the right by player speed amount of pixels
				g2.drawImage(jeffLeft, playerX, playerY, null);
				direction = "left";
			} else {
				if (direction.equals("back")) {
					g2.drawImage(jeffBack, playerX, playerY, null);
				} else if (direction.equals("left")) {
					g2.drawImage(jeffLeft, playerX, playerY, null);
				} else if (direction.equals("right")) {
					g2.drawImage(jeffRight, playerX, playerY, null);
				} else {
					g2.drawImage(jeffFront, playerX, playerY, null);
				}
			}

			if (j.isJumpscare()) {
				g2.drawImage(j.getCreepyMan(), 0, 0, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Draw other items and elements
		try {
			Items it = new Items(new Input());
			it.baseballBat(g2, p.inventoryCollision);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.drawNpcs(g2);
		n.communicate(g2);
		cs.changeScene(m);

		g2.dispose(); // saves resources
	}

	public int getTileSize() {
		return tileSize;
	}
}
