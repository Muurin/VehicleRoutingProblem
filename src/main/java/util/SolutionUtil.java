package util;

import Model.Location;
import algorithm.solution.SolutionContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SolutionUtil {

	public static List<Location> locationsSortedByDistance(Collection<Location> locations, Location currentLocation) {
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).sorted(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).collect(Collectors.toList());
	}

	public static Location findNearestLocationFrom(Collection<Location> locations, Location currentLocation) {
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).min(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).orElse(null);
	}

	public static boolean anyCustomersInNeedOfService(SolutionContext solutionContext) {
		return solutionContext.getCustomers().size() != 0;
	}

	public static Location getRandomLocation(Collection<Location> locations) {
		List<Location> locationList = new ArrayList<>(locations);
		return locationList.get((int) (Math.random() * locationList.size()));
	}

}
