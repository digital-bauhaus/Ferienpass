package de.bauhaus.digital.repository;

import de.bauhaus.digital.domain.Teilnehmer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static de.bauhaus.digital.DomainFactory.createSampleUserOfName;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeilnehmerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeilnehmerRepository teilnehmerRepository;

    @Before
    public void init() {
        Teilnehmer garyEich = createSampleUserOfName("Eich", "Gary");
        entityManager.persist(garyEich);
        entityManager.flush();
    }

    @Test
    public void testFindByLastName() throws Exception {

        List<Teilnehmer> usersWithLastNameEich = teilnehmerRepository.findByNachname("Eich");

        assertThat(usersWithLastNameEich.isEmpty(), is(false));
        assertThat(usersWithLastNameEich.get(0).getNachname(), containsString("Eich"));
    }


    @Test
    public void testFindByFirstName() throws Exception {
        // Search for specific User in Database according to firstname
        List<Teilnehmer> usersWithFirstNameGary = teilnehmerRepository.findByVorname("Gary");

        assertThat(usersWithFirstNameGary.isEmpty(), is(false));
        assertThat(usersWithFirstNameGary.get(0).getVorname(), containsString("Gary"));
    }

}
