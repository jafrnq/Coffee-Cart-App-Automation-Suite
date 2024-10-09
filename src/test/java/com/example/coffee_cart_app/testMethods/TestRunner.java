package com.example.coffee_cart_app.testMethods;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;

import io.cucumber.java.en.Given;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (
    features = "src/test/resources",
    glue = "com.example.coffee_cart_app.testMethods", 
    monochrome = true
)


public class TestRunner extends AbstractTestNGCucumberTests {
    cofeeCartAppUtilityMethods utilityMethods = new cofeeCartAppUtilityMethods();
        WebDriver driver; // Declare WebDriver
        WebDriverWait wait; // Declare WebDriverWait
        Actions actions; // Declare Actions

        float totalOrderPrice = 0;

    //#region SETUP METHODS
    @BeforeTest
    public void beforeTest(){
        utilityMethods.insertHeadiingLines("STARTING TEST");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @BeforeMethod
    @Given("I am in the menu page of the shop")
    public void setUp(){
        utilityMethods.insertHeadiingLines("STARTING NEW TEST");
        driver.manage().deleteAllCookies();
        driver.get("https://coffee-cart.app/");
        wait.until(ExpectedConditions.titleIs("Coffee cart"));
        assertTrue(driver.getTitle().equals("Coffee cart"));
        
    }
    @AfterMethod
    public void tearDown(){
        utilityMethods.insertHeadiingLines("Test Method completed");
        
        driver.manage().deleteAllCookies();
        
        totalOrderPrice = 0;
    }


    @AfterTest
    public void afterTest(){
        driver.quit();
    }

}
