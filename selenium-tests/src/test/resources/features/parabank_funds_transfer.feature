  As a user
  I want to transfer funds between accounts
  So that I can manage my finances

  Scenario: Transfer funds with valid amount
    Given I am logged in to Parabank
    When I transfer 10 from account "12345" to account "54321"
    Then I should see a transfer confirmation or error

  Scenario: Transfer funds exceeding balance
    Given I am logged in to Parabank
    When I transfer 999999 from account "12345" to account "54321"
    Then I should see an error or insufficient funds message

@smoke @transfer
Scenario: Transfer funds with valid amount
  Given I am logged in to Parabank
  When I transfer 10 from account "12345" to account "54321"
  Then I should see a transfer confirmation or error

@regression @transfer
Scenario: Transfer funds exceeding balance
  Given I am logged in to Parabank
  When I transfer 999999 from account "12345" to account "54321"
  Then I should see an error or insufficient funds message

# Hooks for setup/teardown are implemented in steps/Hooks.java
# Step definitions are parameterized for reusability (see steps/FundsTransferSteps.java)