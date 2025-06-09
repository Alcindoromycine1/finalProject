package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;

public class LoadingScreen {

	private BufferedImage loadingImage, volumeUp;//image that is shown in the middle of the screen during the intro screens
	private boolean loadingScreen = true;
	private int fadeValue = 255;//used for fading in and out (transparency wise) in the loading screen
	private boolean logoFaded = false;//detects whether the logo has successfully finished fading
	private boolean logoIn = false;//detects whether the logo has successfully faded in (not out)
	private boolean volumeDone = false;//detects whether the volume scene has sucessfully finished fading

	private MainMenu mainMenu;//variable that moves the user from the loading screen into the main menu
	
	public LoadingScreen(GamePanel gp) {
		loadImages();//loads the images that will be displayed on the screen
		mainMenu = gp.getMainMenu();//adding the main menu information to this object
	}

	/** 
	 * @purpose loads the images in the loading screen once
	 * */
	public void loadImages() {
		try {
			loadingImage = ImageIO.read(new File("src/textures/loadingScreen.png"));
			volumeUp = ImageIO.read(new File("src/textures/volumeUp.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @purpose fades in and out of the different loading screen scenes with the images
	 * @param g2
	 */
	public void drawLoadingScreen(Graphics2D g2) {
		if (!logoIn) {//not faded in yet
			g2.drawImage(loadingImage, 0, 0, null);//draws logo image
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue -= 2;
			if (fadeValue <= 0) {//avoids going into negative transparency values (not possible)
				fadeValue = 0;
				logoIn = true;//finished fading in
			}
		} else if (logoIn && !logoFaded) {//has faded in
			g2.drawImage(loadingImage, 0, 0, null);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			fadeValue += 2;//fade out
			g2.fillRect(0, 0, 768, 576);
			if (fadeValue >= 255) {//avoids going above the transparency values (not possible)
				fadeValue = 255;
				logoFaded = true;//has finished fading this scene
			}
		} else if (logoFaded && !volumeDone) {//volume has not faded in
			g2.drawImage(volumeUp, 0, 0, null);
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue -= 1;
			if (fadeValue <= 0) {//avoids going into negative transparency values (not possible)
				fadeValue = 0;
				volumeDone = true;//has faded in and out of the volume scene
			}
		} else if (volumeDone) {//volume scene has faded both in and out
			g2.drawImage(volumeUp, 0, 0, null);//image of volume
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue += 1;
			if (fadeValue >= 255) {//avoids going above the transparency values (not possible)
				fadeValue = 255;
				loadingScreen = false; // End the loading screen
				mainMenu.setInMenu(true); // Switch to the main menu
			}
		}
	}
	
	/** 
	 * @purpose Getter method
	 * @return loadingImage
	 */
	public BufferedImage getLoadingImage() {
		return loadingImage;
	}
	
	/** 
	 * @purpose Getter method
	 * @return loadingScreen
	 */
	
	public boolean isLoadingScreen() {
		return loadingScreen;
	}

}
