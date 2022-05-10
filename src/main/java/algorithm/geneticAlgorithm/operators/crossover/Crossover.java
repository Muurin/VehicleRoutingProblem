package algorithm.geneticAlgorithm.operators.crossover;

import algorithm.geneticAlgorithm.model.Allele;
import algorithm.geneticAlgorithm.model.GAChromosome;

import java.util.Collection;
import java.util.List;

public interface Crossover {

    Collection<List<Allele>> crossover(List<Allele> gaChromosome1, List<Allele> gaChromosome2);
}
