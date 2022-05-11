package algorithm.geneticAlgorithm.operators.elimination;

import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.PriorityQueue;

@RequiredArgsConstructor
public class EliminateNWeakestIndividuals implements Elimination {

    private final int numberOfEliminations;

    @Override
    public Population eliminate(Population population) {
        Population result = new Population(population.getSize());
        result.setNumberOfVehicles(population.getNumberOfVehicles());
        result.setIndividuals(new PriorityQueue<>(new LinkedList<>(population.getIndividuals()).subList(0, numberOfEliminations < population.getIndividuals().size() ?
                population.getIndividuals().size() - numberOfEliminations : 0)));
        return result;
    }
}
