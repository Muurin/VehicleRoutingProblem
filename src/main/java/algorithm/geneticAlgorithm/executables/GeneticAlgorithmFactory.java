package algorithm.geneticAlgorithm.executables;

import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.elimination.Elimination;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import algorithm.geneticAlgorithm.actions.CallbackAction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneticAlgorithmFactory {

    private final PopulationFactory populationFactory;

    private final Crossover crossover;

    private final Mutation mutation;

    private final Selection selection;

    private final Elimination elimination;

    private final ConvergenceChecker convergenceChecker;

    private final CallbackAction callbackAction;

    public void start(int nthread, int popSize, int numberOfVehicles) throws InterruptedException {

        for(int i = 0;i<nthread;i++){
            Thread thread = new Thread(new GeneticAlgorithm(
                    populationFactory,
                    crossover,
                    mutation,
                    selection,
                    elimination,
                    convergenceChecker.deepCopy(),
                    callbackAction));
            thread.start();
            thread.join();
        }

    }
}




