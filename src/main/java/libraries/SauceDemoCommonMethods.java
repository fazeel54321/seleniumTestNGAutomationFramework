package libraries;

import pageObjects.sauceDemo.Products;
import pageObjects.sauceDemo.Login;

public class SauceDemoCommonMethods {
public static void login(String browserName, String username, String password) throws AssertionError, InterruptedException {
	Browser.launchApplication(browserName, "SauceDemo");
	Login.login(username, password);
	Assertion.assertEquals(Products.getPageTitle(), "Products", "Page Title");
}
}
