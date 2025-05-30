package testCases;

import java.util.Map;

import libraries.Assertion;
import libraries.Browser;
import libraries.Driver;
import libraries.Results;
import pageObjects.sauceDemo.Login;

public class Close_Error_Message_Icon extends Driver{
	public void Close_Error_Message_Icon(Map<String, String> testData) {
		try {
			Browser.launchApplication(testData.get("browser"), "SauceDemo");
			Login.login(testData.get("username"), testData.get("password"));
			boolean isErrorClosed = Login.closeError();
			Assertion.assertTrue(isErrorClosed, "Error Message Invisibled");		
		}catch(Exception e) {
			executionContinue.set(false);
			log.error(Results.getStackMsg(e));
			
		}
	}
}
