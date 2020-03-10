package de.bauhaus.digital.domain;

import de.bauhaus.digital.DomainFactory;
import org.junit.Test;

import static de.bauhaus.digital.DomainFactory.createSampleProjectOfSlots;
import static de.bauhaus.digital.DomainFactory.createSampleUser;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProjektTest {

    @Test
    public void project_with_20_totalSlots_has_20_freeSlots() {
        Projekt project = createSampleProjectOfSlots(20, 0);
        assertThat(project.getPlaetzeFrei(), is(20));
    }

    @Test
    public void project_with_20_totalSlots_and_5_reservedSlots_has_15_freeSlots() {
        Projekt project = createSampleProjectOfSlots(20, 5);
        assertThat(project.getPlaetzeFrei(), is(15));
    }

    @Test
    public void when_adding_one_teilnehmer_freeSlots_decreases_once() {
        Projekt project = createSampleProjectOfSlots(20, 0);
        int freeSlots = project.getPlaetzeFrei();
        project.addAnmeldung(createSampleUser());
        assertThat(project.getPlaetzeFrei(), is(freeSlots - 1));
    }

    @Test
    public void when_adding_one_Teilnehmer_plaetzeReserviert_increases_once() {
        Projekt kinderuni = createSampleProjectOfSlots(20, 10);
        kinderuni.addAnmeldung(DomainFactory.createSampleUserOfName("Müller", "Luis"));
        assertThat(kinderuni.getPlaetzeReserviert(), is(11));
    }

    @Test
    public void when_adding_5_Teilnehmer_plaetzeReserviert_increases_5_times() {
        Projekt gartenParty = createSampleProjectOfSlots(15, 3);
        gartenParty.addAnmeldung(DomainFactory.createSampleUserOfName("Schulze", "Max"));
        gartenParty.addAnmeldung(DomainFactory.createSampleUserOfName("Meier", "Moritz")  );
        gartenParty.addAnmeldung(DomainFactory.createSampleUserOfName("Schreiner", "Paul"));
        gartenParty.addAnmeldung(DomainFactory.createSampleUserOfName("Müller", "Pauline"));
        gartenParty.addAnmeldung(DomainFactory.createSampleUserOfName("Siegmund", "Peter"));
        assertThat(gartenParty.getPlaetzeReserviert(), is(8));
    }

    @Test
    public void when_cancelling_one_Teilnehmer_slotsFree_increases(){
        Projekt project = createSampleProjectOfSlots(20, 0);
        Teilnehmer user = createSampleUser();
        project.addAnmeldung(user);
        int freeSlots = project.getPlaetzeFrei();
        project.addStornierung(user);
        assertThat(project.getPlaetzeFrei(), is(freeSlots + 1));
    }

    @Test
    public void when_cancelling_one_Teilnehmer_plaetzeReserviert_decreases(){
        Projekt project = createSampleProjectOfSlots(20, 0);
        Teilnehmer user = createSampleUser();
        project.addAnmeldung(user);
        int reservedSlots = project.getPlaetzeReserviert();
        project.addStornierung(user);
        assertThat(project.getPlaetzeReserviert(), is(reservedSlots - 1));
    }

}
