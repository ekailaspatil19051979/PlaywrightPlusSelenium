package com.example.bdd;

import com.example.utils.ApiUtils;
import com.example.utils.LoginUtils;
import com.example.utils.NavigationUtils;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.json.JSONObject;
import java.net.http.HttpResponse;
import static org.testng.Assert.*;

public class AdvancedSteps {
    private HttpResponse<String> apiResponse;
    private WebDriver driver;

    @When("I create an item via API with name {string}")
    public void i_create_item_via_api(String name) throws Exception {
        String apiUrl = "https://example.com/api/items";
        String requestBody = "{\"name\":\"" + name + "\"}";
        apiResponse = ApiUtils.post(apiUrl, requestBody);
        assertEquals(apiResponse.statusCode(), 201);
    }

    @Then("I should see the item {string} in the UI")
    public void i_should_see_item_in_ui(String name) {
        // ...initialize driver...
        NavigationUtils.goTo(driver, "https://example.com/login");
        LoginUtils.loginSelenium(driver, "user", "pass");
        NavigationUtils.goTo(driver, "https://example.com/items");
        assertTrue(driver.getPageSource().contains(name));
        driver.quit();
    }

    @Then("the API response should contain field {string}")
    public void api_response_should_contain_field(String field) {
        JSONObject json = new JSONObject(apiResponse.body());
        assertTrue(json.has(field));
    }
}