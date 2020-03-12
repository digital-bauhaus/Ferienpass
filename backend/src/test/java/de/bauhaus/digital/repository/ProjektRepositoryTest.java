package de.bauhaus.digital.repository;

import static de.bauhaus.digital.DomainFactory.createSampleProjektBuilder;
import static de.bauhaus.digital.DomainFactory.createSampleTeilnehmer;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import java.time.LocalDate;
import java.util.Collections;
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
public class ProjektRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjektRepository projektRepository;
    @Autowired
    private TeilnehmerRepository teilnehmerRepository;

    @Before
    public void init() {
        Projekt erstes = createSampleProjektBuilder()
                .name("erstes")
                .datumBeginn(LocalDate.of(2020, 1, 1))
                .datumEnde(LocalDate.of(2020, 1, 2))
                .build();
        Projekt zweites = createSampleProjektBuilder()
                .name("zweites")
                .datumBeginn(LocalDate.of(2021, 1, 1))
                .datumEnde(LocalDate.of(2021, 1, 2))
                .build();
        Projekt drittes = createSampleProjektBuilder()
                .name("drittes")
                .datumBeginn(LocalDate.of(2021, 1, 1))
                .datumEnde(LocalDate.of(2021, 1, 3))
                .build();
        Projekt inaktiv = createSampleProjektBuilder().aktiv(false).build();
        entityManager.persist(erstes);
        entityManager.persist(zweites);
        entityManager.persist(drittes);
        entityManager.persist(inaktiv);
        entityManager.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidProjekt_whenSaving_thenFails() {
        Projekt invalidProjekt = Projekt.newBuilder().build();
        projektRepository.save(invalidProjekt);
    }

    @Test
    public void givenProjektMitAngemeldetenTeilnehmern_whenDeletingProjekt_thenTeilnehmerAreNotDeleted() {
        Teilnehmer teilnehmer = createSampleTeilnehmer();
        Projekt projekt = createSampleProjektBuilder()
                .angemeldeteTeilnehmer(Collections.singletonList(teilnehmer))
                .build();
        projektRepository.save(projekt);

        // check that the Teilnehmer got an ID
        long teilnehmerId = teilnehmer.getId();
        assertThat(teilnehmerId, greaterThan(0L));

        // find the Teilnehmer in the database
        Optional<Teilnehmer> teilnehmerNachSpeichern = teilnehmerRepository.findById(teilnehmerId);
        assertThat(teilnehmerNachSpeichern.isPresent(), is(true));

        // delete the Projekt
        projektRepository.delete(projekt);

        // find the Teilnehmer again
        Optional<Teilnehmer> teilnehmerNachLoeschen = teilnehmerRepository.findById(teilnehmerId);
        assertThat(teilnehmerNachLoeschen.isPresent(), is(true));
    }

    @Test
    public void givenProjektMitStorniertenTeilnehmern_whenDeletingProjekt_thenTeilnehmerAreNotDeleted() {
        Teilnehmer teilnehmer = createSampleTeilnehmer();
        Projekt projekt = createSampleProjektBuilder()
                .stornierteTeilnehmer(Collections.singletonList(teilnehmer))
                .build();
        projektRepository.save(projekt);

        // check that the Teilnehmer got an ID
        long teilnehmerId = teilnehmer.getId();
        assertThat(teilnehmerId, greaterThan(0L));

        // find the Teilnehmer in the database
        Optional<Teilnehmer> teilnehmerNachSpeichern = teilnehmerRepository.findById(teilnehmerId);
        assertThat(teilnehmerNachSpeichern.isPresent(), is(true));

        // delete the Projekt
        projektRepository.delete(projekt);

        // find the Teilnehmer again
        Optional<Teilnehmer> teilnehmerNachLoeschen = teilnehmerRepository.findById(teilnehmerId);
        assertThat(teilnehmerNachLoeschen.isPresent(), is(true));
    }

    @Test
    public void whenFindAllActive_ThenInactiveProjectsAreMissing() {

        List<Projekt> allSortedByDatum = projektRepository.findAllActive();

        assertThat(allSortedByDatum.size(), is(3));
    }

    @Test
    public void whenFindAllActiveSortedByDatum_ThenIsSortedByDatum() {

        List<Projekt> allSortedByDatum = projektRepository.findAllActiveSortedByDatum();

        assertThat(allSortedByDatum.size(), is(3));

        assertThat(allSortedByDatum.get(0).getName(), is("erstes"));

        assertThat(allSortedByDatum.get(1).getName(), is("zweites"));

        assertThat(allSortedByDatum.get(2).getName(), is("drittes"));
    }

}
