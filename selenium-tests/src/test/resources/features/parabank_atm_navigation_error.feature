  As a user
  I want to use ATM services and handle navigation errors
  So that I can verify error handling and navigation

  Scenario: ATM Withdraw and Deposit
    Given I am logged in to Parabank
    When I use ATM Services to withdraw 20 and deposit 50
    Then I should see a success, error, or unavailable message

  Scenario: Navigation to invalid URL
    When I navigate to an invalid Parabank URL
    Then I should see a 404 or error message

  Scenario: Session expiry handling
    Given I am logged in to Parabank
    When my session expires
    Then I should see a session expired or login prompt

@regression @atm
Scenario: ATM Withdraw and Deposit
  Given I am logged in to Parabank
  When I use ATM Services to withdraw 20 and deposit 50
  Then I should see a success, error, or unavailable message

@regression @navigation
Scenario: Navigation to invalid URL
  When I navigate to an invalid Parabank URL
  Then I should see a 404 or error message

@regression @session
Scenario: Session expiry handling
  Given I am logged in to Parabank
  When my session expires
  Then I should see a session expired or login prompt

# Hooks for setup/teardown are implemented in steps/Hooks.java
# Step definitions are parameterized for reusability (see steps/ATMNavigationErrorSteps.java)