package main;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Npc {

	public boolean collisionNpc = false;

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
}
