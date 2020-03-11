package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleArzt;
import static de.bauhaus.digital.DomainFactory.createSampleBehinderung;
import static de.bauhaus.digital.DomainFactory.createSampleKontakt;
import static de.bauhaus.digital.DomainFactory.createSampleProjekt;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmer;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.bauhaus.digital.DomainFactory;
import de.bauhaus.digital.domain.Arzt;
import de.bauhaus.digital.domain.Behinderung;
import de.bauhaus.digital.domain.Kontakt;
import de.bauhaus.digital.domain.Teilnehmer;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class TeilnehmerControllerTest extends AbstractControllerTest {

    @Test
    public void givenInvalidCredentials_whenAddingTeilnehmer_thenHttp401Unauthorized() {
        Teilnehmer user = createSampleTeilnehmer();

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
    public void givenTeilnehmer_whenAddingTwoTeilnehmerAndRetrievingAll_thenListHasTwoAdditionalEntries() {
        int initialSize = getAllUsers().size();

        addUser(createSampleTeilnehmer());
        addUser(createSampleTeilnehmer());

        List<Teilnehmer> alleTeilnehmer = getAllUsers();

        assertThat(alleTeilnehmer.size(), is(initialSize + 2));
    }

    @Test
    public void givenInvalidId_whenRequestingTeilnehmer_thenNotFound() {
        getNoUser(-1L);
    }

    @Test
    public void givenTeilnehmer_whenAddingAndRetrieving_thenTheyMatch() {
        Teilnehmer user = createSampleTeilnehmer();

        Long userId = addUser(user);

        Teilnehmer responseUser = getUser(userId);

        Assertions.assertThat(responseUser).
                usingRecursiveComparison()
                .ignoringFields("id", "arzt.id", "behinderung.id", "notfallKontakt.id")
                .isEqualTo(user);
    }

    @Test
    public void givenTeilnehmerInDb_whenUpdatingAndRetrieving_thenRetrievedTeilnehmerMatchesUpdated() {

        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Teilnehmer originalTeilnehmer = getUser(teilnehmerId);

        // Verwaltung
        boolean aktiv = true;
        LocalDate registrierungsdatum = LocalDate.now();
        boolean bezahlt = false;
        boolean schulkind = true;
        boolean datenschutzErklaerungAkzeptiert = true;
        boolean teilnahmeBedingungAkzeptiert = true;

        // Grunddaten
        String vorname = "Marianne U";
        String nachname = "Musterfrau U";
        LocalDate geburtsdatum = LocalDate.of(2000, 3, 1);
        String strasse = "Musterstraße U";
        String hausnummer = "4a U";
        String wohnort = "Musterstadt U";
        String postleitzahl = "99999";
        String telefon = "9999999999";
        String email = "update@update.de";
        boolean darfErmaessigtenPreisZahlen = true;

        // Pflichtangaben
        Boolean darfBehandeltWerden = false;
        Boolean darfAlleinNachHause = false;
        Boolean darfReiten = false;
        Boolean darfSchwimmen = false;
        String schwimmAbzeichen = "Seepferdchen U";

        // Allergien, Krankheiten
        String allergien = "Katzenhaare";
        String krankheiten = "Grippe";
        String medikamente = "Tabletten";
        boolean hitzeempfindlich = true;
        boolean essenVegetarier = true;
        boolean essenLaktoseUnvertraeglichkeit = true;
        boolean essenEinerUnvertraeglichkeit = true;
        String essenWeitereLimitierungen = "vegan";
        String krankenkasse = "Krankenkasse";

        // Behinderung
        boolean liegtBehinderungVor = true;

        // Important: We use the copy-builder, so we automatically use the old Arzt, Kontakt and Behinderung and
        // make sure we use the correct IDs
        Teilnehmer teilnehmerToUpdate = Teilnehmer.newBuilder(originalTeilnehmer)
                .aktiv(aktiv)
                .registrierungsdatum(registrierungsdatum)
                .bezahlt(bezahlt)
                .schulkind(schulkind)
                .datenschutzErklaerungAkzeptiert(datenschutzErklaerungAkzeptiert)
                .teilnahmeBedingungAkzeptiert(teilnahmeBedingungAkzeptiert)
                .vorname(vorname)
                .nachname(nachname)
                .geburtsdatum(geburtsdatum)
                .strasse(strasse)
                .hausnummer(hausnummer)
                .wohnort(wohnort)
                .postleitzahl(postleitzahl)
                .telefon(telefon)
                .email(email)
                .darfErmaessigtenPreisZahlen(darfErmaessigtenPreisZahlen)
                .darfBehandeltWerden(darfBehandeltWerden)
                .darfAlleinNachHause(darfAlleinNachHause)
                .darfReiten(darfReiten)
                .darfSchwimmen(darfSchwimmen)
                .schwimmAbzeichen(schwimmAbzeichen)
                .allergien(allergien)
                .krankheiten(krankheiten)
                .medikamente(medikamente)
                .hitzeempfindlich(hitzeempfindlich)
                .essenVegetarier(essenVegetarier)
                .essenLaktoseUnvertraeglichkeit(essenLaktoseUnvertraeglichkeit)
                .essenEinerUnvertraeglichkeit(essenEinerUnvertraeglichkeit)
                .essenWeitereLimitierungen(essenWeitereLimitierungen)
                .krankenkasse(krankenkasse)
                .liegtBehinderungVor(liegtBehinderungVor)
                .build();

        // actual update is here
        updateUser(teilnehmerToUpdate);

        Teilnehmer responseTeilnehmer = getUser(teilnehmerId);

        Assertions.assertThat(responseTeilnehmer).
                usingRecursiveComparison()
                .isEqualTo(teilnehmerToUpdate);
    }

    @Test
    public void givenTeilnehmerInDb_whenUpdatingNestedEntitiesAndRetrieving_thenRetrievedTeilnehmerMatchesUpdated() {

        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Teilnehmer originalTeilnehmer = getUser(teilnehmerId);

        // Important: We use the copy-builders to make sure we use the correct IDs

        Arzt updatedArzt = Arzt.newBuilder(originalTeilnehmer.getArzt())
                .name("Eich U")
                .anschrift("Route 1 Alabastia, 39829 U")
                .telefon("555-6891 U").build();

        Kontakt updatedKontakt = Kontakt.newBuilder(originalTeilnehmer.getNotfallKontakt())
                .name("Igor Eich U")
                .anschrift("Route 4 Neuborkia  96825 U")
                .telefon("555-2532 U")
                .build();

        // Merkzeichen
        boolean merkzeichen_AussergewoehnlicheGehbehinderung_aG = false;
        boolean merkzeichen_Hilflosigkeit_H = false;
        boolean merkzeichen_Blind_Bl = false;
        boolean merkzeichen_Gehoerlos_Gl = false;
        boolean merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B = false;
        boolean merkzeichen_BeeintraechtigungImStrassenverkehr_G = false;
        boolean merkzeichen_Taubblind_TBL = false;

        // Hilfsmittel
        boolean rollstuhlNutzungNotwendig = false;
        String weitereHilfsmittel = "Krücken U";

        // Wertmarke
        boolean wertmarkeVorhanden = false;

        // Begleitperson
        boolean begleitungNotwendig = false;
        boolean begleitpersonPflege = false;
        boolean begleitpersonMedizinischeVersorgung = false;
        boolean begleitpersonMobilitaet = false;
        boolean begleitpersonOrientierung = false;
        boolean begleitpersonSozialeBegleitung = false;
        boolean begleitpersonSinneswahrnehmung = false;

        String eingeschraenkteSinne = "Geruchssinn U";
        String hinweiseZumUmgangMitDemKind = "Nicht unbeaufsichtigt lassen. U";
        boolean unterstuetzungSucheBegleitperson = false;
        String gewohnterBegleitpersonenDienstleister = "Dienstleister U";
        boolean beantragungKostenuebernahmeBegleitperson = false;
        boolean zustimmungWeitergabeDatenAmtFamilieUndSoziales = false;

        Behinderung updatedBehinderung = Behinderung.newBuilder(originalTeilnehmer.getBehinderung())
                .merkzeichen_AussergewoehnlicheGehbehinderung_aG(merkzeichen_AussergewoehnlicheGehbehinderung_aG)
                .merkzeichen_Hilflosigkeit_H(merkzeichen_Hilflosigkeit_H)
                .merkzeichen_Blind_Bl(merkzeichen_Blind_Bl)
                .merkzeichen_Gehoerlos_Gl(merkzeichen_Gehoerlos_Gl)
                .merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B)
                .merkzeichen_BeeintraechtigungImStrassenverkehr_G(merkzeichen_BeeintraechtigungImStrassenverkehr_G)
                .merkzeichen_Taubblind_TBL(merkzeichen_Taubblind_TBL)
                .rollstuhlNutzungNotwendig(rollstuhlNutzungNotwendig)
                .weitereHilfsmittel(weitereHilfsmittel)
                .wertmarkeVorhanden(wertmarkeVorhanden)
                .begleitungNotwendig(begleitungNotwendig)
                .begleitpersonPflege(begleitpersonPflege)
                .begleitpersonMedizinischeVersorgung(begleitpersonMedizinischeVersorgung)
                .begleitpersonMobilitaet(begleitpersonMobilitaet)
                .begleitpersonOrientierung(begleitpersonOrientierung)
                .begleitpersonSozialeBegleitung(begleitpersonSozialeBegleitung)
                .begleitpersonSinneswahrnehmung(begleitpersonSinneswahrnehmung)
                .eingeschraenkteSinne(eingeschraenkteSinne)
                .hinweiseZumUmgangMitDemKind(hinweiseZumUmgangMitDemKind)
                .unterstuetzungSucheBegleitperson(unterstuetzungSucheBegleitperson)
                .gewohnterBegleitpersonenDienstleister(gewohnterBegleitpersonenDienstleister)
                .beantragungKostenuebernahmeBegleitperson(beantragungKostenuebernahmeBegleitperson)
                .zustimmungWeitergabeDatenAmtFamilieUndSoziales(zustimmungWeitergabeDatenAmtFamilieUndSoziales)
                .build();

        Teilnehmer teilnehmerToUpdate = Teilnehmer.newBuilder(originalTeilnehmer)
                .arzt(updatedArzt)
                .notfallKontakt(updatedKontakt)
                .behinderung(updatedBehinderung)
                .build();

        // actual update is here
        updateUser(teilnehmerToUpdate);

        Teilnehmer responseTeilnehmer = getUser(teilnehmerId);

        Assertions.assertThat(responseTeilnehmer).
                usingRecursiveComparison()
                .isEqualTo(teilnehmerToUpdate);
    }

    @Test
    public void givenTeilnehmer_whenDeletedAndRequested_thenIsNotFound() {
        Long userId = addUser(createSampleTeilnehmer());

        Teilnehmer responseUser = getUser(userId);

        Assert.assertThat(responseUser.getId(), is(userId));

        deleteUser(responseUser.getId());

        getNoUser(userId);
    }

    @Test
    public void givenTeilnehmerThatIsAssignedToProjekt_whenDeletingTeilnehmer_thenNoFail() {
        // Given
        Long teilnehmerId = addUser(createSampleTeilnehmer());
        Long projektId = addProject(createSampleProjekt());
        assignUserToProject(projektId, teilnehmerId);

        // When
        deleteUser(teilnehmerId);

        // Then
        // pass :)
    }

    @Test
    public void givenTeilnehmerThatWasCancelledFromProjekt_whenDeletingTeilnehmer_thenNoFail() {
        // Given
        Long userId = addUser(createSampleTeilnehmer());
        Long projectId = addProject(createSampleProjekt());
        assignUserToProject(projectId, userId);
        Boolean isUserUnassignedFromProject = unassignUserFromProject(projectId, userId);
        assertThat(isUserUnassignedFromProject, is(true));

        // When
        deleteUser(userId);

        // Then
        // pass :)
    }

    // Helpers -----------------------------------------------------------------------------------

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
