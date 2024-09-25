package com.example.coffee_cart_app.testMethods;

import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;

import org.testng.annotations.Test;

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

        performNavigateToCartPage();

        performAssertCartPageElements();
    }

    @Test 
    @When("I try to checkout my items in the cart page")
    public void checkOutOrdersUsingCheckoutPage(){}

    @Test
    @When("I try to checkout using the payContainer div")
    public void checkOutOrdersUsingPayContainerDiv(){}

    @Test
    @When("I try to check out without adding any item to cart")
    public void checkOutWithNoItemInCart(){}

    @Test
    @When("I try to add all items in cart and check them out")
    public void checkOutAllItemsInMenu(){}





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

    public void assertCartItems(WebElement cartMenuItems){
        WebElement cartListHeader = cartMenuItems.findElement(By.cssSelector("li.list-header"));
        
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
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(payContainer));
        //Clicks and asserts the popup modal
        WebElement paymentDetailsModal = switchToAndAssertModalHeader(checkoutButton, "Payment details");

        //asserting modal elements 
        WebElement nameField = paymentDetailsModal.findElement(By.cssSelector("input[id='name']"));
        WebElement emailField = paymentDetailsModal.findElement(By.cssSelector("input[id='email']"));
        WebElement promotionCheckbox = paymentDetailsModal.findElement(By.cssSelector("div input[id='promotion']"));
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
