package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class PairSwitchingMutation implements Mutation {

    private final double mutationChance;

    private final int numberOfSwitchedPairs;

    @Override
    public List<Allele> mutate(List<Allele> alleles) {
        Random r = new Random();
        if (r.nextDouble() > mutationChance) {
            return alleles;
        }

        List<Allele> result = new ArrayList<>(alleles);
        Allele temp;

        for (int i = 0; i < numberOfSwitchedPairs; i++) {

            int mutationIndex1;
            int mutationIndex2;

            do {
                mutationIndex1 = r.nextInt(alleles.size());

                mutationIndex2 = r.nextInt(alleles.size());
            }
            while (mutationIndex1 == mutationIndex2);

            temp = result.get(mutationIndex1);
            result.set(mutationIndex1, result.get(mutationIndex2));
            result.set(mutationIndex2, temp);

        }

        return result;
    }
}
