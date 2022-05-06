package algorithm.geneticAlgorithm.actions;

import algorithm.geneticAlgorithm.model.Population;

public interface CallbackAction {

    void resultAction(Population population);

    void crossoverAction(Population population);

    void selectionAction(Population population);

    void mutationAction(Population population);

    void iterationAction(Population population);

}
