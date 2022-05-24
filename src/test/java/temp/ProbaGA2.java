package temp;

import algorithm.geneticAlgorithm.actions.TestingCallback;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithm;
import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
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

import java.io.File;
import java.io.IOException;

public class ProbaGA2 {

    public static void main(String[] args) throws IOException, InterruptedException {

        String pathToInstances = Constants.instancesPath;
        String[] filenames = new File(pathToInstances).list();//Constants.randomPreanalisysInstances;

//        List<String> resultFilenames = IntStream.range(0,10).mapToObj(operand -> Constants.populationTuningPath+","+operand).collect(Collectors.toList());

        for (String filename : filenames) {
            System.out.println("INSTANCA -" + filename);
            Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + filename);
//            for (Integer popSize : Constants.populationSizes) {
//            GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
//                    new PopulationFactory(new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator())),
//                    new OX(),
//                    new CyclicMutation(5, 0.3),
//                    new TournamentSelection(3, 0.6),
//                    new EliminationWithElitism(0.2),
//                    new TimeConvergenceChecker(1, TimeIntervalType.MINUTE),
//                    //new PreAnalisysLoggingCallback("",""),
//                    new TestingCallback(),
//                    100);

            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm( new PopulationFactory(new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator())),
                    new OX(),
                    new CyclicMutation(5, 0.3),
                    new TournamentSelection(3, 0.6),
                    new EliminationWithElitism(0.2),
                    new TimeConvergenceChecker(1, TimeIntervalType.MINUTE),
                    //new PreAnalisysLoggingCallback("",""),
                    new TestingCallback(),
                    100);
            try {
               geneticAlgorithm.run();
            } catch (RuntimeException e) {
            }
            System.out.println("Zavrsio "+ filename);
        }

    }
}
