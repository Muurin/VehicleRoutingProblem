package algorithm.geneticAlgorithm.convergenceChecker;

import lombok.Getter;

@Getter
public enum TimeIntervalType {
    MILLISECOND(1),
    SECOND(1000),
    MINUTE(60000),
    HOUR(3600000);

    private final double scalar;

    TimeIntervalType(double scalar){
        this.scalar=scalar;
    }


}
