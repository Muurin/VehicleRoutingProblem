package temp;

import algorithm.geneticAlgorithm.executables.preanalisys.Constants;

public class Proba4 {

    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        for(String filename: Constants.randomPreanalisysInstances){
            stringBuilder.append(filename.replace(".txt",", "));

        }
        System.out.println(stringBuilder.toString());
    }
}
