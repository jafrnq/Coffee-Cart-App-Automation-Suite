package com.example.coffee_cart_app.utilityMethods;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class cofeeCartAppUtilityMethods {
    protected WebDriver driver;
    protected WebDriverWait wait;

    //Global Variables
    float totalOrderPrice = 0;
    //Parent divs of menu page
    protected By appdiv = By.id("app");
    protected By topMenu = By.cssSelector("#app ul");
    protected By menuItems = By.cssSelector("#app div[data-v-a9662a08]");
    protected By payContainerButton = By.cssSelector("#app .pay-container button.pay"); 
    protected By promoContainer = By.cssSelector("#app .promo");





    //#region GLOBAL VARIABLES================================================================================================
    protected By snackbarMessageElement = By.cssSelector(".snackbar");

    //#endregion


    //#region SETUP METHODS
    @BeforeTest
    public void beforeTest(){
        insertHeadiingLines("STARTING TEST");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @BeforeMethod
    @Given("I am in the menu page of the shop")
    public void setUp(){
        insertHeadiingLines("STARTING NEW TEST");
        driver.manage().deleteAllCookies();
        driver.get("https://coffee-cart.app/");
        wait.until(ExpectedConditions.titleIs("Coffee cart"));
        assertTrue(driver.getTitle().equals("Coffee cart"));
        ;
    }

    @AfterMethod
    public void tearDown(){
        insertHeadiingLines("Test Method completed");
        driver.manage().deleteAllCookies();
    }
    
    @AfterTest
    public void afterTest(){
        driver.quit();
    }
    //#endregion

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
    
    
    public void explicitWait(int milliseconds){
        //explicitly waits with defined value
        try {
            System.out.println("Will pause for: " + milliseconds + "ms.");
            Thread.sleep(milliseconds); 
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public void explicitWait(){
        //explicitly waits for default value of 500
        explicitWait(500);
    }



    public void insertHeadiingLines(String customString){
        System.out.println("\n(" + customString + ")" + "=========================================================================================== ");
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
    

    //#region MENU PAGE CONTROLS

    //Used for gets the paycontainerbutton text which is the total price
    public String getPayContainerPriceText(){
        String payContainerText;
        WebElement payContainerButtonDiv = driver.findElement(payContainerButton);
        payContainerText = payContainerButtonDiv.getText();
        
        System.out.println("Curent cart container price: " + payContainerText);
        return payContainerText;
    }
    
    public float performPromoControls(String performMethod, float totalOrderPrice){
        WebElement promoContainerDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(promoContainer));
        String promoText = promoContainerDiv.findElement(By.tagName("span")).getText();
        
        System.out.println("Promo Appeared =>>>");
        System.out.println(promoText); 

        switch (performMethod.toLowerCase()){
            case "accept":
                WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#app div.promo button:nth-of-type(1)"))));
                yesButton.click();
                totalOrderPrice += extractFloatFromString(promoText);
                System.out.println("PROMO ACCEPTED");
            break;
            
            case "reject":
                WebElement noButton= wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#app div.promo button:nth-of-type(2)"))));
                noButton.click();
                System.out.println("PROMO REJECTED");
            break;
            
            default:
                System.out.println("Perform Method does not match any case");
            break;
        }
        return totalOrderPrice;
    }
    @Then ("All items should be recorded in the cart with its total cost")
    public void assertTotalPriceAndTotalBasedOnSite(float totalOrderPrice, float currentPriceBasedOnSite){
        insertHeadiingLines("assertThuotalPriceAndTotalBasedOnSite");
        System.out.println("Total value based on manualCounter: " + totalOrderPrice);
        System.out.println("Total value based on site: " + currentPriceBasedOnSite);
        assertTrue(currentPriceBasedOnSite == totalOrderPrice, "TotalOrderPRice and PriceBasedOnSite does not match,");

    }
    ////#region Pay Container Methods

    public void hoverOverPayContainer(){
        WebElement payContainerDiv = driver.findElement(payContainerButton);
    }


    


}


    //#endregion
