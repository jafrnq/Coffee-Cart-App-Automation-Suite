package com.example.coffee_cart_app.utilityMethods;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class cofeeCartAppUtilityMethods {
    protected WebDriver driver;
    protected WebDriverWait wait;



    //#region GLOBAL VARIABLES================================================================================================
    protected By snackbarMessageElement = By.cssSelector(".snackbar");

    //#endregion

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
        assertTrue(driver.getTitle().equals("Coffee cart"));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    //#region SWITCH TO ()METHODS==============================================================================================================
    public WebElement switchToAndAssertModalHeader(WebElement button, String expectedModalHeader){
        button.click();
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        String modalHeadingText = modal.findElement(By.tagName("h1")).getText();
        
        assertTrue(modal.isDisplayed());
        assertEquals(modalHeadingText, expectedModalHeader,"Modal header and expected header does not match");
        return modal;
    }


    //#endregion
    //#region OTHER METHODS======================================================================================================
    
    public void waitAndAssertSnackBarMessage(By by, String expectedSnackbarText){
        WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        String snackbarText = snackbar.getText();
        assertEquals(snackbarText, expectedSnackbarText,"Snackbar message does not match with expected, MESSAGE: " + snackbarText);
    }
    
    
    public void implicitWait(int milliseconds){
        //implicitly waits with defined value
        try {
            System.out.println("Will pause for: " + milliseconds + "ms.");
            Thread.sleep(milliseconds); 
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public void implicitWait(){
        //implicityly waits for default value of 500
        implicitWait(500);
    }



    public void insertHeadiingLines(String customString){
        System.out.println("(" + customString + ")" + "=========================================================================================== \n");
    }

    public String extractTextFromString(String string){
        return string.replaceAll("[^a-zA-Z]", "").trim();
    }

    //Extracts int from string
    public int extractNumberFromString(String string){
        // return Integer.parseInt(string, )
        String cleanedString = string.replaceAll("[^\\d.]", "");
        return Integer.parseInt(cleanedString);
    }
    //Extracts float from string
    public float extractFloatFromString(String string){
        // return Integer.parseInt(string, )
        String cleanedString = string.replaceAll("[^\\d.]", "");
        return Float.parseFloat(cleanedString);
    }

    public void inputStringToField(By by, String inputString ){
        WebElement inputField = driver.findElement(by);
        inputField.sendKeys(inputString);
    }

    public void inputStringToField(WebElement inputFieldElement, String inputString ){
        inputFieldElement.sendKeys(inputString);
    }


    //#endregion
    
}
