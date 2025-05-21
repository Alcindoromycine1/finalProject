package main;

import java.awt.image.*;
import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;

public class Tiles {

	public static BufferedImage tileImages[][] = new BufferedImage[Maps.maxWorldRow][Maps.maxWorldCol];

	public static void tileCreating() throws IOException {

		for (int i = 0; i < Maps.tiles.length; i++) {
			for (int j = 0; j < Maps.tiles[i].length; j++) {

				if (Maps.tiles[i][j] == 62) {// grass is found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockUpdated.png"));
				} else if (Maps.tiles[i][j] == 63) {// trees are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/tree.png"));
				} else if (Maps.tiles[i][j] == 64) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/cobblestone.png"));
				} else if (Maps.tiles[i][j] == 65) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/1.png"));
				} else if (Maps.tiles[i][j] == 66) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/2.png"));
				} else if (Maps.tiles[i][j] == 67) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/3.png"));
				} else if (Maps.tiles[i][j] == 68) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/4.png"));
				} else if (Maps.tiles[i][j] == 69) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/5.png"));
				} else if (Maps.tiles[i][j] == 70) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/6.png"));
				} else if (Maps.tiles[i][j] == 71) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/7.png"));
				} else if (Maps.tiles[i][j] == 10) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/8.png"));
				} else if (Maps.tiles[i][j] == 11) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/9.png"));
				} else if (Maps.tiles[i][j] == 12) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/10.png"));
				} else if (Maps.tiles[i][j] == 13) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/11.png"));
				} else if (Maps.tiles[i][j] == 14) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/12.png"));
				} else if (Maps.tiles[i][j] == 15) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/13.png"));
				} else if (Maps.tiles[i][j] == 16) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/14.png"));
				} else if (Maps.tiles[i][j] == 17) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/15.png"));
				} else if (Maps.tiles[i][j] == 18) {// planks are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/plank.png"));
					// bed was found at:
					// https://cdnb.artstation.com/p/assets/images/images/035/882/977/large/gregory-ligman-bed-00.jpg?1616150094
				} else if (Maps.tiles[i][j] == 19) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bedTopLeft.png"));
				} else if (Maps.tiles[i][j] == 20) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bedTopRight.png"));
				} else if (Maps.tiles[i][j] == 21) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bedBottomLeft.png"));
				} else if (Maps.tiles[i][j] == 22) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bedBottomRight.png"));
				} else if (Maps.tiles[i][j] == 23) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallTop.png"));
				} else if (Maps.tiles[i][j] == 24) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallBottom.png"));
				} else if (Maps.tiles[i][j] == 25) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallTopSide.png"));
				} else if (Maps.tiles[i][j] == 26) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallBottomSide.png"));
				} else if (Maps.tiles[i][j] == 27) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallSideTop.png"));
				} else if (Maps.tiles[i][j] == 28) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallTopBorder.png"));
				} else if (Maps.tiles[i][j] == 29) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/emptyWallTop.png"));
				} else if (Maps.tiles[i][j] == 30) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/wallTopRight.png"));
				} else if (Maps.tiles[i][j] == 31) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/houseTopCorner.png"));
				} else if (Maps.tiles[i][j] == 32) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/houseWallTopRightCorner.png"));
				} else if (Maps.tiles[i][j] == 33) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassUpdated.png"));
				} else if (Maps.tiles[i][j] == 34) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/water.png"));
				} else if (Maps.tiles[i][j] == 35) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/sand.png"));
				} else if (Maps.tiles[i][j] == 36) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/void.png"));
				} else if (Maps.tiles[i][j] == 37) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineFloor.png"));
				} else if (Maps.tiles[i][j] == 38) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineWall.png"));
				} else if (Maps.tiles[i][j] == 39) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineUpperWall.png"));
				} else if (Maps.tiles[i][j] == 40) {// beds are found in the file
					// door inspired from: https://hazushadc.itch.io/pixel-door-assets
					tileImages[i][j] = ImageIO.read(new File("src/textures/topLeftDoor.png"));
				} else if (Maps.tiles[i][j] == 41) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/topRightDoor.png"));
				} else if (Maps.tiles[i][j] == 42) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bottomLeftDoor.png"));
				} else if (Maps.tiles[i][j] == 43) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bottomRightDoor.png"));
				} else if (Maps.tiles[i][j] == 44) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineTopWall.png"));
				} else if (Maps.tiles[i][j] == 45) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineOtherTopWall.png"));
				} else if (Maps.tiles[i][j] == 46) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineBottomWall.png"));
				} else if (Maps.tiles[i][j] == 47) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/doctrineEmptyWall.png"));
				} else if (Maps.tiles[i][j] == 48) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/downLeft.png"));
				} else if (Maps.tiles[i][j] == 49) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/downLeftCorner.png"));
				} else if (Maps.tiles[i][j] == 50) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/downRight.png"));
				} else if (Maps.tiles[i][j] == 51) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/downRightCorner.png"));
				} else if (Maps.tiles[i][j] == 52) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/sideDown.png"));
				} else if (Maps.tiles[i][j] == 53) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/sideUp.png"));
				} else if (Maps.tiles[i][j] == 54) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bottomLeftDesk.png"));
				} else if (Maps.tiles[i][j] == 55) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bottomMiddleDesk.png"));
				} else if (Maps.tiles[i][j] == 56) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bottomRightDesk.png"));
				} else if (Maps.tiles[i][j] == 57) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/topRightDesk.png"));
				} else if (Maps.tiles[i][j] == 58) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/topMiddleDesk.png"));
				} else if (Maps.tiles[i][j] == 59) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/topLeftDesk.png"));
				} else if (Maps.tiles[i][j] == 60) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/topAndRightCorner.png"));
				} else if (Maps.tiles[i][j] == 61) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/topLeftCornerDot.png"));
				} else if (Maps.tiles[i][j] == 72) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/bottomLeftCornerDot.png"));
				} else if (Maps.tiles[i][j] == 73) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/tombstone.png"));
				} else if (Maps.tiles[i][j] == 74) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/dugUpEarth.png"));
				} else if (Maps.tiles[i][j] == 75) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/deadGrass.png"));
				} else if (Maps.tiles[i][j] == 76) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftBottom.png"));
				} else if (Maps.tiles[i][j] == 77) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftBottomRightCorner.png"));
				} else if (Maps.tiles[i][j] == 78) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftRight.png"));
				} else if (Maps.tiles[i][j] == 79) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftTopRightCorner.png"));
				} else if (Maps.tiles[i][j] == 80) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftTop.png"));
				} else if (Maps.tiles[i][j] == 81) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftTopLeftCorner.png"));
				} else if (Maps.tiles[i][j] == 82) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftLeft.png"));
				} else if (Maps.tiles[i][j] == 83) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockShiftBottomLeftCorner.png"));
				} else if (Maps.tiles[i][j] == 84) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/deadTree.png"));
				} else if (Maps.tiles[i][j] == 85) {// beds are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/book.png"));
				} else if (Maps.tiles[i][j] == 86) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/exorcismRoom.png"));
				} else if (Maps.tiles[i][j] == 87) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram1.png"));
				} else if (Maps.tiles[i][j] == 88) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram2.png"));
				} else if (Maps.tiles[i][j] == 89) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram3.png"));
				} else if (Maps.tiles[i][j] == 90) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram4.png"));
				} else if (Maps.tiles[i][j] == 91) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram5.png"));
				} else if (Maps.tiles[i][j] == 92) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram6.png"));
				} else if (Maps.tiles[i][j] == 93) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram7.png"));
				} else if (Maps.tiles[i][j] == 94) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram8.png"));
				} else if (Maps.tiles[i][j] == 95) {
					tileImages[i][j] = ImageIO.read(new File("src/textures/pentagram9.png"));
				} 
			}
		}
	}
}