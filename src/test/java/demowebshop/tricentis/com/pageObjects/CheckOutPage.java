package demowebshop.tricentis.com.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CheckOutPage extends MainPage {

	// Constructor
	public CheckOutPage(WebDriver driver) {
		super(driver);
	}

	// Constants
	private static final String COUNTRY_ISRAEL = "Israel";
	private static final String ATTRIBUTE_VALUE = "value";


	// Element declarations

	//Billing address
	@FindBy (css=".section.select-billing-address label")
	WebElement billingAddressSectionText;
	@FindBy (css="#billing-address-select")
	WebElement billingAddressDD;
	@FindBy(css = "#billing-address-select option[value='']")
	WebElement billingAddressNewAddressOption;
	@FindBy(css = "#BillingNewAddress_FirstName")
	WebElement billingAddressFirstNameField;
	@FindBy(css = "#BillingNewAddress_LastName")
	WebElement billingAddressLastNameField;
	@FindBy(css = "#BillingNewAddress_Email")
	WebElement billingAddressEmailField;
	@FindBy(css = "#BillingNewAddress_CountryId")
	WebElement countryDD;
	@FindBy (css="#BillingNewAddress_CountryId > option")
	List<WebElement> billingCountriesOptionList;
	@FindBy(css = "#BillingNewAddress_City")
	WebElement billingCityField;
	@FindBy(css = "#BillingNewAddress_Address1")
	WebElement billingAddressField;
	@FindBy(css = "#BillingNewAddress_ZipPostalCode")
	WebElement billingZipCodeField;
	@FindBy(css = "#BillingNewAddress_PhoneNumber")
	WebElement billingPhoneNumberField;
	@FindBy(css = "#billing-buttons-container > input")
	WebElement billingAddressContinueBtn;

	//Shipping address
	@FindBy (css=".section.select-shipping-address label")
	WebElement shippingAddressSectionText;
	@FindBy (css="#shipping-address-select")
	public WebElement shippingAddressDD;
	@FindBy(css = "#shipping-address-select option[value='']")
	WebElement shippingAddressNewAddressOption;
	@FindBy(css = "#ShippingNewAddress_FirstName")
	WebElement shippingAddressFirstNameField;
	@FindBy(css = "#ShippingNewAddress_LastName")
	WebElement shippingAddressLastNameField;
	@FindBy(css = "#ShippingNewAddress_Email")
	WebElement shippingAddressEmailField;
	@FindBy(css = "#ShippingNewAddress_CountryId")
	WebElement shippingCountryDD;
	@FindBy (css="#ShippingNewAddress_CountryId > option")
	List<WebElement> shippingCountriesOptionList;
	@FindBy(css = "#ShippingNewAddress_City")
	WebElement shippingCityField;
	@FindBy(css = "#ShippingNewAddress_Address1")
	WebElement shippingAddressField;
	@FindBy(css = "#ShippingNewAddress_ZipPostalCode")
	WebElement shippingZipCodeField;
	@FindBy(css = "#ShippingNewAddress_PhoneNumber")
	WebElement shippingPhoneNumberField;
	@FindBy (css="#PickUpInStore")
	WebElement inStorePickupCheckBox;
	@FindBy(css = "#shipping-buttons-container > input")
	WebElement shippingAddressContinueBtn;

	//Shipping methods
	@FindBy(css = ".method-description")
	List<WebElement> shippingMethodDescriptions;
	@FindBy (css=".method-name")
	List<WebElement> shippingMethodTitles;
	@FindBy(css = "#shippingoption_0")
	WebElement groundShippingRadioBtn;
	@FindBy(css = "#shippingoption_1")
	WebElement nextDayAirRadioBtn;
	@FindBy(css = "#shippingoption_2")
	WebElement secondDayAirRadioBtn;
	@FindBy (css="#shipping-method-buttons-container > input")
	WebElement shippingMethodContinueBtn;

	//Payment methods
	@FindBy(css = ".method-list li")
	List<WebElement> paymentMethods;
	@FindBy(css = ".payment-details label")
	List<WebElement> paymentMethodsTitles;
	@FindBy (css=".payment-details input[type='radio']")
	List<WebElement> paymentMethodsRadioBtn;
	@FindBy (css="#paymentmethod_0")
	WebElement cashOnDeliveryRadioBtn;
	@FindBy (css="#paymentmethod_1")
	WebElement checkMoneyOrderRadioBtn;
	@FindBy (css="#paymentmethod_2")
	WebElement creditCardRadioBtn;
	@FindBy (css="#paymentmethod_3")
	WebElement purchaseOrderRadioBtn;
	@FindBy (css="#payment-method-buttons-container > input")
	WebElement paymentMethodContinueBtn;

	//Payment information
	@FindBy (css="div.info > table > tbody > tr > td > p")
	WebElement paymentMethodInfo;
	@FindBy (css="#payment-info-buttons-container > input")
	WebElement paymentInformationContinueBtn;
	@FindBy (css=".validation-summary-errors ul li")
	List<WebElement> paymentInformationValidationErrors;

	//Confirm order
	@FindBy (css="#confirm-order-buttons-container > input")
	WebElement confirmOrderBtn;

	// Methods

	// Private helper methods for code reuse
	private void selectNewAddress(WebElement dropdown, WebElement newAddressOption) {
		click(dropdown);
		click(newAddressOption);
	}

	private void fillProfileInputs(List<WebElement> optionList, String city, String address, String zipCode, String phoneNumber) {
		click(countryDD);
		for (WebElement option : optionList) {
			if (COUNTRY_ISRAEL.equalsIgnoreCase(option.getText())) {
				click(option);
				break;
			}
		}
		fillText(billingCityField, city);
		fillText(billingAddressField, address);
		fillText(billingZipCodeField, zipCode);
		fillText(billingPhoneNumberField, phoneNumber);
	}

	// Billing Address Methods
	public String getBillingAddressSectionText() {
		return getText(billingAddressSectionText);
	}

	public String getDefaultBillingAddress() {
		Select select = new Select(billingAddressDD);
		return getText(select.getFirstSelectedOption());
	}

	public void selectNewBillingAddress() {
		selectNewAddress(billingAddressDD, billingAddressNewAddressOption);
	}

	public String getBillingAddressProfileInputs() {
		String firstName = getAttribute(billingAddressFirstNameField, ATTRIBUTE_VALUE);
		String lastName = getAttribute(billingAddressLastNameField, ATTRIBUTE_VALUE);
		String email = getAttribute(billingAddressEmailField, ATTRIBUTE_VALUE);
		return String.format("%s, %s, %s", firstName, lastName, email);
	}

	public void fillInfoBillingAddress(String city, String address, String zipCode, String phoneNumber) {
		fillProfileInputs(billingCountriesOptionList, city, address, zipCode, phoneNumber);
	}

	public void billingAddressContinue() {
		click(billingAddressContinueBtn);
		waiting(1000);
	}

	// Shipping Address Methods
	public String getShippingAddressSectionText() {
		waitUntilElementVisible(shippingAddressSectionText);
		return getText(shippingAddressSectionText);
	}

	public String getDefaultShippingAddress() {
		Select select = new Select(shippingAddressDD);
		return getText(select.getFirstSelectedOption());
	}

	public void selectNewShippingAddress() {
		selectNewAddress(shippingAddressDD, shippingAddressNewAddressOption);
	}

	public String getShippingAddressProfileInputs() {
		String firstName = getAttribute(shippingAddressFirstNameField, ATTRIBUTE_VALUE);
		String lastName = getAttribute(shippingAddressLastNameField, ATTRIBUTE_VALUE);
		String email = getAttribute(shippingAddressEmailField, ATTRIBUTE_VALUE);
		return String.format("%s, %s, %s", firstName, lastName, email);
	}

	public void fillInfoShippingAddress(String city, String address, String zipCode, String phoneNumber) {
		fillProfileInputs(shippingCountriesOptionList, city, address, zipCode, phoneNumber);
	}

	public String selectInStorePickUp() {
		click(inStorePickupCheckBox);
		waitUntilElementInvisible(shippingAddressDD);
		return getVisibilityStatus(shippingAddressDD);
	}

	public void shippingAddressContinue() {
		click(shippingAddressContinueBtn);
		waiting(1000);
	}

	// Shipping Methods
	public String shippingMethodsGetText() {
		StringBuilder descriptions = new StringBuilder();
		for (WebElement description : shippingMethodDescriptions) {
			descriptions.append(getText(description)).append("\n\n");
		}
		return descriptions.toString().trim();
	}

	public void selectShippingMethod(String shippingMethod) {
		switch (shippingMethod) {
		case "Ground":
			click(groundShippingRadioBtn);
			break;
		case "Next Day Air":
			click(nextDayAirRadioBtn);
			break;
		case "2nd Day Air":
			click(secondDayAirRadioBtn);
			break;
		default:
			System.out.println("Shipping method with name " + shippingMethod + " not found.");
			break;
		}
	}

	public void shippingMethodContinue() {
		waitUntilElementVisible(shippingMethodContinueBtn);
		click(shippingMethodContinueBtn);
		waiting(1000);
	}

	// Payment Methods
	public String getPaymentMethods() {
		StringBuilder paymentMethodsText = new StringBuilder();
		for (WebElement title : paymentMethodsTitles) {
			paymentMethodsText.append(getText(title)).append("\n\n");
		}
		return paymentMethodsText.toString().trim();
	}

	public void selectPaymentMethod(String paymentMethod) {
		switch (paymentMethod) {
		case "Cash On Delivery":
			click(cashOnDeliveryRadioBtn);
			break;
		case "Check / Money Order":
			click(checkMoneyOrderRadioBtn);
			break;
		case "Credit Card":
			click(creditCardRadioBtn);
			break;
		case "Purchase Order":
			click(purchaseOrderRadioBtn);
			break;
		default:
			System.out.println("Payment method with name " + paymentMethod + " not found.");
			break;
		}
	}

	public void paymentMethodContinue() {
		click(paymentMethodContinueBtn);
		waiting(1000);
	}

	// Payment Information
	public String paymentInformationGetText() {
		return getText(paymentMethodInfo);
	}

	public void paymentInformationContinue() {
		click(paymentInformationContinueBtn);
		waiting(1000);
	}

	public String getPaymentValidationErrors() {
		StringBuilder errors = new StringBuilder();
		for (WebElement error : paymentInformationValidationErrors) {
			errors.append(getText(error)).append("\n");
		}
		return errors.toString().trim();
	}

	// Confirm Order
	public void confirmOrder() {
		waitUntilElementVisible(confirmOrderBtn);
		click(confirmOrderBtn);
		waiting(3000);
	}

	public void selectCashOnDelivery() {
		click(cashOnDeliveryRadioBtn);
	}

	public String getPaymentMethodInfo() {
		waitUntilElementVisible(paymentMethodInfo); // Ensure the element is visible before getting text
		return getText(paymentMethodInfo);
	}

	public String getPaymentInformationValidationErrors() {
		StringBuilder errorMessages = new StringBuilder();

		// Ensure the list of validation errors is not empty
		if (paymentInformationValidationErrors != null && !paymentInformationValidationErrors.isEmpty()) {
			for (WebElement errorElement : paymentInformationValidationErrors) {
				// Append each error message to the StringBuilder
				errorMessages.append(getText(errorElement)).append("\n");
			}
		} else {
			errorMessages.append("No validation errors found.");
		}

		return errorMessages.toString().trim();
	}
}