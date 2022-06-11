package algorithm.geneticAlgorithm.executables.analisys;

import lombok.Getter;

import java.util.Collection;

@Getter
public class MinAvgMax {

    private final double min;
    private final double avg;
    private final double max;
    private final double sum;

    public MinAvgMax(Collection<Double> values){
        if(values.size()==0){
            throw new IllegalArgumentException("No values");
        }
        this.min = values.stream().min(Double::compareTo).get();
        this.max = values.stream().max(Double::compareTo).get();
        this.avg = values.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        this.sum = values.stream().mapToDouble(Double::doubleValue).sum();

    }
}