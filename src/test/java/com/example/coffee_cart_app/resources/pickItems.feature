Feature: Pick Items Functionality
    In order to purchase something form the shop
    As a valid shop customer
    I want to add to card my preferred items 

Scenario: Picking a Single Item
    Given I am in the menu page of the shop
    When I click an item
    Then that item should be added to the cart


# Scenario: Picking multiple order of the same item 
# Scenario: Picking multiple order of more than one item(3)
# Scenario: Picking three Items but refusing to accept promo
# Scenario: Picking three Items and to accept promo
# Scenario: Picking more than three Items
# Scenario: Picking All Items
