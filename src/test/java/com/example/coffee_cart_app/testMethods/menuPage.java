package com.example.coffee_cart_app.testMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.openqa.selenium.NoSuchElementException;

// import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;


import java.util.List;

import java.util.Arrays;

import org.openqa.selenium.By;
import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;



public class menuPage extends cofeeCartAppUtilityMethods{


    @Test //Done and working
    public void testMenuPageElementsVisibility(){
        assertMenuPageElements();
    }

    @Test //Done and working
    public void pickASingleItem(){
        float totalOrderPrice = 0;
        
        //Adds item to cart and updates the totalOrderPrice
        totalOrderPrice = performAddItemToCart("Cappuccino", totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    }

    @Test //Done and working
    public void pickMultipleOfTheSameItem(){
        float totalOrderPrice = 0;
        
        //Orders the same item multiple times and assigns the total value on totalOrderPrice
        totalOrderPrice = performAddSingleItemToCartMultipleTimes("Mocha", 5, totalOrderPrice);

        float currentPriceBasedOnPayContainerDiv = extractFloatFromString(getPayContainerPriceText());
        assertTotalPriceAndTotalBasedOnSite(totalOrderPrice, currentPriceBasedOnPayContainerDiv);
    }

    @Test 
    public void pickDifferentItems(){
        List<String> preDefinedOrdersList = Arrays.asList("Mocha", "Flat White", "Cafe Latte", "Espresso Con Panna", "Cafe Breve");
        float totalOrderPrice = 0;

        totalOrderPrice = performAddDifferentItemsToCart(preDefinedOrdersList, totalOrderPrice);
        
    }

   
    //#region PERFORM METHODS
    public float performAddDifferentItemsToCart(List<String> ordersList, float totalOrderPrice){

        for (int i = 0; i<ordersList.size() ; i++ ){


            //Finish tomorrow


            
        }
        return totalOrderPrice;
    }


    public float performAddSingleItemToCartMultipleTimes(String itemName, int orderCount, float totalOrderPrice ){

        for (int i = 0; i < orderCount; i++){
            totalOrderPrice =  performAddItemToCart("Mocha", totalOrderPrice);
        }

        System.out.println("Successfully ordered: " + orderCount + " " + itemName);
        System.out.println("Order total based on manual count: " + totalOrderPrice);
        return totalOrderPrice;
    }
    
 
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
                performPromoControls("Reject");
            }

        }catch(NoSuchElementException e){
            System.out.println("Promo Element not visible as expected...");
        }
        return totalOrderPrice;
    }
    //#endregion


    //#region ASSERT MTHODS 
    public void assertMenuPageElements(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(appdiv));
        assertTopMenuBar(driver.findElement(topMenu)); 
        assertMenuItems(driver.findElement(menuItems)); 
        assertBuyButton(driver.findElement(payContainerButton));
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

    
    // public void assertCartUsingHoverButton(WebElement checkoutButton){} //Later after asseting buy elemnt
    
    // public void assertassertClickBuyButton(WebElement checkoutButton){
    //     checkoutButton.click();
    //     WebElement 
    // } //Later for actual operation


    // public void assertActualCartPage(){} 
    //#endregion

    
}
