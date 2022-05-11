package algorithm.geneticAlgorithm.operators.elimination;

import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.TreeSet;

@RequiredArgsConstructor
public class EliminateNWeakestIndividuals implements Elimination{

    private final  int numberOfEliminations;

    @Override
    public Population eliminate(Population population) {
        Population result = new Population();
        result.setNumberOfVehicles(population.getNumberOfVehicles());
        result.setSize(population.getSize());
        result.setIndividuals(new TreeSet<>(new LinkedList<>(population.getIndividuals()).subList(0, numberOfEliminations < population.getSize() ? population.getSize() - numberOfEliminations:0)));
        return result;
    }
}
