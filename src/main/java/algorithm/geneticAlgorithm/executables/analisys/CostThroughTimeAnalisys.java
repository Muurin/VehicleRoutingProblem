package algorithm.geneticAlgorithm.executables.analisys;

import algorithm.geneticAlgorithm.actions.IterationsThroughTimeLoggincCallback;
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
import solution.evaluators.TimeWindowEvaluator;

import java.io.IOException;

import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.*;

public class CostThroughTimeAnalisys {

    public static void main(String[] args) throws IOException, InterruptedException {

        for(String file: timeAnalisysInstances){

            Instance instance = new EVRP_CTWInstanceLoader().load(instancesPath + file);

            PermutationGAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(instance), new TimeWindowEvaluator());

            GeneticAlgorithmFactory geneticAlgorithmFactory = new GeneticAlgorithmFactory(
                    new PopulationFactory(gaChromosomeFactory),
                    new OX(),
                    mutations[3],
                    new TournamentSelection(3, 0.6),
                    new EliminationWithElitism(0.2),
                    new TimeConvergenceChecker(10, TimeIntervalType.MINUTE),
                    new IterationsThroughTimeLoggincCallback(costThroughTimeAnalisysPath,file,200L,10L,TimeIntervalType.MINUTE),
                    populationSizes[0]);

            geneticAlgorithmFactory.start(1);
        }
    }
}
