package algorithm.geneticAlgorithm.model;

import model.Location;
import model.Vehicle;
import solution.SolutionContext;
import solution.SolutionContextFactory;
import solution.evaluators.SolutionEvaluator;
import util.VehicleUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PermutationGAChromosomeFactory implements GAChromosomeFactory {

    private final SolutionEvaluator solutionEvaluator;

    private final ThreadLocal<SolutionContext> solutionContextThreadLocal;


    public PermutationGAChromosomeFactory(SolutionContextFactory solutionContextFactory, SolutionEvaluator solutionEvaluator) {
        this.solutionEvaluator = solutionEvaluator;
        this.solutionContextThreadLocal = ThreadLocal.withInitial(solutionContextFactory::createSolutionContext);
    }

    @Override
    public GAChromosome createGAChromosome(List<Gene> genes) {

        GAChromosome result = GAChromosome
                .builder()
                .genes(genes)
                .costValue(solutionEvaluator.evaluate(extendSolutionWithChargingStations(genes)))
                .build();
        solutionContextThreadLocal.get().reset();
        return result;
    }

    @Override
    public GAChromosome createRandomFeasibleGAChromosome() {

        List<Gene> permutedLocations = solutionContextThreadLocal.get()
                .getCustomers()
                .values()
                .stream()
                .map(location -> Gene.builder().location(location).build())
                .collect(Collectors.toList());

        Collections.shuffle(permutedLocations);
        solutionContextThreadLocal.get().reset();
        return createGAChromosome(permutedLocations);
    }

    @Override
    public double evaluateChromosome(GAChromosome gaChromosome) {
        return solutionEvaluator.evaluate(extendSolutionWithChargingStations(gaChromosome.getGenes()));
    }

    private SolutionContext extendSolutionWithChargingStations(List<Gene> genes) {

        SolutionContext solutionContext = solutionContextThreadLocal.get();
        Long vehicleId = 0L;
        Vehicle currentVehicle = solutionContext.addVehicleSpecificId(vehicleId);

        //for each location
        for (Gene gene : genes) {
            Location destination = gene.getLocation();

            if (!VehicleUtil.canServiceCustomer(currentVehicle, destination)) {
                VehicleUtil.goToDepot(currentVehicle);
                currentVehicle = solutionContext.addVehicleSpecificId(++vehicleId);
            }

            VehicleUtil.passIntermediateChargingStations(currentVehicle, destination);
            currentVehicle.travelTo(destination);
        }
        VehicleUtil.goToDepot(currentVehicle);
        return solutionContext;
    }


}
