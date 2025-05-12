package main;

import java.awt.event.*;
import java.io.*;
import java.util.*;
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
	public boolean upPressed, downPressed, leftPressed, rightPressed, changeMapPressed, ePressed;
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
			if (npc.collisionNpc) {
				npc.dialogue = !npc.dialogue;
			}
			ePressed = true;
		} else if (code == KeyEvent.VK_F) {
			changeMapPressed = true;
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

		mouseX = e.getX();
		mouseY = e.getY();

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
		}else {
			Items.hoveringBack = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public static boolean instructionsPressed = false;

	@Override
	public void mousePressed(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();
		mouseHolding = true;

		if (e.getX() >= inventoryBoxX && e.getX() <= inventoryBoxX + 100 && e.getY() >= inventoryBoxY
				&& e.getY() <= inventoryBoxY + 100) {
			mouseDragging = true;
		}
		if (mouseX >= instructionsX && mouseX <= instructionsX + 135 && mouseY >= instructionsY
				&& mouseY <= instructionsY + 45) {

			instructionsPressed = true;
			Items.movementPrompt = true;

		}
		if (mouseX >= backX && mouseX <= backX + 120 && mouseY >= backY && mouseY <= backY + 40) {
			Items.backPressed = true;
		} else {
			Items.backPressed = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		mouseClicked = false;
		mouseHolding = false;
		mouseDragging = false;

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
