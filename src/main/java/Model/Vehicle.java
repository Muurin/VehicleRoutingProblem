package Model;


import Instances.Properties.VehicleProperty;
import Instances.Properties.VehiclePropertyType;
import algorithm.paths.Route;
import lombok.*;
import util.PropertiesUtil;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

	private String id;

	private double currentTime=0;
	private double currentLoad;
	private double currentFuel;
	private double currentDistance=0;

	private Map<VehiclePropertyType, VehicleProperty> vehiclePropertyMap=new HashMap<>();

	private Stack<Route> vehiclePath = new Stack<>();

	public void addRoute(Route route){
		vehiclePath.add(route);
	}

	public Route removeLastRoute(){
		return vehiclePath.pop();
	}

	public Route checkLastRoute(){
		return vehiclePath.peek();
	}

	public void resetPath(){

		vehiclePath.clear();
		currentTime=0;
		currentDistance=0;
	}

	//full recharge
	public double refuel() {
		return (PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY))-currentFuel)
				* PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.INVERSE_REFUELING_RATE));
	}
	//TODO implement heuristic for charging -check for fueling/charging rate or function
	public double refuel(Object object){
		return 0;
	}
}
