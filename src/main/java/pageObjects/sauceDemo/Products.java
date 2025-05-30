package pageObjects.sauceDemo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import libraries.Driver;
import libraries.Web;

public class Products extends Driver{
	private static By products = By.xpath("//*[text()='Products']");
	private static By title = By.xpath("//*[@class='login_logo' or @class='title']");
	private static By inventoryItems = By.className("inventory_item");
    
	public static String getPageTitle() throws InterruptedException {
		Web.waitTillPageLoads();
		String pageTitle = Web.getText(title);
		return pageTitle;
	}
	public static String getTitle() {
		return driver.get().getTitle();
	}
	
	public static List listOfInventoryItems() {
		List<WebElement> list = Web.findElements(inventoryItems);
		int countOfInventories = list.size();
        log.info("No. of Inventories: "+countOfInventories);
		return 	list;
	}
	public static boolean checkInventoryDetails(WebElement element) {
	boolean isDetailsAreVisible = true;
	String inventoryName = Web.getText(element.findElement(By.className("inventory_item_name")));
	if(!inventoryName.matches("[a-zA-Z\\- ]+")) {
		log.error("Doesn't have inventory name");
		return false;
	}
	String inventoryPrice = Web.getText(element.findElement(By.className("inventory_item_price")));
	if(!(inventoryPrice.matches("^\\$\\d+(\\.\\d{2})?$+")&&inventoryPrice.contains("$"))) {
		log.error("Doesn't have inventory price");
		return false;
	}
	if(!Web.waitUntilVisible(element.findElement(By.xpath("//img[@class='inventory_item_img']"))).isDisplayed()) {
		log.error("Doesn't have inventory image");
		return false;
	}
	if(!Web.waitUntilClickable(element.findElement(By.xpath("//img[@class='inventory_item_img']"))).isEnabled()) {
		log.error("Add to cart button not enabled or not available");
		return false;
	}
	
	
	return isDetailsAreVisible;
	}
	
	public static boolean checkInventoriesDetails() {
	List<WebElement> list = listOfInventoryItems();
    for(int i=0; i<list.size(); i++) {
    	if(!checkInventoryDetails(list.get(i))) {
    		return false;
    	}
    	
    }
    return true;
	}
	
	
}
