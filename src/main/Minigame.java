package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

public class Minigame {

	public static ArrayList<Point> points = new ArrayList<>();
	public static boolean isExorcising;
	public static int color;;

	public static void startExorcising() {
		isExorcising = true;
	}

	static int sumX = 0;
	static int sumY = 0;
	static int maxPointing = 1000 / 5;
	static ArrayList<Point> proper = new ArrayList<>();
	static int count = 0;

	static int circleX = 500;
	static int circleY = 300;

	static double originalArea = 0;
	static ArrayList<Point> shapePoints = new ArrayList<>();

	public static void circle(Graphics2D g2) {
		int radius = 75;
		g2.setColor(Color.WHITE);
		g2.fillOval(500 - radius, 300 - radius, radius * 2, radius * 2);
		originalArea = Math.PI * Math.pow(radius, 2);
		shapePoints.clear();

		int circleX = 500;
		int circleY = 300;
		int numPoints = 100;
		for (int i = 0; i < numPoints; i++) {
			double theta = 2 * Math.PI * i / numPoints;
			int x = (int) (circleX + radius * Math.cos(theta));
			int y = (int) (circleY + radius * Math.sin(theta));
			shapePoints.add(new Point(x, y));
		}
	}

	public static void calculation() {
		sumX = 0;
		sumY = 0;
		count = 0;
		proper.clear();

		for (Point point : Minigame.points) {
			proper.add(point);
			sumX += point.x;
			sumY += point.y;
			count++;
		}
	}

	public static double calculateArea() {
		double area = 0;

		for (int i = 0; i < newPoints.size(); i++) {
			Point current = Minigame.newPoints.get(i);
			Point next = Minigame.newPoints.get((i + 1) % newPoints.size());

			area += (current.x * next.y) - (next.x * current.y);
		}
		return Math.abs(area * 0.5);
	}

	static int newSumX = 0;
	static int newSumY = 0;
	static ArrayList<Point> newPoints = new ArrayList<>();
	static Point newCentroid;
	static Point centroid;

	public static void newCentroid() {
		newSumX = 0;
		newSumY = 0;
		newPoints.clear();

		if (Minigame.count == 0) {
			return;
		}

		centroid = new Point(Minigame.sumX / Minigame.count, Minigame.sumY / Minigame.count);
		Point difference = new Point(Minigame.circleX - centroid.x, Minigame.circleY - centroid.y);

		for (Point point : Minigame.points) {
			Point shiftedPoint = new Point(point.x + difference.x, point.y + difference.y);
			newPoints.add(shiftedPoint);
			newSumX += shiftedPoint.x;
			newSumY += shiftedPoint.y;

		}

		newCentroid = new Point(newSumX / Minigame.count, newSumY / Minigame.count);
	}

	static boolean ready = false;

	public static double scaleFactor() {

		double scale = 0;
		double area = calculateArea();
		scale = Math.sqrt(area) / Math.sqrt(originalArea);

		return scale;

	}

	public static boolean isValid(int threshold) {
		boolean valid = false;
		int count = 0;

		// Cache once outside loops
		ArrayList<Point> interpPoints = interpolateNewPoints(2);

		for (Point currentPoint : shapePoints) {
			double minDistance = Double.MAX_VALUE;
			Point closestPoint = new Point(10000, 10000);

			for (Point currentInterpPoint : interpPoints) {
				double distance = currentPoint.distance(currentInterpPoint);
				if (distance < minDistance) {
					minDistance = distance;
					closestPoint = currentInterpPoint;
				}
			}

			if (closestPoint == null || Math.abs(closestPoint.x - currentPoint.x) > threshold
					|| Math.abs(closestPoint.y - currentPoint.y) > threshold) {
				return false;
			}

		}

		return true;
	}

	public static ArrayList<Point> interpolateNewPoints(int pointsBetween) {
		ArrayList<Point> interpolatedNewPoints = new ArrayList<>();

		if (newPoints.size() < 2)
			return interpolatedNewPoints;

		for (int i = 0; i < newPoints.size() - 1; i++) {
			Point p1 = newPoints.get(i);
			Point p2 = newPoints.get(i + 1);

			for (int j = 0; j <= pointsBetween; j++) {
				double t = (double) j / pointsBetween;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				interpolatedNewPoints.add(new Point(x, y));
			}
		}

		return interpolatedNewPoints;
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
		// System.out.println("Detected shape: " + currentShape);

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
				// Linear Interpolation used get x and y coordinates of predicted ovals.
				// Found on: https://www.geeksforgeeks.org/linear-interpolation-formula/
				double t = (double) j / spacing;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				g2.fillOval(x - 5, y - 5, 10, 10); // Draws the ovals
			}
		}

	}
}
