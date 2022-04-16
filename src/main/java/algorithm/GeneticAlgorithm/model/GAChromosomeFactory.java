package algorithm.GeneticAlgorithm.model;

import Instances.Properties.LocationPropertyType;
import Instances.Properties.VehicleProperty;
import Instances.Properties.VehiclePropertyType;
import Model.Location;
import Model.Vehicle;
import algorithm.paths.Route;
import algorithm.solution.SolutionContext;
import algorithm.solution.evaluators.SolutionEvaluator;
import lombok.RequiredArgsConstructor;
import util.Util;
import util.PropertiesUtil;
import util.VehicleUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class GAChromosomeFactory {

    private final SolutionContext solutionContext;

    private final SolutionEvaluator solutionEvaluator;

    public GAChromosome createGAChromosome(int numberOfVehicles, List<Allele> alleles){

        GAChromosome gaChromosome = new GAChromosome();

        gaChromosome.setNumberOfVehicles(numberOfVehicles);
        gaChromosome.setAlleles(alleles);

        extendSolutionWithChargingStations(alleles);
        gaChromosome.setFitness(solutionEvaluator.evaluate(solutionContext));
        solutionContext.reset();

        return gaChromosome;
    }

    public GAChromosome createRandomFeasibleGAChromosome(int numberOfVehicles){
        Map<String, Location> customers = solutionContext.getCustomers();
        Map<VehiclePropertyType, VehicleProperty> vehiclePropertyMap = solutionContext.addVehicle().getVehiclePropertyMap();

        double[] randomWeights = new double[customers.size()];
        Location[] locations = new Location[customers.size()];
        Random random = new Random();

        double[] availableResources = new double[numberOfVehicles];
        Arrays.fill(availableResources, PropertiesUtil.getDoublePropertyValue(vehiclePropertyMap.get(VehiclePropertyType.VEHICLE_LOAD_CAPACITY)));
        int count = 0;

        Set<Integer> availableVehicles = IntStream.range(1, numberOfVehicles + 1).boxed().collect(Collectors.toSet());

        for (Location location : customers.values()) {

            locations[count] = location;

            int vehicle = Util.getRandomElementFromCollection(availableVehicles);

            double requiredResources = PropertiesUtil.getDoublePropertyValue(location.getLocationProperties().get(LocationPropertyType.DEMAND));

            while (availableResources[vehicle - 1] < requiredResources) {
                availableVehicles.remove(vehicle);
                vehicle = Util.getRandomElementFromCollection(availableVehicles);
            }

            availableResources[vehicle - 1] -= requiredResources;
            randomWeights[count] = vehicle + random.nextDouble();
            count++;
        }

        List<Allele> alleles = IntStream.range(0, locations.length)
                .mapToObj(e -> Allele.builder().location(locations[e]).weight(randomWeights[e]).build()).collect(Collectors.toList());

        return createGAChromosome(numberOfVehicles,alleles);
    }

    private void extendSolutionWithChargingStations(List<Allele> alleles) {

        Location startingLocation;
        Location destination = null;

        for (Allele allele : alleles) {

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
