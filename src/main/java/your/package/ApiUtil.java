package your.package;

import java.net.http.*;
import java.net.URI;

public class ApiUtil {
    public static String getUserToken(String username, String password) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://yourapp.com/api/login"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // ...parse and return token as needed...
        return response.body();
    }
}
