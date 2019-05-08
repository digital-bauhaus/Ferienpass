package de.bauhaus.digital;

import de.bauhaus.digital.domain.*;

import java.time.LocalDate;

public class DomainFactory {

    public static Projekt createSingleProject() {
        return new Projekt("Schwimmen im See", LocalDate.of(2018, 7, 1),
                LocalDate.of(2018, 7, 3), 15, 20, 12, 10, 5, "Sportjugend " +
                "Weimar","www.google.com");
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
        String email = "myEmail@weimar.de";

        String hitzeempfindlichkeits = "grosse Hitze: eincremen";

        return new Teilnehmer("Gary","Eich", LocalDate.of(2005,10,20),registerDate, "Bahnhofstraße 4", "Weimar", "99423", "03544444", "0453434", true, kontact,
                true, false, false, "Seepferdchen", false, false, arzt,  allergien, essenLimitierungen, krankheiten, true, behinderung,hitzeempfindlichkeits,medikaments,email);
    }
}