package solution;

import instances.Properties.InstanceProperty;
import model.Location;
import model.Vehicle;
import model.VehicleFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SolutionContext {

    private Map<String, Location> depots = new HashMap<>();

    private Map<String, Location> chargingStations = new HashMap<>();

    private Map<String, Location> customers = new HashMap<>();

    private Map<String, Location> servicedCustomers = new HashMap<>();

    private Map<Long, Vehicle> vehicles = new HashMap<>();

    private Set<InstanceProperty> instanceProperties;

    private VehicleFactory vehicleFactory;

    public Vehicle addVehicle() {
        Vehicle vehicle = vehicleFactory.createVehicle();
        vehicles.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    public Vehicle addVehicleSpecificId(Long id) {
        Vehicle vehicle = vehicleFactory.createVehicleSpecificId(id);
        vehicles.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    public void reset() {
        servicedCustomers.clear();
        vehicles.clear();
    }


}
