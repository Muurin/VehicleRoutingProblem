package algorithm.paths;

import instances.Properties.LocationProperty;
import instances.Properties.LocationPropertyType;
import instances.Properties.VehiclePropertyType;
import model.Enum.LocationType;
import model.Location;
import model.Vehicle;
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

		//currently unloading time is constant and does not depend on load size, this assumes that customers and not visited after being fully serviced
		if (destinationLocation.getLocationType() == LocationType.CUSTOMER_LOCATION) {

			LocationProperty demandProperty = destinationLocation.getLocationProperties().get(LocationPropertyType.DEMAND);

			double demand = PropertiesUtil.getDoublePropertyValue(demandProperty);

			if(vehicle.getCurrentLoad() >= demand){

				if (destinationLocation.getLocationProperties().containsKey(LocationPropertyType.SERVICE_TIME)) {
					timeSpent += PropertiesUtil.getDoublePropertyValue(destinationLocation.getLocationProperties().get(LocationPropertyType.SERVICE_TIME));
				}

				vehicle.setCurrentLoad(vehicle.getCurrentLoad() - demand);

				this.loadTransferred = demand;

				vehicle.getSolutionContext().getServicedCustomers().put(destinationLocation.getId(),destinationLocation);

			}

		} else if (destinationLocation.getLocationType() == LocationType.RECHARGING_STATION) {
			timeSpent += vehicle.refuel();
		}


	}

}
