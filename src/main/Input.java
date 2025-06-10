package main;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import Horror.Jumpscare;

import java.awt.*;

/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class Input implements KeyListener, MouseMotionListener, MouseListener {

	private int mouseX = 0;// the x coordinate of the cursor
	private int mouseY = 0;// the y coordinate of the cursor
	private boolean upPressed, downPressed, leftPressed, rightPressed;// movement keys that are pressed (WASD)
	private boolean changeMapPressed;// the key that is pressed when entering a new area (replaced by confirmation
										// menu)
	private boolean ePressed;// the key that is used for interactions
	private boolean cPressed;// the key that is used for entering cars
	private boolean useBookPressed;// the key that is used for entering books
	private boolean upReleased, downReleased, leftReleased, rightReleased;// evaluates to true when the movement keys
																			// have been released
	private Npc npc;// Reference to the Npc class to access Npc methods and properties
	private Items items;// Reference to the Items class to access Items methods and properties
	private Minigame minigame;// Reference to the Minigame class to access Minigame methods and properties
	private Jumpscare jumpscare;// Reference to the Jumpscare class to access Jumpscare methods and properties
	private MainMenu mainMenu;// Reference to the MainMenu class to access MainMenu methods and properties
	private LoadingScreen ls;// Reference to the LoadingScreen class to access LoadingScreen methods and
								// properties
	private Maps maps;// Reference to the Maps class to access Maps methods and properties

	private boolean isCircle;// checks if the shape detected is a circle
	private boolean isZigzag;// checks if the shape detected is a zigzag
	private boolean isTriangle;// checks if the shape detected is a triangle

	private int instructionsX = 640;// the x value of instructions menu
	private int instructionsY = 10;// the y value of instructions menu
	private int backX = 335;// the x value of back menu
	private int backY = 430;// the y value of back menu

	private boolean hoveringQuit = false;// hovering over the quit menu in the endscreen

	private boolean instructionsPressed = false;// checks if the instructions menu has been pressed
	private boolean readBook = false;// checks if the user has closed the book after opening it
	private boolean pressedQuit = false;// checks if the user has pressed the quit button in the end screen

	/**
	 * Constructor for the Input Class
	 * 
	 * @param gp
	 */
	public Input(GamePanel gp) {

		jumpscare = gp.getJ();
		items = gp.getIt();
		npc = gp.getN();
		minigame = gp.getMinigame();
		mainMenu = gp.getMainMenu();
		ls = gp.getLs();
		maps = gp.getM();

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * @purpose detects key pressed and stores true or false values into variables
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode(); // gets the ascii value of the key pressed

		if (code == KeyEvent.VK_W) {
			upPressed = true;
			upReleased = false;
		} else if (code == KeyEvent.VK_S) {
			downPressed = true;
			downReleased = false;
		} else if (code == KeyEvent.VK_A) {
			leftPressed = true;
			leftReleased = false;
		} else if (code == KeyEvent.VK_D) {
			rightPressed = true;
			rightReleased = false;
		} else if (code == KeyEvent.VK_E) {
			ePressed = true;
		} else if (code == KeyEvent.VK_B) {
			useBookPressed = true;
		} else if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);// exit the game
		} else if (code == KeyEvent.VK_SPACE) {
			npc.setTextIndex(npc.getTextIndex() + 1);// increase text index to see new text line
			npc.setOnce(false);// only reset text index once
		}
	}

	/**
	 * @purpose detects when a specific key was released
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();// gets the ascii value of the key pressed

		if (code == KeyEvent.VK_W) {// w released
			upPressed = false;
			upReleased = true;
		} else if (code == KeyEvent.VK_S) {// s released
			downPressed = false;
			downReleased = true;
		} else if (code == KeyEvent.VK_A) {// a released
			leftPressed = false;
			leftReleased = true;
		} else if (code == KeyEvent.VK_D) {// d released
			rightPressed = false;
			rightReleased = true;
		} else if (code == KeyEvent.VK_B) {// book releaed
			useBookPressed = false;
		} else if (code == KeyEvent.VK_C) {// car released
			cPressed = false;
		}

	}

	/**
	 * @purpose used to detect when the mouse is being held and dragged around
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

		mouseX = e.getX();// getting the x position
		mouseY = e.getY();// getting the y position
		if (minigame.getIsExorcising()) {// if in exorcism room
			ArrayList<Point> pointsLocal = minigame.getPoints();
			pointsLocal.add(e.getPoint());// stores all cursor movements while dragging in pointsLocal arrayList
			minigame.setPoints(pointsLocal);
		}
	}

	/**
	 * @purpose detects when the mouse is being moved (not clicking)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();// gets the x coordinate of mouse
		mouseY = e.getY();// gets the y coordinate of mouse
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 220 && mouseY <= 280) {// hovering over the play button
			mainMenu.setHoveringPlay(true);// set to true to hovering over play button
		} else {
			mainMenu.setHoveringPlay(false);// not hovering over play menu
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350) {// hovering over the help button
			mainMenu.setHoveringHelp(true);// set to true to hovering over help button
		} else {
			mainMenu.setHoveringHelp(false);// not hovering over help menu
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 360 && mouseY <= 420) {// hovering over the credits button
			mainMenu.setHoveringCredits(true);// set to true to hovering over credits button
		} else {
			mainMenu.setHoveringCredits(false);// not hovering over credits menu
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 430 && mouseY <= 490) {// hovering over the exit button
			mainMenu.setHoveringExit(true);// set to true to hovering over exit button
		} else {
			mainMenu.setHoveringExit(false);// not hovering over exit button
		}
		if (mouseX >= instructionsX && mouseX <= instructionsX + 120 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 40) {// hovering over the instructions button
			items.setHoveringInstructions(true);// set to true to hovering over the instructions button
		} else {
			items.setHoveringInstructions(false);// not hovering over the instructions button
		}
		if (mouseX >= backX && mouseX <= backX + 130 && mouseY >= backY && mouseY <= backY + 40) {// hovering over the
																									// back menu
			items.setHoveringBack(true);// set to true to hovering over the back button
		} else {
			items.setHoveringBack(false);// not hovering over the back button
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160 && mouseY <= 160 + 62) {// hovering over the movement
																							// button
			items.setHoveringMovement(true);// set to true to hovering over the movement button
		} else {
			items.setHoveringMovement(false);// not hovering over the movement button
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250 && mouseY <= 250 + 62) {// hovering over the objective
																							// button
			items.setHoveringObjective(true);// set to true to hovering over the movement button
		} else {
			items.setHoveringObjective(false);// not hovering over the objective button
		}
		if (items.isEnterBook() && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 445 && mouseY <= 445 + 40) {// hovering
																													// over
																													// the
																													// next
																													// page
																													// button
			items.setHoveringNextPage(true);// set to true to hovering over the next page button
		} else {
			items.setHoveringNextPage(false);// not hovering over the next page button
		}
		if (items.isEnterBook() && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {// hovering
																													// over
																													// the
																													// exit
																													// page
																													// button
			items.setHoveringExitPage(true);// set to true to hovering over the exit page button
		} else {
			items.setHoveringExitPage(false);// not hovering over the exit page
		}
		if (mouseX >= 225 && mouseX <= 225 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {// hovering
																														// over
																														// the
																														// yes
																														// button
			items.setHoveringYes(true);// set to true to hovering over the yes button
		} else {
			items.setHoveringYes(false);// not hovering over the yes button
		}
		if (mouseX >= 425 && mouseX <= 425 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {// hovering
																														// over
																														// no
																														// button
			items.setHoveringNo(true);// set to true to hovering over no button
		} else {
			items.setHoveringNo(false);// not hovering over the no button
		}
		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90
				&& (items.isHelpPressed() || items.isCreditsPressed())) {
			items.setHoveringX(true);// hovering over the x button in either the credits or help menu
		} else {
			items.setHoveringX(false);// not hovering over the x button
		}
		if (mouseX >= 325 && mouseX <= 325 + 130 && mouseY >= backY && mouseY <= backY + 40 && maps.isEnd()) {// hovering
																												// over
																												// the
																												// quit
																												// button
																												// in
																												// the
																												// end
																												// screen
			hoveringQuit = true;// not hovering over the quit button
		} else {
			hoveringQuit = false;// not hovering over the quit button
		}
	}

	/**
	 * @purpose detects once the mouse dragging has bene released
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (minigame.getIsExorcising()) {// in exorcism room
			ArrayList<Point> pointsLocal = minigame.getPoints();
			pointsLocal.add(e.getPoint());// stores point when released cursor
			minigame.setPoints(pointsLocal);
		}
	}

	/**
	 * @purpose detects when the user presses with their cursor (not drag)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();// gets the x cursor coordinate
		mouseY = e.getY();// gets the y cursor coordinate

		if (mouseX >= instructionsX && mouseX <= instructionsX + 135 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 45 && !mainMenu.isInMenu() /* && !ls.isLoadingScreen() */) {// hovering
																											// your
																											// cursor on
																											// the
																											// instructions
																											// button
			instructionsPressed = true;// pressed the instructions button
			items.setInstructionsPrompt(true);// open the instructions prompt
			return;
		}

		if ((items.isInstructionsPrompt() || items.isMovementPrompt() || items.isObjectivePrompts()) && mouseX >= backX
				&& mouseX <= backX + 130 && mouseY >= backY && mouseY <= backY + 40) {// hovering your cursor on the
																						// back button
			items.setBackPressed(true);// pressed the back button
			return;
		}

		if (items.isInstructionsPrompt() && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160
				&& mouseY <= 160 + 62) {// hovering your cursor on the movement button
			items.setMovementPrompt(true);// pressed the movement button
			items.setInstructionsPrompt(false);// exit instructions menu
			return;
		}

		if (items.isInstructionsPrompt() && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250
				&& mouseY <= 250 + 62) {// hovering your cursor on the objective button
			items.setObjectivePrompts(true);// pressed objective button
			items.setInstructionsPrompt(false);// exit instructions menu
			return;
		}

		if (items.isEnterBook()) {// pressed b to enter the book
			if (mouseX >= 530 && mouseX <= 680 && mouseY >= 445 && mouseY <= 485) {
				items.setPlayGif(true);// play the book flipping gif
				items.setStaticImageBook(false);// stops the static image so the GIF can work
				items.setNextPage(items.getNextPage() + 1);// go to the next page of the book
				items.playBookFlipSound();// book flipping sound
				items.timer();// how long the GIF will play for
				int maxPages = 8;// maximum number of pages you can flip in the book
				if (items.getNextPage() > maxPages) {// next page is not past the maximum pages in the book
					items.setNextPage(maxPages);// flip to the next page
					items.stopBookFlipSound();// stop the book flipping sounds
					items.setPlayGif(false);// stop the GIF
				}
			}
			readBook = true;// you have now read the book (atleast opened the book)
		}
		if (items.isEnterBook() && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {// hovering
																													// over
																													// close
																													// book
																													// button
			items.setEnterBook(false);// pressed close book to close the book
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 220 && mouseY <= 280 && mainMenu.isInMenu()) {// hovering over
																										// play button
			mainMenu.setInMenu(false);// pressed the play button (no longer in main menu)
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 430 && mouseY <= 490 && mainMenu.isInMenu()) {// hovering over
																										// exit button
			System.exit(0);// pressed exit program
		}
		if (mouseX >= 225 && mouseX <= 225 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {// hovering
																														// over
																														// confirmation
																														// menu
																														// yes
																														// pressed
			items.setYesPressed(true);// has pressed yes button
		}
		if (mouseX >= 425 && mouseX <= 425 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {// hovering
																														// over
																														// confirmation
																														// menu
																														// no
																														// pressed
			items.setNoPressed(true);// has pressed no button
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350 & mainMenu.isInMenu()) {// hovering over
																										// the help
																										// button
			items.setHelpPressed(true);// pressed the help button
		}

		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90 && items.isHelpPressed()) {// hovering over
																										// the x button
			items.setHelpPressed(false);// no longer in help menu

			if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350 && mainMenu.isInMenu()
					&& !ls.isLoadingScreen()) {// hovering over the help button
				items.setHelpPressed(true);// pressed the help button

			}
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 360 && mouseY <= 420 && mainMenu.isInMenu()) {
			{// hovering over the credits button
				items.setCreditsPressed(true);// pressed the credits button
			}
			if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90
					&& (items.isHelpPressed() || items.isCreditsPressed())) {// hovering over the x button
				items.setCreditsPressed(false);// close either the help menu or credits menu
			}
			if (mouseX >= 325 && mouseX <= 325 + 130 && mouseY >= backY && mouseY <= backY + 40 && maps.isEnd()) {// hovering
																													// over
																													// the
																													// end
																													// screen
																													// exit
																													// button
				pressedQuit = true;// pressed the exit button
			} else {
				pressedQuit = false;// did not press the exit button
			}
			if (mouseX >= 700 && mouseX <= 725 && mouseY >= 80 && mouseY <= 120 && mainMenu.isInMenu()) {// hovering
																											// over the
																											// click me
																											// button in
																											// the main
																											// menu
				mainMenu.setEasterEgg(true);// pressed the click me button
			}
		}
	}

	/**
	 * @purpose detects when the mouse that was dragging was then released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (minigame.getIsExorcising()) {//in exorcism room
			minigame.calculation();//calculates the sum of all the points of the template
			minigame.newCentroid();//centroid of the drawn shape
			minigame.calculatedResult();//check whether the shape drawn is a vertical, horizontal or neither shape

			minigame.circle();//gets all the points and area of the template circle
			isCircle = minigame.isValid(17);//the threshold of how close the drawn shape is to a perfect circle

			minigame.triangle();//gets all the points and area of the template triangle
			isTriangle = minigame.isValid(33);//the threshold of how close the drawn shape is to a perfect triangle

			minigame.zigzag();//gets all the points and area of the template zigzag
			isZigzag = minigame.isValid(15);//the threshold of how close the drawn shape is to a perfect zigzag

			if (isCircle && items.getGhostShape().equals("Circle")) {//circle properly drawn
				items.setDestroyCircle(true);//has destroyed a circle
				items.setGhostCount(items.getGhostCount() + 1);//has exorcised a ghost (or one step of exorcising the ghost)
			} else if (isTriangle && items.getGhostShape().equals("Triangle")) {//triangle properly drawn
				items.setDestroyTriangle(true);//has destroyed a triangle
				items.setGhostCount(items.getGhostCount() + 1);//has exorcised a ghost (or one step of exorcising the ghost)
			} else if (isZigzag && items.getGhostShape().equals("Zigzag")) {//zigzag properly drawn
				items.setDestroyZigzag(true);//has destroyed a zigzag
				items.setGhostCount(items.getGhostCount() + 1);//has exorcised a ghost (or one step of exorcising the ghost)
			} else if (minigame.getCurrentShape().equals("vertical") && items.getGhostShape().equals("Vertical")) {//vertical line properly drawn
				items.setDestroyVertical(true);//has destroyed a triangle
				items.setGhostCount(items.getGhostCount() + 1);//has exorcised a ghost (or one step of exorcising the ghost)
			} else if (minigame.getCurrentShape().equals("horizontal") && items.getGhostShape().equals("Horizontal")) {//horizontal line properly drawn
				items.setDestroyHorizontal(true);//has destroyed a triangle
				items.setGhostCount(items.getGhostCount() + 1);//has exorcised a ghost (or one step of exorcising the ghost)
			} else if (items.getGhostShape().equals("duoghost1")) {//the first duoghost
				if (items.isDestroyHorizontal() && items.isDestroyZigzag()) {//both shapes for duoghost1 have been drawn
					items.destroyRightGhost = true;//destroyed the right ghost      
				}
			} else if (items.getGhostShape().equals("duoghost2") && !items.destroyLeftGhost) {//the second duoghost
				if (items.isDestroyVertical() && items.isDestroyCircle()) {//both shapes for duoghost2 have been drawn
					items.destroyLeftGhost = true;//destroyed the left ghost
				}
			} else if (items.getGhostShape().equals("duoghost3") && !items.destroyLeftGhost) {//the third duoghost
				if (items.isDestroyHorizontal() && items.isDestroyCircle()) {//both shapes for duoghost3 have been drawn
					items.destroyRightGhost = true;//destroyed the right ghost
				}
			} else if (items.getGhostShape().equals("duoghost4") && !items.destroyLeftGhost) {//the fourth duoghost
				if (items.isDestroyTriangle() && items.isDestroyZigzag()) {//both shapes for duoghost4 have been drawn
					items.destroyLeftGhost = true;//destroyed the left ghost
				}
			} else if (items.getGhostShape().equals("trioghost")) {//the trio ghost
				if (items.isDestroyTriangle() && items.isDestroyZigzag() && items.isDestroyCircle()) {
					items.destroyTrioGhost = true;
				}
			} else if (items.getGhostShape().equals("bossghost")) {
				if (items.isDestroyHorizontal() && items.isDestroyZigzag() && items.isDestroyCircle()
						&& items.isDestroyVertical() && items.isDestroyTriangle()) {
					items.destroyBossGhost = true;
				}
			}

			ArrayList<Point> pointsLocal = minigame.getPoints();
			pointsLocal.clear();
			minigame.setPoints(pointsLocal);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public boolean isUpPressed() {
		return upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setJumpscare(Jumpscare j) {
		this.jumpscare = j;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public void setLs(LoadingScreen ls) {
		this.ls = ls;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public boolean isChangeMapPressed() {
		return changeMapPressed;
	}

	public void setChangeMapPressed(boolean changeMapPressed) {
		this.changeMapPressed = changeMapPressed;
	}

	public boolean isePressed() {
		return ePressed;
	}

	public void setePressed(boolean ePressed) {
		this.ePressed = ePressed;
	}

	public boolean iscPressed() {
		return cPressed;
	}

	public void setcPressed(boolean cPressed) {
		this.cPressed = cPressed;
	}

	public boolean isUseBookPressed() {
		return useBookPressed;
	}

	public void setUseBookPressed(boolean useBookPressed) {
		this.useBookPressed = useBookPressed;
	}

	public boolean isUpReleased() {
		return upReleased;
	}

	public void setUpReleased(boolean upReleased) {
		this.upReleased = upReleased;
	}

	public boolean isDownReleased() {
		return downReleased;
	}

	public void setDownReleased(boolean downReleased) {
		this.downReleased = downReleased;
	}

	public boolean isLeftReleased() {
		return leftReleased;
	}

	public void setLeftReleased(boolean leftReleased) {
		this.leftReleased = leftReleased;
	}

	public boolean isRightReleased() {
		return rightReleased;
	}

	public void setRightReleased(boolean rightReleased) {
		this.rightReleased = rightReleased;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	public boolean isReadBook() {
		return readBook;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public boolean isInstructionsPressed() {
		return instructionsPressed;
	}

	public void setInstructionsPressed(boolean instructionsPressed) {
		this.instructionsPressed = instructionsPressed;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setCPressed(boolean cPressed) {
		this.cPressed = cPressed;
	}

	public boolean isHoveringQuit() {
		return hoveringQuit;
	}

	public boolean isPressedQuit() {
		return pressedQuit;
	}

	public void setM(Maps m) {
		this.maps = m;
	}

}
