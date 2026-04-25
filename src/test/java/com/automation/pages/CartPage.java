package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the Shopping Cart page.
 */
public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForUrl("cart");
        return getCurrentUrl().contains("cart");
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public List<String> getCartItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public double getTotalPrice() {
        return itemPrices.stream()
                .mapToDouble(e -> Double.parseDouble(e.getText().replace("$", "")))
                .sum();
    }

    public CheckoutPage proceedToCheckout() {
        log.info("Proceeding to checkout");
        click(checkoutButton);
        return new CheckoutPage(driver);
    }

    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        return new InventoryPage(driver);
    }
}
