package main;

import Horror.Jumpscare;
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
                clip.stop(); // Stop the clip if it's already playing
            }
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

	public boolean isPlaying() {
		return clip != null && clip.isRunning();
	}

}
