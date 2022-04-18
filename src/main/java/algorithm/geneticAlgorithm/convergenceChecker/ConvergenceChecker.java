package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;

public interface ConvergenceChecker {

    boolean isConverging(Population population);
}
