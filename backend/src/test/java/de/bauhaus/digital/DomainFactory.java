package de.bauhaus.digital;

import de.bauhaus.digital.domain.*;

import java.time.LocalDate;

public class DomainFactory {

    public static Projekt createSampleProject() {
        return createSampleProject("Schwimmem im See", LocalDate.of(2018, 7,
                1), LocalDate.of(2018, 7, 3), 10, 5, "Sportjugend Weimar", 15, 20);
    }

    public static Projekt createSampleProjectOfSlots(int slotsGesamt, int slotsReserviert) {
        return createSampleProject(
                "Schwimmem im See",
                LocalDate.of(2018, 7,1),
                LocalDate.of(2018, 7, 3),
                slotsGesamt,
                slotsReserviert);
    }

    public static Projekt createSampleProject(String projektName,
                                              LocalDate datum,
                                              LocalDate endeDatum,
                                              int slotsGesamt,
                                              int slotsReserviert) {
        return createSampleProject(projektName, datum, endeDatum, slotsGesamt, slotsReserviert, "Sportjugend Weimar", 15, 20);
    }

    public static Projekt createSampleProject(String projektName, LocalDate datum, LocalDate endeDatum, int slotsGesamt, int slotsReserviert, String traeger, int mindestAlter, int hoechstAlter) {
        return new Projekt(projektName, datum, endeDatum, mindestAlter,
                hoechstAlter, 12, slotsGesamt, slotsReserviert, traeger, "www" +
                ".google.com");
    }

    public static Teilnehmer createSampleUser() {
        return createSampleUserOfName("Gary", "Eich");
    }

    public static Teilnehmer createSampleUserOfName(String lastName,
                                                    String firstName) {

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

        return new Teilnehmer(
                firstName,
                lastName,
                LocalDate.of(2005,10,20),
                LocalDate.now(),
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
    }

}
