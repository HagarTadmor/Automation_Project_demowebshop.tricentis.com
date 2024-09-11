package demowebshop.tricentis.com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
	
	private String searchResultText;
	private int searchResultNum;

	@Test (description = "Search from quick search on home page, null search")
	public void tc01_quickSearch() {
		//1. On home page, click on search button without inserting text and assert the warning alert
		mainPage.homePage();
		String alertText = mainPage.nullSearchAlert();
		Assert.assertEquals(alertText, "Please enter some search keyword", "Alert text does not match");
	}

	@Test (description = "Search from quick search on home page, search 2 characters")
	public void tc02_quickSearch() {
		//1. On home page, perform a search from search bar with text 'xy'
		mainPage.homePage();
		mainPage.quickSearch("xy");

		//2. Assert the url contains 'search'
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("https://demowebshop.tricentis.com/search"), "The URL does not contain 'search'. Current URL: " + currentUrl);

		//3. Assert the warning for minimum 3 characters
		searchResultText = searchPage.getWarningText();
		Assert.assertEquals(searchResultText, "Search term minimum length is 3 characters", "Warning text does not match the expected value.");
	}

	@Test (description = "Search from quick search on home page, search 3 characters")
	public void tc03_quickSearch() {
		//1. On home page, perform a search from search bar with text 'abc'
		mainPage.homePage();
		mainPage.quickSearch("abc");

		//2. Assert search result text (no products were found)
		searchResultText = searchPage.getResultText();
		Assert.assertEquals(searchResultText, "No products were found that matched your criteria.", "Result text does not match the expected value.");
	}

	@Test (description = "Search from quick search on home page, search certain product name and assert by product name")
	public void tc04_quickSearch() {
		//1. On home page, perform a search of product 'Casual Golf Belt'
		mainPage.homePage();
		mainPage.quickSearch("casual golf belt");

		//2. Assert search result by product name
		searchResultText = searchPage.getProductResults("casual golf belt");
		Assert.assertEquals(searchResultText, "Casual Golf Belt", "Product was not found or doesn't match the expected result.");
	}

	@Test (description = "On search page, search certain product name and assert products quantity")
	public void tc05_searchPage() {
		//1. Enter search page and perform a search of text 'sneaker'
		mainPage.searchtPage();
		searchPage.search("sneaker");

		//2. Assert 2 products were found in search results
		searchResultNum = searchPage.getProductResultsCount();
		Assert.assertEquals(searchResultNum, 2, "Result count does not match the expected result.");
	}

	@Test (description = "On search page, search certain product name, add to cart and assert it was added")
	public void tc06_searchPage() {
		//1. Enter search page and perform a search of text 'health book'
		mainPage.searchtPage();
		searchPage.search("health book");
		
		//2. Add to cart and assert it was added
		String notificationBarMsg = searchPage.addToCartFromSearch();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");
	}

	@Test (description = "On search page perform a search, then perform an advanced search and assert results")
	public void tc07_searchPage() {
		//1. Enter search page and perform a search of text 'diamond', assert there 4 products were found
		mainPage.searchtPage();
		searchPage.search("diamond");
		searchResultNum = searchPage.getProductResultsCount();
		Assert.assertEquals(searchResultNum, 4, "Result count does not match the expected result.");
		
		//2. Perform an advanced search of text 'diamond', category 'jewelry', price range 1-361, assert results now show 2 products
		searchPage.advancedSearch("diamond", "jewelry", "1", "361");
		searchResultNum = searchPage.getProductResultsCount();
		Assert.assertEquals(searchResultNum, 3, "Result count does not match the expected result.");
	}
}
