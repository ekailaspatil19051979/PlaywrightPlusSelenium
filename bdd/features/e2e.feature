Feature: End-to-end item creation

  Scenario: Create item via API and verify via UI
    Given I create an item named "TestItem" via API
    When I login as "user" with password "pass"
    And I navigate to the items page
    Then I should see "TestItem" in the list
