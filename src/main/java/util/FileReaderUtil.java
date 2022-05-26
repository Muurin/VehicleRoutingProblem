package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtil {

    public static void processPreanalisysData(String pathToResults, String[] filenames) throws IOException {

        double[] fileSums = new double[filenames.length];

        for(int i=0;i<filenames.length;i++){

            boolean doneWithLocationProcessing = false;

            try (
                    BufferedReader br = new BufferedReader((new FileReader(pathToResults+"/"+filenames[i])))) {
                String line;
                br.readLine(); //first line - column names
                while ((line = br.readLine()) != null) {

                    fileSums[i] += Double.parseDouble(line.trim());

                    if (line.trim().isEmpty()) {
                        doneWithLocationProcessing = true;
                    }}

                System.out.println(fileSums[i]);

            }
        }


}



}
