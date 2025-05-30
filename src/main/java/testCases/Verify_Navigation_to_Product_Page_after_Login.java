package testCases;

import java.util.Map;

import libraries.Assertion;
import libraries.Driver;
import libraries.Results;
import libraries.SauceDemoCommonMethods;
import pageObjects.sauceDemo.Products;

public class Verify_Navigation_to_Product_Page_after_Login extends Driver {
	public void Verify_Navigation_to_Product_Page_after_Login(Map<String, String> testData) {
	try {
		SauceDemoCommonMethods.login(testData.get("browser"), testData.get("username"), testData.get("password"));
		Assertion.assertEquals(Products.getTitle(), testData.get("expectedTitle"), "Title");
	}catch(Exception e) {
		executionContinue.set(false);
		log.error(Results.getStackMsg(e));
		
	}
	}

}
