package com.example.coffee_cart_app.testMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.NoSuchElementException;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

// import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;


import java.util.List;

import java.util.Arrays;

import org.openqa.selenium.By;
import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;



public class menuPage extends cofeeCartAppUtilityMethods{
    //Global Variables
    float totalOrderPrice = 0;

    // Setup Methods
    @Override
    @AfterMethod
    public void tearDown(){
        insertHeadiingLines("Test Method completed");
        driver.manage().deleteAllCookies();
        totalOrderPrice = 0;
    }

    ////#region TEST METHODS=======================================================================================
    @Test //Done and working
    @When ("I check the visibility of main Menu page elements")
    public void testMenuPageElementsVisibility(){
        assertMenuPageElements();
    }

    @Test //Done and working
    @When("I pick a single item")
    public void pickASingleItem(){
        //Adds item to cart and updates the totalOrderPrice
        totalOrderPrice = performAddItemToCart("Cappuccino", totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    }

    @Test //Done and working
    @When("Order the same item multiple times")
    public void pickMultipleOfTheSameItem(){
        
        //Orders the same item multiple times and assigns the total value on totalOrderPrice
        totalOrderPrice = performAddSingleItemToCartMultipleTimes("Mocha", 5, totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    }

    @Test //Done and working
    @When("I add three or more items in the cart")
    public void pickDifferentItems(){
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");

        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
        assertCompareCartListfromManualOrdersList(preDefinedOrdersList);
    }

    @Test //Done and working
    @Then ("I accept the promo offer")
    public void acceptPromo(){
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte");
        
        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice, "Accept");
        
        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
        assertCompareCartListfromManualOrdersList(preDefinedOrdersList);
    }

    @Test 
    @Then ("I Reject the promo offer")
    public void rejectPromo(){
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte");

        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice, "Reject");

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
        assertCompareCartListfromManualOrdersList(preDefinedOrdersList);
   }

   @Test
   @When ("I add all items to cart")
   public void addAllItemsToCart(){
    float totalOrderPrice = 0;
    List<String> allMenuItems= Arrays.asList("Espresso", "Espresso Macchiato", "Cappuccino", "Mocha", "Flat White", "Americano", "Cafe Latte", "Cafe Breve", "Espresso Con Panna");

    totalOrderPrice = performAddDifferentItemsToCart(allMenuItems, totalOrderPrice);

    float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
    assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    assertCompareCartListfromManualOrdersList(allMenuItems);
   }

    //#endregion
    //#region ASSERT METHODS==============================================================================================================
    
    
    @Then("The main menu elements should be visible")
    public void assertMenuPageElements(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(appdiv));
        assertTopMenuBar(driver.findElement(topMenu)); 
        assertMenuItems(driver.findElement(menuItems)); 
        assertBuyButton(driver.findElement(payContainerButton));
    }   

    @When("I observe the elements in the page")
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
    public void assertMenuItems(WebElement menuItemsDiv){
        insertHeadiingLines("Starting assertMenuItems");
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
