package demowebshop.tricentis.com.pageObjects;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WishListPage extends MainPage {

	// Constructor
	public WishListPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy(css = ".wishlist-content")
	WebElement emptyWishListMsg;
	@FindBy(css = ".cart-item-row")
	List<WebElement> productsList;
	@FindBy(css = ".product")
	WebElement productName;
	@FindBy(css = "[name='removefromcart']")
	List<WebElement> removeFromWishListCheckBox;
	@FindBy(css = "[name='updatecart']")
	WebElement updateWishListBtn;
	@FindBy(css = "[name='addtocartbutton']")
	WebElement addToCartBtn;
	@FindBy(css = ".product-subtotal")
	List<WebElement> productsSubTotalElement;

	// Methods for interacting with products in the wish list
	public void setProductQuantity(String productName, String quantity) {
		for (WebElement productRow : productsList) {
			Helpers_ProductRowElements elements = getProductRowElements(productRow);
			WebElement titleElement = elements.getTitleElement();
			WebElement quantityElement = elements.getQuantityElement();

			if (titleElement.getText().equalsIgnoreCase(productName)) {
				click(quantityElement);
				fillText(quantityElement, quantity);
				click(updateWishListBtn);
				break;
			}
		}
	}

	public String removeAllProductsFromWishList() {
		try {
			// Select all checkboxes
			for (WebElement removeCheckbox : removeFromWishListCheckBox) {
				if (!removeCheckbox.isSelected()) {
					click(removeCheckbox); // Click on each checkbox to select it
				}
			}
			// Click the update cart button after selecting all checkboxes
			click(updateWishListBtn);
			// Wait for the empty cart message to be visible
			wait.until(ExpectedConditions.visibilityOf(emptyWishListMsg));
			// Get the text of the empty cart message
			String emptyCartMessage = emptyWishListMsg.getText();
			if ("Your Shopping Cart is empty!".equalsIgnoreCase(emptyCartMessage)) {
				return emptyCartMessage;
			} else {
				return "The wish list was not emptied successfully.";
			}
		} catch (NoSuchElementException e) {
			// Handle the case where an element is not found
			return "An element required to remove products from the wish list was not found.";
		}
	}

	public void removeProductFromWishListByName(String productName) {
		boolean productFound = false;
		for (WebElement productRow : productsList) {
			Helpers_ProductRowElements elements = getProductRowElements(productRow);
			WebElement titleElement = elements.getTitleElement();
			WebElement removeCheckbox = removeFromWishListCheckBox.get(productsList.indexOf(productRow));

			if (titleElement.getText().equalsIgnoreCase(productName)) {
				if (!removeCheckbox.isSelected()) {
					click(removeCheckbox);  // Select the checkbox
				}
				productFound = true;
				break;
			}
		}

		if (productFound) {
			click(updateWishListBtn);  // Click the update wishlist button
		} else {
			System.out.println("Product with the title '" + productName + "' was not found in the wishlist.");
		}
	}

	public String getWishListMessage() {
		return getText(emptyWishListMsg);
	}

	public int getProductQuantity(String productName) {
		for (WebElement productRow : productsList) {
			Helpers_ProductRowElements elements = getProductRowElements(productRow);
			WebElement titleElement = elements.getTitleElement();
			WebElement quantityElement = elements.getQuantityElement();

			if (titleElement.getText().equalsIgnoreCase(productName)) {
				try {
					String quantityValue = quantityElement.getAttribute("value");
					return Integer.parseInt(quantityValue);
				} catch (NumberFormatException e) {
					System.out.println("Failed to parse product quantity.");
					return -1;
				}
			}
		}
		System.out.println("This product was not found on your wishlist.");
		return -1;
	}

	public double getTotalSumOfPrices() {
		double totalSum = 0;
		// Iterate over the list of product subtotal elements
		for (WebElement priceElement : productsSubTotalElement) {
			try {
				// Extract the text content of the element
				String priceText = getText(priceElement);
				// Convert the string to a double and add to the total sum
				double price = Double.parseDouble(priceText);
				totalSum += price;
			} catch (NumberFormatException e) {
				System.out.println("Failed to parse price: " + getText(priceElement));
			}
		}
		// Return the total sum as a double
		return totalSum;
	}

	public void addToCartFromWishList(String productName) {
		boolean productFound = false;

		for (WebElement productRow : productsList) {
			Helpers_ProductRowElements elements = getProductRowElements(productRow);
			WebElement titleElement = elements.getTitleElement();
			WebElement addToCartCheckbox = productRow.findElement(By.cssSelector("[name='addtocart']"));

			if (titleElement.getText().equalsIgnoreCase(productName)) {
				click(addToCartCheckbox);
				click(addToCartBtn);
				productFound = true;
				break;
			}
		}

		if (!productFound) {
			System.out.println("Product with the title '" + productName + "' was not found in the wishlist.");
		}
	}

	public String locateByProductName(String productName) {
		boolean productFound = false;
		// Iterate over the list of product rows
		for (WebElement productRow : productsList) {
			Helpers_ProductRowElements elements = getProductRowElements(productRow);
			WebElement titleElement = elements.getTitleElement(); 
			if (titleElement.getText().equalsIgnoreCase(productName)) {
				productFound = true;
				break;
			}
		}

		if (productFound) {
			return "Product " + productName + " was found in the wish list.";
		} else {
			return "Product " + productName + " was not found in the wish list.";
		}
	}

	// Helper method to get ProductRowElements for a given row
	protected Helpers_ProductRowElements getProductRowElements(WebElement productRow) {
		WebElement titleElement = productRow.findElement(By.cssSelector(".product > a"));
		WebElement quantityElement = productRow.findElement(By.cssSelector("td.qty.nobr > input"));
		return new Helpers_ProductRowElements(titleElement, quantityElement);
	}
	
}
