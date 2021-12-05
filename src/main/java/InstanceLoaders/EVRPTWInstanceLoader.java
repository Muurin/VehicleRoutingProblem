package InstanceLoaders;

import Instances.EVRPTWInstance;
import Instances.Instance;
import Instances.Properties.InstanceProperty;
import Instances.Properties.InstancePropertyType;
import Instances.Properties.VehicleProperty;
import Instances.Properties.VehiclePropertyType;
import Model.Enum.LocationType;
import Model.Location;
import util.CartesianCoordinates;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class EVRPTWInstanceLoader implements InstanceLoader {

	@Override
	public Instance load(String path) throws IOException {

		boolean doneWithLocationProcessing = false;

		Set<Location> locations = new HashSet<>();
		Set<InstanceProperty> instanceProperties = new HashSet<>();
		Set<VehicleProperty> vehicleProperties = new HashSet<>();

		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.ELECTRIC_VEHICLE).build());
		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.HOMOGENOUS_FLEET).build());
		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.LINEAR_CHARGING).build());
		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.TIME_WINDOWS).build());

		try (BufferedReader br = new BufferedReader((new FileReader(path)))) {
			String line;
			br.readLine(); //first line - column names
			while ((line = br.readLine()) != null) {

				if (line.trim().isEmpty()) {
					doneWithLocationProcessing = true;
				}
				String[] columnValues = line.split("\s+");

				if (!doneWithLocationProcessing) {

					String locationTypeString = columnValues[1];
					String xCoordinate = columnValues[2];
					String yCoordinate = columnValues[3];
					String demand = columnValues[4];
					String readyTime = columnValues[5];
					String dueDate = columnValues[6];
					String serviceTime = columnValues[7];

					LocationType locationType = switch (locationTypeString.trim()) {
						case "d" -> LocationType.DEPOT;
						case "f" -> LocationType.RECHARGING_STATION;
						case "c" -> LocationType.CUSTOMER_LOCATION;
						default -> throw new RuntimeException();
					};

					locations.add(Location
							.builder()
							.locationType(locationType)
							.demand(Double.parseDouble(demand))
							.readyTime(Double.parseDouble(readyTime))
							.dueDate(Double.parseDouble(dueDate))
							.serviceTime(Double.parseDouble(serviceTime))
							.cartesianCoordinates(CartesianCoordinates
									.builder()
									.x(Double.parseDouble(xCoordinate))
									.y(Double.parseDouble(yCoordinate))
									.build())
							.build());

				} else {
					//properties
					VehicleProperty vehicleProperty = new VehicleProperty();
					switch (columnValues[0]) {
						case "Q" -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY);
							vehicleProperty.getPropertyMappings().put(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY.toString(), columnValues[1]);
						}
						case "C" -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.VEHICLE_LOAD_CAPACITY);
							vehicleProperty.getPropertyMappings().put(VehiclePropertyType.VEHICLE_LOAD_CAPACITY.toString(), columnValues[1]);
						}
						case "r" -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.FUEL_CONSUMPTION_RATE);
							vehicleProperty.getPropertyMappings().put(VehiclePropertyType.FUEL_CONSUMPTION_RATE.toString(), columnValues[1]);
						}
						case "g" -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.INVERSE_REFUELING_RATE);
							vehicleProperty.getPropertyMappings().put(VehiclePropertyType.INVERSE_REFUELING_RATE.toString(), columnValues[1]);
						}
						case "v" -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.AVERAGE_VELOCITY);
							vehicleProperty.getPropertyMappings().put(VehiclePropertyType.AVERAGE_VELOCITY.toString(), columnValues[1]);
						}
						default -> throw new RuntimeException();
					}
				}


			}
		}


		return EVRPTWInstance
				.builder()
				.instanceProperties(instanceProperties)
				.vehicleProperties(vehicleProperties)
				.locations(locations)
				.build();
	}
}
