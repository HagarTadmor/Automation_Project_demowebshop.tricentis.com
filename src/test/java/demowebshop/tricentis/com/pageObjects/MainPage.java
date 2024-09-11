package demowebshop.tricentis.com.pageObjects;

import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

	// Constructor
	public MainPage(WebDriver driver) {
		super(driver);
	}

	// Element declarations
	@FindBy (css=".header-logo")
	WebElement homePageBtn;
	@FindBy (css="#topcartlink")
	WebElement shoppingCartBtn;
	@FindBy (css=".cart-qty")
	WebElement cartQuantity;
	@FindBy(css = ".count")
	WebElement shoppingCartText;
	@FindBy (css="div.header-links > ul > li:nth-child(4) > a")
	WebElement wishListBtn;
	@FindBy (css="#bar-notification > p")
	WebElement barNotificationMsg;
	@FindBy (css="#small-searchterms")
	WebElement searchBarField;
	@FindBy (css=".button-1.search-box-button")
	WebElement searchBarBtn;
	@FindBy (css=".wishlist-qty")
	WebElement wishListQuantityElement;
	@FindBy (css=".ico-login")
	WebElement loginBtn;
	@FindBy (css=".ico-logout")
	WebElement logOutBtn;

	// Navigation methods
	public void homePage() {
		click(homePageBtn);
	}

	public void booksPage() {
		navigateToPage("books", "https://demowebshop.tricentis.com/books");
	}

	public void computersPage() {
		navigateToPage("computers", "https://demowebshop.tricentis.com/computers");
	}

	public void desktopsPage() {
		navigateToCategoryPage("computers", "desktops", "https://demowebshop.tricentis.com/desktops");
	}

	public void notebooksPage() {
		navigateToCategoryPage("computers", "notebooks", "https://demowebshop.tricentis.com/notebooks");
	}

	public void accessoriesPage() {
		navigateToCategoryPage("computers", "accessories", "https://demowebshop.tricentis.com/accessories");
	}

	public void electronicsPage() {
		navigateToPage("electronics", "https://demowebshop.tricentis.com/electronics");
	}

	public void cameraPhotoPage() {
		navigateToCategoryPage("electronics", "camera, photo", "https://demowebshop.tricentis.com/camera-photo");
	}

	public void cellPhonesPage() {
		navigateToCategoryPage("electronics", "cell phones", "https://demowebshop.tricentis.com/cell-phones");
	}

	public void apparelAndShoesPage() {
		navigateToPage("apparel & shoes", "https://demowebshop.tricentis.com/apparel-shoes");
	}

	public void digitalDownloadsPage() {
		navigateToPage("digital downloads", "https://demowebshop.tricentis.com/digital-downloads");
	}

	public void jewelryPage() {
		navigateToPage("jewelry", "https://demowebshop.tricentis.com/jewelry");
	}

	public void giftCardsPage() {
		navigateToPage("gift cards", "https://demowebshop.tricentis.com/gift-cards");
	}

	// Shopping cart and wishlist methods
	public void shoppingCartPage() {
		click(shoppingCartBtn);
		assertCurrentURL("https://demowebshop.tricentis.com/cart");
	}

	public void wishListPage() {
		click(wishListBtn);
		assertCurrentURL("https://demowebshop.tricentis.com/wishlist");
	}

	public String getCartState() {
		mouseHovering(shoppingCartBtn);
		String modifiedText = getText(shoppingCartText)
				.replaceAll("\\d+", "")
				.replaceAll(" {2,}", " ");
		return modifiedText.trim();
	}

	public int getCartQuantity() {
		try {
			String quantityText = getText(cartQuantity);
			String quantityStr = quantityText.replaceAll("[^0-9]", "");
			return Integer.parseInt(quantityStr);
		} catch (NoSuchElementException | NumberFormatException e) {
			System.out.println("Failed to retrieve cart quantity.");
			return -1;
		}
	}

	public int getWishListQuantity() {
		try {
			String quantityText = getText(wishListQuantityElement);
			String quantityStr = quantityText.replaceAll("[^0-9]", "");
			return Integer.parseInt(quantityStr);
		} catch (NoSuchElementException | NumberFormatException e) {
			System.out.println("Failed to retrieve wish list quantity.");
			return -1;
		}
	}

	// Search methods
	public void searchtPage() {
		driver.navigate().to("https://demowebshop.tricentis.com/search");
		assertCurrentURL("https://demowebshop.tricentis.com/search");
	}

	public void quickSearch(String text) {
		fillText(searchBarField, text);
		click(searchBarBtn);
	}

	public String nullSearchAlert() {
		click(searchBarBtn);
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		waiting(500);
		alert.accept();
		return alertText;
	}

	// Authentication methods
	public String logOut() {
		click(logOutBtn);
		return getText(loginBtn);
	}

	// Notification methods
	public String getNotificationBarText() {
		return getText(barNotificationMsg);
	}
}