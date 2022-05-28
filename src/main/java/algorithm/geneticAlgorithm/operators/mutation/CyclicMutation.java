package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Gene;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class CyclicMutation implements Mutation {

    private final int shift;

    private final double mutationChance;

    private final Random r = new Random();

    @Override
    public List<Gene> mutate(List<Gene> genes) {

        if (r.nextDouble() > mutationChance) {
            return genes;
        }

        List<Gene> mutated = new LinkedList<>(genes.subList(genes.size() - (shift % genes.size()), genes.size()));

        mutated.addAll(genes.subList(0, genes.size() - (shift % genes.size())));
        return mutated;
    }

    @Override
    public String getName() {
        return "CyclicMutation";
    }
}
