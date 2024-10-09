package com.example.coffee_cart_app.testMethods;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (
    features = "src/test/resources",
    glue = "com.example.coffee_cart_app.testMethods", 
    monochrome = true
)


public class TestRunner extends AbstractTestNGCucumberTests {

}
