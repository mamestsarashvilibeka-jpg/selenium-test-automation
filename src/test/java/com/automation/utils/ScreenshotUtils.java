package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility for capturing and saving screenshots.
 */
public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOTS_DIR = "screenshots/";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {}

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String fileName = testName + "_" + timestamp + ".png";
            Path dir = Paths.get(SCREENSHOTS_DIR);
            Files.createDirectories(dir);
            Path filePath = dir.resolve(fileName);

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), filePath);

            log.info("Screenshot saved: {}", filePath);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Failed to capture screenshot for test: {}", testName, e);
            return null;
        }
    }

    public static byte[] captureScreenshotAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
