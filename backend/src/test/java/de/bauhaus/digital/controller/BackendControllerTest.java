package de.bauhaus.digital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.bauhaus.digital.FerienpassApplication;
import de.bauhaus.digital.domain.*;
import de.bauhaus.digital.transformation.AnmeldungJson;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static de.bauhaus.digital.DomainFactory.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = FerienpassApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port = 8089"
)
public class BackendControllerTest {
    private static final String BASE_URL = "http://localhost:8089/api";

    @Value("classpath:requests/anmeldung-post-data.json")
    private Resource anmeldungJsonFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        // Init RestAssured with BasicAuth credentials once - so we don't need to do that on every RestAssured call
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("test");
        authScheme.setPassword("foo");
        RestAssured.authentication = authScheme;
    }

    @Test
    public void login_should_give_http_200_with_right_credentials() {

        get(BASE_URL + "/login")
        .then()
            .statusCode(is(HttpStatus.SC_OK));
    }

    @Test
    public void login_should_give_http_401_unauthorized_with_wrong_credentials() {

        given()
            .auth().basic("wrong", "creds")
        .when()
            .get(BASE_URL + "/login")
        .then()
            .statusCode(is(HttpStatus.SC_UNAUTHORIZED));
    }

    /****************************
     * Test user (Teilnehmer) API
     ****************************/

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
        assertThat(responseUser.getVorname(), is(user.getVorname()));
        assertThat(responseUser.getNachname(), is(user.getNachname()));
    }

    @Test
    public void add_new_user_should_give_http_401_unauthorized_when_credentials_are_wrong() {
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
    public void addNewUserAddSeveralListItemsAndRemoveThemAgain() {
        Teilnehmer user = createSampleUser();

        String allergy = "Arbeiten: Viele Aufgaben und viel reden \n"+
        "Freizeit: Urlaub und Spaß haben";
        user.setAllergien(allergy);

        String nutrition = "Fleisch ist verboten, da Vegetarier\n"+
                "Obst: Sollte dennoch Obst essen";
        user.setEssenLimitierungen(nutrition);

        String illness = "Grippe: Sollte viel Pausen machen\n"+
            "Husten: Immer in den Arm husten";
        user.setKrankheiten(illness);

        //add some drugs
        String drugs = "Nasentropfen: 2x am Tag\n" +
                "Hustensaft: nach dem Essen";
        user.setMedikamente(drugs);

        //add some heat problems
        String heat = "heiss: ausschlag muss behandelt werden";
        user.setHitzeempfindlichkeiten(heat);

        Long userId = addUser(user);

        Teilnehmer responseUser = getUser(userId);

        assertThat(responseUser.getAllergien(), is(allergy));
        assertThat(responseUser.getEssenLimitierungen(), is(nutrition));
        assertThat(responseUser.getKrankheiten(), is(illness));
        assertThat(responseUser.getHitzeempfindlichkeiten(),is(heat));
        assertThat(responseUser.getMedikamente(),is(drugs));
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


        String essenLimitierungen = "Laktoseintoleranz";
        String allergien = "Heuschnupfen: Nasenspray nur 2x am Tag";

        Boolean liegtBehinderungVor = true;
        Behinderung behinderung = new Behinderung();
        behinderung.setRollstuhlNutzungNotwendig(true);
        behinderung.setMerkzeichen_Hilflosigkeit_H(true);
        behinderung.setWertmarkeVorhanden(true);

        String medikamente = "Nasenspray von Forte: 2x am Tag";

        String hitzeempfindlichkeiten = "grosse Hitze: eincremen";

        String vorname = "Klaus";
        String nachname = "Klausen";
        LocalDate geburtsdatum = LocalDate.of(1999, 12, 31);
        String strasse = "Bahnhofstraße 5";
        String stadt = "Erfurt";
        String plz = "99082";
        String telefon = "03544444";
        String krankenkasse = "AOK";
        String email = "myEmail@weimar.de";
        Teilnehmer klausKlausen = new Teilnehmer(
                vorname,
                nachname,
                geburtsdatum,
                LocalDate.now(),
                strasse,
                stadt,
                plz,
                telefon,
                krankenkasse,
                false,
                kontakt,
                true,
                false,
                true,
                "Seepferdchen",
                false,
                true,
                arzt,
                allergien,
                essenLimitierungen,
                krankheiten,
                liegtBehinderungVor,
                behinderung,
                hitzeempfindlichkeiten,
                medikamente,
                email);

        // Exlicitely set Id of User to update, so our implementation can find it
        klausKlausen.setId(userId);

        updateUser(klausKlausen);

        Teilnehmer responseUser = getUser(userId);


        Assert.assertThat(responseUser.getVorname(),is(vorname));
        Assert.assertThat(responseUser.getNachname(),is(nachname));
        Assert.assertThat(responseUser.getGeburtsdatum(),is(geburtsdatum));
        Assert.assertThat(responseUser.getStrasse(),is(strasse));
        Assert.assertThat(responseUser.getPostleitzahl(),is(plz));
        Assert.assertThat(responseUser.getStadt(),is(stadt));
        Assert.assertThat(responseUser.getTelefon(),is(telefon));
        Assert.assertThat(responseUser.getKrankenkasse(),is(krankenkasse));
        Assert.assertThat(responseUser.getNotfallKontakt().getName(),is(kontakt.getName()));
        Assert.assertThat(responseUser.getNotfallKontakt().getAddress(),is(kontakt.getAddress()));
        Assert.assertThat(responseUser.getNotfallKontakt().getTelephone(),is(kontakt.getTelephone()));
        Assert.assertThat(responseUser.getArzt().getName(),is(arzt.getName()));
        Assert.assertThat(responseUser.getArzt().getAddress(),is(arzt.getAddress()));
        Assert.assertThat(responseUser.getArzt().getTelephone(),is(arzt.getTelephone()));
        Assert.assertThat(responseUser.getAllergien(),is(allergien));
        Assert.assertThat(responseUser.getKrankheiten(),is(krankheiten));
        Assert.assertThat(responseUser.getEssenLimitierungen(),is(essenLimitierungen));
        Assert.assertThat(responseUser.getMedikamente(),is(medikamente));
        Assert.assertThat(responseUser.getHitzeempfindlichkeiten(),is(hitzeempfindlichkeiten));
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



    /*******************************************
     * Tests API for registering from Ferienpass-Anmeldung Microservice
     ******************************************/

    @Test
    public void addNewTeilnehmerFromFerienpassAnmeldungMicroservice() throws IOException {

        // Zuerst fuer klare Verhältnisse sorgen und Seiteneffekte vermeiden!
        // Daher neue Projekte anlegen ...
        Long pizzaBackenId = addProjekt(createSampleProject(
                "Pizza backen",
                LocalDate.of(2018, 7, 12),
                LocalDate.of(2018, 7, 13),
                15,
                3));
        Long fussballId = addProjekt(createSampleProject(
                "Fussball",
                LocalDate.of(2018, 8, 14),
                LocalDate.of(2018, 8, 17),
                10,
                7));
        Long golfSpielenId = addProjekt(createSampleProject(
                "Golf spielen",
                LocalDate.of(2018, 7, 2),
                LocalDate.of(2018, 7, 2),
                18,
                5));

        // ... und deren Ids im anmeldung-post-data.json ueberschreiben
        AnmeldungJson anmeldungJson = objectMapper.readValue(anmeldungJsonFile.getInputStream(), AnmeldungJson.class);
        anmeldungJson.getProjects().get(0).setId(pizzaBackenId.intValue());
        anmeldungJson.getProjects().get(1).setId(fussballId.intValue());
        anmeldungJson.getProjects().get(2).setId(golfSpielenId.intValue());

        // Nun den API-call aus dem Anmeldungs-Frontend ausfuehren
        Long userId = registerNewUserFromAnmeldungFrontend(anmeldungJson);

        Teilnehmer responseUser = getUser(userId);

        assertThat(responseUser.getVorname(), is("Paul"));
        assertThat(responseUser.getNachname(), is("Siegmund"));
        assertThat(responseUser.getGeburtsdatum(), is(LocalDate.of(2019,1,10)));
        assertThat(responseUser.getStrasse(), is("Rainer-Maria-Rilke-Strasse 33"));
        assertThat(responseUser.getPostleitzahl(), is("99423"));
        assertThat(responseUser.getStadt(), is("Weimar"));
        assertThat(responseUser.getTelefon(), is("03643 / 123456"));
        assertThat(responseUser.getEmail(), is("luigi.mueller@web.de"));

        pruefeProjekte(responseUser, pizzaBackenId, fussballId, golfSpielenId);
        pruefeAllergienKrankheitenEtc(responseUser);
        pruefeBehinderungsdaten(responseUser);
        pruefeErklaerung(responseUser);
    }

    private void pruefeProjekte(Teilnehmer responseUser, Long pizzaBackenId, Long fussballId, Long golfSpielenId) {
        debugOutProjekte();

        // Ist der User in Projekt Pizza backen & Golf spielen angemeldet?
        List<Teilnehmer> anmeldungenPizzaBacken = getProjekt(pizzaBackenId).getAnmeldungen();
        assertThat(containsTeilnehmer(responseUser, anmeldungenPizzaBacken), is(true));

        List<Teilnehmer> anmeldungenFussball = getProjekt(fussballId).getAnmeldungen();
        assertThat(containsTeilnehmer(responseUser, anmeldungenFussball), is(false));

        List<Teilnehmer> anmeldungenGolfSpielen = getProjekt(golfSpielenId).getAnmeldungen();
        assertThat(containsTeilnehmer(responseUser, anmeldungenGolfSpielen), is(true));
    }

    @Test
    public void pruefeRegistrierungProjekteBeiApiCallAnmeldungMicroservice() throws IOException {
        // Zuerst fuer klare Verhältnisse sorgen und Seiteneffekte vermeiden!
        // Daher neue Projekte anlegen ...
        Long pizzaBackenId = addProjekt(createSampleProject(
                "Pizza backen",
                LocalDate.of(2018, 7, 12),
                LocalDate.of(2018, 7, 13),
                8,
                3));
        Long fussballId = addProjekt(createSampleProject(
                "Fussball",
                LocalDate.of(2018, 8, 14),
                LocalDate.of(2018, 8, 17),
                10,
                7));
        Long golfSpielenId = addProjekt(createSampleProject(
                "Golf spielen",
                LocalDate.of(2018, 7, 2),
                LocalDate.of(2018, 7, 2),
                9,
                5));

        // ... und deren Ids im anmeldung-post-data.json ueberschreiben
        AnmeldungJson anmeldungJson = objectMapper.readValue(anmeldungJsonFile.getInputStream(), AnmeldungJson.class);
        anmeldungJson.getProjects().get(0).setId(pizzaBackenId.intValue());
        anmeldungJson.getProjects().get(1).setId(fussballId.intValue());
        anmeldungJson.getProjects().get(2).setId(golfSpielenId.intValue());

        // Nun den API-call aus dem Anmeldungs-Frontend ausfuehren
        Long userId = registerNewUserFromAnmeldungFrontend(anmeldungJson);

        // Pizza backen startet mit 15 Slots gesamt und 3 reserviert
        // Da im anmeldung-post-data.json Pizza backen 1x fuer den Teilnehmer reserviert wird
        // sollten jetzt nur noch 8 - 3 - 1 = 4 Plaetze frei sein - sowie 4 reserviert
        Projekt pizzaBacken = getProjekt(pizzaBackenId);
        assertThat(pizzaBacken.getSlotsReserviert(), is(4));
        assertThat(pizzaBacken.getSlotsFrei(), is(4));

        // Fussball 10 gesamt - 7 reserviert - keine Anmeldung = 3 frei bzw. 7 reserviert
        Projekt fussball = getProjekt(fussballId);
        assertThat(fussball.getSlotsReserviert(), is(7));
        assertThat(fussball.getSlotsFrei() , is(3));

        // Golf spielen 9 gesamt - 5 reserviert - 1 Anmeldung = 3 frei bzw. 6 reserviert
        Projekt golfSpielen = getProjekt(golfSpielenId);
        assertThat(golfSpielen.getSlotsReserviert(), is(6));
        assertThat(golfSpielen.getSlotsFrei(), is(3));

        // Slotsfrei bei Fussball auf 0 fahren
        // Dafuer Pizza backen nicht mehr reservieren
        setzeAnmeldungFuerPizza(anmeldungJson, false);
        // aber Fussball
        setzeAnmeldungFuerFussball(anmeldungJson, true);
        // Golf spielen auch nicht
        setzeAnmeldungFuerGolfSpielen(anmeldungJson, false);

        // 3 Teilnehmer auf Fussball registrieren - 3 Frontend-API-calls
        registerNewUserFromAnmeldungFrontend(anmeldungJson);
        registerNewUserFromAnmeldungFrontend(anmeldungJson);
        registerNewUserFromAnmeldungFrontend(anmeldungJson);

        Projekt fussballNach3Anmeldungen = getProjekt(fussballId);
        assertThat(fussballNach3Anmeldungen.getSlotsFrei(), is(0));
        assertThat(fussballNach3Anmeldungen.getSlotsReserviert(), is(10));

        // Wenn wir noch einen Teilnehmer auf Fussball reservieren wollen,
        // sollte die API uns einen HTTP 409 schicken und das Projekt Fussball
        // zurueckgeben als Projekt, das keinen Platz mehr frei hat
        // siehe https://github.com/digital-bauhaus/Ferienpass-Admin/issues/27
        List<Long> projekteOhneFreieSlots = registerNewUserFromAnmeldungFrontendForEmptySlotProjekts(anmeldungJson);
        assertThat(projekteOhneFreieSlots.iterator().next(), is(fussballId));

        // Wie sieht das bei mehreren Projekten aus, die nicht mehr frei sind?
        // Dafuer setzen reservieren wir auch noch alle freien Slots von Golf spielen
        // Dafuer muessen wir Fussball wieder de-registrieren (sonst laufen wir ja gleich in den Fehler)
        // und Golf registrieren
        setzeAnmeldungFuerFussball(anmeldungJson, false);
        setzeAnmeldungFuerGolfSpielen(anmeldungJson, true);
        registerNewUserFromAnmeldungFrontend(anmeldungJson);
        registerNewUserFromAnmeldungFrontend(anmeldungJson);
        registerNewUserFromAnmeldungFrontend(anmeldungJson);
        // Nun sollte Golf spielen auch voll sein
        Projekt golfSpielenNach3WeiterenAnmeldungen = getProjekt(golfSpielenId);
        assertThat(golfSpielenNach3WeiterenAnmeldungen.getSlotsFrei(), is(0));

        // Nun auch wieder fuer Fussball registrieren wollen
        setzeAnmeldungFuerFussball(anmeldungJson, true);

        // Wenn wir noch einen Teilnehmer auf Fussball & Golf spielen reservieren wollen,
        // sollte die API uns wieder einen HTTP 409 schicken und die Projekte Fussball
        // und Golf spielen als Liste der Projekte zurueckgeben, die keinen Platz mehr
        // frei haben (https://github.com/digital-bauhaus/Ferienpass-Admin/issues/27)
        projekteOhneFreieSlots = registerNewUserFromAnmeldungFrontendForEmptySlotProjekts(anmeldungJson);

        assertThat(projekteOhneFreieSlots.size(), is(2));
        assertThat(projekteOhneFreieSlots, hasItem(golfSpielenId));
        assertThat(projekteOhneFreieSlots, hasItem(fussballId));
        assertThat(projekteOhneFreieSlots, not(hasItem(pizzaBackenId)));

        // Nun haben wir 2 ausgebuchte Projekte und nur Pizza backen hat noch Slots frei
        // Wenn ein Teilnehmer sich auf ausgebuchte Projekte nicht mehr anmelden kann,
        // dann soll auch sichergestellt sein, dass er sich nicht gleichzeitig auf
        // noch freie Projekte angemeldet hat mit dem API-Aufruf

        // Hierfuer nehmen wir einen neuen Teilnehmer auf Basis des anmeldung.json
        setzeNeuenNamen(anmeldungJson, "Luis", "Fernandez");

        // Pizza sollte nun noch 4 Plätze frei haben
        // Fussball und Golf keine mehr
        assertThat(getProjekt(pizzaBackenId).getSlotsFrei(), is(4));
        assertThat(getProjekt(fussballId).getSlotsFrei(), is(0));
        assertThat(getProjekt(golfSpielenId).getSlotsFrei(), is(0));

        // Nun versuchen wir unseren Luis Fernandez zu registrieren
        registerNewUserFromAnmeldungFrontendForEmptySlotProjekts(anmeldungJson);
        Projekt pizzaNachGemischtemRequest = getProjekt(pizzaBackenId);
        for(Teilnehmer angemeldeterTeilnehmer : pizzaNachGemischtemRequest.getAnmeldungen()) {
            assertThat(angemeldeterTeilnehmer.getNachname(), not("Fernandez"));
        }

        // Wir haben nun immer noch 2 ausgebuchte Projekte
        // Wenn ein Teilnehmer nun NUR ein Projekt der beiden un-checked und eines der
        // vollen Projekte trotzdem weiterhin auf checked setzt, soll in der Antwort
        // die gesamte Liste aller Projekte zurückgeliefert werden, die
        // keine Plätze mehr frei haben
        setzeAnmeldungFuerFussball(anmeldungJson, false);
        setzeAnmeldungFuerGolfSpielen(anmeldungJson, true);
        setzeAnmeldungFuerPizza(anmeldungJson, false);

        projekteOhneFreieSlots = registerNewUserFromAnmeldungFrontendForEmptySlotProjekts(anmeldungJson);
        assertThat(projekteOhneFreieSlots, hasItem(fussballId));
        assertThat(projekteOhneFreieSlots, hasItem(golfSpielenId));
        assertThat(projekteOhneFreieSlots, not(hasItem(pizzaBackenId)));

    }

    private void setzeNeuenNamen(AnmeldungJson anmeldungJson, String vorname, String nachname) {
        anmeldungJson.setBaseForename(vorname);
        anmeldungJson.setBaseFamilyName(nachname);
    }

    private void setzeAnmeldungFuerPizza(AnmeldungJson anmeldungJson, boolean registriert) {
        anmeldungJson.getProjects().get(0).setRegistered(registriert);
    }

    private void setzeAnmeldungFuerFussball(AnmeldungJson anmeldungJson, boolean registriert) {
        anmeldungJson.getProjects().get(1).setRegistered(registriert);
    }

    private void setzeAnmeldungFuerGolfSpielen(AnmeldungJson anmeldungJson, boolean registriert) {
        anmeldungJson.getProjects().get(2).setRegistered(registriert);
    }

    private void pruefeErklaerung(Teilnehmer responseUser) {
        assertThat(responseUser.isDarfAlleinNachHause(), is(true));
        assertThat(responseUser.isDarfReiten(), is(false));
        assertThat(responseUser.isDarfSchwimmen(), is(false));
    }

    private void pruefeBehinderungsdaten(Teilnehmer responseUser) {
        assertThat(responseUser.isLiegtBehinderungVor(), is(true));
        Behinderung behinderung = responseUser.getBehinderung();

        // Merkzeichen
        assertThat(behinderung.isMerkzeichen_AussergewoehnlicheGehbehinderung_aG(), is(true));
        assertThat(behinderung.isMerkzeichen_Hilflosigkeit_H(), is(false));
        assertThat(behinderung.isMerkzeichen_Blind_Bl(), is(false));
        assertThat(behinderung.isMerkzeichen_Gehoerlos_Gl(), is(true));
        assertThat(behinderung.isMerkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(), is(false));
        assertThat(behinderung.isMerkzeichen_BeeintraechtigungImStrassenverkehr_G(), is(true));
        assertThat(behinderung.isMerkzeichen_Taubblind_TBL(), is(true));

        assertThat(behinderung.isRollstuhlNutzungNotwendig(), is(true));
        assertThat(behinderung.getWeitereHilfsmittel(), is("Krücken"));
        assertThat(behinderung.isWertmarkeVorhanden(), is(true));

        // Begleitperson
        assertThat(behinderung.isBegleitungNotwendig(), is(true));
        assertThat(behinderung.isBegleitpersonPflege(), is(false));
        assertThat(behinderung.isBegleitpersonMedizinischeVersorgung(), is(true));
        assertThat(behinderung.isBegleitpersonMobilitaet(), is(false));
        assertThat(behinderung.isBegleitpersonOrientierung(), is(false));
        assertThat(behinderung.isBegleitpersonSozialeBegleitung(), is(true));

        assertThat(behinderung.getEingeschraenkteSinne(), is("Sicht; Gehör; Geschmack; Geruch"));

        assertThat(behinderung.getHinweiseZumUmgangMitDemKind(), is("Bei unserem Kind ist insbesondere darauf zu achten, dass es manchmal spontan..."));
        assertThat(behinderung.isUnterstuetzungSucheBegleitpersonNotwendig(), is(true));
        assertThat(behinderung.getGewohnterBegleitpersonenDienstleister(), is("Mensch im Mittelpunkt e.V."));
        assertThat(behinderung.isBeantragungKostenuebernahmeBegleitpersonNotwendig(), is(false));
    }

    private void pruefeAllergienKrankheitenEtc(Teilnehmer responseUser) {
        String expectedHealthInsurance = "Techniker Krankenkasse";
        assertThat(responseUser.getKrankenkasse(),is(expectedHealthInsurance));

        //ToDo: Woher kommen die Daten dieses Users? -> aus resources/requests/anmeldung-post-data.json
        String expextedAllergies = "Heuschnupfen\nHausstaub\nNussallergie\nKatzenhaarallergie\nRegenallergie";
        assertThat(responseUser.getAllergien(),is(expextedAllergies));

        String expextedIllnesses = "Epilepsie\nSchnupfen\nHalschmerzen\nKopfschmerzen\nBauchschmerzen\n";
        assertThat(responseUser.getKrankheiten(),is(expextedIllnesses));

        String expextedNutrition = "Schokoladenunverträglichkeit\nSofortiges Kotzen nach Nutellagenuss\nWasserunverträglichkeit\n" +
                "Weizenunverträglichkeit\nBierunverträglichkeit\n";
        assertThat(responseUser.getEssenLimitierungen(),is(expextedNutrition));


        assertThat(responseUser.isErlaubeMedikamentation(), is(false));

        Kontakt notfallKontakt = responseUser.getNotfallKontakt();
        assertThat(notfallKontakt.getName(), is("Andreas Müller"));
        assertThat(notfallKontakt.getAddress(), is("Werner-Heisenberg-Straße 5"));
        assertThat(notfallKontakt.getTelephone(), is("0172/34012875"));

        Arzt hausarzt = responseUser.getArzt();
        assertThat(hausarzt.getName(), is("Dr. Martin Schreiber"));
        assertThat(hausarzt.getAddress(), is("Amadeusstrasse 2"));
        assertThat(hausarzt.getTelephone(), is("0364 / 0123456"));
    }

    private boolean containsTeilnehmer(Teilnehmer teilnehmer, List<Teilnehmer> anmeldungen) {
        for (Teilnehmer angemeldeterTeilnehmer : anmeldungen) {
            if(angemeldeterTeilnehmer.getId() == teilnehmer.getId())
                return true;
        }
        return false;
    }

    private Projekt getProjekt(List<Projekt> projekte, String projektname) {
        for (Projekt projekt : projekte) {
            System.out.println("Projekt: " + projekt.getName());
            if(projektname.equals(projekt.getName())) {
                return projekt;
            }
        }
        return null;
    }


    /****************************
     * Test project (Projekt) API
     ****************************/

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
        assertThat(responeProjekt.getSlotsFrei(), is(projekt.getSlotsFrei()));
        assertThat(responeProjekt.getKosten(), is(projekt.getKosten()));
        assertThat(responeProjekt.getMindestAlter(), is(projekt.getMindestAlter()));
        assertThat(responeProjekt.getHoechstAlter(), is(projekt.getHoechstAlter()));
        assertThat(responeProjekt.getDatum(), is(projekt.getDatum()));
        assertThat(responeProjekt.getSlotsGesamt(), is(projekt.getSlotsGesamt()));
        assertThat(responeProjekt.getWebLink(), is(projekt.getWebLink()));
        assertThat(responeProjekt.getAnmeldungen(), is(projekt.getAnmeldungen()));
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
        assertThat(responseProjekt.getAnmeldungen().size(), is(1));
        assertThat(responseProjekt.getAnmeldungen().get(0).getId(), is(responseUser.getId()));
    }

    @Test
    public void addProjectAndSetItToInactive() {
        //Create a project
        Projekt projekt = createSampleProject();
        Long projectID = addProjekt(projekt);
        assertThat(projekt.isAktiv(),is(true));

        //set project inactive
        assertThat(setProjektInactive(projectID), is(true));

        //retrieve project
        Projekt responseProjekt = getProjekt(projectID);

        assertThat(projectID, is(responseProjekt.getId()));
        assertThat(projekt.getName(), is(responseProjekt.getName()));
        assertThat(responseProjekt.isAktiv(),is(false));
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
            sumOfRegisteredTeilnehmer += projekt.getAnmeldungen().size();
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
            newSumOfRegisteredTeilnehmer += projekt.getAnmeldungen().size();
        }
        //4 Assignments
        assertThat(newSumOfRegisteredTeilnehmer,is(sumOfRegisteredTeilnehmer+4));
    }

    @Test
    public void shouldAssignUserCorrectlyToProjekt() throws Exception {
        // Given
        Long projectId = addProjekt(createSampleProject());

        Teilnehmer newUser = createSampleUser();
        newUser.setVorname("Anton");
        newUser.setNachname("Tirol");
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

        Projekt projekt = getProjekt(projectId);
        projekt.setName("Klettern am Berg");
        projekt.setDatum(LocalDate.of(2019, 8,3));
        projekt.setDatumEnde(LocalDate.of(2019, 9,2));
        projekt.setSlotsReserviert(6);
        projekt.setSlotsGesamt(25);


        Projekt updatedProjekt = updateProjekt(projekt);

        assertThat(updatedProjekt.getName(), is(projekt.getName()));
        assertThat(updatedProjekt.getDatum(), is(projekt.getDatum()));
        assertThat(updatedProjekt.getDatumEnde(), is(projekt.getDatumEnde()));
        assertThat(updatedProjekt.getSlotsReserviert(), is(projekt.getSlotsReserviert()));
        assertThat(updatedProjekt.getSlotsGesamt(), is(projekt.getSlotsGesamt()));
    }

    @Test
    public void shouldDeleteAddedProjectCorrectly() {
        Long projectId = addProjekt(createSampleProject());

        Boolean isProjectDeleted = deleteProjekt(projectId);

        assertThat(isProjectDeleted, is(true));
    }

    @Test
    public void givenEndeDatumBeforeDatum_whenCreatingProjekt_thenBadRequest() {
        Projekt projekt = createSampleProject();

        projekt.setDatum(LocalDate.of(2019, 12, 5));
        projekt.setDatumEnde(LocalDate.of(2019, 12, 4));

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
        Projekt projekt = createSampleProject();

        projekt.setHoechstAlter(10);
        projekt.setMindestAlter(11);

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
        Projekt projekt = createSampleProject();

        projekt.setSlotsReserviert(10);
        projekt.setSlotsGesamt(5);

        given()
                .body(projekt)
                .contentType(ContentType.JSON)
            .when()
                .post(BASE_URL+"/projects")
                .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasSize(1))
                .body("errors[0].field", is("slotsReserviert"));
    }

    @Test
    public void givenProjektWithOneTeilnehmer_whenUpdatingProjektWithZeroSlotsReserved_thenBadRequest() {
        Long projectId = addProjekt(createSampleProject());
        Long userId = addUser(createSampleUser());

        assignUser2Projekt(projectId, userId);

        Projekt projekt = getProjekt(projectId);

        projekt.setSlotsReserviert(0);

        given()
                .body(projekt)
                .contentType(ContentType.JSON)
            .when()
                .put(BASE_URL+"/projects")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasSize(1))
                .body("errors[0].field", is("slotsReserviert"));
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

    private Long addUser(Teilnehmer teilnehmer) {
        return given()
                    .body(teilnehmer)
                    .contentType(ContentType.JSON)
                .when()
                    .post(BASE_URL + "/users")
                .then()
                    .statusCode(is(HttpStatus.SC_CREATED))
                    .extract()
                        .body().as(Long.class);
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
                .statusCode(is(HttpStatus.SC_NO_CONTENT));
    }

    private Long registerNewUserFromAnmeldungFrontend(AnmeldungJson anmeldungJson) {
        return given()
                .contentType(ContentType.JSON)
                .body(anmeldungJson)
                .when()
                .post(BASE_URL + "/register")
                .then()
                .statusCode(is(HttpStatus.SC_CREATED))
                .extract()
                .body().as(Long.class);
    }

    private List<Long> registerNewUserFromAnmeldungFrontendForEmptySlotProjekts(AnmeldungJson anmeldungJson) {
        return Arrays.asList(given()
                .contentType(ContentType.JSON)
                .body(anmeldungJson)
                .when()
                .post(BASE_URL + "/register")
                .then()
                .statusCode(is(HttpStatus.SC_CONFLICT))
                .extract()
                .body().as(Long[].class));
    }

    private Projekt getProjekt(Long projectID) {
        return given()
                .pathParam("projekt_id", projectID)
                .when()
                .get(BASE_URL + "/projects/{projekt_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().as(Projekt.class);
    }

    private Long addProjekt(Projekt projekt) {
        return given()
                    .body(projekt)
                    .contentType(ContentType.JSON)
               .when()
                    .post(BASE_URL+"/projects")
               .then()
                    .statusCode(is(HttpStatus.SC_CREATED))
                    .extract()
                        .body().as(Long.class);
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

    private List<Projekt> getAllProjects() {
        return Arrays.asList(given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL + "/projects")
                        .then()
                        .statusCode(is(HttpStatus.SC_OK))
                        .extract()
                        .body().as(Projekt[].class));
    }

    private List<Teilnehmer> getAllUsers() {
        return Arrays.asList(given()
                .when()
                .get(BASE_URL + "/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Teilnehmer[].class));
    }

    private Boolean assignUser2Projekt(Long projektId, Long userId) {
        return given()
                    .pathParam("projektId", projektId)
                    .pathParam("userId", userId)
                    .contentType(ContentType.JSON)
                .when()
                    .put(BASE_URL + "/projects/{projektId}/users/{userId}")
                .then()
                    .statusCode(is(HttpStatus.SC_OK))
                    .extract().body().as(Boolean.class);
    }

    private Boolean unassignUserFromProjekt(Long projektId, Long userId) {
        return given()
                    .pathParam("projektId", projektId)
                    .pathParam("userId", userId)
                    .contentType(ContentType.JSON)
                .when()
                    .delete(BASE_URL + "/projects/{projektId}/users/{userId}")
                .then()
                    .statusCode(is(HttpStatus.SC_OK))
                    .extract().body().as(Boolean.class);
    }

    private Teilnehmer getUser(Long userId) {
        return given()
                .pathParam("id", userId)
            .when()
                .get(BASE_URL + "/users/{id}")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().as(Teilnehmer.class);
    }

    private void getNoUser(Long userId) {
            given()
                .pathParam("id", userId)
                .when()
                .get(BASE_URL + "/users/{id}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }


    private boolean setProjektInactive(Long projektId) {
        return given()
                    .pathParam("projekt_id", projektId)
                .when()
                    .put(BASE_URL + "/projects/disable/{projekt_id}")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(Boolean.class);
    }

    private Boolean deleteProjekt(Long projektId) {
        return given()
                    .pathParam("projekt_id", projektId)
                    .contentType(ContentType.JSON)
                .when()
                    .delete(BASE_URL + "/projects/{projekt_id}")
                .then()
                    .statusCode(is(HttpStatus.SC_OK))
                    .extract().body().as(Boolean.class);

    }

    private void debugOutProjekte() {
        List<Projekt> allProjects = getAllProjects();

        allProjects.forEach(projekt -> System.out.println(projekt.getId() + ", Name: " + projekt.getName()));
        allProjects.forEach(projekt -> {
            projekt.getAnmeldungen().forEach(teilnehmer -> System.out.println(projekt.getId() + ", Teilnehmer: " + teilnehmer.getVorname()));

        });
        System.out.println("Projekte " + allProjects);
    }
}
