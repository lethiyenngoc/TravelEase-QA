package com.travelease.listeners;

import com.travelease.base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        Object testInstance = result.getInstance();

        if (!(testInstance instanceof BaseTest)) {
            return;
        }

        WebDriver driver = ((BaseTest) testInstance).getDriver();

        if (driver == null) {
            return;
        }

        try {

            // Chụp screenshot dạng file
            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            // Tạo folder screenshots nếu chưa có
            File screenshotDirectory = new File("screenshots");

            if (!screenshotDirectory.exists()) {
                screenshotDirectory.mkdirs();
            }

            // Tạo timestamp
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
            );

            // Tên screenshot
            String fileName =
                    result.getMethod().getMethodName()
                            + "_"
                            + timestamp
                            + ".png";

            File destination =
                    new File(screenshotDirectory, fileName);

            // Lưu screenshot vào folder screenshots
            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Screenshot saved: "
                            + destination.getAbsolutePath()
            );

            // Attach screenshot vào Allure Report
            byte[] screenshotBytes =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Failure Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshotBytes),
                    ".png"
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save screenshot: "
                            + e.getMessage()
            );
        }
    }
}