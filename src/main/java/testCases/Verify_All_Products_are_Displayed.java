package testCases;

import java.util.Map;

import libraries.Assertion;
import libraries.Driver;
import libraries.Results;
import libraries.SauceDemoCommonMethods;
import pageObjects.sauceDemo.Products;

public class Verify_All_Products_are_Displayed extends Driver{
public void Verify_All_Products_are_Displayed(Map<String, String> testData) {
	try {
		SauceDemoCommonMethods.login(testData.get("browser"), testData.get("username"), testData.get("password"));
		boolean isDetailsVisible = Products.checkInventoriesDetails();
		Assertion.assertTrue(isDetailsVisible, "Inventory Details Visible");
	} catch(Exception e) {
		executionContinue.set(false);
		log.error(Results.getStackMsg(e));
		
	}
}

}
