package demowebshop.tricentis.com.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SearchPage extends MainPage {

	// Constructor
	public SearchPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy(css = "#Q")
	WebElement searchField;
	@FindBy(css = ".button-1.search-button")
	WebElement searchBtn;
	@FindBy(css = "#As")
	WebElement advancedSearchCheckBox;
	@FindBy(css = ".warning")
	WebElement searchWarningText;
	@FindBy(css = ".result")
	WebElement searchResultText;
	@FindBy(css = ".product-title")
	List<WebElement> productTitles;
	@FindBy(css = "#Cid")
	WebElement categoryDD;
	@FindBy(css = "#Isc")
	WebElement subCategoriesCheckBox;
	@FindBy(css = "#Mid")
	WebElement manufacturerDD;
	@FindBy(css = "#Pf")
	WebElement priceRangeFromField;
	@FindBy(css = "#Pt")
	WebElement priceRangeToField;
	@FindBy(css = "#Sid")
	WebElement productDescriptionsCheckBox;

	// Methods for performing searches
	public void search(String text) {
		fillText(searchField, text);
		click(searchBtn);
	}

	public String getResultText() {
		return getText(searchResultText);
	}

	public String getWarningText() {
		return getText(searchWarningText);
	}

	public String getProductResults(String productTitle) {
		for (WebElement product : productTitles) {
			if (product.getText().equalsIgnoreCase(productTitle)) {
				return product.getText();
			}
		}
		return "Product not found"; // Return a message if no matching product is found
	}

	public int getProductResultsCount() {
		return productTitles.size();
	}

	// Methods for advanced search
	public String addToCartFromSearch() {
		AllProductsPage allProductsPage = new AllProductsPage(driver);
		return allProductsPage.addToCart();
	}

	public void advancedSearch(String searchText, String categoryOption, String priceRangeFrom, String priceRangeTo) {
		fillText(searchField, searchText);
		click(advancedSearchCheckBox);
		pickCategory(categoryOption);
		click(subCategoriesCheckBox);
		fillText(priceRangeFromField, priceRangeFrom);
		fillText(priceRangeToField, priceRangeTo);
		click(productDescriptionsCheckBox);
		click(searchBtn);
	}

	public String pickCategory(String categoryOption) {
		click(categoryDD);

		Select categorySelect = new Select(categoryDD);
		for (WebElement category : categorySelect.getOptions()) {
			if (category.getText().equalsIgnoreCase(categoryOption)) {
				category.click();
				break;
			}
		}
		return categorySelect.getFirstSelectedOption().getText();
	}
}
