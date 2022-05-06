package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;

import javax.naming.OperationNotSupportedException;

public interface ConvergenceChecker {

    boolean isConverging(Population population);

    default String evaluate(Population population) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    ConvergenceChecker deepCopy();

    String getName();
}
