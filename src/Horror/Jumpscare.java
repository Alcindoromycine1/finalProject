package Horror;

import main.*;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Jumpscare {

	private BufferedImage creepyMan;
	Sound sound;
	public static boolean jumpscare = false;

	public Jumpscare() {
		loadStuff();
	}

	public void loadStuff() {
		try {
			creepyMan = ImageIO.read(new File("src/textures/creepyMan.png"));
			sound = new Sound("src/sound/jumpScare1.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawJumpscare(Graphics2D g2) {
		g2.drawImage(creepyMan, 0, 0, null);
	}

	public void playSound() {
		if (!sound.isPlaying()) {
			sound.play();
		}
	}
	
	
	
}
