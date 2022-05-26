package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;
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
    public List<Allele> mutate(List<Allele> alleles) {

        if (r.nextDouble() > mutationChance) {
            return alleles;
        }

        List<Allele> mutated = new LinkedList<>(alleles.subList(alleles.size() - (shift % alleles.size()), alleles.size()));

        mutated.addAll(alleles.subList(0, alleles.size() - (shift % alleles.size())));
        return mutated;
    }

    @Override
    public String getName() {
        return "CyclicMutation";
    }
}
