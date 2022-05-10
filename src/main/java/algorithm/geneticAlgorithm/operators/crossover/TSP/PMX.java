package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;

import java.util.*;

public class PMX implements Crossover {


    @Override
    public Collection<List<Allele>> crossover(List<Allele> chromosome1, List<Allele> chromosome2) {

        Random r = new Random();

        int crossoverIndex1;
        int crossoverIndex2;
        do {
            crossoverIndex1 = r.nextInt(chromosome1.size());

            crossoverIndex2 = r.nextInt(chromosome1.size());
        }
        while (crossoverIndex1 == crossoverIndex2);


        Allele[] offspring1 = new Allele[chromosome1.size()];
        Allele[] offspring2 = new Allele[chromosome1.size()];

        Map<Allele, Allele> childOneMappings = new HashMap<>();
        Map<Allele, Allele> childTwoMappings = new HashMap<>();

        Set<Allele> usedAlleles1 = new HashSet<>();
        Set<Allele> usedAlleles2 = new HashSet<>();

        int minIndex = Math.min(crossoverIndex1, crossoverIndex2);
        int maxIndex = Math.max(crossoverIndex1, crossoverIndex2);

        for (int i = minIndex; i < maxIndex; i++) {
            Allele fromParent1 = chromosome1.get(i);
            Allele fromParent2 = chromosome2.get(i);

            offspring1[i] = fromParent2;
            offspring2[i] = fromParent1;

            usedAlleles1.add(fromParent2);
            usedAlleles2.add(fromParent1);

            childOneMappings.put(fromParent2, fromParent1);
            childTwoMappings.put(fromParent1, fromParent2);
        }

        for (int i = 0; i < chromosome1.size(); i++) {

            if (offspring1[i] == null) {
                Allele candidateAllele = chromosome1.get(i);
                while (usedAlleles1.contains(candidateAllele)) {
                    candidateAllele = childOneMappings.get(candidateAllele);
                }
                offspring1[i] = candidateAllele;
                usedAlleles1.add(candidateAllele);
            }

            if (offspring2[i] == null) {
                Allele candidateAllele = chromosome2.get(i);
                while (usedAlleles2.contains(candidateAllele)) {
                    candidateAllele = childTwoMappings.get(candidateAllele);
                }
                offspring2[i] = candidateAllele;
                usedAlleles2.add(candidateAllele);
            }
        }

        return List.of(Arrays.asList(offspring1), Arrays.asList(offspring2));
    }


}
