package algorithm.geneticAlgorithm.convergenceChecker;

import lombok.Getter;

@Getter
public enum TimeIntervalType {
    MILLISECOND(1),
    SECOND(60),
    MINUTE(3600),
    HOUR(216000);

    private final double scalar;

    TimeIntervalType(double scalar){
        this.scalar=scalar;
    }


}
