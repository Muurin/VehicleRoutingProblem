package algorithm.geneticAlgorithm.operators.crossover.TSP;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;

import javax.sound.midi.SysexMessage;
import java.util.*;

public class MOC implements Crossover {


    @Override
    public Collection<List<Allele>> crossover(List<Allele> chromosome1, List<Allele> chromosome2) {

        Random r = new Random();

        int index = r.nextInt(chromosome1.size());

        Set<Allele> parent1LeftAllelesSet = new HashSet<>(chromosome1.subList(0, index));
        Set<Allele> parent2LeftAllelesSet = new HashSet<>(chromosome2.subList(0, index));

        List<Allele> parent1RightAllelesList = new ArrayList<>(chromosome1.subList(index, chromosome1.size()));
        List<Allele> parent2RightAllelesList = new ArrayList<>(chromosome2.subList(index, chromosome2.size()));


        Allele[] offspring1 = new Allele[chromosome1.size()];
        Allele[] offspring2 = new Allele[chromosome1.size()];

        int count1 = 0, count2 = 0;

        for (int i = 0; i < offspring1.length; i++) {

            if (parent2LeftAllelesSet.contains(chromosome1.get(i))) {
                offspring1[i] = chromosome1.get(i);
            }else{
                offspring1[i] = parent2RightAllelesList.get(count1++);
            }

            if (parent1LeftAllelesSet.contains(chromosome2.get(i))) {
                offspring2[i] = chromosome2.get(i);
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
