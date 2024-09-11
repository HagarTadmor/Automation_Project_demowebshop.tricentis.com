package demowebshop.tricentis.com.pageObjects;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.asm.MemberSubstitution.FieldValue;

public class SingleProductPage extends MainPage {

	// Constructor
	public SingleProductPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy (css=".free-shipping")
	WebElement freeShippingBanner;
	@FindBy (css=".stock")
	WebElement stockAvailability;
	@FindBy (css=".product-review-links")
	WebElement reviewCountElement;
	@FindBy (css=".button-1.add-to-cart-button")
	WebElement addToCartBtn;
	@FindBy (css="#bar-notification")
	WebElement barNotification;
	@FindBy (css="#bar-notification > p")
	WebElement barNotificationMsg;
	@FindBy (css=".close")
	WebElement barNotificationCloseBtn;
	@FindBy (css=".button-2.add-to-wishlist-button")
	WebElement addToWishListBtn;
	@FindBy (css=".qty-input")
	WebElement quantityField;
	@FindBy (css="#product_attribute_80_1_38")
	WebElement phoneCoverColorDD;
	@FindBy (css="#product_attribute_10_7_36")
	WebElement shirtSizeDD;

	// Methods for interacting with product details
	public String checkAvailability() {
		try {
			// Check if the stock availability element is present and visible
			if (stockAvailability != null && stockAvailability.isDisplayed()) {
				// Use the getText method to get the availability text
				String availabilityText = getText(stockAvailability).toLowerCase();

				if (availabilityText.contains("in stock")) {
					return getText(stockAvailability);
				} else if (availabilityText.contains("out of stock")) {
					return getText(stockAvailability);
				}
			}
		} catch (NoSuchElementException e) {
			// Handle the case where the stock availability element is not found
			System.out.println("Stock availability element not found.");
		}
		// Return a default message if the element is not found or text does not match
		return "Sorry, the product availability could not be determined.";
	}

	public String addToCart() {
		try {
			// Attempt to find the "Add to Cart" button
			WebElement addToCartButton;
			try {
				addToCartButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button-1.add-to-cart-button")));
			} catch (Exception e) {
				// If the element is not found, return this message
				return "Sorry, the 'Add to Cart' button was not found.";
			}

			// Check if the "Add to Cart" button is displayed
			if (addToCartButton != null && addToCartButton.isDisplayed()) {
				click(addToCartButton);

				// Wait for the notification bar to appear
				waitUntilElementVisible(barNotification);

				// Check if the notification bar is visible
				if (barNotification.isDisplayed()) {
					// Retrieve and return the notification text
					String notificationText = getNotificationBarText();

					// Handle different scenarios based on the notification text
					if (notificationText.contains("maximum quantity allowed")) {
						return notificationText;
					} else if (notificationText.contains("added to your shopping cart")) {
						return notificationText;
					} else {
						return "Unexpected notification message: " + notificationText;
					}
				} else {
					return "Sorry, the notification bar did not appear.";
				}
			} else {
				return "Sorry, the 'Add to Cart' button is not displayed.";
			}
		} catch (Exception e) {
			// Handle unexpected errors
			return "An unexpected error occurred: " + e.getMessage();
		} finally {
			// Close the notification bar if it was displayed
			if (barNotification.isDisplayed()) {
				click(barNotificationCloseBtn);
			}
		}
	}

	public int getNumberOfReviews() {
		try {
			String reviewText = reviewCountElement.getText();
			// Extract number from the review text
			String reviewCountStr = reviewText.replaceAll("[^0-9]", "");
			return Integer.parseInt(reviewCountStr);
		} catch (NoSuchElementException e) {
			// Handle the case where the review count element is not found
			System.out.println("Review count element not found.");
			return -1;
		} catch (NumberFormatException e) {
			// Handle the case where the review count cannot be parsed as an integer
			System.out.println("Failed to parse review count.");
			return -1;
		}
	}

	public String addToWishList() {
		try {
			click(addToWishListBtn);

			waitUntilElementVisible(barNotification);
			String notificationText = getNotificationBarText();
			if (barNotification.isDisplayed()) {
				click(barNotificationCloseBtn);
			}
			return notificationText;
		} catch (NoSuchElementException e) {
			// Handle the case where the 'Add to Wish List' button is not found
			return "Sorry, this product can not be added to your wishlist at the moment.";
		}
	}

	public void setProductQuantity(int quantity) {
		waitUntilElementClickable(quantityField);
		click(quantityField);
		quantityField.clear();
		quantityField.sendKeys(String.valueOf(quantity));
	}

	public String checkIfFreeShipping() {
		try {
			// Check if the free shipping banner is displayed
			if (freeShippingBanner.isDisplayed()) {
				// Use the getText method to get the text from the freeShippingBanner element
				String bannerText = getText(freeShippingBanner);

				if (bannerText.equalsIgnoreCase("free shipping")) {
					return "Yes, there's free shipping for this product.";
				} else {
					return "Sorry, there's no free shipping for this product. The banner text is: " + bannerText;
				}
			} else {
				return "Sorry, there's no free shipping for this product.";
			}
		} catch (NoSuchElementException e) {
			// Handle the case where the free shipping banner element is not found
			return "Sorry, there's no free shipping for this product.";
		}
	}

	// Methods for selecting product options
	public String pickPhoneCoverColor(String colorOption) {
		click(phoneCoverColorDD);

		Select colorSelect = new Select(phoneCoverColorDD);
		for (WebElement color : colorSelect.getOptions()) {
			if (color.getText().equalsIgnoreCase(colorOption)) {
				color.click();
				break;
			}
		}
		return colorSelect.getFirstSelectedOption().getText();
	}

	public String pickShirtSize(String sizeOption) {
		click(shirtSizeDD);

		Select sizeSelect = new Select(shirtSizeDD);
		for (WebElement size : sizeSelect.getOptions()) {
			if (size.getText().equalsIgnoreCase(sizeOption)) {
				size.click();
				break;
			}
		}
		return sizeSelect.getFirstSelectedOption().getText();
	}
}