package Instances;

import Instances.Properties.InstanceProperty;
import Instances.Properties.VehicleProperty;
import Model.Location;

import java.util.Set;

public interface Instance {

	Set<Location> getLocations();

	Set<InstanceProperty>  getInstanceProperties();

	Set<VehicleProperty> getVehicleProperties();


}
