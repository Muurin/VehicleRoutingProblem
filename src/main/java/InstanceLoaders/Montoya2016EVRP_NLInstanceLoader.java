package InstanceLoaders;

import Instances.Instance;
import Instances.InstanceImpl;
import Instances.Properties.InstanceProperty;
import Instances.Properties.InstancePropertyType;
import Instances.Properties.VehicleProperty;
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

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(path));
		Element rootNode = doc.getRootElement();

		Element network = rootNode.getChild("network");


		Set<Location> locations = new HashSet<>();
		Set<InstanceProperty> instanceProperties = new HashSet<>();
		Set<VehicleProperty> vehicleProperties = new HashSet<>();

		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.ELECTRIC_VEHICLE).build());
		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.HOMOGENOUS_FLEET).build());
		instanceProperties.add(InstanceProperty.builder().instancePropertyType(InstancePropertyType.NON_LINEAR_CHARGING).build());

		Map<String, String> locationIdRequestServiceTimeMap = new HashMap<>();

		for (Element request : network.getChild("requests").getChildren("requests")) {
			locationIdRequestServiceTimeMap.put(request.getAttributeValue("node"), request.getChild("service_time").getText());
		}


		for (Element location : network.getChild("nodes").getChildren("node")) {
			locations.add(Location
					.builder()
					.id(location.getAttributeValue("id"))
					.locationType(switch (location.getAttribute("type").toString()) {
						case "0" -> LocationType.DEPOT;
						case "2" -> LocationType.RECHARGING_STATION;
						case "1" -> LocationType.CUSTOMER_LOCATION;
						default -> throw new RuntimeException();
					})
					.cartesianCoordinates(CartesianCoordinates
							.builder()
							.x(Double.parseDouble(location.getChild("cx").getText()))
							.y(Double.parseDouble(location.getChild("cy").getText()))
							.build())
					.serviceTime((location.getAttribute("type").toString().equals("1") ? Double
							.parseDouble(locationIdRequestServiceTimeMap
									.get(location.getAttributeValue("id"))) : -1))
					.readyTime(0) // customers are ready at the start, no time-windows
					.dueDate(0) //no time-windows -> no due date
					.demand(1) // 1 service = 1 resource, not capacitated
					.build());
		}
		//TODO Vehicle property parsing, charging function factory, consider units of measurement - standardize for all types of instances
		for (Element vehicleProperty : network.getChild("fleet").getChildren()) {
			switch (vehicleProperty.getName()) {
				case "departure_node":
					break;
				case "arrival_node":
					break;
				case "max_travel_time":
					break;
				case "speed_factor":
					break;
				case "custom":
					for (Element customProperty : vehicleProperty.getChildren()) {
						switch (customProperty.getName()) {
							case "consumption_rate":
								break;
							case "battery_capacity":
								break;
							case "charging_function":
								break;
						}
					}
					break;
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
