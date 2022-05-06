package algorithm.geneticAlgorithm.model;

import algorithm.solution.SolutionContext;
import algorithm.solution.SolutionContextFactory;

import java.util.List;

public interface GAChromosomeFactory {

    GAChromosome createGAChromosome(List<Allele> alleles);

    GAChromosome createRandomFeasibleGAChromosome(int numberOfVehicles);
}
