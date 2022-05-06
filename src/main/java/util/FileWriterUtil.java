package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;

public class FileWriterUtil {

    public static String createDirectory(String path){
        File dir=new File(path);
        if(!dir.exists()) {
            dir.mkdir();
        }
        return dir.getAbsolutePath();
    }

    public static void writeToNewOrExistingFile(String directoryPath, String filename, String content){
        createDirectory(directoryPath);

        try {
            Files.writeString(
                    Path.of(directoryPath,filename),
                    content + System.lineSeparator(),
                    CREATE, APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
