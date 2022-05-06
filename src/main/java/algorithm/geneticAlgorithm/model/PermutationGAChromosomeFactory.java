package algorithm.geneticAlgorithm.model;

import algorithm.solution.SolutionContextFactory;
import algorithm.solution.evaluators.SolutionEvaluator;
import lombok.RequiredArgsConstructor;
import util.SolutionUtil;

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
                .costValue(solutionEvaluator.evaluate(SolutionUtil.extendSolutionWithChargingStations(solutionContextFactory,alleles)))
                .build();
    }

    @Override
    public GAChromosome createRandomFeasibleGAChromosome(int numberOfVehicles) {

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

}
