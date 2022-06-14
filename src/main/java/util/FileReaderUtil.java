package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtil {

    public static void processPreanalisysData(String pathToResults, String[] filenames) throws IOException {

        double[] fileSums = new double[filenames.length];

        for (int i = 0; i < filenames.length; i++) {

            try (
                    BufferedReader br = new BufferedReader((new FileReader(pathToResults + "/" + filenames[i])))) {
                String line;
                br.readLine(); //first line - column names
                while ((line = br.readLine()) != null) {

                    fileSums[i] += Double.parseDouble(line.trim());

                    System.out.println(fileSums[i]);

                }
            }

        }
    }

    public static void processAnalisysDataByCriteria(String pathToResults, String[] criterias, String[] ordinals) throws IOException {

        for (String optimizedBy : criterias) {
            System.out.println();
            for (String evaluatedBy : criterias) {
                for (String ordinal : ordinals) {
                    double sum = 0;
                    try (
                            BufferedReader br = new BufferedReader((new FileReader(pathToResults + "/" + optimizedBy + ordinal + "/" + evaluatedBy)))

                    ) {
                        String line;
                        br.readLine(); //first line - column names
                        while ((line = br.readLine()) != null) {

                            sum += Double.parseDouble(line.trim());
                        }
                    }
//                    System.out.println(String.valueOf(sum/5));
                    FileWriterUtil.writeToNewOrExistingFile(pathToResults + "/" + "COLLECTED_" + optimizedBy, evaluatedBy + ".txt", String.valueOf(sum));
                }
            }
        }
    }

    public static void processAnalisysData(String pathToResults, String filename, int numberOfRuns) throws IOException {

        for (int i = 0; i < numberOfRuns; i++) {
            double sum = 0;

            try (
                    BufferedReader br = new BufferedReader((new FileReader(pathToResults + "/" + filename + i)))

            ) {
                String line;
                br.readLine(); //first line - column names
                while ((line = br.readLine()) != null) {

                    sum += Double.parseDouble(line.trim());
                }
            }
            FileWriterUtil.writeToNewOrExistingFile(pathToResults, "COLLECTED_" + filename + ".txt", String.valueOf(sum));

        }
    }

}
