package InstanceLoaders;

import Instances.Instance;
import Instances.InstanceImpl;
import Instances.Properties.*;
import Model.Enum.LocationType;
import Model.Location;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import util.CartesianCoordinates;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Montoya et al. 2016
 * Instances for the electric vehicle routing problem with non-linear charging functions
 * Number of instances: 120
 * -------------------------------------------------------------------------------------------------------------
 * <network>
 * - the node with type=0 is the depot
 * - the nodes with type=1 are the customers
 * - the nodes with type=2 are the charging stations (CSs)
 * - coordinates are given in km
 * - nodes with type=2 define the type of charging station (slow, normal, fast) in tag <cs_type>
 * - computations must be done using double precision (14 decimal) Euclidean distances
 * <fleet>
 * - There is just one type of electric vehicle in the 120 instances
 * - Routes start and end at node 0 (the depot)
 * - <speed_factor> is given in km/h
 * - <consumption_rate> defines the energy consumption in wh/km
 * - <battery_capacity> defines the total capacity in wh
 * - <function cs_type="X"> defines the charging function of the electric vehicle when charged at a station of type X
 * - The charging functions are piecewise linear functions with 3 break points (pus point 0,0)
 * - The break points are given in 2D coordinates (X:<charging_time>,Y:<battery_level>)
 * - <battery_level> is given in wh
 * - <charging_time> is given in h
 * <requests>
 * - Each customer has 1 request
 * - Each customer has a service time
 * <p>
 * Instances are named using the following convention: tcAcBsCcDE, where:
 * - A is the method used to place the customers (i.e., 0: random uniform, 1: clustered, 2: mixture of both)
 * - B is the number of customers
 * - C is the number of the CSs,
 * - D is 't' if the CSs are located using a p-median heuristic and 'f' if the CSs were randomly located
 * - E is the number of the instance for each combination of parameters (i.e., E={0,1,2,3,4}).
 */
public class Montoya2016EVRP_NLInstanceLoader implements InstanceLoader {


	@Override
	public Instance load(String path) throws IOException, JDOMException {
//
//		SAXBuilder builder = new SAXBuilder();
//		Document doc = builder.build(new File(path));
//		Element rootNode = doc.getRootElement();
//
//		Element network = rootNode.getChild("network");
//
//
//		Set<Location> locations = new HashSet<>();
//		Set<InstanceProperty> instanceProperties = new HashSet<>();
//		Set<VehicleProperty> vehicleProperties = new HashSet<>();
//
//		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.ELECTRIC_VEHICLE).build());
//		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.HOMOGENOUS_FLEET).build());
//		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.NON_LINEAR_CHARGING).build());
//
//		Map<String, String> locationIdRequestServiceTimeMap = new HashMap<>();
//
//		for (Element request : network.getChild("requests").getChildren("requests")) {
//			locationIdRequestServiceTimeMap.put(request.getAttributeValue("node"), request.getChild("service_time").getText());
//		}
//
//
//		for (Element locationElement : network.getChild("nodes").getChildren("node")) {
//
//			Location location = Location
//					.builder()
//					.id(locationElement.getAttributeValue("id"))
//					.locationType(switch (locationElement.getAttribute("type").toString()) {
//						case "0" -> LocationType.DEPOT;
//						case "2" -> LocationType.RECHARGING_STATION;
//						case "1" -> LocationType.CUSTOMER_LOCATION;
//						default -> throw new RuntimeException();
//					})
//					.cartesianCoordinates(CartesianCoordinates
//							.builder()
//							.x(Double.parseDouble(locationElement.getChild("cx").getText()))
//							.y(Double.parseDouble(locationElement.getChild("cy").getText()))
//							.build())
//					.build();
//
//			HashMap<PropertyKey, Object> locationPropertyValues = new HashMap<>();
//			if (location.getLocationType() == LocationType.CUSTOMER_LOCATION) {
//
//				locationPropertyValues.put(PropertyKey.SINGLE_DOUBLE_VALUE, locationElement.getChild("service_time").getText());
//
//				location.getLocationProperties().put(LocationPropertyType.SERVICE_TIME,LocationProperty
//						.builder()
//						.locationPropertyType(LocationPropertyType.SERVICE_TIME)
//						.propertyMappings(locationPropertyValues)
//						.build());
//			} else if (location.getLocationType() == LocationType.RECHARGING_STATION) {
//
//				LocationProperty property= LocationProperty.builder().locationPropertyType(LocationPropertyType.CHARGING_FUNCTION_TYPE).build();
//				property.getPropertyMappings().put()
//				locationPropertyValues.put(.toString(),LocationProperty.builder().build() locationElement.getChild("custom").getChild("cs_type").getText());
//
//				location.getLocationProperties().add(LocationProperty
//						.builder()
//						.locationPropertyType(LocationPropertyType.SERVICE_TIME)
//						.propertyValues(locationPropertyValues)
//						.build());
//			}
//
//		}
//		for (Element vehiclePropertyElement : network.getChild("fleet").getChildren()) {
//			VehicleProperty vehicleProperty = new VehicleProperty();
//			switch (vehiclePropertyElement.getName()) {
//				case "departure_node":
//					vehicleProperty.setVehiclePropertyType(VehiclePropertyType.DEPARTURE_NODE);
//					vehicleProperty.getPropertyMappings().put(VehiclePropertyType.DEPARTURE_NODE.toString(), vehiclePropertyElement.getText());
//					break;
//				case "arrival_node":
//					vehicleProperty.setVehiclePropertyType(VehiclePropertyType.ARRIVAL_NODE);
//					vehicleProperty.getPropertyMappings().put(VehiclePropertyType.ARRIVAL_NODE.toString(), vehiclePropertyElement.getText());
//					break;
//				case "max_travel_time":
//					vehicleProperty.setVehiclePropertyType(VehiclePropertyType.MAX_TRAVEL_TIME);
//					vehicleProperty.getPropertyMappings().put(VehiclePropertyType.MAX_TRAVEL_TIME.toString(), vehiclePropertyElement.getText());
//					break;
//				case "speed_factor":
//					vehicleProperty.setVehiclePropertyType(VehiclePropertyType.AVERAGE_VELOCITY);
//					vehicleProperty.getPropertyMappings().put(VehiclePropertyType.AVERAGE_VELOCITY.toString(), vehiclePropertyElement.getText());
//					break;
//				case "custom":
//					for (Element customProperty : vehiclePropertyElement.getChildren()) {
//						switch (customProperty.getName()) {
//							case "consumption_rate":
//								vehicleProperty.setVehiclePropertyType(VehiclePropertyType.FUEL_CONSUMPTION_RATE);
//								vehicleProperty.getPropertyMappings().put(VehiclePropertyType.FUEL_CONSUMPTION_RATE, vehiclePropertyElement.getText());
//								break;
//							case "battery_capacity":
//								vehicleProperty.setVehiclePropertyType(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY);
//								vehicleProperty.getPropertyMappings().put(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY, vehiclePropertyElement.getText());
//								break;
//							case "charging_functions":
//								for (Element chargingFunction : customProperty.getChildren("function")) {
//									String name = chargingFunction.getAttributeValue("cs_type");
//
//								}
//								break;
//						}
//					}
//					break;
//			}
//		}
//
//
//		return InstanceImpl
//				.builder()
//				.instanceProperties(instanceProperties)
//				.vehicleProperties(vehicleProperties)
//				.locations(locations)
//				.build();
		return null;
	}

}
