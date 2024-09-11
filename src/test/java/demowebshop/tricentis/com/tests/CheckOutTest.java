package demowebshop.tricentis.com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import demowebshop.tricentis.com.pageObjects.ConfirmationPage;

public class CheckOutTest extends BaseTest {

	private String actualSectionText;
	private String expectedSectionText;
	private String expectedProfileInfo;
	private String actualProfileInfo;

	@Test (description = "Assert check out page by URL")
	public void tc01_checkOutTest() {
		//1. Enter books category, add 'Computing and Internet' product to cart
		mainPage.booksPage();
		allProductsPage.addToCartByProductName("Computing and Internet");

		//2. Enter shopping cart continue to checkout page
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();

		//3. Assert check out page by URL
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "https://demowebshop.tricentis.com/onepagecheckout";
		Assert.assertEquals(actualUrl, expectedUrl, "The URL did not match the expected URL for the checkout page.");
	}

	@Test (description = "Assert billing address section")
	public void tc02_checkOutTest() {
		//1. Enter books category, add 'fiction' product to cart
		mainPage.booksPage();
		allProductsPage.addToCartByProductName("fiction");

		//2. Enter shopping cart continue to checkout page
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();

		//3. Assert billing address section
		actualSectionText = checkOutPage.getBillingAddressSectionText();
		expectedSectionText = "Select a billing address from your address book or enter a new address.";
		Assert.assertEquals(actualSectionText, expectedSectionText);
	}

	@Test (description = "Assert default billing address info")
	public void tc03_checkOutTest() {
		//1. Enter books category, add 'fiction' product to cart
		mainPage.booksPage();
		allProductsPage.addToCartByProductName("fiction");

		//2. Enter shopping cart continue to checkout page
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();

		//3. Assert billing address default info
		String actualDefaultBillingAddress = checkOutPage.getDefaultBillingAddress();
		String expectedDefaultBillingAddress = "Hagar Tadmor, Flower 1, Tel Aviv 1234567, Israel";
		Assert.assertEquals(actualDefaultBillingAddress, expectedDefaultBillingAddress);
	}

	@Test (description = "Assert name, last name and email in new billing option")
	public void tc04_checkOutTest() {
		//1. From home page enter Apparel & Shoes category, add 'Blue Jeans' product to cart
		mainPage.homePage();
		mainPage.apparelAndShoesPage();
		allProductsPage.addToCartByProductName("Blue Jeans");

		//2. Enter shopping cart and continue to checkout page
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();

		//3. Select new billing option and assert in billing address section the input in 'first name', 'last name' and 'email' fields matches profile info
		checkOutPage.selectNewBillingAddress();
		expectedProfileInfo = "Hagar, Tadmor, hagartadmor@gmail.com";
		actualProfileInfo = checkOutPage.getBillingAddressProfileInputs();
		Assert.assertEquals(actualProfileInfo, expectedProfileInfo, "Inputs does not match profile info.");
	}

	@Test (description = "Assert shipping address section")
	public void tc05_checkOutTest() {
		//1. From home page enter Apparel & Shoes category, add 'Blue Jeans' product to cart
		mainPage.homePage();
		mainPage.apparelAndShoesPage();
		allProductsPage.addToCartByProductName("Blue Jeans");

		//2. Enter shopping cart and continue to checkout page
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();

		//4. Select new billing address option and fill in required info and continue
		checkOutPage.selectNewBillingAddress();
		checkOutPage.fillInfoBillingAddress("Haifa", "Tree 3", "1472583", "050-1472583");
		checkOutPage.billingAddressContinue();

		//5. Assert shipping address section
		actualSectionText = checkOutPage.getShippingAddressSectionText();
		expectedSectionText = "Select a shipping address from your address book or enter a new address.";
		Assert.assertEquals(actualSectionText, expectedSectionText);
	}

	@Test (description = "Assert default shipping address")
	public void tc06_checkOutTest() {
		//1. From home page, enter shopping cart and remove all products
		mainPage.homePage();
		mainPage.shoppingCartPage();
		shoppingCartPage.removeAllProductsFromCart();

		//2. Enter cell phones category, enter product 'smartphone', set quantity to 3 and add to cart
		mainPage.cellPhonesPage();
		allProductsPage.enterProductByName("smartphone");
		singleProductPage.setProductQuantity(3);
		singleProductPage.addToCart();

		//3. Enter cart and continue to check out
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();

		//4. Press continue on billing address and assert shipping address default info
		checkOutPage.billingAddressContinue();
		String actualDefaultShippingAddress = checkOutPage.getDefaultShippingAddress();
		String expectedDefaultShippingAddress = "Hagar Tadmor, Flower 1, Tel Aviv 1234567, Israel";
		Assert.assertEquals(actualDefaultShippingAddress, expectedDefaultShippingAddress);
	}

	@Test (description = "Assert name, last name and email in shipping address section")
	public void tc07_checkOutTest() {
		//1. From home page - assert there's products in the cart
		mainPage.homePage();
		String actualCartState = mainPage.getCartState();
		String expectedCartState = "There are item(s) in your cart.";
		Assert.assertEquals(actualCartState, expectedCartState);

		//2. Enter shopping cart, continue to checkout page, continue from billing address section to shipping address section
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();

		//3. Select new shipping address option and assert in billing address section the input in 'first name', 'last name' and 'email' fields matches profile info
		checkOutPage.selectNewShippingAddress();
		expectedProfileInfo = "Hagar, Tadmor, hagartadmor@gmail.com";
		actualProfileInfo = checkOutPage.getShippingAddressProfileInputs();
		Assert.assertEquals(actualProfileInfo, expectedProfileInfo, "Inputs does not match profile info.");
	}

	@Test (description = "Assert shipping address is invisible in shipping address section after checking 'in store pick up'")
	public void tc08_checkOutTest() {
		//1. From home page - assert there's products in the cart
		mainPage.homePage();
		String actualCartState = mainPage.getCartState();
		String expectedCartState = "There are item(s) in your cart.";
		Assert.assertEquals(actualCartState, expectedCartState);

		//2. Enter cart, continue to check out, continue to shipping address section
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();

		//3. Select in store pick up and assert shipping address is invisible
		String actualVisibilityStatus = checkOutPage.selectInStorePickUp();
		String expectedVisibilityStatus = "Invisible";
		Assert.assertEquals(actualVisibilityStatus, expectedVisibilityStatus);
	}

	@Test (description = "Assert shipping methods")
	public void tc09_checkOutTest() {
		//1. From home page - assert there's products in the cart
		mainPage.homePage();
		String actualCartState = mainPage.getCartState();
		String expectedCartState = "There are item(s) in your cart.";
		Assert.assertEquals(actualCartState, expectedCartState);

		//2. Enter cart, continue to check out, continue to shipping address section, continue to shipping method
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();
		checkOutPage.shippingAddressContinue();

		//3. Assert that the actual descriptions match the expected descriptions
		String actualShippingMethods = checkOutPage.shippingMethodsGetText();
		String expectedShippingMethods = "Compared to other shipping methods, like by flight or over seas, ground shipping is carried out closer to the earth\n\n"
				+ "The one day air shipping\n\n"
				+ "The two day air shipping";
		Assert.assertEquals(actualShippingMethods, expectedShippingMethods, "The shipping method descriptions are not as expected.");
	}

	@Test (description = "Assert payment methods")
	public void tc10_checkOutTest() {
		//1. From home page - assert there's products in the cart
		mainPage.homePage();
		String actualCartState = mainPage.getCartState();
		String expectedCartState = "There are item(s) in your cart.";
		Assert.assertEquals(actualCartState, expectedCartState);

		//2. Enter cart, continue to check out, continue to shipping address section, continue to shipping method
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();
		checkOutPage.shippingAddressContinue();

		//3. Select 'Next Day Air' shipping method and continue
		checkOutPage.selectShippingMethod("Next Day Air");
		checkOutPage.shippingMethodContinue();

		//4. Assert the payment methods
		String actualPaymentMethods = checkOutPage.getPaymentMethods();
		String expectedPaymentMethods = "Cash On Delivery (COD) (7.00)\nCheck / Money Order (5.00)\nCredit Card\nPurchase Order";
		Assert.assertEquals(actualPaymentMethods, expectedPaymentMethods);
	}

	@Test (description = "Assert payment information after picking 'Cash On Delivery' payment option")
	public void tc11_checkOutTest() {
		//1. From home page - assert there's products in the cart
		mainPage.homePage();
		String actualCartState = mainPage.getCartState();
		String expectedCartState = "There are item(s) in your cart.";
		Assert.assertEquals(actualCartState, expectedCartState);

		//2. Enter cart, continue to check out, continue to shipping address section, continue to shipping method, continue to payment method
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();
		checkOutPage.shippingAddressContinue();
		checkOutPage.shippingMethodContinue();

		//3. Select 'cash on delivery' payment method and continue
		checkOutPage.selectCashOnDelivery();
		checkOutPage.paymentMethodContinue();

		//4. Assert payment information
		String actualPaymentInformation = checkOutPage.getPaymentMethodInfo();
		String expectedPaymentInformation = "You will pay by COD";
		Assert.assertEquals(actualPaymentInformation, expectedPaymentInformation);
	}

	@Test (description = "Assert validation for 'Credit Card' payment option")
	public void tc12_checkOutTest() {
		//1. From home page - assert there's products in the cart
		mainPage.homePage();
		String actualCartState = mainPage.getCartState();
		String expectedCartState = "There are item(s) in your cart.";
		Assert.assertEquals(actualCartState, expectedCartState);

		//2. Enter cart, continue to check out, continue to shipping address section, continue to shipping method, continue to payment method
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();
		checkOutPage.shippingAddressContinue();
		checkOutPage.shippingMethodContinue();

		//3. Select 'credit card' payment method and continue
		checkOutPage.selectPaymentMethod("Credit Card");
		checkOutPage.paymentMethodContinue();

		//4. Press continue with out filling in credit card info
		checkOutPage.paymentInformationContinue();

		//5. Assert the warnings
		String actualValidationWarnings = checkOutPage.getPaymentInformationValidationErrors();
		String expectedValidationWarnings = "Enter cardholder name\nWrong card number\nWrong card code";
		Assert.assertEquals(actualValidationWarnings, expectedValidationWarnings);
	}

	@Test (description = "Assert confirm order")
	public void tc13_checkOutTest() {
		//1. From home page - enter card and remove all items
		mainPage.homePage();
		mainPage.shoppingCartPage();
		shoppingCartPage.removeAllProductsFromCart();

		//2. Enter Apparel & Shoes category and add 'Casual Golf Belt' product to cart
		mainPage.apparelAndShoesPage();
		allProductsPage.addToCartByProductName("Casual Golf Belt");

		//3. Enter cart, continue to check out, continue to shipping address section, continue to shipping method, continue to payment method, continue to payment information
		mainPage.shoppingCartPage();
		shoppingCartPage.goToCheckOut();
		checkOutPage.billingAddressContinue();
		checkOutPage.shippingAddressContinue();
		checkOutPage.shippingMethodContinue();
		checkOutPage.paymentMethodContinue();

		//4. Continue to confirm order and press confirm
		checkOutPage.paymentInformationContinue();
		checkOutPage.confirmOrder();

		//5. Assert confirmation by page text
		String actualConfirmationText = confirmationPage.getOrderConfirmationText();
		String expectedConfirmationText = "Your order has been successfully processed!";
		Assert.assertEquals(actualConfirmationText, expectedConfirmationText);

		//6. Assert confirmation by URL
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "https://demowebshop.tricentis.com/checkout/completed/";
		Assert.assertEquals(actualUrl, expectedUrl, "The URL did not match the expected URL for the checkout page.");
	}

}
