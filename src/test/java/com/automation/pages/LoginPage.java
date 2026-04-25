package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Login page (targets SauceDemo as demo site).
 */
public class LoginPage extends BasePage {

    private static final String PAGE_URL = "https://www.saucedemo.com/";

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        log.info("Navigating to Login page: {}", PAGE_URL);
        driver.get(PAGE_URL);
        return this;
    }

    public LoginPage enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        type(passwordInput, password);
        return this;
    }

    public InventoryPage clickLogin() {
        log.info("Clicking login button");
        click(loginButton);
        return new InventoryPage(driver);
    }

    public LoginPage clickLoginExpectingFailure() {
        click(loginButton);
        return this;
    }

    public LoginPage loginAs(String username, String password) {
        return enterUsername(username)
                .enterPassword(password);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().equals(PAGE_URL) || getCurrentUrl().contains("saucedemo");
    }
}
