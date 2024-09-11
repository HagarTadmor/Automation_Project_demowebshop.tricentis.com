package demowebshop.tricentis.com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WishListTest extends BaseTest {

	private int wishListQuantity;
	private String notificationBarMsg;

	@Test (description = "Add products to wish list, assert they were added by wish list quantity, enter wish list and change quantity to 3")
	public void tc01_wishListTest() {
		//1. From home page, enter cell phones category, add 'smartphone' and 'phone Cover' to wish list and assert it was added
		mainPage.homePage();
		mainPage.cellPhonesPage();
		allProductsPage.enterProductByName("smartphone");
		notificationBarMsg = singleProductPage.addToWishList();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your wishlist");
		
		//2. Enter cell phones category, add 'phone cover' to wish list and assert it was added
		mainPage.cellPhonesPage();
		allProductsPage.enterProductByName("phone cover");
		notificationBarMsg = singleProductPage.addToWishList();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your wishlist");

		//3. Enter wish list and change quantity of 'smartphone' to 3
		mainPage.wishListPage();
		wishListPage.setProductQuantity("smartphone", "3");
		int productQuantity = wishListPage.getProductQuantity("smartphone");
		Assert.assertEquals(productQuantity, 3);

		//4. Assert wish list quantity is 4
		wishListQuantity = mainPage.getWishListQuantity();
		Assert.assertEquals(wishListQuantity, 4);
	}

	@Test (description = "Remove items from wish list and check that wish list is empty")
	public void tc02_wishListTest() {
		//1. Enter wish list and remove items from it
		mainPage.wishListPage();
		wishListPage.removeAllProductsFromWishList();
		
		//2. Assert wish list is empty
		String wishListMsg = wishListPage.getWishListMessage();
		Assert.assertEquals(wishListMsg, "The wishlist is empty!");
	}

	@Test (description = "Add a product to wish list, then add it to shopping cart and assert wish list is empty by quantity")
	public void tc03_wishListTest() {
		//1. From home page enter accessories page, enter product 'TCP Instructor Led Training' and add to wishlist
		mainPage.homePage();
		mainPage.accessoriesPage();
		allProductsPage.enterProductByName("tcp instructor led training");
		singleProductPage.addToWishList();

		//2. Enter wish list, add 'tcp coaching day' to shopping cart and assert wishlist has 0 products
		mainPage.wishListPage();
		wishListPage.addToCartFromWishList("TCP Instructor Led Training");
		wishListQuantity = mainPage.getWishListQuantity();
		Assert.assertEquals(wishListQuantity, 0);
	}

	@Test (description = "Add 2 products to wish list and get the sum of prices")
	public void tc04_wishListTest() {
		//1. Enter jewelry category, add 'Black & White Diamond Heart' to wish list
		mainPage.homePage();
		mainPage.jewelryPage();
		allProductsPage.enterProductByName("Black & White Diamond Heart");
		singleProductPage.addToWishList();

		//2. Enter books category, add 3 'Health Book' to wish list
		mainPage.booksPage();
		allProductsPage.enterProductByName("Health Book");
		singleProductPage.setProductQuantity(3);
		singleProductPage.addToWishList();

		//3. Enter wishlist, get total sum of prices and assert
		mainPage.wishListPage();
		double totalSum = wishListPage.getTotalSumOfPrices();
		Assert.assertEquals(totalSum, 160.00);
	}

	@Test (description = "Add a product to wish list and assert it was added by product name")
	public void tc05_wishListTest() {
		//1. Enter Digital downloads category, enter '3rd album' product and add to wish list
		mainPage.digitalDownloadsPage();
		allProductsPage.enterProductByName("3rd album");
		singleProductPage.addToWishList();

		//2. Enter wish list and assert '3rd album' product is in the wish list by product name
		mainPage.wishListPage();
		String productFound = wishListPage.locateByProductName("3rd album");
		String expectedMessage = "Product 3rd album was found in the wish list.";
		Assert.assertEquals(expectedMessage, productFound);
	}

}
