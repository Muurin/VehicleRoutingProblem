package Model;


import Instances.Properties.VehicleProperty;
import Instances.Properties.VehiclePropertyType;
import algorithm.paths.Route;
import algorithm.solution.SolutionContext;
import lombok.*;
import util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

	private Long id;

	private double currentTime = 0;
	private double currentLoad;
	private double currentFuel;
	private double currentDistance = 0;

	private SolutionContext solutionContext;

	@Builder.Default
	private Map<VehiclePropertyType, VehicleProperty> vehiclePropertyMap = new HashMap<>();

	@Builder.Default
	private Stack<Route> vehiclePath = new Stack<>();

	public void addRoute(Route route) {
		vehiclePath.add(route);
		currentFuel -= vehiclePath.peek().getFuelSpent();
		currentDistance += vehiclePath.peek().getDistanceTravelled();
		currentTime += vehiclePath.peek().getTimeSpent();
		currentLoad -= vehiclePath.peek().getLoadTransferred();
	}

	public void travelTo(Location location) {
		Route route = new Route(getCurrentLocation(), location, this);
		addRoute(route);
	}

	public Route removeLastRoute() {
		currentFuel += vehiclePath.peek().getFuelSpent();
		currentDistance -= vehiclePath.peek().getDistanceTravelled();
		currentTime -= vehiclePath.peek().getTimeSpent();
		currentLoad += vehiclePath.peek().getLoadTransferred();
		return vehiclePath.pop();
	}

	public Route checkLastRoute() {
		return vehiclePath.peek();
	}

	public void resetPath() {

		vehiclePath.clear();
		currentTime = 0;
		currentDistance = 0;
	}

	//full recharge
	public double refuel() {
		currentFuel=PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY));

		return (PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY)) - currentFuel)
				* PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.INVERSE_REFUELING_RATE));
	}

	//TODO implement heuristic for charging -check for fueling/charging rate or function
	public double refuel(Object object) {
		return 0;
	}

	public Location getCurrentLocation() {
		return vehiclePath.peek().getDestinationLocation();
	}


}
