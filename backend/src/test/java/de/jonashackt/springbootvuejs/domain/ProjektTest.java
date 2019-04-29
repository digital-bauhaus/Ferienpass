package de.jonashackt.springbootvuejs.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProjektTest {

    @Test public void
    when_adding_one_Teilnehmer_to_Projekt_slotsreserviert_should_decline_once() {
        Projekt kinderuni = createProjekt("Kinderuni", LocalDate.of(2018, 7, 5), LocalDate.of(2018, 7, 5), 20, 10);
        kinderuni.addAnmeldung(createUser("Müller", "Luis"));
        assertThat(kinderuni.getSlotsReserviert(), is(11));
    }

    @Test public void
    when_adding_5_Teilnehmer_to_Projekt_slotsreserviert_should_decline_5_times() {
        Projekt gartenParty = createProjekt("Gartenparty", LocalDate.of(2018, 6, 4), LocalDate.of(2018, 6, 6), 15, 3);
        gartenParty.addAnmeldung(createUser("Schulze", "Max"));
        gartenParty.addAnmeldung(createUser("Meier", "Moritz")  );
        gartenParty.addAnmeldung(createUser("Schreiner", "Paul"));
        gartenParty.addAnmeldung(createUser("Müller", "Pauline"));
        gartenParty.addAnmeldung(createUser("Siegmund", "Peter"));
        assertThat(gartenParty.getSlotsReserviert(), is(8));
    }

    public static Projekt createProjekt(String projektName, LocalDate datum, LocalDate datumEnde, int slotsgesamt, int slotsReserviert) {
        return new Projekt(projektName, datum, datumEnde, 15, 12, slotsgesamt, slotsReserviert, "Sportjugend Weimar", "www.google.com");
    }

    public static Teilnehmer createUser(String name, String vorname) {
        LocalDate registerDate = LocalDate.now();
        Arzt arzt = new Arzt("Eich", "Route 1 Alabastia, 39829",
                "555-6891");
        Kontakt kontact = new Kontakt("Igor Eich", "Route 4 Neuborkia  96825", "555-2532");
        String essenLimitierungen = "Laktoseintoleranz";
        String krankheiten = "Grippe: Muss oft Husten Hustenbonbons";

        String allergien = "Heuschnupfen: Nasenspray nur 2x am Tag";


        Behinderung behinderung = new Behinderung();
        behinderung.setRollstuhlNutzungNotwendig(true);
        behinderung.setMerkzeichen_Taubblind_TBL(true);

        String medikaments = "Nasenspray von Forte: 2x am Tag";

        String hitzeempfindlichkeits = "grosse Hitze: eincremen";

        Teilnehmer user = new Teilnehmer(vorname,name, LocalDate.of(2005,10,20),registerDate, "Bahnhofstraße 4", "Weimar", "99423", "03544444", "0453434", true, kontact,
                true, false, false, "Seepferdchen", false, false, arzt,  allergien, essenLimitierungen, krankheiten, true, behinderung,hitzeempfindlichkeits,medikaments);
        return user;
    }



}
