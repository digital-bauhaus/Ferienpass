package de.bauhaus.digital.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TeilnehmerTest {

    @Test
    public void givenTeilnehmerAttributes_whenCreatingUsingTheBuilder_thenCreatedTeilnehmerMatchesAttribues() {

        long id = 1;

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
        String email = "test@test;de";
        boolean darfErmaessigtenPreisZahlen = false;

        // Pflichtangaben
        Boolean darfBehandeltWerden = true;
        Boolean darfAlleinNachHause = true;
        Boolean darfReiten = true;
        Boolean darfSchwimmen = true;
        String schwimmAbzeichen = "Silber";
        Kontakt notfallKontakt = new Kontakt();

        // Allergien, Krankheiten
        String allergien = "Katzenhaare";
        String krankheiten = "Grippe";
        String medikamente = "Tabletten";
        boolean hitzeempfindlich = true;
        boolean essenVegetarier = true;
        boolean essenLaktoseUnvertraeglichkeit = true;
        boolean essenEinerUnvertraeglichkeit = true;
        String essenWeitereLimitierungen = "vegan";
        String krankenkasse = "Krankenkasse";
        Arzt arzt = new Arzt();

        // Behinderung
        boolean liegtBehinderungVor = true;
        Behinderung behinderung = new Behinderung();

        // Speziell fuer Registrierung
        List<Long> gewuenschteProjekte = new ArrayList<>();

        Teilnehmer teilnehmer = Teilnehmer.newBuilder()
                .id(id)
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
                .essenEinerUnvertraeglichkeit(essenEinerUnvertraeglichkeit)
                .essenWeitereLimitierungen(essenWeitereLimitierungen)
                .krankenkasse(krankenkasse)
                .arzt(arzt)
                .liegtBehinderungVor(liegtBehinderungVor)
                .behinderung(behinderung)
                .gewuenschteProjekte(gewuenschteProjekte)
                .build();

        assertThat(teilnehmer.getId(), is(id));
        assertThat(teilnehmer.isAktiv(), is(aktiv));
        assertThat(teilnehmer.getRegistrierungsdatum(), is(registrierungsdatum));
        assertThat(teilnehmer.isBezahlt(), is(bezahlt));
        assertThat(teilnehmer.isSchulkind(), is(schulkind));
        assertThat(teilnehmer.isDatenschutzErklaerungAkzeptiert(), is(datenschutzErklaerungAkzeptiert));
        assertThat(teilnehmer.isTeilnahmeBedingungAkzeptiert(), is(teilnahmeBedingungAkzeptiert));
        assertThat(teilnehmer.getVorname(), is(vorname));
        assertThat(teilnehmer.getNachname(), is(nachname));
        assertThat(teilnehmer.getGeburtsdatum(), is(geburtsdatum));
        assertThat(teilnehmer.getStrasse(), is(strasse));
        assertThat(teilnehmer.getHausnummer(), is(hausnummer));
        assertThat(teilnehmer.getWohnort(), is(wohnort));
        assertThat(teilnehmer.getPostleitzahl(), is(postleitzahl));
        assertThat(teilnehmer.getTelefon(), is(telefon));
        assertThat(teilnehmer.getEmail(), is(email));
        assertThat(teilnehmer.isDarfErmaessigtenPreisZahlen(), is(darfErmaessigtenPreisZahlen));
        assertThat(teilnehmer.getDarfBehandeltWerden(), is(darfBehandeltWerden));
        assertThat(teilnehmer.getDarfAlleinNachHause(), is(darfAlleinNachHause));
        assertThat(teilnehmer.getDarfReiten(), is(darfReiten));
        assertThat(teilnehmer.getDarfSchwimmen(), is(darfSchwimmen));
        assertThat(teilnehmer.getSchwimmAbzeichen(), is(schwimmAbzeichen));
        assertThat(teilnehmer.getNotfallKontakt(), is(notfallKontakt));
        assertThat(teilnehmer.getAllergien(), is(allergien));
        assertThat(teilnehmer.getKrankheiten(), is(krankheiten));
        assertThat(teilnehmer.getMedikamente(), is(medikamente));
        assertThat(teilnehmer.isHitzeempfindlich(), is(hitzeempfindlich));
        assertThat(teilnehmer.isEssenVegetarier(), is(essenVegetarier));
        assertThat(teilnehmer.isEssenLaktoseUnvertraeglichkeit(), is(essenLaktoseUnvertraeglichkeit));
        assertThat(teilnehmer.isEssenEinerUnvertraeglichkeit(), is(essenEinerUnvertraeglichkeit));
        assertThat(teilnehmer.getEssenWeitereLimitierungen(), is(essenWeitereLimitierungen));
        assertThat(teilnehmer.getKrankenkasse(), is(krankenkasse));
        assertThat(teilnehmer.getArzt(), is(arzt));
        assertThat(teilnehmer.isLiegtBehinderungVor(), is(liegtBehinderungVor));
        assertThat(teilnehmer.getBehinderung(), is(behinderung));
        assertThat(teilnehmer.getGewuenschteProjekte(), is(gewuenschteProjekte));
    }

}
