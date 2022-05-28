package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Gene;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AggregateEqualChanceMutation implements Mutation {

    private final List<Mutation> mutations;

    @Override
    public List<Gene> mutate(List<Gene> genes) {
        Collections.shuffle(mutations);
        return mutations.get(0).mutate(genes);

    }

    @Override
    public String getName() {
        return "Aggregate mutation";
    }
}
