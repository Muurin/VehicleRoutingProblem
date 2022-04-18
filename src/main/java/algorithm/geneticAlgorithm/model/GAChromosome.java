package algorithm.geneticAlgorithm.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class GAChromosome implements Comparable<GAChromosome> {

    private double inverseFitness;

    private List<Allele> alleles = new LinkedList<>();


    @Override
    public int compareTo(GAChromosome o) {
        return -Double.compare(inverseFitness, o.getInverseFitness());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GAChromosome))return false;
        GAChromosome chromosome = (GAChromosome) o;
        return chromosome.getAlleles().equals(alleles);
    }

}
