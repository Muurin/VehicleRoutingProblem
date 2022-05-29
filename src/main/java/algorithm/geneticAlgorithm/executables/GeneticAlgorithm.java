package algorithm.geneticAlgorithm.executables;

import algorithm.geneticAlgorithm.actions.CallbackAction;
import algorithm.geneticAlgorithm.convergenceChecker.ConvergenceChecker;
import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import algorithm.geneticAlgorithm.model.PopulationFactory;
import algorithm.geneticAlgorithm.operators.crossover.Crossover;
import algorithm.geneticAlgorithm.operators.elimination.Elimination;
import algorithm.geneticAlgorithm.operators.mutation.Mutation;
import algorithm.geneticAlgorithm.operators.selection.Selection;
import lombok.RequiredArgsConstructor;
import util.Pair;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GeneticAlgorithm implements Runnable {


    private final PopulationFactory populationFactory;

    private final Crossover crossover;

    private final Mutation mutation;

    private final Selection selection;

    private final Elimination elimination;

    private final ConvergenceChecker convergenceChecker;

    private final CallbackAction callbackAction;

    private final int popSize;

    @Override
    public void run() {

        //initialize population
        Population population = populationFactory.initPopulation(popSize);
        //until convergence do

        while (!convergenceChecker.isConverging(population)) {
            //selection
            List<Pair<GAChromosome>> selected = selection.select(population);
            //elimination
            population = elimination.eliminate(population);
            callbackAction.selectionAction(population);

            //crossover
            Collection<GAChromosome> offspring = new LinkedList<>();
            for (Pair<GAChromosome> parents : selected) {
                offspring.addAll(crossover.crossover(parents.getLeft().getGenes(), parents.getRight().getGenes())
                        .stream().map(populationFactory::allelsToChromosome).collect(Collectors.toList()));
                callbackAction.crossoverAction(population);
                offspring = offspring
                        .stream()
                        .map(gaChromosome -> mutation.mutate(gaChromosome.getGenes()))
                        .map(populationFactory::allelsToChromosome).collect(Collectors.toList());
                callbackAction.mutationAction(population);
            }

            population = populationFactory.supplementPopulationWithIndividuals(population, offspring);
            //mutation


            //generate more individuals if lacking individuals
            if (!population.isFull()) {
                population = populationFactory.supplementPopulationWithRandomIndividuals(population);
            }
            callbackAction.iterationAction(population);
        }
        //Extract result
        callbackAction.resultAction(population);
    }

}

