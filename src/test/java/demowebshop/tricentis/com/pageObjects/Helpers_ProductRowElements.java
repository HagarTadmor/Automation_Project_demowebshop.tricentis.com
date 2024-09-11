package demowebshop.tricentis.com.pageObjects;

import org.openqa.selenium.WebElement;

public class Helpers_ProductRowElements {
    private WebElement titleElement;
    private WebElement quantityElement;

    public Helpers_ProductRowElements(WebElement titleElement, WebElement quantityElement) {
        this.titleElement = titleElement;
        this.quantityElement = quantityElement;
    }

    public WebElement getTitleElement() {
        return titleElement;
    }

    public WebElement getQuantityElement() {
        return quantityElement;
    }
}
