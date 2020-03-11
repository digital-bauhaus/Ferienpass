package de.bauhaus.digital.domain;

import static de.bauhaus.digital.DomainFactory.createSampleProjectOfSlots;
import static de.bauhaus.digital.DomainFactory.createSampleUser;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import de.bauhaus.digital.DomainFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ProjektTest {

    @Test
    public void givenProjektAttributes_whenCreatingUsingTheBuilder_thenCreatedProjektMatchesAttribues() {
        boolean aktiv = true;
        String name = "Testprojekt";
        LocalDate datumBeginn = LocalDate.of(2020, 3, 1);
        LocalDate datumEnde = LocalDate.of(2020, 3, 15);
        int mindestAlter = 5;
        int hoechstAlter = 15;
        int plaetzeGesamt = 20;
        int plaetzeReserviert = 3;
        List<Teilnehmer> angemeldeteTeilnehmer = new ArrayList<>();
        List<Teilnehmer> stornierteTeilnehmer = new ArrayList<>();

        Projekt projekt = Projekt.newBuilder()
                .aktiv(aktiv)
                .name(name)
                .datumBeginn(datumBeginn)
                .datumEnde(datumEnde)
                .mindestAlter(mindestAlter)
                .hoechstAlter(hoechstAlter)
                .plaetzeGesamt(plaetzeGesamt)
                .plaetzeReserviert(plaetzeReserviert)
                .angemeldeteTeilnehmer(angemeldeteTeilnehmer)
                .stornierteTeilnehmer(stornierteTeilnehmer)
                .build();

        assertThat(projekt.isAktiv(), is(aktiv));
        assertThat(projekt.getName(), is(name));
        assertThat(projekt.getDatumBeginn(), is(datumBeginn));
        assertThat(projekt.getDatumEnde(), is(datumEnde));
        assertThat(projekt.getMindestAlter(), is(mindestAlter));
        assertThat(projekt.getHoechstAlter(), is(hoechstAlter));
        assertThat(projekt.getPlaetzeGesamt(), is(plaetzeGesamt));
        assertThat(projekt.getPlaetzeReserviert(), is(plaetzeReserviert));
        assertThat(projekt.getAngemeldeteTeilnehmer(), is(angemeldeteTeilnehmer));
        assertThat(projekt.getStornierteTeilnehmer(), is(stornierteTeilnehmer));
    }

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
        project.meldeTeilnehmerAn(createSampleUser());
        assertThat(project.getPlaetzeFrei(), is(freeSlots - 1));
    }

    @Test
    public void when_adding_one_Teilnehmer_plaetzeReserviert_increases_once() {
        Projekt kinderuni = createSampleProjectOfSlots(20, 10);
        kinderuni.meldeTeilnehmerAn(DomainFactory.createSampleUserOfName("Müller", "Luis"));
        assertThat(kinderuni.getPlaetzeReserviert(), is(11));
    }

    @Test
    public void when_adding_5_Teilnehmer_plaetzeReserviert_increases_5_times() {
        Projekt gartenParty = createSampleProjectOfSlots(15, 3);
        gartenParty.meldeTeilnehmerAn(DomainFactory.createSampleUserOfName("Schulze", "Max"));
        gartenParty.meldeTeilnehmerAn(DomainFactory.createSampleUserOfName("Meier", "Moritz")  );
        gartenParty.meldeTeilnehmerAn(DomainFactory.createSampleUserOfName("Schreiner", "Paul"));
        gartenParty.meldeTeilnehmerAn(DomainFactory.createSampleUserOfName("Müller", "Pauline"));
        gartenParty.meldeTeilnehmerAn(DomainFactory.createSampleUserOfName("Siegmund", "Peter"));
        assertThat(gartenParty.getPlaetzeReserviert(), is(8));
    }

    @Test
    public void when_cancelling_one_Teilnehmer_plaetzeFrei_increases(){
        Projekt project = createSampleProjectOfSlots(20, 0);
        Teilnehmer user = createSampleUser();
        project.meldeTeilnehmerAn(user);
        int freeSlots = project.getPlaetzeFrei();
        project.storniereTeilnehmer(user);
        assertThat(project.getPlaetzeFrei(), is(freeSlots + 1));
    }

    @Test
    public void when_cancelling_one_Teilnehmer_plaetzeReserviert_decreases(){
        Projekt project = createSampleProjectOfSlots(20, 0);
        Teilnehmer user = createSampleUser();
        project.meldeTeilnehmerAn(user);
        int reservedSlots = project.getPlaetzeReserviert();
        project.storniereTeilnehmer(user);
        assertThat(project.getPlaetzeReserviert(), is(reservedSlots - 1));
    }

}
