package algorithm.geneticAlgorithm.executables.preanalisys;

import algorithm.geneticAlgorithm.actions.PreAnalisysLoggingCallback;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithmFactory;
import algorithm.geneticAlgorithm.model.PermutationGAChromosomeFactory;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
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

public class CrossoverTuning {

    private static final Integer POP_SIZE = 50;

    private static final Double MUTATION_CHANCE = 0.3;

    public static void main(String[] args) throws InterruptedException, IOException {

        String pathToInstances = Constants.instancesPath;
        String[] filenames = Constants.randomPreanalisysInstances;

        for (String filename : filenames) {
            Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);
            for (Crossover crossover : Constants.crossovers) {

                List<String> resultFilenames = IntStream.range(0, 10).mapToObj(operand -> Constants.crossoverTuningPath + "Crossover_" + crossover.getName() + "," + operand).collect(Collectors.toList());


                GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                        new PopulationFactory(new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator())),
                        crossover,
                        new CyclicMutation(5, MUTATION_CHANCE),
                        new TournamentSelection(3, 0.6),
                        new EliminationWithElitism(0.2),
                        new TimeConvergenceChecker(10, TimeIntervalType.MINUTE),
                        new PreAnalisysLoggingCallback("", ""),
                        POP_SIZE);

                geneticAlgorithmFactory.start(resultFilenames);
            }

        }

    }

}
