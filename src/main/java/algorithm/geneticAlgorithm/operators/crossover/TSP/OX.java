package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Gene;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class OX implements Crossover {

    @Override
    public Collection<List<Gene>> crossover(List<Gene> parent1, List<Gene> parent2) {

        Random r = new Random();

        int crossoverIndex1;
        int crossoverIndex2 ;
        do{
            crossoverIndex1 =r.nextInt(parent1.size());

            crossoverIndex2= r.nextInt(parent1.size());
        }
        while(crossoverIndex1 == crossoverIndex2);


        return List.of(getOneOffspring(parent1, parent2,
                        Math.min(crossoverIndex1,crossoverIndex2),  Math.max(crossoverIndex1,crossoverIndex2)),
                getOneOffspring(parent2, parent1,
                        Math.min(crossoverIndex1,crossoverIndex2), Math.max(crossoverIndex1,crossoverIndex2)));
    }

    @Override
    public String getName() {
        return "OX";
    }

    private List<Gene> getOneOffspring(List<Gene> chromosome1, List<Gene> chromosome2,
                                       int index1, int index2) {

        Set<Gene> usedGenes = new HashSet<>();
        Gene[] offspring = new Gene[chromosome1.size()];
        for (int i = index1; i < index2; i++) {
            Gene fromParent1 = chromosome1.get(i);
            usedGenes.add(fromParent1);
            offspring[i] = fromParent1;
        }

        int index = 0;

        for (Gene fromParent2 : chromosome2) {

            if (index == index1) {
                index = index2;
            }

            if (!usedGenes.contains(fromParent2)) {
                offspring[index++] = fromParent2;
            }
        }

        return Arrays.asList(offspring);
    }
}
