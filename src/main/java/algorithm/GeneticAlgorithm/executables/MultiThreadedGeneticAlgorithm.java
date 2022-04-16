package algorithm.GeneticAlgorithm.executables;

import Instances.Instance;
import algorithm.GeneticAlgorithm.operators.crossover.Crossover;
import algorithm.GeneticAlgorithm.operators.mutation.Mutation;
import algorithm.GeneticAlgorithm.operators.selection.Selection;
import algorithm.solution.evaluators.SolutionEvaluator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MultiThreadedGeneticAlgorithm {

    private final Instance instance;

    private final SolutionEvaluator solutionEvaluator;

    private final Crossover crossover;

    private final Mutation mutation;

    private final Selection selection;


    public void start(int nrep, int nmut, int npop){

    }



}
