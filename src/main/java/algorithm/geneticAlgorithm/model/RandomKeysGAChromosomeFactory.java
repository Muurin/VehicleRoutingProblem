package algorithm.geneticAlgorithm.model;

import solution.SolutionContext;
import solution.SolutionContextFactory;
import solution.evaluators.SolutionEvaluator;
import instances.Properties.LocationPropertyType;
import instances.Properties.VehicleProperty;
import instances.Properties.VehiclePropertyType;
import lombok.RequiredArgsConstructor;
import model.Location;
import model.Vehicle;
import util.PropertiesUtil;
import util.Util;
import util.VehicleUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class RandomKeysGAChromosomeFactory implements GAChromosomeFactory {

    int numberOfVehicles;

    private final SolutionContextFactory solutionContextFactory;

    private final SolutionEvaluator solutionEvaluator;

    @Override
    public GAChromosome createGAChromosome(List<Gene> genes) {

        return GAChromosome
                .builder()
                .costValue(solutionEvaluator.evaluate(extendSolutionWithChargingStations(solutionContextFactory, genes)))
                .genes(genes)
                .build();
    }

    @Override
    public GAChromosome createRandomFeasibleGAChromosome() {

        SolutionContext solutionContext = solutionContextFactory.createSolutionContext();

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

        List<Gene> genes = IntStream.range(0, locations.length)
                .mapToObj(e -> Gene.builder().location(locations[e]).weight(randomWeights[e]).build()).collect(Collectors.toList());

        return createGAChromosome(genes);
    }

    @Override
    public double evaluateChromosome(GAChromosome gaChromosome) {
        return solutionEvaluator.evaluate(extendSolutionWithChargingStations(solutionContextFactory,gaChromosome.getGenes()));
    }

    private SolutionContext extendSolutionWithChargingStations(SolutionContextFactory solutionContextFactory, List<Gene> genes) {

        SolutionContext solutionContext = solutionContextFactory.createSolutionContext();
        //for each location
        for (Gene gene : genes) {

            Long vehicleId = Math.round(gene.getWeight());
            //determine vehicle
            Vehicle currentVehicle = !solutionContext.getVehicles().containsKey(vehicleId) ?
                    solutionContext.addVehicleSpecificId(vehicleId) : solutionContext.getVehicles().get(vehicleId);
            //current vehicle hasnt travelled yet

            if (currentVehicle.getVehiclePath().isEmpty()) {
                currentVehicle.travelTo(gene.getLocation());
            } else {
                Location destination = gene.getLocation();
                while (!VehicleUtil.canReachLocation(currentVehicle, destination)) {
                    Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(currentVehicle, destination);
                    currentVehicle.travelTo(chargingStation);
                }
                currentVehicle.travelTo(destination);
            }
        }

        return solutionContext;
    }


}
