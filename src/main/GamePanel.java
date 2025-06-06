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

/*
 * Noah Sussman, Akhilan Saravanan, and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class GamePanel extends JPanel implements Runnable {

	// These are the settings for the window
	private final int originalTileSize = 16; // 16x16 pixel tile size
	private final int scale = 3; // scales everything to appear much larger on the window
	private final int tileSize = originalTileSize * scale; // scales every tile to appear much larger on the window
															// (48x48)

	private int playerSpeed = 25;

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

	private JFrame window;

	// FPS variables
	private long frameCount = 0; // To count frames
	private long lastTime = System.nanoTime(); // Time of the last FPS calculation
	private double fps = 0; // FPS value

	// Game components
	private Tiles t;
	private Maps m;
	private Jumpscare j;

	private Player p;
	private Npc n;
	private Items it;
	private Input id;
	private MainMenu mainMenu;
	private Minigame minigame;
	private LoadingScreen ls;

	private String direction = "";// stores the direction the player is facing

	private int screenX;
	private int screenY;

	private Sound ambientAudio;
	private Sound mainMenuSound;
	private Sound footstepSound;

	BufferedImage jeffFront;
	BufferedImage jeffBack;
	BufferedImage jeffRight;
	BufferedImage jeffLeft;

	// Constructor
	public GamePanel(JFrame window) throws IOException {
		mainMenu = new MainMenu();
		minigame = new Minigame();
		it = new Items(this);
		id = new Input(this);
		m = new Maps(this);
		n = new Npc(this);
		j = new Jumpscare(this);
		t = new Tiles(this);
		ls = new LoadingScreen(this);
		p = new Player(this);

		m.setT(t);

		this.window = window;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Preferred window size
		this.setDoubleBuffered(true); // Improves rendering performance
		this.addKeyListener(p.keyH); // Recognizes key inputs
		this.addMouseMotionListener(id); // Recognizes mouse movement
		this.addMouseListener(id); // Recognize mouse clicks
		this.setFocusable(true);

		screenX = WIDTH / 2 - (tileSize / 2); // centers the player in the middle of the screen
		screenY = HEIGHT / 2 - (tileSize / 2); // centers the player in the middle of the screen

		// Background
		m.changeMap(3);
		// Find trees in the map

		// load tiles
		t.tileCreating();

		m.findTrees();

		// Load character

		p.loadCharacterImages();

		m.setP(p);
		m.setItems(it);
		m.setNpc(n);
		m.setJ(j);
		m.setT(t);
		m.setInp(id);

		it.setP(p);
		it.setInput(id);
		it.setNpc(n);
		it.setJ(j);
		it.setM(m);
		
		n.setInput(id);
		n.setItems(it);

		p.setM(m);
		p.setN(n);
		p.setIt(it);
		p.setKeyH(id);

		id.setNpc(n);
		id.setJumpscare(j);

		t.setM(m);

		id.setLs(ls);

		try {
			// https://www.youtube.com/watch?v=tmlZeYnfw7g
			ambientAudio = new Sound("src/sound/ambientAudio.wav");
			// https://www.youtube.com/watch?v=1a7kscUeItk
			mainMenuSound = new Sound("src/sound/mainMenuSound.wav");
			footstepSound = new Sound("src/sound/walkingSoundEffect.wav");

			jeffFront = ImageIO.read(new File("src/textures/charAI.png"));
			jeffBack = ImageIO.read(new File("src/textures/jeffBack.png"));
			jeffRight = ImageIO.read(new File("src/textures/jeffRight.png"));
			jeffLeft = ImageIO.read(new File("src/textures/jeffLeft.png"));
		} catch (Exception e) {
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
		if (p.disableCharacterMovement() == false) {
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
		p.collisionChecking();
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

	// where all the drawing happens
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			m.camera(g, this);// camera method
			characterImage(g);// draws the character depending on the direction

			it.car(g2, this);

			it.doctrine(g2, this);

			if (m.getCurrentMap() == 3) {
				m.drawTint(g2, this);
			}

			// Npc.text(g2);
			it.instructions(g2);
			if (id.isInstructionsPressed()) {
				it.prompts(g2);
				it.backMenu(g2);
			}

			if (m.getCurrentMap() == 5) {
				try {
					m.drawExorcismRoom(g2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!m.hasJumpscared && !m.hasDoctrined) {
				m.fade(2, 3, g2, 248, 216, 82, 48, 414, 48, 145, 126, this);
				id.setChangeMapPressed(false);
				it.setInHouse(true);
			}
			if (m.getStepCount() == -1 && it.isInHouse()) {
				n.text(g2, 5);
				it.setInHouse(false);
			}
			if (!m.hasDoctrined && m.hasJumpscared) {
				m.fade(3, 4, g2, 168, -159, 100, 100, 5550, 520, 150, 100, this);
				id.setChangeMapPressed(false);
			}
			if (m.hasDoctrined) { // exorcism room
				m.fade(4, 5, g2, 838, 216, 55, 55, 838, 216, 55, 55, this);
				id.setChangeMapPressed(false);
				p.disableCharacterMovement();
			}
			if (m.getHasFaded() == 2) {
				if (m.hasJumpscared && !m.hasDoctrined) {
					// worldX = 384;
					// worldY = 288;
				}
				if (m.hasJumpscared && m.hasDoctrined) {
					worldX = 0;
					worldY = 0;
				}
				m.setHasFaded(0);
			}

			it.insideDoctrine(g2, this);
			// Items.houseMirror(g2);

			if (m.getCurrentMap() == 5) {
				minigame.startExorcising();
				minigame.drawPoints(g2);
			}

			it.graveyard(g2, this);
			it.ghost(g2, 5100, 320, 125, 98, this);
			it.book(g2, this);

			m.nightmare(g2, this, this);

			if (m.getCurrentMap() == 5) {
				it.ghostLogic(g2, this);
			}

			p.footStepSounds();
			p.carSound();
			m.playNightmareSound();
			m.playDoctrineSound();

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
				n.text(g2, 7);
			}

			it.help(g2);
			it.credits(g2);

		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.dispose();

	}

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
