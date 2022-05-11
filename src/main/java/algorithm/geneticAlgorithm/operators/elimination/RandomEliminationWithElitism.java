package algorithm.geneticAlgorithm.operators.elimination;

import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class RandomEliminationWithElitism implements Elimination {

    private final int eliteIndividuals;

    private final int numberOfEliminations;

    @Override
    public Population eliminate(Population population) {
        Population resultPopulation = new Population();

        if (numberOfEliminations > population.getIndividuals().size()) {
            return resultPopulation;
        }

        TreeSet<GAChromosome> result = new TreeSet<>(new ArrayList<>(population.getIndividuals()).subList(0, eliteIndividuals));
        List<GAChromosome> eliminationList = new ArrayList<>(new LinkedList<>(population.getIndividuals()).subList(eliteIndividuals, population.getIndividuals().size()));


        int currentMaxIndex = eliminationList.size();
        Random r = new Random();

        for (int i = 0; i < numberOfEliminations; i++) {
            if (currentMaxIndex == -1) {
                break;
            }
            eliminationList.remove(r.nextInt(currentMaxIndex--));
        }

        resultPopulation.setIndividuals(result);
        return resultPopulation;
    }
}
