package de.bauhaus.digital.controller;


import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.Test;

public class BackendControllerTest extends AbstractControllerTest {

    @Test
    public void login_should_give_http_200_with_right_credentials() {

        get(BASE_URL + "/login")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void login_should_give_http_401_unauthorized_with_wrong_credentials() {

        given()
            .auth().basic("wrong", "creds")
        .when()
            .get(BASE_URL + "/login")
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

}
