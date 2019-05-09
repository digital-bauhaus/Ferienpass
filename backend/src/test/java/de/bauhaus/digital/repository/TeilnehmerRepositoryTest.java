package de.bauhaus.digital.repository;

import de.bauhaus.digital.DomainFactory;
import de.bauhaus.digital.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static de.bauhaus.digital.DomainFactory.*;

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

    @Before
    public void init() {
        entityManager.persist(createSampleUser());
        entityManager.persist(createSampleUser());

        projects.save(DomainFactory.createSampleProject(
                "Ball Werfen", LocalDate.of(2018, 7, 16), LocalDate.of(2018, 7, 17), 20, 1, "Tasifan",
                10,
                20));
        projects.save(DomainFactory.createSampleProject(
                "Bauspielplatz", LocalDate.of(2018, 8, 02), LocalDate.of(2018, 8, 02), 10, 1, "Nordlicht e.V.",
                6,
                20));
        projects.save(DomainFactory.createSampleProject(
                "Papier-Werkstatt", LocalDate.of(2018, 7, 23), LocalDate.of(2018, 7, 25), 8, 1, "Sektion Weimar des Deutschen Alpenvereins e.V.",
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
