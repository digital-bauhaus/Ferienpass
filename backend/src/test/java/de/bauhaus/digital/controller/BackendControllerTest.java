package de.bauhaus.digital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.bauhaus.digital.FerienpassAdminApplication;
import de.bauhaus.digital.domain.*;
import de.bauhaus.digital.repository.TeilnehmerRepositoryTest;
import de.bauhaus.digital.transformation.AnmeldungJson;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = FerienpassAdminApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port = 8089"
)
public class BackendControllerTest {
    private static final String BASE_URL = "http://localhost:8089/api";

    @Value("classpath:requests/anmeldung-post-data.json")
    private Resource anmeldungJsonFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    private TeilnehmerRepositoryTest teilnehmerRepositoryTest = new TeilnehmerRepositoryTest();

    /****************************
     * Test user (Teilnehmer) API
     ****************************/
    @Test
    public void addNewUserAndRetrieveItBack() {
        Teilnehmer user = teilnehmerRepositoryTest.createUser();

        Long userId = addUser(user);

        Teilnehmer responseUser = getUser(userId);
        assertThat(responseUser.getVorname(), is(user.getVorname()));
        assertThat(responseUser.getNachname(), is(user.getNachname()));
    }

    @Test
    public void addNewUserAddSeveralListItemsAndRemoveThemAgain() {
        Teilnehmer user = teilnehmerRepositoryTest.createUser();

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

        Long userId = addUser(teilnehmerRepositoryTest.createUser());
        Long userId2 = addUser(teilnehmerRepositoryTest.createUser());

        List<Teilnehmer> allUsers = getAllUsers();

        assertThat(allUsers.size()-initialSize, is(2));
        long id1 = allUsers.get(initialSize).getId();
        long id2 = allUsers.get(initialSize+1).getId();
        assertThat(id1,is(userId));
        assertThat(id2,is(userId2));

    }

    @Test
    public void isUserUpdatedCorrectlyThroughParamApi() throws  Exception {
        Teilnehmer newUser = teilnehmerRepositoryTest.createUser();
        newUser.setVorname("Max");
        newUser.setNachname("Mustermann");


        Long userId =
                given()
                        .body(newUser)
                        .contentType(ContentType.JSON)
                        .when()
                        .post(BASE_URL + "/adduser")
                        .then()
                        .statusCode(is(HttpStatus.SC_CREATED))
                        .extract()
                        .body().as(Long.class);



        String vorname = "Klaus";
        String nachname = "Klausen";
        String geburtsdatum = "1999,12,31";
        String strasse = "Bahnhof 5";
        String plz = "00111";
        String stadt = "Erfurt";
        String tel = "999994444";
        String krankenkasse = "AOK";
        String kontaktName = "AlaramKontakt";
        String kontaktAdresse = "Hinter dem Dorf 4";
        String kontaktTel = "0101010101";
        String arztName = "Doktor Who";
        String arztAdresse = "Arzthaus 1";
        String arztTel = "5555";
        boolean erlaubeMedikamentation = false;
        boolean darfSchwimmen = true;
        boolean darfReiten = false;
        boolean darfAlleinNachHause = true;
        String schwimmAbzeichen = "Seepferdchen";
        boolean bezahlt = false;
        boolean darfBehandeltWerden = true;
        boolean liegtBehinderungVor = true;
        boolean behinderungG = false;
        boolean behinderungH = true;
        boolean behinderungAG = false;
        boolean behinderungB1 = false;
        boolean behinderungG1 = false;
        boolean behinderungB = false;
        boolean behinderungTBL = false;
        boolean rollstuhl = true;
        String behinderungHilfsmittel = "";
        boolean wertMarke = true;
        boolean begleitungNotwending = false;
        boolean begleitPflege = false;
        boolean begleitMedVor = false;
        boolean begleitMobilität = false;
        boolean begleitOrientierung = false;
        boolean begleitSozial = false;
        String eingeschränkteSinne = "";
        String hinweiseZumUmgang = "";
        boolean behinderungUnterstützung = false;
        String untersützungKontakt = "";
        boolean kostenÜbernahme = true;
        String allergien="Heuschnupfen";
        String krankheiten = "Grippaler Infekt";
        String essenLimitierungen = "Käfer";
        String medikamete = "Bio Bier";
        String hitzeempfindlichkeiten = "Hat er";

        Teilnehmer updatedTeilnehmer =
                given()
                        .param("userId", userId)
                        .param("vorname",vorname)
                        .param("nachname",nachname)
                        .param("geburtsdatum",geburtsdatum)
                        .param("strasse",strasse)
                        .param("plz",plz)
                        .param("stadt",stadt)
                        .param("tel",tel)
                        .param("krankenkasse",krankenkasse)
                        .param("kontaktName",kontaktName)
                        .param("kontaktAdresse",kontaktAdresse)
                        .param("kontaktTel",kontaktTel)
                        .param("arztName",arztName)
                        .param("arztAdresse",arztAdresse)
                        .param("arztTel",arztTel)
                        .param("erlaubeMedikamentation", erlaubeMedikamentation)
                        .param("darfSchwimmen", darfSchwimmen)
                        .param("darfReiten", darfReiten)
                        .param("darfAlleinNachHause", darfAlleinNachHause)
                        .param("schwimmAbzeichen", schwimmAbzeichen)
                        .param("bezahlt", bezahlt)
                        .param("darfBehandeltWerden", darfBehandeltWerden)
                        .param("liegtBehinderungVor", liegtBehinderungVor)
                        .param("behinderungG", behinderungG)
                        .param("behinderungH", behinderungH)
                        .param("behinderungAG", behinderungAG)
                        .param("behinderungB1", behinderungB1)
                        .param("behinderungG1", behinderungG1)
                        .param("behinderungB", behinderungB)
                        .param("behinderungTBL", behinderungTBL)
                        .param("rollstuhl", rollstuhl)
                        .param("behinderungHilfsmittel", behinderungHilfsmittel)
                        .param("wertMarke", wertMarke)
                        .param("begleitungNotwending", begleitungNotwending)
                        .param("begleitPflege", begleitPflege)
                        .param("begleitMedVor", begleitMedVor)
                        .param("begleitMobilität", begleitMobilität)
                        .param("begleitOrientierung", begleitOrientierung)
                        .param("begleitSozial", begleitSozial)
                        .param("eingeschränkteSinne", eingeschränkteSinne)
                        .param("hinweiseZumUmgang", hinweiseZumUmgang)
                        .param("behinderungUnterstützung", behinderungUnterstützung)
                        .param("untersützungKontakt", untersützungKontakt)
                        .param("kostenÜbernahme", kostenÜbernahme)
                        .param("allergien",allergien)
                        .param("krankheiten",krankheiten)
                        .param("essenLimitierungen",essenLimitierungen)
                        .param("hitzeempfindlichkeiten",hitzeempfindlichkeiten)
                        .param("medikamente",medikamete)
                        .when()
                        .get(BASE_URL+"/updateUser")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().as(Teilnehmer.class);

        Teilnehmer responseUser =
                given()
                        .pathParam("id", userId)
                        .when()
                        .get(BASE_URL + "/user/{id}")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .assertThat()
                        .extract().as(Teilnehmer.class);

        Assert.assertThat(responseUser.getVorname(),is(vorname));
        Assert.assertThat(responseUser.getNachname(),is(nachname));
        String[] datum = geburtsdatum.split(",");
        Assert.assertThat(responseUser.getGeburtsdatum(),is(LocalDate.of(Integer.parseInt(datum[0]),Integer.parseInt(datum[1]),Integer.parseInt(datum[2]))));
        Assert.assertThat(responseUser.getStrasse(),is(strasse));
        Assert.assertThat(responseUser.getPostleitzahl(),is(plz));
        Assert.assertThat(responseUser.getStadt(),is(stadt));
        Assert.assertThat(responseUser.getTelefon(),is(tel));
        Assert.assertThat(responseUser.getKrankenkasse(),is(krankenkasse));
        Assert.assertThat(responseUser.getNotfallKontakt().getName(),is(kontaktName));
        Assert.assertThat(responseUser.getNotfallKontakt().getAddress(),is(kontaktAdresse));
        Assert.assertThat(responseUser.getNotfallKontakt().getTelephone(),is(kontaktTel));
        Assert.assertThat(responseUser.getArzt().getName(),is(arztName));
        Assert.assertThat(responseUser.getArzt().getAddress(),is(arztAdresse));
        Assert.assertThat(responseUser.getArzt().getTelephone(),is(arztTel));
        Assert.assertThat(responseUser.getAllergien(),is(allergien));
        Assert.assertThat(responseUser.getKrankheiten(),is(krankheiten));
        Assert.assertThat(responseUser.getEssenLimitierungen(),is(essenLimitierungen));
        Assert.assertThat(responseUser.getMedikamente(),is(medikamete));
        Assert.assertThat(responseUser.getHitzeempfindlichkeiten(),is(hitzeempfindlichkeiten));

    }

    /*******************************************
     * Tests API for registering from Ferienpass-Anmeldung Microservice
     ******************************************/

    @Test
    public void addNewTeilnehmerFromFerienpassAnmeldungMicroservice() throws IOException {

        // Zuerst fuer klare Verhältnisse sorgen und Seiteneffekte vermeiden!
        // Daher neue Projekte anlegen ...
        Long pizzaBackenId = addProjekt(ProjektTest.createProjekt(
                "Pizza backen",
                LocalDate.of(2018, 7, 12),
                LocalDate.of(2018, 7, 13),
                15,
                3));
        Long fussballId = addProjekt(ProjektTest.createProjekt(
                "Fussball",
                LocalDate.of(2018, 8, 14),
                LocalDate.of(2018, 8, 17),
                10,
                7));
        Long golfSpielenId = addProjekt(ProjektTest.createProjekt(
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
        Long pizzaBackenId = addProjekt(ProjektTest.createProjekt(
                "Pizza backen",
                LocalDate.of(2018, 7, 12),
                LocalDate.of(2018, 7, 13),
                8,
                3));
        Long fussballId = addProjekt(ProjektTest.createProjekt(
                "Fussball",
                LocalDate.of(2018, 8, 14),
                LocalDate.of(2018, 8, 17),
                10,
                7));
        Long golfSpielenId = addProjekt(ProjektTest.createProjekt(
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
        assertThat(projekteOhneFreieSlots.get(0), is(fussballId));
        assertThat(projekteOhneFreieSlots.get(1), is(golfSpielenId));

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
        assertThat(projekteOhneFreieSlots.get(0), is(fussballId));
        assertThat(projekteOhneFreieSlots.get(1), is(golfSpielenId));
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
        //ToDo: Woher kommen die Daten dieses Users?
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
    public void addNewProjectAndetrieveItBack() {
        Projekt projekt = teilnehmerRepositoryTest.createSingleProject();
        Long projectID = addProjekt(projekt);

        Projekt responeProjekt = getProjekt(projectID);
        assertThat(projectID, is(responeProjekt.getId()));
        assertThat(responeProjekt.getName(), is(projekt.getName()));
        assertThat(responeProjekt.getSlotsFrei(), is(projekt.getSlotsFrei()));
        assertThat(responeProjekt.getKosten(), is(projekt.getKosten()));
        assertThat(responeProjekt.getAlterLimitierung(), is(projekt.getAlterLimitierung()));
        assertThat(responeProjekt.getDatum(), is(projekt.getDatum()));
        assertThat(responeProjekt.getSlotsGesamt(), is(projekt.getSlotsGesamt()));
        assertThat(responeProjekt.getWebLink(), is(projekt.getWebLink()));
        assertThat(responeProjekt.getAnmeldungen(), is(projekt.getAnmeldungen()));
    }


    @Test
    public void addProjectAndUserAndAssignProjectToUserAndRetrieveAllProjectsForThisUser() {
        Long projectID = addProjekt(teilnehmerRepositoryTest.createSingleProject());

        Long userId = addUser(teilnehmerRepositoryTest.createUser());
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
    public void addProjectUsingParametersAndTestForSuccess() {
        Projekt projekt = teilnehmerRepositoryTest.createSingleProject();
        Long projectID =
                given()
                        .param("name", projekt.getName())
                        .param("date", localDate2String(projekt.getDatum()))
                        .param("endDate", localDate2String(projekt.getDatumEnde()))
                        .param("age",projekt.getAlterLimitierung())
                        .param("price",projekt.getKosten())
                        .param("slots",projekt.getSlotsGesamt())
                        .param("slotsReserved",projekt.getSlotsReserviert())
                        .param("traeger",projekt.getTraeger())
                        .param("weblink",projekt.getWebLink())
                .when()
                .get(BASE_URL + "/createproject")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .extract().as(Long.class);

        Projekt responseProjekt = getProjekt(projectID);

        assertThat(projectID, is(responseProjekt.getId()));
        assertThat(responseProjekt.getName(), is(projekt.getName()));
        assertThat(responseProjekt.getSlotsFrei(), is(projekt.getSlotsFrei()));
        assertThat(responseProjekt.getKosten(), is(projekt.getKosten()));
        assertThat(responseProjekt.getAlterLimitierung(), is(projekt.getAlterLimitierung()));
        assertThat(responseProjekt.getDatum(), is(projekt.getDatum()));
        assertThat(responseProjekt.getSlotsGesamt(), is(projekt.getSlotsGesamt()));
        assertThat(responseProjekt.getWebLink(), is(projekt.getWebLink()));
        assertThat(responseProjekt.getAnmeldungen(), is(projekt.getAnmeldungen()));
    }

    private String localDate2String(LocalDate datum) {
        return datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void addProjectAndSetItToInactive() {
        //Create a project
        Projekt projekt = teilnehmerRepositoryTest.createSingleProject();
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

    private Projekt getProjekt(Long projectID) {
        return given()
                .pathParam("projekt_id", projectID)
                .when()
                .get(BASE_URL + "/project/{projekt_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().as(Projekt.class);
    }

    @Test
    public void assignProjektToUserAndRetrieveAllProjectsForTheUsers() {
        Long userId = addUser(teilnehmerRepositoryTest.createUser());
        Long projectId = addProjekt(teilnehmerRepositoryTest.createSingleProject());

        Boolean wasTeilnehmerAssignedToProjekt = assignUser2Projekt(projectId, userId);
        assertThat(wasTeilnehmerAssignedToProjekt, is(true));

        //Get the user again
        Teilnehmer responseUser = getUser(userId);

        //Get all projects fo the userID
        List<Projekt> projectsOfUserID = getAllProjekteWhereUserIsAssigned(responseUser);
        assertThat(projectsOfUserID.size(),is(1));
        assertThat(projectsOfUserID.get(0).getId(), is(projectId));

    }

    @Test
    public void testConsistencyOfRegisteredProjectsOfTeilnehmerAndRegisteredTeilnehmerOfProjects() {
        List<Projekt> allProjects = getAllProjects();
        int numberOfProjects = allProjects.size();
        System.out.println("Number of projects at start: " + numberOfProjects);

        // number of registered Teilnehmer in all projects should be equal
        // to number of registered projects of all Teilnehmer
        Projekt projekt1 = teilnehmerRepositoryTest.createSingleProject();
        Long projectID1 = addProjekt(projekt1);
        assertThat(projekt1.isAktiv(),is(true));

        Projekt projekt2 = teilnehmerRepositoryTest.createSingleProject();
        Long projectID2 = addProjekt(projekt2);
        assertThat(projekt2.isAktiv(),is(true));

        Teilnehmer user1 = teilnehmerRepositoryTest.createUser();
        Long userId1 = addUser(user1);

        Teilnehmer user2 = teilnehmerRepositoryTest.createUser();
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
    public void findProjectsByFirstNameAndLastName() throws Exception {
        Teilnehmer newUser = teilnehmerRepositoryTest.createUser();
        newUser.setVorname("Anton");
        newUser.setNachname("Tirol");


        Long userId =
                given()
                        .body(newUser)
                        .contentType(ContentType.JSON)
                        .when()
                        .post(BASE_URL + "/adduser")
                        .then()
                        .statusCode(is(HttpStatus.SC_CREATED))
                        .extract()
                        .body().as(Long.class);

        Teilnehmer responseUser =
                given()
                        .pathParam("id", userId)
                        .when()
                        .get(BASE_URL + "/user/{id}")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .assertThat()
                        .extract().as(Teilnehmer.class);

        Assert.assertThat(responseUser.getNachname(),is("Tirol"));
        Assert.assertThat(responseUser.getVorname(),is("Anton"));

        Projekt p = teilnehmerRepositoryTest.createSingleProject();
        Long projectID =
                given()
                        .body(p)
                        .contentType(ContentType.JSON)
                        .when()
                        .post(BASE_URL+"/addproject")
                        .then()
                        .statusCode(is(HttpStatus.SC_CREATED))
                        .extract()
                        .body().as(Long.class);

        Map<String,Long> newID_Map = new HashMap<String, Long>();
        newID_Map.put("user",responseUser.getId());
        newID_Map.put("project", projectID);
        Boolean success =
                given()
                        .body(newID_Map)
                        .contentType(ContentType.JSON)
                        .when()
                        .post(BASE_URL+"/assignProject")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().as(Boolean.class);
        MatcherAssert.assertThat(success,is(true));

        List<Projekt> projectsByFirstNameAndLastName =
                Arrays.asList(given()
                        .param("vorname","Anton")
                        .param("nachname","Tirol")
                        .when()
                        .get(BASE_URL+"/projectsof")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().as(Projekt[].class));
        Assert.assertThat(projectsByFirstNameAndLastName.size(),is(1));
    }



    private List<Projekt> getAllProjekteWhereUserIsAssigned(Teilnehmer responseUser) {
        return Arrays.asList(
                given()
                        .param("userID", responseUser.getId())
                        .when()
                        .get(BASE_URL+"/projectsofid")
                        .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().as(Projekt[].class));
    }

    private Long addUser(Teilnehmer teilnehmer) {
        return given()
                .body(teilnehmer)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/adduser")
                .then()
                .statusCode(is(HttpStatus.SC_CREATED))
                .extract()
                .body().as(Long.class);
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

    private Long addProjekt(Projekt projekt) {
        return given()
                    .body(projekt)
                    .contentType(ContentType.JSON)
               .when()
                    .post(BASE_URL+"/addproject")
               .then()
                    .statusCode(is(HttpStatus.SC_CREATED))
                    .extract()
                        .body().as(Long.class);
    }

    private List<Projekt> getAllProjects() {
        return Arrays.asList(given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BASE_URL + "/allprojects")
                        .then()
                        .statusCode(is(HttpStatus.SC_OK))
                        .extract()
                        .body().as(Projekt[].class));
    }

    private List<Teilnehmer> getAllUsers() {
        return Arrays.asList(given()
                .when()
                .get(BASE_URL + "/allusers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Teilnehmer[].class));
    }

    private Boolean assignUser2Projekt(Long projektId, Long userId) {
        Map<String,Long> newID_Map = new HashMap<String, Long>();
        newID_Map.put("user",userId);
        newID_Map.put("project", projektId);
        return given()
                .body(newID_Map)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/assignProject")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Boolean.class);
    }

    private Teilnehmer getUser(Long userId) {
        return given()
                .pathParam("id", userId)
                .when()
                .get(BASE_URL + "/user/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .extract().as(Teilnehmer.class);
    }

    private boolean setProjektInactive(Long projectID) {
        return given()
                .param("project_id", projectID)
                .when()
                .get(BASE_URL + "/deleteproject")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .extract().as(Boolean.class);
    }

    private void debugOutProjekte() {
        List<Projekt> allProjects = getAllProjects();

        allProjects.forEach(projekt -> System.out.println(projekt.getId() + ", Name: " + projekt.getName()));
        allProjects.forEach(projekt -> {
            projekt.getAnmeldungen().forEach(teilnehmer -> System.out.println(projekt.getId() + ", Teilnehmer: " + teilnehmer.getVorname() ));

        });
        System.out.println("Projekte " + allProjects);
    }
}
