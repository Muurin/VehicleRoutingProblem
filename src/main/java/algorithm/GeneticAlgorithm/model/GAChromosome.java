package algorithm.GeneticAlgorithm.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GAChromosome {

    private int numberOfVehicles;

    private double vehicleLoadCapacity;

    private List<Allele> alleles;

}
