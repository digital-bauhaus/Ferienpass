package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleProject;
import static de.bauhaus.digital.DomainFactory.createSampleUser;
import static de.bauhaus.digital.DomainFactory.createSampleUserOfName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.bauhaus.digital.domain.Arzt;
import de.bauhaus.digital.domain.Behinderung;
import de.bauhaus.digital.domain.Kontakt;
import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class TeilnehmerControllerTest extends AbstractControllerTest {

    @Test
    public void givenInvalidCredentials_whenAddingUser_thenGiveHttp401Unauthorized() {
        Teilnehmer user = createSampleUser();

        given()
                .auth().basic("wrong", "credentials")
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/users")
                .then()
                .statusCode(is(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenInvalidId_whenRequestingUser_thenNotFound() {
        given()
            .pathParam("id", -1)
        .when()
            .get(BASE_URL + "/users/{id}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void addNewUserAndRetrieveItBack() {
        Teilnehmer user = createSampleUser();

        Long userId = addUser(user);

        Teilnehmer responseUser = getUser(userId);

        Assertions.assertThat(responseUser).
                usingRecursiveComparison()
                .ignoringFields("id", "arzt.id", "behinderung.id", "notfallKontakt.id")
                .isEqualTo(user);
    }

    @Test
    public void addTwoUsersAndCheckWhetherAllUsersAreComplete() {
        int initialSize = getAllUsers().size();

        Long userId = addUser(createSampleUser());
        Long userId2 = addUser(createSampleUser());

        List<Teilnehmer> allUsers = getAllUsers();

        assertThat(allUsers.size()-initialSize, is(2));
        long id1 = allUsers.get(initialSize).getId();
        long id2 = allUsers.get(initialSize+1).getId();
        assertThat(id1,is(userId));
        assertThat(id2,is(userId2));
    }

    @Test
    public void isUserUpdatedCorrectly() {
        Long userId = addUser(createSampleUser());

        Arzt arzt = new Arzt(
                "Doktor Who",
                "Arzthaus 1",
                "555-6891");

        Kontakt kontakt = new Kontakt(
                "Igor Müller",
                "Hinter dem Dorf 4",
                "555-2532");

        String krankheiten = "Grippe: Muss oft Husten Hustenbonbons";


        String essenWeitereLimitierungen = "Laktoseintoleranz";
        String allergien = "Heuschnupfen: Nasenspray nur 2x am Tag";

        Boolean liegtBehinderungVor = true;
        Behinderung behinderung = Behinderung.newBuilder().
                rollstuhlNutzungNotwendig(true).
                merkzeichen_Hilflosigkeit_H(true).
                wertmarkeVorhanden(true).
                build();

        String medikamente = "Nasenspray von Forte: 2x am Tag";

        boolean hitzeempfindlich = true;

        String vorname = "Klaus";
        String nachname = "Klausen";
        LocalDate geburtsdatum = LocalDate.of(1999, 12, 31);
        String strasse = "Bahnhofstraße";
        String hausnummer = "5";
        String wohnort = "Erfurt";
        String plz = "99082";
        String telefon = "03544444";
        String krankenkasse = "AOK";
        String email = "myEmail@weimar.de";

        Teilnehmer klausKlausen = Teilnehmer.newBuilder()
                .id(userId) // Exlicitely set Id of User to update, so our implementation can find it
                .schulkind(true)
                .datenschutzErklaerungAkzeptiert(true)
                .teilnahmeBedingungAkzeptiert(true)
                .vorname(vorname)
                .nachname(nachname)
                .geburtsdatum(geburtsdatum)
                .strasse(strasse)
                .hausnummer(hausnummer)
                .wohnort(wohnort)
                .postleitzahl(plz)
                .telefon(telefon)
                .krankenkasse(krankenkasse)
                .darfBehandeltWerden(false)
                .notfallKontakt(kontakt)
                .darfAlleinNachHause(true)
                .darfReiten(false)
                .darfSchwimmen(true)
                .schwimmAbzeichen("Seepferdchen")
                .bezahlt(false)
                .darfBehandeltWerden(true)
                .arzt(arzt)
                .allergien(allergien)
                .essenWeitereLimitierungen(essenWeitereLimitierungen)
                .krankheiten(krankheiten)
                .liegtBehinderungVor(liegtBehinderungVor)
                .behinderung(behinderung)
                .hitzeempfindlich(hitzeempfindlich)
                .medikamente(medikamente)
                .email(email)
                .build();

        updateUser(klausKlausen);

        Teilnehmer responseUser = getUser(userId);

        Assert.assertThat(responseUser.getVorname(),is(vorname));
        Assert.assertThat(responseUser.getNachname(),is(nachname));
        Assert.assertThat(responseUser.getGeburtsdatum(),is(geburtsdatum));
        Assert.assertThat(responseUser.getStrasse(),is(strasse));
        Assert.assertThat(responseUser.getPostleitzahl(),is(plz));
        Assert.assertThat(responseUser.getWohnort(),is(wohnort));
        Assert.assertThat(responseUser.getTelefon(),is(telefon));
        Assert.assertThat(responseUser.getKrankenkasse(),is(krankenkasse));
        Assert.assertThat(responseUser.getNotfallKontakt().getName(),is(kontakt.getName()));
        Assert.assertThat(responseUser.getNotfallKontakt().getAnschrift(),is(kontakt.getAnschrift()));
        Assert.assertThat(responseUser.getNotfallKontakt().getTelefon(),is(kontakt.getTelefon()));
        Assert.assertThat(responseUser.getArzt().getName(),is(arzt.getName()));
        Assert.assertThat(responseUser.getArzt().getAnschrift(),is(arzt.getAnschrift()));
        Assert.assertThat(responseUser.getArzt().getTelefon(),is(arzt.getTelefon()));
        Assert.assertThat(responseUser.getAllergien(),is(allergien));
        Assert.assertThat(responseUser.getKrankheiten(),is(krankheiten));
        Assert.assertThat(responseUser.getEssenWeitereLimitierungen(),is(essenWeitereLimitierungen));
        Assert.assertThat(responseUser.getMedikamente(),is(medikamente));
        Assert.assertThat(responseUser.isHitzeempfindlich(),is(hitzeempfindlich));
        Assert.assertThat(responseUser.getEmail(), is(email));
        Assert.assertThat(responseUser.isLiegtBehinderungVor(), is(liegtBehinderungVor));
    }

    @Test
    public void isUserDeletedCorrectly() {
        Long userId = addUser(createSampleUser());

        Teilnehmer responseUser = getUser(userId);

        Assert.assertThat(responseUser.getId(), is(userId));

        deleteUser(responseUser.getId());

        getNoUser(userId);
    }

    @Test
    public void should_be_able_to_delete_Teilnehmer_if_was_assigned_to_Projekt() throws Exception {
        // Given
        Long userId = addUser(createSampleUser());
        Long projectId = addProject(createSampleProject());
        assignUserToProject(projectId, userId);

        // When
        deleteUser(userId);

        // Then
        // pass :)
    }

    @Test
    public void should_be_able_to_delete_Teilnehmer_if_was_storniert_on_Projekt() throws Exception {
        // Given
        Long userId = addUser(createSampleUser());
        Long projectId = addProject(createSampleProject());
        assignUserToProject(projectId, userId);
        Boolean isUserUnassignedFromProject = unassignUserFromProject(projectId, userId);
        assertThat(isUserUnassignedFromProject, is(true));

        // When
        deleteUser(userId);

        // Then
        // pass :)
    }

    private void updateUser(Teilnehmer teilnehmer) {
        given()
            .body(teilnehmer)
            .contentType(ContentType.JSON)
        .when()
            .put(BASE_URL + "/users")
        .then()
            .statusCode(is(HttpStatus.SC_OK));
    }

    private void deleteUser(long userId) {
        given()
                .pathParam("userId", userId)
                .contentType(ContentType.JSON)
        .when()
                .delete(BASE_URL + "/users/{userId}")
        .then()
                .statusCode(is(HttpStatus.SC_OK));
    }

    private void getNoUser(Long userId) {
        given()
            .pathParam("id", userId)
            .when()
            .get(BASE_URL + "/users/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
