package com.travelease.tests;

import com.travelease.base.BaseTest;
import com.travelease.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private CartPage cartPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        cartPage = new CartPage(driver);
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void addTourToCartSuccessfully() throws InterruptedException{

        // AUTO-CART-008
        String expectedTour =
                "TP.HCM – Phan Thiết – Mũi Né – Đồi Cát Bay (2N1Đ)";

        cartPage.openTourDetailPage();
        Thread.sleep(3000);

        cartPage.clickAddToCart();
        Thread.sleep(5000);

        Assert.assertTrue(
                cartPage.isTourAddedToCart(expectedTour),
                "Selected tour should be displayed in the cart"
        );
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void updateTotalPriceWhenQuantityChanges()
            throws InterruptedException {

        // AUTO-CART-009

        cartPage.openTourDetailPage();

        // Add tour to cart
        cartPage.clickAddToCart();

        Thread.sleep(2000);

        // Total trước khi thay đổi quantity
        String totalBefore = cartPage.getCartTotal();

        // Đổi Adult từ 1 → 2
        cartPage.changeAdultQuantity(2);

        Thread.sleep(3000);

        // Verify quantity
        Assert.assertEquals(
                cartPage.getAdultQuantity(),
                "2",
                "Adult quantity should be updated to 2"
        );

        // Total sau khi thay đổi quantity
        String totalAfter = cartPage.getCartTotal();

        Assert.assertEquals(
                totalAfter,
                "4.300.000",
                "Total price should be 4.300.000đ when adult quantity is 2"
        );
    }
}