package algorithm.geneticAlgorithm.operators.crossover;

import algorithm.geneticAlgorithm.model.GAChromosome;

import java.util.Collection;

public interface Crossover {

    Collection<GAChromosome> crossover(GAChromosome gaChromosome1, GAChromosome gaChromosome2);

    //TODO come up with a crossover method that is as cheap as possible but assures feasibility
    //feasibility - capacity of vehicles
    //crossover  may change the order and vehicles alike
    //sortirati po customerima i raditi uniform crossover, paziti na crossover, ako bi neka promjena napravila narusila feasiblity, odustati ako treba forsati mutation
}
