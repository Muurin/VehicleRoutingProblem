package Instances;

import Instances.Properties.InstanceProperty;
import Instances.Properties.VehicleProperty;
import Model.Location;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EVRPTWInstance implements Instance {

	private Set<Location> locations;

	private Set<InstanceProperty> instanceProperties;

	private Set<VehicleProperty> vehicleProperties;


}
