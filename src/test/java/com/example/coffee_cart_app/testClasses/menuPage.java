package com.example.coffee_cart_app.testClasses;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.example.coffee_cart_app.utilityMethods.baseTest;

import static org.testng.Assert.assertTrue;


import java.util.List;
import java.util.Arrays;

import org.openqa.selenium.By;


public class menuPage extends baseTest{
    //Global Variables
    float totalOrderPrice = 0;

    @AfterMethod
    public void tearDown() {
        insertHeadingLines("Test Method completed");
        driver.manage().deleteAllCookies();
        // driver.get("https://coffee-cart.app/");
        totalOrderPrice = 0;

    }

    ////#region TEST METHODS=======================================================================================
    @Test //Done and working
    public void testMenuPageElementsVisibility(){
        
        assertMenuPageElements();
    }

    @Test //Done and working
    public void pickASingleItem(){ 

        totalOrderPrice = performAddItemToCart("Cappuccino", totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());

        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    }

    @Test //Done and working
    public void pickMultipleOfTheSameItem(){
        
        totalOrderPrice = performAddSingleItemToCartMultipleTimes("Mocha", 5, totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());

        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    }

    @Test //Done and working
    public void pickDifferentItems(){
        
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");

        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
        assertCompareCartListfromPayContainerToManualOrdersList(preDefinedOrdersList);
    }

    @Test //Done and working
    public void acceptPromo(){
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte");
        
        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice, "Accept");
        
        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
        assertCompareCartListfromPayContainerToManualOrdersList(preDefinedOrdersList);
    }

    @Test 
    public void rejectPromo(){
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte");

        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice, "Reject");

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
        assertCompareCartListfromPayContainerToManualOrdersList(preDefinedOrdersList);
   }

   @Test
   public void addAllItemsToCart(){

    List<String> allMenuItems= Arrays.asList("Espresso", "Espresso Macchiato", "Cappuccino", "Mocha", "Flat White", "Americano", "Cafe Latte", "Cafe Breve", "Espresso Con Panna");

    totalOrderPrice = performAddDifferentItemsToCart(allMenuItems, totalOrderPrice);

    float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
    assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    assertCompareCartListfromPayContainerToManualOrdersList(allMenuItems);
   }

    //#endregion
    //#region ASSERT METHODS==============================================================================================================
    public void assertMenuPageElements(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(appdiv));
        assertTopMenuBar(driver.findElement(topMenu)); 
        assertMenuItems(driver.findElement(menuItems)); 
        assertBuyButton(driver.findElement(payContainerButton));
    }   

    public void assertTopMenuBar(WebElement topMenuBar){
        insertHeadingLines("Starting assertMenuTopBar");
        List<String> expectedTopMenuBarElements = Arrays.asList("menu", "cart", "github");
        List <WebElement> topBarHeaderElements = topMenuBar.findElements(By.tagName("li"));
        
        for (WebElement element : topBarHeaderElements){
            wait.until(ExpectedConditions.elementToBeClickable(element));
            String  elementText = extractTextFromString(element.findElement(By.tagName("a")).getText());
            assertTrue(expectedTopMenuBarElements.contains(elementText), "Element not found: " + elementText);
        }
        System.out.println("All top menu bar elements Verified");
    }

    public void assertMenuItems(WebElement menuItemsDiv){
        insertHeadingLines("Starting assertMenuItems");
        List<String> expectedMenuItems = Arrays.asList("Espresso", "EspressoMacchiato", "Cappuccino", "Mocha", "FlatWhite", "Americano", "CafeLatte", "CafeBreve", "EspressoConPanna");
        List<Float> expectedItemPrice = Arrays.asList(10.00f, 12.00f, 19.00f, 8.00f, 18.00f, 7.00f, 16.00f, 14.00f, 15.00f);
        
        List<WebElement> menuItems = menuItemsDiv.findElements(By.cssSelector("li[data-v-a9662a08]"));

        for (WebElement item : menuItems){
            String itemDescription = item.findElement(By.tagName("h4")).getText();

            WebElement cupIconToClick = wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.className("cup"))));
            String itemName = extractTextFromString(itemDescription);
            float itemPrice = extractFloatFromString(itemDescription);

            assertTrue(cupIconToClick.isDisplayed(),"Cup icon is not visible..." + itemName);
            assertTrue(expectedMenuItems.contains(itemName), "Element not found: " + itemName);
            assertTrue(expectedItemPrice.contains(itemPrice), "Price not found in expected lsit" + itemPrice);
            System.out.println("Item name: " + itemName); 
            System.out.println("Item price: " + itemPrice + "\n");
            System.out.println("All Menu Items Verified ");
            
        }
    }

    public void assertBuyButton(WebElement payContainer){
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(payContainer));
        //Clicks and asserts the popup modal
        WebElement paymentDetailsModal = switchToAndAssertModalHeader(checkoutButton, "Payment details");

        //asserting modal elements 
        WebElement nameField = paymentDetailsModal.findElement(By.cssSelector("input[id='name']"));
        WebElement emailField = paymentDetailsModal.findElement(By.cssSelector("input[id='email']"));
        // WebElement promotionCheckbox = paymentDetailsModal.findElement(By.cssSelector("div input[id='promotion']"));
        WebElement promotionCheckbox = paymentDetailsModal.findElement(By.cssSelector("div[aria-label='Promotion agreement']"));
        WebElement submitButton = paymentDetailsModal.findElement(By.id("submit-payment"));
        assertTrue(nameField.isDisplayed() 
                && emailField.isDisplayed() 
                && promotionCheckbox.isDisplayed()
                && submitButton.isDisplayed(), "Payment modal elements not found");

        inputStringToField(nameField, "John Doe");
        inputStringToField(emailField, "exampleEmail@gmail.com");
        promotionCheckbox.click();
        submitButton.click();
        waitAndAssertSnackBarMessage(snackbarMessageElement, "Thanks for your purchase. Please check your email for payment.");

    }

    
    
}
