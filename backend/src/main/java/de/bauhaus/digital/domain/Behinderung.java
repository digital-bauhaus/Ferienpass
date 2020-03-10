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

    // Hilfsmittel
    private boolean rollstuhlNutzungNotwendig;
    private String weitereHilfsmittel;

    // Wertmarke
    private boolean wertmarkeVorhanden;

    // Begleitperson
    private boolean begleitungNotwendig;
    private boolean begleitpersonPflege;
    private boolean begleitpersonMedizinischeVersorgung;
    private boolean begleitpersonMobilitaet;
    private boolean begleitpersonOrientierung;
    private boolean begleitpersonSozialeBegleitung;
    private boolean begleitpersonSinneswahrnehmung;

    private String eingeschraenkteSinne;
    private String hinweiseZumUmgangMitDemKind;
    private boolean unterstuetzungSucheBegleitperson;
    private String gewohnterBegleitpersonenDienstleister;
    private boolean beantragungKostenuebernahmeBegleitperson;
    private boolean zustimmungWeitergabeDatenAmtFamilieUndSoziales;

    protected Behinderung() {}

    private Behinderung(Builder builder) {
        merkzeichen_AussergewoehnlicheGehbehinderung_aG = builder.merkzeichen_AussergewoehnlicheGehbehinderung_aG;
        merkzeichen_Hilflosigkeit_H = builder.merkzeichen_Hilflosigkeit_H;
        merkzeichen_Blind_Bl = builder.merkzeichen_Blind_Bl;
        merkzeichen_Gehoerlos_Gl = builder.merkzeichen_Gehoerlos_Gl;
        merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B = builder.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B;
        merkzeichen_BeeintraechtigungImStrassenverkehr_G = builder.merkzeichen_BeeintraechtigungImStrassenverkehr_G;
        merkzeichen_Taubblind_TBL = builder.merkzeichen_Taubblind_TBL;
        rollstuhlNutzungNotwendig = builder.rollstuhlNutzungNotwendig;
        weitereHilfsmittel = builder.weitereHilfsmittel;
        wertmarkeVorhanden = builder.wertmarkeVorhanden;
        begleitungNotwendig = builder.begleitungNotwendig;
        begleitpersonPflege = builder.begleitpersonPflege;
        begleitpersonMedizinischeVersorgung = builder.begleitpersonMedizinischeVersorgung;
        begleitpersonMobilitaet = builder.begleitpersonMobilitaet;
        begleitpersonOrientierung = builder.begleitpersonOrientierung;
        begleitpersonSozialeBegleitung = builder.begleitpersonSozialeBegleitung;
        begleitpersonSinneswahrnehmung = builder.begleitpersonSinneswahrnehmung;
        eingeschraenkteSinne = builder.eingeschraenkteSinne;
        hinweiseZumUmgangMitDemKind = builder.hinweiseZumUmgangMitDemKind;
        unterstuetzungSucheBegleitperson = builder.unterstuetzungSucheBegleitperson;
        gewohnterBegleitpersonenDienstleister = builder.gewohnterBegleitpersonenDienstleister;
        beantragungKostenuebernahmeBegleitperson = builder.beantragungKostenuebernahmeBegleitperson;
        zustimmungWeitergabeDatenAmtFamilieUndSoziales = builder.zustimmungWeitergabeDatenAmtFamilieUndSoziales;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
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

    public boolean isBegleitpersonSinneswahrnehmung() {
        return begleitpersonSinneswahrnehmung;
    }

    public String getEingeschraenkteSinne() {
        return eingeschraenkteSinne;
    }

    public String getHinweiseZumUmgangMitDemKind() {
        return hinweiseZumUmgangMitDemKind;
    }

    public boolean isUnterstuetzungSucheBegleitperson() {
        return unterstuetzungSucheBegleitperson;
    }

    public String getGewohnterBegleitpersonenDienstleister() {
        return gewohnterBegleitpersonenDienstleister;
    }

    public boolean isBeantragungKostenuebernahmeBegleitperson() {
        return beantragungKostenuebernahmeBegleitperson;
    }

    public boolean isZustimmungWeitergabeDatenAmtFamilieUndSoziales() {
        return zustimmungWeitergabeDatenAmtFamilieUndSoziales;
    }

    @Override
    public String toString() {
        return "Behinderung{" +
                "id=" + id +
                ", merkzeichen_AussergewoehnlicheGehbehinderung_aG=" + merkzeichen_AussergewoehnlicheGehbehinderung_aG +
                ", merkzeichen_Hilflosigkeit_H=" + merkzeichen_Hilflosigkeit_H +
                ", merkzeichen_Blind_Bl=" + merkzeichen_Blind_Bl +
                ", merkzeichen_Gehoerlos_Gl=" + merkzeichen_Gehoerlos_Gl +
                ", merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B="
                + merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B +
                ", merkzeichen_BeeintraechtigungImStrassenverkehr_G=" + merkzeichen_BeeintraechtigungImStrassenverkehr_G
                +
                ", merkzeichen_Taubblind_TBL=" + merkzeichen_Taubblind_TBL +
                ", rollstuhlNutzungNotwendig=" + rollstuhlNutzungNotwendig +
                ", weitereHilfsmittel='" + weitereHilfsmittel + '\'' +
                ", wertmarkeVorhanden=" + wertmarkeVorhanden +
                ", begleitungNotwendig=" + begleitungNotwendig +
                ", begleitpersonPflege=" + begleitpersonPflege +
                ", begleitpersonMedizinischeVersorgung=" + begleitpersonMedizinischeVersorgung +
                ", begleitpersonMobilitaet=" + begleitpersonMobilitaet +
                ", begleitpersonOrientierung=" + begleitpersonOrientierung +
                ", begleitpersonSozialeBegleitung=" + begleitpersonSozialeBegleitung +
                ", begleitpersonSinneswahrnehmung=" + begleitpersonSinneswahrnehmung +
                ", eingeschraenkteSinne='" + eingeschraenkteSinne + '\'' +
                ", hinweiseZumUmgangMitDemKind='" + hinweiseZumUmgangMitDemKind + '\'' +
                ", unterstuetzungSucheBegleitperson=" + unterstuetzungSucheBegleitperson +
                ", gewohnterBegleitpersonenDienstleister='" + gewohnterBegleitpersonenDienstleister + '\'' +
                ", beantragungKostenuebernahmeBegleitperson=" + beantragungKostenuebernahmeBegleitperson +
                ", zustimmungWeitergabeDatenAmtFamilieUndSoziales=" + zustimmungWeitergabeDatenAmtFamilieUndSoziales +
                '}';
    }

    public static final class Builder {

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
        private boolean begleitpersonSinneswahrnehmung;
        private String eingeschraenkteSinne;
        private String hinweiseZumUmgangMitDemKind;
        private boolean unterstuetzungSucheBegleitperson;
        private String gewohnterBegleitpersonenDienstleister;
        private boolean beantragungKostenuebernahmeBegleitperson;
        private boolean zustimmungWeitergabeDatenAmtFamilieUndSoziales;

        private Builder() {
        }

        public Builder merkzeichen_AussergewoehnlicheGehbehinderung_aG(
                boolean merkzeichen_AussergewoehnlicheGehbehinderung_aG) {
            this.merkzeichen_AussergewoehnlicheGehbehinderung_aG = merkzeichen_AussergewoehnlicheGehbehinderung_aG;
            return this;
        }

        public Builder merkzeichen_Hilflosigkeit_H(boolean merkzeichen_Hilflosigkeit_H) {
            this.merkzeichen_Hilflosigkeit_H = merkzeichen_Hilflosigkeit_H;
            return this;
        }

        public Builder merkzeichen_Blind_Bl(boolean merkzeichen_Blind_Bl) {
            this.merkzeichen_Blind_Bl = merkzeichen_Blind_Bl;
            return this;
        }

        public Builder merkzeichen_Gehoerlos_Gl(boolean merkzeichen_Gehoerlos_Gl) {
            this.merkzeichen_Gehoerlos_Gl = merkzeichen_Gehoerlos_Gl;
            return this;
        }

        public Builder merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B(
                boolean merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B) {
            this.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B = merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B;
            return this;
        }

        public Builder merkzeichen_BeeintraechtigungImStrassenverkehr_G(
                boolean merkzeichen_BeeintraechtigungImStrassenverkehr_G) {
            this.merkzeichen_BeeintraechtigungImStrassenverkehr_G = merkzeichen_BeeintraechtigungImStrassenverkehr_G;
            return this;
        }

        public Builder merkzeichen_Taubblind_TBL(boolean merkzeichen_Taubblind_TBL) {
            this.merkzeichen_Taubblind_TBL = merkzeichen_Taubblind_TBL;
            return this;
        }

        public Builder rollstuhlNutzungNotwendig(boolean rollstuhlNutzungNotwendig) {
            this.rollstuhlNutzungNotwendig = rollstuhlNutzungNotwendig;
            return this;
        }

        public Builder weitereHilfsmittel(String weitereHilfsmittel) {
            this.weitereHilfsmittel = weitereHilfsmittel;
            return this;
        }

        public Builder wertmarkeVorhanden(boolean wertmarkeVorhanden) {
            this.wertmarkeVorhanden = wertmarkeVorhanden;
            return this;
        }

        public Builder begleitungNotwendig(boolean begleitungNotwendig) {
            this.begleitungNotwendig = begleitungNotwendig;
            return this;
        }

        public Builder begleitpersonPflege(boolean begleitpersonPflege) {
            this.begleitpersonPflege = begleitpersonPflege;
            return this;
        }

        public Builder begleitpersonMedizinischeVersorgung(boolean begleitpersonMedizinischeVersorgung) {
            this.begleitpersonMedizinischeVersorgung = begleitpersonMedizinischeVersorgung;
            return this;
        }

        public Builder begleitpersonMobilitaet(boolean begleitpersonMobilitaet) {
            this.begleitpersonMobilitaet = begleitpersonMobilitaet;
            return this;
        }

        public Builder begleitpersonOrientierung(boolean begleitpersonOrientierung) {
            this.begleitpersonOrientierung = begleitpersonOrientierung;
            return this;
        }

        public Builder begleitpersonSozialeBegleitung(boolean begleitpersonSozialeBegleitung) {
            this.begleitpersonSozialeBegleitung = begleitpersonSozialeBegleitung;
            return this;
        }

        public Builder begleitpersonSinneswahrnehmung(boolean begleitpersonSinneswahrnehmung) {
            this.begleitpersonSinneswahrnehmung = begleitpersonSinneswahrnehmung;
            return this;
        }

        public Builder eingeschraenkteSinne(String eingeschraenkteSinne) {
            this.eingeschraenkteSinne = eingeschraenkteSinne;
            return this;
        }

        public Builder hinweiseZumUmgangMitDemKind(String hinweiseZumUmgangMitDemKind) {
            this.hinweiseZumUmgangMitDemKind = hinweiseZumUmgangMitDemKind;
            return this;
        }

        public Builder unterstuetzungSucheBegleitperson(boolean unterstuetzungSucheBegleitperson) {
            this.unterstuetzungSucheBegleitperson = unterstuetzungSucheBegleitperson;
            return this;
        }

        public Builder gewohnterBegleitpersonenDienstleister(String gewohnterBegleitpersonenDienstleister) {
            this.gewohnterBegleitpersonenDienstleister = gewohnterBegleitpersonenDienstleister;
            return this;
        }

        public Builder beantragungKostenuebernahmeBegleitperson(boolean beantragungKostenuebernahmeBegleitperson) {
            this.beantragungKostenuebernahmeBegleitperson = beantragungKostenuebernahmeBegleitperson;
            return this;
        }

        public Builder zustimmungWeitergabeDatenAmtFamilieUndSoziales(
                boolean zustimmungWeitergabeDatenAmtFamilieUndSoziales) {
            this.zustimmungWeitergabeDatenAmtFamilieUndSoziales = zustimmungWeitergabeDatenAmtFamilieUndSoziales;
            return this;
        }

        public Behinderung build() {
            return new Behinderung(this);
        }
    }
}
