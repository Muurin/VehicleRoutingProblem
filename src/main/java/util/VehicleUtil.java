package util;

import Instances.Properties.VehiclePropertyType;
import Model.Location;
import Model.Vehicle;
import algorithm.paths.Route;
import algorithm.solution.SolutionContext;

public class VehicleUtil {

	public static void initializeDepot(SolutionContext solutionContext, Vehicle vehicle){
		Location depot=solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_NODE)));
		vehicle.addRoute(Route
				.builder()
				.startingLocation(depot)
				.destinationLocation(depot)
				.build());
	}

	public Location getArrivalLocation(SolutionContext solutionContext, Vehicle vehicle){
		return solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));
	}

}
