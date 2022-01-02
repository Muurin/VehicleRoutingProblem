package algorithm.paths;

import Instances.Properties.LocationPropertyType;
import Instances.Properties.VehiclePropertyType;
import Model.Enum.LocationType;
import Model.Location;
import Model.Vehicle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import util.MathUtil;
import util.PropertiesUtil;

@Getter
@Setter
@Builder
public class Route {

	private Location startingLocation;

	private Location destinationLocation;

	private double timeSpent;

	private double distanceTravelled;

	private double timeArrivedAtDestination;

	public Route(Location startingLocation, Location destinationLocation, Vehicle vehicle){
		this.startingLocation=startingLocation;
		this.destinationLocation=destinationLocation;

		timeSpent = PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.AVERAGE_VELOCITY))
				/ MathUtil.getEuclideanDistanceTo(startingLocation,destinationLocation);

		distanceTravelled=MathUtil.getEuclideanDistanceTo(startingLocation,destinationLocation);

		timeArrivedAtDestination= vehicle.getCurrentTime()+timeSpent;

		//currently unloading time is constant and does not depend of load size, this assumes that customers and not visited after being fully serviced
		if(destinationLocation.getLocationType() == LocationType.CUSTOMER_LOCATION){
			if(destinationLocation.getLocationProperties().containsKey(LocationPropertyType.SERVICE_TIME)){
				timeSpent=PropertiesUtil.getDoublePropertyValue(destinationLocation.getLocationProperties().get(LocationPropertyType.SERVICE_TIME));
			}
		}
		else if(destinationLocation.getLocationType() == LocationType.RECHARGING_STATION){
			timeSpent+=vehicle.refuel();
		}


	}

}
