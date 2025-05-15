package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Minigame {

	public static ArrayList<Point> points = new ArrayList<>();
	public static boolean isExorcising;
	public static int color;;

	public static void startExorcising() {
		isExorcising = true;
	}

	public static void drawPoints(Graphics2D g2) {

		for (int i = 1; i < points.size(); i++) {
			Point p1 = points.get(i - 1);
			Point p2 = points.get(i);
			/*
			 * if (color == 1) { g2.setColor(Color.RED); } else if (color == 2) {
			 * g2.setColor(Color.BLUE); }
			 */

			g2.setColor(new Color((int) (Math.random() * 0x1000000))); // Random color for each point

			// Finds an imaginary point between the point before and after
			// Predicts the position of a point in between p1 and p2.
			double midPoint = p1.distance(p2);
			int spacing = (int) midPoint / 2; // Number of ovals (points) between p1 and p2 to where we want to draw the
												// predicted ovals
			for (int j = 0; j <= spacing; j++) {// Draws the ovals in between the two points
				// Linear Inerpolation used get x and y coordinates of predicted ovals. 
				//Found on: https://www.geeksforgeeks.org/linear-interpolation-formula/
				double t = (double) j / spacing;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				g2.fillOval(x - 5, y - 5, 10, 10); // Draws the ovals
			}
		}

	}
}
