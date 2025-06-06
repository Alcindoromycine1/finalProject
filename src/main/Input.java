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

	private int mouseX = 0;
	private int mouseY = 0;
	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private boolean changeMapPressed;
	private boolean ePressed;
	private boolean cPressed;
	private boolean useBookPressed;
	private boolean upReleased, downReleased, leftReleased, rightReleased;
	private int mouseOffsetX = 0;
	private int mouseOffsetY = 0;
	private Npc npc;
	private Items items;
	private Minigame minigame;
	private Jumpscare jumpscare;
	private MainMenu mainMenu;
	private LoadingScreen ls;

	private boolean isCircle;
	private boolean isZigzag;
	private boolean isTriangle;

	private boolean mouseClicked = false;
	private boolean mouseHolding = false;
	private boolean mouseDragging = false;
	private int inventoryBoxX = 300;
	private int inventoryBoxY = 300;

	private int instructionsX = 640;
	private int instructionsY = 10;
	private int backX = 345;
	private int backY = 430;

	public Input(GamePanel gp) {

		jumpscare = gp.getJ();
		items = gp.getIt();
		npc = gp.getN();
		minigame = gp.getMinigame();
		mainMenu = gp.getMainMenu();
		ls = gp.getLs();

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

	@Override
	public void keyTyped(KeyEvent e) {

	}

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
		} else if (code == KeyEvent.VK_F) {
			changeMapPressed = true;
		} else if (code == KeyEvent.VK_B) {
			useBookPressed = true;
		} else if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (code == KeyEvent.VK_SPACE) {
			npc.setTextIndex(npc.getTextIndex() + 1);
		} else if (code == KeyEvent.VK_C) {
			cPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();// gets the ascii value of the key pressed

		if (code == KeyEvent.VK_W) {
			upPressed = false;
			upReleased = true;
		} else if (code == KeyEvent.VK_S) {
			downPressed = false;
			downReleased = true;
		} else if (code == KeyEvent.VK_A) {
			leftPressed = false;
			leftReleased = true;
		} else if (code == KeyEvent.VK_D) {
			rightPressed = false;
			rightReleased = true;
		} else if (code == KeyEvent.VK_B) {
			useBookPressed = false;
		} else if (code == KeyEvent.VK_C) {
			cPressed = false;
		} 

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();
		if (minigame.getIsExorcising()) {
			ArrayList<Point> pointsLocal = minigame.getPoints();
			pointsLocal.add(e.getPoint());
			minigame.setPoints(pointsLocal);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 220 && mouseY <= 280) {
			mainMenu.setHoveringPlay(true);
		} else {
			mainMenu.setHoveringPlay(false);
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350) {
			mainMenu.setHoveringHelp(true);
		} else {
			mainMenu.setHoveringHelp(false);
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 360 && mouseY <= 420) {
			mainMenu.setHoveringCredits(true);
		} else {
			mainMenu.setHoveringCredits(false);
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 430 && mouseY <= 490) {
			mainMenu.setHoveringExit(true);
		} else {
			mainMenu.setHoveringExit(false);
		}
		if (mouseX >= instructionsX && mouseX <= instructionsX + 120 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 40) {
			items.setHoveringInstructions(true);
		} else {
			items.setHoveringInstructions(false);
		}
		if (mouseX >= backX && mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			items.setHoveringBack(true);
		} else {
			items.setHoveringBack(false);
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160 && mouseY <= 160 + 62) {
			items.setHoveringMovement(true);
		} else {
			items.setHoveringMovement(false);
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250 && mouseY <= 250 + 62) {
			items.setHoveringKeybind(true);
		} else {
			items.setHoveringKeybind(false);
		}
		if (items.isEnterBook() && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 445 && mouseY <= 445 + 40) {
			items.setHoveringNextPage(true);
		} else {
			items.setHoveringNextPage(false);
		}
		if (items.isEnterBook() && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {
			items.setHoveringExitPage(true);
		} else {
			items.setHoveringExitPage(false);
		}
		if (mouseX >= 225 && mouseX <= 225 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {
			items.setHoveringYes(true);
		} else {
			items.setHoveringYes(false);
		}
		if (mouseX >= 425 && mouseX <= 425 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {
			items.setHoveringNo(true);
		} else {
			items.setHoveringNo(false);
		}
		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90
				&& (items.isHelpPressed() || items.isCreditsPressed())) {
			items.setHoveringX(true);
		} else {
			items.setHoveringX(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (minigame.getIsExorcising()) {
			ArrayList<Point> pointsLocal = minigame.getPoints();
			pointsLocal.add(e.getPoint());
			minigame.setPoints(pointsLocal);
		}
	}

	private boolean instructionsPressed = false;

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseHolding = true;

		if (mouseX >= inventoryBoxX && mouseX <= inventoryBoxX + 100 && mouseY >= inventoryBoxY
				&& mouseY <= inventoryBoxY + 100) {
			mouseDragging = true;
		}

		if (mouseX >= instructionsX && mouseX <= instructionsX + 135 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 45) {
			instructionsPressed = true;
			items.setInstructionsPrompt(true);
			return;
		}

		if ((items.isInstructionsPrompt() || items.isMovementPrompt() || items.isKeybindPrompts()) && mouseX >= backX
				&& mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			items.setBackPressed(true);
			return;
		}

		if (items.isInstructionsPrompt() && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160
				&& mouseY <= 160 + 62) {
			items.setMovementPrompt(true);
			items.setInstructionsPrompt(false);
			return;
		}

		if (items.isInstructionsPrompt() && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250
				&& mouseY <= 250 + 62) {
			items.setKeybindPrompts(true);
			items.setInstructionsPrompt(false);
			return;
		}

		if (items.isEnterBook()) {
			if (mouseX >= 530 && mouseX <= 680 && mouseY >= 445 && mouseY <= 485) {
				items.setPlayGif(true);
				items.setStaticImageBook(false);
				items.setNextPage(items.getNextPage() + 1);
				items.playBookFlipSound();
				items.timer();
				int maxPages = 5;
				if (items.getNextPage() > maxPages) {
					items.setNextPage(maxPages);
					items.stopBookFlipSound();
				}
			}
		}
		if (items.isEnterBook() && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {
			items.setEnterBook(false);
			;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 220 && mouseY <= 280 && mainMenu.isInMenu()) {
			mainMenu.setInMenu(false);
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 430 && mouseY <= 490 && mainMenu.isInMenu()) {
			System.exit(0);
		}
		if (mouseX >= 225 && mouseX <= 225 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {
			items.setYesPressed(true);
		}
		if (mouseX >= 425 && mouseX <= 425 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.isInConfirmation()) {
			items.setNoPressed(true);
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350 & mainMenu.isInMenu()) {
			items.setHelpPressed(true);
		}

		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90 && items.isHelpPressed()) {
			items.setHelpPressed(false);

			if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350 && mainMenu.isInMenu()
					&& !ls.isLoadingScreen()) {
				items.setHelpPressed(true);

			}
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 360 && mouseY <= 420 && mainMenu.isInMenu()) {
			items.setCreditsPressed(true);
		}
		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90
				&& (items.isHelpPressed() || items.isCreditsPressed())) {
			items.setCreditsPressed(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked = false;
		mouseHolding = false;
		mouseDragging = false;
		if (minigame.getIsExorcising()) {
			minigame.calculation();
			minigame.newCentroid();
			minigame.calculatedResult();

			minigame.circle();
			isCircle = minigame.isValid(15);

			minigame.triangle();
			isTriangle = minigame.isValid(25);

			minigame.zigzag();
			isZigzag = minigame.isValid(15);

			if (isCircle && items.getGhostShape().equals("Circle")) {
				items.setDestroyCircle(true);
				items.setGhostCount(items.getGhostCount() + 1);
			} else if (isTriangle && items.getGhostShape().equals("Triangle")) {
				items.setDestroyTriangle(true);
				items.setGhostCount(items.getGhostCount() + 1);
			} else if (isZigzag && items.getGhostShape().equals("Zigzag")) {
				items.setDestroyZigzag(true);
				items.setGhostCount(items.getGhostCount() + 1);
			} else if (minigame.getCurrentShape().equals("vertical") && items.getGhostShape().equals("Vertical")) {
				items.setDestroyVertical(true);
				items.setGhostCount(items.getGhostCount() + 1);
			} else if (minigame.getCurrentShape().equals("horizontal") && items.getGhostShape().equals("Horizontal")) {
				items.setDestroyHorizontal(true);
				items.setGhostCount(items.getGhostCount() + 1);
			} else if (items.getGhostShape().equals("duoghost1")) {
				if (items.isDestroyHorizontal() && items.isDestroyZigzag()) {
					items.setDestroyDuoGhost(true);
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

}
