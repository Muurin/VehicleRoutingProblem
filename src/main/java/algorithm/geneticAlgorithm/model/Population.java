package algorithm.geneticAlgorithm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.TreeSet;

@Getter
@Setter
public class Population {

    private int size;

    private int numberOfVehicles;

    private double lowestInverseFitness;

    private TreeSet<GAChromosome> individuals = new TreeSet<>();

    public boolean isFull() {
        return size == individuals.size();
    }

    public void setIndividuals(TreeSet<GAChromosome> individuals) {
        if (individuals.size() != 0) {
            lowestInverseFitness = Objects.requireNonNull(individuals.pollFirst()).getCostValue();
        }
        this.individuals = individuals;
    }

}
