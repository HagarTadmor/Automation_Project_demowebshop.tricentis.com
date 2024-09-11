package demowebshop.tricentis.com.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage extends MainPage {

	// Constructor
	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}
	
	// Element declarations
	@FindBy (css=".section.order-completed div strong")
	WebElement orderConfirmationText;
	
	// Methods
	public String getOrderConfirmationText() {
		waitUntilElementVisible(orderConfirmationText);
		return getText(orderConfirmationText);
	}
}
