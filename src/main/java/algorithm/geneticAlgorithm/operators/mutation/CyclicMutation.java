package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class CyclicMutation implements Mutation {

    private final int shift;

    private final double mutationChance;

    @Override
    public List<Allele> mutate(List<Allele> alleles) {
        Random r = new Random();
        if (r.nextDouble() > mutationChance) {
            return alleles;
        }

        List<Allele> mutated = alleles.subList(alleles.size() - (shift % alleles.size()), alleles.size());

        mutated.addAll(alleles.subList(0, alleles.size() - (shift % alleles.size())));
        return mutated;
    }
}
