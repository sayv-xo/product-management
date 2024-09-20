package com.example.product_management.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

public class GetProductTest {
    @Test
    public void testStatusCodeAndContentType() {
        when().
                get("http://localhost:8080/api/public/products").
                then().
                assertThat().
                statusCode(200).
                contentType("application/json");
    }
}
