  As a logged-in user
  I want to view my account overview
  So that I can see my account details

  Scenario: View account overview
    Given I am logged in to Parabank
    When I navigate to the Accounts Overview page
    Then I should see the Accounts Overview or an error

@smoke @overview
Scenario: View account overview
  Given I am logged in to Parabank
  When I navigate to the Accounts Overview page
  Then I should see the Accounts Overview or an error

# Hooks for setup/teardown are implemented in steps/Hooks.java
# Step definitions are parameterized for reusability (see steps/AccountOverviewSteps.java)