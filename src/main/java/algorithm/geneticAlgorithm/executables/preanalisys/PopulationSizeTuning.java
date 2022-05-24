package algorithm.geneticAlgorithm.executables.preanalisys;

import algorithm.geneticAlgorithm.actions.PreAnalisysLoggingCallback;
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

public class PopulationSizeTuning {

    private static final String populationTuningPath = "/Users/marinovcaricek/Diplomski rad/Results/preanalisys/population_tuning";

    //TODO make operators that work with % of population, determine instanecs for preanalisys
    public static void main(String[] args) throws IOException, InterruptedException {

        String pathToInstances = Constants.instancesPath;
        String[] filenames = Constants.randomPreanalisysInstances;

        List<String> resultFilenames = IntStream.range(0,10).mapToObj(operand -> Constants.populationTuningPath+","+operand).collect(Collectors.toList());

        for (String filename : filenames) {
            Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);
            for (Integer popSize : Constants.populationSizes) {
                    GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                            new PopulationFactory(new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator())),
                            new OX(),
                            new CyclicMutation(5, 0.3),
                            new TournamentSelection(3, 0.6),
                            new EliminationWithElitism(0.2),
                            new TimeConvergenceChecker(10, TimeIntervalType.MINUTE),
                            new PreAnalisysLoggingCallback("",""),
                            popSize);

                    geneticAlgorithmFactory.start(resultFilenames);
            }

        }
    }

}
