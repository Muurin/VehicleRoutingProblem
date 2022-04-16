package algorithm.GeneticAlgorithm.model;

import lombok.Data;

import java.util.List;

@Data
public class GAChromosome implements Comparable<GAChromosome> {

    private int numberOfVehicles;

    private double fitness;

    private List<Allele> alleles;


    @Override
    public int compareTo(GAChromosome o) {
        return Double.compare(fitness, o.getFitness());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GAChromosome))return false;
        GAChromosome chromosome = (GAChromosome) o;
        return chromosome.getAlleles().equals(alleles);
    }

}
