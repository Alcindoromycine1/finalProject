package main;

import java.awt.image.*;
import java.util.ArrayList;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MainMenu {

	private BufferedImage background;

	private boolean inMenu = false;
	private boolean hoveringPlay = false;
	private boolean hoveringHelp = false;
	private boolean hoveringExit = false;
	private boolean hoveringCredits = false;
	private boolean easterEgg = false;
	private ArrayList<Character> recommended;
	String defaultRec = "It is recommended to visit the help menu first.";

	public MainMenu() {
		loadImages();
		recommended = new ArrayList<Character>();
		for (int i = 0; i < defaultRec.length(); i++) {
			recommended.add(defaultRec.charAt(i));
		}
	}

	public void loadImages() {
		try {
			background = ImageIO.read(new File("src/textures/mainMenu.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//insertion sort algorithm
	//easter egg, sorting the recommended text at the bottom if you click mirror on top right of main menu
	public String getRecommendation() {
		if (easterEgg) {
			for (int i = 1; i < recommended.size(); i++) {
		        char key = recommended.get(i);
		        int j = i - 1;

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

	public void mainMenu(Graphics2D g2) {
		Color selected = new Color(144, 50, 50);
		Color unselected = new Color(193, 45, 57);
		g2.setColor(Color.WHITE);
		g2.drawImage(background, 0, 0, null);
		g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 14));
		g2.drawString ("Click me \\/", 680, 70);
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

	public boolean isInMenu() {
		return inMenu;
	}

	public boolean isHoveringPlay() {
		return hoveringPlay;
	}

	public boolean isHoveringHelp() {
		return hoveringHelp;
	}

	public boolean isHoveringExit() {
		return hoveringExit;
	}

	public boolean isHoveringCredits() {
		return hoveringCredits;
	}

	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}

	public void setHoveringPlay(boolean hoveringPlay) {
		this.hoveringPlay = hoveringPlay;
	}

	public void setHoveringHelp(boolean hoveringHelp) {
		this.hoveringHelp = hoveringHelp;
	}

	public void setHoveringExit(boolean hoveringExit) {
		this.hoveringExit = hoveringExit;
	}

	public void setHoveringCredits(boolean hoveringCredits) {
		this.hoveringCredits = hoveringCredits;
	}
	
	public void setEasterEgg(boolean easterEgg) {
		this.easterEgg = easterEgg;
	}

}
