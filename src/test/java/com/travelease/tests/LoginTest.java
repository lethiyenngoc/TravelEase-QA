package com.travelease.tests;

import com.travelease.base.BaseTest;
import com.travelease.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    private final String adminEmail = System.getenv("TRAVELEASE_ADMIN_EMAIL");
    private final String adminPassword = System.getenv("TRAVELEASE_ADMIN_PASSWORD");

    private final String inactiveAdminEmail = System.getenv("TRAVELEASE_INACTIVE_EMAIL");
    private final String inactiveAdminPassword = System.getenv("TRAVELEASE_INACTIVE_PASSWORD");

    private final String restrictedAdminEmail = System.getenv("TRAVELEASE_RESTRICTED_EMAIL");
    private final String restrictedAdminPassword = System.getenv("TRAVELEASE_RESTRICTED_PASSWORD");

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void loginWithValidCredentials() {

        // AUTO-AUTH-004

//        String email = "...";
//        String password = "...";
//        loginPage.login(email, password);
        loginPage.login(adminEmail, adminPassword);

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Admin should be redirected to dashboard after successful login"
        );
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void loginWithIncorrectPassword() {

        // AUTO-AUTH-005

        String wrongPassword = "Wrong@123";

        loginPage.login(adminEmail, wrongPassword);

        // Kiểm tra nội dung alert
        String alertMessage = loginPage.getAlertMessage();

        Assert.assertEquals(
                alertMessage,
                "Mật khẩu không đúng!",
                "Incorrect password alert message should be displayed"
        );

        // Đóng alert
        loginPage.acceptAlert();

        // Kiểm tra vẫn ở trang Login
        Assert.assertTrue(
                loginPage.isOnLoginPage(),
                "Admin should remain on login page when password is incorrect"
        );
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void loginWithInactiveAccount() {

        // AUTO-AUTH-006

        loginPage.login(inactiveAdminEmail, inactiveAdminPassword);

        String alertMessage = loginPage.getAlertMessage();

        Assert.assertEquals(
                alertMessage,
                "Tài khoản chưa được kích hoạt!",
                "Inactive account alert message should be displayed"
        );

        loginPage.acceptAlert();

        Assert.assertTrue(
                loginPage.isOnLoginPage(),
                "Inactive Admin should remain on login page"
        );
    }

//    @Test(enabled = false)
    @Test(groups = "regression")
    public void logoutAndCannotAccessDashboardAgain() {

        // AUTO-AUTH-007

        loginPage.login(adminEmail, adminPassword);

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Admin should login successfully before logout"
        );

        // Logout
        loginPage.clickLogout();

        Assert.assertTrue(
                loginPage.isOnLoginPage(),
                "Admin should be redirected to login page after logout"
        );

        // Try accessing dashboard again
        driver.get("http://localhost:3000/admin/dashboard");

        Assert.assertTrue(
                loginPage.isOnLoginPage(),
                "Admin should not access dashboard after logout"
        );
    }

    @Test(groups = "known-bug")
    public void restrictedAdminCannotAccessCategoryManagement() {

        // AUTO-RBAC-010

        // Login with restricted Admin account
        loginPage.login(restrictedAdminEmail, restrictedAdminPassword);

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Restricted admin should be able to login successfully"
        );

        // Directly access a page outside assigned permission
        loginPage.openCategoryManagement();

        // User should NOT be allowed to access Category Management
        Assert.assertFalse(
                loginPage.isOnCategoryManagementPage(),
                "BUG-002: Restricted admin can access Category Management directly by URL"
        );
    }
}