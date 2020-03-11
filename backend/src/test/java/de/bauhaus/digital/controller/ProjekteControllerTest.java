package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleProject;
import static de.bauhaus.digital.DomainFactory.createSampleUser;
import static de.bauhaus.digital.DomainFactory.createSampleUserOfName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;

public class ProjekteControllerTest extends AbstractControllerTest {

    @Test
    public void givenInvalidId_whenRequestingProject_thenNotFound() {
        given()
                .pathParam("id", -1)
            .when()
                .get(BASE_URL + "/projects/{id}")
            .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void addNewProjectAndetrieveItBack() {
        Projekt projekt = createSampleProject();
        Long projectID = addProjekt(projekt);

        Projekt responeProjekt = getProjekt(projectID);
        assertThat(projectID, is(responeProjekt.getId()));
        assertThat(responeProjekt.getName(), is(projekt.getName()));
        assertThat(responeProjekt.getPlaetzeFrei(), is(projekt.getPlaetzeFrei()));
        assertThat(responeProjekt.getMindestAlter(), is(projekt.getMindestAlter()));
        assertThat(responeProjekt.getHoechstAlter(), is(projekt.getHoechstAlter()));
        assertThat(responeProjekt.getDatumBeginn(), is(projekt.getDatumBeginn()));
        assertThat(responeProjekt.getPlaetzeGesamt(), is(projekt.getPlaetzeGesamt()));
        assertThat(responeProjekt.getAngemeldeteTeilnehmer(), is(projekt.getAngemeldeteTeilnehmer()));
    }


    @Test
    public void addProjectAndUserAndAssignProjectToUserAndRetrieveAllProjectsForThisUser() {
        Long projectID = addProjekt(createSampleProject());

        Long userId = addUser(createSampleUser());
        List<Teilnehmer> allUsers = getAllUsers();

        assertThat(allUsers.get(allUsers.size()-1).getId(), is(userId));

        assertThat(assignUser2Projekt(projectID, userId),is(true));

        //Verify that the added user has now the project assigned
        Teilnehmer responseUser = getUser(userId);

        Projekt responseProjekt = getProjekt(projectID);
        assertThat(responseProjekt.getAngemeldeteTeilnehmer().size(), is(1));
        assertThat(responseProjekt.getAngemeldeteTeilnehmer().get(0).getId(), is(responseUser.getId()));
    }

    @Test
    public void assignProjektToUserAndRetrieveAllProjectsForTheUsers() {
        Long userId = addUser(createSampleUser());
        Long projectId = addProjekt(createSampleProject());

        Boolean wasTeilnehmerAssignedToProjekt = assignUser2Projekt(projectId, userId);
        assertThat(wasTeilnehmerAssignedToProjekt, is(true));

        //Get the user again
        Teilnehmer responseUser = getUser(userId);

        //Get all projects for the userID
        List<Projekt> projectsOfUserID = getAllProjekteWhereUserIsAssigned(responseUser.getId());
        assertThat(projectsOfUserID.size(),is(1));
        assertThat(projectsOfUserID.get(0).getId(), is(projectId));
    }

    @Test
    public void unassignUserFromProjektAndRetrieveAllCancelledProjectsForTheUsers() {
        Long userId = addUser(createSampleUser());
        Long projectId = addProjekt(createSampleProject());
        assignUser2Projekt(projectId, userId);

        unassignUserFromProjekt(projectId, userId);

        //Get the user again
        Teilnehmer responseUser = getUser(userId);

        //Get all cancelled projects for the userID
        List<Projekt> cancelledProjectsOfUser = getAllProjekteWhereUserIsCancelled(responseUser.getId());
        assertThat(cancelledProjectsOfUser.size(),is(1));
        assertThat(cancelledProjectsOfUser.get(0).getId(), is(projectId));
    }

    @Test
    public void testConsistencyOfRegisteredProjectsOfTeilnehmerAndRegisteredTeilnehmerOfProjects() {
        List<Projekt> allProjects = getAllProjects();
        int numberOfProjects = allProjects.size();
        System.out.println("Number of projects at start: " + numberOfProjects);

        // number of registered Teilnehmer in all projects should be equal
        // to number of registered projects of all Teilnehmer
        Projekt projekt1 = createSampleProject();
        Long projectID1 = addProjekt(projekt1);
        assertThat(projekt1.isAktiv(),is(true));

        Projekt projekt2 = createSampleProject();
        Long projectID2 = addProjekt(projekt2);
        assertThat(projekt2.isAktiv(),is(true));

        Teilnehmer user1 = createSampleUser();
        Long userId1 = addUser(user1);

        Teilnehmer user2 = createSampleUser();
        Long userId2 = addUser(user2);

        allProjects = getAllProjects();

        System.out.println("Number of projects after adding two: " + allProjects.size());
        assertThat(allProjects.size(), is(numberOfProjects+2));

        numberOfProjects = allProjects.size();
        int sumOfRegisteredTeilnehmer = 0;
        for (Projekt projekt: allProjects) {
            sumOfRegisteredTeilnehmer += projekt.getAngemeldeteTeilnehmer().size();
        }
        System.out.println("Number of registered participants of all projects: " + sumOfRegisteredTeilnehmer);

        //Assign some projects to users
        assertThat(assignUser2Projekt(projectID1, userId1),is(true));
        assertThat(assignUser2Projekt(projectID2, userId1),is(true));
        assertThat(assignUser2Projekt(projectID1, userId2),is(true));
        assertThat(assignUser2Projekt(projectID2, userId2),is(true));

        allProjects = getAllProjects();
        System.out.println("Number of projects after assignment: " + allProjects.size());
        assertThat(allProjects.size(), is(numberOfProjects));

        int newSumOfRegisteredTeilnehmer = 0;
        for (Projekt projekt: allProjects) {
            newSumOfRegisteredTeilnehmer += projekt.getAngemeldeteTeilnehmer().size();
        }
        //4 Assignments
        assertThat(newSumOfRegisteredTeilnehmer,is(sumOfRegisteredTeilnehmer+4));
    }

    @Test @Ignore
    public void shouldAssignUserCorrectlyToProjekt() throws Exception {
        // Given
        Long projectId = addProjekt(createSampleProject());

        Teilnehmer newUser = createSampleUserOfName("Tirol", "Anton");
        Long userId = addUser(newUser);

        // When
        Boolean isUserAssignedToProject = assignUser2Projekt(projectId, userId);

        // Then
        assertThat(isUserAssignedToProject,is(true));

        Teilnehmer teilnehmer = getAllAssignedUsersForGivenProject(projectId).get(0);
        assertThat(teilnehmer.getId(), is(userId));

        List<Projekt> projectsByFirstNameAndLastName = getAlleZugewiesenenProjekteByFirstNameAndLastName("Anton", "Tirol");
        assertThat(projectsByFirstNameAndLastName.size(),is(1));
    }

    @Test
    public void shouldUnassignUserCorrectlyFromProjekt() throws Exception {
        // Given
        Long userId = addUser(createSampleUser());
        Long projectId = addProjekt(createSampleProject());
        assignUser2Projekt(projectId, userId);

        // When
        Boolean isUserUnassignedFromProject = unassignUserFromProjekt(projectId, userId);

        // Then
        assertThat(isUserUnassignedFromProject, is(true));
        Projekt projekt = getProjekt(projectId);
        Teilnehmer teilnehmer = projekt.getStornierteTeilnehmer().get(0);
        assertThat(teilnehmer.getId(), is(userId));
    }

    @Test
    public void shouldUpdateProjectCorrectly() {
        Long projectId = addProjekt(createSampleProject());

        Projekt projekt = Projekt.newBuilder(getProjekt(projectId))
                .name("Klettern am Berg")
                .datumBeginn(LocalDate.of(2019, 8,3))
                .datumEnde(LocalDate.of(2019, 9,2))
                .plaetzeReserviert(6)
                .plaetzeGesamt(25)
                .build();

        Projekt updatedProjekt = updateProjekt(projekt);

        assertThat(updatedProjekt.getName(), is(projekt.getName()));
        assertThat(updatedProjekt.getDatumBeginn(), is(projekt.getDatumBeginn()));
        assertThat(updatedProjekt.getDatumEnde(), is(projekt.getDatumEnde()));
        assertThat(updatedProjekt.getPlaetzeReserviert(), is(projekt.getPlaetzeReserviert()));
        assertThat(updatedProjekt.getPlaetzeGesamt(), is(projekt.getPlaetzeGesamt()));
    }

    @Test
    public void shouldDeleteAddedProjectCorrectly() {
        Long projectId = addProjekt(createSampleProject());

        deleteProjekt(projectId);

        getNoProjekt(projectId);
    }

    @Test
    public void givenDatumEndeBeforeDatumBeginn_whenCreatingProjekt_thenBadRequest() {
        Projekt projekt = Projekt.newBuilder(createSampleProject())
                .datumBeginn(LocalDate.of(2019, 12, 5))
                .datumEnde(LocalDate.of(2019, 12, 4))
                .build();

        given()
            .body(projekt)
            .contentType(ContentType.JSON)
        .when()
            .post(BASE_URL+"/projects")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("errors", hasSize(1))
            .body("errors[0].field", is("datumEnde"));
    }

    @Test
    public void givenHoechstAlterSmallerThanMindestAlter_whenCreatingProjekt_thenBadRequest() {
        Projekt projekt = Projekt.newBuilder(createSampleProject())
                .mindestAlter(11)
                .hoechstAlter(10)
                .build();

        given()
                .body(projekt)
                .contentType(ContentType.JSON)
            .when()
                .post(BASE_URL+"/projects")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasSize(1))
                .body("errors[0].field", is("hoechstAlter"));
    }

    @Test
    public void givenMoreSlotsReservedThanTotalSlots_whenCreatingProjekt_thenBadRequest() {
        Projekt projekt = Projekt.newBuilder(createSampleProject())
                .plaetzeReserviert(11)
                .plaetzeGesamt(5)
                .build();

        given()
                .body(projekt)
                .contentType(ContentType.JSON)
            .when()
                .post(BASE_URL+"/projects")
                .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasSize(1))
                .body("errors[0].field", is("plaetzeReserviert"));
    }

    @Test
    public void givenProjektWithOneTeilnehmer_whenUpdatingProjektWithZeroSlotsReserved_thenBadRequest() {
        Long projectId = addProjekt(createSampleProject());
        Long userId = addUser(createSampleUser());

        assignUser2Projekt(projectId, userId);

        Projekt projekt = Projekt.newBuilder(getProjekt(projectId))
                .plaetzeReserviert(0)
                .build();

        given()
                .body(projekt)
                .contentType(ContentType.JSON)
            .when()
                .put(BASE_URL+"/projects")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasSize(1))
                .body("errors[0].field", is("plaetzeReserviert"));
    }

    private List<Projekt> getAllProjekteWhereUserIsAssigned(Long userId) {
        return Arrays.asList(
                given()
                        .pathParam("userId", userId)
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL+"/users/{userId}/projects")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .assertThat()
                        .extract().as(Projekt[].class));
    }

    private List<Projekt> getAllProjekteWhereUserIsCancelled(Long userId) {
        return Arrays.asList(
                given()
                        .pathParam("userId", userId)
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL+"/users/{userId}/cancelledprojects")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .assertThat()
                        .extract().as(Projekt[].class));
    }

    private List<Projekt> getAlleZugewiesenenProjekteByFirstNameAndLastName(String vorname, String nachname) {
        return Arrays.asList(given()
                .param("vorname",vorname)
                .param("nachname",nachname)
                .when()
                .get(BASE_URL+"/projects/byusername")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Projekt[].class));
    }

    private List<Teilnehmer> getAllAssignedUsersForGivenProject(Long projectId) {
        return Arrays.asList(
                given()
                    .pathParam("projektId", projectId)
                    .contentType(ContentType.JSON)
                .when()
                    .get(BASE_URL+"/projects/{projektId}/users")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .assertThat()
                    .extract().as(Teilnehmer[].class));
    }

    private Projekt updateProjekt(Projekt projekt) {
        return given()
                    .body(projekt)
                    .contentType(ContentType.JSON)
                .when()
                    .put(BASE_URL+"/projects")
                .then()
                    .statusCode(is(HttpStatus.SC_OK))
                    .extract()
                    .body().as(Projekt.class);
    }

    private void deleteProjekt(Long projektId) {
        given()
            .pathParam("projekt_id", projektId)
            .contentType(ContentType.JSON)
        .when()
            .delete(BASE_URL + "/projects/{projekt_id}")
        .then()
            .statusCode(is(HttpStatus.SC_OK));
    }

    private void getNoProjekt(Long projektId) {
        given()
            .pathParam("id", projektId)
            .when()
            .get(BASE_URL + "/projects/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}