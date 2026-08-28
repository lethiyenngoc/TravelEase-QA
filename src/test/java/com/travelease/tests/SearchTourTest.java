package com.travelease.tests;

import com.travelease.base.BaseTest;
import com.travelease.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTourTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        homePage = new HomePage(driver);
        homePage.openHomePage();
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void searchTourWithValidKeyword() {

        // AUTO-SEARCH-001
        String keyword = "Sapa";

        homePage.searchTour(keyword);

        Assert.assertTrue(
                homePage.hasTourContaining(keyword),
                "At least one returned tour should contain keyword: " + keyword
        );
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void searchTourWithNonExistingKeyword() {

        // AUTO-SEARCH-002
        String keyword = "xyznotfound123";

        homePage.searchTour(keyword);

        Assert.assertTrue(
                homePage.hasNoTourResults(),
                "Search result should not contain any tours for keyword: " + keyword
        );
    }

//    @Test(enabled = false)
    @Test(groups = "known-bug")
    public void selectTourFromSearchSuggestion() {

        // AUTO-SEARCH-003
        String keyword = "Sapa";

        String suggestion =
                "Sapa – Bản Cát Cát – Fansipan (3N2Đ)";

        // 1. Nhập keyword
        homePage.typeSearchKeyword(keyword);

        // 2. Chọn tour trong suggestion
        homePage.selectSuggestion(suggestion);

        // 3. Bấm Tìm Kiếm
        homePage.clickSearchButton();

        // 4. Verify tour được hiển thị
        Assert.assertTrue(
                homePage.hasTourResults(),
                "BUG-001: No tour is displayed after selecting search suggestion: "
                        + suggestion
        );
    }
}