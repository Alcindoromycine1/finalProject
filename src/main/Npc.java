package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Npc {

	public boolean collisionNpc = false;
	public boolean dialogue = false;

	Input input;
	Items items;
	Maps maps;

	private int playerX;
	private int playerY;

	private int worldX;
	private int worldY;

	public Npc(GamePanel gp) {
		input = gp.id;
		items = gp.it;
		maps = gp.m;

		playerX = gp.getPlayerX();
		playerY = gp.getPlayerY();

		worldX = gp.getWorldX();
		worldY = gp.getWorldY();
	}

	public void updateNpcValues(int playerX, int playerY, int worldX, int worldY) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.worldX = worldX;
		this.worldY = worldY;
	}

	public void doctor(Graphics2D g2) throws IOException {

		int doctorX = 480 - worldX;
		int doctorY = 260 - worldY;

		BufferedImage doctor = null;
		doctor = ImageIO.read(new File("src/textures/doctor.png"));
		g2.drawImage(doctor, doctorX, doctorY, null);

	}

	public void npcs() {

		int businessManX = 550;
		int businessManY = 120;
		if (playerX + 32 > businessManX // Right side of hitbox is past left side of tree
				&& playerX < businessManX + 50 // Left side of hitbox is before right side of tree
				&& playerY + 72 > businessManY // Bottom side of hitbox is below top side of tree
				&& playerY < businessManY + 78) { // Top side of hitbox is above bottom side of tree
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

	int textIndex = 0;

	public void text(Graphics2D g2, int list) {

		if (list == 1) {
			String textBadGuy[] = { "Hehehehe! I'm back.", "I'm going to slash your tires." };
			if (textIndex < textBadGuy.length) {
				textBubble(g2, textBadGuy[textIndex]);
			}
		} else if (list == 2) {
			String textGhostGraveyard[] = { "AHHHHH! Is that a... ", "Don't kill me please!",
					"I can help you kill us if you just leave me be.", "Just press B to open that book above you.",
					"The book will teach you all the knowledge you need to know to get rid of us." };
			if (textIndex < textGhostGraveyard.length) {
				textBubble(g2, textGhostGraveyard[textIndex]);
			} else {
				items.firstTime = false;
			}
		} else if (list == 3) {
			String textDoctor[] = { "I don't have much time left.",
					"You must find the book with all the knowledge to dispell the  thing that hurt me. It is located in the graveyard.",
					"Then, go to the doctrine, and remove the ...", "*The doctor has passed away*" };
			if (textIndex < textDoctor.length) {
				textBubble(g2, textDoctor[textIndex]);
			}
		} else if (list == 4) {
			String textDoctrineGhost[] = { "Don't hurt me. Please leave me alone.",
					"If you must, go inside the door at the end of this path! To   get rid of my kind." };
			if (textIndex < textDoctrineGhost.length) {
				textBubble(g2, textDoctrineGhost[textIndex]);
			}
		} else if (list == 5) {
			String textJeff[] = { "What is this place?", "I'm tired, I think I'll take a nap here." };
			if (textIndex < textJeff.length) {
				textBubble(g2, textJeff[textIndex]);
			}
		} else if (list == 6) {
			String textNightmare[] = { "What am I doing here?", "What is that thing on the table?",
					"What is this doctor doing to it?", "Is that one of those evil AI?",
					"This can't be real, it's just another hallucination." };
			if (textIndex < textNightmare.length) {
				textBubble(g2, textNightmare[textIndex]);
			} else {
				maps.doneNightmare = true;
			}
		}
	}

	public void textBubble(Graphics2D g2, String dialogue) {
		final int maxTextLength = 62;// max number of characters per line
		final int lineSpacing = 30;// spacing between lines
		final int textX = 90;// x position of text
		final int textY = 445;// y position of text

		Font bubbleFont = new Font("Arial", Font.PLAIN, 20);

		g2.setColor(Color.white);
		g2.fillRoundRect(80, 410, 620, 150, 10, 10);
		g2.setColor(Color.black);
		g2.drawRoundRect(80, 410, 620, 150, 10, 10);
		g2.setFont(new Font("Arial", Font.PLAIN, 13));
		g2.drawString("Press Spacebar To Continue", 485, 550);
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

	public void setInput(Input input) {
		this.input = input;
	}

	public void setItems(Items items) {
		this.items = items;
	}

}
