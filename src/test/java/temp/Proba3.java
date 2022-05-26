package temp;

import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
import util.FileReaderUtil;

import java.io.IOException;

public class Proba3 {


    public static void main(String[] args) throws IOException {

//        FileReaderUtil.processPreanalisysData(Constants.populationTuningPath+"/Popsize_50", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.populationTuningPath+"/Popsize_100", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.populationTuningPath+"/Popsize_200", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.populationTuningPath+"/Popsize_500", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//
//        FileReaderUtil.processPreanalisysData(Constants.mutationChanceTuningPath+"/MutationChance_1_0", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.mutationChanceTuningPath+"/MutationChance_0_7", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.mutationChanceTuningPath+"/MutationChance_0_5", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.mutationChanceTuningPath+"/MutationChance_0_3", new String[]{"0","1","2","3","4","5","6","7","8","9"});

//        FileReaderUtil.processPreanalisysData(Constants.crossoverTuningPath+"/Crossover_PMX", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.crossoverTuningPath+"/Crossover_MOC", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.crossoverTuningPath+"/Crossover_OX", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.crossoverTuningPath+"/Crossover_Aggregated crossover", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//
//        FileReaderUtil.processPreanalisysData(Constants.mutationTuningPath+"/SequencePertrubationMutation", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.mutationTuningPath+"/CyclicMutation", new String[]{"0","1","2","3","4","5","6","7","8","9"});
//        FileReaderUtil.processPreanalisysData(Constants.mutationTuningPath+"/PairSwitchingMutation", new String[]{"0","1","2","3","4","5","6","7","8","9"});
        FileReaderUtil.processPreanalisysData(Constants.mutationTuningPath+"/Aggregate mutation", new String[]{"0","1","2","3","4","5","6","7","8","9"});


    }
}
