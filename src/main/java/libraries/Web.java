/**
 * 
 */
package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author 80092513
 *
 */
public class Web extends Driver{
	
	public static void click(By element) {
		waitUntilClickable(element).click();
		log.info(element+" : Clicked successfully");
	}
	public static String getText(By element) {
		String text = waitUntilVisible(element).getText();
		log.info(text+" : text retrived: "+text);
		return text;
	}
	public static String getText(WebElement element) {
		String text = waitUntilVisible(element).getText();
		log.info(text+" : text retrived: "+text);
		return text;
	}
	
	public static void type(By element, String text) {
	WebElement element1 = 	waitUntilClickable(element);
	//element1.clear();
	if(text!=null){
	element1.sendKeys(text);
	}
	element1.sendKeys(Keys.TAB);
	log.info(element+" : typed successfully - "+text);
	}
	
	public static WebElement findElement(By element) {
		log.info(element+" : Finding");
		WebElement found = driver.get().findElement(element);
		log.info(element+" : Found successfully");		
		return found;
	}
	
	public static List findElements(By element) {
		log.info(element+" : Finding");
		List<WebElement> elements =  driver.get().findElements(element);
		log.info(element+" : Found successfully");
		return elements;
	}
	
	public static void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public static WebElement waitUntilClickable(By element) {
		log.info(element+" : waiting to be clickable");
		WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(waitTime.get()));
		WebElement waitedElement =  wait.until(ExpectedConditions.elementToBeClickable(element));
		scrollToElement(waitedElement);
		log.info(element+" : is clickable");			
		return waitedElement;
	}
	public static WebElement waitUntilClickable(WebElement element) {
		log.info("waiting for element to be clickable");
		WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(waitTime.get()));
		WebElement waitedElement =  wait.until(ExpectedConditions.elementToBeClickable(element));
		scrollToElement(waitedElement);
		log.info("element is clickable");			
		return waitedElement;
	}
	public static WebElement waitUntilVisible(By element) {
		log.info(element+" : waiting to be visible");
		WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(waitTime.get()));
		WebElement waitedElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		log.info(element+" : is visible");			
		return waitedElement;
	}
	public static WebElement waitUntilVisible(WebElement element) {
		log.info("waiting for element to be visible");
		WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(waitTime.get()));
		WebElement waitedElement =  wait.until(ExpectedConditions.visibilityOf(element));
		scrollToElement(waitedElement);
		log.info("element is visible");			
		return waitedElement;
	}
	public static Boolean waitUntilInvisible(By element) {
		log.info(element+" : waiting to be invisible");
		WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(waitTime.get()));
		Boolean waitedElement =  wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
		log.info(element+" : is invisible");			
		return waitedElement;
	}
	
	public static void waitTillPageLoads() {
		log.info("Waiting for page to load");
		driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(waitTime.get()));
		log.info("Page loaded successfully");
	}
	
	public static boolean isAvailable(By element) throws InterruptedException {
		boolean isAvailable = false;
		for(int i=0; i<waitTime.get(); i++) {
		List<WebElement> elements = findElements(element);
		if(elements.size()>0) {
			isAvailable = true;
			log.info(element+" : is available");
			break;
		} else {
			Thread.sleep(1000);
		}
		}
		if(!isAvailable) {
			log.info(element+" : is not available");
		}
		
		return isAvailable;
	}
}
