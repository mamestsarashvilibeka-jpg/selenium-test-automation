package com.automation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton config reader for properties files.
 * Supports JVM system property overrides for CI/CD pipelines.
 *
 * @author QA Automation Team
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static volatile ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
            log.info("Configuration loaded successfully");
        } catch (IOException e) {
            log.error("Failed to load config.properties", e);
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    public String get(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null) throw new RuntimeException("Property '" + key + "' not found in config");
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue)).trim();
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
