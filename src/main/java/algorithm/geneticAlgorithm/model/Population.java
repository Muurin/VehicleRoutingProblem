package algorithm.geneticAlgorithm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.PriorityQueue;

@Getter
@Setter
@RequiredArgsConstructor
public class Population {

    private final int size;

    private int numberOfVehicles;

    private double lowestInverseFitness;

    private PriorityQueue<GAChromosome> individuals = new PriorityQueue<>();

    public boolean isFull() {
        return size == individuals.size();
    }

    public void setIndividuals(PriorityQueue<GAChromosome> individuals) {
        if (individuals.size() != 0) {
            lowestInverseFitness = Objects.requireNonNull(individuals.peek()).getCostValue();
        }
        this.individuals = individuals;
    }

}
