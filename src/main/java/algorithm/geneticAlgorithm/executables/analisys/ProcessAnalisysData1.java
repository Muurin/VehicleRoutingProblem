package algorithm.geneticAlgorithm.executables.analisys;

import util.ComputerPaths;
import util.FileReaderUtil;

import java.io.IOException;

import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.gaAnalisysPath;

public class ProcessAnalisysData1 {

    public static final String STRING = gaAnalisysPath;
    private static String[] criteria = {"DISTANCE","TIME", "TIME_WINDOWS"};

    private static String[] ordinal = {"0","1","2", "3", "4","5","6","7","8","9"};
    public static void main(String[] args) throws IOException {

//      FileReaderUtil.processAnalisysDataByCriteria(STRING,criteria,ordinal);
        FileReaderUtil.processAnalisysDataByCriteria(ComputerPaths.pathToResults,criteria,ordinal);
    }
}
