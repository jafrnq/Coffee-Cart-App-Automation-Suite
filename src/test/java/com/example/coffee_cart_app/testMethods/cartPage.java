package com.example.coffee_cart_app.testMethods;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;



public class cartPage{
    cofeeCartAppUtilityMethods utilityMethods = new cofeeCartAppUtilityMethods();
    //Global Variables
    WebDriver driver; // Declare WebDriver
    WebDriverWait wait; // Declare WebDriverWait
    float totalOrderPrice = 0;

    @BeforeMethod
    public void setUp(){
        utilityMethods.setUp();
    }

    @AfterMethod
    public void tearDown(){
        utilityMethods.tearDown();
    }

    //TEST METHODS=================================================================================
    @Test
    @When("I observe the elements in the page")
    public void assertCartPageElements(){//Done working
        totalOrderPrice = utilityMethods.performAddItemToCart("Mocha", totalOrderPrice);

        utilityMethods.navigateToCartPage();

        performAssertCartPageElements();
        
        System.out.println("Cart page elements verified");
    }

    @Test 
    @When("I try to checkout my items in the cart page")
    public void checkOutOrdersUsingCheckoutPage(){
        List<String> ordersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        totalOrderPrice = utilityMethods.performAddDifferentItemsToCart(ordersList, totalOrderPrice);

        utilityMethods.navigateToCartPage();

        assertCompareCartListFromSiteToManualOrdersList(ordersList);    
        
        utilityMethods.performCheckOutOnPayContainer("John Doe", "exampleemail@gmailcom");
        
        System.out.println("Checking out using cart page verified");
    }

    @Test
    @When("I try to checkout using the payContainer div")
    public void checkOutOrdersUsingPayContainerButton(){
        List<String> ordersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        
        totalOrderPrice = utilityMethods.performAddDifferentItemsToCart(ordersList, totalOrderPrice);
        
        utilityMethods.hoverOverPayContainer();

        utilityMethods.assertCompareCartListfromPayContainerToManualOrdersList(ordersList);    

        utilityMethods.performCheckOutOnPayContainer("John Doe", "exampleemail@gmailcom");
    }

    @Test
    @When("I try to check out without adding any item to cart")
    public void checkOutWithNoItemInCart(){
        utilityMethods.navigateToCartPage(); //Automatically locates the No Order message
    }

    @Test
    @When("I try to increase the quantity of the item")
    public void addToCartThenIncreaseQuantity(){

        String itemToOrder = "Mocha";
        totalOrderPrice = utilityMethods.performAddSingleItemToCartMultipleTimes(itemToOrder, 10, totalOrderPrice);

        utilityMethods.navigateToCartPage();

        String preTotalPrice= utilityMethods.getPayContainerPriceText();
    
        //modify Order Quantity
        int postItemQuantity = utilityMethods.modifyOrderQuantity("add", 10, itemToOrder);
        String postTotalPrice= utilityMethods.getPayContainerPriceText();
        
        assertItemQuantity(itemToOrder, postItemQuantity);
        assertTrue(!preTotalPrice.equals(postTotalPrice), "The total price did not change");
    }

    @Test
    @When("I try to decrease the quantity of the item")
    public void addToCartThenDecreaseQuantity(){
        String itemToOrder = "Cafe Latte";
        totalOrderPrice = utilityMethods.performAddSingleItemToCartMultipleTimes(itemToOrder, 20, totalOrderPrice);
        
        utilityMethods.navigateToCartPage();

        String preTotalPrice= utilityMethods.getPayContainerPriceText();
    
        //modify Order Quantity
        int postItemQuantity = utilityMethods.modifyOrderQuantity("minus", 10, itemToOrder);
        String postTotalPrice= utilityMethods.getPayContainerPriceText();
        
        // @Then("The changes should be reflected in the item count and total price")
        assertItemQuantity(itemToOrder, postItemQuantity);
        assertTrue(!preTotalPrice.equals(postTotalPrice), "The total price did not change");

    }

    @Test
    @When("I try to add all items in cart and check them out")
    public void checkOutAllItemsInMenu(){

        List<String> allMenuItems= Arrays.asList("Espresso", "Espresso Macchiato", "Cappuccino", "Mocha", "Flat White", "Americano", "Cafe Latte", "Cafe Breve", "Espresso Con Panna");

        totalOrderPrice = utilityMethods.performAddDifferentItemsToCart(allMenuItems, totalOrderPrice);

        utilityMethods.navigateToCartPage();

        utilityMethods.performCheckOutOnPayContainer("John Doe", "example123@gmail.com");
        
        System.out.println("Checking out all items verified");
    }

    @Test
    @When("I try to check out without inputting any credentials")
    public void checkOutWithEmptyPaymentDetails(){

        utilityMethods.performAddItemToCart("Mocha", totalOrderPrice);

        utilityMethods.navigateToCartPage();

        String validationMesasge = utilityMethods.performCheckOutOnPayContainer("", "");
        
        assertTrue(validationMesasge.equals("Please fill out this field.Please fill out this field."));
    }

    @Test
    @When("I try to check out with incomplete email address")
    public void checkOutWithIncompletePaymentDetails(){

        utilityMethods.performAddItemToCart("Mocha", totalOrderPrice);

        utilityMethods.navigateToCartPage();
        
        String validationMesasge =  utilityMethods.performCheckOutOnPayContainer("John", "sampleEmail");
        
        assertTrue(validationMesasge.equals("Please include an '@' in the email address. 'sampleEmail' is missing an '@'."));
    }


    //#region PERFORM METHODS===================================================================
    @Then("The cart page elements should be visible")
    public void performAssertCartPageElements(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(utilityMethods.appdiv));
        assertTopMenuBar(driver.findElement(utilityMethods.topMenu)); 
        assertCartItems(driver.findElement(utilityMethods.cartItems)); 
        assertBuyButton(driver.findElement(utilityMethods.payContainerButton));

    }

    //#endregion
    //#region ASSERT METHODS===================================================================
    public void assertCompareCartListFromSiteToManualOrdersList(List<String> orderList){
        List<String> cartItemsfromCartPage= utilityMethods.getItemListFromCartPage();

        utilityMethods.insertHeadiingLines("assertCompareCartListFromSiteToManualOrdersList");
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
        utilityMethods.insertHeadiingLines("Starting assertMenuTopBar");
        List<String> expectedTopMenuBarElements = Arrays.asList("menu", "cart", "github");
        List <WebElement> topBarHeaderElements = topMenuBar.findElements(By.tagName("li"));
        
        for (WebElement element : topBarHeaderElements){
            wait.until(ExpectedConditions.elementToBeClickable(element));
            String  elementText = utilityMethods.extractTextFromString(element.findElement(By.tagName("a")).getText());
            assertTrue(expectedTopMenuBarElements.contains(elementText), "Element not found: " + elementText);
        }
        System.out.println("All top menu bar elements Verified");
    }

    @When("I observe the elements in the page")
    public void assertBuyButton(WebElement payContainer){
        utilityMethods.performCheckOutOnPayContainer("John Doe", "exampleemail@gmailcom");
    }

    @Then("The changes should be reflected in the item count")
    public void assertItemQuantity(String itemName, int actualQuantity){
        assertTrue(actualQuantity == utilityMethods.getItemQuantity(itemName), "Item quantity did not match");
    }



}
