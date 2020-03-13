package de.bauhaus.digital.controller;


import static de.bauhaus.digital.DomainFactory.createSampleProjekt;
import static de.bauhaus.digital.DomainFactory.createSampleProjektBuilder;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmer;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmerBuilder;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import de.bauhaus.digital.repository.ProjektRepository;
import de.bauhaus.digital.repository.TeilnehmerRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PublicControllerTest extends AbstractControllerTest {

    @Autowired
    ProjektRepository projektRepository;
    @Autowired
    TeilnehmerRepository teilnehmerRepository;

    @Before
    public void cleanDatabase() {
        projektRepository.deleteAll();
        teilnehmerRepository.deleteAll();
    }

    @Test
    public void givenTeilnehmerOhneWunschprojekte_whenRegistrierung_thenTeilnehmerWirdNichtAngelegtMitHttpUnprocessableEntity() {
        Teilnehmer teilnehmer = createSampleTeilnehmer();

        whenRegisterTeilnehmer(teilnehmer)
        .then()
            .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);

        assertThat(getAllUsers().size(), is(0));
    }

    @Test
    public void givenTeilnehmerMitUngueltigenWunschprojekten_whenRegistrierung_thenTeilnehmerWirdNichtAngelegtMitHttpNotFound() {
        Long projektId = addProject(createSampleProjekt());
        Long invalidProjektId = -1L;
        List<Long> gewuenschteProjekte = Arrays.asList(projektId, invalidProjektId);
        Teilnehmer teilnehmer = createSampleTeilnehmerBuilder().gewuenschteProjekte(gewuenschteProjekte).build();

        whenRegisterTeilnehmer(teilnehmer)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);

        assertThat(getAllUsers().size(), is(0));
    }

    @Test
    public void givenTeilnehmerMitAusgebuchtenWunschprojekten_whenRegistrierung_thenTeilnehmerWirdNichtAngelegtMitHttpConflict() {
        Long projektVerfuegbarId = addProject(createSampleProjektBuilder().plaetzeGesamt(10).plaetzeReserviert(0).build());
        Long projektAusgebuchtId = addProject(createSampleProjektBuilder().plaetzeGesamt(10).plaetzeReserviert(10).build());
        List<Long> gewuenschteProjekte = Arrays.asList(projektVerfuegbarId, projektAusgebuchtId);
        Teilnehmer teilnehmer = createSampleTeilnehmerBuilder().gewuenschteProjekte(gewuenschteProjekte).build();

        whenRegisterTeilnehmer(teilnehmer)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);

        assertThat(getAllUsers().size(), is(0));
    }

    @Test
    public void givenTeilnehmerMitWunschprojekten_whenRegistrierung_thenTeilnehmerWirdAngelegt() {
        Long projektId = addProject(createSampleProjekt());
        Long projektId2 = addProject(createSampleProjekt());
        List<Long> gewuenschteProjekte = Arrays.asList(projektId, projektId2);
        Teilnehmer teilnehmer = createSampleTeilnehmerBuilder().gewuenschteProjekte(gewuenschteProjekte).build();

        registerTeilnehmer(teilnehmer);

        List<Teilnehmer> alleTeilnehmer = getAllUsers();
        Teilnehmer registrierterTeilnehmer = alleTeilnehmer.get(0);

        assertThat(alleTeilnehmer.size(), is(1));
        Assertions.assertThat(registrierterTeilnehmer).
                usingRecursiveComparison()
                .ignoringFields("id", "arzt.id", "behinderung.id", "notfallKontakt.id", "gewuenschteProjekte")
                .isEqualTo(teilnehmer);
    }

    @Test
    public void givenTeilnehmerMitWunschprojektenUndExpliziterId_whenRegistrierung_thenTeilnehmerWirdNeuAngelegt() {
        Long projektId = addProject(createSampleProjekt());
        Long projektId2 = addProject(createSampleProjekt());
        List<Long> gewuenschteProjekte = Arrays.asList(projektId, projektId2);

        Long vorhandenerTeilnehmerId = addUser(createSampleTeilnehmer());
        Teilnehmer vorhandenerTeilnehmer = getUser(vorhandenerTeilnehmerId);
        Teilnehmer teilnehmerMitExpliziterId = Teilnehmer.newBuilder(vorhandenerTeilnehmer).gewuenschteProjekte(gewuenschteProjekte).build();

        registerTeilnehmer(teilnehmerMitExpliziterId);

        List<Teilnehmer> alleTeilnehmer = getAllUsers();
        Teilnehmer registrierterTeilnehmer = alleTeilnehmer.get(1); // not the first one! this is the vorhandene Teilnehmer!

        assertThat(alleTeilnehmer.size(), is(2));
        Assertions.assertThat(registrierterTeilnehmer).
                usingRecursiveComparison()
                .ignoringFields("id", "arzt.id", "behinderung.id", "notfallKontakt.id", "gewuenschteProjekte")
                .isEqualTo(teilnehmerMitExpliziterId);
        assertThat(registrierterTeilnehmer.getId(), not(vorhandenerTeilnehmerId));
    }

    @Test
    public void givenTeilnehmerMitWunschprojekten_whenRegistrierung_thenTeilnehmerIstFuerProjekteAngemeldet() {
        Long projektId = addProject(createSampleProjekt());
        Long projektId2 = addProject(createSampleProjekt());
        List<Long> gewuenschteProjekte = Arrays.asList(projektId, projektId2);
        Teilnehmer teilnehmer = createSampleTeilnehmerBuilder().gewuenschteProjekte(gewuenschteProjekte).build();

        registerTeilnehmer(teilnehmer);

        List<Teilnehmer> alleTeilnehmer = getAllUsers();
        Teilnehmer registrierterTeilnehmer = alleTeilnehmer.get(0);

        assertThat(alleTeilnehmer.size(), is(1));
        List<Projekt> alleAngemeldetenProjekteDesTeilnehmers = getAlleAngemeldetenProjekteDesTeilnehmers(registrierterTeilnehmer.getId());
        assertThat(alleAngemeldetenProjekteDesTeilnehmers.size(), is(2));
        assertThat(alleAngemeldetenProjekteDesTeilnehmers.get(0).getId(), is(projektId));
        assertThat(alleAngemeldetenProjekteDesTeilnehmers.get(1).getId(), is(projektId2));
    }

    @Test
    public void giveNoAuthentication_whenRegistrierung_thenTeilnehmerWirdAngelegt() {
        Long projektId = addProject(createSampleProjekt());
        Long projektId2 = addProject(createSampleProjekt());
        List<Long> gewuenschteProjekte = Arrays.asList(projektId, projektId2);
        Teilnehmer teilnehmer = createSampleTeilnehmerBuilder().gewuenschteProjekte(gewuenschteProjekte).build();

        revokeRestCredentials();
        registerTeilnehmer(teilnehmer);
        initRestCredentials();

        List<Teilnehmer> alleTeilnehmer = getAllUsers();
        Teilnehmer registrierterTeilnehmer = alleTeilnehmer.get(0);

        assertThat(alleTeilnehmer.size(), is(1));
        Assertions.assertThat(registrierterTeilnehmer).
                usingRecursiveComparison()
                .ignoringFields("id", "arzt.id", "behinderung.id", "notfallKontakt.id", "gewuenschteProjekte")
                .isEqualTo(teilnehmer);
    }

    @Test
    public void givenNoAuthentication_whenRequestingAlleProjekte_thenSuccess() {
        int initialSize = getAllProjects().size();

        addProject(createSampleProjekt());
        addProject(createSampleProjekt());

        List<Projekt> alleProjekte = Arrays.asList(
                given()
                    .contentType(ContentType.JSON)
                .when()
                    .get(BASE_URL + "/public/projects")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .body().as(Projekt[].class));

        assertThat(alleProjekte.size(), is(initialSize + 2));
    }

    private void registerTeilnehmer(Teilnehmer teilnehmer) {
        whenRegisterTeilnehmer(teilnehmer)
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

    private Response whenRegisterTeilnehmer(Teilnehmer teilnehmer) {
        return given()
                .body(teilnehmer)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/public/register");
    }
}
