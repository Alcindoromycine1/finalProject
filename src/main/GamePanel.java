package main;

import javax.imageio.ImageIO;
import javax.swing.*;

import Horror.Jumpscare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class GamePanel extends JPanel implements Runnable {

	// These are the settings for the window
	final int originalTileSize = 16;// 16x16 pixel tile size
	final int scale = 3;// scales everything to appear much larger on the window
	public final int tileSize = originalTileSize * scale;// scales every tile to appear much larger on the window
															// (48x48
	// Character position
	static int playerX = 400;
	static int playerY = 400;
	int playerSpeed = 4;

	// pixels)
	final int maxScreenCol = 16;// window is 16 tiles wide
	final int maxScreenRow = 12;// window is 12 tiles long
	final int WIDTH = tileSize * maxScreenCol;// screen width in pixels (768 pixels)
	final int HEIGHT = tileSize * maxScreenRow;// screen height in pixels (576 pixels)
	final int FPS_TARGET = 60;// Frame rate target

	Thread gameThread;// keeps the program running until closed

	private JFrame window;

	// FPS variables
	private long frameCount = 0; // To count frames
	private long lastTime = System.nanoTime(); // Time of the last FPS calculation
	private double fps = 0; // FPS value
	Tiles t = new Tiles();
	Maps m = new Maps();
	// Sound s = new Sound ();
	Player p = new Player();
	Jumpscare j = new Jumpscare();

	public GamePanel() {

	}

	public GamePanel(JFrame window) throws IOException {// Constructor

		this.window = window;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));// Preferred window size
		this.setDoubleBuffered(true);// Improves rendering performance
		this.addKeyListener(Player.keyH);// recognizes key inputs
		this.setFocusable(true);
		// Background
		m.mapIntro();
		t.tileCreating();
		// Load character
		p.loadCharacterImages();
		m.findTrees();
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();// starts the game thread (automatically calls the run method)
	}

	@Override
	public void run() {// This method is automatically called when we create the gameThread

		double drawInterval = 1000000000 / FPS_TARGET;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {// as long as the game thread exists
			update();// updates the information
			repaint();// draws the screen with the updated information

			// Calculate FPS
			frameCount++;
			long now = System.nanoTime();
			if (now - lastTime >= 1000000000) { // 1 second has passed
				fps = frameCount; // Set the FPS
				frameCount = 0; // Reset the frame counter
				lastTime = now; // Reset the last time
			}

			// 60 FPS LOCK
			// https://www.youtube.com/watch?v=VpH33Uw-_0E&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=2&ab_channel=RyiSnow
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

	public void update() {

		window.setTitle("Are we Cooked? FPS: " + fps);
		p.collisionChecking();
		p.collision();
	}

	String direction = "";

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;// to utilize Graphics2D class (has more functions)

		int tilePlaceX = 0;
		int tilePlaceY = 0;
		int tileCoordsX[] = new int[50];
		try {

			for (int i = 0; i < Maps.tiles.length; i++) {
				tilePlaceX = 0;// resetting tilePlaceX position
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
			// Character movement
			if (p.keyH.upPressed == true) {// if the player moves up

				playerY -= playerSpeed;// go up by player speed amount of pixels
				g2.drawImage(jeffBack, playerX, playerY, null);
				direction = "back";
				// playMusic(0);

			} else if (p.keyH.downPressed) {

				playerY += playerSpeed;// go down by player speed amount of pixels
				g2.drawImage(jeffFront, playerX, playerY, null);
				direction = "front";
				// playMusic(0);
			} else if (p.keyH.leftPressed) {

				playerX -= playerSpeed;// go to the left by player speed amount of pixels
				g2.drawImage(jeffRight, playerX, playerY, null);
				direction = "right";
				// playMusic(0);
			} else if (p.keyH.rightPressed) {

				playerX += playerSpeed;// go to the right by player speed amount of pixels
				g2.drawImage(jeffLeft, playerX, playerY, null);
				direction = "left";
				// playMusic(0);
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
		
			if (j.isJumpscare() == true) { 
				g2.drawImage(j.getCreepyMan(), 0, 0, null); 
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}

		g2.dispose();// saves resources

	}

	public int getTileSize() {
		return tileSize;
	}

}

