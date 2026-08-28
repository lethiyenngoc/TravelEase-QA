package com.travelease.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By addToCartButton =
            By.cssSelector("button.inner-button-add-cart");

    private By cartTourTitle =
            By.cssSelector("div.inner-product div.inner-title a");

    private By adultQuantityInput =
            By.cssSelector("input[input-quantity='quantityAdult']");

    private By cartTotal =
            By.cssSelector("span[cart-total]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openTourDetailPage() {
        driver.get(
                "http://localhost:3000/tour/detail/tp-hcm-phan-thiet-mui-ne-doi-cat-bay-2n1d"
        );
    }

    public void clickAddToCart() {
        WebElement button =
                wait.until(ExpectedConditions.elementToBeClickable(
                        addToCartButton
                ));

        button.click();
    }

    public String getCartTourTitle() {
        WebElement title =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        cartTourTitle
                ));

        return title.getText();
    }

    public boolean isTourAddedToCart(String expectedTourName) {
        return getCartTourTitle().contains(expectedTourName);
    }

    public String getCartTotal() {
        WebElement total =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        cartTotal
                ));

        return total.getText().trim();
    }

    public void changeAdultQuantity(int quantity) {

        WebElement input =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        adultQuantityInput
                ));

        input.clear();
        input.sendKeys(String.valueOf(quantity));
    }

    public String getAdultQuantity() {
        WebElement input =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        adultQuantityInput
                ));

        return input.getAttribute("value");
    }
}