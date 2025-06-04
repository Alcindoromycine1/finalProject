package main;

import java.awt.image.*;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MainMenu {

	private BufferedImage background;
	public boolean inMenu = true;
	public boolean hoveringPlay = false;
	public boolean hoveringHelp = false;
	public boolean hoveringExit = false;
	public boolean hoveringCredits = false;
	
	public MainMenu() {
		loadImages();
	}
	
	public void loadImages() {
		try {
			background = ImageIO.read(new File("src/textures/mainMenu.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mainMenu(Graphics2D g2) {
		Color selected = new Color(144, 50, 50);
		Color unselected = new Color(193, 45, 57);
		g2.drawImage(background, 0, 0, null);
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
	
}
