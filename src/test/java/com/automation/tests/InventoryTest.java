package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Test suite for Inventory / Product Listing functionality.
 */
@Epic("E-Commerce")
@Feature("Product Catalog")
public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeEachTest() {
        inventoryPage = new LoginPage(getDriver())
                .open()
                .loginAs("standard_user", "secret_sauce")
                .clickLogin();
    }

    @Test(description = "Verify products are displayed on inventory page")
    @Story("Product Display")
    @Severity(SeverityLevel.BLOCKER)
    public void testProductsDisplayed() {
        assertTrue(inventoryPage.isLoaded(), "Inventory page should load");
        assertTrue(inventoryPage.getProductCount() > 0, "At least one product should be visible");
        log.info("Products found: {}", inventoryPage.getProductCount());
    }

    @Test(description = "Verify products can be sorted A-Z by name")
    @Story("Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortProductsAZ() {
        inventoryPage.sortBy("Name (A to Z)");

        List<String> names = inventoryPage.getProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            assertTrue(
                    names.get(i).compareToIgnoreCase(names.get(i + 1)) <= 0,
                    "Products should be sorted A-Z. Found: " + names.get(i) + " before " + names.get(i + 1)
            );
        }
    }

    @Test(description = "Verify products can be sorted Z-A by name")
    @Story("Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortProductsZA() {
        inventoryPage.sortBy("Name (Z to A)");

        List<String> names = inventoryPage.getProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            assertTrue(
                    names.get(i).compareToIgnoreCase(names.get(i + 1)) >= 0,
                    "Products should be sorted Z-A"
            );
        }
    }

    @Test(description = "Verify products can be sorted by price low to high")
    @Story("Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortByPriceLowToHigh() {
        inventoryPage.sortBy("Price (low to high)");

        List<Double> prices = inventoryPage.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            assertTrue(
                    prices.get(i) <= prices.get(i + 1),
                    "Prices should be ascending. Found: " + prices.get(i) + " > " + prices.get(i + 1)
            );
        }
    }

    @Test(description = "Verify adding a product updates the cart badge")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddProductToCart() {
        assertEquals(inventoryPage.getCartItemCount(), 0, "Cart should be empty initially");

        inventoryPage.addItemToCartByIndex(0);

        assertEquals(inventoryPage.getCartItemCount(), 1, "Cart badge should show 1 item");
    }

    @Test(description = "Verify multiple items can be added to the cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddMultipleItemsToCart() {
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        inventoryPage.addItemToCartByIndex(2);

        assertEquals(inventoryPage.getCartItemCount(), 3, "Cart should have 3 items");
    }

    @Test(description = "Verify cart page shows correct items")
    @Story("Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testCartContainsCorrectItems() {
        List<String> allProducts = inventoryPage.getProductNames();
        String firstProduct = allProducts.get(0);

        inventoryPage.addItemToCartByIndex(0);
        CartPage cartPage = inventoryPage.goToCart();

        assertTrue(cartPage.isLoaded(), "Cart page should load");
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain 1 item");
        assertTrue(cartPage.getCartItemNames().contains(firstProduct),
                "Cart should contain the added product: " + firstProduct);
    }
}
