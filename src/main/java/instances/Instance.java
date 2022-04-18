package instances;

import instances.Properties.InstanceProperty;
import instances.Properties.VehicleProperty;
import model.Location;

import java.util.Set;

public interface Instance {

	Set<Location> getLocations();

	Set<InstanceProperty>  getInstanceProperties();

	Set<VehicleProperty> getVehicleProperties();


}
