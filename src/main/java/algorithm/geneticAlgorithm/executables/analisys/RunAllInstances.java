package algorithm.geneticAlgorithm.executables.analisys;

import algorithm.geneticAlgorithm.actions.AnalisysMultipleEvaluatorsCallback;
import algorithm.geneticAlgorithm.actions.CallbackAction;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithmFactory;
import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.*;

public class RunAllInstances {

    public static void main(String[] args) throws IOException, InterruptedException {

//        String pathToInstances = instancesPath;
        String pathToInstances = ComputerPaths.pathToInstances;//instancesPath;
        String[] filenames = new File(pathToInstances).list();

        List<EvaluatorInfo> evaluatorInfos = List.of(
                EvaluatorInfo.builder().solutionEvaluator(new SimpleTimeEvaluator()).filename("TIME").build(),
                EvaluatorInfo.builder().solutionEvaluator(new SimpleDistanceEvaluator()).filename("DISTANCE").build(),
                EvaluatorInfo.builder().solutionEvaluator(new TimeWindowEvaluator()).filename("TIME_WINDOWS").build());

        for (EvaluatorInfo evaluatorInfo : evaluatorInfos) {
            for (String filename : filenames) {
                if (filename.startsWith("read")) {
                    continue;
                }

                Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);

                PermutationGAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator());
                CallbackAction callbackAction = new AnalisysMultipleEvaluatorsCallback(gaAnalisysPath + "_optimized_by_" + evaluatorInfo.getFilename(), null, gaChromosomeFactory, evaluatorInfos);

                GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                        new PopulationFactory(gaChromosomeFactory),
                        new OX(),
                        mutations[3],
                        new TournamentSelection(3, 0.6),
                        new EliminationWithElitism(0.2),
                        new TimeConvergenceChecker(10, TimeIntervalType.MINUTE),
                        callbackAction,
                        populationSizes[0]);

                List<String> resultFilenames = IntStream.range(0, 10).mapToObj(operand -> ComputerPaths.pathToResults +"/"+ evaluatorInfo.getFilename() + "," + operand).collect(Collectors.toList());

                geneticAlgorithmFactory.start(resultFilenames);

            }
        }
    }
}

