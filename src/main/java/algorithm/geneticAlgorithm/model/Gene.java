package algorithm.geneticAlgorithm.model;

import model.Location;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class Gene {

    private Location location;

    private double weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gene)) return false;
        Gene gene = (Gene) o;
        return location.equals(gene.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString(){
        return location.toString();
    }

}
