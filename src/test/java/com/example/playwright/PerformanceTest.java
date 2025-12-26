package com.example.playwright;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class PerformanceTest {
    @Test
    public void testPageLoadPerformance() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            long start = System.currentTimeMillis();
            page.navigate("https://example.com");
            long duration = System.currentTimeMillis() - start;
            assert duration < 3000 : "Page load took too long: " + duration + "ms";
            browser.close();
        }
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
