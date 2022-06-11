package model.paths;

import model.Location;
import lombok.*;
import util.MathUtil;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {

	private Location startingLocation;

	private Location destinationLocation;

	private double distanceTravelled;

	private double timeSpent;

	public Route(Location startingLocation, Location destinationLocation) {
		this.startingLocation = startingLocation;
		this.destinationLocation = destinationLocation;

		distanceTravelled = MathUtil.getEuclideanDistanceTo(startingLocation, destinationLocation);

	}

}
