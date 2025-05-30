
package libraries;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import libraries.Driver;

public class Utilities extends Driver{
	
//To get the URL of the application from the config file
public static String getUrl(String applicationName){
		return getProperty(applicationName+"_URL");
}

//To get the Wait Time of the application from the config file
public static int getWaitTime(String applicationName) {
	return Integer.valueOf(getProperty(applicationName+"_WAITTIME"));
}

//To get the value of a property from the config file
public static String getProperty(String key){
	Properties prop = new Properties();
	try {
		FileInputStream	file = new FileInputStream("./Config/config.properties");
			prop.load(file);
		
	} catch (IOException e) {
		log.error(e.getCause().getMessage());
	}
	String value = prop.getProperty(key.toUpperCase());
	log.info(key+" : Retrieved value: "+value);
	
	return value;
}

}
