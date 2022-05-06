package algorithm.geneticAlgorithm.convergenceChecker;

import algorithm.geneticAlgorithm.model.Population;
import util.FileWriterUtil;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class LoggingAggregateConvergenceChecker extends AggregateConvergenceChecker{

    private final String loggingirectoryPath;


    public LoggingAggregateConvergenceChecker(ConvergenceChecker primaryConvergenceChecker, List<ConvergenceChecker> secondaryCheckers, String loggingDirectoryPath) {
        super(primaryConvergenceChecker, secondaryCheckers);
        this.loggingirectoryPath =loggingDirectoryPath;
    }

    @Override
    protected void groupAction(ConvergenceChecker primaryConvergenceChecker, List<ConvergenceChecker> secondaryConvergenceCheckers, Population population) {
        List<ConvergenceChecker> convergenceCheckers = new LinkedList<>(secondaryConvergenceCheckers);
        convergenceCheckers.add(primaryConvergenceChecker);

        for(ConvergenceChecker convergenceChecker : convergenceCheckers){
            try {
                FileWriterUtil.writeToNewOrExistingFile(loggingirectoryPath,
                        convergenceChecker.getName()+File.separator+"txt",
                        convergenceChecker.evaluate(population)
                        );
            }catch (OperationNotSupportedException e){

            }
        }


    }
}
