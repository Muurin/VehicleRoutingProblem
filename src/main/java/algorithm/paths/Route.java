package algorithm.paths;

import Instances.Properties.LocationProperty;
import Instances.Properties.LocationPropertyType;
import Instances.Properties.VehiclePropertyType;
import Model.Enum.LocationType;
import Model.Location;
import Model.Vehicle;
import lombok.*;
import util.MathUtil;
import util.PropertiesUtil;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {

	private Location startingLocation;

	private Location destinationLocation;

	private double timeSpent;

	private double distanceTravelled;

	private double timeArrivedAtDestination;

	private double fuelSpent;

	private double loadTransferred = 0;

	public Route(Location startingLocation, Location destinationLocation, Vehicle vehicle) {
		this.startingLocation = startingLocation;
		this.destinationLocation = destinationLocation;

		timeSpent = PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.AVERAGE_VELOCITY))
				/ MathUtil.getEuclideanDistanceTo(startingLocation, destinationLocation);

		distanceTravelled = MathUtil.getEuclideanDistanceTo(startingLocation, destinationLocation);

		fuelSpent = PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.FUEL_CONSUMPTION_RATE)) * distanceTravelled;

		timeArrivedAtDestination = vehicle.getCurrentTime() + timeSpent;

		//currently unloading time is constant and does not depend of load size, this assumes that customers and not visited after being fully serviced
		if (destinationLocation.getLocationType() == LocationType.CUSTOMER_LOCATION) {

			if (destinationLocation.getLocationProperties().containsKey(LocationPropertyType.SERVICE_TIME)) {
				timeSpent = PropertiesUtil.getDoublePropertyValue(destinationLocation.getLocationProperties().get(LocationPropertyType.SERVICE_TIME));
			}

			LocationProperty demandProperty = destinationLocation.getLocationProperties().get(LocationPropertyType.DEMAND);
			LocationProperty demandFulfilledProperty = destinationLocation.getLocationProperties().get(LocationPropertyType.DEMAND_FULFILLED);

			double demandFulfilled = PropertiesUtil.getDoublePropertyValue(demandFulfilledProperty);
			double demand = PropertiesUtil.getDoublePropertyValue(demandProperty);
			double transferred = demand - demandFulfilled;

			PropertiesUtil.setDoublePropertyValue(demandFulfilledProperty, demand + transferred);
			vehicle.setCurrentLoad(vehicle.getCurrentLoad() - transferred);

			this.loadTransferred = transferred;

			vehicle.getSolutionContext().getServicedCustomers().add(destinationLocation.getId());

		} else if (destinationLocation.getLocationType() == LocationType.RECHARGING_STATION) {
			timeSpent += vehicle.refuel();
		}


	}

}
