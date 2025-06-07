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

	private boolean once = false; // used to check if the player has done things
	
	//dialogue sounds
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
		
		loadSounds();
	}
	
	private void loadSounds() {
		//ghost graveyard sounds
		gg1 = new Sound("src/sound/gg1.wav");
		gg2 = new Sound("src/sound/gg2.wav");
		gg3 = new Sound("src/sound/gg3.wav");
		gg4 = new Sound("src/sound/gg4.wav");
		gg5 = new Sound("src/sound/gg5.wav");
		
		//ghost doctrine sounds
		gd1 = new Sound("src/sound/gd1.wav");
		gd2 = new Sound("src/sound/gd2.wav");
		
		//doctor sounds
		d1 = new Sound("src/sound/d1.wav");
		d2 = new Sound("src/sound/d2.wav");
		d3 = new Sound("src/sound/d3.wav");
		
		//exorcism ghost sounds
		ghosta = new Sound("src/sound/ghosta.wav");
		ghostb = new Sound("src/sound/ghostb.wav");
		
		//inside house sounds
		h1 = new Sound("src/sound/h1.wav");
		h2 = new Sound("src/sound/h2.wav");
		
		//nightmare sounds
		n1 = new Sound("src/sound/n1.wav");
		n2 = new Sound("src/sound/n2.wav");
		n3 = new Sound("src/sound/n3.wav");
		n4 = new Sound("src/sound/n4.wav");
		n5 = new Sound("src/sound/n5.wav");
	}

	public void updateNpcValues(int playerX, int playerY, int worldX, int worldY) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.worldX = worldX;
		this.worldY = worldY;
	}

	public void doctor(Graphics2D g2, GamePanel gp) throws IOException {
		
		int doctorX = 650 - gp.getWorldX();
		int doctorY = 170 - gp.getWorldY();

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
			if (textIndex == 0) {
				if (!gg1.isPlaying() && !once) {
					gg1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {
				gg1.stop();
				if (!gg2.isPlaying() && !once) {
					gg2.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 2) {
				gg2.stop();
				if (!gg3.isPlaying() && !once) {
					gg3.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 3) {
				gg3.stop();
				if (!gg4.isPlaying() && !once) {
					gg4.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 4) {
				gg4.stop();
				if (!gg5.isPlaying() && !once) {
					gg5.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textGhostGraveyard.length) {
				textBubble(g2, textGhostGraveyard[textIndex]);
			} else {
				items.setFirstTime(false);
				gg5.stop();
			}
		} else if (list == 3) {
			String textDoctor[] = { "I don't have much time left.",
					"You must find the book with all the knowledge to dispell the  thing that hurt me. It is located in the graveyard.",
					"Then, go to the doctrine, and remove the ...", "*The doctor has passed away*" };
			if (textIndex == 0) {
				if (!d1.isPlaying() && !once) {
					d1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {
				d1.stop();
				if (!d2.isPlaying() && !once) {
					d2.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 2) {
				d2.stop();
				if (!d3.isPlaying() && !once) {
					d3.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textDoctor.length) {
				textBubble(g2, textDoctor[textIndex]);
			} else {
				d3.stop();
				m.doneDoctorDead = true;
			}
		} else if (list == 4) {
			String textDoctrineGhost[] = { "Don't hurt me. Please leave me alone.",
					"If you must, go inside the door at the end of this path! To   get rid of my kind." };
			if (textIndex == 0) {
				if (!gd1.isPlaying() && !once) {
					gd1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {
				gd1.stop();
				if (!gd2.isPlaying() && !once) {
					gd2.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textDoctrineGhost.length) {
				textBubble(g2, textDoctrineGhost[textIndex]);
			} else {
				gd2.stop();
			}
		} else if (list == 5) {
			String textJeff[] = { "What is this place?", "I'm tired, I think I'll take a nap here." };
			if (textIndex == 0) {
				if (!h1.isPlaying() && !once) {
					h1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {
				h1.stop();
				if (!h2.isPlaying() && !once) {
					h2.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textJeff.length) {
				textBubble(g2, textJeff[textIndex]);
			} else {
				h2.stop();
			}
		} else if (list == 6) {
			String textNightmare[] = { "What am I doing here?", "What is that thing on the table?",
					"What is this doctor doing to it?", "Is that one of those evil AI?",
					"This can't be real, it's just another hallucination." };
			if (textIndex == 0) {
				if (!n1.isPlaying() && !once) {
					n1.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 1) {
				n1.stop();
				if (!n2.isPlaying() && !once) {
					n2.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 2) {
				n2.stop();
				if (!n3.isPlaying() && !once) {
					n3.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 3) {
				n3.stop();
				if (!n4.isPlaying() && !once) {
					n4.play();
					once = true; // ensures the sound only plays once
				}
			} else if (textIndex == 4) {
				n4.stop();
				if (!n5.isPlaying() && !once) {
					n5.play();
					once = true; // ensures the sound only plays once
				}
			}
			if (textIndex < textNightmare.length) {
				textBubble(g2, textNightmare[textIndex]);
			} else {
				maps.setDoneNightmare(true);
				n5.stop();
			}
		} else if (list == 7) {
			String textCarDestroyed[] = { "My brain is playing tricks on me again!",
					"I crashed the car because of this." };
			if (textIndex < textCarDestroyed.length) {
				textBubble(g2, textCarDestroyed[textIndex]);
			} else {
				items.setCarSceneDone(true);
			}
		}else if (list == 7) {
			String textCarDestroyed[] = {"My brain is playing tricks on me again!", "I crashed the car because of this."};
			if (textIndex < textCarDestroyed.length) {
				textBubble(g2, textCarDestroyed[textIndex]);
			} else {
				//items.carOn = false;
			}
		}else if (list == 7) {
			String textCarDestroyed[] = {"My brain is playing tricks on me again!", "I crashed the car because of this."};
			if (textIndex < textCarDestroyed.length) {
				textBubble(g2, textCarDestroyed[textIndex]);
			} else {
				//items.carOn = false;
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
	
	public void setOnce (boolean once) {
		this.once = once;
	}

}
