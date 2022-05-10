package temp;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.PermutationGAChromosomeFactory;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.crossover.TSP.MOC;
import algorithm.geneticAlgorithm.operators.crossover.TSP.PMX;
import algorithm.geneticAlgorithm.operators.mutation.CyclicMutation;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.mutation.PairSwitchingMutation;
import algorithm.geneticAlgorithm.operators.mutation.SequencePertrubationMutation;
import algorithm.solution.SolutionContextFactory;
import algorithm.solution.evaluators.SimpleDistanceEvaluator;
import testUtil.ObjectInitializers;

import java.util.Collection;

public class Proba {  private static final String DIR = "/Users/marinovcaricek/Diplomski rad/Results";

    public static void main(String[] args) {

//        FileWriterUtil.writeToNewOrExistingFile(DIR,"proba.txt", "nsgbuabgou;bg;ag;ahgiah");
//        FileWriterUtil.writeToNewOrExistingFile(DIR,"proba.txt", "nsSADA BIH LMAO giah");

        PermutationGAChromosomeFactory gaChromosomeFactory = new PermutationGAChromosomeFactory(new SolutionContextFactory(ObjectInitializers.givenInstanceExample1()), new SimpleDistanceEvaluator());
        Crossover crossover = new MOC();
                //new PMX();
                //new OX();

        Mutation mutation = new CyclicMutation(3,1.0);
        Mutation mutation1 =  new SequencePertrubationMutation(1.0);
        Mutation mutation2 = new PairSwitchingMutation(1.0, 1);

        GAChromosome gaChromosome1 = gaChromosomeFactory.createRandomFeasibleGAChromosome();
        GAChromosome gaChromosome2 = gaChromosomeFactory.createRandomFeasibleGAChromosome();

        System.out.println(gaChromosome1);
        System.out.println(gaChromosome2);

//        System.out.println(mutation.mutate(gaChromosome1.getAlleles()));

//        System.out.println(mutation1.mutate(gaChromosome2.getAlleles()));

        System.out.println(mutation2.mutate(gaChromosome2.getAlleles()));



//        for(Collection<Allele> child : crossover.crossover(gaChromosome1.getAlleles(),gaChromosome2.getAlleles())){
//            System.out.println(child);
//        }

    }

}
