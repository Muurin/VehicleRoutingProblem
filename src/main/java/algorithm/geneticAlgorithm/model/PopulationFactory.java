package algorithm.geneticAlgorithm.model;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

@RequiredArgsConstructor
public class PopulationFactory {

    private final GAChromosomeFactory gaChromosomeFactory;

    public Population initPopulation(int size, int numberOfVehicles) {
        Population population = new Population();
        TreeSet<GAChromosome> individuals = population.getIndividuals();

        while (individuals.size() < size) {
            individuals.add(gaChromosomeFactory.createRandomFeasibleGAChromosome(numberOfVehicles));
        }
        population.setSize(size);

        population.setLowestInverseFitness(Objects.requireNonNull(individuals.pollFirst()).getInverseFitness());

        return population;
    }

    public Population supplementPopulationWithRandomIndividuals(Population population) {

        Population newPopulation = new Population();
        int numberOfVehicles = population.getNumberOfVehicles();
        int size = population.getSize();

        newPopulation.setNumberOfVehicles(numberOfVehicles);
        TreeSet<GAChromosome> individuals = new TreeSet<>(population.getIndividuals());

        while (individuals.size() < size) {
            individuals.add(gaChromosomeFactory.createRandomFeasibleGAChromosome(numberOfVehicles));
        }

        newPopulation.setIndividuals(individuals);
        newPopulation.setSize(size);
        newPopulation.setLowestInverseFitness(Objects.requireNonNull(individuals.pollFirst()).getInverseFitness());

        return newPopulation;

    }

    public Population supplementPopulationWithIndividuals(Population population, Collection<GAChromosome> individuals) {

        Population newPopulation = new Population();
        int numberOfVehicles = population.getNumberOfVehicles();
        int size = population.getSize();

        newPopulation.setNumberOfVehicles(numberOfVehicles);

        TreeSet<GAChromosome> newIndividuals = new TreeSet<>(population.getIndividuals());

        if (newIndividuals.size() + individuals.size() > size) {
            throw new RuntimeException("Population size exceeded");
        }

        newIndividuals.addAll(individuals);

        newPopulation.setIndividuals(newIndividuals);
        newPopulation.setSize(size);
        newPopulation.setLowestInverseFitness(Objects.requireNonNull(newIndividuals.pollFirst()).getInverseFitness());

        return newPopulation;

    }


}
