package algorithm.geneticAlgorithm.model;

import algorithm.solution.SolutionContext;
import algorithm.solution.SolutionContextFactory;
import algorithm.solution.evaluators.SolutionEvaluator;
import instances.Properties.VehiclePropertyType;
import lombok.RequiredArgsConstructor;
import model.Location;
import model.Vehicle;
import util.PropertiesUtil;
import util.VehicleUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PermutationGAChromosomeFactory implements GAChromosomeFactory {

    private final SolutionContextFactory solutionContextFactory;

    private final SolutionEvaluator solutionEvaluator;

    @Override
    public GAChromosome createGAChromosome(List<Allele> alleles) {

        return GAChromosome
                .builder()
                .alleles(alleles)
                .costValue(solutionEvaluator.evaluate(extendSolutionWithChargingStations(solutionContextFactory, alleles)))
                .build();
    }

    @Override
    public GAChromosome createRandomFeasibleGAChromosome() {

        List<Allele> permutedLocations = solutionContextFactory
                .createSolutionContext()
                .getCustomers()
                .values()
                .stream()
                .map(location -> Allele.builder().location(location).build())
                .collect(Collectors.toList());

        Collections.shuffle(permutedLocations);
        return createGAChromosome(permutedLocations);
    }

    @Override
    public double evaluateChromosome(GAChromosome gaChromosome) {
        return solutionEvaluator.evaluate(extendSolutionWithChargingStations(solutionContextFactory, gaChromosome.getAlleles()));
    }

    private SolutionContext extendSolutionWithChargingStations(SolutionContextFactory solutionContextFactory, List<Allele> alleles) {

        SolutionContext solutionContext = solutionContextFactory.createSolutionContext();
        Long vehicleId = 0L;
        Vehicle currentVehicle = solutionContext.addVehicleSpecificId(vehicleId);
        //for each location
        for (Allele allele : alleles) {
            Location destination = allele.getLocation();

            if (!VehicleUtil.canServiceCustomer(currentVehicle, destination)) {
                Location depot = solutionContext.getDepots()
                        .get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_LOCATION)));
                passIntermediateChargingStations(currentVehicle, depot);
                currentVehicle.travelTo(depot);
                currentVehicle = solutionContext.addVehicleSpecificId(++vehicleId);
            }
            //current vehicle hasnt travelled yet

            if (currentVehicle.getVehiclePath().isEmpty()) {
                currentVehicle.travelTo(destination);
            } else {
                passIntermediateChargingStations(currentVehicle, destination);
                currentVehicle.travelTo(destination);
            }
        }

        return solutionContext;
    }

    private void passIntermediateChargingStations(Vehicle currentVehicle, Location destination) {
        while (!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle, destination)) {
            Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(currentVehicle, destination);
            currentVehicle.travelTo(chargingStation);
        }
    }

}
