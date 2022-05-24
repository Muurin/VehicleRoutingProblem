package algorithm.geneticAlgorithm.operators.elimination;

import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.PriorityQueue;

@RequiredArgsConstructor
public class EliminationWithElitism implements Elimination{

    private final double elitePercentage;

    @Override
    public Population eliminate(Population population) {
        Population resultPopulation = new Population(population.getSize());

        PriorityQueue<GAChromosome> result = new PriorityQueue<>(new ArrayList<>(population.getIndividuals()).subList(0, (int) Math.round(elitePercentage*population.getSize())));
        resultPopulation.setIndividuals(result);
        return resultPopulation;
    }
}
