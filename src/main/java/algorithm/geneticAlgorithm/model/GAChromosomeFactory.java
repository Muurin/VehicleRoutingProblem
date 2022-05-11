package algorithm.geneticAlgorithm.model;

import java.util.List;

public interface GAChromosomeFactory {

    GAChromosome createGAChromosome(List<Allele> alleles);

    GAChromosome createRandomFeasibleGAChromosome();

    double evaluateChromosome(GAChromosome gaChromosome);

}
