package com.automation.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener for enhanced test reporting and logging.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("╔══════════════════════════════════════════════════════════════╗");
        log.info("  Test Suite Starting: {}", context.getName());
        log.info("╚══════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("╔══════════════════════════════════════════════════════════════╗");
        log.info("  Suite: {}  |  Passed: {}  |  Failed: {}  |  Skipped: {}",
                context.getName(),
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
        log.info("╚══════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ TEST STARTED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ TEST PASSED: {} ({} ms)",
                result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ TEST FAILED: {} — {}",
                result.getMethod().getMethodName(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠️  TEST SKIPPED: {}", result.getMethod().getMethodName());
    }
}
