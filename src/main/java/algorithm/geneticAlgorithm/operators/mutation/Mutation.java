package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.model.GAChromosome;

import java.util.Collection;
import java.util.List;

public interface Mutation {

    /**
     * Return a new population with mutated individuals
     * @param alleles
     * @return
     */
    List<Allele> mutate(List<Allele> alleles);
}
