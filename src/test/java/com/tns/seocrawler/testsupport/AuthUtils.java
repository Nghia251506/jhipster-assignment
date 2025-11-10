package com.tns.seocrawler.testsupport;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.http.ContentType;

/**
 * Tiện ích để lấy JWT token admin.
 * - Dùng chung cho các test khác.
 * - Coi app như hộp đen: gọi /api/authenticate.
 */
public final class AuthUtils {

    private static final String BASE_URL = "http://localhost:8080";
    private static String adminToken;

    private AuthUtils() {}

    public static String getAdminToken() {
        if (adminToken == null) {
            adminToken = given()
                .contentType(ContentType.JSON)
                .body(
                    """
                    {
                      "username": "admin",
                      "password": "Ntn1506@",
                      "rememberMe": true
                    }
                    """
                )
                .when()
                .post(BASE_URL + "/api/authenticate")
                .then()
                .statusCode(200)
                .body("id_token", notNullValue())
                .extract()
                .path("id_token");
        }
        return adminToken;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
