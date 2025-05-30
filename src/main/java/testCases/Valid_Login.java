package testCases;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;

import libraries.Assertion;
import libraries.Browser;
import libraries.Driver;
import libraries.Results;
import pageObjects.sauceDemo.*;

public class Valid_Login extends Driver{
public void Valid_Login(Map<String, String> testData) {
	try {
		Browser.launchApplication(testData.get("browser"), "SauceDemo");
		Login.login(testData.get("username"), testData.get("password"));
		Assertion.assertEquals(Products.getPageTitle(), testData.get("expectedPageTitle"), "Page Title");		
	}catch(Exception e) {
		executionContinue.set(false);
		log.error(Results.getStackMsg(e));
		
	}
}
}
