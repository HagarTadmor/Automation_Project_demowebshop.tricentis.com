package demowebshop.tricentis.com.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import demowebshop.tricentis.com.pageObjects.AllProductsPage;
import demowebshop.tricentis.com.pageObjects.BasePage;
import demowebshop.tricentis.com.pageObjects.CheckOutPage;
import demowebshop.tricentis.com.pageObjects.ConfirmationPage;
import demowebshop.tricentis.com.pageObjects.GiftCardPage;
import demowebshop.tricentis.com.pageObjects.LoginPage;
import demowebshop.tricentis.com.pageObjects.MainPage;
import demowebshop.tricentis.com.pageObjects.SearchPage;
import demowebshop.tricentis.com.pageObjects.ShoppingCartPage;
import demowebshop.tricentis.com.pageObjects.SingleProductPage;
import demowebshop.tricentis.com.pageObjects.WishListPage;
import utils.Utils;

public class BaseTest {

	WebDriver driver;
	WebDriverWait wait;
	BasePage basePage;
	MainPage mainPage;
	LoginPage loginPage;
	AllProductsPage allProductsPage;
	SingleProductPage singleProductPage;
	WishListPage wishListPage;
	ShoppingCartPage shoppingCartPage;
	CheckOutPage checkOutPage;
	GiftCardPage giftCardPage;
	SearchPage searchPage;
	ConfirmationPage confirmationPage;

	@Parameters({"browser"})
	@BeforeClass
	public void setup(@Optional("Chrome") String browser) {
	    switch (browser) {
	        case "Chrome":
	            WebDriverManager.chromedriver().setup();
	            driver = new ChromeDriver();
	            break;
	        case "Firefox":
	            WebDriverManager.firefoxdriver().setup();
	            driver = new FirefoxDriver();
	            break;
	        case "Edge":
	            WebDriverManager.edgedriver().setup();
	            EdgeOptions edgeOptions = new EdgeOptions();
	            driver = new EdgeDriver(edgeOptions);
	            break;
	        default:
	            throw new IllegalArgumentException("No such browser " + browser);
	    }

	    driver.manage().window().maximize();
	    driver.get(Utils.readValue("url"));
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    setUpPages(); // Initialize page objects here
	}

	@BeforeClass(dependsOnMethods = "setup")
	public void setUpLogin() {
	    String username = Utils.readValue("username");
	    String password = Utils.readValue("password");

	    loginPage = PageFactory.initElements(driver, LoginPage.class);
	    loginPage.login(username, password);
	}

	protected void setUpPages() {
		basePage = PageFactory.initElements(driver, BasePage.class);
		mainPage = PageFactory.initElements(driver, MainPage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		allProductsPage  = PageFactory.initElements(driver, AllProductsPage.class);
		singleProductPage = PageFactory.initElements(driver, SingleProductPage.class);
		shoppingCartPage = PageFactory.initElements(driver, ShoppingCartPage.class);
		wishListPage = PageFactory.initElements(driver, WishListPage.class);
		checkOutPage = PageFactory.initElements(driver, CheckOutPage.class);
		giftCardPage = PageFactory.initElements(driver, GiftCardPage.class);
		searchPage = PageFactory.initElements(driver, SearchPage.class);
		confirmationPage = PageFactory.initElements(driver, ConfirmationPage.class);
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
