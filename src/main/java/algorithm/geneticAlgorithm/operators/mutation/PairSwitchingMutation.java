package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Gene;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class PairSwitchingMutation implements Mutation {

    private final double mutationChance;

    private final int numberOfSwitchedPairs;

    private final Random r = new Random();

    @Override
    public List<Gene> mutate(List<Gene> genes) {
        if (r.nextDouble() > mutationChance) {
            return genes;
        }

        List<Gene> result = new ArrayList<>(genes);
        Gene temp;

        for (int i = 0; i < numberOfSwitchedPairs; i++) {

            int mutationIndex1;
            int mutationIndex2;

            do {
                mutationIndex1 = r.nextInt(genes.size());

                mutationIndex2 = r.nextInt(genes.size());
            }
            while (mutationIndex1 == mutationIndex2);

            temp = result.get(mutationIndex1);
            result.set(mutationIndex1, result.get(mutationIndex2));
            result.set(mutationIndex2, temp);

        }

        return result;
    }

    @Override
    public String getName() {
        return "PairSwitchingMutation";
    }
}
