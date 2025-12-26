package com.example.e2e;

import com.example.utils.ApiUtils;
import com.example.utils.LoginUtils;
import com.example.utils.NavigationUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.net.http.HttpResponse;

public class ApiUiE2ETest {
    @Test
    public void createViaApiVerifyViaUi() throws Exception {
        // Step 1: Create entity via API
        String apiUrl = "https://example.com/api/items";
        String requestBody = "{\"name\":\"TestItem\"}";
        HttpResponse<String> response = ApiUtils.post(apiUrl, requestBody);
        Assert.assertEquals(response.statusCode(), 201);

        // Step 2: Verify via UI (Selenium example)
        WebDriver driver = /* ...get WebDriver instance... */;
        NavigationUtils.goTo(driver, "https://example.com/login");
        LoginUtils.loginSelenium(driver, "user", "pass");
        NavigationUtils.goTo(driver, "https://example.com/items");
        boolean found = driver.getPageSource().contains("TestItem");
        Assert.assertTrue(found, "Created item should be visible in UI");
        driver.quit();
    }
}
