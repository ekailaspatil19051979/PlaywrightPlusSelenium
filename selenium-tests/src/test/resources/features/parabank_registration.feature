  As a new user
  I want to register for a Parabank account
  So that I can access online banking

  Scenario: Register a new user
    Given I am on the Parabank registration page
    When I register with unique credentials
    Then I should see a registration confirmation or error

@regression @registration
Scenario: Register a new user
  Given I am on the Parabank registration page
  When I register with unique credentials
  Then I should see a registration confirmation or error

# Hooks for setup/teardown are implemented in steps/Hooks.java
# Step definitions are parameterized for reusability (see steps/RegistrationSteps.java)