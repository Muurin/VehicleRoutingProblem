package Model;

import Instances.Properties.VehicleProperty;
import Instances.Properties.VehiclePropertyType;

import java.util.Map;
import java.util.Set;

/**
 * Vehicle properties are supposed to be constants that are not modified during the execution, therefore shallow copy is
 * enough
 */
public class VehicleFactory {

	private Long latestId = 1L;

	Map<VehiclePropertyType, VehicleProperty> vehicleProperties;

	public VehicleFactory(Set<VehicleProperty> vehicleProperties) {

		for (VehicleProperty vehicleProperty : vehicleProperties) {
			this.vehicleProperties.put(vehicleProperty.getVehiclePropertyType(), vehicleProperty);
		}

	}

	public Vehicle createVehicle() {
		Vehicle vehicle = Vehicle
				.builder()
				.vehiclePropertyMap(vehicleProperties)
				.id(latestId.toString())
				.build();

		latestId += 1;
		return vehicle;
	}

}
