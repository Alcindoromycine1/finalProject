package main;

import Horror.Jumpscare;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Maps {

	// WORLD SETTINGS
	int maxWorldCol;
	int maxWorldRow;
	final int worldWidth = 48 * maxWorldCol; // world width in pixels (2400 pixels)
	final int worldHeight = 48 * maxWorldRow; // world height in pixels (2400 pixels)

	public ArrayList<ArrayList<Integer>> tiles = new ArrayList<>();
	public ArrayList<int[]> treePositions = new ArrayList<>();
	public ArrayList<int[]> housePositions = new ArrayList<>();
	public ArrayList<int[]> bedPositions = new ArrayList<>();
	public ArrayList<int[]> houseWallPositions = new ArrayList<>();
	public ArrayList<int[]> grassPositions = new ArrayList<>();
	public ArrayList<int[]> waterPositions = new ArrayList<>();
	public ArrayList<int[]> bookPositions = new ArrayList<>();

	// Player p = new Player();
	
	Tiles t;
	Npc npc;
	Jumpscare j;
	Player p;
	Items items;
	
	int worldX;
	int worldY;
	int currentMap;
	
    public Maps (GamePanel gp) {
    	
    	this.worldX = gp.getWorldX();
    	this.worldY = gp.getWorldY();
    	
    	this.items = gp.it;
    	this.npc = gp.n;
    	this.t = gp.t;
    	this.j = gp.j;
    	this.p = gp.p;
    	
    }
	
    public void updateMapValues(int worldX, int worldY) {
		this.worldX = worldX;
		this.worldY = worldY;
	}
		
    public void setTiles(Tiles t) {
    	this.t = t;
    }
	public String changeMap(Graphics2D g, int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");// intro map
			currentMap = 1;
		} else if (mapToChange == 2) {

			mapIntro("src/maps/mapHouse.txt");// house map
			currentMap = 2;
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");// open map
			currentMap = 3;
		} else if (mapToChange == 4) {
			mapIntro("src/maps/doctrine.txt");
			currentMap = 4;
		}
		return "-1";// no map found

	}

	public String changeMap(int mapToChange) {
		if (mapToChange == 1) {
			mapIntro("src/maps/mapIntro.txt");
			currentMap = 1;
		} else if (mapToChange == 2) {
			mapIntro("src/maps/mapHouse.txt");
			currentMap = 2;
		} else if (mapToChange == 3) {
			mapIntro("src/maps/openMap.txt");
			currentMap = 3;
		} else if (mapToChange == 4) {
			mapIntro("src/maps/doctrine.txt");
			currentMap = 4;
		} else if (mapToChange == 5) {
			mapIntro("src/maps/blank.txt");
			currentMap = 5;
		}
		return "-1";

	}

	public void mapIntro(String filePath) {
		tiles.clear(); // Clear old tile data before loading new map

		try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
			String lines;
			while ((lines = r.readLine()) != null) {
				lines = lines.trim();
				if (lines.isEmpty())
					continue;

				String[] val = lines.split("\\s+");
				ArrayList<Integer> rowList = new ArrayList<>();

				for (String s : val) {
					rowList.add(Integer.parseInt(s));
				}

				tiles.add(rowList);
			}

			// Set world dimensions based on map size
			maxWorldRow = tiles.size();
			maxWorldCol = tiles.get(0).size();

		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void findIntroHouse() throws IOException {

		BufferedReader r = new BufferedReader(new FileReader("src/maps/mapIntro.txt"));

	}

	public void findTrees() throws IOException {

		BufferedReader r = new BufferedReader(new FileReader("src/maps/mapIntro.txt"));
		String lines = "";
		int numberOfLines = 0;
		// Count the number of lines in the file
		// Iterate through the 2D array to find occurrences of 1
		for (int row = 0; row < tiles.size(); row++) {
			for (int col = 0; col < tiles.get(row).size(); col++) {
				if (tiles.get(row).get(col) == 63) {
					treePositions.add(new int[] { col * 48, row * 48 });// stores the location of where the trees are
				} else if (tiles.get(row).get(col) == 65 || tiles.get(row).get(col) == 66
						|| tiles.get(row).get(col) == 67 || tiles.get(row).get(col) == 68
						|| tiles.get(row).get(col) == 69 || tiles.get(row).get(col) == 70
						|| tiles.get(row).get(col) == 71 || tiles.get(row).get(col) == 10
						|| tiles.get(row).get(col) == 11 || tiles.get(row).get(col) == 12
						|| tiles.get(row).get(col) == 13 || tiles.get(row).get(col) == 14
						|| tiles.get(row).get(col) == 15 || tiles.get(row).get(col) == 16
						|| tiles.get(row).get(col) == 17 || tiles.get(row).get(col) == 84
						|| tiles.get(row).get(col) == 73 || tiles.get(row).get(col) == 40
						|| tiles.get(row).get(col) == 41 || tiles.get(row).get(col) == 42
						|| tiles.get(row).get(col) == 43 || tiles.get(row).get(col) == 44
						|| tiles.get(row).get(col) == 45 || tiles.get(row).get(col) == 46
						|| tiles.get(row).get(col) == 47 || tiles.get(row).get(col) == 54
						|| tiles.get(row).get(col) == 55 || tiles.get(row).get(col) == 56
						|| tiles.get(row).get(col) == 57 || tiles.get(row).get(col) == 58
						|| tiles.get(row).get(col) == 59 || tiles.get(row).get(col) == 39
						|| tiles.get(row).get(col) == 38) {
					housePositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles.get(row).get(col) == 21 || tiles.get(row).get(col) == 22
						|| tiles.get(row).get(col) == 19 || tiles.get(row).get(col) == 20) {
					bedPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the beds are
				} else if (tiles.get(row).get(col) == 23 || tiles.get(row).get(col) == 24
						|| tiles.get(row).get(col) == 25 || tiles.get(row).get(col) == 26
						|| tiles.get(row).get(col) == 27 || tiles.get(row).get(col) == 28
						|| tiles.get(row).get(col) == 29 || tiles.get(row).get(col) == 30
						|| tiles.get(row).get(col) == 31 || tiles.get(row).get(col) == 32) {
					houseWallPositions.add(new int[] { col * 48, row * 48 });// stores the location of where the walls
																				// are
				} else if (tiles.get(row).get(col) == 33) {
					grassPositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles.get(row).get(col) == 34) {
					waterPositions.add(new int[] { col * 48, row * 48 });
				} else if (tiles.get(row).get(col) == 85) {
					bookPositions.add(new int[] { col * 48, row * 48 });
				}
			}
		}
	}

	public boolean incrementingUp = true;
	public int yVal = -5;
	
	public void drawGhostLeft(Graphics2D g2, String shape, boolean completed) {
		//try {
			//items.minigameGhost(g2, 1200, 820 + yVal, shape, 250, 196, 0);
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
	}
	public void drawExorcismRoom(Graphics2D g2) throws IOException {

		try {

			BufferedImage exorcismRoom = ImageIO.read(new File(
					"src/textures/the_room_where_spiritual_purification_and_demonic_entity_expulsion_through_sacred_rituals_occurs_with_the_presence_of_religion_certified_exorcists_in_white_robes.png"));
			g2.drawImage(exorcismRoom, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.setColor(new Color(87, 44, 19));
		g2.fillRect(0, 0, 280, 71);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.setColor(Color.WHITE);
		g2.drawString("Draw:", 15, 40);
		
		if (yVal == -5) {
			incrementingUp = true;
		} else if (yVal == 30) {
			incrementingUp = false;
		}
		
		if (incrementingUp) {
			yVal++;
		} else {
			yVal--;
		}
		
		drawGhostLeft(g2, "Square", false);
		//ghost one
	//	items.minigameGhost(g2, 1000, 860 + yVal, "Circle", 250, 196);
		
	//	g2.minigameGhost(g2, 1200, 820 + yVal, "Square", 250, 196);

	}

	public void camera(Graphics g2, GamePanel gp) {
    try {
        for (int worldRow = 0; worldRow < maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < maxWorldCol; worldCol++) {

                // Use instance variables for camera offset
                int screenX = worldCol * 48 - gp.getWorldX();
                int screenY = worldRow * 48 - gp.getWorldY();

                // Only draw tiles that are on the screen
                if (screenX + 48 > 0 && screenX < 768 && screenY + 48 > 0 && screenY < 576) {
                    g2.drawImage(t.tileImages.get(worldRow).get(worldCol), screenX, screenY, 48, 48, null);
                }
            }
        }
    } catch (Exception e) {
       // e.printStackTrace();
    }
}


	// change scene variables
	int fadeValue = 0;
	int stepCount = 0;
	int hasFaded = 0;

	public void fading(Graphics2D g2, Tiles t, int newMap, int originalMap) throws IOException {

		if (stepCount == 0) {// fade out
			Color fadeColor = new Color(0, 0, 0, fadeValue);
			g2.setColor(fadeColor);
			g2.fillRect(0, 0, 768, 576);
			fadeValue += 2;
			if (fadeValue >= 255) {
				fadeValue = 255;
				stepCount = 1;
			}

		}

		else if (stepCount == 1) {// change map
			try {

				// Clear old map data
				treePositions.clear();
				housePositions.clear();
				bedPositions.clear();
				houseWallPositions.clear();
				grassPositions.clear();
				waterPositions.clear();

				t.tileImages.clear();

				for (int i = 0; i < maxWorldRow; i++) {
					ArrayList<BufferedImage> row = new ArrayList<>();
					for (int j = 0; j < maxWorldCol; j++) {
						row.add(null); // initialize each cell with null
					}
					t.tileImages.add(row);
				}

				g2.fillRect(-10000, -10000, 768 + 20000, 576 + 20000); // Clear the screen
				// Change to the new map
				if (goOut) {
					changeMap(newMap);
				} else if (!goOut) {
					changeMap(originalMap);
				}
				// Load the new map's tiles
				// Reinitialize the new map's data
				t.tileCreating();
				findIntroHouse();
				findTrees();
				worldX = 288;
				worldY = 216;
			} catch (IOException e) {
				e.printStackTrace();
			}

			stepCount = 2;
		}

		else if (stepCount == 2) {// fade in
			g2.setColor(new Color(0, 0, 0, fadeValue));
			g2.fillRect(0, 0, 768, 576);
			fadeValue -= 2;
			if (fadeValue <= 0) {
				fading = false;
				p.disableCharacterMovement();
				stepCount = -1;
				fadeValue = 0;
				hasFaded++;

			}
		}
		if (hasFaded == 2) {
			if (stepCount == -1 && !hasJumpscared) {
				hasJumpscared = true;
				stepCount = 0;
			}
			if (stepCount == -1 && !hasDoctrined) {
				hasDoctrined = true;
				stepCount = 0;
			}
		} else if (hasFaded == 1) {
			if (stepCount == -1 && !hasDoctrined && hasJumpscared) {
				hasDoctrined = true;
				stepCount = 0;
			}
		}
	}

	boolean fading = false;
	boolean goOut = false;
	boolean hasJumpscared = false;
	boolean hasDoctrined = false;

	public void fade(int changeMap, int oldMap, Graphics2D g2, int worX, int worY, int width, int height, int oldworX,
			int oldworY, int oldWidth, int oldHeight) throws IOException {

		
		if (Input.changeMapPressed && worldX >= oldworX && worldX <= oldworX + oldWidth
				&& worldY >= oldworY && worldY <= oldworY + oldHeight) {
			fading = true;
			p.disableCharacterMovement();
			Input.changeMapPressed = false;
			j.setJumpscare(true);
		}
		if (!fading && Input.changeMapPressed && worldX >= worX && worldX <= worX + width
				&& worldY >= worY && worldY <= worY + height) {
			fading = true;
			p.disableCharacterMovement();
			stepCount = 0;
			goOut = true;
		}
		if (fading) {
			fading(g2, t, oldMap, changeMap);
		}
	}

	public boolean doneNightmare = false;
	public boolean inNightmare = false;
	public boolean usingBed = false;

	public void nightmare(Graphics2D g2) throws IOException {
		if (usingBed && !inNightmare && !doneNightmare) {
			items.inConfirmation = true;
			items.confirmation(g2, "Do you want to sleep?", 180);

			if (items.yesPressed) {
				inNightmare = true;
				usingBed = false;
				items.yesPressed = false;
				items.inConfirmation = false;
			}
			if (items.noPressed) {
				inNightmare = false;
				usingBed = false;
				items.noPressed = false;
				items.inConfirmation = false;
			}
			return;
		}

		if (inNightmare) {
			System.out.println("In nightmare");
		}

		if (doneNightmare) {
			inNightmare = false;
			npc.doctor(g2);
		}
	}

	public void setT(Tiles t) {
		this.t = t;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public void setJ(Jumpscare j) {
		this.j = j;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public void setItems(Items items) {
		this.items = items;
	}
	
}
