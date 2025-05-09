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

				if (Maps.tiles[i][j] == 0) {// grass is found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/grassBlockUpdated.png"));
				} else if (Maps.tiles[i][j] == 1) {// trees are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/tree.png"));
				} else if (Maps.tiles[i][j] == 2) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/cobblestone.png"));
				} else if (Maps.tiles[i][j] == 3) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/1.png"));
				} else if (Maps.tiles[i][j] == 4) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/2.png"));
				} else if (Maps.tiles[i][j] == 5) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/3.png"));
				} else if (Maps.tiles[i][j] == 6) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/4.png"));
				} else if (Maps.tiles[i][j] == 7) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/5.png"));
				} else if (Maps.tiles[i][j] == 8) {// cobblestones are found in the file
					tileImages[i][j] = ImageIO.read(new File("src/textures/6.png"));
				} else if (Maps.tiles[i][j] == 9) {// cobblestones are found in the file
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
				}
			}
		}
	}
}
