package com.example.coffee_cart_app.utilityMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class testtestBeforeActualProject {
    ChromeDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 


    @BeforeMethod
    public void setUp(){
        driver.manage().deleteAllCookies();
        driver.get("https://www.google.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div:has(textarea)")));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testBrowsingAsite(){
        System.out.println("Tangina mo jephoy dizon test is working");
    }


    @Test
    public void tanginaMoJephoyDizon(){}
}
