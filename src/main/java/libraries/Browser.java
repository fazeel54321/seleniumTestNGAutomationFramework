package libraries;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser extends Driver{

	//To launch the browser and application	
	public static void launchApplication(String browserName, String applicationName) {
		switch(browserName.toLowerCase()) {
		case("chrome"):
			WebDriverManager.chromedriver().setup();
		    driver.set(new ChromeDriver());	
		    break;
		case("edge"):
			WebDriverManager.edgedriver().setup();
		    driver.set(new EdgeDriver());
		    break;
		case("firefox"):
			WebDriverManager.firefoxdriver().setup();
		    driver.set(new FirefoxDriver());
		    break;
		default:
			log.info(browserName+": No such browser");
		}
		if(applicationName.equalsIgnoreCase("saucedemo")) {
		application.set(applicationName.toUpperCase());
		waitTime.set(Utilities.getWaitTime(application.get()));
		log.info("Wait Time: "+waitTime.get());
	    }
		log.info(browserName+" Launched successfully");
		driver.get().manage().window().maximize();
		driver.get().get(Utilities.getUrl(application.get()));
		log.info(application.get()+" Launched successfully");	
		
	}
	
	//To close the browser
	public static void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.set(null);
        }
    }
}

