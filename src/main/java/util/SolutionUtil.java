package util;

import Instances.Properties.LocationProperty;
import Instances.Properties.LocationPropertyType;
import Model.Enum.LocationType;
import Model.Location;
import Model.Vehicle;
import algorithm.solution.SolutionContext;
import algorithm.paths.Route;

import java.util.Collection;
import java.util.Comparator;

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

		vehicle.addRoute(Route
				.builder()
				.startingLocation(vehicle.checkLastRoute().getDestinationLocation())
				.destinationLocation(customer)
				.build());

		if(PropertiesUtil.getDoublePropertyValue(demandFulfilledProperty) == demand){
			solutionContext.getServicedCustomers().add(customer.getId());
		}


	}

	public static Location findNearestLocationFrom(Collection<Location> locations,Location currentLocation){
		return locations.stream().filter(location -> !location.getId().equals(currentLocation.getId())).min(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, currentLocation))).get();
	}

	public boolean customersInNeedOfService(SolutionContext solutionContext){
		return solutionContext.getCustomers().size()!=solutionContext.getServicedCustomers().size();
	}


}
