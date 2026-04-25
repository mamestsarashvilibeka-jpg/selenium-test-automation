package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Checkout pages (step one and two).
 */
public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage fillShippingInfo(String firstName, String lastName, String postalCode) {
        log.info("Filling checkout info for: {} {}", firstName, lastName);
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        return this;
    }

    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }

    public CheckoutPage clickFinish() {
        log.info("Completing order");
        click(finishButton);
        return this;
    }

    public String getTotalAmount() {
        return getText(totalLabel);
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public String getConfirmationMessage() {
        return getText(confirmationText);
    }

    public boolean isOrderComplete() {
        return isDisplayed(confirmationHeader) &&
               getConfirmationHeader().toLowerCase().contains("thank you");
    }
}
