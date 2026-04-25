package com.automation.tests;

import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for Login functionality.
 *
 * Tests:
 * - Successful login with valid credentials
 * - Failed login with invalid password
 * - Failed login with locked-out user
 * - Failed login with empty fields
 * - Logout after login
 */
@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";

    @Test(description = "Verify successful login with valid credentials")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("User logs in with correct username and password and is redirected to inventory page")
    public void testSuccessfulLogin() {
        log.info("Running: testSuccessfulLogin");

        InventoryPage inventoryPage = new LoginPage(getDriver())
                .open()
                .loginAs(VALID_USER, VALID_PASS)
                .clickLogin();

        assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded after login");
        assertTrue(inventoryPage.getProductCount() > 0, "Products should be displayed");
    }

    @Test(description = "Verify error message with invalid password")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidPasswordShowsError() {
        log.info("Running: testInvalidPasswordShowsError");

        LoginPage loginPage = new LoginPage(getDriver())
                .open()
                .loginAs(VALID_USER, "wrongpassword")
                .clickLoginExpectingFailure();

        assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message text should be correct");
    }

    @Test(description = "Verify locked out user cannot login")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testLockedOutUser() {
        log.info("Running: testLockedOutUser");

        LoginPage loginPage = new LoginPage(getDriver())
                .open()
                .loginAs("locked_out_user", VALID_PASS)
                .clickLoginExpectingFailure();

        assertTrue(loginPage.isErrorDisplayed(), "Error should be shown for locked user");
        assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "Error should mention locked out");
    }

    @Test(description = "Verify empty credentials show validation error")
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyCredentialsShowError() {
        log.info("Running: testEmptyCredentialsShowError");

        LoginPage loginPage = new LoginPage(getDriver())
                .open()
                .clickLoginExpectingFailure();

        assertTrue(loginPage.isErrorDisplayed(), "Error should be displayed for empty fields");
        assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(description = "Verify successful logout redirects to login page")
    @Story("Logout")
    @Severity(SeverityLevel.NORMAL)
    public void testLogout() {
        log.info("Running: testLogout");

        LoginPage loginPage = new LoginPage(getDriver())
                .open()
                .loginAs(VALID_USER, VALID_PASS)
                .clickLogin()
                .logout();

        assertTrue(loginPage.isOnLoginPage(), "Should be redirected to login page after logout");
    }

    @Test(description = "Verify login works for multiple user types",
            dataProvider = "validUsers")
    @Story("Valid Login")
    @Severity(SeverityLevel.NORMAL)
    public void testMultipleValidUsers(String username, String expectedBehavior) {
        log.info("Running testMultipleValidUsers with user: {}", username);

        InventoryPage inventoryPage = new LoginPage(getDriver())
                .open()
                .loginAs(username, VALID_PASS)
                .clickLogin();

        assertTrue(inventoryPage.isLoaded(),
                "User '" + username + "' should reach inventory page. Expected: " + expectedBehavior);
    }

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][] {
                {"standard_user",        "Normal access, normal speed"},
                {"performance_glitch_user", "Normal access, slow performance expected"},
        };
    }
}
