package demowebshop.tricentis.com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductsTest extends BaseTest {

	private String notificationBarMsg;
	private String freeShippingMsg;
	private String outOfStockMsg;
	private String selectedOption;

	@Test (description = "Add to cart 2 products")
	public void tc01_products() {
		//1. Enter digital downloads category and add the '3rd album' product to the cart
		mainPage.digitalDownloadsPage();
		notificationBarMsg = allProductsPage.addToCartByProductName("3rd album");
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");

		//2. Enter cell phones sub category (under electronics), enter phone cover product, pick color blue
		mainPage.cellPhonesPage();
		allProductsPage.enterProductByName("phone cover");
		selectedOption = singleProductPage.pickPhoneCoverColor("blue");
		Assert.assertEquals(selectedOption, "Blue", "The selected color is not correctly set to 'Blue'.");

		//3. Add to cart
		notificationBarMsg = singleProductPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");
	}

	@Test (description = "Add to cart a product with free shipping")
	public void tc02_products() {
		//1. Enter books category, enter the 'computing and Internet' product
		mainPage.booksPage();
		allProductsPage.enterProductByName("computing and internet");

		//2. Check it has free shipping
		freeShippingMsg = singleProductPage.checkIfFreeShipping();
		Assert.assertEquals(freeShippingMsg, "Yes, there's free shipping for this product.", "Expected message not received.");

		//3. Add to cart
		notificationBarMsg = singleProductPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");
	}

	@Test (description = "Add to cart a product with quantity higher than 1")
	public void tc03_products() {
		//1. Enter apparel & shoes category, enter 'blue jeans' product, set quantity '245' and click on add to cart
		mainPage.apparelAndShoesPage();
		allProductsPage.enterProductByName("blue jeans");
		singleProductPage.setProductQuantity(245);
		notificationBarMsg = singleProductPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "The maximum quantity allowed for purchase is 100.", "Expected message not received.");

		//2. Refresh page, set quantity to 100 and add to cart
		mainPage.refreshPage();
		singleProductPage.setProductQuantity(100);
		notificationBarMsg = singleProductPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");
	}

	@Test (description = "Enter 'apparel & shoes' category, set display to '12', view as list and add to cart a product")
	public void tc04_products() {
		//1. Enter 'apparel & shoes' category, select display 12 
		mainPage.apparelAndShoesPage();
		selectedOption = allProductsPage.displayPerPage(12);
		Assert.assertEquals(selectedOption, "12", "The display per page option is not correctly set to '12'.");

		//2. Select view as 'list'
		selectedOption = allProductsPage.viewProducts("list");
		Assert.assertEquals(selectedOption, "List", "The view option is not correctly set to 'List'.");

		//3. Enter product 'Men's Wrinkle Free Long Sleeve', select size Large
		allProductsPage.enterProductByName("men's wrinkle free long sleeve");
		selectedOption = singleProductPage.pickShirtSize("large");
		Assert.assertEquals(selectedOption, "Large", "The selected size is not correctly set to 'L'.");

		//4. Add to wish list
		notificationBarMsg = singleProductPage.addToWishList();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your wishlist", "Expected message not received.");
	}

	@Test (description = "Enter jewelry category, sort products 'Price: High to Low' and add to cart the highest price product that's			  							available")
	public void tc05_products() {
		//1. Enter Jewlery category
		mainPage.jewelryPage();

		//2. Sort by 'price: high to low', assert sort by
		selectedOption = allProductsPage.sortBy("price: high to low");
		Assert.assertEquals(selectedOption, "Price: High to Low", "The sort by option is not correctly set.");

		//3. Add highest price product to cart
		notificationBarMsg = allProductsPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");
	}

	@Test (description = "Add a giftcard to cart")
	public void tc06_products() {
		//1. Enter gift cards category, enter product '$100 Physical Gift Card' and assert sender name
		mainPage.giftCardsPage();
		allProductsPage.enterProductByName("$100 physical gift card");
		Assert.assertEquals(giftCardPage.getGiftCardSenderName(), "Hagar Tadmor", "Sender name is not correct.");

		//2. Fill info and add to cart
		giftCardPage.fillGiftCardInfo("Mom", "Happy birthday");
		singleProductPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "The product has been added to your shopping cart", "Expected message not received.");
	}

	@Test (description = "Try to add to cart out of stock product")
	public void tc07_products() {
		//1. Enter books category, enter product 'copy of computing and internet ex'
		mainPage.booksPage();
		allProductsPage.enterProductByName("copy of computing and internet ex");

		//2. Check if it's in stock (should be out of stock)
		outOfStockMsg = singleProductPage.checkAvailability();
		Assert.assertEquals(outOfStockMsg, "Availability: Out of stock", "Expected message not received.");

		//3. Try to add to cart (should not be able to)
		notificationBarMsg = singleProductPage.addToCart();
		Assert.assertEquals(notificationBarMsg, "Sorry, the 'Add to Cart' button was not found.", "Expected message not received.");
	}
} 