package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.FerienpassAdminApplication;
import de.jonashackt.springbootvuejs.domain.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = FerienpassAdminApplication.class)
public class TeilnehmerRepositoryTest {
    private static final String BASE_URL = "http://localhost:8089/api";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeilnehmerRepository users;

    @Autowired
    private ProjektRepository projects;

    private static int addedProjects = 0;

    public static Teilnehmer createSampleUser() {
        Arzt arzt = new Arzt("Eich", "Route 1 Alabastia, 39829",
                "555-6891");
        Kontakt kontact = new Kontakt("Igor Eich", "Route 4 Neuborkia  96825", "555-2532");

        String essenLimitierungen = "Laktoseintoleranz";
        String krankheiten = "Grippe: Muss oft Husten und braucht Hustenbonbons";
        String allergien = "Heuschnupfen: braucht Nasenspray, siehe Medikamente";
        Behinderung behinderung = new Behinderung();
        behinderung.setRollstuhlNutzungNotwendig(true);
        behinderung.setMerkzeichen_Taubblind_TBL(true);
        String schwimmAbzeichen = "Seepferdchen";
        String hitze = "Wärme: bis 25 Grad ist alles okay";
        String medikamente = "Nasentropfen_ maximal 2x am Tag ein Schub";

        Teilnehmer user = new Teilnehmer(
                "Gary",
                "Eich",
                LocalDate.of(2005,10,20),LocalDate.now(),
                "Bahnhofstraße 4",
                "Weimar",
                "99423",
                "03544444",
                "0453434",
                true,
                kontact,
                true,
                false,
                false,
                schwimmAbzeichen,
                false,
                false,
                arzt,
                allergien,
                essenLimitierungen,
                krankheiten,
                true,
                behinderung,
                hitze,
                medikamente);

        return user;
    }

    public static Projekt createSampleProject(String projektName, int slotsGesamt, LocalDate datum, LocalDate endeDatum, String traeger, int alterLimitierung) {
        return new Projekt(projektName, datum, endeDatum, alterLimitierung, 20, slotsGesamt, 1, traeger, "www.google.com");
    }

    public static Teilnehmer createUser() {
        LocalDate registerDate = LocalDate.now();
        Arzt arzt = new Arzt("Eich", "Route 1 Alabastia, 39829",
                "555-6891");
        Kontakt kontact = new Kontakt("Igor Eich", "Route 4 Neuborkia  96825", "555-2532");
        String krankheiten = "Grippe: Muss oft Husten Hustenbonbons";


        String essenLimitierungen = "Laktoseintoleranz";
        String allergien = "Heuschnupfen: Nasenspray nur 2x am Tag";

        Behinderung behinderung = new Behinderung();
        behinderung.setRollstuhlNutzungNotwendig(true);
        behinderung.setMerkzeichen_Taubblind_TBL(true);

        String medikaments = "Nasenspray von Forte: 2x am Tag";

        String hitzeempfindlichkeits = "grosse Hitze: eincremen";

        Teilnehmer user = new Teilnehmer("Gary","Eich", LocalDate.of(2005,10,20),registerDate, "Bahnhofstraße 4", "Weimar", "99423", "03544444", "0453434", true, kontact,
                true, false, false, "Seepferdchen", false, false, arzt,  allergien, essenLimitierungen, krankheiten, true, behinderung,hitzeempfindlichkeits,medikaments);
        return user;
    }


    public List<Projekt> createProjects(int numberOfProjects) {
        ArrayList<Projekt> result = new ArrayList<Projekt>();
        for (int i = addedProjects; i < addedProjects+numberOfProjects; i++){
            Projekt p = new Projekt("Testprojekt " + i, LocalDate.of(2018, 6, 4), LocalDate.of(2018, 6, 7), 5+i, 20, 3+i, 1, "Sportjugend Weimar", "www.google.com");
            result.add(p);}
        return result;
    }

    public Projekt createSingleProject() {
       return new Projekt("Schwimmen im See", LocalDate.of(2018, 7, 1), LocalDate.of(2018, 7, 3), 15, 12, 10, 5, "Sportjugend Weimar","www.google.com");
    }

    @Before
    public void init() {
        entityManager.persist(createUser());
        entityManager.persist(createSampleUser());

        projects.save(createSampleProject(
                "Ball Werfen",
                20,
                LocalDate.of(2018, 7, 16),
                LocalDate.of(2018, 7, 17),
                "Tasifan",
                10));
        projects.save(createSampleProject(
                "Bauspielplatz",
                10,
                LocalDate.of(2018, 8, 02),
                LocalDate.of(2018, 8, 02),
                "Nordlicht e.V.",
                6));
        projects.save(createSampleProject(
                "Papier-Werkstatt",
                8,
                LocalDate.of(2018, 7, 23),
                LocalDate.of(2018, 7, 25),
                "Sektion Weimar des Deutschen Alpenvereins e.V.",
                8));
    }

    @Test
    public void testFindByLastName() throws Exception {
        // Search for specific User in Database according to lastname
        List<Teilnehmer> usersWithLastNameEich = users.findByNachname("Eich");
        Teilnehmer user = usersWithLastNameEich.get(0);
        assertThat(user.getNachname(), containsString("Eich"));
    }

    @Test
    public void testFindProjectsByFirstNameAndLastName() throws Exception {
        Teilnehmer newUser = createUser();
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

        assertThat(responseUser.getNachname(),is("Tirol"));
        assertThat(responseUser.getVorname(),is("Anton"));

        Projekt p = createSingleProject();
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

        List<Projekt> allProjects = projects.findAllProjects();
        assertThat(allProjects.size(),is(3));

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
        assertThat(projectsByFirstNameAndLastName.size(),is(1));
    }


    @Test
    public void testFindByFirstName() throws Exception {
        // Search for specific User in Database according to firstname
        List<Teilnehmer> usersWithFirstNameJonas = users.findByVorname("Gary");

        assertThat(usersWithFirstNameJonas.get(0).getVorname(), containsString("Gary"));
    }

    @Test
    public void updateTeilnehmer() throws  Exception {
        Teilnehmer newUser = createUser();
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

        assertThat(responseUser.getVorname(),is(vorname));
        assertThat(responseUser.getNachname(),is(nachname));
        String[] datum = geburtsdatum.split(",");
        assertThat(responseUser.getGeburtsdatum(),is(LocalDate.of(Integer.parseInt(datum[0]),Integer.parseInt(datum[1]),Integer.parseInt(datum[2]))));
        assertThat(responseUser.getStrasse(),is(strasse));
        assertThat(responseUser.getPostleitzahl(),is(plz));
        assertThat(responseUser.getStadt(),is(stadt));
        assertThat(responseUser.getTelefon(),is(tel));
        assertThat(responseUser.getKrankenkasse(),is(krankenkasse));
        assertThat(responseUser.getNotfallKontakt().getName(),is(kontaktName));
        assertThat(responseUser.getNotfallKontakt().getAddress(),is(kontaktAdresse));
        assertThat(responseUser.getNotfallKontakt().getTelephone(),is(kontaktTel));
        assertThat(responseUser.getArzt().getName(),is(arztName));
        assertThat(responseUser.getArzt().getAddress(),is(arztAdresse));
        assertThat(responseUser.getArzt().getTelephone(),is(arztTel));
        assertThat(responseUser.getAllergien(),is(allergien));
        assertThat(responseUser.getKrankheiten(),is(krankheiten));
        assertThat(responseUser.getEssenLimitierungen(),is(essenLimitierungen));
        assertThat(responseUser.getMedikamente(),is(medikamete));
        assertThat(responseUser.getHitzeempfindlichkeiten(),is(hitzeempfindlichkeiten));

    }
}
