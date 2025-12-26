@smoke @regression
Feature: Example feature for tagging and stability

  @smoke
  Scenario: Quick login check
    Given I am logged in as "demo"
    Then I should see "Account Overview" on the page

  @regression
  Scenario: Navigation check
    When I navigate to "Accounts" page
    Then I should see "Account Details" on the page
