package com.example.coffee_cart_app.testClasses;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.example.coffee_cart_app.utilityMethods.baseTest;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class cartPage extends baseTest{
    //Global Variables
    float totalOrderPrice = 0;

    @AfterMethod
    public void tearDown() {
        insertHeadingLines("Test Method completed");
        driver.manage().deleteAllCookies();
        totalOrderPrice = 0;

    }


    //TEST METHODS=================================================================================
    @Test
    public void assertCartPageElements(){//Done working
        totalOrderPrice = performAddItemToCart("Mocha", totalOrderPrice);

        navigateToCartPage();

        performAssertCartPageElements();
        
        System.out.println("Cart page elements verified");
    }

    @Test 
    public void checkOutOrdersUsingCheckoutPage(){
        List<String> ordersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        totalOrderPrice = performAddDifferentItemsToCart(ordersList, totalOrderPrice);

        navigateToCartPage();

        assertCompareCartListFromSiteToManualOrdersList(ordersList);    
        
        performCheckOutOnPayContainer("John Doe", "exampleemail@gmailcom");
        
        System.out.println("Checking out using cart page verified");
    }

    @Test
    public void checkOutOrdersUsingPayContainerButton(){
        List<String> ordersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        
        totalOrderPrice = performAddDifferentItemsToCart(ordersList, totalOrderPrice);
        
        hoverOverPayContainer();

        assertCompareCartListfromPayContainerToManualOrdersList(ordersList);    

        performCheckOutOnPayContainer("John Doe", "exampleemail@gmailcom");
    }

    @Test
    public void checkOutWithNoItemInCart(){
        navigateToCartPage(); //Automatically locates the No Order message
    }

    @Test
    public void addToCartThenIncreaseQuantity(){

        String itemToOrder = "Mocha";
        totalOrderPrice = performAddSingleItemToCartMultipleTimes(itemToOrder, 10, totalOrderPrice);

        navigateToCartPage();

        String preTotalPrice= getPayContainerPriceText();
    
        //modify Order Quantity
        int postItemQuantity = modifyOrderQuantity("add", 10, itemToOrder);
        String postTotalPrice= getPayContainerPriceText();
        
        assertItemQuantity(itemToOrder, postItemQuantity);
        assertTrue(!preTotalPrice.equals(postTotalPrice), "The total price did not change");
    }

    @Test
    public void addToCartThenDecreaseQuantity(){
        String itemToOrder = "Cafe Latte";
        totalOrderPrice = performAddSingleItemToCartMultipleTimes(itemToOrder, 20, totalOrderPrice);
        
        navigateToCartPage();

        String preTotalPrice= getPayContainerPriceText();
    
        //modify Order Quantity
        int postItemQuantity = modifyOrderQuantity("minus", 10, itemToOrder);
        String postTotalPrice= getPayContainerPriceText();
        
        assertItemQuantity(itemToOrder, postItemQuantity);
        assertTrue(!preTotalPrice.equals(postTotalPrice), "The total price did not change");

    }

    @Test
    public void checkOutAllItemsInMenu(){

        List<String> allMenuItems= Arrays.asList("Espresso", "Espresso Macchiato", "Cappuccino", "Mocha", "Flat White", "Americano", "Cafe Latte", "Cafe Breve", "Espresso Con Panna");

        totalOrderPrice = performAddDifferentItemsToCart(allMenuItems, totalOrderPrice);

        navigateToCartPage();

        performCheckOutOnPayContainer("John Doe", "example123@gmail.com");
        
        System.out.println("Checking out all items verified");
    }

    @Test
    public void checkOutWithEmptyPaymentDetails(){

        performAddItemToCart("Mocha", totalOrderPrice);

        navigateToCartPage();

        String validationMesasge = performCheckOutOnPayContainer("", "");
        
        assertTrue(validationMesasge.equals("Please fill out this field.Please fill out this field."));
    }

    @Test
    public void checkOutWithIncompletePaymentDetails(){

        performAddItemToCart("Mocha", totalOrderPrice);

        navigateToCartPage();
        
        String validationMesasge =  performCheckOutOnPayContainer("John", "sampleEmail");
        
        assertTrue(validationMesasge.equals("Please include an '@' in the email address. 'sampleEmail' is missing an '@'."));
    }


    //#region PERFORM METHODS===================================================================
    public void performAssertCartPageElements(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(appdiv));
        assertTopMenuBar(driver.findElement(topMenu)); 
        assertCartItems(driver.findElement(cartItems)); 
        assertBuyButton(driver.findElement(payContainerButton));

    }

    //#endregion
    //#region ASSERT METHODS===================================================================
    public void assertCompareCartListFromSiteToManualOrdersList(List<String> orderList){
        List<String> cartItemsfromCartPage= getItemListFromCartPage();

        insertHeadingLines("assertCompareCartListFromSiteToManualOrdersList");
        System.out.println("Items from page: " + cartItemsfromCartPage);
        System.out.println("Items from orderList" + orderList);
        
        assertTrue(cartItemsfromCartPage.containsAll(orderList));
        System.out.println("Cart list verified and matches the manual order list");
    }

    public void assertCartItems(WebElement cartMenuItems){

        List<WebElement> itemsInCart = cartMenuItems.findElements(By.cssSelector("li.list-item"));
        for (WebElement item : itemsInCart){

            WebElement removeItemButton = wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div:nth-of-type(4)"))));
            WebElement unitEditor = wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div:nth-of-type(2) div.unit-controller"))));
            assertTrue(removeItemButton.isDisplayed() && unitEditor.isDisplayed(), "RemoveItem and UnitEditor buttons are not visible...");            

            String itemName = item.findElement(By.cssSelector("div:nth-of-type(1)")).getText();
            String itemPrice = item.findElement(By.className("unit-desc")).getText();
            String itemTotalPrice = item.findElement(By.cssSelector("div:nth-of-type(3)")).getText();         

            System.out.println("Items in cart");
            System.out.println("Item: " + itemName + ", Price and quantity: " + itemPrice + ", TOTAL: " + itemTotalPrice);
        }
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

    public void assertBuyButton(WebElement payContainer){
        performCheckOutOnPayContainer("John Doe", "exampleemail@gmailcom");
    }

    public void assertItemQuantity(String itemName, int actualQuantity){
        assertTrue(actualQuantity == getItemQuantity(itemName), "Item quantity did not match");
    }



}
