package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Gene;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;

import java.util.*;

public class MOC implements Crossover {


    @Override
    public Collection<List<Gene>> crossover(List<Gene> parent1, List<Gene> parent2) {

        Random r = new Random();

        int index = r.nextInt(parent1.size());

        Set<Gene> parent1LeftAllelesSet = new HashSet<>(parent1.subList(0, index));
        Set<Gene> parent2LeftAllelesSet = new HashSet<>(parent2.subList(0, index));

        List<Gene> parent1RightAllelesList = new ArrayList<>(parent1.subList(index, parent1.size()));
        List<Gene> parent2RightAllelesList = new ArrayList<>(parent2.subList(index, parent2.size()));


        Gene[] offspring1 = new Gene[parent1.size()];
        Gene[] offspring2 = new Gene[parent1.size()];

        int count1 = 0, count2 = 0;

        for (int i = 0; i < offspring1.length; i++) {

            if (parent2LeftAllelesSet.contains(parent1.get(i))) {
                offspring1[i] = parent1.get(i);
            }else{
                offspring1[i] = parent2RightAllelesList.get(count1++);
            }

            if (parent1LeftAllelesSet.contains(parent2.get(i))) {
                offspring2[i] = parent2.get(i);
            }else{
                offspring2[i] = parent1RightAllelesList.get(count2++);
            }
        }

        return List.of(Arrays.asList(offspring1), Arrays.asList(offspring2));
    }

    @Override
    public String getName() {
        return "MOC";
    }
}
