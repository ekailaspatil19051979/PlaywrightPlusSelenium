package com.example.contract;

import com.example.utils.ApiUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;

public class ApiContractTest {
    @Test
    public void getItemContract() throws Exception {
        String apiUrl = "https://example.com/api/items/1";
        var response = ApiUtils.get(apiUrl);
        Assert.assertEquals(response.statusCode(), 200);

        JSONObject json = new JSONObject(response.body());
        Assert.assertTrue(json.has("id"));
        Assert.assertTrue(json.has("name"));
        Assert.assertTrue(json.has("createdAt"));
        // ...add more contract assertions as needed...
    }
}
