package main;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {

	private Clip clip;
	
	public Sound(String filePath) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Error loading sound: " + e.getMessage());
		}
	}

	public void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

	public boolean isPlaying() {
		return clip != null && clip.isRunning();
	}
	
	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.close();
		}
	}
	
	public void volume(float volume) {
		if (clip != null) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume); // volume in decibels
		}
	}

}
