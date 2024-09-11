package demowebshop.tricentis.com.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class LoginTest extends BaseTest {

	String email = Utils.readValue("username");
	String password = Utils.readValue("password");
	private String actualErrorMessage;
	private String expectedErrorMessage;

	@Override
	@BeforeMethod
	public void setUpLogin() {
		//Override the setUpLogin method on base test and initialize pages
		setUpPages();
	}

	@Test (description = "Perform a failed login without email field")
	public void tc01_failedLoginTest() {
		//1. In login page, fill in only password field and click on login button
		loginPage.login("", password);

		//2. Assert error messages
		actualErrorMessage = loginPage.getLoginErrorMessage();
		expectedErrorMessage = "Login was unsuccessful. Please correct the errors and try again. - No customer account found";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}

	@Test (description = "Perform a failed login without password field")
	public void tc02_failedLoginTest() {
		//1. In login page, fill in only email field and click on login button
		loginPage.login(email, "");

		//2. Assert error messages
		actualErrorMessage = loginPage.getLoginErrorMessage();
		expectedErrorMessage = "Login was unsuccessful. Please correct the errors and try again. - The credentials provided are incorrect";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}

	@Test (description = "Perform a failed login with invalid email field")
	public void tc03_failedLoginTest() {
		//1. In login page, fill in invalid email, valid password
		loginPage.login("123", password);

		//2. Assert error messages
		actualErrorMessage = loginPage.getLoginValidationErrorMessage();
		expectedErrorMessage = "Please enter a valid email address.";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}


	@Test (description = "Perform a failed login with invalid password field")
	public void tc04_failedLoginTest() {
		//1. In login page, fill in valid email, invalid password
		loginPage.login(email, "אאא אאא");

		//2. Assert error messages
		actualErrorMessage = loginPage.getLoginErrorMessage();
		expectedErrorMessage = "Login was unsuccessful. Please correct the errors and try again. - The credentials provided are incorrect";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}

	@Test (description = "Perform a failed login with nonexistent email in field")
	public void tc05_failedLoginTest() {
		//1. In login page, fill in valid email of a nonexistent user, valid password
		loginPage.login("hagartadmor21@gmail.com", "123");

		//2. Assert error messages
		actualErrorMessage = loginPage.getLoginErrorMessage();
		expectedErrorMessage = "Login was unsuccessful. Please correct the errors and try again. - No customer account found";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}

	@Test (description = "Click on forgot password and assert you reached password recovery page")
	public void tc06_passwordRecovery() {
		//1. Enter forgot password page and assert it by page title
		String pageTitle = loginPage.forgotPassword();
		String expectedTitle = "Password recovery";
		Assert.assertEquals(pageTitle, expectedTitle);

		//2. Assert 'forgot password' page by URL
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "https://demowebshop.tricentis.com/passwordrecovery";
		Assert.assertEquals(actualUrl, expectedUrl, "The URL did not match the expected URL for the forgot password page.");
	}

	@Test (description = "Perform a successful login and log out")
	public void tc07_successfulLoginAndLogOutTest() {
		//1. Enter login page and perform a successful login
		loginPage.login(email, password);

		//2. Click on log out button and assert the log in button appears
		String logOut = mainPage.logOut();
		Assert.assertEquals(logOut, "Log in");
	}

	@Test (description = "Perform a successful login")
	public void tc08_successfulLoginTest() {
		//1. Enter login page and perform a successful login
		loginPage.login(email, password);

		//2. Assert login was successful by account name
		String actualAccountName = loginPage.getAccountName();
		String expectedAcountName = email;
		Assert.assertEquals(actualAccountName, expectedAcountName);
	}
	
}
