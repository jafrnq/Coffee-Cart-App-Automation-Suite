package com.example.coffee_cart_app.testMethods;

import org.testng.annotations.Test;

import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;



public class cartPage extends cofeeCartAppUtilityMethods {
    //Global Variables
    float totalOrderPrice = 0;

    //Setup Methods
    @Override
    @AfterMethod
    public void tearDown(){
        insertHeadiingLines("Test Method completed");
        driver.manage().deleteAllCookies();
        totalOrderPrice = 0;
    }


    //TEST METHODS=================================================================================
    @Test
    @When("I observe the elements in the page")
    public void assertCartPageElements(){//Done working
        totalOrderPrice = performAddItemToCart("Mocha", totalOrderPrice);

        navigateToCartPage();

        performAssertCartPageElements();
        
        System.out.println("Cart page elements verified");
    }

    @Test 
    @When("I try to checkout my items in the cart page")
    public void checkOutOrdersUsingCheckoutPage(){
        List<String> ordersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        totalOrderPrice = performAddDifferentItemsToCart(ordersList, totalOrderPrice);

        navigateToCartPage();

        assertCompareCartListFromSiteToManualOrdersList(ordersList);    
        
        performCheckOutOnPayContainer(driver.findElement(payContainerButton));
        
        System.out.println("Checking out using cart page verified");
    }

    @Test
    @When("I try to checkout using the payContainer div")
    public void checkOutOrdersUsingPayContainerButton(){
        List<String> ordersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        
        totalOrderPrice = performAddDifferentItemsToCart(ordersList, totalOrderPrice);
        
        hoverOverPayContainer();

        assertCompareCartListfromPayContainerToManualOrdersList(ordersList);    

        performCheckOutOnPayContainer(driver.findElement(payContainerButton));
    }

    @Test
    @When("I try to check out without adding any item to cart")
    public void checkOutWithNoItemInCart(){
        navigateToCartPage(); //Automatically locates the No Order message
    }

    @Test
    @When("I try to increase the quantity of the item")
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
    @When("I try to decrease the quantity of the item")
    public void addToCartThenDecreaseQuantity(){
        String itemToOrder = "Cafe Latte";
        totalOrderPrice = performAddSingleItemToCartMultipleTimes(itemToOrder, 20, totalOrderPrice);
        
        navigateToCartPage();

        String preTotalPrice= getPayContainerPriceText();
    
        //modify Order Quantity
        int postItemQuantity = modifyOrderQuantity("minus", 10, itemToOrder);
        String postTotalPrice= getPayContainerPriceText();
        
        // @Then("The changes should be reflected in the item count and total price")
        assertItemQuantity(itemToOrder, postItemQuantity);
        assertTrue(!preTotalPrice.equals(postTotalPrice), "The total price did not change");

    }

    @Test
    @When("I try to add all items in cart and check them out")
    public void checkOutAllItemsInMenu(){

        List<String> allMenuItems= Arrays.asList("Espresso", "Espresso Macchiato", "Cappuccino", "Mocha", "Flat White", "Americano", "Cafe Latte", "Cafe Breve", "Espresso Con Panna");

        totalOrderPrice = performAddDifferentItemsToCart(allMenuItems, totalOrderPrice);

        navigateToCartPage();

        performCheckOutOnPayContainer(driver.findElement(payContainerButton));
        
        System.out.println("Checking out all items verified");
    }





    //#region PERFORM METHODS===================================================================
    @Then("The cart page elements should be visible")
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

        insertHeadiingLines("assertCompareCartListFromSiteToManualOrdersList");
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
        insertHeadiingLines("Starting assertMenuTopBar");
        List<String> expectedTopMenuBarElements = Arrays.asList("menu", "cart", "github");
        List <WebElement> topBarHeaderElements = topMenuBar.findElements(By.tagName("li"));
        
        for (WebElement element : topBarHeaderElements){
            wait.until(ExpectedConditions.elementToBeClickable(element));
            String  elementText = extractTextFromString(element.findElement(By.tagName("a")).getText());
            assertTrue(expectedTopMenuBarElements.contains(elementText), "Element not found: " + elementText);
        }
        System.out.println("All top menu bar elements Verified");
    }

    @When("I observe the elements in the page")
    public void assertBuyButton(WebElement payContainer){
        performCheckOutOnPayContainer(payContainer);
    }

    @Then("The changes should be reflected in the item count")
    public void assertItemQuantity(String itemName, int actualQuantity){
        assertTrue(actualQuantity == getItemQuantity(itemName), "Item quantity did not match");
    }



}
