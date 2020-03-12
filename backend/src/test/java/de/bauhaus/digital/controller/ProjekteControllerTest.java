package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleProjekt;
import static de.bauhaus.digital.DomainFactory.createSampleProjektBuilder;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmer;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import de.bauhaus.digital.DomainFactory;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.http.ContentType;
import java.time.LocalDate;
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
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
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

        List<Teilnehmer> allAssignedUsersForOriginalProject = getAllAssignedUsersForGivenProject(projektId);
        List<Teilnehmer> allCancelledUsersForOriginalProject = getAllCancelledUsersForGivenProject(projektId);

        // Important: We use the copy-builder, so we automatically use the correct ID
        Projekt projektToUpdate = Projekt.newBuilder(originalProjekt).build();

        // actual update is here
        updateProjekt(projektToUpdate);

        List<Teilnehmer> allAssignedUsersForUpdatedProject = getAllAssignedUsersForGivenProject(projektId);
        List<Teilnehmer> allCancelledUsersForUpdatedProject = getAllCancelledUsersForGivenProject(projektId);

        assertThat(allAssignedUsersForOriginalProject.get(0).getId(), is(allAssignedUsersForUpdatedProject.get(0).getId()));
        assertThat(allCancelledUsersForOriginalProject.get(0).getId(), is(allCancelledUsersForUpdatedProject.get(0).getId()));
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
        unassignUserFromProject(projektId, teilnehmerId);

        // When
        deleteProjekt(projektId);

        // Then
        // pass :)
    }

    @Test
    public void givenProjekt_whenUserIsAssigned_thenUserIsCorrectlyAssigned() {
        // Given
        Long userId = addUser(createSampleTeilnehmer());
        Long projectId = addProject(createSampleProjekt());

        // When
        assignUserToProject(projectId, userId);

        // Then
        List<Teilnehmer> allAssignedUsersForGivenProject = getAllAssignedUsersForGivenProject(projectId);
        assertThat(allAssignedUsersForGivenProject.size(), is(1));
        assertThat(allAssignedUsersForGivenProject.get(0).getId(), is(userId));
        List<Teilnehmer> getAllCancelledUsersForGivenProject = getAllCancelledUsersForGivenProject(projectId);
        assertThat(getAllCancelledUsersForGivenProject.size(), is(0));
    }

    @Test
    public void givenProjektWithAssignedUser_whenUserIsCancelled_thenUserIsCorrectlyCancelled() {
        // Given
        Long userId = addUser(createSampleTeilnehmer());
        Long projectId = addProject(createSampleProjekt());
        assignUserToProject(projectId, userId);

        // When
        unassignUserFromProject(projectId, userId);

        // Then
        List<Teilnehmer> allAssignedUsersForGivenProject = getAllAssignedUsersForGivenProject(projectId);
        assertThat(allAssignedUsersForGivenProject.size(), is(0));
        List<Teilnehmer> getAllCancelledUsersForGivenProject = getAllCancelledUsersForGivenProject(projectId);
        assertThat(getAllCancelledUsersForGivenProject.size(), is(1));
        assertThat(getAllCancelledUsersForGivenProject.get(0).getId(), is(userId));
    }

    @Test
    public void givenProjektWithNoFreeSlots_whenUserIsAssigned_thenFullyBooked() {
        // Given
        Long userId = addUser(createSampleTeilnehmer());
        Projekt fullyBookedProjekt = createSampleProjektBuilder().plaetzeGesamt(5).plaetzeReserviert(5).build();
        Long projectId = addProject(fullyBookedProjekt);

        given()
            .pathParam("projectId", projectId)
            .pathParam("userId", userId)
            .contentType(ContentType.JSON)
        .when()
            .put(BASE_URL + "/projects/{projectId}/users/{userId}")
        .then()
            .statusCode(HttpStatus.SC_CONFLICT);
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

    // TODO
    @Test @Ignore
    public void givenProjektWithOneTeilnehmer_whenUpdatingProjektWithZeroSlotsReserved_thenBadRequest() {
        Long projectId = addProject(createSampleProjektBuilder().plaetzeGesamt(10).plaetzeReserviert(9).build());
        Long userId = addUser(createSampleTeilnehmer());

        assignUserToProject(projectId, userId);

        Projekt projekt = Projekt.newBuilder(getProject(projectId))
                .plaetzeGesamt(9)
                .build();

        given()
            .body(projekt)
            .contentType(ContentType.JSON)
        .when()
            .put(BASE_URL+"/projects")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("errors", hasSize(2))
            .body("errors[0].field", is("plaetzeReserviert"));
    }

    // Helpers -----------------------------------------------------------------------------------

    private void updateProjekt(Projekt projekt) {
        given()
            .body(projekt)
            .contentType(ContentType.JSON)
        .when()
            .put(BASE_URL+"/projects")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    private void deleteProjekt(Long projektId) {
        given()
            .pathParam("projekt_id", projektId)
            .contentType(ContentType.JSON)
        .when()
            .delete(BASE_URL + "/projects/{projekt_id}")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    private void getNoProjekt(Long projektId) {
        given()
            .pathParam("id", projektId)
        .when()
            .get(BASE_URL + "/projects/{id}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
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
                    .extract().as(Teilnehmer[].class));
    }

    private List<Teilnehmer> getAllCancelledUsersForGivenProject(Long projectId) {
        return Arrays.asList(
                given()
                    .pathParam("projektId", projectId)
                    .contentType(ContentType.JSON)
                .when()
                    .get(BASE_URL+"/projects/{projektId}/cancelledusers")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(Teilnehmer[].class));
    }

}
