package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Npc {

	public boolean collisionNpc = false;
	boolean dialogue = false;
	Input input;

	public Npc(Input input) {
		this.input = input;
	}

	public Npc() {

	}

	public void npcs() {

		int businessManX = 550;
		int businessManY = 120;
		if (GamePanel.playerX + 32 > businessManX // Right side of hitbox is past left side of tree
				&& GamePanel.playerX < businessManX + 50 // Left side of hitbox is before right side of tree
				&& GamePanel.playerY + 72 > businessManY // Bottom side of hitbox is below top side of tree
				&& GamePanel.playerY < businessManY + 78) { // Top side of hitbox is above bottom side of tree
			collisionNpc = true;
		} else {
			collisionNpc = false;
		}
	}

	public void communicate(Graphics g2) {
		if (dialogue) {
			g2.setColor(Color.black);
			g2.fillRect(150, 300, 500, 200);
			g2.setColor(Color.white);
			g2.drawString("Hello! I'm a businessman.", 170, 350);
		}
	}
}
