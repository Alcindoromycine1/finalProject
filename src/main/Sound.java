package main;

import Horror.Jumpscare;
import javax.sound.sampled.*;
import java.io.*;

public class Sound {

	private Clip clip;   //the variable to store the audio clip that will be played
	
	// Constructor that initializes the sound clip from a file path	
	public Sound(String filePath) {
		try { // Load the audio file from the specified path, catches any exceptions that may occur
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Error loading sound: " + e.getMessage());
		}
	}
	
	// Constructor that initializes the sound clip
	public void play() {
        if (clip != null) { // Checks if the clip is not null before playing
            if (clip.isRunning()) {   // If the clip is already playing, stop it first
                clip.stop();
            }
            clip.setFramePosition(0); 
            clip.start();
        }
    }
	
	/**
	 * Returns of the sound clip is currently playing.
	 */
	public boolean isPlaying() {  
		return clip != null && clip.isRunning();
	}
	
	/**
	 * Stops the sound clip if it is currently playing.
	 */
	public void stop() {
		if (clip != null && clip.isRunning()) { // Checks if the clip is not null and is currently playing
			clip.stop();
		}
	}

	/**
	 * Controls the volume of the sound clip.
	 */
	public void volume(float volume) {
		if (clip != null) {  // Checks if the clip is not null before adjusting volume
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume); // volume in decibels
		}
	}

}
