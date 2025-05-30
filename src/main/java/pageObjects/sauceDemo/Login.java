package pageObjects.sauceDemo;

import org.openqa.selenium.By;
import libraries.Web;

public class Login {
	private static By username = By.id("user-name");
	private static By password = By.id("password");
	private static By login = By.id("login-button");
	private static By errorMessage = By.xpath("//*[@class='error-message-container error']");
	private static By errorClose = By.className("error-button");
	private static By usernameErrorIcon = By.xpath("//input[@class='input_error form_input error' and @id='user-name']");
	private static By passwordErrorIcon = By.xpath("//input[@class='input_error form_input error' and @id='password']");
	public static void login(String username, String password) {
		typeUsernameAndPassword(username, password);
		clickLogin();
	}
	public static String getErrorMessage() {
		return Web.getText(errorMessage);
	}
	public static Boolean closeError() {
		Web.click(errorClose);
		return Web.waitUntilInvisible(errorClose);
	}
	public static void isErrorIconsVisible() {
		Web.waitUntilVisible(usernameErrorIcon);
		Web.waitUntilVisible(passwordErrorIcon);
	}
	public static void typeUsernameAndPassword(String username, String password) {
		Web.type(Login.username, username);
		Web.type(Login.password, password);
	}
	public static void clickLogin() {
		Web.click(login);
	}
	public static boolean isErrorIconsDisappear() {
		Web.waitUntilInvisible(usernameErrorIcon);
		Web.waitUntilInvisible(passwordErrorIcon);
		return true;
	}
	

}
