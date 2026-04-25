package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for explicit waits and element interactions.
 */
public class WaitUtils {

    private static final Logger log = LogManager.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = 15;

    private WaitUtils() {}

    public static WebDriverWait getWait(WebDriver driver, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        log.debug("Waiting for element visible: {}", locator);
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        log.debug("Waiting for element clickable: {}", locator);
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static boolean waitForInvisible(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForUrlContains(WebDriver driver, String partialUrl) {
        log.debug("Waiting for URL to contain: {}", partialUrl);
        getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlContains(partialUrl));
    }

    public static void waitForTitleContains(WebDriver driver, String partialTitle) {
        getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.titleContains(partialTitle));
    }
}
