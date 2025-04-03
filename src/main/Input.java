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
public class Input implements KeyListener {

	public int xPos = 0;
	public int yPos = 0;
	public boolean upPressed, downPressed, leftPressed, rightPressed;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();// gets the ascii value of the key pressed

		if (code == KeyEvent.VK_W) {

			upPressed = true;

		} else if (code == KeyEvent.VK_S) {

			downPressed = true;

		} else if (code == KeyEvent.VK_A) {

			leftPressed = true;

		} else if (code == KeyEvent.VK_D) {

			rightPressed = true;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();// gets the ascii value of the key pressed

		if (code == KeyEvent.VK_W) {

			upPressed = false;

		} else if (code == KeyEvent.VK_S) {

			downPressed = false;

		} else if (code == KeyEvent.VK_A) {

			leftPressed = false;

		} else if (code == KeyEvent.VK_D) {

			rightPressed = false;

		}

	}

}



