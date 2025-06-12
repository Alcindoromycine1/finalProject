/**
 * @author Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * @since April 2, 2025
 * @version 2.0
 * Final Project ICS4U0
 * Whispers of the Deceived
 */
package main;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import interfaces.ReadFromFile;

/**
 * The tiles class is responsible for managing our basic graphics for the game.
 * It reads values from the maps folder and stores them within an arrayList.
 * After that, it converts them to buffered images and store this in the
 * arrayList values.
 */
public class Tiles implements ReadFromFile {

	public ArrayList<ArrayList<BufferedImage>> tileImages = new ArrayList<>(); // 2D ArrayList to hold tile images

	Maps m; // Reference to the Maps class to access maps methods and properties

	/**
	 * Constructor for the Tiles class.
	 * 
	 * @param gp GamePanel instance to access the Maps instance.
	 */
	public Tiles(GamePanel gp) {
		this.m = gp.getM();
	}

	/**
	 * Method to create tile images based on the map's tile codes. It initializes
	 * the tileImages ArrayList and assigns images to each tile code.
	 * 
	 * @throws IOException if there is an error reading the image files.
	 */
	@Override
	public void readFile() throws IOException {

		tileImages.clear();
		for (int i = 0; i < m.getTiles().size(); i++) { // Initialize the 2D ArrayList for each row
			ArrayList<BufferedImage> rowImages = new ArrayList<>(); // Create a new ArrayList for each row
			for (int j = 0; j < m.getTiles().get(i).size(); j++) { // Initialize each column in the row
				rowImages.add(null);
			}
			tileImages.add(rowImages);
		}

		// Then assign images
		for (int i = 0; i < m.getTiles().size(); i++) { // Iterate through each row of tiles
			for (int j = 0; j < m.getTiles().get(i).size(); j++) { // Iterate through each tile in the row
				int tileCode = m.getTiles().get(i).get(j);
				BufferedImage image = null;

				if (tileCode == 62) { // Assign images based on tile codes
					image = ImageIO.read(new File("src/textures/grassBlockUpdated.png")); // Reads the image file for
																							// grass block into the
																							// BufferedImage arrayList
				} else if (tileCode == 63) {
					image = ImageIO.read(new File("src/textures/tree.png")); // Reads the image file for tree into the
																				// BufferedImage arrayList
				} else if (tileCode == 64) {
					image = ImageIO.read(new File("src/textures/cobblestone.png")); // Reads the image file for
																					// cobblestone into the
																					// BufferedImage arrayList
				} else if (tileCode == 65) {
					image = ImageIO.read(new File("src/textures/1.png")); // Reads the image file for 1.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 66) {
					image = ImageIO.read(new File("src/textures/2.png")); // Reads the image file for 2.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 67) {
					image = ImageIO.read(new File("src/textures/3.png")); // Reads the image file for 3.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 68) {
					image = ImageIO.read(new File("src/textures/4.png")); // Reads the image file for 4.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 69) {
					image = ImageIO.read(new File("src/textures/5.png")); // Reads the image file for 5.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 70) {
					image = ImageIO.read(new File("src/textures/6.png")); // Reads the image file for 6.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 71) {
					image = ImageIO.read(new File("src/textures/7.png")); // Reads the image file for 7.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 10) {
					image = ImageIO.read(new File("src/textures/8.png")); // Reads the image file for 8.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 11) {
					image = ImageIO.read(new File("src/textures/9.png")); // Reads the image file for 9.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 12) {
					image = ImageIO.read(new File("src/textures/10.png")); // Reads the image file for 10.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 13) {
					image = ImageIO.read(new File("src/textures/11.png")); // Reads the image file for 11.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 14) {
					image = ImageIO.read(new File("src/textures/12.png"));// Reads the image file for 12.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 15) {
					image = ImageIO.read(new File("src/textures/13.png")); // Reads the image file for 13.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 16) {
					image = ImageIO.read(new File("src/textures/14.png")); // Reads the image file for 14.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 17) {
					image = ImageIO.read(new File("src/textures/15.png")); // Reads the image file for 15.png into the
																			// BufferedImage arrayList
				} else if (tileCode == 18) {
					image = ImageIO.read(new File("src/textures/plank.png")); // Reads the image file for plank.png into
																				// the BufferedImage arrayList
				} else if (tileCode == 19) {
					image = ImageIO.read(new File("src/textures/bedTopLeft.png")); // Reads the image file for
																					// bedTopLeft.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 20) {
					image = ImageIO.read(new File("src/textures/bedTopRight.png")); // Reads the image file for
																					// bedTopRight.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 21) {
					image = ImageIO.read(new File("src/textures/bedBottomLeft.png")); // Reads the image file for
																						// bedBottomLeft.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 22) {
					image = ImageIO.read(new File("src/textures/bedBottomRight.png")); // Reads the image file for
																						// bedBottomRight.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 23) {
					image = ImageIO.read(new File("src/textures/wallTop.png")); // Reads the image file for wallTop.png
																				// into the BufferedImage arrayList
				} else if (tileCode == 24) {
					image = ImageIO.read(new File("src/textures/wallBottom.png")); // Reads the image file for
																					// wallBottom.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 25) {
					image = ImageIO.read(new File("src/textures/wallTopSide.png")); // Reads the image file for
																					// wallTopSide.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 26) {
					image = ImageIO.read(new File("src/textures/wallBottomSide.png")); // Reads the image file for
																						// wallBottomSide.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 27) {
					image = ImageIO.read(new File("src/textures/wallSideTop.png")); // Reads the image file for
																					// wallSideTop.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 28) {
					image = ImageIO.read(new File("src/textures/wallTopBorder.png")); // Reads the image file for
																						// wallTopBorder.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 29) {
					image = ImageIO.read(new File("src/textures/emptyWallTop.png")); // Reads the image file for
																						// emptyWallTop.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 30) {
					image = ImageIO.read(new File("src/textures/wallTopRight.png")); // Reads the image file for
																						// wallTopRight.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 31) {
					image = ImageIO.read(new File("src/textures/houseTopCorner.png")); // Reads the image file for
																						// houseTopCorner.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 32) {
					image = ImageIO.read(new File("src/textures/houseWallTopRightCorner.png")); // Reads the image file
																								// for
																								// houseWallTopRightCorner.png
																								// into the
																								// BufferedImage
																								// arrayList
				} else if (tileCode == 33) {
					image = ImageIO.read(new File("src/textures/grassUpdated.png")); // Reads the image file for
																						// grassUpdated.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 34) {
					image = ImageIO.read(new File("src/textures/water.png")); // Reads the image file for water.png into
																				// the BufferedImage arrayList
				} else if (tileCode == 35) {
					image = ImageIO.read(new File("src/textures/sand.png")); // Reads the image file for sand.png into
																				// the BufferedImage arrayList
				} else if (tileCode == 36) {
					image = ImageIO.read(new File("src/textures/void.png")); // Reads the image file for void.png into
																				// the BufferedImage arrayList
				} else if (tileCode == 37) {
					image = ImageIO.read(new File("src/textures/doctrineFloor.png")); // Reads the image file for
																						// doctrineFloor.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 38) {
					image = ImageIO.read(new File("src/textures/doctrineWall.png")); // Reads the image file for
																						// doctrineWall.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 39) {
					image = ImageIO.read(new File("src/textures/doctrineUpperWall.png")); // Reads the image file for
																							// doctrineUpperWall.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 40) {
					image = ImageIO.read(new File("src/textures/topLeftDoor.png")); // Reads the image file for
																					// topLeftDoor.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 41) {
					image = ImageIO.read(new File("src/textures/topRightDoor.png")); // Reads the image file for
																						// topRightDoor.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 42) {
					image = ImageIO.read(new File("src/textures/bottomLeftDoor.png")); // Reads the image file for
																						// bottomLeftDoor.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 43) {
					image = ImageIO.read(new File("src/textures/bottomRightDoor.png")); // Reads the image file for
																						// bottomRightDoor.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 44) {
					image = ImageIO.read(new File("src/textures/doctrineTopWall.png")); // Reads the image file for
																						// doctrineTopWall.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 45) {
					image = ImageIO.read(new File("src/textures/doctrineOtherTopWall.png")); // Reads the image file for
																								// doctrineOtherTopWall.png
																								// into the
																								// BufferedImage
																								// arrayList
				} else if (tileCode == 46) {
					image = ImageIO.read(new File("src/textures/doctrineBottomWall.png")); // Reads the image file for
																							// doctrineBottomWall.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 47) {
					image = ImageIO.read(new File("src/textures/doctrineEmptyWall.png")); // Reads the image file for
																							// doctrineEmptyWall.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 48) {
					image = ImageIO.read(new File("src/textures/downLeft.png")); // Reads the image file for
																					// downLeft.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 49) {
					image = ImageIO.read(new File("src/textures/downLeftCorner.png")); // Reads the image file for
																						// downLeftCorner.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 50) {
					image = ImageIO.read(new File("src/textures/downRight.png")); // Reads the image file for
																					// downRight.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 51) {
					image = ImageIO.read(new File("src/textures/downRightCorner.png")); // Reads the image file for
																						// downRightCorner.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 52) {
					image = ImageIO.read(new File("src/textures/sideDown.png")); // Reads the image file for
																					// sideDown.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 53) {
					image = ImageIO.read(new File("src/textures/sideUp.png")); // Reads the image file for sideUp.png
																				// into the BufferedImage arrayList
				} else if (tileCode == 54) {
					image = ImageIO.read(new File("src/textures/bottomLeftDesk.png")); // Reads the image file for
																						// bottomLeftDesk.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 55) {
					image = ImageIO.read(new File("src/textures/bottomMiddleDesk.png")); // Reads the image file for
																							// bottomMiddleDesk.png into
																							// the BufferedImage
																							// arrayList
				} else if (tileCode == 56) {
					image = ImageIO.read(new File("src/textures/bottomRightDesk.png")); // Reads the image file for
																						// bottomRightDesk.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 57) {
					image = ImageIO.read(new File("src/textures/topRightDesk.png")); // Reads the image file for
																						// topRightDesk.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 58) {
					image = ImageIO.read(new File("src/textures/topMiddleDesk.png")); // Reads the image file for
																						// topMiddleDesk.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 59) {
					image = ImageIO.read(new File("src/textures/topLeftDesk.png")); // Reads the image file for
																					// topLeftDesk.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 60) {
					image = ImageIO.read(new File("src/textures/topAndRightCorner.png")); // Reads the image file for
																							// topAndRightCorner.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 61) {
					image = ImageIO.read(new File("src/textures/topLeftCornerDot.png")); // Reads the image file for
																							// topLeftCornerDot.png into
																							// the BufferedImage
																							// arrayList
				} else if (tileCode == 72) {
					image = ImageIO.read(new File("src/textures/bottomLeftCornerDot.png")); // Reads the image file for
																							// bottomLeftCornerDot.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 73) {
					image = ImageIO.read(new File("src/textures/tombstone.png")); // Reads the image file for
																					// tombstone.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 74) {
					image = ImageIO.read(new File("src/textures/dugUpEarth.png")); // Reads the image file for
																					// dugUpEarth.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 75) {
					image = ImageIO.read(new File("src/textures/deadGrass.png")); // Reads the image file for
																					// deadGrass.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 76) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftBottom.png")); // Reads the image file
																								// for
																								// grassBlockShiftBottom.png
																								// into the
																								// BufferedImage
																								// arrayList
				} else if (tileCode == 77) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftBottomRightCorner.png")); // Reads the
																											// image
																											// file for
																											// grassBlockShiftBottomRightCorner.png
																											// into the
																											// BufferedImage
																											// arrayList
				} else if (tileCode == 78) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftRight.png")); // Reads the image file for
																								// grassBlockShiftRight.png
																								// into the
																								// BufferedImage
																								// arrayList
				} else if (tileCode == 79) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftTopRightCorner.png")); // Reads the image
																										// file for
																										// grassBlockShiftTopRightCorner.png
																										// into the
																										// BufferedImage
																										// arrayList
				} else if (tileCode == 80) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftTop.png")); // Reads the image file for
																							// grassBlockShiftTop.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 81) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftTopLeftCorner.png")); // Reads the image
																										// file for
																										// grassBlockShiftTopLeftCorner.png
																										// into the
																										// BufferedImage
																										// arrayList
				} else if (tileCode == 82) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftLeft.png")); // Reads the image file for
																							// grassBlockShiftLeft.png
																							// into the BufferedImage
																							// arrayList
				} else if (tileCode == 83) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftBottomLeftCorner.png")); // Reads the
																										// image file
																										// for
																										// grassBlockShiftBottomLeftCorner.png
																										// into the
																										// BufferedImage
																										// arrayList
				} else if (tileCode == 84) {
					image = ImageIO.read(new File("src/textures/deadTree.png")); // Reads the image file for
																					// deadTree.png into the
																					// BufferedImage arrayList
				} else if (tileCode == 85) {
					image = ImageIO.read(new File("src/textures/book.png")); // Reads the image file for book.png into
																				// the BufferedImage arrayList
				} else if (tileCode == 86) {
					image = ImageIO.read(new File("src/textures/sideDoorDown.png")); // Reads the image file for
																						// sideDoorDown.png into the
																						// BufferedImage arrayList
				} else if (tileCode == 87) {
					image = ImageIO.read(new File("src/textures/sideDoorUp.png")); // Reads the image file for
																					// sideDoorUp.png into the
																					// BufferedImage arrayList
				} else {
					image = ImageIO.read(new File("src/textures/void.png")); // Default case, reads the image file for
																				// void.png into the BufferedImage
																				// arrayList
				}

				tileImages.get(i).set(j, image); // Set the image in the 2D ArrayList at the current position
			}
		}
	}

	/**
	 * Setter Method
	 * 
	 * @param m
	 */
	public void setM(Maps m) { // Setter for Maps instance
		this.m = m;
	}
}