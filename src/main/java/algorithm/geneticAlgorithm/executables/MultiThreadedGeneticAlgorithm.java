package algorithm.geneticAlgorithm.executables;

import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.elimination.Elimination;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import algorithm.geneticAlgorithm.resultProcessor.ResultProcessor;
import lombok.RequiredArgsConstructor;
import util.Pair;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class MultiThreadedGeneticAlgorithm {

    private final PopulationFactory populationFactory;

    private final Crossover crossover;

    private final Mutation mutation;

    private final Selection selection;

    private final Elimination elimination;

    private final ThreadLocal<ConvergenceChecker> convergenceChecker;

    private final ResultProcessor resultProcessor;

    public void start(int nthread, int popSize, int numberOfVehicles) {

        for (int i = 0; i < nthread; i++) {

            //start thread

            //initialize population
            Population population = populationFactory.initPopulation(popSize, numberOfVehicles);
            //until convergence do

            while (!convergenceChecker.get().isConverging(population)) {
                //selection
                List<Pair<GAChromosome>> selected = selection.select(population);
                //elimination
                population = elimination.eliminate(population);

                //crossover
                List<GAChromosome> offspring = new LinkedList<>();
                for (Pair<GAChromosome> parents : selected) {

                    offspring.addAll(crossover.crossover(parents.getLeft(), parents.getRight()));
                }

                population = populationFactory.supplementPopulationWithIndividuals(population, offspring);
                //mutation
                population = mutation.mutate(population);

                //generate more individuals if lacking individuals
                if (!population.isFull()) {
                    population = populationFactory.supplementPopulationWithRandomIndividuals(population);
                }

            }
            //Extract result
            resultProcessor.process(population);
        }

    }
}




