package main;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {

	Clip clip;

	public static void playSound(String filePath) {
		File soundFile = new File(filePath);
		if (!soundFile.exists()) {
			System.out.println("Sound file not found: " + filePath);
			return;
		}
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
		}
	}

}
