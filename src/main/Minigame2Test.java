package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Minigame2Test {

	public static ArrayList<Point> points = new ArrayList<>();
	public static boolean isExorcising = false;
	public static int color;

	public static void startExorcising() {
		isExorcising = true;
	}

	public static void stopExorcising() {
		isExorcising = false;
	}

	static int newSumX = 0;
	static int newSumY = 0;
	public static ArrayList<Point> newPoints = new ArrayList<>();
	static Point newCentroid;
	static Point centroid;
	static int sumX = 0;
	static int sumY = 0;
	static ArrayList<Point> proper = new ArrayList<>();
	static int count = 0;

	static int circleX = 500;
	static int circleY = 300;

	static double originalArea = 0;
	static ArrayList<Point> shapePoints = new ArrayList<>();

	public static double calculateStandardDeviation(ArrayList<Point> points, boolean returnX) {
		if (points.size() == 0) {
			return 0;
		}

		double sum = 0;
		for (Point p : points) {
			sum += returnX ? p.x : p.y;
		}

		double mean = sum / points.size();
		double variance = 0;

		for (Point p : points) {
			double value = returnX ? p.x : p.y;
			variance += Math.pow(value - mean, 2);
		}
		variance /= points.size();
		return Math.sqrt(variance);
	}

	public static void circle(Graphics2D g2) {
		int radius = 50;
		originalArea = Math.PI * Math.pow(radius, 2);
		shapePoints.clear();

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
			Point current = newPoints.get(i);
			Point next = newPoints.get((i + 1) % newPoints.size());
			area += (current.x * next.y) - (next.x * current.y);
		}
		return Math.abs(area * 0.5);
	}

	public static void newCentroid() {
		newSumX = 0;
		newSumY = 0;
		newPoints.clear();

		if (Minigame.count == 0) return;

		centroid = new Point(sumX / count, sumY / count);
		Point difference = new Point(circleX - centroid.x, circleY - centroid.y);

		for (Point point : Minigame.points) {
			Point shifted = new Point(point.x + difference.x, point.y + difference.y);
			newPoints.add(shifted);
			newSumX += shifted.x;
			newSumY += shifted.y;
		}

		newCentroid = new Point(newSumX / count, newSumY / count);
	}

	static boolean ready = false;

	public static double scaleFactor() {
		double area = calculateArea();
		return Math.sqrt(area) / Math.sqrt(originalArea);
	}

	public static boolean isValid(int threshold) {
		ArrayList<Point> interpPoints = interpolateNewPoints(2);

		// 🔧 Normalize shape and input for better comparison
		normalizePoints(interpPoints);
		normalizePoints(shapePoints);

		for (Point ref : shapePoints) {
			double minDistance = Double.MAX_VALUE;
			for (Point input : interpPoints) {
				double distance = ref.distance(input);
				if (distance < minDistance) {
					minDistance = distance;
				}
			}
			if (minDistance > threshold) return false;
		}
		return true;
	}

	public static ArrayList<Point> interpolateNewPoints(int pointsBetween) {
		ArrayList<Point> interpolated = new ArrayList<>();
		if (newPoints.size() < 2) return interpolated;

		for (int i = 0; i < newPoints.size() - 1; i++) {
			Point p1 = newPoints.get(i);
			Point p2 = newPoints.get(i + 1);

			for (int j = 0; j <= pointsBetween; j++) {
				double t = (double) j / pointsBetween;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				interpolated.add(new Point(x, y));
			}
		}
		return interpolated;
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
		p[7] = Minigame.points.get(Math.min(size * 7 / 8, size - 1));

		int threshold = 30;
		boolean allHorizontal = true;
		boolean allVertical = true;

		for (int i = 1; i < p.length; i++) {
			if (Math.abs(p[0].y - p[i].y) >= threshold) allHorizontal = false;
			if (Math.abs(p[0].x - p[i].x) >= threshold) allVertical = false;
		}

		if (allHorizontal) {
			Minigame.color = 1;
		} else if (allVertical) {
			Minigame.color = 2;
		}
		Minigame.points.clear();
	}

	public static void drawPoints(Graphics2D g2) {
		System.out.println("X Deviance: " + calculateStandardDeviation(points, true));
		System.out.println("Y Deviance: " + calculateStandardDeviation(points, false));
		for (int i = 1; i < points.size(); i++) {
			Point p1 = points.get(i - 1);
			Point p2 = points.get(i);
			g2.setColor(Color.RED);

			double midPoint = p1.distance(p2);
			int spacing = (int) midPoint / 2;
			for (int j = 0; j <= spacing; j++) {
				double t = (double) j / spacing;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				g2.fillOval(x - 5, y - 5, 10, 10);
			}
		}
	}

	// 🔧 NEW: Utility to center, scale, and rotate input
	public static void normalizePoints(ArrayList<Point> pts) {
		if (pts.size() < 2) return;

		// Translate to centroid
		Point centroid = calculateCentroid(pts);
		for (int i = 0; i < pts.size(); i++) {
			Point p = pts.get(i);
			pts.set(i, new Point(p.x - centroid.x, p.y - centroid.y));
		}

		// Uniform scale to 100x100 box
		Rectangle bounds = calculateBounds(pts);
		double scale = 100.0 / Math.max(bounds.width, bounds.height);
		for (int i = 0; i < pts.size(); i++) {
			Point p = pts.get(i);
			pts.set(i, new Point((int)(p.x * scale), (int)(p.y * scale)));
		}

		// Rotate so the first point is aligned with x-axis
		Point p0 = pts.get(0);
		double angle = Math.atan2(p0.y, p0.x);
		double cos = Math.cos(-angle);
		double sin = Math.sin(-angle);

		for (int i = 0; i < pts.size(); i++) {
			Point p = pts.get(i);
			int x = (int)(p.x * cos - p.y * sin);
			int y = (int)(p.x * sin + p.y * cos);
			pts.set(i, new Point(x, y));
		}
	}

	public static Point calculateCentroid(ArrayList<Point> pts) {
		int sumX = 0, sumY = 0;
		for (Point p : pts) {
			sumX += p.x;
			sumY += p.y;
		}
		return new Point(sumX / pts.size(), sumY / pts.size());
	}

	public static Rectangle calculateBounds(ArrayList<Point> pts) {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for (Point p : pts) {
			minX = Math.min(minX, p.x);
			minY = Math.min(minY, p.y);
			maxX = Math.max(maxX, p.x);
			maxY = Math.max(maxY, p.y);
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
}
