package de.bauhaus.digital.domain;

import javax.persistence.*;

@Entity
public class Behinderung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Merkzeichen
    private boolean merkzeichen_AussergewoehnlicheGehbehinderung_aG;
    private boolean merkzeichen_Hilflosigkeit_H;
    private boolean merkzeichen_Blind_Bl;
    private boolean merkzeichen_Gehoerlos_Gl;
    private boolean merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B;
    private boolean merkzeichen_BeeintraechtigungImStrassenverkehr_G;
    private boolean merkzeichen_Taubblind_TBL;
    private boolean rollstuhlNutzungNotwendig;

    private String weitereHilfsmittel;
    private boolean wertmarkeVorhanden;
    private boolean begleitungNotwendig;

    private boolean begleitpersonPflege;
    private boolean begleitpersonMedizinischeVersorgung;
    private boolean begleitpersonMobilitaet;
    private boolean begleitpersonOrientierung;
    private boolean begleitpersonSozialeBegleitung;
    private String eingeschraenkteSinne;

    private String hinweiseZumUmgangMitDemKind;
    private boolean unterstuetzungSucheBegleitpersonNotwendig;
    private String gewohnterBegleitpersonenDienstleister;
    private boolean beantragungKostenuebernahmeBegleitpersonNotwendig;

    public void setMerkzeichen_AussergewoehnlicheGehbehinderung_aG(boolean merkzeichn_AussergewoehnlicheGehbehinderung_aG) {
        this.merkzeichen_AussergewoehnlicheGehbehinderung_aG = merkzeichn_AussergewoehnlicheGehbehinderung_aG;
    }

    public void setMerkzeichen_Hilflosigkeit_H(boolean merkzeichen_Hilflosigkeit_H) {
        this.merkzeichen_Hilflosigkeit_H = merkzeichen_Hilflosigkeit_H;
    }

    public void setMerkzeichen_Blind_Bl(boolean merkzeichen_Blind_Bl) {
        this.merkzeichen_Blind_Bl = merkzeichen_Blind_Bl;
    }

    public void setMerkzeichen_Gehoerlos_Gl(boolean merkzeichen_Gehoerlos_Gl) {
        this.merkzeichen_Gehoerlos_Gl = merkzeichen_Gehoerlos_Gl;
    }

    public void setMerkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(boolean merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B) {
        this.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B = merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B;
    }

    public void setMerkzeichen_BeeintraechtigungImStrassenverkehr_G(boolean merkzeichen_BeeintraechtigungImStrassenverkehr_G) {
        this.merkzeichen_BeeintraechtigungImStrassenverkehr_G = merkzeichen_BeeintraechtigungImStrassenverkehr_G;
    }

    public void setMerkzeichen_Taubblind_TBL(boolean merkzeichen_Taubblind_TBL) {
        this.merkzeichen_Taubblind_TBL = merkzeichen_Taubblind_TBL;
    }

    public void setRollstuhlNutzungNotwendig(boolean rollstuhlNutzungNotwendig) {
        this.rollstuhlNutzungNotwendig = rollstuhlNutzungNotwendig;
    }

    public void setWeitereHilfsmittel(String weitereHilfsmittel) {
        this.weitereHilfsmittel = weitereHilfsmittel;
    }

    public void setWertmarkeVorhanden(boolean wertmarkeVorhanden) {
        this.wertmarkeVorhanden = wertmarkeVorhanden;
    }

    public void setBegleitungNotwendig(boolean begleitungNotwendig) {
        this.begleitungNotwendig = begleitungNotwendig;
    }

    public void setBegleitpersonPflege(boolean begleitpersonPflege) {
        this.begleitpersonPflege = begleitpersonPflege;
    }

    public void setBegleitpersonMedizinischeVersorgung(boolean begleitpersonMedizinischeVersorgung) {
        this.begleitpersonMedizinischeVersorgung = begleitpersonMedizinischeVersorgung;
    }

    public void setBegleitpersonMobilitaet(boolean begleitpersonMobilitaet) {
        this.begleitpersonMobilitaet = begleitpersonMobilitaet;
    }

    public void setBegleitpersonOrientierung(boolean begleitpersonOrientierung) {
        this.begleitpersonOrientierung = begleitpersonOrientierung;
    }

    public void setBegleitpersonSozialeBegleitung(boolean begleitpersonSozialeBegleitung) {
        this.begleitpersonSozialeBegleitung = begleitpersonSozialeBegleitung;
    }

    public void setEingeschraenkteSinne(String eingeschraenkteSinne) {
        this.eingeschraenkteSinne = eingeschraenkteSinne;
    }

    public void setHinweiseZumUmgangMitDemKind(String hinweiseZumUmgangMitDemKind) {
        this.hinweiseZumUmgangMitDemKind = hinweiseZumUmgangMitDemKind;
    }

    public void setUnterstuetzungSucheBegleitpersonNotwendig(boolean unterstuetzungSucheBegleitpersonNotwendig) {
        this.unterstuetzungSucheBegleitpersonNotwendig = unterstuetzungSucheBegleitpersonNotwendig;
    }

    public void setGewohnterBegleitpersonenDienstleister(String gewohnterBegleitpersonenDienstleister) {
        this.gewohnterBegleitpersonenDienstleister = gewohnterBegleitpersonenDienstleister;
    }

    public void setBeantragungKostenuebernahmeBegleitpersonNotwendig(boolean beantragungKostenuebernahmeBegleitpersonNotwendig) {
        this.beantragungKostenuebernahmeBegleitpersonNotwendig = beantragungKostenuebernahmeBegleitpersonNotwendig;
    }

    public boolean isMerkzeichen_AussergewoehnlicheGehbehinderung_aG() {

        return merkzeichen_AussergewoehnlicheGehbehinderung_aG;
    }

    public boolean isMerkzeichen_Hilflosigkeit_H() {
        return merkzeichen_Hilflosigkeit_H;
    }

    public boolean isMerkzeichen_Blind_Bl() {
        return merkzeichen_Blind_Bl;
    }

    public boolean isMerkzeichen_Gehoerlos_Gl() {
        return merkzeichen_Gehoerlos_Gl;
    }

    public boolean isMerkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B() {
        return merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B;
    }

    public boolean isMerkzeichen_BeeintraechtigungImStrassenverkehr_G() {
        return merkzeichen_BeeintraechtigungImStrassenverkehr_G;
    }

    public boolean isMerkzeichen_Taubblind_TBL() {
        return merkzeichen_Taubblind_TBL;
    }

    public boolean isRollstuhlNutzungNotwendig() {
        return rollstuhlNutzungNotwendig;
    }

    public String getWeitereHilfsmittel() {
        return weitereHilfsmittel;
    }

    public boolean isWertmarkeVorhanden() {
        return wertmarkeVorhanden;
    }

    public boolean isBegleitungNotwendig() {
        return begleitungNotwendig;
    }

    public boolean isBegleitpersonPflege() {
        return begleitpersonPflege;
    }

    public boolean isBegleitpersonMedizinischeVersorgung() {
        return begleitpersonMedizinischeVersorgung;
    }

    public boolean isBegleitpersonMobilitaet() {
        return begleitpersonMobilitaet;
    }

    public boolean isBegleitpersonOrientierung() {
        return begleitpersonOrientierung;
    }

    public boolean isBegleitpersonSozialeBegleitung() {
        return begleitpersonSozialeBegleitung;
    }

    public String getEingeschraenkteSinne() {
        return eingeschraenkteSinne;
    }

    public String getHinweiseZumUmgangMitDemKind() {
        return hinweiseZumUmgangMitDemKind;
    }

    public boolean isUnterstuetzungSucheBegleitpersonNotwendig() {
        return unterstuetzungSucheBegleitpersonNotwendig;
    }

    public String getGewohnterBegleitpersonenDienstleister() {
        return gewohnterBegleitpersonenDienstleister;
    }

    public boolean isBeantragungKostenuebernahmeBegleitpersonNotwendig() {
        return beantragungKostenuebernahmeBegleitpersonNotwendig;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {

        return id;
    }

    public Behinderung() {
        this.merkzeichen_AussergewoehnlicheGehbehinderung_aG = false;
        this.merkzeichen_Hilflosigkeit_H = false;
        this.merkzeichen_Blind_Bl = false;
        this.merkzeichen_Gehoerlos_Gl = false;
        this.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B = false;
        this.merkzeichen_BeeintraechtigungImStrassenverkehr_G = false;
        this.merkzeichen_Taubblind_TBL = false;
        this.rollstuhlNutzungNotwendig = false;
        this.weitereHilfsmittel = "";
        this.wertmarkeVorhanden = false;
        this.begleitungNotwendig = false;
        this.begleitpersonPflege = false;
        this.begleitpersonMedizinischeVersorgung = false;
        this.begleitpersonMobilitaet = false;
        this.begleitpersonOrientierung = false;
        this.begleitpersonSozialeBegleitung = false;
        this.eingeschraenkteSinne = "";
        this.hinweiseZumUmgangMitDemKind = "";
        this.unterstuetzungSucheBegleitpersonNotwendig = false;
        this.gewohnterBegleitpersonenDienstleister = "";
        this.beantragungKostenuebernahmeBegleitpersonNotwendig = false;
    }


}
