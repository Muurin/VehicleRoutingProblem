package model;


import instances.Properties.LocationProperty;
import instances.Properties.LocationPropertyType;
import instances.Properties.VehicleProperty;
import instances.Properties.VehiclePropertyType;
import model.paths.Route;
import solution.SolutionContext;
import lombok.*;
import model.Enum.LocationType;
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

	private void addRoute(Route route) {
		vehiclePath.add(route);

		double timeSpent = route.getDistanceTravelled()/PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.AVERAGE_VELOCITY));

		double fuelSpent = PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.FUEL_CONSUMPTION_RATE)) * route.getDistanceTravelled();
		currentFuel -= fuelSpent;
		//currently unloading time is constant and does not depend on load size, this assumes that customers and not visited after being fully serviced
		if (route.getDestinationLocation().getLocationType() == LocationType.CUSTOMER_LOCATION) {

			LocationProperty demandProperty = route.getDestinationLocation().getLocationProperties().get(LocationPropertyType.DEMAND);

			double demand = PropertiesUtil.getDoublePropertyValue(demandProperty);

			if(currentLoad >= demand){

				if (route.getDestinationLocation().getLocationProperties().containsKey(LocationPropertyType.SERVICE_TIME)) {
					timeSpent += PropertiesUtil.getDoublePropertyValue(route.getDestinationLocation().getLocationProperties().get(LocationPropertyType.SERVICE_TIME));
				}

				currentLoad = currentLoad - demand;

				solutionContext.getServicedCustomers().put(route.getDestinationLocation().getId(),route.getDestinationLocation());

			}

		} else if (route.getDestinationLocation().getLocationType() == LocationType.RECHARGING_STATION) {
			timeSpent += refuel();
		}
		route.setTimeSpent(timeSpent);

		currentDistance += vehiclePath.peek().getDistanceTravelled();
		currentTime += timeSpent;

	}

	public void travelTo(Location destination) {
		Route route = new Route(getCurrentLocation(), destination);
		addRoute(route);
	}

	//full recharge
	public double refuel() {
		currentFuel=PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY));

		return (PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY)) - currentFuel)
				* PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.INVERSE_REFUELING_RATE));
	}

	public Location getCurrentLocation() {
		if(vehiclePath.isEmpty()){
			return getDepot();
		}
		return vehiclePath.peek().getDestinationLocation();
	}

	private Location getDepot() {
		return solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(vehiclePropertyMap.get(VehiclePropertyType.DEPARTURE_LOCATION)));

	}


}
