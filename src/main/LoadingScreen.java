package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;

public class LoadingScreen {

	private BufferedImage loadingImage, volumeUp;
	private boolean loadingScreen = true;
	private int fadeValue = 255;
	private boolean logoFaded = false;
	private boolean logoIn = false;
	private boolean volumeDone = false;

	private MainMenu mainMenu;
	
	public LoadingScreen(GamePanel gp) {
		loadImages();
		mainMenu = gp.getMainMenu();
	}

	public void loadImages() {
		try {
			loadingImage = ImageIO.read(new File("src/textures/loadingScreen.png"));
			volumeUp = ImageIO.read(new File("src/textures/volumeUp.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getLoadingImage() {
		return loadingImage;
	}

	public void drawLoadingScreen(Graphics2D g2) {
		if (!logoIn) {
			g2.drawImage(loadingImage, 0, 0, null);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue -= 2;
			if (fadeValue <= 0) {
				fadeValue = 0;
				logoIn = true;
			}
		} else if (logoIn && !logoFaded) {
			g2.drawImage(loadingImage, 0, 0, null);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			fadeValue += 2;
			g2.fillRect(0, 0, 768, 576);
			if (fadeValue >= 255) {
				fadeValue = 255;
				logoFaded = true;
			}
		} else if (logoFaded && !volumeDone) {
			g2.drawImage(volumeUp, 0, 0, null);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue -= 1;
			if (fadeValue <= 0) {
				fadeValue = 0;
				volumeDone = true;
			}
		} else if (volumeDone) {
			g2.drawImage(volumeUp, 0, 0, null);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue += 1;
			if (fadeValue >= 255) {
				fadeValue = 255;
				loadingScreen = false; // End the loading screen
				mainMenu.setInMenu(true); // Switch to the main menu
			}
		}
	}
	
	public boolean isLoadingScreen() {
		return loadingScreen;
	}

}
