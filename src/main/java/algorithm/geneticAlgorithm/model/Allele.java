package algorithm.geneticAlgorithm.model;

import model.Location;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString(){
        return location.toString();
    }

}
