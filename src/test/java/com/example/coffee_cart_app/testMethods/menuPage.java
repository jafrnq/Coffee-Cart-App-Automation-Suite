package com.example.coffee_cart_app.testMethods;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.assertTrue;

import java.util.List;
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
        WebElement appdiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app")));
        WebElement topMenuBar = appdiv.findElement(By.tagName("ul"));


        assertTopMenuBar(topMenuBar);
    }


    public void assertTopMenuBar(WebElement topMenuBar){
        List<String> expectedTopMenuBarElements = Arrays.asList("Home", "Categories", "Faq");
        List <WebElement> topBarHeaderElements = topMenuBar.findElements(By.tagName("li"));
        for (WebElement element : topBarHeaderElements){
            String  elementText = element.findElement(By.tagName("a")).getText();
            assertTrue
            System.out.println("Element: " + elementText);
        }
    }
    
    //#endregion

    
}
