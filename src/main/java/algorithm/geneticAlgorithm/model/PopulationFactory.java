package algorithm.geneticAlgorithm.model;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class PopulationFactory {

    private final GAChromosomeFactory gaChromosomeFactory;

    public Population initPopulation(int size) {
        Population population = new Population(size);
        PriorityQueue<GAChromosome> individuals = population.getIndividuals();

        while (individuals.size() < size) {
            individuals.add(gaChromosomeFactory.createRandomFeasibleGAChromosome());
        }

        population.setLowestInverseFitness(Objects.requireNonNull(individuals.peek()).getCostValue());

        return population;
    }

    public Population supplementPopulationWithRandomIndividuals(Population population) {

        Population newPopulation = new Population(population.getSize());
        int numberOfVehicles = population.getNumberOfVehicles();
        int size = population.getSize();

        newPopulation.setNumberOfVehicles(numberOfVehicles);
        PriorityQueue<GAChromosome> individuals = new PriorityQueue<>(population.getIndividuals());

        while (individuals.size() < size) {
            individuals.add(gaChromosomeFactory.createRandomFeasibleGAChromosome());
        }

        newPopulation.setIndividuals(individuals);
        newPopulation.setLowestInverseFitness(Objects.requireNonNull(individuals.peek()).getCostValue());

        return newPopulation;

    }

    public Population supplementPopulationWithIndividuals(Population population, Collection<GAChromosome> individuals) {

        Population newPopulation = new Population(population.getSize());
        int numberOfVehicles = population.getNumberOfVehicles();
        int size = population.getSize();

        newPopulation.setNumberOfVehicles(numberOfVehicles);

        PriorityQueue<GAChromosome> newIndividuals = new PriorityQueue<>(population.getIndividuals());

        if (newIndividuals.size() + individuals.size() > size) {
            throw new RuntimeException("Population size exceeded");
        }

        newIndividuals.addAll(individuals);

        newPopulation.setIndividuals(newIndividuals);
        newPopulation.setLowestInverseFitness(Objects.requireNonNull(newIndividuals.peek()).getCostValue());

        return newPopulation;

    }

    public GAChromosome allelsToChromosome(List<Gene> genes){
        return gaChromosomeFactory.createGAChromosome(genes);
    }


}
