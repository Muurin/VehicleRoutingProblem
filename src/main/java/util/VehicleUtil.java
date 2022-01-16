package util;

import Instances.Properties.LocationPropertyType;
import Instances.Properties.VehiclePropertyType;
import Model.Enum.LocationType;
import Model.Location;
import Model.Vehicle;
import algorithm.paths.Route;
import algorithm.solution.SolutionContext;

import java.util.Comparator;
import java.util.List;

public class VehicleUtil {

	public static void initializeDepot(Vehicle vehicle) {
		Location depot = vehicle.getSolutionContext().getDepots().get(PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_LOCATION)));
		vehicle.addRoute(Route
				.builder()
				.startingLocation(depot)
				.destinationLocation(depot)
				.build());
	}

	public static Location getArrivalLocation(Vehicle vehicle) {
		return vehicle.getSolutionContext().getDepots().get(PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));
	}

	public static double requiredFuel(Vehicle vehicle, Location loc1, Location loc2) {
		return PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.FUEL_CONSUMPTION_RATE)) * MathUtil.getEuclideanDistanceTo(loc1, loc2);
	}

	public static boolean canServiceCustomer(Vehicle vehicle, Location customer) {
		if (customer.getLocationType() != LocationType.CUSTOMER_LOCATION) {
			throw new RuntimeException("greska");
		}
		return vehicle.getCurrentLoad() >= PropertiesUtil.getDoublePropertyValue(customer.getLocationProperties().get(LocationPropertyType.DEMAND));
	}

	public static boolean canReachLocationAndNearestChargingStation(Vehicle vehicle, Location targetLocation) {
		return vehicle.getCurrentFuel() >= requiredFuel(vehicle, vehicle.getCurrentLocation(), targetLocation)
				+ requiredFuel(vehicle, targetLocation, SolutionUtil.findNearestLocationFrom(vehicle.getSolutionContext().getChargingStations().values(), targetLocation));
	}

	public static boolean canReachLocation(Vehicle vehicle, Location targetLocation){
		return vehicle.getCurrentFuel() >= requiredFuel(vehicle, vehicle.getCurrentLocation(), targetLocation);
	}

	public static Location chooseIntermediateChargingStation(Vehicle vehicle, Location targetLocation) {
		return vehicle.getSolutionContext().getChargingStations().values().stream().filter(location -> canReachLocation(vehicle,location)).min(Comparator.comparingDouble(o -> intermediateDistance(vehicle.getCurrentLocation(), o, targetLocation))).orElse(null);
	}

	private static double intermediateDistance(Location startingLocation, Location intermediateLocation, Location destination) {
		return MathUtil.getEuclideanDistanceTo(startingLocation, intermediateLocation) + MathUtil.getEuclideanDistanceTo(intermediateLocation, destination);
	}

	public static boolean canServiceAtLeastOneCustomer(Vehicle vehicle) {
		return vehicle.getSolutionContext().getCustomers().values().stream().anyMatch(location -> canServiceCustomer(vehicle, location));
	}

	/**
	 * Returns null if there is no valid location found
	 * @param vehicle
	 * @param solutionContext
	 * @return
	 */
	public static Location getClosestReachableAndServiceableCustomer(Vehicle vehicle, SolutionContext solutionContext){

		List<Location> locationSortedByDistance = SolutionUtil.locationsSortedByDistance(solutionContext.getCustomers().values(),vehicle.getCurrentLocation());
		return locationSortedByDistance.stream().filter(location -> canServiceCustomer(vehicle, location)).findFirst().orElse(null);


	}

}
