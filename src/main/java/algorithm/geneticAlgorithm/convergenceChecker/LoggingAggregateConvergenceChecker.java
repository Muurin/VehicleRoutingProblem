package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggingAggregateConvergenceChecker extends AggregateConvergenceChecker{

    private final Map<String,String> loggingFiles = new HashMap<>();

    public LoggingAggregateConvergenceChecker(ConvergenceChecker primaryConvergenceChecker, List<ConvergenceChecker> secondaryCheckers, String loggingDirectoryPath) {
        super(primaryConvergenceChecker, secondaryCheckers);
        loggingFiles.put(primaryConvergenceChecker.getName(),loggingDirectoryPath+ File.separator+primaryConvergenceChecker.getName());
        secondaryCheckers.forEach(convergenceChecker -> loggingFiles.put(convergenceChecker.getName(),loggingDirectoryPath+ File.separator+convergenceChecker.getName()));
    }

    @Override
    protected void groupAction(ConvergenceChecker primaryConvergenceChecker, List<ConvergenceChecker> secondaryConvergenceCheckers, Population population) {



    }
}
