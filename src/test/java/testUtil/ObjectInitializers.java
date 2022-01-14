package testUtil;

import Instances.Properties.*;
import Model.Enum.LocationType;
import Model.Location;
import Model.Vehicle;
import Model.VehicleFactory;
import algorithm.solution.SolutionContext;
import util.CartesianCoordinates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectInitializers {

	public static Vehicle createVehicleWithProperties(SolutionContext solutionContext,Set<VehicleProperty> vehiclePropertySet){
		Map<VehiclePropertyType,VehicleProperty> vehiclePropertyMap= vehiclePropertySet.stream().collect(Collectors.toMap(VehicleProperty::getVehiclePropertyType, Function.identity()));
		return  Vehicle.builder().vehiclePropertyMap(vehiclePropertyMap).solutionContext(solutionContext).build();
	}

	public static SolutionContext createSolutionContextExample1(){
		SolutionContext solutionContext= new SolutionContext();

		Map<String, Location> depots= new HashMap<>();
		depots.put("1",Location.builder().locationType(LocationType.DEPOT).id("1").cartesianCoordinates(CartesianCoordinates.builder().y(0.0).x(0.0).build()).build());
		solutionContext.setDepots(depots);

		Map<String, Location> customers= new HashMap<>();
		customers.put("2",Location.builder().locationType(LocationType.CUSTOMER_LOCATION).id("2").cartesianCoordinates(CartesianCoordinates.builder().y(5.0).x(0.0).build()).build());
		customers.put("3",Location.builder().locationType(LocationType.CUSTOMER_LOCATION).id("3").cartesianCoordinates(CartesianCoordinates.builder().y(3.0).x(3.0).build()).build());
		customers.put("4",Location.builder().locationType(LocationType.CUSTOMER_LOCATION).id("4").cartesianCoordinates(CartesianCoordinates.builder().y(1.0).x(1.0).build()).build());
		customers.put("5",Location.builder().locationType(LocationType.CUSTOMER_LOCATION).id("5").cartesianCoordinates(CartesianCoordinates.builder().y(2.0).x(3.0).build()).build());
		customers.put("9",Location.builder().locationType(LocationType.CUSTOMER_LOCATION).id("9").cartesianCoordinates(CartesianCoordinates.builder().y(-1.0).x(-1.0).build()).build());

		AtomicReference<Double> demand = new AtomicReference<>((double) customers.size());

		customers.forEach((s, location) -> {
			LocationProperty property = LocationProperty.builder().locationPropertyType(LocationPropertyType.DEMAND).build();
			property.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE,demand.get());
			demand.updateAndGet(v -> v - 1);
			location.getLocationProperties().put(LocationPropertyType.DEMAND,property);
		});

		solutionContext.setCustomers(customers);

		Map<String, Location> chargingStations= new HashMap<>();
		chargingStations.put("6",Location.builder().locationType(LocationType.RECHARGING_STATION).id("6").cartesianCoordinates(CartesianCoordinates.builder().y(7.0).x(0.0).build()).build());
		chargingStations.put("7",Location.builder().locationType(LocationType.RECHARGING_STATION).id("7").cartesianCoordinates(CartesianCoordinates.builder().y(3.3).x(5.0).build()).build());
		chargingStations.put("8",Location.builder().locationType(LocationType.RECHARGING_STATION).id("8").cartesianCoordinates(CartesianCoordinates.builder().y(1.5).x(1.5).build()).build());
		solutionContext.setChargingStations(chargingStations);

		Set<VehicleProperty> vehicleProperties= new HashSet<>();
		vehicleProperties.add(createVehicleProperty(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY,PropertyKey.SINGLE_DOUBLE_VALUE,3.0));
		vehicleProperties.add(createVehicleProperty(VehiclePropertyType.FUEL_CONSUMPTION_RATE,PropertyKey.SINGLE_DOUBLE_VALUE,1.0));
		vehicleProperties.add(createVehicleProperty(VehiclePropertyType.AVERAGE_VELOCITY,PropertyKey.SINGLE_DOUBLE_VALUE,1.0));
		vehicleProperties.add(createVehicleProperty(VehiclePropertyType.VEHICLE_LOAD_CAPACITY,PropertyKey.SINGLE_DOUBLE_VALUE,2.0));
		vehicleProperties.add(createVehicleProperty(VehiclePropertyType.DEPARTURE_LOCATION,PropertyKey.SINGLE_STRING_VALUE,"1"));
		vehicleProperties.add(createVehicleProperty(VehiclePropertyType.ARRIVAL_LOCATION,PropertyKey.SINGLE_STRING_VALUE,"1"));

		solutionContext.setVehicleFactory(new VehicleFactory(solutionContext,vehicleProperties));
		return solutionContext;
	}

	public static VehicleProperty createVehicleProperty(VehiclePropertyType vehiclePropertyType,PropertyKey propertyKey,Object value){
		Map<PropertyKey,Object> propertyKeyObjectMap= new HashMap<>();
		propertyKeyObjectMap.put(propertyKey,value);
		return VehicleProperty.builder().vehiclePropertyType(vehiclePropertyType).propertyMappings(propertyKeyObjectMap).build();
	}

	public static LocationProperty createLocationProperty(LocationPropertyType locationPropertyType, PropertyKey propertyKey, Object value){
		Map<PropertyKey,Object> propertyKeyObjectMap= new HashMap<>();
		propertyKeyObjectMap.put(propertyKey,value);
		return LocationProperty.builder().locationPropertyType(locationPropertyType).propertyMappings(propertyKeyObjectMap).build();
	}

}
