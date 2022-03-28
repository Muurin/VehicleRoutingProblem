package util;

import Instances.Properties.LocationPropertyType;
import Instances.Properties.VehicleProperty;
import Instances.Properties.VehiclePropertyType;
import Model.Location;
import Model.Vehicle;
import algorithm.GeneticAlgorithm.model.Allele;
import algorithm.GeneticAlgorithm.model.GAChromosome;
import algorithm.paths.Route;
import algorithm.solution.SolutionContext;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class GAUtil {

    private Map<VehiclePropertyType, VehicleProperty> vehiclePropertyMap;

    //TODO method that generates random feasible solution using random keys algorithm
    //input : customer Ids, number of vehicles
    //output : feasible GA solution
    // vehicle ordinal starts from 1
    public GAChromosome generateFeasibleGASolution(Map<String, Location> customers, Integer numberOfVehicles) {

        double[] randomWeights = new double[customers.size()];
        Location[] locations = new Location[customers.size()];
        Random random = new Random();

        double[] availableResources = new double[numberOfVehicles];
        Arrays.fill(availableResources, PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_LOAD_CAPACITY)));
        int count = 0;

        Set<Integer> availableVehicles = IntStream.range(1, numberOfVehicles + 1).boxed().collect(Collectors.toSet());

        for (Location location : customers.values()) {

            locations[count] = location;

            int vehicle = getRandomElementFromCollection(availableVehicles);

            double requiredResources = PropertiesUtil.getDoublePropertyValue(location.getLocationProperties().get(LocationPropertyType.DEMAND));

            while (availableResources[vehicle - 1] < requiredResources) {
                availableVehicles.remove(vehicle);
                vehicle = getRandomElementFromCollection(availableVehicles);
            }

            availableResources[vehicle - 1] -= requiredResources;
            randomWeights[count] = vehicle + random.nextDouble();
            count++;
        }

        List<Allele> alleles = IntStream.range(0, locations.length)
                .mapToObj(e -> Allele.builder().location(locations[e]).weight(randomWeights[e]).build()).collect(Collectors.toList());
        return GAChromosome
                .builder()
                .vehicleLoadCapacity(PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_LOAD_CAPACITY)))
                .alleles(alleles).numberOfVehicles(numberOfVehicles)
                .build();

    }

    private <T> T getRandomElementFromCollection(Collection<T> collection) {
        if (collection.isEmpty()) {
            throw new RuntimeException("Not enough vehicles");
        }
        return (T) collection.toArray()[new Random().nextInt(collection.size())];
    }

    public void extendSolutionWithChargingStations(SolutionContext solutionContext, GAChromosome gaChromosome) {

        Location startingLocation = null;
        Location destination = null;

        for (Allele allele : gaChromosome.getAlleles()) {

            Long vehicleId = Math.round(allele.getWeight());
            Vehicle currentVehicle = !solutionContext.getVehicles().containsKey(vehicleId) ?
                    solutionContext.addVehicleSpecificId(vehicleId) : solutionContext.getVehicles().get(vehicleId);

            if (destination == null) {
                startingLocation = destination = allele.getLocation();
                currentVehicle.addRoute(new Route(startingLocation, destination, currentVehicle));

            }

            startingLocation = currentVehicle.getCurrentLocation();
            destination = allele.getLocation();
            while (!VehicleUtil.canReachLocation(currentVehicle, destination)) {
                Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(currentVehicle, destination);
                currentVehicle.addRoute(new Route(startingLocation, chargingStation, currentVehicle));
            }
            currentVehicle.addRoute(new Route(startingLocation, destination, currentVehicle));

        }


    }

}
