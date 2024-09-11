package demowebshop.tricentis.com.pageObjects;

import static org.testng.Assert.assertEquals;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	private Actions actions;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		actions = new Actions(driver);
	}

	// General Methods
	public WebDriver getDriver() {
		return driver;
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	// Wait Methods
	public void waitUntilElementClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitUntilElementLocated(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitUntilElementVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitUntilElementInvisible(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public String getVisibilityStatus(WebElement element) {
		try {
			return element.isDisplayed() ? "Visible" : "Invisible";
		} catch (NoSuchElementException e) {
			// If the element is not found, it's considered as not visible
			return "Invisible";
		}
	}

	// Interaction Methods
	public void fillText(WebElement element, String text) {
		highlightElement(element, "pink");
		element.clear();
		element.sendKeys(text);
	}

	public void click(WebElement element) {
		highlightElement(element, "yellow");
		element.click();
	}

	public String getText(WebElement element) {
		highlightElement(element, "orange");
		return element.getText();
	}

	public String getAttribute(WebElement element, String attributeName) {
		highlightElement(element, "orange");
		return element.getAttribute(attributeName);
	}

	public void selectByValue(WebElement element, String value) {
		highlightElement(element, "yellow");
		Select select = new Select(element);
		select.selectByValue(value);
	}

	public void mouseHovering(WebElement element) {
		highlightElement(element, "yellow");
		actions.moveToElement(element).perform();
	}

	// Navigation Methods
	public void assertCurrentURL(String expectedUrl) {
		String currentUrl = driver.getCurrentUrl();
		assertEquals(currentUrl, expectedUrl, "The URL is not as expected");
	}

	public void navigateToPage(String menuName, String expectedUrl) {
		List<WebElement> topMenuList = driver.findElements(By.cssSelector(".top-menu > li"));
		for (WebElement topMenu : topMenuList) {
			if (topMenu.getText().equalsIgnoreCase(menuName)) {
				click(topMenu);
				assertCurrentURL(expectedUrl);
				break;
			}
		}
	}

	public void navigateToCategoryPage(String menuName, String categoryName, String expectedUrl) {
		List<WebElement> topMenuList = driver.findElements(By.cssSelector(".top-menu > li"));
		for (WebElement topMenu : topMenuList) {
			if (topMenu.getText().equalsIgnoreCase(menuName)) {
				mouseHovering(topMenu);
				List<WebElement> categoryList = driver.findElements(By.cssSelector(".sublist.firstLevel > li"));
				for (WebElement category : categoryList) {
					if (category.getText().equalsIgnoreCase(categoryName)) {
						click(category);
						assertCurrentURL(expectedUrl);
						break;
					}
				}
				break;
			}
		}
	}

	// Utility Methods
	protected Helpers_ProductRowElements getProductRowElements(WebElement productRow) {
		WebElement titleElement = productRow.findElement(By.cssSelector(".product > a"));
		WebElement quantityElement = productRow.findElement(By.cssSelector("td.qty.nobr > input"));
		return new Helpers_ProductRowElements(titleElement, quantityElement);
	}

	protected void highlightElement(WebElement element, String color) {
		String originalStyle = element.getAttribute("style");
		String newStyle = "background-color:" + color + ";" + originalStyle;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].setAttribute('style', '" + newStyle + "');", element);

		js.executeScript("setTimeout(function() { arguments[0].setAttribute('style', '" + originalStyle + "'); }, 400);", element);
	}

	public void waiting(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
