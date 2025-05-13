package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Npc {

	public static boolean collisionNpc = false;
	public static boolean dialogue = false;
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

	static int textIndex = 0;

	public static void text(Graphics2D g2) {

		String textBadGuy[] = {
				"Hehehehe! I'm back.", "I'm going to slash your tires."};
		if (textIndex < textBadGuy.length) {
			textBubble(g2, textBadGuy[textIndex]);
		}
	}

	public static void textBubble(Graphics2D g2, String dialogue) {
		final int maxTextLength = 62;// max number of characters per line
		final int lineSpacing = 30;// spacing between lines
		final int textX = 90;// x position of text
		final int textY = 445;// y position of text
		
		Font bubbleFont = new Font("Arial", Font.PLAIN, 20);

		g2.setColor(Color.white);
		g2.fillRoundRect(80, 410, 620, 150, 10, 10);
		g2.setColor(Color.black);
		g2.drawRoundRect(80, 410, 620, 150, 10, 10);
		g2.drawString("Press Spacebar To Continue", 532, 550);
		g2.setFont(bubbleFont);

		int lineNumber = 0;// indicates what line the text is on
		for (int i = 0; i < dialogue.length(); i += maxTextLength) {
			if (lineNumber >= 4) {
				return;// stops drawing the bubble if there is no more text
			}
			String line = dialogue.substring(i, Math.min(i + maxTextLength, dialogue.length()));// breaks up the text
																								// into smaller pieces
																								// so that there are no
																								// excess characters
																								// that don't fit on the
																								// screen
			g2.drawString(line, textX, textY + lineSpacing * lineNumber);// draws the text on the screen (line number
																			// changes what line the text is drawn on)
			lineNumber++;
		}
	}

}
