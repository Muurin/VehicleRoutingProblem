package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;

public class IterationConvergenceChecker implements ConvergenceChecker{

    private int counter;
    private final int maxIterations;

    public IterationConvergenceChecker(int maxIterations){
        this.counter=0;
        this.maxIterations=maxIterations;
    }
    @Override
    public boolean isConverging(Population population) {
        return ++counter == maxIterations;
    }

    @Override
    public ConvergenceChecker deepCopy() {
        return null;
    }

    @Override
    public String getName() {
        return "IterationConvergenceChecker";
    }
}
