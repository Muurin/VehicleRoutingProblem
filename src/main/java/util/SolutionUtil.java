package util;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.paths.Route;
import algorithm.solution.SolutionContextFactory;
import model.Location;
import algorithm.solution.SolutionContext;
import model.Vehicle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;

public class SolutionUtil {

	public static List<Location> locationsSortedByDistance(Collection<Location> locations, Location currentLocation) {
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).sorted(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).collect(Collectors.toList());
	}

	public static Location findNearestLocationFromNotVisited(Collection<Location> locations, Location currentLocation, Map<String,Location> visited) {
		return locations.stream().filter(location -> !visited.containsKey(location.getId())&& !location.getId().equals(currentLocation.getId())).min(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).orElse(null);
	}

	public static Location findNearestLocationFromSet(Collection<Location> locations, Location currentLocation){
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).min(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).orElse(null);
	}

	public static boolean anyCustomersInNeedOfService(SolutionContext solutionContext) {
		return solutionContext.getCustomers().size() == solutionContext.getServicedCustomers().size();
	}

	public static Location getRandomLocation(Collection<Location> locations) {
		List<Location> locationList = new ArrayList<>(locations);
		return locationList.get((int) (Math.random() * locationList.size()));
	}

}
