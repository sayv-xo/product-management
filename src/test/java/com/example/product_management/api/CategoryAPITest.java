package com.example.product_management.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryAPITest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @Order(1)
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
    @Order(2)
    public void testGetCategory() {
        when().
                get("/api/public/categories").
                then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("categoryName", hasItem(equalTo("New Category")));
    }

    @Test
    @Order(3)
    public void testUpdateCategory() {
        String categoryName = "New Category";
        String updatedCategoryName = "Change Category";
        String requestBody = "{ \"categoryName\": \"" + updatedCategoryName + "\" }";

        int categoryID = given()
                .when()
                .get("/api/public/categories")
                .then()
                .statusCode(200)
                .extract()
                .path("find { it.categoryName == '" + categoryName + "' }.categoryID");


        given()
                .contentType("application/json")
                .body("{\"categoryID\":" + categoryID + ", \"categoryName\":\"" + updatedCategoryName + "\"}")
                .when()
                .put("/api/admin/categories/" + categoryID)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    public void testDeleteCategory() {
        String categoryName = "Change Category";

        int categoryID = given()
                .when()
                .get("/api/public/categories")
                .then()
                .statusCode(200)
                .extract()
                .path("find { it.categoryName == '" + categoryName + "' }.categoryID");


        given()
                .when()
                .delete("/api/admin/categories/" + categoryID)
                .then()
                .statusCode(200);
    }
}
