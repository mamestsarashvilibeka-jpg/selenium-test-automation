package com.automation.pages;

import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Base Page Object containing shared element interactions.
 * All page objects extend this class.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final Logger log;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.log = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        WaitUtils.waitForClickable(driver, element);
        log.debug("Clicking element: {}", element);
        element.click();
    }

    protected void click(By locator) {
        WaitUtils.waitForClickable(driver, locator).click();
    }

    protected void type(WebElement element, String text) {
        WaitUtils.waitForVisible(driver, element);
        element.clear();
        element.sendKeys(text);
        log.debug("Typed '{}' into element", text);
    }

    protected String getText(WebElement element) {
        WaitUtils.waitForVisible(driver, element);
        return element.getText();
    }

    protected void selectByText(WebElement element, String text) {
        new Select(element).selectByVisibleText(text);
    }

    protected void selectByValue(WebElement element, String value) {
        new Select(element).selectByValue(value);
    }

    protected void hoverOver(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getTitle() {
        return driver.getTitle();
    }

    protected void waitForUrl(String partialUrl) {
        WaitUtils.waitForUrlContains(driver, partialUrl);
    }
}
