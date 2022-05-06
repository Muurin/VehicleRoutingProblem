package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.GAChromosome;

import java.util.Collection;

public interface Mutation {

    /**
     * Return a new population with mutated individuals
     * @param individuals
     * @return
     */
    Collection<GAChromosome> mutate(Collection<GAChromosome> individuals);
}
