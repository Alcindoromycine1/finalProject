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

	private Input input;
	private Items items;
	private Maps maps;

	private int playerX;
	private int playerY;

	private int worldX;
	private int worldY;

	public Npc(GamePanel gp) {
		input = gp.getId();
		items = gp.getIt();
		maps = gp.getM();

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

	public void doctor(Graphics2D g2, GamePanel gp) throws IOException {

		int doctorX = 480 - gp.getWorldX();
		int doctorY = 260 - gp.getWorldY();

		BufferedImage doctor = null;
		doctor = ImageIO.read(new File("src/textures/doctor.png"));
		g2.drawImage(doctor, doctorX, doctorY, null);

	}

	private int textIndex = 0;

	public void text(Graphics2D g2, int list) {
		if (list == 2) {
			String textGhostGraveyard[] = { "AHHHHH! Is that a... ", "Don't kill me please!",
					"I can help you kill us if you just leave me be.", "Just press B to open that book above you.",
					"The book will teach you all the knowledge you need to know to get rid of us." };
			if (textIndex < textGhostGraveyard.length) {
				textBubble(g2, textGhostGraveyard[textIndex]);
			} else {
				items.setFirstTime(false);
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
				maps.setDoneNightmare(true);
			}
		} else if (list == 7) {
			String textCarDestroyed[] = { "My brain is playing tricks on me again!",
					"I crashed the car because of this." };
			if (textIndex < textCarDestroyed.length) {
				textBubble(g2, textCarDestroyed[textIndex]);
			} else {
				items.setCarSceneDone(true);
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

	public int getTextIndex() {
		return textIndex;
	}
	
	public void setTextIndex(int textIndex) {
		this.textIndex = textIndex;
	}

}
