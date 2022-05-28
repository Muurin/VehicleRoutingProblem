package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Gene;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AggregatedEqualChanceCrossover implements Crossover {

    private final List<Crossover> crossovers;

    @Override
    public Collection<List<Gene>> crossover(List<Gene> parent1, List<Gene> parent2) {
        Collections.shuffle(crossovers);
        return crossovers.get(0).crossover(parent1, parent2);
    }

    @Override
    public String getName() {
        return "Aggregated crossover" ;
        //+ crossovers.stream().map(Crossover::getName).collect(Collectors.joining(","));
    }
}
