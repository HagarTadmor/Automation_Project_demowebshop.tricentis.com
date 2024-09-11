package demowebshop.tricentis.com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {

	private int cartQuantity;
	private String emptyCartMessage;
	private String errorMessage;


	@Test (description = "Add products to the cart, remove all products from cart and assert it's empty")
	public void tc01_shoppingCartTest() {
		//1. Enter Digital downloads category and add 'music 2' & '3rd album' products to the shopping cart
		mainPage.digitalDownloadsPage();
		allProductsPage.addToCartByProductName("music 2");
		allProductsPage.addToCartByProductName("3rd album");

		//2. Enter shopping cart, remove all products from cart and assert it's empty
		mainPage.shoppingCartPage();
		emptyCartMessage = shoppingCartPage.removeAllProductsFromCart();
		Assert.assertEquals(emptyCartMessage, "Your Shopping Cart is empty!", "The shopping cart is not empty after removal.");
	}

	@Test (description = "Add products to cart and assert cart quantity")
	public void tc02_shoppingCartTest() {
		//1. Enter books category, and add 'Computing and Internet' product to cart
		mainPage.homePage();
		mainPage.booksPage();
		allProductsPage.addToCartByProductName("Computing and Internet");

		//2. Enter accessories category, enter product 'TCP Public RPA/TCD', set quantity to 4 and add to cart
		mainPage.accessoriesPage();
		allProductsPage.enterProductByName("TCP Public RPA/TCD");
		singleProductPage.setProductQuantity(4);
		singleProductPage.addToCart();

		//3. Go to home page and check the cart contains 5 products 
		mainPage.homePage();
		cartQuantity = mainPage.getCartQuantity();
		Assert.assertEquals(cartQuantity, 5, "Expected cart quantity is 5, but found: " + cartQuantity);
	}

	@Test (description = "Remove all products from the cart, add products to the cart and assert total price")
	public void tc03_shoppingCartTest() {
		//1. Remove all products from the cart
		mainPage.homePage();
		mainPage.shoppingCartPage();
		emptyCartMessage = shoppingCartPage.removeAllProductsFromCart();
		Assert.assertEquals(emptyCartMessage, "Your Shopping Cart is empty!", "The shopping cart is not empty after removal.");

		//2. Enter home page and add to cart '14.1-inch Laptop' product
		mainPage.homePage();
		allProductsPage.addToCartByProductName("14.1-inch Laptop");

		//3. Enter cell phones category, enter 'smartphone' product, set quantity to 5 and add to cart
		mainPage.cellPhonesPage();
		allProductsPage.enterProductByName("smartphone");
		singleProductPage.setProductQuantity(5);
		singleProductPage.addToCart();

		//4. Enter shopping cart and assert total price
		mainPage.shoppingCartPage();
		double totalPrice = shoppingCartPage.getTotalPrice();
		Assert.assertEquals(totalPrice, 2090.00);
	}

	@Test (description = "Add to cart a product from 'Apparel & Shoes' category, enter cart and press 'continue shopping', assert page goes back to 'Apparel & Shoes' category")
	public void tc04_shoppingCartTest() {
		//1. Enter Apparel & Shoes category, add to cart product 'Casual Golf Belt'
		mainPage.apparelAndShoesPage();
		allProductsPage.addToCartByProductName("Casual Golf Belt");

		//2. Enter shopping cart, press 'continue shopping' and assert page goes back to home page
		mainPage.shoppingCartPage();
		shoppingCartPage.continueShopping();
		String currentUrl = driver.getCurrentUrl();
		String expectedUrl = "https://demowebshop.tricentis.com/apparel-shoes";
		Assert.assertEquals(currentUrl, expectedUrl, "The page did not navigate back to the 'Apparel & Shoes' category.");
	}

	@Test (description = "Enter cart, insert invalid discount code and assert the error message")
	public void tc05_shoppingCartTest() {
		//1. Enter cart, insert 'hello' in discount code field, assert the error message
		mainPage.shoppingCartPage();
		shoppingCartPage.discountCode("hello");
		mainPage.waiting(300);
		errorMessage = shoppingCartPage.getCodeErrorMessage();
		Assert.assertEquals(errorMessage, "The coupon code you entered couldn't be applied to your order");
	}

	@Test (description = "Enter cart, insert invalid gift card code and assert the error message")
	public void tc06_shoppingCartTest() {
		//1. Enter cart, Insert '123456' in gift card code field and the error message
		mainPage.shoppingCartPage();
		shoppingCartPage.giftCardCode("123456");
		errorMessage = shoppingCartPage.getCodeErrorMessage();
		Assert.assertEquals(errorMessage, "The coupon code you entered couldn't be applied to your order");
	}

	@Test (description = "Enter cart, insert values in 'Estimate shipping' block and assert all the shipping options")
	public void tc07_shoppingCartTest() {
		//1. Enter cart and pick 'israel' in country drop down, insert '9876543' in zip code field
		mainPage.shoppingCartPage();
		shoppingCartPage.estimateShipping("israel", "9876543");
		
		//2. Assert the shipping options and prices
		String shippingOptions = shoppingCartPage.getEstimstedShippingOptions();
		Assert.assertEquals(shippingOptions, "Ground (0.00), Next Day Air (0.00), 2nd Day Air (0.00), In-Store Pickup (0.00)");
	}
	
	@Test (description = "Enter cart and press 'checkout' without checking the 'terms of service' and assert the alert")
	public void tc08_shoppingCartTest() {
		//Enter shopping cart and assert check out alert (clicking on checkout with out checking the 'terms of service')
		mainPage.shoppingCartPage();
		String checkOutAlert = shoppingCartPage.checkOutAlert();
		Assert.assertEquals(checkOutAlert, "Please accept the terms of service before the next step.");
	}

}
