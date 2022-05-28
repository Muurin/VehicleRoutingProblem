package algorithm.geneticAlgorithm.operators.crossover;

import algorithm.geneticAlgorithm.model.Gene;

import java.util.Collection;
import java.util.List;

public interface Crossover {

    Collection<List<Gene>> crossover(List<Gene> parent1, List<Gene> parent2);

    String getName();
}
