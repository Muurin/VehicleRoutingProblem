package algorithm.geneticAlgorithm.actions;

import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.model.Population;
import util.FileWriterUtil;

import java.io.File;

public class IterationsThroughTimeLoggincCallback implements CallbackAction {

    private String pathToLogs;

    private String filename;

    private Long loggingTimeInterval;

    private Long lastLog;

    public IterationsThroughTimeLoggincCallback(String pathToLogs, String filename, Long numberOfLogs, Long logTime, TimeIntervalType timeIntervalType) {
        this.pathToLogs = pathToLogs;
        this.filename = filename;
        this.loggingTimeInterval = (long) (logTime * timeIntervalType.getScalar() / numberOfLogs);
    }

    @Override
    public void resultAction(Population population) {

    }

    @Override
    public void crossoverAction(Population population) {

    }

    @Override
    public void selectionAction(Population population) {

    }

    @Override
    public void mutationAction(Population population) {

    }

    @Override
    public void iterationAction(Population population) {
        if (lastLog == null || lastLog + loggingTimeInterval < System.currentTimeMillis()){
            FileWriterUtil.writeToNewOrExistingFile(pathToLogs, filename, String.valueOf(population.getIndividuals().peek().getCostValue()));
            lastLog=System.currentTimeMillis();
        }
    }

    @Override
    public CallbackAction cloneWithDifferentConfiguration(String configuration) {
        return null;
    }
}
