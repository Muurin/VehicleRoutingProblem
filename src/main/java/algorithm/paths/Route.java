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

	private double distanceTravelled;

	public Route(Location startingLocation, Location destinationLocation) {
		this.startingLocation = startingLocation;
		this.destinationLocation = destinationLocation;

		distanceTravelled = MathUtil.getEuclideanDistanceTo(startingLocation, destinationLocation);

	}

}
