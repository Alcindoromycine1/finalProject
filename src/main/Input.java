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

	public int mouseX = 0;
	public int mouseY = 0;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean changeMapPressed;
	public boolean ePressed;
	public boolean cPressed;
	public boolean useBookPressed;
	public boolean upReleased, downReleased, leftReleased, rightReleased;
	public int mouseOffsetX = 0;
	public int mouseOffsetY = 0;
	Npc npc;
	Items items;
	Minigame minigame;
	Jumpscare jumpscare;
	MainMenu mainMenu;

	public Input(GamePanel gp) {

		jumpscare = gp.j;
		items = gp.it;
		npc = gp.n;
		minigame = gp.minigame;
		mainMenu = gp.mainMenu;

	}

	public void setJumpscare(Jumpscare j) {
		this.jumpscare = j;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
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
			if (npc.collisionNpc) {
				npc.dialogue = !npc.dialogue;
			}
			ePressed = true;
		} else if (code == KeyEvent.VK_F) {
			changeMapPressed = true;
		} else if (code == KeyEvent.VK_B) {
			useBookPressed = true;
		} else if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (code == KeyEvent.VK_SPACE) {
			npc.textIndex++;
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

		}

	}

	public boolean mouseClicked = false;
	public boolean mouseHolding = false;
	public boolean mouseDragging = false;
	public int inventoryBoxX = 300;
	public int inventoryBoxY = 300;

	@Override
	public void mouseDragged(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();
		if (minigame.isExorcising) {
			minigame.points.add(e.getPoint());
		}
	}

	public int instructionsX = 640;
	public int instructionsY = 10;
	public int backX = 345;
	public int backY = 430;

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 220 && mouseY <= 280) {
			mainMenu.hoveringPlay = true;
		} else {
			mainMenu.hoveringPlay = false;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350) {
			mainMenu.hoveringHelp = true;
		} else {
			mainMenu.hoveringHelp = false;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 360 && mouseY <= 420) {
			mainMenu.hoveringCredits = true;
		} else {
			mainMenu.hoveringCredits = false;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 430 && mouseY <= 490) {
			mainMenu.hoveringExit = true;
		} else {
			mainMenu.hoveringExit = false;
		}
		if (mouseX >= instructionsX && mouseX <= instructionsX + 120 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 40) {
			items.hoveringInstructions = true;
		} else {
			items.hoveringInstructions = false;
		}
		if (mouseX >= backX && mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			items.hoveringBack = true;
		} else {
			items.hoveringBack = false;
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160 && mouseY <= 160 + 62) {
			items.hoveringMovement = true;
		} else {
			items.hoveringMovement = false;
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250 && mouseY <= 250 + 62) {
			items.hoveringKeybind = true;
		} else {
			items.hoveringKeybind = false;
		}
		if (items.enterBook && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 445 && mouseY <= 445 + 40) {
			items.hoveringNextPage = true;
		} else {
			items.hoveringNextPage = false;
		}
		if (items.enterBook && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {
			items.hoveringExitPage = true;
		} else {
			items.hoveringExitPage = false;
		}
		if (mouseX >= 225 && mouseX <= 225 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.inConfirmation) {
			items.hoveringYes = true;
		} else {
			items.hoveringYes = false;
		}
		if (mouseX >= 425 && mouseX <= 425 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.inConfirmation) {
			items.hoveringNo = true;
		} else {
			items.hoveringNo = false;
		}
		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90 && items.helpPressed) {
			items.hoveringX = true;
		} else {
			items.hoveringX = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (minigame.isExorcising) {
			minigame.points.add(e.getPoint());
		}
	}

	public static boolean instructionsPressed = false;

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
			Input.instructionsPressed = true;
			items.instructionsPrompt = true;
			return;
		}

		if ((items.instructionsPrompt || items.movementPrompt || items.keybindPrompts) && mouseX >= backX
				&& mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			items.backPressed = true;
			return;
		}

		if (items.instructionsPrompt && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160 && mouseY <= 160 + 62) {
			items.movementPrompt = true;
			items.instructionsPrompt = false;
			return;
		}

		if (items.instructionsPrompt && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250 && mouseY <= 250 + 62) {
			items.keybindPrompts = true;
			items.instructionsPrompt = false;
			return;
		}

		if (items.enterBook) {
			if (mouseX >= 530 && mouseX <= 680 && mouseY >= 445 && mouseY <= 485) {
				items.playGif = true;
				items.staticImageBook = false;
				items.nextPage++;
				jumpscare.timer();
				int maxPages = 5;
				if (items.nextPage > maxPages) {
					items.nextPage = maxPages;
				}
			}
		}
		if (items.enterBook && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {
			items.enterBook = false;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 220 && mouseY <= 280 && mainMenu.inMenu) {
			mainMenu.inMenu = false;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 430 && mouseY <= 490 && mainMenu.inMenu) {
			System.exit(0);
		}
		if (mouseX >= 225 && mouseX <= 225 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.inConfirmation) {
			items.yesPressed = true;
		}
		if (mouseX >= 425 && mouseX <= 425 + 130 && mouseY >= 355 && mouseY <= 355 + 45 && items.inConfirmation) {
			items.noPressed = true;
		}
		if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350) {
			items.helpPressed = true;
		}

		if (mouseX >= 685 && mouseX <= 715 && mouseY >= 60 && mouseY <= 90 && items.helpPressed) {
			items.helpPressed = false;

			if (mouseX >= 245 && mouseX <= 525 && mouseY >= 290 && mouseY <= 350 && mainMenu.inMenu
					&& !LoadingScreen.loadingScreen) {
				items.helpPressed = true;

			}
			if (mouseX >= 245 && mouseX <= 525 && mouseY >= 360 && mouseY <= 420 && mainMenu.inMenu) {
				items.creditsPressed = true;
			}

		}
	}

	boolean isCircle;
	boolean isZigzag;
	boolean isTriangle;

	@Override
	public void mouseReleased(MouseEvent e) {

		mouseClicked = false;
		mouseHolding = false;
		mouseDragging = false;
		if (minigame.isExorcising) {
			minigame.calculation();
			minigame.newCentroid();
			minigame.calculatedResult();

			minigame.circle();
			isCircle = minigame.isValid(15);

			minigame.triangle();
			isTriangle = minigame.isValid(25);

			minigame.zigzag();
			isZigzag = minigame.isValid(15);

			if (isCircle && items.ghostShape.equals("Circle")) {
				items.destroyCircle = true;
				items.ghostCount++;
			} else if (isTriangle && items.ghostShape.equals("Triangle")) {
				items.destroyTriangle = true;
				items.ghostCount++;
			} else if (isZigzag && items.ghostShape.equals("Zigzag")) {
				items.destroyZigzag = true;
				items.ghostCount++;
			} else if (minigame.currentShape.equals("vertical") && items.ghostShape.equals("Vertical")) {
				items.destroyVertical = true;
				items.ghostCount++;
			} else if (minigame.currentShape.equals("horizontal") && items.ghostShape.equals("Horizontal")) {
				items.destroyHorizontal = true;
				items.ghostCount++;
			} else if (items.ghostShape.equals("duoghost1")) {
				if (items.destroyHorizontal && items.destroyZigzag) {
					items.destroyDuoGhost = true;
				}
			}
			minigame.points.clear();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
