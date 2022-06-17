package temp;

import algorithm.geneticAlgorithm.actions.AnalisysMultipleEvaluatorsCallback;
import algorithm.geneticAlgorithm.actions.CallbackAction;
import algorithm.geneticAlgorithm.actions.TestingCallback;
import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithmFactory;
import algorithm.geneticAlgorithm.executables.analisys.EvaluatorInfo;
import algorithm.geneticAlgorithm.model.GAChromosomeFactory;
import algorithm.geneticAlgorithm.model.PermutationGAChromosomeFactory;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.crossover.TSP.OX;
import algorithm.geneticAlgorithm.operators.elimination.Elimination;
import algorithm.geneticAlgorithm.operators.elimination.EliminationWithElitism;
import algorithm.geneticAlgorithm.operators.mutation.CyclicMutation;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import algorithm.geneticAlgorithm.operators.selection.TournamentSelection;
import solution.SolutionContextFactory;
import solution.VRPInstancePaths;
import solution.evaluators.SimpleDistanceEvaluator;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instanceLoaders.InstanceLoader;
import instances.Instance;
import org.jdom2.JDOMException;
import solution.evaluators.SimpleTimeEvaluator;
import solution.evaluators.TimeWindowEvaluator;
import util.ComputerPaths;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.*;

public class ProbaGA {

    private static final String tempPath= "/Users/marinovcaricek/Diplomski rad/Results/temp";

    public static void main(String[] args) throws IOException, JDOMException, InterruptedException {


        String pathToInstances = ComputerPaths.pathToInstances;//instancesPath;
        String[] filenames = new File(pathToInstances).list();

        List<EvaluatorInfo> evaluatorInfos = List.of(
                EvaluatorInfo.builder().solutionEvaluator(new SimpleTimeEvaluator()).filename("TIME").build(),
                EvaluatorInfo.builder().solutionEvaluator(new SimpleDistanceEvaluator()).filename("DISTANCE").build(),
                EvaluatorInfo.builder().solutionEvaluator(new TimeWindowEvaluator()).filename("TIME_WINDOWS").build());
        int count=0;
        int cores = Runtime.getRuntime().availableProcessors();
        List<Thread> threads = new LinkedList<>();
            for (String filename : filenames) {
                if (filename.startsWith("read")) {
                    continue;
                }


                Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);

                PermutationGAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator());
                CallbackAction callbackAction = new AnalisysMultipleEvaluatorsCallback(ComputerPaths.pathToResultsProba + "/", null, gaChromosomeFactory, evaluatorInfos);

                GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                        new PopulationFactory(gaChromosomeFactory),
                        new OX(),
                        mutations[3],
                        new TournamentSelection(3, 0.6),
                        new EliminationWithElitism(0.2),
                        new TimeConvergenceChecker(10, TimeIntervalType.MINUTE),
                        callbackAction,
                        populationSizes[0]);

                threads.add(new Thread(geneticAlgorithmFactory::create));
                if (count++ % cores == 0 && count != 0) {

                    for (Thread thread : threads) {
                        thread.start();
                    }
                    for (Thread thread : threads) {
                        thread.join();
                    }

                }
            }
    }
}
