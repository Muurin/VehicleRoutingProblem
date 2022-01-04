package util;

import Instances.Properties.LocationProperty;
import Instances.Properties.LocationPropertyType;
import Instances.Properties.VehiclePropertyType;
import Model.Enum.LocationType;
import Model.Location;
import Model.Vehicle;
import algorithm.solution.SolutionContext;
import algorithm.paths.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SolutionUtil {


	public static void serviceCustomer(SolutionContext solutionContext, Location customer, Vehicle vehicle){
		if(!customer.getLocationType().equals(LocationType.CUSTOMER_LOCATION)){
			throw new RuntimeException("Not a customer");
		}

		LocationProperty demandProperty=customer.getLocationProperties().get(LocationPropertyType.DEMAND);
		LocationProperty demandFulfilledProperty=customer.getLocationProperties().get(LocationPropertyType.DEMAND_FULFILLED);

		double demandFulfilled=PropertiesUtil.getDoublePropertyValue(demandFulfilledProperty);
		double demand=PropertiesUtil.getDoublePropertyValue(demandProperty);
		double transferred= Math.min(demand- demandFulfilled, vehicle.getCurrentLoad());

		PropertiesUtil.setDoublePropertyValue(demandFulfilledProperty,demand+transferred);
		vehicle.setCurrentLoad(vehicle.getCurrentLoad()-transferred);

		Route route=new Route(vehicle.checkLastRoute().getStartingLocation(),customer,vehicle);
		route.setLoadTransferred(transferred);

		vehicle.addRoute(route);

		if(MathUtil.areEqual(PropertiesUtil.getDoublePropertyValue(demandFulfilledProperty),demand,null)){
			solutionContext.getServicedCustomers().add(customer.getId());
		}

	}

	public static List<Location> locationsSortedByDistance(Collection<Location> locations,Location currentLocation){
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).sorted(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).collect(Collectors.toList());
	}

	public static Location findNearestLocationFrom(Collection<Location> locations,Location currentLocation){
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).min(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).get();
	}

	public static boolean customersInNeedOfService(SolutionContext solutionContext){
		return solutionContext.getCustomers().size()!=solutionContext.getServicedCustomers().size();
	}

	public static Location getRandomLocation(Collection<Location> locations){
		List<Location> locationList=new ArrayList<>(locations);
		return locationList.get((int)(Math.random()* locationList.size()));
	}

	public static boolean hasEnoughFuelToVisit(Vehicle vehicle, Location location){
		return vehicle.getCurrentFuel()
				> PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.FUEL_CONSUMPTION_RATE))
					* MathUtil.getEuclideanDistanceTo(vehicle.checkLastRoute().getDestinationLocation(),location);
	}


}
