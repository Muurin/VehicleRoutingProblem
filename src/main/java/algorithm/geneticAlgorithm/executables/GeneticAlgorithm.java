package algorithm.geneticAlgorithm.executables;

import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.elimination.Elimination;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import algorithm.geneticAlgorithm.resultProcessor.CallbackAction;
import lombok.RequiredArgsConstructor;
import util.Pair;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class GeneticAlgorithm implements Runnable{


    private final PopulationFactory populationFactory;

    private final Crossover crossover;

    private final Mutation mutation;

    private final Selection selection;

    private final Elimination elimination;

    private final ConvergenceChecker convergenceChecker;

    private final CallbackAction callbackAction;

    int popSize;

    int numberOfVehicles;

    @Override
    public void run() {

        //initialize population
        Population population = populationFactory.initPopulation(popSize, numberOfVehicles);
        //until convergence do

        while (!convergenceChecker.isConverging(population)) {
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
        callbackAction.resultAction(population);
    }

}

