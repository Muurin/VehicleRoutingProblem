package algorithm.geneticAlgorithm.actions;

import algorithm.geneticAlgorithm.model.Population;
import lombok.RequiredArgsConstructor;
import util.FileWriterUtil;

import java.util.Objects;

@RequiredArgsConstructor
public class ResultLoggingCallback implements CallbackAction{

    protected final String resultPath;

    private final String filename;

    @Override
    public void resultAction(Population population) {
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
    }

    /**
     * Configuration are new parameters for PreAnalisysCallbackFunction divided by comma
     * @param configuration
     * @return
     */
    @Override
    public CallbackAction cloneWithDifferentConfiguration(String configuration) {
        String[] params = configuration.split(",");
        return new ResultLoggingCallback(params[0],params[1]);
    }

}
