package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;

public class TimeConvergenceChecker implements ConvergenceChecker {

    private boolean started;

    private long startedTime = 0;

    private final long maxExecutionTimeMillisecond;

    private TimeConvergenceChecker(long maxExecutionTime) {
        this.maxExecutionTimeMillisecond = maxExecutionTime;
    }

    public TimeConvergenceChecker(long maxExecutionTime, TimeIntervalType timeIntervalType) {
        this.maxExecutionTimeMillisecond = (long) (maxExecutionTime * timeIntervalType.getScalar());
    }

    @Override
    public boolean isConverging(Population population) {
        if (!started) {
            startedTime = System.currentTimeMillis();
            started = true;
        }
        return System.currentTimeMillis() - startedTime > maxExecutionTimeMillisecond;
    }

    @Override
    public ConvergenceChecker deepCopy() {
        return new TimeConvergenceChecker(maxExecutionTimeMillisecond);
    }

    @Override
    public String getName() {
        return "TimeConvergenceChecker";
    }
}
