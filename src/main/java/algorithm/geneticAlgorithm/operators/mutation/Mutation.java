package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Population;

public interface Mutation {

    /**
     * Return a new population with mutated individuals
     * @param population
     * @return
     */
    Population mutate(Population population);
}
