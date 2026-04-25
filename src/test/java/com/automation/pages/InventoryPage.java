package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the Inventory/Products page.
 */
public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(css = "[data-test='product_sort_container']")
    private WebElement sortDropdown;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForUrl("inventory");
        return getCurrentUrl().contains("inventory");
    }

    public int getProductCount() {
        return inventoryItems.size();
    }

    public List<String> getProductNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return itemPrices.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public InventoryPage sortBy(String option) {
        log.info("Sorting products by: {}", option);
        selectByText(sortDropdown, option);
        return this;
    }

    public InventoryPage addItemToCartByIndex(int index) {
        String itemName = itemNames.get(index).getText();
        log.info("Adding item to cart: {}", itemName);
        // Each item has an "Add to cart" button sibling
        WebElement addBtn = inventoryItems.get(index)
                .findElement(org.openqa.selenium.By.cssSelector("[data-test^='add-to-cart']"));
        click(addBtn);
        return this;
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        log.info("Navigating to cart");
        click(cartIcon);
        return new CartPage(driver);
    }

    public LoginPage logout() {
        click(menuButton);
        WaitUtils.waitForClickable(driver, logoutLink);
        click(logoutLink);
        return new LoginPage(driver);
    }
}
