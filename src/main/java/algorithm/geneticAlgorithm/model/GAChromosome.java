package algorithm.geneticAlgorithm.model;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class GAChromosome implements Comparable<GAChromosome> {

    private double costValue;
    @Builder.Default
    private List<Allele> alleles = new LinkedList<>();


    @Override
    public int compareTo(GAChromosome o) {
        return -Double.compare(costValue, o.getCostValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GAChromosome))return false;
        GAChromosome chromosome = (GAChromosome) o;
        return chromosome.getAlleles().equals(alleles);
    }

}
