package algorithm.geneticAlgorithm.operators.selection;

import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import util.Pair;

import java.util.List;

public interface Selection {

    /**
     * Selects pairs of parents for crossover phase
     * @param population - population to perform selection on
     * @return List of parent pairs to produce offspring
     */
    List<Pair<GAChromosome>> select(Population population);
}
