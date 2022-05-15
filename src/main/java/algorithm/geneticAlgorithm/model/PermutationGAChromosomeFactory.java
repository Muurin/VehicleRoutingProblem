package algorithm.geneticAlgorithm.model;

import solution.SolutionContext;
import solution.SolutionContextFactory;
import solution.evaluators.SolutionEvaluator;
import instances.Properties.VehiclePropertyType;
import model.Location;
import model.Vehicle;
import util.PropertiesUtil;
import util.VehicleUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PermutationGAChromosomeFactory implements GAChromosomeFactory {

    private final SolutionEvaluator solutionEvaluator;

    private final SolutionContextFactory solutionContextFactory;


    public PermutationGAChromosomeFactory(SolutionContextFactory solutionContextFactory, SolutionEvaluator solutionEvaluator) {
        this.solutionEvaluator = solutionEvaluator;
        this.solutionContextFactory = solutionContextFactory;

    }

    @Override
    public GAChromosome createGAChromosome(List<Allele> alleles) {

        return GAChromosome
                .builder()
                .alleles(alleles)
                .costValue(solutionEvaluator.evaluate(extendSolutionWithChargingStations(alleles)))
                .build();
    }

    @Override
    public GAChromosome createRandomFeasibleGAChromosome() {

        List<Allele> permutedLocations = solutionContextFactory.createSolutionContext()
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
        return solutionEvaluator.evaluate(extendSolutionWithChargingStations(gaChromosome.getAlleles()));
    }

    private SolutionContext extendSolutionWithChargingStations(List<Allele> alleles) {

        SolutionContext solutionContext = solutionContextFactory.createSolutionContext();
        Long vehicleId = 0L;
        Vehicle currentVehicle = solutionContext.addVehicleSpecificId(vehicleId);

        //for each location
        for (Allele allele : alleles) {
            Location destination = allele.getLocation();

            if (!VehicleUtil.canServiceCustomer(currentVehicle, destination)) {
                goToDepot(solutionContext, currentVehicle);
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
        goToDepot(solutionContext, currentVehicle);

        return solutionContext;
    }

    private void goToDepot(SolutionContext solutionContext, Vehicle currentVehicle) {
        Location depot = solutionContext.getDepots()
                .get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.DEPARTURE_LOCATION)));
        passIntermediateChargingStations(currentVehicle, depot);
        currentVehicle.travelTo(depot);
    }


    private void passIntermediateChargingStations(Vehicle currentVehicle, Location destination) {
        while (!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle, destination)) {
            Location chargingStation = VehicleUtil.chooseIntermediateChargingStation(currentVehicle, destination);
//            System.out.println("MEDUPUNJENJE! - "+ currentVehicle.getCurrentLocation().getId() + " " + chargingStation.getId()+ " "+ destination.getId());
            currentVehicle.travelTo(chargingStation);
        }
    }

}
