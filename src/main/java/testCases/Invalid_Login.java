package testCases;

import java.util.Map;

import org.testng.Assert;

import libraries.Assertion;
import libraries.Browser;
import libraries.Driver;
import libraries.Results;
import pageObjects.sauceDemo.Products;
import pageObjects.sauceDemo.Login;

public class Invalid_Login extends Driver{
	public void Invalid_Login(Map<String, String> testData) {
		try {
			Browser.launchApplication(testData.get("browser"), "SauceDemo");
			Login.login(testData.get("username"), testData.get("password"));
			String errorMessage = Login.getErrorMessage();
			Assertion.assertContains(errorMessage, testData.get("expectedErrorMessage"), "Error Message");		
		}catch(Exception e) {
			executionContinue.set(false);
			log.error(Results.getStackMsg(e));
			
		}
	}

}
