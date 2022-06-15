package algorithm.geneticAlgorithm.executables.analisys;

import algorithm.geneticAlgorithm.actions.AnalisysMultipleEvaluatorsCallback;
import algorithm.geneticAlgorithm.actions.CallbackAction;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithmFactory;
import algorithm.geneticAlgorithm.model.PermutationGAChromosomeFactory;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.crossover.TSP.OX;
import algorithm.geneticAlgorithm.operators.elimination.EliminationWithElitism;
import algorithm.geneticAlgorithm.operators.selection.TournamentSelection;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instances.Instance;
import solution.SolutionContextFactory;
import solution.evaluators.SimpleDistanceEvaluator;
import solution.evaluators.SimpleTimeEvaluator;
import solution.evaluators.TimeWindowEvaluator;
import util.ComputerPaths;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.*;

public class HeapErrorMain {

    private static Queue<RunConfig> runConfigs =  new LinkedList<>();//new PriorityQueue<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        String pathToInstances = ComputerPaths.pathToInstances;//instancesPath;
        String[] filenames = new File(pathToInstances).list();

        List<EvaluatorInfo> evaluatorInfos = List.of(
                EvaluatorInfo.builder().solutionEvaluator(new SimpleTimeEvaluator()).filename("TIME").build(),
                EvaluatorInfo.builder().solutionEvaluator(new SimpleDistanceEvaluator()).filename("DISTANCE").build(),
                EvaluatorInfo.builder().solutionEvaluator(new TimeWindowEvaluator()).filename("TIME_WINDOWS").build()
        );

        int cores = Runtime.getRuntime().availableProcessors();
        int count = 1;
        for (EvaluatorInfo evaluatorInfo : evaluatorInfos) {
            for (String filename : filenames) {
                if (filename.startsWith("read")) {
                    continue;
                }
                Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);

                PermutationGAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator());
                CallbackAction callbackAction = new AnalisysMultipleEvaluatorsCallback(gaAnalisysPath + "_optimized_by_" + evaluatorInfo.getFilename(), null, gaChromosomeFactory, evaluatorInfos);

                PopulationFactory populationFactory = new PopulationFactory(gaChromosomeFactory);
                GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                        populationFactory,
                        new OX(),
                        mutations[3],
                        new TournamentSelection(3, 0.6),
                        new EliminationWithElitism(0.2),
                        new TimeConvergenceChecker(1, TimeIntervalType.MINUTE),
                        callbackAction,
                        populationSizes[0]);

                List<RunConfig> newConfigs = IntStream.range(0, 10).mapToObj(operand -> RunConfig
                        .builder()
                        .populationFactory(populationFactory)
                        .resultFilename(ComputerPaths.pathToResults + "/" + evaluatorInfo.getFilename() + "," + operand)
                        .build()).collect(Collectors.toList());
                runConfigs.addAll(newConfigs);

                if (count++ % cores == 0 ) {

                    List<RunConfig> toRun = IntStream.range(0, Math.min(cores, runConfigs.size())).mapToObj(operand -> runConfigs.poll()).collect(Collectors.toList());

                    geneticAlgorithmFactory.start(toRun);

                    Thread.sleep(1000 * 60);
                }

            }
        }
    }
}
