package temp;

import algorithm.geneticAlgorithm.actions.CallbackAction;
import algorithm.geneticAlgorithm.actions.PreAnalisysLoggingCallback;
import algorithm.geneticAlgorithm.actions.TestingCallback;
import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeConvergenceChecker;
import algorithm.geneticAlgorithm.convergenceChecker.TimeIntervalType;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithm;
import algorithm.geneticAlgorithm.executables.GeneticAlgorithmFactory;
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

import java.io.IOException;

public class ProbaGA {

    private static final String tempPath= "/Users/marinovcaricek/Diplomski rad/Results/temp";

    public static void main(String[] args) throws IOException, JDOMException, InterruptedException {

        String instancePath = VRPInstancePaths.getEVRP_CTWPath2();

        InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
        Instance instance = instanceLoader.load(instancePath);

        GAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new SimpleDistanceEvaluator());

        PopulationFactory populationFactory = new PopulationFactory(gaChromosomeFactory);

        Crossover crossover = new OX();
        Mutation mutation = new CyclicMutation(2, 0.5);
        Elimination elimination = new EliminationWithElitism(0.5);
        Selection selection = new TournamentSelection(3, 0.3);
        ConvergenceChecker convergenceChecker = new TimeConvergenceChecker(1, TimeIntervalType.MINUTE);
                //new IterationConvergenceChecker(100);
       CallbackAction callbackAction = //new PreAnalisysLoggingCallback(tempPath+"/result",tempPath+"/progress","OX_Cyclic_c101_21.txt",100);
                new TestingCallback();


//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(
//                populationFactory,
//                crossover,
//                mutation,
//                selection,
//                elimination,
//                convergenceChecker,
//                callbackAction,
//                    100);
//
//        geneticAlgorithm.run();

        GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                populationFactory,
                crossover,
                mutation,
                selection,
                elimination,convergenceChecker,callbackAction,200);

        geneticAlgorithmFactory.start(1);
    }
}
