package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropFileReader {
    public static Properties getConfigData() throws IOException
    {
        FileInputStream src= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\testData.config");
        Properties prop = new Properties();
        prop.load(src);
        return prop;

    }
}
