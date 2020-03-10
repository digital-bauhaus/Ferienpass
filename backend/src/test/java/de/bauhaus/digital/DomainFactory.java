package de.bauhaus.digital;

import de.bauhaus.digital.domain.*;

import java.time.LocalDate;

public class DomainFactory {

    public static Projekt createSampleProject() {
        return createSampleProject("Schwimmem im See", LocalDate.of(2018, 7,
                1), LocalDate.of(2018, 7, 3), 10, 5, 15, 20);
    }

    public static Projekt createSampleProjectOfSlots(int plaetzeGesamt, int plaetzeReserviert) {
        return createSampleProject(
                "Schwimmem im See",
                LocalDate.of(2018, 7,1),
                LocalDate.of(2018, 7, 3),
                plaetzeGesamt,
                plaetzeReserviert);
    }

    public static Projekt createSampleProject(String projektName,
                                              LocalDate datumBeginn,
                                              LocalDate datumEnde,
                                              int plaetzeGesamt,
                                              int plaetzeReserviert) {
        return createSampleProject(projektName, datumBeginn, datumEnde, plaetzeGesamt, plaetzeReserviert, 15, 20);
    }

    public static Projekt createSampleProject(String projektName, LocalDate datumBeginn, LocalDate datumEnde, int plaetzeGesamt, int plaetzeReserviert, int mindestAlter, int hoechstAlter) {
        return Projekt.newBuilder().name(projektName).datumBeginn(datumBeginn).datumEnde(datumEnde).plaetzeGesamt(plaetzeGesamt).plaetzeReserviert(plaetzeReserviert).mindestAlter(mindestAlter).hoechstAlter(hoechstAlter).build();
    }

    public static Teilnehmer createSampleUser() {
        return createSampleUserOfName("Gary", "Eich");
    }

    public static Teilnehmer createSampleUserOfName(String lastName,
                                                    String firstName) {

        Arzt arzt = new Arzt("Eich", "Route 1 Alabastia, 39829",
                "555-6891");
        Kontakt kontact = new Kontakt("Igor Eich", "Route 4 Neuborkia  96825", "555-2532");

        String essenWeitereLimitierungen = "Laktoseintoleranz";
        String krankheiten = "Grippe: Muss oft Husten und braucht Hustenbonbons";
        String allergien = "Heuschnupfen: braucht Nasenspray, siehe Medikamente";
        Behinderung behinderung = Behinderung.newBuilder().
                rollstuhlNutzungNotwendig(true).
                merkzeichen_Taubblind_TBL(true).
                build();
        String schwimmAbzeichen = "Seepferdchen";
        String medikamente = "Nasentropfen_ maximal 2x am Tag ein Schub";
        String email = "myEmail@weimar.de";

        return Teilnehmer.newBuilder()
                .schulkind(true)
                .datenschutzErklaerungAkzeptiert(true)
                .teilnahmeBedingungAkzeptiert(true)
                .vorname(firstName)
                .nachname(lastName)
                .geburtsdatum(LocalDate.of(2005,10,20))
                .strasse("Bahnhofstraße")
                .hausnummer("4")
                .wohnort("Weimar")
                .postleitzahl("99423")
                .telefon("03544444")
                .krankenkasse("AOK")
                .darfBehandeltWerden(true)
                .notfallKontakt(kontact)
                .darfAlleinNachHause(true)
                .darfReiten(false)
                .darfSchwimmen(false)
                .schwimmAbzeichen(schwimmAbzeichen)
                .bezahlt(false)
                .darfBehandeltWerden(false)
                .arzt(arzt)
                .allergien(allergien)
                .essenWeitereLimitierungen(essenWeitereLimitierungen)
                .krankheiten(krankheiten)
                .liegtBehinderungVor(true)
                .behinderung(behinderung)
                .hitzeempfindlich(true)
                .medikamente(medikamente)
                .email(email)
                .build();
    }

}
