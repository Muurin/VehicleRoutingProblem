package algorithm.geneticAlgorithm.executables;

import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.executables.analisys.RunConfig;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.elimination.Elimination;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import algorithm.geneticAlgorithm.actions.CallbackAction;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class GeneticAlgorithmFactory {

    private final PopulationFactory populationFactory;

    private final Crossover crossover;

    private final Mutation mutation;

    private final Selection selection;

    private final Elimination elimination;

    private final ConvergenceChecker convergenceChecker;

    private final CallbackAction callbackAction;

    private final int popSize;

    public GeneticAlgorithm create() {
        return new GeneticAlgorithm(
                populationFactory,
                crossover,
                mutation,
                selection,
                elimination,
                convergenceChecker.deepCopy(),
                callbackAction,
                popSize);
    }

    public void start(int nthread) throws InterruptedException {

        for (int i = 0; i < nthread; i++) {
            Thread thread = new Thread(new GeneticAlgorithm(
                    populationFactory,
                    crossover,
                    mutation,
                    selection,
                    elimination,
                    convergenceChecker.deepCopy(),
                    callbackAction,
                    popSize));
            thread.start();
        }
    }


    public void start(List<String> threadSpecificCallbackConfigs) throws InterruptedException {
        List<Thread> threads = new LinkedList<>();
        for (String threadSpecificCallbackConfig : threadSpecificCallbackConfigs) {
            Thread thread = new Thread(new GeneticAlgorithm(
                    populationFactory,
                    crossover,
                    mutation,
                    selection,
                    elimination,
                    convergenceChecker.deepCopy(),
                    callbackAction.cloneWithDifferentConfiguration(threadSpecificCallbackConfig),
                    popSize));
            threads.add(thread);
            thread.start();
        }
        for (Thread th : threads) {
            th.join();
        }
    }

    public void start(Collection<RunConfig> configs) throws InterruptedException {
        List<Thread> threads = new LinkedList<>();
        for (RunConfig config : configs) {
            Thread thread = new Thread(new GeneticAlgorithm(
                    config.getPopulationFactory(),
                    crossover,
                    mutation,
                    selection,
                    elimination,
                    convergenceChecker.deepCopy(),
                    callbackAction.cloneWithDifferentConfiguration(config.getResultFilename()),
                    popSize));
            threads.add(thread);
            thread.start();
        }
        for (Thread th : threads) {
            th.join();
        }
    }

}




