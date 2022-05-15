package temp;

import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.PermutationGAChromosomeFactory;
import algorithm.geneticAlgorithm.model.Population;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.crossover.TSP.MOC;
import algorithm.geneticAlgorithm.operators.mutation.CyclicMutation;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.mutation.PairSwitchingMutation;
import algorithm.geneticAlgorithm.operators.mutation.SequencePertrubationMutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import algorithm.geneticAlgorithm.operators.selection.TournamentSelection;
import solution.SolutionContextFactory;
import solution.evaluators.SimpleDistanceEvaluator;
import testUtil.ObjectInitializers;

import java.util.List;
import java.util.PriorityQueue;

public class Proba {
    private static final String DIR = "/Users/marinovcaricek/Diplomski rad/Results";

    public static void main(String[] args) {

        //        FileWriterUtil.writeToNewOrExistingFile(DIR,"proba.txt", "nsgbuabgou;bg;ag;ahgiah");
        //        FileWriterUtil.writeToNewOrExistingFile(DIR,"proba.txt", "nsSADA BIH LMAO giah");

        PermutationGAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(ObjectInitializers.givenInstanceExample1()), new SimpleDistanceEvaluator());
        Crossover crossover = new MOC();
        //new PMX();
        //new OX();

        Mutation mutation = new CyclicMutation(3, 1.0);
        Mutation mutation1 = new SequencePertrubationMutation(1.0);
        Mutation mutation2 = new PairSwitchingMutation(1.0, 1);

        GAChromosome gaChromosome1 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome2 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome3 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome4 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome5 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome6 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome7 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome8 = gaChromosomeFactory.createRandomFeasibleGAChromosome();

        Population population = new Population(8);
        population.setIndividuals(new PriorityQueue<>(List.of(gaChromosome1, gaChromosome2, gaChromosome3, gaChromosome4, gaChromosome5, gaChromosome6, gaChromosome7, gaChromosome8)));

        System.out.println(gaChromosome1);
        System.out.println(gaChromosome2);
        System.out.println(gaChromosome3);
        System.out.println(gaChromosome4);
        System.out.println(gaChromosome5);
        System.out.println(gaChromosome6);
        System.out.println(gaChromosome7);
        System.out.println(gaChromosome8);
        //        System.out.println(population.getIndividuals());

        //        Elimination elimination = new RandomEliminationWithElitism(2,6);
        //new EliminateNWeakestIndividuals(2);
        //        population = elimination.eliminate(population);
        Selection selection = new TournamentSelection(3, 5);
        System.out.println(selection.select(population));


        System.out.println(population.getIndividuals());

        //        System.out.println(mutation.mutate(gaChromosome1.getAlleles()));

        //        System.out.println(mutation1.mutate(gaChromosome2.getAlleles()));

        //        System.out.println(mutation2.mutate(gaChromosome2.getAlleles()));


        //        for(Collection<Allele> child : crossover.crossover(gaChromosome1.getAlleles(),gaChromosome2.getAlleles())){
        //            System.out.println(child);
        //        }

       System.out.println("MOJ ID JE" + Thread.currentThread().getName());

    }

}
