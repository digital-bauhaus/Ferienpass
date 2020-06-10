package de.bauhaus.digital;

import de.bauhaus.digital.domain.Arzt;
import de.bauhaus.digital.domain.Behinderung;
import de.bauhaus.digital.domain.Kontakt;
import de.bauhaus.digital.domain.Projekt;
import de.bauhaus.digital.domain.Teilnehmer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DomainFactory {

    public static Projekt.Builder createSampleProjektBuilder() {
        boolean aktiv = true;
        String name = "Testprojekt";
        LocalDate datumBeginn = LocalDate.of(2020, 3, 1);
        LocalDate datumEnde = LocalDate.of(2020, 3, 15);
        int mindestAlter = 5;
        int hoechstAlter = 15;
        int plaetzeGesamt = 20;
        int plaetzeReserviert = 3;
        String gruppe = "Gruppe";
        List<Teilnehmer> angemeldeteTeilnehmer = new ArrayList<>();
        List<Teilnehmer> stornierteTeilnehmer = new ArrayList<>();

        return Projekt.newBuilder()
                .aktiv(aktiv)
                .name(name)
                .datumBeginn(datumBeginn)
                .datumEnde(datumEnde)
                .mindestAlter(mindestAlter)
                .hoechstAlter(hoechstAlter)
                .plaetzeGesamt(plaetzeGesamt)
                .plaetzeReserviert(plaetzeReserviert)
                .gruppe(gruppe)
                .angemeldeteTeilnehmer(angemeldeteTeilnehmer)
                .stornierteTeilnehmer(stornierteTeilnehmer);
    }

    public static Projekt createSampleProjekt() {
        return createSampleProjektBuilder().build();
    }

    public static Teilnehmer.Builder createSampleTeilnehmerBuilder() {

        // Verwaltung
        boolean aktiv = true;
        LocalDate registrierungsdatum = LocalDate.now();
        boolean bezahlt = false;
        boolean schulkind = true;
        boolean datenschutzErklaerungAkzeptiert = true;
        boolean teilnahmeBedingungAkzeptiert = true;

        // Grunddaten
        String vorname = "Marianne";
        String nachname = "Musterfrau";
        LocalDate geburtsdatum = LocalDate.of(2020, 3, 1);
        String strasse = "Musterstraße";
        String hausnummer = "4a";
        String wohnort = "Musterstadt";
        String postleitzahl = "012345";
        String telefon = "0123456789";
        String email = "test@test.de";
        boolean darfErmaessigtenPreisZahlen = false;

        // Pflichtangaben
        Boolean darfBehandeltWerden = true;
        Boolean darfAlleinNachHause = true;
        Boolean darfReiten = true;
        Boolean darfSchwimmen = true;
        String schwimmAbzeichen = "Seepferdchen";
        Kontakt notfallKontakt = createSampleKontakt();

        // Allergien, Krankheiten
        String allergien = "Katzenhaare";
        String krankheiten = "Grippe";
        String medikamente = "Tabletten";
        boolean hitzeempfindlich = true;
        boolean essenVegetarier = true;
        boolean essenLaktoseUnvertraeglichkeit = true;
        boolean essenEierUnvertraeglichkeit = true;
        String essenWeitereLimitierungen = "vegan";
        String krankenkasse = "Krankenkasse";
        Arzt arzt = createSampleArzt();

        // Behinderung
        boolean liegtBehinderungVor = true;
        Behinderung behinderung = createSampleBehinderung();

        // Speziell fuer Registrierung
        List<Long> gewuenschteProjekte = new ArrayList<>();

        return Teilnehmer.newBuilder()
                .aktiv(aktiv)
                .registrierungsdatum(registrierungsdatum)
                .bezahlt(bezahlt)
                .schulkind(schulkind)
                .datenschutzErklaerungAkzeptiert(datenschutzErklaerungAkzeptiert)
                .teilnahmeBedingungAkzeptiert(teilnahmeBedingungAkzeptiert)
                .vorname(vorname)
                .nachname(nachname)
                .geburtsdatum(geburtsdatum)
                .strasse(strasse)
                .hausnummer(hausnummer)
                .wohnort(wohnort)
                .postleitzahl(postleitzahl)
                .telefon(telefon)
                .email(email)
                .darfErmaessigtenPreisZahlen(darfErmaessigtenPreisZahlen)
                .darfBehandeltWerden(darfBehandeltWerden)
                .darfAlleinNachHause(darfAlleinNachHause)
                .darfReiten(darfReiten)
                .darfSchwimmen(darfSchwimmen)
                .schwimmAbzeichen(schwimmAbzeichen)
                .notfallKontakt(notfallKontakt)
                .allergien(allergien)
                .krankheiten(krankheiten)
                .medikamente(medikamente)
                .hitzeempfindlich(hitzeempfindlich)
                .essenVegetarier(essenVegetarier)
                .essenLaktoseUnvertraeglichkeit(essenLaktoseUnvertraeglichkeit)
                .essenEierUnvertraeglichkeit(essenEierUnvertraeglichkeit)
                .essenWeitereLimitierungen(essenWeitereLimitierungen)
                .krankenkasse(krankenkasse)
                .arzt(arzt)
                .liegtBehinderungVor(liegtBehinderungVor)
                .behinderung(behinderung)
                .gewuenschteProjekte(gewuenschteProjekte);
    }

    public static Teilnehmer createSampleTeilnehmer() {
        return createSampleTeilnehmerBuilder().build();
    }

    public static Teilnehmer createSampleTeilnehmerOfName(String vorname, String nachname) {
        return createSampleTeilnehmerBuilder().vorname(vorname).nachname(nachname).build();
    }

    public static Arzt createSampleArzt() {
        return Arzt.newBuilder()
                .name("Eich")
                .anschrift("Route 1 Alabastia, 39829")
                .telefon("555-6891")
                .build();
    }

    public static Kontakt createSampleKontakt() {
        return Kontakt.newBuilder()
                .name("Igor Eich")
                .anschrift("Route 4 Neuborkia  96825")
                .telefon("555-2532")
                .build();
    }

    public static Behinderung createSampleBehinderung() {
        // Merkzeichen
        boolean merkzeichen_AussergewoehnlicheGehbehinderung_aG = true;
        boolean merkzeichen_Hilflosigkeit_H = true;
        boolean merkzeichen_Blind_Bl = true;
        boolean merkzeichen_Gehoerlos_Gl = true;
        boolean merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B = true;
        boolean merkzeichen_BeeintraechtigungImStrassenverkehr_G = true;
        boolean merkzeichen_Taubblind_TBL = true;

        // Hilfsmittel
        boolean rollstuhlNutzungNotwendig = true;
        String weitereHilfsmittel = "Krücken";

        // Wertmarke
        boolean wertmarkeVorhanden = true;

        // Begleitperson
        boolean begleitungNotwendig = true;
        boolean begleitpersonPflege = true;
        boolean begleitpersonMedizinischeVersorgung = true;
        boolean begleitpersonMobilitaet = true;
        boolean begleitpersonOrientierung = true;
        boolean begleitpersonSozialeBegleitung = true;
        boolean begleitpersonSinneswahrnehmung = true;

        String eingeschraenkteSinne = "Geruchssinn";
        String hinweiseZumUmgangMitDemKind = "Nicht unbeaufsichtigt lassen.";
        boolean unterstuetzungSucheBegleitperson = true;
        String gewohnterBegleitpersonenDienstleister = "Dienstleister";
        boolean beantragungKostenuebernahmeBegleitperson = true;
        boolean zustimmungWeitergabeDatenAmtFamilieUndSoziales = true;

        return Behinderung.newBuilder()
                .merkzeichen_AussergewoehnlicheGehbehinderung_aG(merkzeichen_AussergewoehnlicheGehbehinderung_aG)
                .merkzeichen_Hilflosigkeit_H(merkzeichen_Hilflosigkeit_H)
                .merkzeichen_Blind_Bl(merkzeichen_Blind_Bl)
                .merkzeichen_Gehoerlos_Gl(merkzeichen_Gehoerlos_Gl)
                .merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B)
                .merkzeichen_BeeintraechtigungImStrassenverkehr_G(merkzeichen_BeeintraechtigungImStrassenverkehr_G)
                .merkzeichen_Taubblind_TBL(merkzeichen_Taubblind_TBL)
                .rollstuhlNutzungNotwendig(rollstuhlNutzungNotwendig)
                .weitereHilfsmittel(weitereHilfsmittel)
                .wertmarkeVorhanden(wertmarkeVorhanden)
                .begleitungNotwendig(begleitungNotwendig)
                .begleitpersonPflege(begleitpersonPflege)
                .begleitpersonMedizinischeVersorgung(begleitpersonMedizinischeVersorgung)
                .begleitpersonMobilitaet(begleitpersonMobilitaet)
                .begleitpersonOrientierung(begleitpersonOrientierung)
                .begleitpersonSozialeBegleitung(begleitpersonSozialeBegleitung)
                .begleitpersonSinneswahrnehmung(begleitpersonSinneswahrnehmung)
                .eingeschraenkteSinne(eingeschraenkteSinne)
                .hinweiseZumUmgangMitDemKind(hinweiseZumUmgangMitDemKind)
                .unterstuetzungSucheBegleitperson(unterstuetzungSucheBegleitperson)
                .gewohnterBegleitpersonenDienstleister(gewohnterBegleitpersonenDienstleister)
                .beantragungKostenuebernahmeBegleitperson(beantragungKostenuebernahmeBegleitperson)
                .zustimmungWeitergabeDatenAmtFamilieUndSoziales(zustimmungWeitergabeDatenAmtFamilieUndSoziales)
                .build();
    }

}
