package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Gene;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class SequencePertrubationMutation implements Mutation {

    private final double mutationChance;

    private final Random r = new Random();


    @Override
    public List<Gene> mutate(List<Gene> genes) {
        if (r.nextDouble() > mutationChance) {
            return genes;
        }

        int mutationIndex1;
        int mutationIndex2;
        do {
            mutationIndex1 = r.nextInt(genes.size());

            mutationIndex2 = r.nextInt(genes.size());
        }
        while (mutationIndex1 == mutationIndex2);

        int minIndex = Math.min(mutationIndex1, mutationIndex2);
        int maxIndex = Math.max(mutationIndex1, mutationIndex2);

        List<Gene> mutatedSequence = new ArrayList<>(genes.subList(minIndex, maxIndex));
        Collections.shuffle(mutatedSequence);

        List<Gene> result = new ArrayList<>(genes.subList(0, minIndex));
        result.addAll(mutatedSequence);
        result.addAll(genes.subList(maxIndex, genes.size()));

        return result;
    }

    @Override
    public String getName() {
        return "SequencePertrubationMutation";
    }
}
