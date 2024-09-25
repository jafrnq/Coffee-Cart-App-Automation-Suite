Feature: Pick Items Functionality
    In order to purchase something form the shop
    As a valid shop customer
    I want to add to card my preferred items 

Scenario: I check the visibility of main Menu page elements
    Given I am in the menu page of the shop
    When  I observe the elements in the page
    Then The main menu elements should be visible

Scenario: I pick a Single Item
    Given I am in the menu page of the shop
    When I click an item
    Then All items should be recorded in the cart with its total cost

Scenario: Order the same item multiple times
    Given I am in the menu page of the shop
    When I add an item to cart multiple times
    Then All items should be recorded in the cart with its total cost

Scenario: Picking three Items but refusing to accept promo
    Given I am in the menu page of the shop
    When I add three or more items in the cart
    And I Reject the promo offer
    Then The promo amount should not be added to total order price

Scenario: Picking three Items and to accept promo
    Given I am in the menu page of the shop
    When I add three or more items in the cart
    And I accept the promo offer
    Then The promo amount should be added to total order price

Scenario: Picking All Items
    Given I am in the menu page of the shop
    When I add all items to cart
    Then All items should be recorded in the cart and its total amount



#Disregard this
# #FOR 2ND TEST CLASS (MAKE NEW FEATURE FILE NAMED "Check out using hover feature")
# Scenario: Checking Cart items using hover payContainer Div
# Scenario: Checking Cart items using going to cart page




