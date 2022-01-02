package Model;

import Instances.Properties.LocationProperty;
import Instances.Properties.LocationPropertyType;
import Model.Enum.LocationType;
import lombok.*;
import util.CartesianCoordinates;
import util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	private String id;

	private CartesianCoordinates cartesianCoordinates;

	private LocationType locationType;

	private Map<LocationPropertyType, LocationProperty> locationProperties = new HashMap<>();

	public void reset() {

		if (locationType == LocationType.CUSTOMER_LOCATION) {
			PropertiesUtil.setDoublePropertyValue(locationProperties.get(LocationPropertyType.DEMAND_FULFILLED), 0.0);
		}
	}

}
