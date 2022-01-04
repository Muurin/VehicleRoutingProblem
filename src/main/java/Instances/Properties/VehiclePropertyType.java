package Instances.Properties;

public enum VehiclePropertyType {

	AVERAGE_VELOCITY,
	INVERSE_REFUELING_RATE, //used for linear charging, units of time required to fuel one unit of energy
	FUEL_CONSUMPTION_RATE, //fuel used per one unit of distance
	VEHICLE_FUEL_TANK_CAPACITY,
	VEHICLE_LOAD_CAPACITY,
	VEHICLE_CHARGING_FUNCTION, //used for non linear charging
	DEPARTURE_NODE,
	ARRIVAL_LOCATION,
	MAX_TRAVEL_TIME;

}
