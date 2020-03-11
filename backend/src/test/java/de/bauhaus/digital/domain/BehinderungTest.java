package de.bauhaus.digital.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BehinderungTest {

    @Test
    public void givenbehinderungAttributes_whenCreatingUsingTheBuilder_thenCreatedbehinderungMatchesAttribues() {
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

        Behinderung behinderung = Behinderung.newBuilder()
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

        assertThat(behinderung.isMerkzeichen_AussergewoehnlicheGehbehinderung_aG(), is(merkzeichen_AussergewoehnlicheGehbehinderung_aG));
        assertThat(behinderung.isMerkzeichen_Hilflosigkeit_H(), is(merkzeichen_Hilflosigkeit_H));
        assertThat(behinderung.isMerkzeichen_Blind_Bl(), is(merkzeichen_Blind_Bl));
        assertThat(behinderung.isMerkzeichen_Gehoerlos_Gl(), is(merkzeichen_Gehoerlos_Gl));
        assertThat(behinderung.isMerkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(), is(merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B));
        assertThat(behinderung.isMerkzeichen_BeeintraechtigungImStrassenverkehr_G(), is(merkzeichen_BeeintraechtigungImStrassenverkehr_G));
        assertThat(behinderung.isMerkzeichen_Taubblind_TBL(), is(merkzeichen_Taubblind_TBL));
        assertThat(behinderung.isRollstuhlNutzungNotwendig(), is(rollstuhlNutzungNotwendig));
        assertThat(behinderung.getWeitereHilfsmittel(), is(weitereHilfsmittel));
        assertThat(behinderung.isWertmarkeVorhanden(), is(wertmarkeVorhanden));
        assertThat(behinderung.isBegleitungNotwendig(), is(begleitungNotwendig));
        assertThat(behinderung.isBegleitpersonPflege(), is(begleitpersonPflege));
        assertThat(behinderung.isBegleitpersonMedizinischeVersorgung(), is(begleitpersonMedizinischeVersorgung));
        assertThat(behinderung.isBegleitpersonMobilitaet(), is(begleitpersonMobilitaet));
        assertThat(behinderung.isBegleitpersonOrientierung(), is(begleitpersonOrientierung));
        assertThat(behinderung.isBegleitpersonSozialeBegleitung(), is(begleitpersonSozialeBegleitung));
        assertThat(behinderung.isBegleitpersonSinneswahrnehmung(), is(begleitpersonSinneswahrnehmung));
        assertThat(behinderung.getEingeschraenkteSinne(), is(eingeschraenkteSinne));
        assertThat(behinderung.getHinweiseZumUmgangMitDemKind(), is(hinweiseZumUmgangMitDemKind));
        assertThat(behinderung.isUnterstuetzungSucheBegleitperson(), is(unterstuetzungSucheBegleitperson));
        assertThat(behinderung.getGewohnterBegleitpersonenDienstleister(), is(gewohnterBegleitpersonenDienstleister));
        assertThat(behinderung.isBeantragungKostenuebernahmeBegleitperson(), is(beantragungKostenuebernahmeBegleitperson));
        assertThat(behinderung.isZustimmungWeitergabeDatenAmtFamilieUndSoziales(), is(zustimmungWeitergabeDatenAmtFamilieUndSoziales));
    }

}
