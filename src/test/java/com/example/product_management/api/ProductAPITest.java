package com.example.product_management.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

public class ProductAPITest {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void getStatusCodeAndContentTypeTest() {
        when().
                get("/api/public/products").
                then().
                assertThat().
                statusCode(200).
                contentType("application/json");
    }
}
