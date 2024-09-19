package com.example.coffee_cart_app.utilityMethods;


import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


public class cofeeCartAppUtilityMethods {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeTest
    public void beforeTest(){
        insertHeadiingLines("STARTING TEST");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @BeforeMethod
    public void setUp(){
        insertHeadiingLines("STARTING NEW TEST");
        driver.manage().deleteAllCookies();
        driver.get("https://coffee-cart.app/");
        wait.until(ExpectedConditions.titleIs("Coffee cart"));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    //#region OTHER METHODS
    public void insertHeadiingLines(String customString){
        System.out.println("(" + customString + ")" + "=========================================================================================== \n");
    }

    //#endregion
    
}
