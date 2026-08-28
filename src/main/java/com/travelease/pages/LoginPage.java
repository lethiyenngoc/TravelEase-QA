package com.travelease.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button.inner-button");
    private By logoutButton = By.cssSelector("a.inner-logout");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLoginPage() {
        driver.get("http://localhost:3000/admin");

        wait.until(
                ExpectedConditions.urlContains("/admin/account/login")
        );
    }

    public void enterEmail(String email) {
        WebElement emailField =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        emailInput
                ));

        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        passwordInput
                ));

        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement button =
                wait.until(ExpectedConditions.elementToBeClickable(
                        loginButton
                ));

        button.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(
                    ExpectedConditions.urlContains("/admin/dashboard")
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl()
                .contains("/admin/account/login");
    }

    public String getAlertMessage() {
        return wait.until(
                ExpectedConditions.alertIsPresent()
        ).getText();
    }

    public void acceptAlert() {
        wait.until(
                ExpectedConditions.alertIsPresent()
        ).accept();
    }

    public void clickLogout() {
        WebElement logout =
                wait.until(ExpectedConditions.elementToBeClickable(
                        logoutButton
                ));

        logout.click();
    }

    public void openCategoryManagement() {
        driver.get("http://localhost:3000/admin/category/list");
    }

    public boolean isOnCategoryManagementPage() {
        return driver.getCurrentUrl().contains("/admin/category/list");
    }
}