package InstanceLoaders;

import Instances.Instance;
import Instances.InstanceImpl;
import Instances.Properties.*;
import Model.Enum.LocationType;
import Model.Location;
import util.CartesianCoordinates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Textual file format:
 * <p>
 * The instances are formatted as follows:
 * <p>
 * ###For each location the instance provides:
 * -StringId as a unique identifier
 * -Type indicates the function of the location, i.e,
 * ---d: depot
 * ---f: recharging station
 * ---c: customer location
 * -x, y are coordinates (distances are assumed to be euclidean)
 * -demand specifies the quantity of freight capacity required
 * -ReadyTime and DueDate are the beginning and the end of the time window (waiting is allowed)
 * -ServiceTime denotes the entire time spend at customer for loading operations
 * <p>
 * ###For the electric vehicles (all identical):
 * -"Q Vehicle fuel tank capacity": units of energy available
 * -"C Vehicle load capacity":      units available for cargo
 * -"r fuel consumption rate":      reduction of battery capacity when traveling one unit of distance
 * -"g inverse refueling rate":     units of time required to recharge one unit of energy
 * -"v average Velocity":           assumed to be constant on all arcs, required to calculate the travel time from distance
 */
public class EVRP_CTWInstanceLoader implements InstanceLoader {

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
		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.CAPACITATED).build());

		try (BufferedReader br = new BufferedReader((new FileReader(path)))) {
			String line;
			br.readLine(); //first line - column names
			while ((line = br.readLine()) != null) {

				if (line.trim().isEmpty()) {
					doneWithLocationProcessing = true;
				}


				if (!doneWithLocationProcessing) {
					String[] columnValues = line.split("\s+");
					String locationId = columnValues[0];
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

					if(locationType==LocationType.DEPOT){
						VehicleProperty vehicleProperty = new VehicleProperty();

						vehicleProperty.setVehiclePropertyType(VehiclePropertyType.ARRIVAL_LOCATION);
						vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_STRING_VALUE, locationId);
						vehicleProperties.add(vehicleProperty);

						vehicleProperty = new VehicleProperty();

						vehicleProperty.setVehiclePropertyType(VehiclePropertyType.DEPARTURE_NODE);
						vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_STRING_VALUE, locationId);
						vehicleProperties.add(vehicleProperty);
					}

					Location location = Location
							.builder()
							.id(locationId)
							.locationType(locationType)
							.cartesianCoordinates(CartesianCoordinates
									.builder()
									.x(Double.parseDouble(xCoordinate))
									.y(Double.parseDouble(yCoordinate))
									.build())
							.build();

					LocationProperty property = LocationProperty.builder().locationPropertyType(LocationPropertyType.DEMAND).build();
					property.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE,demand);
					location.getLocationProperties().put(LocationPropertyType.DEMAND,property);

					 property = LocationProperty.builder().locationPropertyType(LocationPropertyType.READY_TIME).build();
					property.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE,readyTime);
					location.getLocationProperties().put(LocationPropertyType.READY_TIME,property);

					 property = LocationProperty.builder().locationPropertyType(LocationPropertyType.DUE_DATE).build();
					property.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE,dueDate);
					location.getLocationProperties().put(LocationPropertyType.DUE_DATE,property);

					 property = LocationProperty.builder().locationPropertyType(LocationPropertyType.SERVICE_TIME).build();
					property.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE,serviceTime);
					location.getLocationProperties().put(LocationPropertyType.SERVICE_TIME,property);



				} else {
					if (line.trim().isEmpty()) {
						continue;
					}
					String[] columnValues = line.split("/");
					//properties
					VehicleProperty vehicleProperty = new VehicleProperty();
					switch (columnValues[0].charAt(0)) {
						case 'Q' -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY);
							vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE, columnValues[1].replaceAll("/", ""));
						}
						case 'C' -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.VEHICLE_LOAD_CAPACITY);
							vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE, columnValues[1].replaceAll("/", ""));
						}
						case 'r' -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.FUEL_CONSUMPTION_RATE);
							vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE, columnValues[1].replaceAll("/", ""));
						}
						case 'g' -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.INVERSE_REFUELING_RATE);
							vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE, columnValues[1].replaceAll("/", ""));
						}
						case 'v' -> {
							vehicleProperty.setVehiclePropertyType(VehiclePropertyType.AVERAGE_VELOCITY);
							vehicleProperty.getPropertyMappings().put(PropertyKey.SINGLE_DOUBLE_VALUE, columnValues[1].replaceAll("/", ""));
						}
					}
					vehicleProperties.add(vehicleProperty);
				}

			}
		}

		return InstanceImpl
				.builder()
				.instanceProperties(instanceProperties)
				.vehicleProperties(vehicleProperties)
				.locations(locations)
				.build();
	}
}
