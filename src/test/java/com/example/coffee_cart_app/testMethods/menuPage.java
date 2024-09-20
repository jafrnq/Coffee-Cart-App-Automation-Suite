package com.example.coffee_cart_app.testMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;


import java.util.List;
import java.util.concurrent.Executor;
import java.util.Arrays;

import org.openqa.selenium.By;

import com.example.coffee_cart_app.utilityMethods.cofeeCartAppUtilityMethods;

public class menuPage extends cofeeCartAppUtilityMethods{

    
    @Test
    public void test(){
        assertMenuPageElements();
    }




    //#region PERFORM METHODS
    public void performAddItemToCart(String item){}
    //#endregion


    //#region ASSERT MTHODS 
    public void assertMenuPageElements(){
        WebElement appdiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app")));// Gets the parent div for all elements inside the page
        WebElement topMenuBar = appdiv.findElement(By.tagName("ul"));
        WebElement menuItemsDiv = appdiv.findElement(By.cssSelector("div[data-v-a9662a08]"));
        WebElement payContainerDiv =appdiv.findElement(By.className("pay-container"));
        
        assertTopMenuBar(topMenuBar); //Done and working
        assertMenuItems(menuItemsDiv); //Done and working
        assertBuyButton(payContainerDiv);//Currently working on
    
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

    public void assertBuyButton(WebElement payContainerDiv){
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(payContainerDiv.findElement(By.className("pay"))));
        checkoutButton.click();

        
    }
    
    //#endregion

    
}
