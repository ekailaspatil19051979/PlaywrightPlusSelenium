package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class PerformanceTest {
    @Test
    public void testPageLoadPerformance() {
        WebDriver driver = new ChromeDriver();
        long start = System.currentTimeMillis();
        driver.get("https://example.com");
        long duration = System.currentTimeMillis() - start;
        assert duration < 3000 : "Page load took too long: " + duration + "ms";
        driver.quit();
    }

    @Test
    public void testApiResponseTime() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://example.com/api/items"))
            .GET()
            .build();
        long start = System.currentTimeMillis();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        long duration = System.currentTimeMillis() - start;
        assert response.statusCode() == 200 : "API did not return 200";
        assert duration < 1500 : "API response took too long: " + duration + "ms";
    }
}
