package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AggregatedEqualChanceCrossover implements Crossover {

    private final List<Crossover> crossovers;

    @Override
    public Collection<List<Allele>> crossover(List<Allele> gaChromosome1, List<Allele> gaChromosome2) {
        Collections.shuffle(crossovers);
        return crossovers.get(0).crossover(gaChromosome1,gaChromosome2);
    }
}
