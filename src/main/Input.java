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
	public boolean upPressed, downPressed, leftPressed, rightPressed, changeMapPressed, ePressed, useBookPressed;
	public boolean upReleased, downReleased, leftReleased, rightReleased;
	public int mouseOffsetX = 0;
	public int mouseOffsetY = 0;
	Npc npc;
	Items i;

	public Input(Npc npc) {

		this.npc = npc;

	}

	public Input() {

		// this.npc = new Npc(this);
		this.i = new Items(this);

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
			if (Npc.collisionNpc) {
				Npc.dialogue = !Npc.dialogue;
			}
			ePressed = true;
		} else if (code == KeyEvent.VK_F) {
			changeMapPressed = true;
		} else if (code == KeyEvent.VK_B) {
			useBookPressed = true;
		} else if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (code == KeyEvent.VK_SPACE) {
			Npc.textIndex++;
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
		Minigame.startExorcising();
		mouseX = e.getX();
		mouseY = e.getY();
		if (Minigame.isExorcising) {
			Minigame.points.add(e.getPoint());
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

		if (mouseX >= instructionsX && mouseX <= instructionsX + 120 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 40) {
			Items.hoveringInstructions = true;
		} else {
			Items.hoveringInstructions = false;
		}
		if (mouseX >= backX && mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			Items.hoveringBack = true;
		} else {
			Items.hoveringBack = false;
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160 && mouseY <= 160 + 62) {
			Items.hoveringMovement = true;
		} else {
			Items.hoveringMovement = false;
		}

		if (mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250 && mouseY <= 250 + 62) {
			Items.hoveringKeybind = true;
		} else {
			Items.hoveringKeybind = false;
		}
		if (Items.enterBook && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 445 && mouseY <= 445 + 40) {
			Items.hoveringNextPage = true;
		} else {
			Items.hoveringNextPage = false;
		}
		if (Items.enterBook && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {
			Items.hoveringExitPage = true;
		} else {
			Items.hoveringExitPage = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Minigame.startExorcising();
		if (Minigame.isExorcising) {
			Minigame.points.add(e.getPoint());
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
			Items.instructionsPrompt = true;
			return;
		}

		if ((Items.instructionsPrompt || Items.movementPrompt || Items.keybindPrompts) && mouseX >= backX
				&& mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			Items.backPressed = true;
			return;
		}

		if (Items.instructionsPrompt && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 160 && mouseY <= 160 + 62) {
			Items.movementPrompt = true;
			Items.instructionsPrompt = false;
			return;
		}

		if (Items.instructionsPrompt && mouseX >= 305 && mouseX <= 305 + 195 && mouseY >= 250 && mouseY <= 250 + 62) {
			Items.keybindPrompts = true;
			Items.instructionsPrompt = false;
			return;
		}

		if (Items.enterBook) {
			if (mouseX >= 530 && mouseX <= 680 && mouseY >= 445 && mouseY <= 485) {
				Items.playGif = true;
				Items.staticImageBook = false;
				Items.nextPage++;
				Jumpscare.timer();
				int maxPages = 5;
				if (Items.nextPage > maxPages) {
					Items.nextPage = maxPages;
				}
			}
		}
		if (Items.enterBook && mouseX >= 530 && mouseX <= 530 + 150 && mouseY >= 100 && mouseY <= 100 + 40) {
			Items.enterBook = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		mouseClicked = false;
		mouseHolding = false;
		mouseDragging = false;
		
		if (Minigame.isExorcising) {
			Minigame.calculation();
			Minigame.newCentroid();
			Minigame.calculatedResult();
			Minigame.ready = true;
			System.out.println(Minigame.isValid(20));
		}
		Minigame.stopExorcising();
		Minigame.points.clear();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Minigame.points.clear();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Minigame.points.clear();
	}

}
