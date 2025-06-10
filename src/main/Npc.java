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
import interfaces.ReadFromFile;


public class Npc implements ReadFromFile {

	private Input input;
	private Items items;
	private Maps maps;

	private int playerX;
	private int playerY;

	private int worldX;
	private int worldY;

	private boolean once = false; // used to check if the player has done things

	// dialogue sounds
	private Sound gg1;
	private Sound gg2;
	private Sound gg3;
	private Sound gg4;
	private Sound gg5;
	private Sound gd1;
	private Sound gd2;
	private Sound d1;
	private Sound d2;
	private Sound d3;
	private Sound ghosta;
	private Sound ghostb;
	private Sound h1;
	private Sound h2;
	private Sound n1;
	private Sound n2;
	private Sound n3;
	private Sound n4;
	private Sound n5;
	private boolean surprisedText;//when jeff finishes speaking in the exorcism room

	/**
	 * Constructor for the Npc Class
	 * @param gp
	 */
	public Npc(GamePanel gp) {
		input = gp.getId();
		items = gp.getIt();
		maps = gp.getM();

		input = gp.id;
		items = gp.it;
		maps = gp.m;

		playerX = gp.getPlayerX();
		playerY = gp.getPlayerY();

		worldX = gp.getWorldX();
		worldY = gp.getWorldY();


		readFile();

	}

	/**
	 * @purpose loading the different audio voice acting lines
	 */
	@Override
	public void readFile() {
		// ghost graveyard sounds
		gg1 = new Sound("sound/gg1.wav");
		gg2 = new Sound("sound/gg2.wav");
		gg3 = new Sound("sound/gg3.wav");
		gg4 = new Sound("sound/gg4.wav");
		gg5 = new Sound("sound/gg5.wav");

		// ghost doctrine sounds
		gd1 = new Sound("sound/gd1.wav");
		gd2 = new Sound("sound/gd2.wav");

		// doctor sounds
		d1 = new Sound("sound/d1.wav");
		d2 = new Sound("sound/d2.wav");
		d3 = new Sound("sound/d3.wav");

		// exorcism ghost sounds
		ghosta = new Sound("sound/ghosta.wav");
		ghostb = new Sound("sound/ghostb.wav");

		// inside house sounds
		h1 = new Sound("sound/h1.wav");
		h2 = new Sound("sound/h2.wav");

		// nightmare sounds
		n1 = new Sound("sound/n1.wav");
		n2 = new Sound("sound/n2.wav");
		n3 = new Sound("sound/n3.wav");
		n4 = new Sound("sound/n4.wav");
		n5 = new Sound("sound/n5.wav");
	}

	/**
	 * @purpose update the player and world positions
	 * @param playerX
	 * @param playerY
	 * @param worldX
	 * @param worldY
	 */
	public void updateNpcValues(int playerX, int playerY, int worldX, int worldY) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.worldX = worldX;
		this.worldY = worldY;
	}

	/**
	 * @purpose doctor graphics
	 * @param g2
	 * @param gp
	 * @throws IOException
	 */
	public void doctor(Graphics2D g2, GamePanel gp) throws IOException {

		int doctorX = 550 - gp.getWorldX();
		int doctorY = 170 - gp.getWorldY();

		BufferedImage doctor = null;
		doctor = ImageIO.read(new File("textures/doctor.png"));
		g2.drawImage(doctor, doctorX, doctorY, null);

	}

	private int textIndex = 0;// what index in the array of text you are at

	/**
	 * @purpose voice acting lines and text lines are called into the textBubble
	 *          method
	 * @param g2
	 * @param list
	 */
	public void text(Graphics2D g2, int list) {
		if (list == 2) {// graveyard text
			String textGhostGraveyard[] = { "AHHHHH! Is that a... ", "Don't kill me please!",
					"I can help you kill us if you just leave me be.", "Just press B to open that book above you.",
					"The book will teach you all the knowledge you need to know to get rid of us." };
			if (textIndex == 0) {// play voice of 1st line
				if (!gg1.isPlaying() && !once) {
					gg1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {// play voice of 2nd line
				gg1.stop();
				if (!gg2.isPlaying() && !once) {
					gg2.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 2) {// play voice of 2nd line
				gg2.stop();
				if (!gg3.isPlaying() && !once) {
					gg3.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 3) {// play voice of 3rd line
				gg3.stop();
				if (!gg4.isPlaying() && !once) {
					gg4.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 4) {// play voice of 4th line
				gg4.stop();
				if (!gg5.isPlaying() && !once) {
					gg5.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textGhostGraveyard.length) {// all the text in the array
				textBubble(g2, textGhostGraveyard[textIndex]);// display the lines
			} else {
				items.setFirstTime(false);// only happens once
				gg5.stop();// stop voice acting
			}
		} else if (list == 3) {// doctor dying text
			String textDoctor[] = { "I don't have much time left.",
					"You must find the book with all the knowledge to dispell the  thing that hurt me. It is located in the graveyard.",
					"Then, go to the doctrine, and remove the ...", "*The doctor has passed away*" };
			if (textIndex == 0) {// play 1st voice line
				if (!d1.isPlaying() && !once) {
					d1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {// play 2nd voice line
				d1.stop();
				if (!d2.isPlaying() && !once) {
					d2.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 2) {// play 3rd voice line
				d2.stop();
				if (!d3.isPlaying() && !once) {
					d3.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 3) {// play 4th voice line
				d3.stop();
			}
			if (textIndex < textDoctor.length) {// all the text in the array
				textBubble(g2, textDoctor[textIndex]);// displays the text

			} else {
				maps.setDoneDoctorDead(true);// used for blocking areas
			}
		} else if (list == 4) {// graveyard ghost text
			String textDoctrineGhost[] = { "NO Don't hurt me! Please leave me alone!",
					"If you must, go inside the door at the end of this path and   get rid of my kind." };
			if (textIndex == 0) {// play 1st voice line
				if (!gd1.isPlaying() && !once) {
					gd1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {// play 2nd voice line
				gd1.stop();
				if (!gd2.isPlaying() && !once) {
					gd2.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textDoctrineGhost.length) {// all the text in the array
				textBubble(g2, textDoctrineGhost[textIndex]);// display the text
			} else {
				items.setDoingDoctrineGhost(false);
				gd2.stop();// stop voice acting
				items.setDoneDoctrineGhost(true);
			}
		} else if (list == 5) {// house entrance text
			String textJeff[] = { "What is this place?", "I'm tired, I think I'll take a nap here." };
			if (textIndex == 0) {// play 1st voice line
				if (!h1.isPlaying() && !once) {
					h1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {// play 2nd voice line
				h1.stop();
				if (!h2.isPlaying() && !once) {
					h2.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textJeff.length) {// all the text in the array
				textBubble(g2, textJeff[textIndex]);// display the text
			} else {
				h2.stop();// stop voice acting
			}
		} else if (list == 6) {// nightmare text
			String textNightmare[] = { "What am I doing here?", "What is that thing on the table?",
					"What is this doctor doing to it?", "Is that one of those evil AI?",
					"This can't be real, it's just another hallucination." };
			if (textIndex == 0) {// play 1st voice line
				if (!n1.isPlaying() && !once) {
					n1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {// play 2nd voice line
				n1.stop();
				if (!n2.isPlaying() && !once) {
					n2.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 2) {// play 3rd voice line
				n2.stop();
				if (!n3.isPlaying() && !once) {
					n3.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 3) {// play 4th voice line
				n3.stop();
				if (!n4.isPlaying() && !once) {
					n4.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 4) {// play 5th voice line
				n4.stop();
				if (!n5.isPlaying() && !once) {
					n5.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textNightmare.length) {// all the lines in the array
				textBubble(g2, textNightmare[textIndex]);// display the text
			} else {
				maps.setDoneNightmare(true);// nightmare is done
				n5.stop();// stop voice acting
			}
		} else if (list == 7) {// after jumpscare text
			String textCarDestroyed[] = { "My brain is playing tricks on me again!",
					"I crashed the car because of this." };
			if (textIndex < textCarDestroyed.length) {// all the lines in the array
				textBubble(g2, textCarDestroyed[textIndex]);// display the text
			} else {
				items.setCarSceneDone(true);// car scene is done after text is complete
			}
		} else if (list == 8) {// in exorcism room
			String textHumanTransformation[] = { "What...", "Why do the ghosts look like humans?",
					"You're like all the other A.I's.", "What are you talking about? I'm not an A.I." };
			if (textIndex < textHumanTransformation.length) {// all the lines in the array
				textBubble(g2, textHumanTransformation[textIndex]);// display the text
			} else {
				items.setLevel(-1);
				maps.setTriggerTransition(true);// trigger the transition to the mirror scene
			}
		} else if (list == 9) {
			String surprisedJeff[] = { "Wait, that's me?", "How is this possible? I thought I was a human.",
					"I just realized something.", "Those ghosts were humans the whole time.",
					"In the doctrine, when I exorcised that last ghost, my hardware must have malfunctioned.",
					"This is why I saw the ghost for what it truly was; a human.", "This means I'm an A.I.",
					"And that was suppose to be the last of the ghosts. Meaning I  just exorcised the last human on this planet.",
					"I was the monster all along.", "That doctor that I saw in this house DECEIVED me.", "*sob*" };
			if (textIndex < surprisedJeff.length) {// all the lines in the array
				textBubble(g2, surprisedJeff[textIndex]);// display the text
			} else {
				maps.setTriggerTransition(false);// stop transitioning to mirror scene
				surprisedText = false;// no longer display text
				maps.setEnd(true);// go to the end screen
			}
		}
	}

	/**
	 *@purpose reads the lines from the arrays and displays them in the text bubble in proper format 
	 * @param g2
	 * @param dialogue
	 */
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

	//Getter and Setter methods
	
	/**
	 * @param input
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/**
	 * 
	 * @param items
	 */
	public void setItems(Items items) {
		this.items = items;
	}

	/**
	 * 
	 * @return surprisedText
	 */
	public boolean isSurprisedText() {
		return surprisedText;
	}

	/**
	 * 
	 * @param suprisedText
	 */
	public void setSurprisedText(boolean suprisedText) {
		this.surprisedText = suprisedText;
	}

	/**
	 * 
	 * @return textIndex
	 */
	public int getTextIndex() {
		return textIndex;
	}
	
/**
 * 
 * @param textIndex
 */
	public void setTextIndex(int textIndex) {
		this.textIndex = textIndex;
	}
	
	/**
	 * 
	 * @param once
	 */
	public void setOnce(boolean once) {
		this.once = once;
	}

}
