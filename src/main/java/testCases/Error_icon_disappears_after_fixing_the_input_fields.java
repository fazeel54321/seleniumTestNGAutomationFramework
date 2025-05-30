package testCases;

import java.util.Map;

import libraries.Assertion;
import libraries.Browser;
import libraries.Driver;
import libraries.Results;
import pageObjects.sauceDemo.Login;

public class Error_icon_disappears_after_fixing_the_input_fields extends Driver {

	public void Error_icon_disappears_after_fixing_the_input_fields(Map<String, String> testData) {
		try {
		Browser.launchApplication(testData.get("browser"), "SauceDemo");
		Login.clickLogin();
		Login.isErrorIconsVisible();
		Login.typeUsernameAndPassword(testData.get("username"), testData.get("password"));
		boolean isErrorIconsDisappear = Login.isErrorIconsDisappear();
		Assertion.assertTrue(isErrorIconsDisappear, "Error Icons Disappeared");
		}catch(Exception e) {
			executionContinue.set(false);
			log.error(Results.getStackMsg(e));
			
		}
	}

}
