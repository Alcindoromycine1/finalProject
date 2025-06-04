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
	boolean jumpscare = false;
	boolean once = false;

	public Items it;

	public Jumpscare(GamePanel gp) {
		loadStuff();
		it = gp.it;
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
		sound.play();
	}

	public boolean isJumpscare() {
		return jumpscare;
	}

	public boolean getOnce() {
		return once;
	}

	public void setOnce(boolean once) {
		this.once = once;
	}

	public void setJumpscare(boolean jumpscare) {
		this.jumpscare = jumpscare;
		once = false; // makes sure sound plays as well
	}

	// Timer created from
	// https://stackoverflow.com/questions/1006611/java-swing-timer
	Timer time;

	public void timer() {
		time = new Timer(723, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				it.playGif = false;
				it.staticImageBook = true;
				time.stop();
			}
		});
		if (time != null && time.isRunning()) {
			time.stop();
		}
		time.setRepeats(false);
		time.start();
	}
}
