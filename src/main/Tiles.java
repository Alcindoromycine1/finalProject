package main;
 
 import java.awt.image.*;
 import java.awt.*;
 import java.io.*;
 
 import javax.imageio.ImageIO;
 
 public class Tiles {
 
 	public static BufferedImage tileImages[][] = new BufferedImage[12][16];
 	Maps m = new Maps();
 
 	public void tileCreating() throws IOException {
 
 		for (int i = 0; i < Maps.tiles.length; i++) {
 			for (int j = 0; j < Maps.tiles[i].length; j++) {
 
 				if (Maps.tiles[i][j] == 0) {// grass is found in the file
 					tileImages[i][j] = ImageIO.read(new File("src/textures/grass.png"));
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
 				}
 			}
 		}
 	}
 
 	public void grassTileBehaviour() {
 
 	}
 
 	public void treeTileBehaviour() {
 
 		// if(collision == true) {
 
 		// }
 
 	}
 
 }

