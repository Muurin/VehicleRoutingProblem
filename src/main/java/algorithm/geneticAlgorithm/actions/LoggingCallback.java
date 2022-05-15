package algorithm.geneticAlgorithm.actions;

import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;
import util.FileWriterUtil;

import java.util.Objects;

@RequiredArgsConstructor
public class LoggingCallback implements CallbackAction{

    private final String resultPath;

    private final String progressPath;

    private final String filename;

    private final int logEveryNthIteration;

    private int count=0;


    @Override
    public void resultAction(Population population) {
//        FileWriterUtil.writeToNewOrExistingFile(resultPath,filename, Objects.requireNonNull(population.getIndividuals().peek()).getAlleles().toString());
        FileWriterUtil.writeToNewOrExistingFile(resultPath,filename, String.valueOf(Objects.requireNonNull(population.getIndividuals().peek()).getCostValue()));
    }

    @Override
    public void crossoverAction(Population population) {

    }

    @Override
    public void selectionAction(Population population) {

    }

    @Override
    public void mutationAction(Population population) {

    }

    @Override
    public void iterationAction(Population population) {
//        if(count%logEveryNthIteration==0) {
//            FileWriterUtil.writeToNewOrExistingFile(progressPath, filename, String.valueOf(Objects.requireNonNull(population.getIndividuals().peek()).getCostValue()));
//            count=0;
//        }
    }
}
