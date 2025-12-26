package integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiUiChainingTest {
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    @Test
    public void createBookingViaApiAndVerifyViaUi() {
        // Step 1: Create booking via API
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstname\":\"Jim\",\"lastname\":\"Brown\",\"totalprice\":111,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2023-01-01\",\"checkout\":\"2023-01-02\"},\"additionalneeds\":\"Breakfast\"}")
                .post(BASE_URL + "/booking");
        Assert.assertEquals(response.statusCode(), 200);
        int bookingId = response.jsonPath().getInt("bookingid");

        // Step 2: Verify booking via UI (example, adjust as per actual UI)
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1280, 1024));
        driver.get("https://restful-booker.herokuapp.com/booking/" + bookingId);
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Jim") && pageSource.contains("Brown"));
        driver.quit();
    }
}
