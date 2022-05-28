package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Gene;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;

import java.util.*;

public class PMX implements Crossover {


    @Override
    public Collection<List<Gene>> crossover(List<Gene> parent1, List<Gene> parent2) {

        Random r = new Random();

        int crossoverIndex1;
        int crossoverIndex2;
        do {
            crossoverIndex1 = r.nextInt(parent1.size());

            crossoverIndex2 = r.nextInt(parent1.size());
        }
        while (crossoverIndex1 == crossoverIndex2);


        Gene[] offspring1 = new Gene[parent1.size()];
        Gene[] offspring2 = new Gene[parent1.size()];

        Map<Gene, Gene> childOneMappings = new HashMap<>();
        Map<Gene, Gene> childTwoMappings = new HashMap<>();

        Set<Gene> usedAlleles1 = new HashSet<>();
        Set<Gene> usedAlleles2 = new HashSet<>();

        int minIndex = Math.min(crossoverIndex1, crossoverIndex2);
        int maxIndex = Math.max(crossoverIndex1, crossoverIndex2);

        for (int i = minIndex; i < maxIndex; i++) {
            Gene fromParent1 = parent1.get(i);
            Gene fromParent2 = parent2.get(i);

            offspring1[i] = fromParent2;
            offspring2[i] = fromParent1;

            usedAlleles1.add(fromParent2);
            usedAlleles2.add(fromParent1);

            childOneMappings.put(fromParent2, fromParent1);
            childTwoMappings.put(fromParent1, fromParent2);
        }

        for (int i = 0; i < parent1.size(); i++) {

            if (offspring1[i] == null) {
                Gene candidateGene = parent1.get(i);
                while (usedAlleles1.contains(candidateGene)) {
                    candidateGene = childOneMappings.get(candidateGene);
                }
                offspring1[i] = candidateGene;
                usedAlleles1.add(candidateGene);
            }

            if (offspring2[i] == null) {
                Gene candidateGene = parent2.get(i);
                while (usedAlleles2.contains(candidateGene)) {
                    candidateGene = childTwoMappings.get(candidateGene);
                }
                offspring2[i] = candidateGene;
                usedAlleles2.add(candidateGene);
            }
        }

        return List.of(Arrays.asList(offspring1), Arrays.asList(offspring2));
    }

    @Override
    public String getName() {
        return "PMX";
    }


}
