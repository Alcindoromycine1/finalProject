/**
 * @author Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * @since April 2, 2025
 * @version 2.0
 * Final Project ICS4U0
 * Whispers of the Deceived
 */
package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This class is responsible for the logic behind the exorcism drawing of
 * various different shapes
 */
public class Minigame {

	private ArrayList<Point> points = new ArrayList<>();// points that the user draws
	private boolean isExorcising = false;// if the user is in the exorcism room and is ready to exorcise
	private int sumX = 0;// sum of all x points template
	private int sumY = 0;// sum of all y points template
	private ArrayList<Point> proper = new ArrayList<>();// all template points
	private int count = 0;// used to know how many points are drawn

	private int circleX = 500;// x value of circle template
	private int circleY = 300;// y value of circle template

	private double originalArea = 0;// area of template shape
	private ArrayList<Point> shapePoints = new ArrayList<>();// all the points drawn (including interpolated ones)

	private int newSumX = 0;// sum of all x points drawn
	private int newSumY = 0;// sum of all y points drawn
	private ArrayList<Point> newPoints = new ArrayList<>();// key points
	private Point newCentroid;// centroid of shape drawn
	private Point centroid;// centroid of template

	private String currentShape = " ";

	/**
	 * begin drawing
	 */
	public void startExorcising() {
		isExorcising = true;
	}

	/**
	 * gets all the points and area of the template circle
	 */
	public void circle() {
		int radius = 50;
		originalArea = Math.PI * Math.pow(radius, 2);// area of template circle
		shapePoints.clear();

		int numPoints = 100;// number of points on the circle to store in shapePoints
		// Getting all the points on the template circle
		for (int i = 0; i < numPoints; i++) {
			double angle = 2 * Math.PI * i / numPoints;
			int x = (int) (circleX + radius * Math.cos(angle));
			int y = (int) (circleY + radius * Math.sin(angle));
			shapePoints.add(new Point(x, y));// storing the point in shapePoints
		}
	}

	/**
	 * gets the area and all the points of the zigzag template
	 * 
	 * @see “Linear Interpolation Formula: Formula with Solved Examples.”
	 *      GeeksforGeeks, GeeksforGeeks, 28 May 2024,
	 *      {@link https://www.geeksforgeeks.org/linear-interpolation-formula/}.
	 */
	public void zigzag() {
		shapePoints.clear();
		// all 4 key points of a zigzag
		Point vertex1 = new Point(0, 100);
		Point vertex2 = new Point(100, 100);
		Point vertex3 = new Point(0, 0);
		Point vertex4 = new Point(100, 0);
		// Adding points to arrayList
		newPoints.add(vertex1);
		newPoints.add(vertex2);
		newPoints.add(vertex3);
		newPoints.add(vertex4);

		int numberOfPoints = 50;// number of points in every line

		// All linear interpolation formulas are taken from the link in java doc
		// comments

		// top horizontal line (linear interpolation)
		for (int i = 0; i <= numberOfPoints; i++) {
			double t = (double) i / numberOfPoints;
			int x = (int) (vertex1.x + t * (vertex2.x - vertex1.x));
			int y = (int) (vertex1.y + t * (vertex2.y - vertex1.y));
			shapePoints.add(new Point(x, y));
		}

		// diagonal line (linear interpolation)
		for (int i = 0; i <= numberOfPoints; i++) {
			double t = (double) i / numberOfPoints;
			int x = (int) (vertex2.x + t * (vertex3.x - vertex2.x));
			int y = (int) (vertex2.y + t * (vertex3.y - vertex2.y));
			shapePoints.add(new Point(x, y));
		}

		// bottom horizontal line (linear interpolation)
		for (int i = 0; i <= numberOfPoints; i++) {
			double t = (double) i / numberOfPoints;
			int x = (int) (vertex3.x + t * (vertex4.x - vertex3.x));
			int y = (int) (vertex3.y + t * (vertex4.y - vertex3.y));
			shapePoints.add(new Point(x, y));
		}

		originalArea = calculateArea();// area of the zigzag shape
	}

	/**
	 * area and all the points on the triangle template
	 * 
	 * @see “Linear Interpolation Formula: Formula with Solved Examples.”
	 *      GeeksforGeeks, GeeksforGeeks, 28 May 2024,
	 *      {@link https://www.geeksforgeeks.org/linear-interpolation-formula/}.
	 */
	public void triangle() {
		shapePoints.clear();

		// 3 key points of the triangle
		Point vertex1 = new Point(455, 210);
		Point vertex2 = new Point(599, 305);
		Point vertex3 = new Point(444, 383);
		originalArea = Math.abs((vertex1.x * (vertex2.y - vertex3.y) + vertex2.x * (vertex3.y - vertex1.y)
				+ vertex3.x * (vertex1.y - vertex2.y)) / 2.0);// Area of the equilateral triangle

		// Interpolates the points that are between the vertices of the triangle
		int numberOfPoints = 33;// how many points each side of the triangle will have
		// Linear interpolation to find the points between the vertices

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

	/**
	 * calculates the sum of all the points of the template
	 */
	public void calculation() {
		sumX = 0;// resets x
		sumY = 0;// resets y
		count = 0;// resets count
		proper.clear();

		for (Point point : points) {
			proper.add(point);
			sumX += point.x;
			sumY += point.y;
			count++;
		}
	}

	/**
	 * Calculates the area of any polygon
	 * 
	 * @return area of polygon
	 * @see “Shoelace Formula.” Wikipedia, Wikimedia Foundation, 13 May 2025,
	 *      {@link https://en.wikipedia.org/wiki/Shoelace_formula}.
	 */
	public double calculateArea() {
		double area = 0;

		// Shoe lace formula for universal polygon area formula taken from the above
		for (int i = 0; i < newPoints.size(); i++) {
			Point current = newPoints.get(i);
			Point next = newPoints.get((i + 1) % newPoints.size());

			area += (current.x * next.y) - (next.x * current.y);
		}
		return Math.abs(area * 0.5);// area of polygon
	}

	/**
	 * centroid of the drawn shape
	 */
	public void newCentroid() {
		newSumX = 0;// reset sum of x
		newSumY = 0;// reset sum of y
		newPoints.clear();

		if (count == 0) {// prevents divide by 0
			return;
		}

		centroid = new Point(sumX / count, sumY / count);// centroid point
		Point difference = new Point(circleX - centroid.x, circleY - centroid.y);// finds the difference of the drawn
																					// shape centroid relative to the
																					// template centroid

		// store difference points in a new arrayList
		for (Point point : points) {
			Point shiftedPoint = new Point(point.x + difference.x, point.y + difference.y);
			newPoints.add(shiftedPoint);
			newSumX += shiftedPoint.x;// sum of new x points
			newSumY += shiftedPoint.y;// sum of new y points

		}

		newCentroid = new Point(newSumX / count, newSumY / count);// sum of new centroid (difference one)
	}

	/**
	 * ensures that the size of the drawn shape doesn't affect its accuracy relative
	 * to the shape
	 * 
	 * @return scale factor
	 */
	public double scaleFactor() {

		double scale = 0;
		double area = calculateArea();// stores area in variable
		scale = Math.sqrt(area) / Math.sqrt(originalArea);// scale factor

		return scale;

	}

	/**
	 * checks whether the drawn shape is valid (accurate drawing to shape template)
	 * 
	 * @param threshold
	 * @return true or false
	 */
	public boolean isValid(int threshold) {
		double distanceSum = 0;

		ArrayList<Point> interpPoints = interpolateNewPoints(2);// interpolates 2 points between for every 2 points

		// calculates the average of all of the points that exceed the threshold

		// sum of exceeded threshold points (not include non exceeding part)
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

		// average of exceeded points
		double averageDistance = distanceSum / shapePoints.size();
		// average of exceeded points checked to be within threshold
		return averageDistance <= threshold;
	}

	/**
	 * interpolates some of the points that will be used in the checking of validity
	 * of the shape (adds more points therefore increasing accuracy)
	 * 
	 * @param pointsBetween
	 * @return interpolated points
	 * @see “Linear Interpolation Formula: Formula with Solved Examples.”
	 *      GeeksforGeeks, GeeksforGeeks, 28 May 2024,
	 *      {@link https://www.geeksforgeeks.org/linear-interpolation-formula/}.
	 */
	public ArrayList<Point> interpolateNewPoints(int pointsBetween) {
		ArrayList<Point> interpolatedNewPoints = new ArrayList<>();

		// If you can't interpolate
		if (newPoints.size() < 2)
			return interpolatedNewPoints;

		// Linear interpolation
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

		return interpolatedNewPoints;// returns all the new points including interpolated points
	}

	/**
	 * to check whether the shape drawn is a vertical, horizontal or neither shape
	 */
	public void calculatedResult() {
		int size = points.size();
		if (size < 4) {// can't detect because not enough points to check
			return;
		}

		// Checks the first, last and two middle points to see whether they are straight
		// in terms of vertical or horizontal
		Point[] p = new Point[4];
		p[0] = points.get(0);
		p[1] = points.get(size - 1);
		p[2] = points.get(size / 2);
		p[3] = points.get(size / 3);

		int threshold = 30;// how many pixels off of a straight line you can be
		boolean allHorizontal = true;// all points are valid for horizontal line (within threshold)
		boolean allVertical = true;// all points are valid for vertical line (within threshold)

		// Checks for if all the 4 points are within the threshold of a vertical or
		// horizontal line
		for (int i = 1; i < p.length; i++) {
			if (Math.abs(p[0].y - p[i].y) >= threshold) {
				allHorizontal = false;
			}
			if (Math.abs(p[0].x - p[i].x) >= threshold) {
				allVertical = false;
			}
		}

		// If all the points are valid for a horizontal line, then the shape drawn is a
		// horizontal line (string used in other class)
		if (allHorizontal) {
			currentShape = "horizontal";
		} else if (allVertical) {// If all the points are valid for a vertical line, then the shape drawn is a
									// vertical line (string used in other class)
			currentShape = "vertical";
		} else {
			currentShape = "none";// not all points are valid for either one so no shape is detected
		}

		points.clear();// clear all the points for future use
	}

	/**
	 * interpolates the rest of the points that aren't the 4 key points
	 * 
	 * @param g2
	 * @see “Linear Interpolation Formula: Formula with Solved Examples.”
	 *      GeeksforGeeks, GeeksforGeeks, 28 May 2024,
	 *      {@link https://www.geeksforgeeks.org/linear-interpolation-formula/}.
	 */
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
				double t = (double) j / spacing;
				int x = (int) (p1.x + t * (p2.x - p1.x));
				int y = (int) (p1.y + t * (p2.y - p1.y));
				g2.fillOval(x - 5, y - 5, 10, 10); // Draws the ovals
			}
		}
	}

	// Getters and Setters

	/**
	 * 
	 * @return points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * @return isExorcising
	 */
	public boolean getIsExorcising() {
		return isExorcising;
	}

	/**
	 * 
	 * @param points
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	/**
	 * 
	 * @return currentShape
	 */
	public String getCurrentShape() {
		return currentShape;
	}

	/**
	 * @return currentShape
	 */
	public void setCurrentShape(String currentShape) {
		this.currentShape = currentShape;
	}
}