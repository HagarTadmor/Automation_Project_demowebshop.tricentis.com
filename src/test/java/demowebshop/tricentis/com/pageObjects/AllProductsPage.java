package demowebshop.tricentis.com.pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import dev.failsafe.internal.util.Assert;

public class AllProductsPage extends MainPage {

	// Constructor
	public AllProductsPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy (css=".product-title")
	List<WebElement> productTitles;
	@FindBy (css=".button-2.product-box-add-to-cart-button")
	List<WebElement> addToCartBtn;
	@FindBy (css="#bar-notification")
	WebElement barNotification;
	@FindBy (css="#bar-notification > p")
	WebElement barNotificationMsg;
	@FindBy (css=".close")
	WebElement barNotificationCloseBtn;
	@FindBy (css="#products-orderby")
	WebElement sortByDD;
	@FindBy (css="#products-pagesize")
	WebElement displayPerPageDD;
	@FindBy (css="#products-viewmode")
	WebElement viewProductsDD;
	@FindBy (css=".price.old-price")
	List<WebElement> oldPriceElement;
	@FindBy (css=".price.actual-price")
	List<WebElement> currentPriceElement;


    // Methods for interacting with products
    public String addToCartByProductName(String productName) {
        WebElement matchedProduct = null;
        WebElement matchedAddToCartBtn = null;

        for (int i = 0; i < productTitles.size(); i++) {
            WebElement product = productTitles.get(i);
            if (product.getText().equalsIgnoreCase(productName)) {
                matchedProduct = product;
                matchedAddToCartBtn = addToCartBtn.get(i);
                break;
            }
        }

        if (matchedProduct == null || matchedAddToCartBtn == null) {
            return "Sorry, the product or the 'Add to Cart' button was not found.";
        }

        getText(matchedProduct);
        click(matchedAddToCartBtn);

        wait.until(ExpectedConditions.visibilityOf(barNotification));
        if (barNotification.isDisplayed()) {
            String notificationText = getText(barNotificationMsg);
            if ("The product has been added to your shopping cart".equalsIgnoreCase(notificationText)) {
                click(barNotificationCloseBtn);
                return notificationText;
            } else {
                return "Sorry, the notification message is not as expected.";
            }
        } else {
            return "Sorry, the notification bar did not appear.";
        }
    }

    public String addToCart() {
        WebElement addToCartButton = null;

        for (WebElement button : addToCartBtn) {
            if (button != null && button.isDisplayed()) {
                addToCartButton = button;
                break;
            }
        }

        if (addToCartButton == null) {
            return "Sorry, no available products with 'Add to Cart' button found.";
        }

        click(addToCartButton);

        try {
            wait.until(ExpectedConditions.visibilityOf(barNotification));
        } catch (Exception e) {
            return "Sorry, the notification bar did not appear.";
        }

        if (barNotification.isDisplayed()) {
            String notificationText = getText(barNotificationMsg);
            if ("The product has been added to your shopping cart".equalsIgnoreCase(notificationText)) {
                click(barNotificationCloseBtn);
                return notificationText;
            } else {
                return "Sorry, the notification message is not as expected.";
            }
        } else {
            return "Sorry, the notification bar did not appear.";
        }
    }

    public String enterProductByName(String productTitle) {
        for (WebElement title : productTitles) {
            if (title.getText().equalsIgnoreCase(productTitle)) {
                click(title);
                return "Product selected successfully.";
            }
        }
        return "Product was not found on this page.";
    }

    // Methods for sorting, display, and view options
    public String sortBy(String option) {
        click(sortByDD);
        Select sortBySelect = new Select(sortByDD);

        for (WebElement sortByOption : sortBySelect.getOptions()) {
            if (sortByOption.getText().equalsIgnoreCase(option)) {
                sortByOption.click();
                break;
            }
        }
        return sortBySelect.getFirstSelectedOption().getText();
    }

    public String displayPerPage(int displaySize) {
        List<Integer> allowedOptions = Arrays.asList(4, 8, 12);

        if (!allowedOptions.contains(displaySize)) {
            return "Invalid display size option.";
        }

        click(displayPerPageDD);
        Select displaySelect = new Select(displayPerPageDD);

        for (WebElement displayOption : displaySelect.getOptions()) {
            if (displayOption.getText().equalsIgnoreCase(String.valueOf(displaySize))) {
                displayOption.click();
                break;
            }
        }
        return displaySelect.getFirstSelectedOption().getText();
    }

    public String viewProducts(String viewOption) {
        List<String> allowedOptions = Arrays.asList("grid", "list");

        if (!allowedOptions.contains(viewOption.toLowerCase())) {
            return "Invalid view option.";
        }

        click(viewProductsDD);
        Select viewSelect = new Select(viewProductsDD);

        for (WebElement option : viewSelect.getOptions()) {
            if (option.getText().equalsIgnoreCase(viewOption)) {
                option.click();
                break;
            }
        }
        return viewSelect.getFirstSelectedOption().getText();
    }

    // Methods for retrieving product prices
    public List<Double> getProductOldPrice() {
        List<Double> oldPrices = new ArrayList<>();
        for (WebElement priceElement : oldPriceElement) {
            String priceText = priceElement.getText().replaceAll("[^\\d.]", "");
            oldPrices.add(Double.parseDouble(priceText));
        }
        return oldPrices;
    }

    public List<Double> getProductCurrentPrice() {
        List<Double> currentPrices = new ArrayList<>();
        for (WebElement priceElement : currentPriceElement) {
            String priceText = priceElement.getText().replaceAll("[^\\d.]", "");
            currentPrices.add(Double.parseDouble(priceText));
        }
        return currentPrices;
    }
}
