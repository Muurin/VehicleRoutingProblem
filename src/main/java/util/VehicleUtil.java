package util;

import instances.Properties.LocationPropertyType;
import instances.Properties.VehiclePropertyType;
import model.Enum.LocationType;
import model.Location;
import model.Vehicle;
import solution.SolutionContext;

import java.util.Comparator;
import java.util.List;

public class VehicleUtil {

    public static Location getArrivalLocation(Vehicle vehicle) {
        return vehicle.getSolutionContext().getDepots().get(PropertiesUtil.getStringPropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));
    }

    public static double requiredFuel(Vehicle vehicle, Location loc1, Location loc2) {
        return PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.FUEL_CONSUMPTION_RATE)) * MathUtil.getEuclideanDistanceTo(loc1, loc2);
    }

    public static boolean canServiceCustomer(Vehicle vehicle, Location customer) {
        if (customer.getLocationType() != LocationType.CUSTOMER_LOCATION) {
            throw new RuntimeException("greska");
        }
        return !vehicle.getSolutionContext().getServicedCustomers().containsKey(customer.getId()) && vehicle.getCurrentLoad() >= PropertiesUtil.getDoublePropertyValue(customer.getLocationProperties().get(LocationPropertyType.DEMAND));
    }

    public static boolean canReachLocationAndNearestChargingStation(Vehicle vehicle, Location targetLocation) {


        if (targetLocation.getLocationType().equals(LocationType.RECHARGING_STATION)) {
            return vehicle.getCurrentFuel() >= requiredFuel(vehicle, vehicle.getCurrentLocation(), targetLocation)
                    && PropertiesUtil.getDoublePropertyValue(vehicle.getVehiclePropertyMap().get(VehiclePropertyType.VEHICLE_FUEL_TANK_CAPACITY)) >=
                    requiredFuel(vehicle, targetLocation, SolutionUtil.findNearestLocationFromSet(vehicle.getSolutionContext().getChargingStations().values(), targetLocation));
        }
        return vehicle.getCurrentFuel() >= requiredFuel(vehicle, vehicle.getCurrentLocation(), targetLocation)
                + requiredFuel(vehicle, targetLocation, SolutionUtil.findNearestLocationFromSet(vehicle.getSolutionContext().getChargingStations().values(), targetLocation));
    }

    public static boolean canReachLocation(Vehicle vehicle, Location targetLocation) {
        return vehicle.getCurrentFuel() >= requiredFuel(vehicle, vehicle.getCurrentLocation(), targetLocation);
    }

    public static Location chooseIntermediateChargingStation(Vehicle vehicle, Location targetLocation) {
        return vehicle.getSolutionContext().getChargingStations().values().stream()
                .filter(location -> canReachLocationAndNearestChargingStation(vehicle, location))
                .min(Comparator.comparingDouble(o -> MathUtil.getEuclideanDistanceTo(o, targetLocation)))
                .orElse(null);
    }

    private static double intermediateDistance(Location startingLocation, Location intermediateLocation, Location destination) {
        return MathUtil.getEuclideanDistanceTo(startingLocation, intermediateLocation) + MathUtil.getEuclideanDistanceTo(intermediateLocation, destination);
    }

    public static boolean canServiceAtLeastOneCustomer(Vehicle vehicle) {
        SolutionContext solutionContext = vehicle.getSolutionContext();
        return solutionContext.getCustomers().values().stream().anyMatch(location -> !solutionContext.getServicedCustomers().containsKey(location.getId()) && canServiceCustomer(vehicle, location));
    }

    /**
     * Returns null if there is no valid location found
     *
     * @param vehicle
     * @param solutionContext
     * @return
     */
    public static Location getClosestReachableAndServiceableCustomer(Vehicle vehicle, SolutionContext solutionContext) {

        List<Location> locationSortedByDistance = SolutionUtil.locationsSortedByDistance(solutionContext.getCustomers().values(), vehicle.getCurrentLocation());
        return locationSortedByDistance.stream().filter(location -> canServiceCustomer(vehicle, location)).findFirst().orElse(null);

    }

    public static void goToDepot(Vehicle currentVehicle) {
        Location depot =currentVehicle.getSolutionContext().getDepots()
                .get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_LOCATION)));
        passIntermediateChargingStationsRelaxedCondition(currentVehicle, depot);
        currentVehicle.travelTo(depot);
    }

    public static void passIntermediateChargingStationsRelaxedCondition(Vehicle currentVehicle, Location destination) {
        while (!VehicleUtil.canReachLocation(currentVehicle, destination)) {
            Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(currentVehicle, destination);
            currentVehicle.travelTo(chargingStation);
        }
    }


    public static void passIntermediateChargingStations(Vehicle currentVehicle, Location destination) {
        while (!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle, destination)) {
            Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(currentVehicle, destination);
            currentVehicle.travelTo(chargingStation);
        }
    }

}
