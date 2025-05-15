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

	public static void calculatedResult() {
		int size = Minigame.points.size();
		if (size < 8) {
			System.out.println("Not enough points to determine shape.");
			return;
		}

		Point[] p = new Point[8];
		p[0] = Minigame.points.get(0);
		p[1] = Minigame.points.get(size - 1);
		p[2] = Minigame.points.get(size / 2 - 1);
		p[3] = Minigame.points.get(size / 2);
		p[4] = Minigame.points.get(size / 2 + 1);
		p[5] = Minigame.points.get(size / 4);
		p[6] = Minigame.points.get(size * 3 / 4);
		if (size * 7 / 8 < size) {
			p[7] = Minigame.points.get(size * 7 / 8);
		} else {
			p[7] = Minigame.points.get(size - 1);
		}

		int threshold = 30;
		boolean allHorizontal = true;
		boolean allVertical = true;

		for (int i = 1; i < p.length; i++) {
			if (Math.abs(p[0].y - p[i].y) >= threshold) {
				allHorizontal = false;
			}
			if (Math.abs(p[0].x - p[i].x) >= threshold) {
				allVertical = false;
			}
		}

		String currentShape;
		if (allHorizontal) {
			currentShape = "horizontal";
		} else if (allVertical) {
			currentShape = "vertical";
		} else {
			currentShape = "none";
		}
		if (currentShape.equals("horizontal")) {
			Minigame.color = 1;
		} else if (currentShape.equals("vertical")) {
			Minigame.color = 2;
		}
		System.out.println("Detected shape: " + currentShape);

		Minigame.points.clear();
	}

	public static void drawPoints(Graphics2D g2) {

		for (int i = 1; i < points.size(); i++) {
			Point p1 = points.get(i - 1);
			Point p2 = points.get(i);
			g2.setColor(Color.RED);

			// Finds an imaginary point between two points
			// Predicts the position of a point in between p1 and p2.
			double midPoint = p1.distance(p2);// Finds the distance between two points
			int spacing = (int) midPoint / 2; // Number of ovals (points) between p1 and p2 to where we want to draw the
												// predicted ovals
			for (int j = 0; j <= spacing; j++) {// Draws the ovals in between the two points
				// Linear Inerpolation used get x and y coordinates of predicted ovals.
				// Found on: https://www.geeksforgeeks.org/linear-interpolation-formula/
				double t = (double) j / spacing;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				g2.fillOval(x - 5, y - 5, 10, 10); // Draws the ovals
			}
		}

	}
}
