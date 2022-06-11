package algorithm.geneticAlgorithm.model;

import solution.SolutionContext;

import java.util.List;

public interface GAChromosomeFactory {

    GAChromosome createGAChromosome(List<Gene> genes);

    GAChromosome createRandomFeasibleGAChromosome();

    double evaluateChromosome(GAChromosome gaChromosome);

    SolutionContext extendSolutionWithChargingStations(List<Gene> genes);

}
