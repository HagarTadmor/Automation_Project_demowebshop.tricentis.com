package demowebshop.tricentis.com.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GiftCardPage extends MainPage {

    // Constants
    private static final String ATTRIBUTE_VALUE = "value";

    // Constructor
    public GiftCardPage(WebDriver driver) {
        super(driver);
    }

    // Element declarations
    @FindBy(css = ".recipient-name")
    private WebElement giftCardRecipientNameField;
    @FindBy(css = ".sender-name")
    private WebElement giftCardSenderNameField;
    @FindBy(css = ".message")
    private WebElement giftCardMessageField;

    // Methods

    public void fillGiftCardInfo(String recipientName, String message) {
        fillText(giftCardRecipientNameField, recipientName);
        fillText(giftCardMessageField, message);
    }

    public String getGiftCardSenderName() {
        waitUntilElementVisible(giftCardSenderNameField);
        return getAttribute(giftCardSenderNameField, ATTRIBUTE_VALUE);
    }
}
