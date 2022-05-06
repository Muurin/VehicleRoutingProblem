package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;

import java.util.List;
import java.util.stream.Collectors;

public class AggregateConvergenceChecker implements ConvergenceChecker{

    private final ConvergenceChecker primaryConvergenceChecker;

    private final List<ConvergenceChecker> secondaryCheckers;


    public AggregateConvergenceChecker(ConvergenceChecker primaryConvergenceChecker, List<ConvergenceChecker> secondaryCheckers){
        this.primaryConvergenceChecker = primaryConvergenceChecker;
        this.secondaryCheckers = secondaryCheckers;
    }

    @Override
    public boolean isConverging(Population population) {
        groupAction(primaryConvergenceChecker,secondaryCheckers,population);
        return primaryConvergenceChecker.isConverging(population);
    }

    @Override
    public ConvergenceChecker deepCopy() {
        return new AggregateConvergenceChecker(primaryConvergenceChecker.deepCopy(),
                secondaryCheckers.stream().map(ConvergenceChecker::deepCopy).collect(Collectors.toList()));
    }

    @Override
    public String getName() {
        return "AggregateConvergenceChecker";
    }

    protected void groupAction(ConvergenceChecker convergenceChecker, List<ConvergenceChecker> convergenceCheckerList, Population population){
    }

}
