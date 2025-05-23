package main;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MainMenu {

	private BufferedImage background;
	private BufferedImage car;
	public static boolean inMenu = true;
	public static boolean hoveringPlay = false;
	
	public MainMenu() {
		loadImages();
	}
	
	public void loadImages() {
		try {
			background = ImageIO.read(new File("src/textures/mainMenu.png"));
			car = ImageIO.read(new File("src/textures/car.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(Graphics2D g2) {
		g2.drawImage(background, 0, 0, null);
		if (hoveringPlay) {
			g2.setColor(new Color(0, 0, 10));
			g2.fillRoundRect(284, 300, 200, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 20));
			g2.drawString("Play", 360, 325);
		} else {
			g2.setColor(Color.RED);
			g2.fillRoundRect(284, 300, 200, 40, 10, 10);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("DejaVu Math TeX Gyre", Font.BOLD, 20));
			g2.drawString("Play", 360, 325);
		}
	}
	
}
