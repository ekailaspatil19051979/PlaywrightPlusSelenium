package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class RestfulBookerApiContractTest {
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    @Test
    public void testAuthContract() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                .post(BASE_URL + "/auth");
        Assert.assertEquals(response.statusCode(), 200);
        Map<String, Object> body = response.jsonPath().getMap("");
        Assert.assertTrue(body.containsKey("token"));
    }

    @Test
    public void testBookingContract() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"firstname\":\"Jim\",\"lastname\":\"Brown\",\"totalprice\":111,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2023-01-01\",\"checkout\":\"2023-01-02\"},\"additionalneeds\":\"Breakfast\"}")
                .post(BASE_URL + "/booking");
        Assert.assertEquals(response.statusCode(), 200);
        Map<String, Object> body = response.jsonPath().getMap("");
        Assert.assertTrue(body.containsKey("bookingid"));
        Assert.assertTrue(body.containsKey("booking"));
        Map<String, Object> booking = (Map<String, Object>) body.get("booking");
        Assert.assertTrue(booking.containsKey("firstname"));
        Assert.assertTrue(booking.containsKey("lastname"));
    }

    @Test
    public void testGetBookingsContract() {
        Response response = RestAssured.given().get(BASE_URL + "/booking");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.jsonPath().getList("").size() >= 0);
    }
}
