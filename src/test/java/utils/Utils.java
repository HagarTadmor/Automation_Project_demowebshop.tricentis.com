package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
	public static String readValue(String key) {
	    String value = "";
	    try (InputStream input = new FileInputStream("./src/test/resources/data/configuration.properties")) {
	        Properties prop = new Properties();

	        // load a properties file
	        prop.load(input);

	        // get the property value
	        value = prop.getProperty(key);

	    } catch (IOException e) {
	        // Log the exception (you can replace this with a logging framework)
	        System.err.println("Error reading properties file: " + e.getMessage());
	    }
	    return value;
	}
}
