package algorithm.geneticAlgorithm.actions;

import algorithm.geneticAlgorithm.model.Population;

public class TestingCallback implements CallbackAction {


    @Override
    public void resultAction(Population population) {
        System.out.println("Krajnji rezultat " + population.getIndividuals().peek());
    }

    @Override
    public void crossoverAction(Population population) {
//        System.out.println("Nakon crossovera " + population.getIndividuals().peek());
    }

    @Override
    public void selectionAction(Population population) {
//        System.out.println("Nakon selekcije "+population.getIndividuals().peek());
    }

    @Override
    public void mutationAction(Population population) {
//        System.out.println("Nakon mutacije " + population.getIndividuals().peek());
    }

    @Override
    public void iterationAction(Population population) {
//        System.out.println("Nakon iteracije " + population.getIndividuals().peek());
    }

    @Override
    public CallbackAction cloneWithDifferentConfiguration(String configuration) {
        return null;
    }
}
