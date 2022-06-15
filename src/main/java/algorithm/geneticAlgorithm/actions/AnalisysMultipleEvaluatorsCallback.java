package algorithm.geneticAlgorithm.actions;

import algorithm.geneticAlgorithm.executables.analisys.EvaluatorInfo;
import algorithm.geneticAlgorithm.model.GAChromosomeFactory;
import algorithm.geneticAlgorithm.model.Population;
import solution.SolutionContext;
import util.FileWriterUtil;

import java.util.List;

public class AnalisysMultipleEvaluatorsCallback extends ResultLoggingCallback {

    private final GAChromosomeFactory gaChromosomeFactory;

    private final List<EvaluatorInfo> evaluatorInfos;

    public AnalisysMultipleEvaluatorsCallback(String resultPath, String filename, GAChromosomeFactory gaChromosomeFactory, List<EvaluatorInfo> evaluatorInfos) {
        super(resultPath, filename);
        this.gaChromosomeFactory = gaChromosomeFactory;
        this.evaluatorInfos = evaluatorInfos;
    }

    @Override
    public void resultAction(Population population) {
        for(EvaluatorInfo evaluatorInfo:evaluatorInfos){
            SolutionContext solution = gaChromosomeFactory.extendSolutionWithChargingStations(population.getIndividuals().peek().getGenes());
            FileWriterUtil.writeToNewOrExistingFile(resultPath,evaluatorInfo.getFilename(),
                    String.valueOf(evaluatorInfo.getSolutionEvaluator().evaluate(solution)));
            solution.reset();
        }


    }
    @Override
    public void iterationAction(Population population) {

    }

    @Override
    public CallbackAction cloneWithDifferentConfiguration(String configuration) {
        String[] params = configuration.split(",");
        return new AnalisysMultipleEvaluatorsCallback(params[0]+params[1],null,gaChromosomeFactory,evaluatorInfos);
    }
}
