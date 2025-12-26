package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class CommonSteps {
    @Given("I am logged in as {string}")
    public void i_am_logged_in_as(String user) {
        // Reusable login logic
    }

    @When("I navigate to {string} page")
    public void i_navigate_to_page(String page) {
        // Reusable navigation logic
    }

    @Then("I should see {string} on the page")
    public void i_should_see_on_the_page(String text) {
        // Reusable assertion logic
    }

    // Add more custom steps for API/UI integration as needed
}