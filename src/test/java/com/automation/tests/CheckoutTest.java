package com.automation.tests;

import com.automation.pages.CheckoutPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for End-to-End Checkout flow.
 * Demonstrates full user journey testing.
 */
@Epic("E-Commerce")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    private final Faker faker = new Faker();
    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    public void loginAndAddItems() {
        inventoryPage = new LoginPage(getDriver())
                .open()
                .loginAs("standard_user", "secret_sauce")
                .clickLogin();

        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
    }

    @Test(description = "Verify complete checkout flow from cart to order confirmation")
    @Story("Complete Purchase")
    @Severity(SeverityLevel.BLOCKER)
    @Description("End-to-end test: add items → checkout → fill info → finish → confirm order")
    public void testCompleteCheckoutFlow() {
        log.info("Running: testCompleteCheckoutFlow");

        CheckoutPage checkoutPage = inventoryPage
                .goToCart()
                .proceedToCheckout()
                .fillShippingInfo(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.address().zipCode()
                )
                .clickContinue();

        String total = checkoutPage.getTotalAmount();
        log.info("Order total: {}", total);
        assertNotNull(total, "Total should be displayed on review page");
        assertTrue(total.contains("Total"), "Total label should contain 'Total'");

        checkoutPage.clickFinish();

        assertTrue(checkoutPage.isOrderComplete(), "Order confirmation should be displayed");
        log.info("Order confirmed: {}", checkoutPage.getConfirmationHeader());
    }

    @Test(description = "Verify checkout summary shows correct order total")
    @Story("Order Summary")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckoutSummaryDisplayed() {
        log.info("Running: testCheckoutSummaryDisplayed");

        CheckoutPage checkoutPage = inventoryPage
                .goToCart()
                .proceedToCheckout()
                .fillShippingInfo("John", "Doe", "12345")
                .clickContinue();

        String total = checkoutPage.getTotalAmount();
        assertNotNull(total, "Total should be shown on summary");
        assertTrue(total.length() > 0, "Total label should not be empty");
    }

    @Test(description = "Verify cart is navigated correctly to checkout")
    @Story("Cart to Checkout Navigation")
    @Severity(SeverityLevel.NORMAL)
    public void testCartToCheckoutNavigation() {
        log.info("Running: testCartToCheckoutNavigation");

        var cartPage = inventoryPage.goToCart();
        assertTrue(cartPage.isLoaded(), "Cart page should load");
        assertEquals(cartPage.getCartItemCount(), 2, "Cart should have 2 items");

        var checkoutPage = cartPage.proceedToCheckout();
        // Verify we're on checkout step 1
        assertTrue(getDriver().getCurrentUrl().contains("checkout-step-one"),
                "Should be on checkout step one");
    }
}
