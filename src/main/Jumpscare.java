package main;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Jumpscare {

	private BufferedImage creepyMan;
	private Sound sound;
	private boolean jumpscare = false;

	public Jumpscare(GamePanel gp) {
		loadStuff();
	}

	public void loadStuff() {
		try {
			creepyMan = ImageIO.read(new File("textures/creepyMan.png"));
			sound = new Sound("sound/jumpScare1.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawJumpscare(Graphics2D g2) {
		g2.drawImage(creepyMan, 0, 0, null);
		timer();
	}

	public void playSound() {
		if (!sound.isPlaying()) {
			sound.volume(-15.0f);
			sound.play();
		}
	}

	public boolean isJumpscare() {
		return jumpscare;
	}

	public void setJumpscare(boolean jumpscare) {
		this.jumpscare = jumpscare;
	}

	// Timer created from
	// https://stackoverflow.com/questions/1006611/java-swing-timer
	private Timer time;

	public void timer() {
		time = new Timer(4000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jumpscare = false;
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
