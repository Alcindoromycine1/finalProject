package main;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tiles {

	public static ArrayList<ArrayList<BufferedImage>> tileImages = new ArrayList<>();

	public static void tileCreating() throws IOException {

		tileImages.clear();
		for (int i = 0; i < Maps.tiles.size(); i++) {
			ArrayList<BufferedImage> rowImages = new ArrayList<>();
			for (int j = 0; j < Maps.tiles.get(i).size(); j++) {
				rowImages.add(null);
			}
			tileImages.add(rowImages);
		}

		// Then assign images
		for (int i = 0; i < Maps.tiles.size(); i++) {
			for (int j = 0; j < Maps.tiles.get(i).size(); j++) {
				int tileCode = Maps.tiles.get(i).get(j);
				BufferedImage image = null;

				if (tileCode == 62) {
					image = ImageIO.read(new File("src/textures/grassBlockUpdated.png"));
				} else if (tileCode == 63) {
					image = ImageIO.read(new File("src/textures/tree.png"));
				} else if (tileCode == 64) {
					image = ImageIO.read(new File("src/textures/cobblestone.png"));
				} else if (tileCode == 65) {
					image = ImageIO.read(new File("src/textures/1.png"));
				} else if (tileCode == 66) {
					image = ImageIO.read(new File("src/textures/2.png"));
				} else if (tileCode == 67) {
					image = ImageIO.read(new File("src/textures/3.png"));
				} else if (tileCode == 68) {
					image = ImageIO.read(new File("src/textures/4.png"));
				} else if (tileCode == 69) {
					image = ImageIO.read(new File("src/textures/5.png"));
				} else if (tileCode == 70) {
					image = ImageIO.read(new File("src/textures/6.png"));
				} else if (tileCode == 71) {
					image = ImageIO.read(new File("src/textures/7.png"));
				} else if (tileCode == 10) {
					image = ImageIO.read(new File("src/textures/8.png"));
				} else if (tileCode == 11) {
					image = ImageIO.read(new File("src/textures/9.png"));
				} else if (tileCode == 12) {
					image = ImageIO.read(new File("src/textures/10.png"));
				} else if (tileCode == 13) {
					image = ImageIO.read(new File("src/textures/11.png"));
				} else if (tileCode == 14) {
					image = ImageIO.read(new File("src/textures/12.png"));
				} else if (tileCode == 15) {
					image = ImageIO.read(new File("src/textures/13.png"));
				} else if (tileCode == 16) {
					image = ImageIO.read(new File("src/textures/14.png"));
				} else if (tileCode == 17) {
					image = ImageIO.read(new File("src/textures/15.png"));
				} else if (tileCode == 18) {
					image = ImageIO.read(new File("src/textures/plank.png"));
				} else if (tileCode == 19) {
					image = ImageIO.read(new File("src/textures/bedTopLeft.png"));
				} else if (tileCode == 20) {
					image = ImageIO.read(new File("src/textures/bedTopRight.png"));
				} else if (tileCode == 21) {
					image = ImageIO.read(new File("src/textures/bedBottomLeft.png"));
				} else if (tileCode == 22) {
					image = ImageIO.read(new File("src/textures/bedBottomRight.png"));
				} else if (tileCode == 23) {
					image = ImageIO.read(new File("src/textures/wallTop.png"));
				} else if (tileCode == 24) {
					image = ImageIO.read(new File("src/textures/wallBottom.png"));
				} else if (tileCode == 25) {
					image = ImageIO.read(new File("src/textures/wallTopSide.png"));
				} else if (tileCode == 26) {
					image = ImageIO.read(new File("src/textures/wallBottomSide.png"));
				} else if (tileCode == 27) {
					image = ImageIO.read(new File("src/textures/wallSideTop.png"));
				} else if (tileCode == 28) {
					image = ImageIO.read(new File("src/textures/wallTopBorder.png"));
				} else if (tileCode == 29) {
					image = ImageIO.read(new File("src/textures/emptyWallTop.png"));
				} else if (tileCode == 30) {
					image = ImageIO.read(new File("src/textures/wallTopRight.png"));
				} else if (tileCode == 31) {
					image = ImageIO.read(new File("src/textures/houseTopCorner.png"));
				} else if (tileCode == 32) {
					image = ImageIO.read(new File("src/textures/houseWallTopRightCorner.png"));
				} else if (tileCode == 33) {
					image = ImageIO.read(new File("src/textures/grassUpdated.png"));
				} else if (tileCode == 34) {
					image = ImageIO.read(new File("src/textures/water.png"));
				} else if (tileCode == 35) {
					image = ImageIO.read(new File("src/textures/sand.png"));
				} else if (tileCode == 36) {
					image = ImageIO.read(new File("src/textures/void.png"));
				} else if (tileCode == 37) {
					image = ImageIO.read(new File("src/textures/doctrineFloor.png"));
				} else if (tileCode == 38) {
					image = ImageIO.read(new File("src/textures/doctrineWall.png"));
				} else if (tileCode == 39) {
					image = ImageIO.read(new File("src/textures/doctrineUpperWall.png"));
				} else if (tileCode == 40) {
					image = ImageIO.read(new File("src/textures/topLeftDoor.png"));
				} else if (tileCode == 41) {
					image = ImageIO.read(new File("src/textures/topRightDoor.png"));
				} else if (tileCode == 42) {
					image = ImageIO.read(new File("src/textures/bottomLeftDoor.png"));
				} else if (tileCode == 43) {
					image = ImageIO.read(new File("src/textures/bottomRightDoor.png"));
				} else if (tileCode == 44) {
					image = ImageIO.read(new File("src/textures/doctrineTopWall.png"));
				} else if (tileCode == 45) {
					image = ImageIO.read(new File("src/textures/doctrineOtherTopWall.png"));
				} else if (tileCode == 46) {
					image = ImageIO.read(new File("src/textures/doctrineBottomWall.png"));
				} else if (tileCode == 47) {
					image = ImageIO.read(new File("src/textures/doctrineEmptyWall.png"));
				} else if (tileCode == 48) {
					image = ImageIO.read(new File("src/textures/downLeft.png"));
				} else if (tileCode == 49) {
					image = ImageIO.read(new File("src/textures/downLeftCorner.png"));
				} else if (tileCode == 50) {
					image = ImageIO.read(new File("src/textures/downRight.png"));
				} else if (tileCode == 51) {
					image = ImageIO.read(new File("src/textures/downRightCorner.png"));
				} else if (tileCode == 52) {
					image = ImageIO.read(new File("src/textures/sideDown.png"));
				} else if (tileCode == 53) {
					image = ImageIO.read(new File("src/textures/sideUp.png"));
				} else if (tileCode == 54) {
					image = ImageIO.read(new File("src/textures/bottomLeftDesk.png"));
				} else if (tileCode == 55) {
					image = ImageIO.read(new File("src/textures/bottomMiddleDesk.png"));
				} else if (tileCode == 56) {
					image = ImageIO.read(new File("src/textures/bottomRightDesk.png"));
				} else if (tileCode == 57) {
					image = ImageIO.read(new File("src/textures/topRightDesk.png"));
				} else if (tileCode == 58) {
					image = ImageIO.read(new File("src/textures/topMiddleDesk.png"));
				} else if (tileCode == 59) {
					image = ImageIO.read(new File("src/textures/topLeftDesk.png"));
				} else if (tileCode == 60) {
					image = ImageIO.read(new File("src/textures/topAndRightCorner.png"));
				} else if (tileCode == 61) {
					image = ImageIO.read(new File("src/textures/topLeftCornerDot.png"));
				} else if (tileCode == 72) {
					image = ImageIO.read(new File("src/textures/bottomLeftCornerDot.png"));
				} else if (tileCode == 73) {
					image = ImageIO.read(new File("src/textures/tombstone.png"));
				} else if (tileCode == 74) {
					image = ImageIO.read(new File("src/textures/dugUpEarth.png"));
				} else if (tileCode == 75) {
					image = ImageIO.read(new File("src/textures/deadGrass.png"));
				} else if (tileCode == 76) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftBottom.png"));
				} else if (tileCode == 77) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftBottomRightCorner.png"));
				} else if (tileCode == 78) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftRight.png"));
				} else if (tileCode == 79) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftTopRightCorner.png"));
				} else if (tileCode == 80) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftTop.png"));
				} else if (tileCode == 81) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftTopLeftCorner.png"));
				} else if (tileCode == 82) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftLeft.png"));
				} else if (tileCode == 83) {
					image = ImageIO.read(new File("src/textures/grassBlockShiftBottomLeftCorner.png"));
				} else if (tileCode == 84) {
					image = ImageIO.read(new File("src/textures/deadTree.png"));
				} else if (tileCode == 85) {
					image = ImageIO.read(new File("src/textures/book.png"));
				} else if (tileCode == 86) {
					image = ImageIO.read(new File("src/textures/sideDoorDown.png"));
				} else if (tileCode == 87) {
					image = ImageIO.read(new File("src/textures/sideDoorUp.png"));
				}
				else {
					image = ImageIO.read(new File("src/textures/void.png"));
				}

				tileImages.get(i).set(j, image);
			}
		}
	}

}