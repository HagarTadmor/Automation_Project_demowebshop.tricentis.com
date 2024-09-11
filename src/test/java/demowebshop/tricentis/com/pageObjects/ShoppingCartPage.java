package demowebshop.tricentis.com.pageObjects;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShoppingCartPage extends MainPage {

	// Constructor
	public ShoppingCartPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy (css="[name='removefromcart']")
	List<WebElement> removeFromCartCheckBox;
	@FindBy (css=".product-name")
	List<WebElement> productName;
	@FindBy (css=".button-2.update-cart-button")
	WebElement updateCartBtn;
	@FindBy (css=".order-summary-content")
	WebElement emptyShoppingCartMsg;
	@FindBy (css=".product-price.order-total")
	WebElement totalPriceElement;
	@FindBy (css=".button-2.continue-shopping-button")
	WebElement continueShoppingBtn;
	@FindBy (css=".discount-coupon-code")
	WebElement dicountCodeField;
	@FindBy (css=".button-2.apply-discount-coupon-code-button")
	WebElement applyCouponBtn;
	@FindBy (css=".gift-card-coupon-code")
	WebElement giftCardField;
	@FindBy (css=".button-2.apply-gift-card-coupon-code-button")
	WebElement addGiftCardBtn;
	@FindBy (css=".message")
	WebElement codeErrorMsg;
	@FindBy (css="#CountryId")
	WebElement countryDD;
	@FindBy (css="#CountryId > option")
	List<WebElement> countriesOptionList;
	@FindBy (css="#ZipPostalCode")
	WebElement zipCodeField;
	@FindBy (css=".button-2.estimate-shipping-button")
	WebElement estimateShippingBtn;
	@FindBy (css=".option-name")
	List<WebElement> shippingOptions;
	@FindBy (css="#termsofservice")
	WebElement termsOfServingCheckBox;
	@FindBy (css="#checkout")
	WebElement checkOutBtn;
	@FindBy (css="#terms-of-service-warning-box")
	WebElement termsOfServiceWarning;
	@FindBy (css=".ui-button-icon-primary.ui-icon.ui-icon-closethick")
	WebElement closeTermsOfServiceWarningBtn;


	// Methods for interacting with cart
	public String removeAllProductsFromCart() {
		try {
			// Select all checkboxes
			for (WebElement removeCheckbox : removeFromCartCheckBox) {
				if (!removeCheckbox.isSelected()) {
					click(removeCheckbox); // Click on each checkbox to select it
				}
			}

			// Click the update cart button after selecting all checkboxes
			click(updateCartBtn);

			// Wait for the empty cart message to be visible
			wait.until(ExpectedConditions.visibilityOf(emptyShoppingCartMsg));

			// Get the text of the empty cart message
			String emptyCartMessage = getText(emptyShoppingCartMsg);
			if ("Your Shopping Cart is empty!".equalsIgnoreCase(emptyCartMessage)) {
				return emptyCartMessage;
			} else {
				return "The cart was not emptied successfully.";
			}
		} catch (NoSuchElementException e) {
			// Handle the case where an element is not found
			return "An element required to remove products from the cart was not found.";
		}
	}

	public void removeProductFromCartByName(String productTitle) {
		boolean productFound = false;

		for (WebElement product : productName) {
			if (product.getText().equalsIgnoreCase(productTitle)) {
				int index = productName.indexOf(product);
				WebElement removeCheckbox = removeFromCartCheckBox.get(index);
				if (!removeCheckbox.isSelected()) {
					click(removeCheckbox);  // Select the checkbox
				}
				productFound = true;
				break;  // Stop the loop as the product has been found and checked
			}
		}

		if (productFound) {
			click(updateCartBtn);  // Click the update cart button if the product was found and selected
		} else {
			System.out.println("Product with the title '" + productTitle + "' was not found in the cart.");
		}
	}

	public void continueShopping() {
		click(continueShoppingBtn);
	}

	public double getTotalPrice() {
		String totalPrice = getText(totalPriceElement);
		return Double.parseDouble(totalPrice);
	}

	// Methods for applying discount and gift card codes
	public void discountCode(String discountCode) {
		fillText(dicountCodeField, discountCode);
		click(applyCouponBtn);
	}

	public void giftCardCode(String giftCardCode) {
		fillText(giftCardField, giftCardCode);
		click(addGiftCardBtn);
	}

	public String getCodeErrorMessage() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".message")));
		return getText(codeErrorMsg);
	}

	// Methods for estimating shipping
	public void estimateShipping(String country, String zipCode) {
		click(countryDD);

		for (WebElement option : countriesOptionList) {
			String optionText = option.getText();
			if (optionText.equalsIgnoreCase(country)) {
				click(option);
				break;
			}
		}
		fillText(zipCodeField, zipCode);
		click(estimateShippingBtn);
	}

	public String getEstimstedShippingOptions() {
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".option-name")));

		StringBuilder optionsText = new StringBuilder();
		for (WebElement option : shippingOptions) {
			optionsText.append(getText(option)).append(", ");
		}
		if (optionsText.length() > 0) {
			optionsText.setLength(optionsText.length() - 2); // Remove last ", "
		}
		return optionsText.toString();
	}

	// Methods for checkout process
	public void goToCheckOut() {
		click(termsOfServingCheckBox);
		click(checkOutBtn);
	}

	public String checkOutAlert() {
		click(checkOutBtn);
		String alertText = getText(termsOfServiceWarning);
		click(closeTermsOfServiceWarningBtn);
		return alertText;
	}
}
