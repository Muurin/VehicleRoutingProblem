package algorithm.geneticAlgorithm.executables.preanalisys;

import algorithm.geneticAlgorithm.actions.ResultLoggingCallback;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithmFactory;
import algorithm.geneticAlgorithm.model.PermutationGAChromosomeFactory;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.crossover.TSP.OX;
import algorithm.geneticAlgorithm.operators.elimination.EliminationWithElitism;
import algorithm.geneticAlgorithm.operators.mutation.CyclicMutation;
import algorithm.geneticAlgorithm.operators.selection.TournamentSelection;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instances.Instance;
import solution.SolutionContextFactory;
import solution.evaluators.SimpleDistanceEvaluator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MutationChanceTuning {

    private static final Integer POP_SIZE = 50;

    public static void main(String[] args) throws IOException, InterruptedException {

        String pathToInstances = Constants.instancesPath;
        String[] filenames = Constants.randomPreanalisysInstances;

        for (String filename : filenames) {
            Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);
            for (Double mutationChance : Constants.mutationChances) {

                List<String> resultFilenames = IntStream.range(0, 10).mapToObj(operand -> Constants.mutationChanceTuningPath + "/MutationChance_" + mutationChance.toString().replace(".","_") + "," + operand).collect(Collectors.toList());

                GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                        new PopulationFactory(new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator())),
                        new OX(),
                        new CyclicMutation(3, mutationChance),
                        new TournamentSelection(3, 0.6),
                        new EliminationWithElitism(0.2),
                        new TimeConvergenceChecker(10, TimeIntervalType.MINUTE),
                        new ResultLoggingCallback("", ""),
                        POP_SIZE);

                geneticAlgorithmFactory.start(resultFilenames);
            }

        }
    }
}
