package model;

import instances.Properties.VehicleProperty;
import instances.Properties.VehiclePropertyType;
import solution.SolutionContext;
import util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Vehicle properties are supposed to be constants that are not modified during the execution, therefore shallow copy is
 * enough
 */
public class VehicleFactory {

    private Long latestId = 1L;

    private final SolutionContext solutionContext;

    private final Map<VehiclePropertyType, VehicleProperty> vehicleProperties = new HashMap<>();

    public VehicleFactory(SolutionContext solutionContext, Set<VehicleProperty> vehicleProperties) {

        for (VehicleProperty vehicleProperty : vehicleProperties) {
            this.vehicleProperties.put(vehicleProperty.getVehiclePropertyType(), vehicleProperty);
        }
        this.solutionContext = solutionContext;

    }

    public Vehicle createVehicle() {
        Vehicle vehicle = Vehicle
                .builder()
                .solutionContext(solutionContext)
                .vehiclePropertyMap(vehicleProperties)
                .id(latestId)
                .currentLoad(PropertiesUtil.getDoublePropertyValue(vehicleProperties.get(VehiclePropertyType.VEHICLE_LOAD_CAPACITY)))
                .currentFuel(PropertiesUtil.getDoublePropertyValue(vehicleProperties.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY)))
                .build();
        latestId += 1;
        return vehicle;
    }

    public Vehicle createVehicleSpecificId(Long id) {
        if (!solutionContext.getVehicles().containsKey(id)) {
            Vehicle vehicle = Vehicle
                    .builder()
                    .id(id)
                    .solutionContext(solutionContext)
                    .vehiclePropertyMap(vehicleProperties)
                    .currentLoad(PropertiesUtil.getDoublePropertyValue(vehicleProperties.get(VehiclePropertyType.VEHICLE_LOAD_CAPACITY)))
                    .currentFuel(PropertiesUtil.getDoublePropertyValue(vehicleProperties.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY)))
                    .build();
            latestId += 1;
            return vehicle;
        }
        throw new RuntimeException("Vehicle id already exists");

    }

}
