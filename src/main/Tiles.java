package main;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Tiles {

    // Change to ArrayList of ArrayLists instead of fixed 2D array
    public static ArrayList<ArrayList<BufferedImage>> tileImages = new ArrayList<>();

    public static void tileCreating() throws IOException {

        // Clear previous images if any
        tileImages.clear();

        // Initialize tileImages with the same size as Maps.tiles
        for (int i = 0; i < Maps.tiles.size(); i++) {
            ArrayList<BufferedImage> rowImages = new ArrayList<>();
            for (int j = 0; j < Maps.tiles.get(i).size(); j++) {
                rowImages.add(null); // reserve space
            }
            tileImages.add(rowImages);
        }

        for (int i = 0; i < Maps.tiles.size(); i++) {
            for (int j = 0; j < Maps.tiles.get(i).size(); j++) {

                int tileCode = Maps.tiles.get(i).get(j);

                BufferedImage image = null;
                switch (tileCode) {
                    case 62:
                        image = ImageIO.read(new File("src/textures/grassBlockUpdated.png"));
                        break;
                    case 63:
                        image = ImageIO.read(new File("src/textures/tree.png"));
                        break;
                    case 64:
                        image = ImageIO.read(new File("src/textures/cobblestone.png"));
                        break;
                    case 65:
                        image = ImageIO.read(new File("src/textures/1.png"));
                        break;
                    case 66:
                        image = ImageIO.read(new File("src/textures/2.png"));
                        break;
                    case 67:
                        image = ImageIO.read(new File("src/textures/3.png"));
                        break;
                    case 68:
                        image = ImageIO.read(new File("src/textures/4.png"));
                        break;
                    case 69:
                        image = ImageIO.read(new File("src/textures/5.png"));
                        break;
                    case 70:
                        image = ImageIO.read(new File("src/textures/6.png"));
                        break;
                    case 71:
                        image = ImageIO.read(new File("src/textures/7.png"));
                        break;
                    case 10:
                        image = ImageIO.read(new File("src/textures/8.png"));
                        break;
                    case 11:
                        image = ImageIO.read(new File("src/textures/9.png"));
                        break;
                    case 12:
                        image = ImageIO.read(new File("src/textures/10.png"));
                        break;
                    case 13:
                        image = ImageIO.read(new File("src/textures/11.png"));
                        break;
                    case 14:
                        image = ImageIO.read(new File("src/textures/12.png"));
                        break;
                    case 15:
                        image = ImageIO.read(new File("src/textures/13.png"));
                        break;
                    case 16:
                        image = ImageIO.read(new File("src/textures/14.png"));
                        break;
                    case 17:
                        image = ImageIO.read(new File("src/textures/15.png"));
                        break;
                    case 18:
                        image = ImageIO.read(new File("src/textures/plank.png"));
                        break;
                    case 19:
                        image = ImageIO.read(new File("src/textures/bedTopLeft.png"));
                        break;
                    case 20:
                        image = ImageIO.read(new File("src/textures/bedTopRight.png"));
                        break;
                    case 21:
                        image = ImageIO.read(new File("src/textures/bedBottomLeft.png"));
                        break;
                    case 22:
                        image = ImageIO.read(new File("src/textures/bedBottomRight.png"));
                        break;
                    case 23:
                        image = ImageIO.read(new File("src/textures/wallTop.png"));
                        break;
                    case 24:
                        image = ImageIO.read(new File("src/textures/wallBottom.png"));
                        break;
                    case 25:
                        image = ImageIO.read(new File("src/textures/wallTopSide.png"));
                        break;
                    case 26:
                        image = ImageIO.read(new File("src/textures/wallBottomSide.png"));
                        break;
                    case 27:
                        image = ImageIO.read(new File("src/textures/wallSideTop.png"));
                        break;
                    case 28:
                        image = ImageIO.read(new File("src/textures/wallTopBorder.png"));
                        break;
                    case 29:
                        image = ImageIO.read(new File("src/textures/emptyWallTop.png"));
                        break;
                    case 30:
                        image = ImageIO.read(new File("src/textures/wallTopRight.png"));
                        break;
                    case 31:
                        image = ImageIO.read(new File("src/textures/houseTopCorner.png"));
                        break;
                    case 32:
                        image = ImageIO.read(new File("src/textures/houseWallTopRightCorner.png"));
                        break;
                    case 33:
                        image = ImageIO.read(new File("src/textures/grassUpdated.png"));
                        break;
                    case 34:
                        image = ImageIO.read(new File("src/textures/water.png"));
                        break;
                    case 35:
                        image = ImageIO.read(new File("src/textures/sand.png"));
                        break;
                    case 36:
                        image = ImageIO.read(new File("src/textures/void.png"));
                        break;
                    case 37:
                        image = ImageIO.read(new File("src/textures/doctrineFloor.png"));
                        break;
                    case 38:
                        image = ImageIO.read(new File("src/textures/doctrineWall.png"));
                        break;
                    case 39:
                        image = ImageIO.read(new File("src/textures/doctrineUpperWall.png"));
                        break;
                    case 40:
                        image = ImageIO.read(new File("src/textures/topLeftDoor.png"));
                        break;
                    case 41:
                        image = ImageIO.read(new File("src/textures/topRightDoor.png"));
                        break;
                    case 42:
                        image = ImageIO.read(new File("src/textures/bottomLeftDoor.png"));
                        break;
                    case 43:
                        image = ImageIO.read(new File("src/textures/bottomRightDoor.png"));
                        break;
                    case 44:
                        image = ImageIO.read(new File("src/textures/doctrineTopWall.png"));
                        break;
                    case 45:
                        image = ImageIO.read(new File("src/textures/doctrineOtherTopWall.png"));
                        break;
                    case 46:
                        image = ImageIO.read(new File("src/textures/doctrineBottomWall.png"));
                        break;
                    case 47:
                        image = ImageIO.read(new File("src/textures/doctrineEmptyWall.png"));
                        break;
                    case 48:
                        image = ImageIO.read(new File("src/textures/downLeft.png"));
                        break;
                    case 49:
                        image = ImageIO.read(new File("src/textures/downLeftCorner.png"));
                        break;
                    case 50:
                        image = ImageIO.read(new File("src/textures/downRight.png"));
                        break;
                    case 51:
                        image = ImageIO.read(new File("src/textures/downRightCorner.png"));
                        break;
                    case 52:
                        image = ImageIO.read(new File("src/textures/sideDown.png"));
                        break;
                    case 53:
                        image = ImageIO.read(new File("src/textures/sideUp.png"));
                        break;
                    case 54:
                        image = ImageIO.read(new File("src/textures/bottomLeftDesk.png"));
                        break;
                    case 55:
                        image = ImageIO.read(new File("src/textures/bottomMiddleDesk.png"));
                        break;
                    case 56:
                        image = ImageIO.read(new File("src/textures/bottomRightDesk.png"));
                        break;
                    case 57:
                        image = ImageIO.read(new File("src/textures/topRightDesk.png"));
                        break;
                    case 58:
                        image = ImageIO.read(new File("src/textures/topMiddleDesk.png"));
                        break;
                    case 59:
                        image = ImageIO.read(new File("src/textures/topLeftDesk.png"));
                        break;
                    case 60:
                        image = ImageIO.read(new File("src/textures/topAndRightCorner.png"));
                        break;
                    case 61:
                        image = ImageIO.read(new File("src/textures/topLeftCornerDot.png"));
                        break;
                    case 72:
                        image = ImageIO.read(new File("src/textures/bottomLeftCornerDot.png"));
                        break;
                    case 73:
                        image = ImageIO.read(new File("src/textures/tombstone.png"));
                        break;
                    case 74:
                        image = ImageIO.read(new File("src/textures/dugUpEarth.png"));
                        break;
                    case 75:
                        image = ImageIO.read(new File("src/textures/deadGrass.png"));
                        break;
                    case 76:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftBottom.png"));
                        break;
                    case 77:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftBottomRightCorner.png"));
                        break;
                    case 78:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftRight.png"));
                        break;
                    case 79:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftTopRightCorner.png"));
                        break;
                    case 80:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftTop.png"));
                        break;
                    case 81:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftTopLeftCorner.png"));
                        break;
                    case 82:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftLeft.png"));
                        break;
                    case 83:
                        image = ImageIO.read(new File("src/textures/grassBlockShiftBottomLeftCorner.png"));
                        break;
                    case 84:
                        image = ImageIO.read(new File("src/textures/deadTree.png"));
                        break;
                    case 85:
                        image = ImageIO.read(new File("src/textures/book.png"));
                        break;
                    default:
                        // optionally handle unknown tile codes here
                        break;
                }

                tileImages.get(i).set(j, image);
            }
        }
    }
}
