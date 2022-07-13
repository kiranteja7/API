package Utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.Assert;

public class DataProvider {
    public static String getTestData(String propFile, String propName) {
        Properties prop = new Properties();
        FileInputStream fls;

        try {
            fls = new FileInputStream(propFile);
            prop.load(fls);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("FAIL || Invalid prop file location or key was passed");
        }
        return prop.getProperty(propName);
    }
}
