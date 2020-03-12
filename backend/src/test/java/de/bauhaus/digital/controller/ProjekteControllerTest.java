package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleProjekt;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmer;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmerOfName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import de.bauhaus.digital.DomainFactory;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class ProjekteControllerTest extends AbstractControllerTest {

    @Test
    public void givenInvalidCredentials_whenAddingProjekt_thenHttp401Unauthorized() {
        Projekt projekt = createSampleProjekt();

        given()
            .auth().basic("wrong", "credentials")
            .body(projekt)
            .contentType(ContentType.JSON)
        .when()
            .post(BASE_URL + "/projects")
        .then()
            .statusCode(is(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenProjekte_whenAddingTwoProjekteAndRetrievingAll_thenListHasTwoAdditionalEntries() {
        int initialSize = getAllProjects().size();

        addProject(createSampleProjekt());
        addProject(createSampleProjekt());

        List<Projekt> alleProjekte = getAllProjects();

        assertThat(alleProjekte.size(), is(initialSize + 2));
    }

    @Test
    public void givenInvalidId_whenRequestingProject_thenNotFound() {
        getNoProjekt(-1L);
    }

    @Test
    public void givenProjekt_whenAddingAndRetrieving_thenTheyMatch() {
        Projekt projekt = createSampleProjekt();

        Long projektId = addProject(projekt);

        Projekt responseProjekt = getProject(projektId);

        Assertions.assertThat(responseProjekt).
                usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(projekt);
    }

    @Test
    public void givenProjektWithAngemeldetenTeilnehmern_whenAddingAndRetrieving_thenAngemeldeteTeilnehmerAreNotIncluded() {
        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Long projektId = addProject(createSampleProjekt());
        assignUserToProject(projektId, teilnehmerId);

        Projekt responseProjekt = getProject(projektId);

        assertThat(responseProjekt.getAngemeldeteTeilnehmer().size(), is(0));
    }

    @Test
    public void givenProjektWithStorniertenTeilnehmern_whenAddingAndRetrieving_thenStornierteTeilnehmerAreNotIncluded() {
        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Long projektId = addProject(createSampleProjekt());
        assignUserToProject(projektId, teilnehmerId);
        unassignUserFromProject(projektId, teilnehmerId);

        Projekt responseProjekt = getProject(projektId);

        assertThat(responseProjekt.getStornierteTeilnehmer().size(), is(0));
    }

    @Test
    public void givenProjektInDb_whenUpdatingAndRetrieving_thenRetrievedProjektMatchesUpdated() {

        Long projektId = addProject(createSampleProjekt());
        Projekt originalProjekt = getProject(projektId);

        boolean aktiv = true;
        String name = "Testprojekt U";
        LocalDate datumBeginn = LocalDate.of(2030, 3, 1);
        LocalDate datumEnde = LocalDate.of(2030, 3, 15);
        int mindestAlter = 10;
        int hoechstAlter = 20;
        int plaetzeGesamt = 30;
        int plaetzeReserviert = 7;
        List<Teilnehmer> angemeldeteTeilnehmer = new ArrayList<>(); // TODO
        List<Teilnehmer> stornierteTeilnehmer = new ArrayList<>(); // TODO

        // Important: We use the copy-builder, so we automatically use the correct ID
        Projekt projektToUpdate = Projekt.newBuilder(originalProjekt)
                .aktiv(aktiv)
                .name(name)
                .datumBeginn(datumBeginn)
                .datumEnde(datumEnde)
                .mindestAlter(mindestAlter)
                .hoechstAlter(hoechstAlter)
                .plaetzeGesamt(plaetzeGesamt)
                .plaetzeReserviert(plaetzeReserviert)
                .angemeldeteTeilnehmer(angemeldeteTeilnehmer)
                .stornierteTeilnehmer(stornierteTeilnehmer)
                .build();

        // actual update is here
        updateProjekt(projektToUpdate);

        Projekt responseProjekt = getProject(projektId);

        Assertions.assertThat(responseProjekt).
                usingRecursiveComparison()
                .isEqualTo(projektToUpdate);
    }

    @Test
    public void givenProjektWithAnmeldungenAndStornierungenInDb_whenUpdatingAndRetrieving_AnmeldungenUndStornierungenAreNotLost() {

        Long projektId = addProject(createSampleProjekt());
        Projekt originalProjekt = getProject(projektId);

        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Long teilnehmerId2 = addUser(createSampleTeilnehmer());
        assignUserToProject(projektId, teilnehmerId);
        assignUserToProject(projektId, teilnehmerId2);
        unassignUserFromProject(projektId, teilnehmerId);

        // TODO !?

        // Important: We use the copy-builder, so we automatically use the correct ID
        Projekt projektToUpdate = Projekt.newBuilder(originalProjekt)
                .angemeldeteTeilnehmer(originalProjekt.getAngemeldeteTeilnehmer())
                .stornierteTeilnehmer(originalProjekt.getStornierteTeilnehmer())
                .build();

        // actual update is here
        updateProjekt(projektToUpdate);

        Projekt responseProjekt = getProject(projektId);

        Assertions.assertThat(responseProjekt).
                usingRecursiveComparison()
                .isEqualTo(projektToUpdate);
    }

    @Test
    public void givenProjekt_whenDeletedAndRequested_thenIsNotFound() {
        Long projektId = addProject(createSampleProjekt());

        Projekt responseProjekt = getProject(projektId);

        Assert.assertThat(responseProjekt.getId(), is(projektId));

        deleteProjekt(responseProjekt.getId());

        getNoProjekt(projektId);
    }

    @Test
    public void givenTeilnehmerThatIsAssignedToProjekt_whenDeletingProjekt_thenNoFail() {
        // Given
        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Long projektId = addProject(createSampleProjekt());
        assignUserToProject(projektId, teilnehmerId);

        // When
        deleteProjekt(projektId);

        // Then
        // pass :)
    }

    @Test
    public void givenTeilnehmerThatWasCancelledFromProjekt_whenDeletingProjekt_thenNoFail() {
        // Given
        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Long projektId = addProject(createSampleProjekt());
        assignUserToProject(projektId, teilnehmerId);
        Boolean isUserUnassignedFromProject = unassignUserFromProject(projektId, teilnehmerId);
        assertThat(isUserUnassignedFromProject, is(true));

        // When
        deleteProjekt(projektId);

        // Then
        // pass :)
    }

    @Test
    public void shouldAssignUserCorrectlyToProjekt() throws Exception {
        // Given
        Long projectId = addProject(createSampleProjekt());

        Teilnehmer newUser = createSampleTeilnehmerOfName("Anton", "Tirol");
        Long userId = addUser(newUser);

        // When
        Boolean isUserAssignedToProject = assignUserToProject(projectId, userId);

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
        Long userId = addUser(createSampleTeilnehmer());
        Long projectId = addProject(DomainFactory.createSampleProjekt());
        assignUserToProject(projectId, userId);

        // When
        Boolean isUserUnassignedFromProject = unassignUserFromProject(projectId, userId);

        // Then
        assertThat(isUserUnassignedFromProject, is(true));
        Projekt projekt = getProject(projectId);
        Teilnehmer teilnehmer = projekt.getStornierteTeilnehmer().get(0);
        assertThat(teilnehmer.getId(), is(userId));
    }

    @Test
    public void addProjectAndUserAndAssignProjectToUserAndRetrieveAllProjectsForThisUser() {
        Long projectID = addProject(DomainFactory.createSampleProjekt());

        Long userId = addUser(createSampleTeilnehmer());
        List<Teilnehmer> allUsers = getAllUsers();

        assertThat(allUsers.get(allUsers.size()-1).getId(), is(userId));

        assertThat(assignUserToProject(projectID, userId),is(true));

        //Verify that the added user has now the project assigned
        Teilnehmer responseTeilnehmer = getUser(userId);

        Projekt responseProjekt = getProject(projectID);
        assertThat(responseProjekt.getAngemeldeteTeilnehmer().size(), is(1));
        assertThat(responseProjekt.getAngemeldeteTeilnehmer().get(0).getId(), is(responseTeilnehmer.getId()));
    }

    @Test
    public void assignProjektToUserAndRetrieveAllProjectsForTheUsers() {
        Long userId = addUser(createSampleTeilnehmer());
        Long projectId = addProject(DomainFactory.createSampleProjekt());

        Boolean wasTeilnehmerAssignedToProjekt = assignUserToProject(projectId, userId);
        assertThat(wasTeilnehmerAssignedToProjekt, is(true));

        //Get the user again
        Teilnehmer responseTeilnehmer = getUser(userId);

        //Get all projects for the userID
        List<Projekt> projectsOfUserID = getAllProjekteWhereUserIsAssigned(responseTeilnehmer.getId());
        assertThat(projectsOfUserID.size(),is(1));
        assertThat(projectsOfUserID.get(0).getId(), is(projectId));
    }

    @Test
    public void unassignUserFromProjektAndRetrieveAllCancelledProjectsForTheUsers() {
        Long userId = addUser(createSampleTeilnehmer());
        Long projectId = addProject(DomainFactory.createSampleProjekt());
        assignUserToProject(projectId, userId);

        unassignUserFromProject(projectId, userId);

        //Get the user again
        Teilnehmer responseTeilnehmer = getUser(userId);

        //Get all cancelled projects for the userID
        List<Projekt> cancelledProjectsOfUser = getAllProjekteWhereUserIsCancelled(responseTeilnehmer.getId());
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
        Projekt projekt1 = DomainFactory.createSampleProjekt();
        Long projectID1 = addProject(projekt1);
        assertThat(projekt1.isAktiv(),is(true));

        Projekt projekt2 = DomainFactory.createSampleProjekt();
        Long projectID2 = addProject(projekt2);
        assertThat(projekt2.isAktiv(),is(true));

        Teilnehmer user1 = createSampleTeilnehmer();
        Long userId1 = addUser(user1);

        Teilnehmer user2 = createSampleTeilnehmer();
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
        assertThat(assignUserToProject(projectID1, userId1),is(true));
        assertThat(assignUserToProject(projectID2, userId1),is(true));
        assertThat(assignUserToProject(projectID1, userId2),is(true));
        assertThat(assignUserToProject(projectID2, userId2),is(true));

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

    // Check Custom Validations -------------------------------------------------------------------

    @Test
    public void givenDatumEndeBeforeDatumBeginn_whenCreatingProjekt_thenBadRequest() {
        Projekt projekt = Projekt.newBuilder(DomainFactory.createSampleProjekt())
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
        Projekt projekt = Projekt.newBuilder(DomainFactory.createSampleProjekt())
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
        Projekt projekt = Projekt.newBuilder(DomainFactory.createSampleProjekt())
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
        Long projectId = addProject(DomainFactory.createSampleProjekt());
        Long userId = addUser(createSampleTeilnehmer());

        assignUserToProject(projectId, userId);

        Projekt projekt = Projekt.newBuilder(getProject(projectId))
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

    // Helpers -----------------------------------------------------------------------------------

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
        return Arrays.asList(
                given()
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
