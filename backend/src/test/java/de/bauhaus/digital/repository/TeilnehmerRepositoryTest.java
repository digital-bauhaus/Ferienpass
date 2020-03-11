package de.bauhaus.digital.repository;

import static de.bauhaus.digital.DomainFactory.createSampleUser;
import static de.bauhaus.digital.DomainFactory.createSampleUserOfName;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import de.bauhaus.digital.domain.Behinderung;
import de.bauhaus.digital.domain.Teilnehmer;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeilnehmerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeilnehmerRepository teilnehmerRepository;

    @Autowired
    private BehinderungRepository behinderungRepository;

    @Before
    public void init() {
        Teilnehmer garyEich = createSampleUserOfName("Eich", "Gary");
        Teilnehmer garyNorris = createSampleUserOfName("Norris", "Gary");
        Teilnehmer chuckNorris = createSampleUserOfName("Norris", "Chuck");
        Teilnehmer inactive = Teilnehmer.newBuilder(createSampleUserOfName("nicht", "aktiv")).aktiv(false).build();
        entityManager.persist(garyEich);
        entityManager.persist(garyNorris);
        entityManager.persist(chuckNorris);
        entityManager.persist(inactive);
        entityManager.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidProjekt_whenSaving_thenFails() {
        Teilnehmer invalidTeilnehmer = Teilnehmer.newBuilder().build();
        teilnehmerRepository.save(invalidTeilnehmer);
    }

    @Test
    public void givenTeilnehmerMitBehinderung_whenDeletingTeilnehmer_thenBehinderungIsAlsoDeleted() {
        Behinderung behinderung = Behinderung.newBuilder().build();
        Teilnehmer teilnehmer = Teilnehmer.newBuilder(createSampleUser())
                .behinderung(behinderung)
                .build();
        teilnehmerRepository.save(teilnehmer);

        // check that the Behinderung got an ID
        long behinderungId = behinderung.getId();
        assertThat(behinderungId, greaterThan(0L));

        // find the Behinderung in the database
        Optional<Behinderung> behinderungNachSpeichern = behinderungRepository.findById(behinderungId);
        assertThat(behinderungNachSpeichern.isPresent(), is(true));

        // delete the Teilnehmer
        teilnehmerRepository.delete(teilnehmer);

        // find the Behinderung again
        Optional<Behinderung> behinderungNachLoeschen = behinderungRepository.findById(behinderungId);
        assertThat(behinderungNachLoeschen.isPresent(), is(false));
    }

    @Test
    public void whenFindAllActive_thenInactiveUsersAreMissing() {

        List<Teilnehmer> allActive = teilnehmerRepository.findAllActive();

        assertThat(allActive.size(), is(3));
    }

    @Test
    public void whenFindAllActiveSortedByName_ThenIsSortedByName() {

        List<Teilnehmer> allSortedByName = teilnehmerRepository.findAllActiveSortedByName();

        assertThat(allSortedByName.size(), is(3));

        assertThat(allSortedByName.get(0).getNachname(), is("Eich"));
        assertThat(allSortedByName.get(0).getVorname(), is("Gary"));

        assertThat(allSortedByName.get(1).getNachname(), is("Norris"));
        assertThat(allSortedByName.get(1).getVorname(), is("Chuck"));

        assertThat(allSortedByName.get(2).getNachname(), is("Norris"));
        assertThat(allSortedByName.get(2).getVorname(), is("Gary"));
    }

}
