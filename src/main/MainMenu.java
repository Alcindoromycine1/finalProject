package main;

import java.awt.image.*;
import java.util.ArrayList;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

/*
 * Noah Sussman, Akhilan Saravanan, and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class MainMenu {

	private BufferedImage background;// main menu graphics

	private boolean inMenu = false;// checks whether the user is in the main menu
	private boolean hoveringPlay = false;// checks if you are hovering over the play button (collision in input)
	private boolean hoveringHelp = false;// checks if you are hovering over the help button (collision in input)
	private boolean hoveringExit = false;// checks if you are hovering over the exit button (collision in input)
	private boolean hoveringCredits = false;// checks if you are hovering over the credits button (collision in input)
	private boolean easterEgg = false;// this is the button that says click me in the main menu
	private ArrayList<Character> recommended;// used to sort the letters in the easter egg in alphabetical order
	String defaultRec = "It is recommended to visit the help menu first.";// unsorted text in main menu

	/**
	 * Constructor for the MainMenu class
	 */
	public MainMenu() {
		loadImages();// loads all the images
		recommended = new ArrayList<Character>();
		for (int i = 0; i < defaultRec.length(); i++) {// fill the arrayList with defaultRec in every element
			recommended.add(defaultRec.charAt(i));
		}
	}

	/**
	 * @purpose loads the images for the class
	 */
	public void loadImages() {
		try {
			background = ImageIO.read(new File("src/textures/mainMenu.png"));// the backgorund of the main menu
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @purpose When the user clicks on the click me area, the text on the bottom
	 *          right of the screen will sort alphabetically
	 * @return defaultRec
	 */
	// easter egg, sorting the recommended text at the bottom if you click mirror on
	// top right of main menu
	public String getRecommendation() {
		if (easterEgg) {// in the main menu and has not clicked the "click me"
			// insertion sorting algorithm
			for (int i = 1; i < recommended.size(); i++) {
				char key = recommended.get(i);
				int j = i - 1;
				//sorts alphabetically
				while (j >= 0 && recommended.get(j) > key) {
					recommended.set(j + 1, recommended.get(j));
					j = j - 1;
				}
				recommended.set(j + 1, key);
			}
			String newRec = "";
			for (char a : recommended) {
				newRec += a;
			}
			return newRec;
		} else {
			return defaultRec;
		}
	}

	/**
	 * @purpose graphics for the main menu and the collision graphics
	 * @param g2
	 */
	public void mainMenu(Graphics2D g2) {
		Color selected = new Color(144, 50, 50);
		Color unselected = new Color(193, 45, 57);
		g2.setColor(Color.WHITE);
		g2.drawImage(background, 0, 0, null);
		g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 14));
		g2.drawString("Click me \\/", 680, 70);
		g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 16));
		g2.drawString(getRecommendation(), 410, 560);
		if (hoveringPlay) {
			g2.setColor(selected);
			g2.fillRoundRect(245, 240 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Play", 360, 278 - 20);
		} else {
			g2.setColor(unselected);
			g2.fillRoundRect(245, 240 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Play", 360, 278 - 20);
		}
		if (hoveringHelp) {
			g2.setColor(selected);
			g2.fillRoundRect(245, 240 + 70 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 + 70 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Help", 359, 278 + 70 - 20);
		} else {
			g2.setColor(unselected);
			g2.fillRoundRect(245, 240 + 70 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 + 70 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Help", 359, 278 + 70 - 20);
		}
		if (hoveringCredits) {
			g2.setColor(selected);
			g2.fillRoundRect(245, 240 + 140 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 + 140 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Credits", 340, 278 + 140 - 20);
		} else {
			g2.setColor(unselected);
			g2.fillRoundRect(245, 240 + 140 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 + 140 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Credits", 340, 278 + 140 - 20);
		}
		if (hoveringExit) {
			g2.setColor(selected);
			g2.fillRoundRect(245, 240 + 210 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 + 210 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Exit", 359, 278 + 210 - 20);
		} else {
			g2.setColor(unselected);
			g2.fillRoundRect(245, 240 + 210 - 20, 280, 60, 10, 10);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(245, 240 + 210 - 20, 280, 60, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 24));
			g2.drawString("Exit", 359, 278 + 210 - 20);
		}
	}

	//Getter and Setter Methods
	
	/**
	 * 
	 * @return inMenu
	 */
	public boolean isInMenu() {
		return inMenu;
	}

	/**
	 * 
	 * @return hoveringPlay
	 */
	public boolean isHoveringPlay() {
		return hoveringPlay;
	}

	/**
	 * 
	 * @return hoveringHelp
	 */
	public boolean isHoveringHelp() {
		return hoveringHelp;
	}

	/**
	 * 
	 * @return hoveringExit
	 */
	public boolean isHoveringExit() {
		return hoveringExit;
	}

	/**
	 * 
	 * @return hoveringCredits
	 */
	public boolean isHoveringCredits() {
		return hoveringCredits;
	}

	/**
	 * 
	 * @param inMenu
	 */
	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}

	/**
	 * 
	 * @param hoveringPlay
	 */
	public void setHoveringPlay(boolean hoveringPlay) {
		this.hoveringPlay = hoveringPlay;
	}

	/**
	 * 
	 * @param hoveringHelp
	 */
	public void setHoveringHelp(boolean hoveringHelp) {
		this.hoveringHelp = hoveringHelp;
	}

	/**
	 * 
	 * @param hoveringExit
	 */
	public void setHoveringExit(boolean hoveringExit) {
		this.hoveringExit = hoveringExit;
	}

	/**
	 * 
	 * @param hoveringCredits
	 */
	public void setHoveringCredits(boolean hoveringCredits) {
		this.hoveringCredits = hoveringCredits;
	}

	/**
	 * 
	 * @param easterEgg
	 */
	public void setEasterEgg(boolean easterEgg) {
		this.easterEgg = easterEgg;
	}

}
