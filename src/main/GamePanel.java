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
	int playerX = 768 / 2;
	int playerY = 576 / 2;
	int worldX = 768 / 2; // world position
	int worldY = 576 / 2; // world position
	
	
	// Game Thread
	Thread gameThread; // keeps the program running until closed

	private JFrame window;

	// FPS variables
	private long frameCount = 0; // To count frames
	private long lastTime = System.nanoTime(); // Time of the last FPS calculation
	private double fps = 0; // FPS value

	// Game components
	public Tiles t;
	public Maps m;
	public Jumpscare j;
	
	public Player p;
	public Npc n;
	public Items it;
	public Input id;
	public MainMenu mainMenu;
	public Minigame minigame;
	
	
	int screenX;
	int screenY;

	// sound
	private Sound footstepSound;

	

	public GamePanel() {
	}

	// Constructor
	public GamePanel(JFrame window) throws IOException {
		
		j = new Jumpscare(this);
		it = new Items(this);
		n = new Npc(this);
		m = new Maps(this);
		t = new Tiles(this);
		
		mainMenu = new MainMenu();
		minigame = new Minigame();
		id = new Input(this);
		p = new Player(this);
		
		m.setTiles(t);
		
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
		
		//load tiles init
		t.tileCreating();
		
		m.findTrees();

		// Load character
		
		p.loadCharacterImages();

		try {
			footstepSound = new Sound("src/sound/walkingSoundEffect.wav");
			System.out.println("Sound loaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		m.setP(p);
		m.setItems(it);
		m.setNpc(n);
		m.setJ(j);
		m.setT(t);
		
		it.setP(p);
		it.setInput(id);
		it.setNpc(n);
		
		n.setInput(id);
		n.setItems(it);
		
		p.setM(m);
		p.setN(n);
		p.setIt(it);
		p.setKeyH(id);
		
	}

	// Start the game thread
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); // starts the game thread (automatically calls the run method)
	}

	@Override
	public void run() {
		System.out.println(m);
		
		
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
		if (p.disableCharacterMovement() == false) {
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
		
		System.out.println("World X,Y: " + worldX + ", " + worldY);
		System.out.println("Player.world X,Y: " + p.worldX + ", " + p.worldY);
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
		it.animation();
		
		
	}

	public void characterImage(Graphics g) throws IOException {

		BufferedImage jeffFront = ImageIO.read(new File("src/textures/charAI.png"));
		BufferedImage jeffBack = ImageIO.read(new File("src/textures/jeffBack.png"));
		BufferedImage jeffRight = ImageIO.read(new File("src/textures/jeffRight.png"));
		BufferedImage jeffLeft = ImageIO.read(new File("src/textures/jeffLeft.png"));

		BufferedImage character = null;
		if (it.visible) {
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
			if (m.currentMap != 5) {
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
			m.camera(g2, this);// camera method
			characterImage(g);// draws the character depending on the direction
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			it.car(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (it.animationFrame >= 150) {
			it.titleScreen(g2);
		}
		// Npc.text(g2);
		it.instructions(g2);
		if (Input.instructionsPressed) {
			it.prompts(g2);
			it.backMenu(g2);
		}
		/*
		 * if (j.isJumpscare()) { if (j.getOnce() == false) { // makes sound run only
		 * once j.playSound(); j.setOnce(true); } // Render the jumpscare image
		 * j.drawJumpscare(g2);
		 * 
		 * // Use a Timer to introduce a delay after rendering Timer delayTimer = new
		 * Timer(2000, new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { j.setJumpscare(false);
		 * // Reset the jumpscare state after 2 seconds ((Timer) e.getSource()).stop();
		 * // Stop the timer } }); delayTimer.setRepeats(false); // Ensure the timer
		 * only runs once delayTimer.start(); }
		 */
		
		if (m.currentMap == /*5*/ 5) {
			try {
				m.drawExorcismRoom(g2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			if (!m.hasJumpscared && !m.hasDoctrined) {
				m.fade(2, 3, g2, 248, 216, 82, 48, 414, 48, 145, 126);
				Input.changeMapPressed = false;
				it.inHouse = true;
			}
			if (m.stepCount == -1 && it.inHouse) {
				n.text(g2, 5);
				it.inHouse = false;
			}
			if (!m.hasDoctrined && m.hasJumpscared) {
				m.fade(3, 4, g2, 168, -159, 100, 100, 5550, 520, 150, 100);
				Input.changeMapPressed = false;
			}
			if (m.hasDoctrined) { // exorcism room
				m.fade(4, 5, g2, 838, 216, 55, 55, 838, 216, 55, 55);
				Input.changeMapPressed = false;
				p.disableCharacterMovement();
			}
			if (m.hasFaded == 2) {
				if (m.hasJumpscared && !m.hasDoctrined) {
					worldX = 384;
					worldY = 288;
				}
				if (m.hasJumpscared && m.hasDoctrined) {
					worldX = 0;
					worldY = 0;
				}
				m.hasFaded = 0;
			}
			// Npc.text(g2, 3);
			// Items.insideDoctrine(g2);
			// Items.houseMirror(g2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		minigame.startExorcising();
		
		minigame.drawPoints(g2);
		
		
		if (minigame.ready) {
			minigame.circle(g2);
		}
		
		
		try {
			it.doctrine(g2);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		try {
			it.graveyard(g2);
			it.ghost(g2, 5100, 320, 125, 98);
			it.book(g2, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (MainMenu.inMenu) {
			mainMenu.mainMenu(g2);
		}
		
		
		try {
			m.nightmare(g2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
}
