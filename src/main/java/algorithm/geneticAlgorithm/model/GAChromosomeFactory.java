package algorithm.geneticAlgorithm.model;

import java.util.List;

public interface GAChromosomeFactory {

    GAChromosome createGAChromosome(List<Gene> genes);

    GAChromosome createRandomFeasibleGAChromosome();

    double evaluateChromosome(GAChromosome gaChromosome);

}
