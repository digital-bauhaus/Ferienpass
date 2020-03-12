package de.bauhaus.digital.controller;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import de.bauhaus.digital.FerienpassApplication;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = FerienpassApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port = 8089"
)
public abstract class AbstractControllerTest {

    protected static final String BASE_URL = "http://localhost:8089/api";

    @Before
    public void init() {
        // Init RestAssured with BasicAuth credentials once - so we don't need to do that on every RestAssured call
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("test");
        authScheme.setPassword("foo");
        RestAssured.authentication = authScheme;
    }

    protected Long addUser(Teilnehmer teilnehmer) {
        return given()
                .body(teilnehmer)
                .contentType(ContentType.JSON)
            .when()
                .post(BASE_URL + "/users")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body().as(Long.class);
    }

    protected List<Teilnehmer> getAllUsers() {
        return Arrays.asList(
            given()
            .when()
                .get(BASE_URL + "/users")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Teilnehmer[].class));
    }

    protected Teilnehmer getUser(Long userId) {
        return given()
                .pathParam("id", userId)
            .when()
                .get(BASE_URL + "/users/{id}")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Teilnehmer.class);
    }

    protected Long addProject(Projekt project) {
        return given()
                .body(project)
                .contentType(ContentType.JSON)
            .when()
                .post(BASE_URL+"/projects")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body().as(Long.class);
    }

    protected List<Projekt> getAllProjects() {
        return Arrays.asList(given()
                .contentType(ContentType.JSON)
            .when()
                .get(BASE_URL + "/projects")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Projekt[].class));
    }

    protected Projekt getProject(Long projectId) {
        return given()
                .pathParam("projectId", projectId)
            .when()
                .get(BASE_URL + "/projects/{projectId}")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Projekt.class);
    }

    protected void assignUserToProject(Long projectId, Long userId) {
        given()
            .pathParam("projectId", projectId)
            .pathParam("userId", userId)
            .contentType(ContentType.JSON)
        .when()
            .put(BASE_URL + "/projects/{projectId}/users/{userId}")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    protected void unassignUserFromProject(Long projectId, Long userId) {
        given()
            .pathParam("projectId", projectId)
            .pathParam("userId", userId)
            .contentType(ContentType.JSON)
        .when()
            .delete(BASE_URL + "/projects/{projectId}/users/{userId}")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

}
