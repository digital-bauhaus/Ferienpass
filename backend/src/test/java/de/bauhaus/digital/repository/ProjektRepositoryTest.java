package de.bauhaus.digital.repository;

import static de.bauhaus.digital.DomainFactory.createSampleProject;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import de.bauhaus.digital.domain.Projekt;
import java.time.LocalDate;
import java.util.List;
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

    @Before
    public void init() {
        Projekt erstes = createSampleProject("erstes", LocalDate.of(2020, 1, 1), LocalDate.of(2020,1, 2), 10, 0);
        Projekt zweites = createSampleProject("zweites", LocalDate.of(2021, 1, 1), LocalDate.of(2021,1, 2), 10, 0);
        Projekt drittes = createSampleProject("drittes", LocalDate.of(2021, 1, 1), LocalDate.of(2021,1, 3), 10, 0);
        Projekt inaktiv = Projekt.newBuilder(createSampleProject()).aktiv(false).build();
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
