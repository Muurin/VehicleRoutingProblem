package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class OX implements Crossover {

    @Override
    public Collection<List<Allele>> crossover(List<Allele> chromosome1, List<Allele>  chromosome2) {

        Random r = new Random();

        int crossoverIndex1;
        int crossoverIndex2 ;
        do{
            crossoverIndex1 =r.nextInt(chromosome1.size());

            crossoverIndex2= r.nextInt(chromosome1.size());
        }
        while(crossoverIndex1 == crossoverIndex2);


        return List.of(getOneOffspring(chromosome1,  chromosome2,
                        Math.min(crossoverIndex1,crossoverIndex2),  Math.max(crossoverIndex1,crossoverIndex2)),
                getOneOffspring( chromosome2, chromosome1,
                        Math.min(crossoverIndex1,crossoverIndex2), Math.max(crossoverIndex1,crossoverIndex2)));
    }

    private List<Allele> getOneOffspring(List<Allele> chromosome1, List<Allele> chromosome2,
                                               int index1, int index2) {

        Set<Allele> usedAlleles = new HashSet<>();
        Allele[] offspring = new Allele[chromosome1.size()];
        for (int i = index1; i < index2; i++) {
            Allele fromParent1 = chromosome1.get(i);
            usedAlleles.add(fromParent1);
            offspring[i] = fromParent1;
        }

        int index = 0;

        for (Allele fromParent2 : chromosome2) {

            if (index == index1) {
                index = index2;
            }

            if (!usedAlleles.contains(fromParent2)) {
                offspring[index++] = fromParent2;
            }
        }

        return Arrays.asList(offspring);
    }
}
