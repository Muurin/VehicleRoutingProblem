package algorithm.geneticAlgorithm.operators.selection;

import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;
import util.Pair;

import java.util.*;

@RequiredArgsConstructor
public class TournamentSelection implements Selection {

    private final int tournamentSize;

    private final double desiredPercentageOfParentPairs;

    @Override
    public List<Pair<GAChromosome>> select(Population population) {

        List<GAChromosome> temp = new LinkedList<>(population.getIndividuals());
        List<Pair<GAChromosome>> result = new LinkedList<>();
        for (int i = 0; i < Math.round(population.getSize() * desiredPercentageOfParentPairs / 4) * 2; i++) {
            result.add(Pair.<GAChromosome>builder().left(tournament(temp)).right(tournament(temp)).build());
        }
        return result;
    }

    private GAChromosome tournament(List<GAChromosome> list) {
        Collections.shuffle(list);
        return new PriorityQueue<>(list.subList(0, tournamentSize)).peek();
    }
}
