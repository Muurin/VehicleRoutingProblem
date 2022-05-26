package algorithm.geneticAlgorithm.executables.preanalisys;

import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.crossover.TSP.AggregatedEqualChanceCrossover;
import algorithm.geneticAlgorithm.operators.crossover.TSP.MOC;
import algorithm.geneticAlgorithm.operators.crossover.TSP.OX;
import algorithm.geneticAlgorithm.operators.crossover.TSP.PMX;
import algorithm.geneticAlgorithm.operators.mutation.*;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final double finalMutationChance = 0;

    public static final double[] mutationChances = {0.3, 0.5, 0.7, 1};

    public static final int[] populationSizes = {50, 100, 200, 500};

    public final static String instancesPath = "/Users/marinovcaricek/Diplomski rad/evrptw_instances/";

    public final static String populationTuningPath = "/Users/marinovcaricek/Diplomski rad/Results/preanalisys/population_tuning";

    public final static String mutationChanceTuningPath = "/Users/marinovcaricek/Diplomski rad/Results/preanalisys/mutation_chance_tuning";

    public final static String crossoverTuningPath = "/Users/marinovcaricek/Diplomski rad/Results/preanalisys/crossover_tuning";

    public final static String mutationTuningPath = "/Users/marinovcaricek/Diplomski rad/Results/preanalisys/mutation_tuning";

    public final static Crossover[] crossovers = {new OX(), new PMX(), new MOC(), new AggregatedEqualChanceCrossover(new ArrayList<>(List.of(new OX(), new PMX(), new MOC())))};

    public final static Mutation[] mutations = {
            new CyclicMutation(3, finalMutationChance),
            new PairSwitchingMutation(finalMutationChance, 3),
            new SequencePertrubationMutation(finalMutationChance),
            new AggregateEqualChanceMutation(new ArrayList<>(List.of(new CyclicMutation(3, finalMutationChance),
                    new PairSwitchingMutation(finalMutationChance, 3),
                    new SequencePertrubationMutation(finalMutationChance))))
    };

    public final static String[] randomPreanalisysInstances = {"rc107_21.txt", "c106C15.txt", "rc208_21.txt", "rc106_21.txt",
            "c101_21.txt", "c208_21.txt", "r105C15.txt", "r209C15.txt", "r112_21.txt", "rc205C10.txt", "c103C15.txt", "r102_21.txt",
            "r103C10.txt", "r208_21.txt", "r201_21.txt", "c104_21.txt", "r202_21.txt", "rc103C15.txt", "rc103_21.txt"};

}
