package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Minigame {

	private ArrayList<Point> points = new ArrayList<>();
	private boolean isExorcising = false;
	private int color;
	private int sumX = 0;
	private int sumY = 0;
	private ArrayList<Point> proper = new ArrayList<>();
	private int count = 0;

	private int circleX = 500;
	private int circleY = 300;

	private double originalArea = 0;
	private ArrayList<Point> shapePoints = new ArrayList<>();

 	private int newSumX = 0;
	private int newSumY = 0;
	private ArrayList<Point> newPoints = new ArrayList<>();
	private Point newCentroid;
	private Point centroid;
	
	private String currentShape = " ";
	
	private boolean ready = false;
	
	private boolean triangleLogic = false;
	private boolean circleLogic = false;
	
	
	public void startExorcising() {
		isExorcising = true;
	}

	public void circle() {
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

	public void zigzag() {
		shapePoints.clear();

		Point vertex1 = new Point(0, 100);
		Point vertex2 = new Point(100, 100);
		Point vertex3 = new Point(0, 0);
		Point vertex4 = new Point(100, 0);
		newPoints.add(vertex1);
		newPoints.add(vertex2);
		newPoints.add(vertex3); 
		newPoints.add(vertex4);

		int numberOfPoints = 50;// number of points in every line

		// top horizontal line
		for (int i = 0; i <= numberOfPoints; i++) {
			double t = (double) i / numberOfPoints;
			int x = (int) (vertex1.x + t * (vertex2.x - vertex1.x));
			int y = (int) (vertex1.y + t * (vertex2.y - vertex1.y));
			shapePoints.add(new Point(x, y));
		}

		// diagonal line
		for (int i = 0; i <= numberOfPoints; i++) {
			double t = (double) i / numberOfPoints;
			int x = (int) (vertex2.x + t * (vertex3.x - vertex2.x));
			int y = (int) (vertex2.y + t * (vertex3.y - vertex2.y));
			shapePoints.add(new Point(x, y));
		}

		// bottom horizontal line
		for (int i = 0; i <= numberOfPoints; i++) {
			double t = (double) i / numberOfPoints;
			int x = (int) (vertex3.x + t * (vertex4.x - vertex3.x));
			int y = (int) (vertex3.y + t * (vertex4.y - vertex3.y));
			shapePoints.add(new Point(x, y));
		}

		originalArea = calculateArea();// area of the zigzag shape
	}

	public void triangle() {
		shapePoints.clear();

		Point vertex1 = new Point(455, 210);
		Point vertex2 = new Point(599, 305);
		Point vertex3 = new Point(444, 383);
		originalArea = Math.abs((vertex1.x * (vertex2.y - vertex3.y) + vertex2.x * (vertex3.y - vertex1.y)
				+ vertex3.x * (vertex1.y - vertex2.y)) / 2.0);// Area of the equilateral triangle

		// Interpolates the points that are between the vertices of the triangle
		int numberOfPoints = 33;// how many points each side of the triangle will have
		// Linear interpolation to find the points between the vertices
		// https://www.geeksforgeeks.org/linear-interpolation-formula/

		// Interpolate from vertex1 to vertex2
		for (int j = 0; j < numberOfPoints; j++) {
			double t = (double) j / numberOfPoints;
			int x = (int) (vertex1.x + t * (vertex2.x - vertex1.x));
			int y = (int) (vertex1.y + t * (vertex2.y - vertex1.y));
			shapePoints.add(new Point(x, y));
		}

		// Interpolate from vertex2 to vertex3
		for (int j = 0; j < numberOfPoints; j++) {
			double t = (double) j / numberOfPoints;
			int x = (int) (vertex2.x + t * (vertex3.x - vertex2.x));
			int y = (int) (vertex2.y + t * (vertex3.y - vertex2.y));
			shapePoints.add(new Point(x, y));
		}

		// Interpolate from vertex3 to vertex1
		for (int j = 0; j < numberOfPoints; j++) {
			double t = (double) j / numberOfPoints;
			int x = (int) (vertex3.x + t * (vertex1.x - vertex3.x));
			int y = (int) (vertex3.y + t * (vertex1.y - vertex3.y));
			shapePoints.add(new Point(x, y));
		}
	}

	public void calculation() {
		sumX = 0;
		sumY = 0;
		count = 0;
		proper.clear();

		for (Point point : points) {
			proper.add(point);
			sumX += point.x;
			sumY += point.y;
			count++;
		}
	}

	public double calculateArea() {
		double area = 0;

		for (int i = 0; i < newPoints.size(); i++) {
			Point current = newPoints.get(i);
			Point next = newPoints.get((i + 1) % newPoints.size());

			area += (current.x * next.y) - (next.x * current.y);
		}
		return Math.abs(area * 0.5);
	}



	public void newCentroid() {
		newSumX = 0;
		newSumY = 0;
		newPoints.clear();

		if (count == 0) {
			return;
		}

		centroid = new Point(sumX / count, sumY / count);
		Point difference = new Point(circleX - centroid.x, circleY - centroid.y);

		for (Point point : points) {
			Point shiftedPoint = new Point(point.x + difference.x, point.y + difference.y);
			newPoints.add(shiftedPoint);
			newSumX += shiftedPoint.x;
			newSumY += shiftedPoint.y;

		}

		newCentroid = new Point(newSumX / count, newSumY / count);
	}

	

	public double scaleFactor() {

		double scale = 0;
		double area = calculateArea();
		scale = Math.sqrt(area) / Math.sqrt(originalArea);

		return scale;

	}
	
	

	public boolean isValid(int threshold) {
		double distanceSum = 0;

		ArrayList<Point> interpPoints = interpolateNewPoints(2);

		for (Point currentPoint : shapePoints) {
			double minDistance = Double.MAX_VALUE;

			for (Point interpPoint : interpPoints) {
				double distance = currentPoint.distance(interpPoint);
				if (distance < minDistance) {
					minDistance = distance;
				}
			}

			distanceSum += minDistance;
		}

		double averageDistance = distanceSum / shapePoints.size();
		return averageDistance <= threshold;
	}

	public ArrayList<Point> interpolateNewPoints(int pointsBetween) {
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

	

	public void calculatedResult() {
		int size = points.size();
		if (size < 4) {
			System.out.println("Not enough points to determine shape.");
			return;
		}

		Point[] p = new Point[4];
		p[0] = points.get(0);
		p[1] = points.get(size - 1);
		p[2] = points.get(size / 2);
		p[3] = points.get(size / 3);

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

		if (allHorizontal) {
			currentShape = "horizontal";
			color = 1;
		} else if (allVertical) {
			currentShape = "vertical";
			color = 2;
		} else {
			currentShape = "none";
		}

		// System.out.println("Line: " + currentShape);

		points.clear();
	}

	public void drawPoints(Graphics2D g2) {

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
	
	// Getters and Setters
	public ArrayList<Point> getPoints() {
		return points;
	}

	public boolean getIsExorcising() {
		return isExorcising;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public String getCurrentShape() {
		return currentShape;
	}

	public void setCurrentShape(String currentShape) {
		this.currentShape = currentShape;
	}
	
}