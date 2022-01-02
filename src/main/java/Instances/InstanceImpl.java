package Instances;

import Instances.Properties.InstanceProperty;
import Instances.Properties.VehicleProperty;
import Model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceImpl implements Instance {

	private Set<Location> locations;

	private Set<InstanceProperty> instanceProperties;

	private Set<VehicleProperty> vehicleProperties;


	@Override
	public Set<Location> getLocations() {
		return locations;
	}

	@Override
	public Set<InstanceProperty> getInstanceProperties() {
		return instanceProperties;
	}

	@Override
	public Set<VehicleProperty> getVehicleProperties() {
		return vehicleProperties;
	}
}
