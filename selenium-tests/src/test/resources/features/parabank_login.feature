  As a Parabank user
  I want to login with valid and invalid credentials
  So that I can access my account or see an error message

  Scenario: Login with valid credentials
    Given I am on the Parabank login page
    When I login with username "testuser" and password "testpass"
    Then I should see the Accounts Overview page

  Scenario: Login with invalid credentials
    Given I am on the Parabank login page
    When I login with username "wronguser" and password "wrongpass"
    Then I should see an error message

@smoke @login
Scenario: Login with valid credentials
  Given I am on the Parabank login page
  When I login with username "testuser" and password "testpass"
  Then I should see the Accounts Overview page

@regression @login
Scenario: Login with invalid credentials
  Given I am on the Parabank login page
  When I login with username "wronguser" and password "wrongpass"
  Then I should see an error message

# Hooks for setup/teardown are implemented in steps/Hooks.java
# Step definitions are parameterized for reusability (see steps/LoginSteps.java)
