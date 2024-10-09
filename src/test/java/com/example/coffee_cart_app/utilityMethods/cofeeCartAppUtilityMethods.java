package com.example.coffee_cart_app.utilityMethods;


import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    //Global Variables
    float totalOrderPrice = 0;
    //Parent divss
    public By appdiv = By.id("app");
    public By topMenu = By.cssSelector("#app ul");
    //Main menu parent elements
    public By menuItems = By.cssSelector("#app div[data-v-a9662a08]");
    public By payContainerButton = By.cssSelector("#app .pay-container button.pay"); 
    public By promoContainer = By.cssSelector("#app .promo");
    
    //Cart Page parent elements
    public By cartItems = By.cssSelector("#app div ul[data-v-8965af83]");
    





    //#region GLOBAL VARIABLES================================================================================================
    public By snackbarMessageElement = By.cssSelector(".snackbar");

    //#endregion


    //#region SETUP METHODS
    @BeforeTest
    public void beforeTest(){
        insertHeadiingLines("STARTING TEST");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }


    @BeforeMethod
    @Given("I am in the menu page of the shop")
    public void setUp(){
        insertHeadiingLines("STARTING NEW TEST");
        driver.manage().deleteAllCookies();
        driver.get("https://coffee-cart.app/");
        wait.until(ExpectedConditions.titleIs("Coffee cart"));
        assertTrue(driver.getTitle().equals("Coffee cart"));
        
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

    //#endregion

    //#region PAGE NAVIGATION ()METHODS==============================================================================================================
    public WebElement switchToAndAssertModalHeader(WebElement button, String expectedModalHeader){
        button.click();
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        String modalHeadingText = modal.findElement(By.tagName("h1")).getText();
        
        assertTrue(modal.isDisplayed());
        assertEquals(modalHeadingText, expectedModalHeader,"Modal header and expected header does not match");
        return modal;
    }
    @Given("I am in the the cart page of the shop after picking items")    
    public void navigateToCartPage(){
        
        WebElement topMenuDiv = driver.findElement(topMenu);
        WebElement cartButton = wait.until(ExpectedConditions.visibilityOf(topMenuDiv.findElement(By.cssSelector("li:nth-of-type(2)"))));
        cartButton.click();
        
        wait.until(ExpectedConditions.urlToBe("https://coffee-cart.app/cart"));
        
        verifyCartMessage();//Checks if the cart is empty
    }



    //#endregion
    //#region OTHER METHODS======================================================================================================
    @Then("The system should let me know that there's no any item in the cart")
    public void verifyCartMessage(){
        try{
            WebElement listDiv = driver.findElement(By.xpath("//div[@class='list']//p[text()='No coffee, go add some.']"));
                if(listDiv.isDisplayed()){
                    System.out.println(listDiv.getText());
                }
        } catch(NoSuchElementException e){
            insertHeadiingLines("navigateToCartPage");
            System.out.println("Orders recognized, proceed");
        }
    }
    
    @Then ("All items should be recorded in the cart and its total amount")
    public void assertCompareCartListfromPayContainerToManualOrdersList(List<String> ordersList){

        hoverOverPayContainer();
        
        List<String> itemsFromSiteCart = getItemListFromCartButton();

        assertTrue(itemsFromSiteCart.containsAll(ordersList));
        System.out.println("Cart list verified and matches the manual order list");
    }

    

    
    public void waitAndAssertSnackBarMessage(By by, String expectedSnackbarText){
        try {
        WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        String snackbarText = snackbar.getText();
        assertEquals(snackbarText, expectedSnackbarText,"Snackbar message does not match with expected, MESSAGE: " + snackbarText);
        } catch (TimeoutException e){
            System.out.println("Snackbar message not found, check validation message: \n");
        }
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
        // return string.replaceAll("[^a-zA-Zx]", "");
    }

    public String removeSpacesBetweenWords(String string){
        String formattedItem = string.replaceAll("\\s+", ""); // Remove all spaces
        return formattedItem;
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
    
    
    @Then("The promo amount should not be added to total order price")
    @Then("The promo amount should be added to total order price")
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

    //#region Pay Container Methods
    @Given("I am checking out in the cart page and filling up the Payment Details Modal")
    @Then("I should be able to check out my orders without going to the cart page")
    @Then("I should be able to check out my orders successfully")
    @Then("I should be able to checkout all of the items succesfully")
    public String performCheckOutOnPayContainer(String name, String email){
        String validationMessage = null;
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(payContainerButton)));
        WebElement paymentDetailsModal = switchToAndAssertModalHeader(checkoutButton, "Payment details");

        WebElement nameField = paymentDetailsModal.findElement(By.cssSelector("input[id='name']"));
        WebElement emailField = paymentDetailsModal.findElement(By.cssSelector("input[id='email']"));
        WebElement submitButton = paymentDetailsModal.findElement(By.id("submit-payment"));
    
        inputStringToField(nameField, name);
        inputStringToField(emailField, email);
        submitButton.click();

        try {
            validationMessage = (nameField.getAttribute("validationMessage") != null || emailField.getAttribute("validationMessage") != null) 
            ? (nameField.getAttribute("validationMessage") + emailField.getAttribute("validationMessage")).trim()
            : null;
        
            if(validationMessage == null){// Valid Scenario
                waitAndAssertSnackBarMessage(snackbarMessageElement, "Thanks for your purchase. Please check your email for payment.");
            
            } else { //Invalid Scenario
                insertHeadiingLines("performCheckOutOnPayContainer");
                System.out.println("Validation Error: " + validationMessage);
            }
        } catch (StaleElementReferenceException e){
            System.out.println("No Validation Error found as expected...");
        }
        return validationMessage;
    }

    @Then("I should be notified to fill up the email field")
    @Then("I should be notified to fill up the fields")
    public String getValidationMessage(WebElement field){
        String validationMessage = field.getAttribute("validationMessage"); 
        return validationMessage;
    }
    
    
    public void hoverOverPayContainer(){
        WebElement payContainerDiv = driver.findElement(payContainerButton);
        actions.moveToElement(payContainerDiv).perform();
    }

    public List<String> getItemListFromCartButton(){
        WebElement ordersListPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#app ul.cart-preview.show")));

        List<WebElement> cartItemsDiv = ordersListPopup.findElements(By.cssSelector("li.list-item div span:nth-of-type(1)"));
        List<String> cartItems = new ArrayList<>();

        for (WebElement item : cartItemsDiv){
            String itemName = item.getText();
            cartItems.add(itemName);
        }
        return cartItems;
    } 


    

    public List<String> getItemListFromCartPage(){
        WebElement cartItemsDiv = driver.findElement(cartItems);
        List<WebElement> cartItems= cartItemsDiv.findElements(By.cssSelector("li.list-item > div[data-v-8965af83]:nth-of-type(1)"));
        List<String> stringCartItems = new ArrayList<>();

        for (WebElement item : cartItems){
            String itemName = item.getText();
            stringCartItems.add(itemName);
        }
        return stringCartItems;
    }

    //#endregion

    //#region MENU PERFORM METHODS====================================================================================================
    @Given("I am in the the menu page of the shop after picking items")
    
    public float performAddDifferentItemsToCart(List<String> ordersList, float totalOrderPrice){
        for (String item : ordersList){
            totalOrderPrice = performAddItemToCart(item, totalOrderPrice);
        }

        System.out.println("Order total based on manual count: " + totalOrderPrice);
        return totalOrderPrice;
    }

    //Override method of addToCartFunction (Adds option to accept or reject promo)
    public float performAddDifferentItemsToCart(List<String> ordersList, float totalOrderPrice, String promoChoice){
        for (String item : ordersList){
            totalOrderPrice = performAddItemToCart(item, totalOrderPrice, promoChoice);
        }

        System.out.println("Order total based on manual count: " + totalOrderPrice);
        return totalOrderPrice;
    }

    @When("I add an item to cart multiple times")
    public float performAddSingleItemToCartMultipleTimes(String itemName, int orderCount, float totalOrderPrice ){

        for (int i = 0; i < orderCount; i++){
            totalOrderPrice =  performAddItemToCart(itemName, totalOrderPrice);
        }

        System.out.println("Successfully ordered: " + orderCount + " " + itemName);
        System.out.println("Order total based on manual count: " + totalOrderPrice);
        return totalOrderPrice;
    }
    
    //Default addtoCartFunction, rejects the promo
    @When ("I click an item")
    public float performAddItemToCart(String item, float totalOrderPrice){
        
        try {
            String stringItem = item + " ";
            WebElement itemDivName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='app']//div[@data-v-a9662a08]//h4[text()='" + stringItem + "']")));
            WebElement itemCupIconToClick = driver.findElement(By.xpath("//div[@id='app']//div[@data-v-a9662a08]//div[@class='cup']//div[@aria-label='"+item+"']"));
            float itemPrice = extractFloatFromString(itemDivName.getText());
            
            itemCupIconToClick.click();

            totalOrderPrice += itemPrice;
            System.out.println("Item added to cart: " + extractTextFromString(itemDivName.getText()));
            System.out.println("Item Price: " + itemPrice);
            System.out.println("Total spending: " + totalOrderPrice + "\n"); //To be removed once method is successfully working

            if (driver.findElement(promoContainer).isDisplayed()){
                performPromoControls("Reject", totalOrderPrice);
            }

        }catch(NoSuchElementException e){
            System.out.println("Promo Element not visible as expected...");
        }
        return totalOrderPrice;
    }

    //Override method of addToCartFunction, accepts the promo
    @When ("I click an item")
    public float performAddItemToCart(String item, float totalOrderPrice, String promoChoice){
        
        try {
            String stringItem = item + " ";
            WebElement itemDivName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='app']//div[@data-v-a9662a08]//h4[text()='" + stringItem + "']")));
            WebElement itemCupIconToClick = driver.findElement(By.xpath("//div[@id='app']//div[@data-v-a9662a08]//div[@class='cup']//div[@aria-label='"+item+"']"));
            float itemPrice = extractFloatFromString(itemDivName.getText());
            
            itemCupIconToClick.click();

            totalOrderPrice += itemPrice;
            System.out.println("Item added to cart: " + extractTextFromString(itemDivName.getText()));
            System.out.println("Item Price: " + itemPrice);
            System.out.println("Total spending: " + totalOrderPrice + "\n"); //To be removed once method is successfully working

            if (driver.findElement(promoContainer).isDisplayed()){
                totalOrderPrice =  performPromoControls(promoChoice, totalOrderPrice); //Reject or Accept
            }

        }catch(NoSuchElementException e){
            System.out.println("Promo Element not visible as expected...");
        }
        return totalOrderPrice;
    }
    //#endregion

    public int modifyOrderQuantity(String method, int quantity, String itemName){
        int quantityAfterOperation = getItemQuantity(itemName);

        //WebElement plusbutton
        //WebElement minusButton
        WebElement itemDiv = getCartItemDiv(itemName);
        WebElement itemUnitController = itemDiv.findElement(By.cssSelector("div:nth-of-type(2) div.unit-controller"));
        
        switch(method.toLowerCase()){
            case "add":
                WebElement plusButton = itemUnitController.findElement(By.cssSelector("button:nth-of-type(1)"));

                for (int i = 0; i < quantity; i++) {
                    plusButton.click();
                }

                quantityAfterOperation += quantity;
                break;
                
            case "minus":
                WebElement minusButton = itemUnitController.findElement(By.cssSelector("button:nth-of-type(2)"));

                for (int i = 0; i < quantity; i++) {
                    minusButton.click();
                }

                quantityAfterOperation -= quantity;
                break;
        }
        
        System.out.println("Order quantity after operation: " + quantityAfterOperation);
        return quantityAfterOperation;
    }


    public WebElement getCartItemDiv(String itemToFind){

        WebElement cartItemsDiv = driver.findElement(cartItems);
        List<WebElement> cartItems = cartItemsDiv.findElements(By.className("list-item"));
        WebElement cartItem = null;

        for(WebElement item : cartItems){
            String itemName = item.findElement(By.cssSelector("div:nth-of-type(1)")).getText();
            System.out.println("Item name: " + itemName);
            
            if (itemName.equals(itemToFind)){
                cartItem = item;
            }
        }
            return cartItem;
    }


    public String getPayContainerPriceText(){
        String payContainerText;
        WebElement payContainerButtonDiv = driver.findElement(payContainerButton);
        payContainerText = payContainerButtonDiv.getText();
        
        System.out.println("Curent cart container price: " + payContainerText);
        return payContainerText;
    }

    public int getItemQuantity(String itemName){

        WebElement itemDiv = getCartItemDiv(itemName);

        String itemPriceAndQuantityString = itemDiv.findElement(By.cssSelector("div:nth-of-type(2) span")).getText();

        int itemQuantity = extractQuantityValueFromString(itemPriceAndQuantityString);
        
        System.out.println("Item quantity: " + itemQuantity);

        return itemQuantity;
    } 

    public int extractQuantityValueFromString(String stringtoModify){
        String[] parts = stringtoModify.split("x");  
        String quantityPart = parts[1].trim();  
        
        return Integer.parseInt(quantityPart);  
    }

}//END OF CLASS
