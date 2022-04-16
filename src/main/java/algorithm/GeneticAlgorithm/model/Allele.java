package algorithm.GeneticAlgorithm.model;

import Model.Location;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Allele {

    private Location location;

    private double weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Allele)) return false;
        Allele allele = (Allele) o;
        return location.equals(allele.getLocation());
    }

}
