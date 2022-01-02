package algorithm.solution;

import Instances.Instance;
import Instances.Properties.InstanceProperty;
import Model.Location;
import Model.Vehicle;
import Model.VehicleFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class SolutionContext {

	private Map<String, Location> depots = new HashMap<>();

	private Map<String, Location> chargingStations = new HashMap<>();

	private Map<String, Location> customers = new HashMap<>();

	private Map<String, Vehicle> vehicles = new HashMap<>();

	private Set<InstanceProperty> instanceProperties;

	private VehicleFactory vehicleFactory;

	private Set<String> servicedCustomers = new HashSet<>();

	public SolutionContext(Instance instance) {
		for (Location location : instance.getLocations()) {
			switch (location.getLocationType()) {
				case CUSTOMER_LOCATION:
					customers.put(location.getId(), location);
					break;
				case DEPOT:
					depots.put(location.getId(), location);
					break;
				case RECHARGING_STATION:
					chargingStations.put(location.getId(), location);
					break;
			}
		}

		instanceProperties = instance.getInstanceProperties();
		vehicleFactory = new VehicleFactory(instance.getVehicleProperties());

	}

	private void addVehicle() {
		Long id = 1L;
		Vehicle vehicle = new Vehicle();
		vehicles.put(vehicle.getId(), vehicle);
	}

	private void reset() {
		servicedCustomers.clear();
		customers.forEach((key, value) -> value.reset());
		vehicles.clear();
	}


}
