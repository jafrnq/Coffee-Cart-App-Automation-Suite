Feature: Checkout items Functionality
    In order to fully purchase an item/items in the shop
    As a valid shop customer
    I want to check out my order after adding them to my cart

Scenario: Checking the visibility of elements in the cart page
    Given I am in the the cart page of the shop after picking items
    When I observe the elements in the page
    Then The cart page elements should be visible

Scenario: Checking Cart items using hover payContainer Div
    Given I am in the the menu page of the shop after picking items
    When I try to checkout using the payContainer div
    Then I should be able to check out my orders without going to the cart page

Scenario: Checking Cart items using going to cart page
    Given I am in the the cart page of the shop after picking items
    When I try to checkout my items in the cart page
    Then I should be able to check out my orders successfully

Scenario: Checking out without adding any items to cart 
    Given I am in the the cart page of the shop after picking items
    When I try to check out without adding any item to cart
    Then The system should let me know that there's no any item in the cart

Scenario: Checking out then modifying its quantity in the cart page
    Given I am in the the cart page of the shop after picking items
    When I try to increase the quantity of the item
    And I try to decrease the quantity of the item
    Then The changes should be reflected in the item count and total price

Scenario: Checking out all items
    Given I am in the the cart page of the shop after picking items
    When I try to add all items in cart and check them out
    Then I should be able to checkout all of the items succesfully

Scenario: Checkout with empty credentials

Scenario:Checkout with incomplete email address

