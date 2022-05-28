package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Gene;

import java.util.List;

public interface Mutation {

    List<Gene> mutate(List<Gene> genes);

    String getName();
}
