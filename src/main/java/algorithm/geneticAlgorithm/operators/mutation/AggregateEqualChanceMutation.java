package algorithm.geneticAlgorithm.operators.mutation;

import algorithm.geneticAlgorithm.model.Allele;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AggregateEqualChanceMutation implements Mutation {

    private final List<Mutation> mutations;


    @Override
    public List<Allele> mutate(List<Allele> alleles) {
        Collections.shuffle(mutations);
        return mutations.get(0).mutate(alleles);

    }
}
