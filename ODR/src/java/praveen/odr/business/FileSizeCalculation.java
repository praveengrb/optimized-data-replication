package praveen.odr.business;

import java.io.File;

/**
 *
 * @author Praveen Sankarasubramanian
 */
public class FileSizeCalculation {

    public static long getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File doesn\'t exist");
            return -1;
        }
        return file.length();
    }

}
