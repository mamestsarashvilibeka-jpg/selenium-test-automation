package com.automation.tests;

import com.automation.config.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ScreenshotUtils;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Base test class providing driver lifecycle and common test utilities.
 * All test classes extend this.
 */
public abstract class BaseTest {

    protected final Logger log = LogManager.getLogger(this.getClass());
    protected final ConfigReader config = ConfigReader.getInstance();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(
            @org.testng.annotations.Optional("chrome") String browser,
            @org.testng.annotations.Optional("false") String headless
    ) {
        String resolvedBrowser = System.getProperty("browser", browser);
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", headless));
        log.info("=== Starting test setup: browser={}, headless={} ===", resolvedBrowser, isHeadless);
        DriverManager.initDriver(resolvedBrowser, isHeadless);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test FAILED: {}", result.getName());
            attachScreenshot(result.getName());
        }
        DriverManager.quitDriver();
        log.info("=== Test teardown complete: {} ===", result.getName());
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @Attachment(value = "Failure Screenshot - {testName}", type = "image/png")
    private byte[] attachScreenshot(String testName) {
        return ScreenshotUtils.captureScreenshotAsBytes(getDriver());
    }
}
