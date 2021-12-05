package Model;

import Model.Enum.LocationType;
import lombok.*;
import util.CartesianCoordinates;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	//private String id;

	private double demand;

	private double readyTime;

	private double dueDate;

	private double serviceTime;

	private CartesianCoordinates cartesianCoordinates;

	private LocationType locationType;



}
