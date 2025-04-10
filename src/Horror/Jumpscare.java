package Horror;

import main.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Jumpscare {

	private BufferedImage creepyMan;
	private boolean jumpscare = false;

	public Jumpscare() {
		loadImage();
	}

	public void loadImage() {
		try {
			creepyMan = ImageIO.read(new File("src/textures/creepyMan.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isJumpscare() {
		return jumpscare;
	}

	public void setJumpscare(boolean jumpscare) {
		this.jumpscare = jumpscare;
	}

	public BufferedImage getCreepyMan() {
		Sound.playSound("src/sound/jumpScare1.WAV");
		return creepyMan;
	}

}
