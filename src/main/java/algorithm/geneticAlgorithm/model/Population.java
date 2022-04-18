package algorithm.geneticAlgorithm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.TreeSet;

@Getter
@Setter
public class Population {

    private int size;

    private int numberOfVehicles;

    private double lowestInverseFitness;

    private TreeSet<GAChromosome> individuals = new TreeSet<>();

    public boolean isFull(){
        return size == individuals.size();
    }

}
