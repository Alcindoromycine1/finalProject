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
	public boolean upPressed, downPressed, leftPressed, rightPressed, changeMapPressed;
	public boolean upReleased, downReleased, leftReleased, rightReleased;
	public int mouseOffsetX = 0;
	public int mouseOffsetY = 0;
	Npc npc;

	public Input(Npc npc) {

		this.npc = npc;

	}
	
	public Input() {
		
		this.npc = new Npc(this);
		
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
		} else if (code == KeyEvent.VK_F) {
			changeMapPressed = true;
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

	@Override
	public void mouseMoved(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();
		mouseClicked = true;
		mouseHolding = true;
		if (e.getX() >= inventoryBoxX && e.getX() <= inventoryBoxX + 100 && e.getY() >= inventoryBoxY
				&& e.getY() <= inventoryBoxY + 100) {
			mouseDragging = true;
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
