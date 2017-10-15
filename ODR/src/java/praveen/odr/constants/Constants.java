/**
 *
 */
package praveen.odr.constants;

/**
 * @author PSAN
 *
 */
public class Constants {

    public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    public static final String DATABASE_URL = "jdbc:mysql://praveenodr.cp1flztlep3v.ap-south-1.rds.amazonaws.com:3306/odr";

    public static final String DATABASE_USERNAME = "praveen";

    public static final String DATABASE_PASSWORD = "password-1";

    public static final String KEY_FILE_LOCATION = "F:/Project/ODR/Project/ODR/Key/";

    public static final String FRAGMENT_FILE_LOCATION = "F://Project/MyFile.txt";

    public static final String FILE_BKP_LOCATION = "./";
    
    public static final String SUCCESS="Success";
    
    public static final String TXT_FILE_EXTENSION=".txt";
    
   //private static final String FILENAME_KEY="<FILENAME>";
    
    public static final String DOWNLOAD_FILE_LOCATION="F:/Project/ODR/Project/ODR/web/Download/%s"+TXT_FILE_EXTENSION;
    
    public static final String LOCATION="<LOC>";
    
    public static final String PART="<PART>";
    public static final String FRAGMENT_SERVER_PATH="F:/Project/ODR/Project/ODR/web/Server/";
    public static final String FRAGMENT_SERVER_LOCATION=FRAGMENT_SERVER_PATH+LOCATION+"/"+PART;
    
}
