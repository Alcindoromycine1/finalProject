package main;
import Horror.Jumpscare;

import javax.sound.sampled.*;
 import java.io.*;
 
 public class Sound {
 
 	Clip clip;
 	static Jumpscare j = new Jumpscare();
 
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
 			Thread.sleep(clip.getMicrosecondLength() / 1000); // Wait for the sound to finish
			j.setJumpscare(false);
 		} catch (Exception e) {
             System.out.println("Error playing sound: " + e.getMessage());
 		} 
 	}
 
 }

