package com.travelease.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchInput = By.name("locationTo");
    private By tourTitles = By.cssSelector("h3.inner-title a");
    private By searchSuggestions = By.cssSelector("div.inner-suggest div.inner-item");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openHomePage() {
        driver.get("http://localhost:3000");
    }

    public void searchTour(String keyword) {

        WebElement input =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        searchInput
                ));

        input.clear();
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
    }

    public List<WebElement> getTourTitles() {

        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        tourTitles
                )
        );
    }

    public boolean hasTourContaining(String keyword) {

        return getTourTitles()
                .stream()
                .anyMatch(tour ->
                        tour.getText()
                                .toLowerCase()
                                .contains(keyword.toLowerCase())
                );
    }

    public boolean hasNoTourResults() {

        List<WebElement> tours = driver.findElements(tourTitles);

        return tours.isEmpty();
    }

    public void selectSuggestion(String suggestionText) {

        By suggestion =
                By.cssSelector(
                        "div.inner-item[data-value='" + suggestionText + "']"
                );

        WebElement suggestionItem =
                wait.until(ExpectedConditions.elementToBeClickable(
                        suggestion
                ));

        suggestionItem.click();
    }

    public void typeSearchKeyword(String keyword) {

        WebElement input =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        searchInput
                ));

        input.clear();
        input.sendKeys(keyword);
    }

    public void clickSearchButton() {

        By searchButton =
                By.cssSelector("button.inner-button[type='submit']");

        WebElement button =
                wait.until(ExpectedConditions.elementToBeClickable(
                        searchButton
                ));

        button.click();
    }

    public boolean hasTourResults() {
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(tourTitles)
            );
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}