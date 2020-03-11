package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleProject;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bauhaus.digital.domain.Arzt;
import de.bauhaus.digital.domain.Behinderung;
import de.bauhaus.digital.domain.Kontakt;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.transformation.AnmeldungJson;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class PublicControllerTest extends AbstractControllerTest {

    @Value("classpath:requests/anmeldung-post-data.json")
    private Resource anmeldungJsonFile;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test @Ignore
    public void addNewTeilnehmerFromFerienpassAnmeldungMicroservice() throws IOException {

        // Zuerst fuer klare Verhältnisse sorgen und Seiteneffekte vermeiden!
        // Daher neue Projekte anlegen ...
        Long pizzaBackenId = addProject(createSampleProject(
                "Pizza backen",
                LocalDate.of(2018, 7, 12),
                LocalDate.of(2018, 7, 13),
                15,
                3));
        Long fussballId = addProject(createSampleProject(
                "Fussball",
                LocalDate.of(2018, 8, 14),
                LocalDate.of(2018, 8, 17),
                10,
                7));
        Long golfSpielenId = addProject(createSampleProject(
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
        assertThat(responseUser.getWohnort(), is("Weimar"));
        assertThat(responseUser.getTelefon(), is("03643 / 123456"));
        assertThat(responseUser.getEmail(), is("luigi.mueller@web.de"));

        pruefeProjekte(responseUser, pizzaBackenId, fussballId, golfSpielenId);
        pruefeAllergienKrankheitenEtc(responseUser);
//        pruefeBehinderungsdaten(responseUser);
        pruefeErklaerung(responseUser);
    }

    private void pruefeProjekte(Teilnehmer responseUser, Long pizzaBackenId, Long fussballId, Long golfSpielenId) {
        debugOutProjekte();

        // Ist der User in Projekt Pizza backen & Golf spielen angemeldet?
        List<Teilnehmer> anmeldungenPizzaBacken = getProject(pizzaBackenId).getAngemeldeteTeilnehmer();
        assertThat(containsTeilnehmer(responseUser, anmeldungenPizzaBacken), is(true));

        List<Teilnehmer> anmeldungenFussball = getProject(fussballId).getAngemeldeteTeilnehmer();
        assertThat(containsTeilnehmer(responseUser, anmeldungenFussball), is(false));

        List<Teilnehmer> anmeldungenGolfSpielen = getProject(golfSpielenId).getAngemeldeteTeilnehmer();
        assertThat(containsTeilnehmer(responseUser, anmeldungenGolfSpielen), is(true));
    }

    @Test @Ignore
    public void pruefeRegistrierungProjekteBeiApiCallAnmeldungMicroservice() throws IOException {
        // Zuerst fuer klare Verhältnisse sorgen und Seiteneffekte vermeiden!
        // Daher neue Projekte anlegen ...
        Long pizzaBackenId = addProject(createSampleProject(
                "Pizza backen",
                LocalDate.of(2018, 7, 12),
                LocalDate.of(2018, 7, 13),
                8,
                3));
        Long fussballId = addProject(createSampleProject(
                "Fussball",
                LocalDate.of(2018, 8, 14),
                LocalDate.of(2018, 8, 17),
                10,
                7));
        Long golfSpielenId = addProject(createSampleProject(
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
        Projekt pizzaBacken = getProject(pizzaBackenId);
        assertThat(pizzaBacken.getPlaetzeReserviert(), is(4));
        assertThat(pizzaBacken.getPlaetzeFrei(), is(4));

        // Fussball 10 gesamt - 7 reserviert - keine Anmeldung = 3 frei bzw. 7 reserviert
        Projekt fussball = getProject(fussballId);
        assertThat(fussball.getPlaetzeReserviert(), is(7));
        assertThat(fussball.getPlaetzeFrei() , is(3));

        // Golf spielen 9 gesamt - 5 reserviert - 1 Anmeldung = 3 frei bzw. 6 reserviert
        Projekt golfSpielen = getProject(golfSpielenId);
        assertThat(golfSpielen.getPlaetzeReserviert(), is(6));
        assertThat(golfSpielen.getPlaetzeFrei(), is(3));

        // plaetzeFrei bei Fussball auf 0 fahren
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

        Projekt fussballNach3Anmeldungen = getProject(fussballId);
        assertThat(fussballNach3Anmeldungen.getPlaetzeFrei(), is(0));
        assertThat(fussballNach3Anmeldungen.getPlaetzeReserviert(), is(10));

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
        Projekt golfSpielenNach3WeiterenAnmeldungen = getProject(golfSpielenId);
        assertThat(golfSpielenNach3WeiterenAnmeldungen.getPlaetzeFrei(), is(0));

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
        assertThat(getProject(pizzaBackenId).getPlaetzeFrei(), is(4));
        assertThat(getProject(fussballId).getPlaetzeFrei(), is(0));
        assertThat(getProject(golfSpielenId).getPlaetzeFrei(), is(0));

        // Nun versuchen wir unseren Luis Fernandez zu registrieren
        registerNewUserFromAnmeldungFrontendForEmptySlotProjekts(anmeldungJson);
        Projekt pizzaNachGemischtemRequest = getProject(pizzaBackenId);
        for(Teilnehmer angemeldeterTeilnehmer : pizzaNachGemischtemRequest.getAngemeldeteTeilnehmer()) {
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
        assertThat(responseUser.getDarfAlleinNachHause(), is(true));
        assertThat(responseUser.getDarfReiten(), is(false));
        assertThat(responseUser.getDarfSchwimmen(), is(false));
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
        assertThat(behinderung.isUnterstuetzungSucheBegleitperson(), is(true));
        assertThat(behinderung.getGewohnterBegleitpersonenDienstleister(), is("Mensch im Mittelpunkt e.V."));
        assertThat(behinderung.isBeantragungKostenuebernahmeBegleitperson(), is(false));
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
        assertThat(responseUser.getEssenWeitereLimitierungen(),is(expextedNutrition));


        Kontakt notfallKontakt = responseUser.getNotfallKontakt();
        assertThat(notfallKontakt.getName(), is("Andreas Müller"));
        assertThat(notfallKontakt.getAnschrift(), is("Werner-Heisenberg-Straße 5"));
        assertThat(notfallKontakt.getTelefon(), is("0172/34012875"));

        Arzt hausarzt = responseUser.getArzt();
        assertThat(hausarzt.getName(), is("Dr. Martin Schreiber"));
        assertThat(hausarzt.getAnschrift(), is("Amadeusstrasse 2"));
        assertThat(hausarzt.getTelefon(), is("0364 / 0123456"));
    }

    private boolean containsTeilnehmer(Teilnehmer teilnehmer, List<Teilnehmer> angemeldeteTeilnehmer) {
        for (Teilnehmer angemeldeterTeilnehmer : angemeldeteTeilnehmer) {
            if(angemeldeterTeilnehmer.getId() == teilnehmer.getId())
                return true;
        }
        return false;
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

    private void debugOutProjekte() {
        List<Projekt> allProjects = getAllProjects();

        allProjects.forEach(projekt -> System.out.println(projekt.getId() + ", Name: " + projekt.getName()));
        allProjects.forEach(projekt -> {
            projekt.getAngemeldeteTeilnehmer().forEach(teilnehmer -> System.out.println(projekt.getId() + ", Teilnehmer: " + teilnehmer.getVorname()));

        });
        System.out.println("Projekte " + allProjects);
    }
}
