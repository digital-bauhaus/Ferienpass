package de.bauhaus.digital.repository;

import de.bauhaus.digital.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
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
        String email = "myEmail@weimar.de";

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
                medikamente,
                email);

        return user;
    }

    public static Projekt createSampleProject(String projektName,
                                              int slotsGesamt,
                                              LocalDate datum,
                                              LocalDate endeDatum,
                                              String traeger,
                                              int mindestAlter,
                                              int hoechstAlter) {
        return new Projekt(projektName, datum, endeDatum, mindestAlter,
                hoechstAlter, 20, slotsGesamt, 1, traeger, "www.google.com");
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
        String email = "myEmail@weimar.de";

        Teilnehmer user = new Teilnehmer("Gary","Eich", LocalDate.of(2005,10,20),registerDate, "Bahnhofstraße 4", "Weimar", "99423", "03544444", "0453434", true, kontact,
                true, false, false, "Seepferdchen", false, false, arzt,  allergien, essenLimitierungen, krankheiten, true, behinderung,hitzeempfindlichkeits,medikaments, email);
        return user;
    }


    public List<Projekt> createProjects(int numberOfProjects) {
        ArrayList<Projekt> result = new ArrayList<Projekt>();
        for (int i = addedProjects; i < addedProjects+numberOfProjects; i++){
            Projekt p = new Projekt("Testprojekt " + i, LocalDate.of(2018, 6,
                    4), LocalDate.of(2018, 6, 7), 5+i, 20+i, 20, 3+i, 1,
                    "Sportjugend Weimar", "www.google.com");
            result.add(p);}
        return result;
    }

    public Projekt createSingleProject() {
       return new Projekt("Schwimmen im See", LocalDate.of(2018, 7, 1),
               LocalDate.of(2018, 7, 3), 15, 20, 12, 10, 5, "Sportjugend " +
               "Weimar","www.google.com");
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
                10,
                20));
        projects.save(createSampleProject(
                "Bauspielplatz",
                10,
                LocalDate.of(2018, 8, 02),
                LocalDate.of(2018, 8, 02),
                "Nordlicht e.V.",
                6,
                20));
        projects.save(createSampleProject(
                "Papier-Werkstatt",
                8,
                LocalDate.of(2018, 7, 23),
                LocalDate.of(2018, 7, 25),
                "Sektion Weimar des Deutschen Alpenvereins e.V.",
                8,
                20));
    }

    @Test
    public void testFindByLastName() throws Exception {
        // Search for specific User in Database according to lastname
        List<Teilnehmer> usersWithLastNameEich = users.findByNachname("Eich");
        Teilnehmer user = usersWithLastNameEich.get(0);
        assertThat(user.getNachname(), containsString("Eich"));
    }


    @Test
    public void testFindByFirstName() throws Exception {
        // Search for specific User in Database according to firstname
        List<Teilnehmer> usersWithFirstNameJonas = users.findByVorname("Gary");

        assertThat(usersWithFirstNameJonas.get(0).getVorname(), containsString("Gary"));
    }

}
