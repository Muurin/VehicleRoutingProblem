package algorithm.GeneticAlgorithm.model;

import Model.Location;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Allele {

    private Location location;

    private double weight;

}
