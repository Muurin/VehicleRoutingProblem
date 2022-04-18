package algorithm.geneticAlgorithm.operators.elimination;

import algorithm.geneticAlgorithm.model.Population;

public interface Elimination {
    /**
     * Eliminates invidividuals from a population, returns a new population with leftover individuals
     * @param population
     * @return
     */
    Population eliminate(Population population);
}
