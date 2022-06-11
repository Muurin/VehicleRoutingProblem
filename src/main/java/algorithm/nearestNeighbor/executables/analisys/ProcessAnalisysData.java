package algorithm.nearestNeighbor.executables.analisys;

import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
import util.FileReaderUtil;

import java.io.IOException;

public class ProcessAnalisysData {

    private static String[] criteria = {"DISTANCE","TIME", "TIME_WINDOWS"};

    public static void main(String[] args) throws IOException {
        for(String filename:criteria){
                FileReaderUtil.processAnalisysData(Constants.gaAnalisysPath+"/NN",filename,10);
        }
    }
}
