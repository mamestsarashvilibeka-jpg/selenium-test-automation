package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Thread-safe WebDriver manager using ThreadLocal for parallel execution.
 * Supports Chrome, Firefox, and Edge browsers.
 *
 * @author QA Automation Team
 * @version 1.0.0
 */
public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void initDriver(String browser, boolean headless) {
        WebDriver driver;
        log.info("Initializing {} driver (headless={})", browser, headless);

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) ffOptions.addArguments("--headless");
                driver = new FirefoxDriver(ffOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default: // chrome
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                options.addArguments(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--window-size=1920,1080",
                        "--disable-gpu",
                        "--disable-extensions"
                );
                driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
        log.info("Driver '{}' initialized successfully", browser);
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            log.info("Driver quit and removed from ThreadLocal");
        }
    }
}
