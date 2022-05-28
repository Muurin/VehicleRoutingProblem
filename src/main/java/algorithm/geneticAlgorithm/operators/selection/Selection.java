package algorithm.geneticAlgorithm.operators.selection;

import algorithm.geneticAlgorithm.model.GAChromosome;
import algorithm.geneticAlgorithm.model.Population;
import util.Pair;

import java.util.List;

public interface Selection {

    List<Pair<GAChromosome>> select(Population population);
}
