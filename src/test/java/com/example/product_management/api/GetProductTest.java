package com.example.product_management.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetProductTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testCreateCategory() {
        String categoryName = "New Category";

        String requestBody = "{ \"categoryName\": \"" + categoryName + "\" }";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/admin/categories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testStatusCodeAndContentType() {
        when().
                get("/api/public/products").
                then().
                assertThat().
                statusCode(200).
                contentType("application/json");
    }

    @Test
    public void testGetCategory() {
        when().
                get("/api/public/categories").
                then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("categoryName", hasItem(equalTo("New Category")));
    }
}
