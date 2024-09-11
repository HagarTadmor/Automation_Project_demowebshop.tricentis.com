package demowebshop.tricentis.com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	// Constructor
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy (css="#Email")
	WebElement emailField;
	@FindBy (css="#Password")
	WebElement passwordField;
	@FindBy (css="#RememberMe")
	WebElement rememberMeCheckBox;
	@FindBy (css=".button-1.login-button")
	WebElement loginBtn;
	@FindBy (css= ".validation-summary-errors > span")
	WebElement errorMessage;
	@FindBy (css= ".validation-summary-errors > ul")
	WebElement errorMessageReason;
	@FindBy (css=".field-validation-error")
	WebElement validationErrorMessage;
	@FindBy (css= ".forgot-password > a")
	WebElement forgotPasswordBtn;
	@FindBy (css=".page-title")
	WebElement pageTitle;
	@FindBy (css="div.header-links > ul > li:nth-child(1) > a")
	WebElement accountName;

	// Methods

	public void login(String email, String password) {
		clickLoginIcon();
		fillText(emailField, email);
		fillText(passwordField, password);
		click(rememberMeCheckBox);
		click(loginBtn);
	}

	public String getLoginErrorMessage() {
		return String.format("%s - %s", getText(errorMessage), getText(errorMessageReason));
	}

	public String getLoginValidationErrorMessage() {
		return getText(validationErrorMessage);
	}

	public String forgotPassword() {
		click(forgotPasswordBtn);
		return getText(pageTitle);
	}

	public String getAccountName() {
		return getText(accountName);
	}

	// Helper method to click the login icon
	private void clickLoginIcon() {
		click(driver.findElement(By.cssSelector(".ico-login")));
	}
}
