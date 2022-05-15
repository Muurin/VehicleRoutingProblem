package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;

import java.util.List;

public interface Mutation {

    /**
     * Return a new population with mutated individuals
     * @param alleles
     * @return
     */
    List<Allele> mutate(List<Allele> alleles);
}
