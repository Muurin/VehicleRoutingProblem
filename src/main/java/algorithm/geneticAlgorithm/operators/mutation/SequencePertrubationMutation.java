package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class SequencePertrubationMutation implements Mutation {

    private final double mutationChance;

    @Override
    public List<Allele> mutate(List<Allele> alleles) {
        Random r = new Random();
        if (r.nextDouble() > mutationChance) {
            return alleles;
        }

        int mutationIndex1;
        int mutationIndex2;
        do {
            mutationIndex1 = r.nextInt(alleles.size());

            mutationIndex2 = r.nextInt(alleles.size());
        }
        while (mutationIndex1 == mutationIndex2);

        int minIndex = Math.min(mutationIndex1, mutationIndex2);
        int maxIndex = Math.max(mutationIndex1, mutationIndex2);

        List<Allele> mutatedSequence = new ArrayList<>(alleles.subList(minIndex, maxIndex));
        Collections.shuffle(mutatedSequence);

        List<Allele> result = new ArrayList<>(alleles.subList(0, minIndex));
        result.addAll(mutatedSequence);
        result.addAll(alleles.subList(maxIndex, alleles.size()));

        return result;
    }
}
