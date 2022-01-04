package util;

import Model.Location;

public class MathUtil {

	public static double getEuclideanDistanceTo(Location loc1, Location loc2) {
		double x1 = loc1.getCartesianCoordinates().getX();
		double y1 = loc1.getCartesianCoordinates().getY();
		double x2 = loc2.getCartesianCoordinates().getX();
		double y2 = loc2.getCartesianCoordinates().getY();

		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public static boolean areEqual(double value1, double value2, Double precision) {
		if (precision == null) {
			precision = 10E-5;
		}
		return Math.abs(value1 - value2) < precision;
	}
}
