package de.bauhaus.digital.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import de.bauhaus.digital.controller.Views;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
public class Teilnehmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Internal.class)
    private long id;

    // Verwaltung

    private boolean aktiv = true;
    private LocalDate registrierungsdatum = LocalDate.now();

    private boolean bezahlt = false;

    @AssertTrue(message = "Das Kind muss zur Schule gehen.")
    private boolean schulkind;
    @AssertTrue(message = "Datenschutzerklärung muss akzeptiert werden.")
    private boolean datenschutzErklaerungAkzeptiert;
    @AssertTrue(message = "Teilnahmebedingungen müssen akzeptiert werden.")
    private boolean teilnahmeBedingungAkzeptiert;

    // Grunddaten

    @NotBlank(message = "Vorname darf nicht leer sein.")
    private String vorname;
    @NotBlank(message = "Nachname darf nicht leer sein.")
    private String nachname;

    @NotNull(message = "Geburtsdatum muss angegeben werden.")
    @Past(message = "Geburtsdatum muss in der Vergangenheit liegen.")
    private LocalDate geburtsdatum;

    @NotBlank(message = "Straße darf nicht leer sein.")
    private String strasse;
    @NotBlank(message = "Hausnummer darf nicht leer sein.")
    private String hausnummer;
    @NotBlank(message = "Wohnort darf nicht leer sein.")
    private String wohnort;
    @NotBlank(message = "Postleitzahl darf nicht leer sein.")
    private String postleitzahl;

    @NotBlank(message = "Telefonnummer darf nicht leer sein.")
    private String telefon;
    @NotBlank(message = "Email darf nicht leer sein.")
    @Email(message = "Es muss eine gültige Email-Adresse angegeben werden.")
    private String email;

    private boolean darfErmaessigtenPreisZahlen;


    // Pflichtangaben

    @NotNull(message = "Es muss eine Auswahl für die Behandlungserlaubnis getroffen werden.")
    private Boolean darfBehandeltWerden;

    @NotNull(message = "Es muss eine Auswahl für das Heimgeherlaubnis getroffen werden.")
    private Boolean darfAlleinNachHause;

    @NotNull(message = "Es muss eine Auswahl für die Reiterlaubnis getroffen werden.")
    private Boolean darfReiten;

    @NotNull(message = "Es muss eine Auswahl für die Schwimmerlaubnis getroffen werden.")
    private Boolean darfSchwimmen;

    // TODO only required when darfSchwimmen=true
    private String schwimmAbzeichen;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="kontakt_id")
    private Kontakt notfallKontakt;


    // Allergien, Krankheiten

    private String allergien;

    private String krankheiten;

    private String medikamente;

    private boolean hitzeempfindlich;

    private boolean essenVegetarier;
    private boolean essenLaktoseUnvertraeglichkeit;
    private boolean essenEinerUnvertraeglichkeit;
    private String essenWeitereLimitierungen;

    private String krankenkasse;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "arzt_id")
    private Arzt arzt;

    // Behinderung

    private boolean liegtBehinderungVor;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "behinderung_id")
    private Behinderung behinderung;

    // Speziell fuer Registrierung

    @Transient
    @JsonSetter
    private List<Long> gewuenschteProjekte = new ArrayList<>();

    protected Teilnehmer() {}

    private Teilnehmer(Builder builder) {
        id = builder.id;
        aktiv = builder.aktiv;
        registrierungsdatum = builder.registrierungsdatum;
        bezahlt = builder.bezahlt;
        schulkind = builder.schulkind;
        datenschutzErklaerungAkzeptiert = builder.datenschutzErklaerungAkzeptiert;
        teilnahmeBedingungAkzeptiert = builder.teilnahmeBedingungAkzeptiert;
        vorname = builder.vorname;
        nachname = builder.nachname;
        geburtsdatum = builder.geburtsdatum;
        strasse = builder.strasse;
        hausnummer = builder.hausnummer;
        wohnort = builder.wohnort;
        postleitzahl = builder.postleitzahl;
        telefon = builder.telefon;
        email = builder.email;
        darfErmaessigtenPreisZahlen = builder.darfErmaessigtenPreisZahlen;
        darfBehandeltWerden = builder.darfBehandeltWerden;
        darfAlleinNachHause = builder.darfAlleinNachHause;
        darfReiten = builder.darfReiten;
        darfSchwimmen = builder.darfSchwimmen;
        schwimmAbzeichen = builder.schwimmAbzeichen;
        notfallKontakt = builder.notfallKontakt;
        allergien = builder.allergien;
        krankheiten = builder.krankheiten;
        medikamente = builder.medikamente;
        hitzeempfindlich = builder.hitzeempfindlich;
        essenVegetarier = builder.essenVegetarier;
        essenLaktoseUnvertraeglichkeit = builder.essenLaktoseUnvertraeglichkeit;
        essenEinerUnvertraeglichkeit = builder.essenEinerUnvertraeglichkeit;
        essenWeitereLimitierungen = builder.essenWeitereLimitierungen;
        krankenkasse = builder.krankenkasse;
        arzt = builder.arzt;
        liegtBehinderungVor = builder.liegtBehinderungVor;
        behinderung = builder.behinderung;
        gewuenschteProjekte = builder.gewuenschteProjekte;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Teilnehmer copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.aktiv = copy.isAktiv();
        builder.registrierungsdatum = copy.getRegistrierungsdatum();
        builder.bezahlt = copy.isBezahlt();
        builder.schulkind = copy.isSchulkind();
        builder.datenschutzErklaerungAkzeptiert = copy.isDatenschutzErklaerungAkzeptiert();
        builder.teilnahmeBedingungAkzeptiert = copy.isTeilnahmeBedingungAkzeptiert();
        builder.vorname = copy.getVorname();
        builder.nachname = copy.getNachname();
        builder.geburtsdatum = copy.getGeburtsdatum();
        builder.strasse = copy.getStrasse();
        builder.hausnummer = copy.getHausnummer();
        builder.wohnort = copy.getWohnort();
        builder.postleitzahl = copy.getPostleitzahl();
        builder.telefon = copy.getTelefon();
        builder.email = copy.getEmail();
        builder.darfErmaessigtenPreisZahlen = copy.isDarfErmaessigtenPreisZahlen();
        builder.darfBehandeltWerden = copy.getDarfBehandeltWerden();
        builder.darfAlleinNachHause = copy.getDarfAlleinNachHause();
        builder.darfReiten = copy.getDarfReiten();
        builder.darfSchwimmen = copy.getDarfSchwimmen();
        builder.schwimmAbzeichen = copy.getSchwimmAbzeichen();
        builder.notfallKontakt = copy.getNotfallKontakt();
        builder.allergien = copy.getAllergien();
        builder.krankheiten = copy.getKrankheiten();
        builder.medikamente = copy.getMedikamente();
        builder.hitzeempfindlich = copy.isHitzeempfindlich();
        builder.essenVegetarier = copy.isEssenVegetarier();
        builder.essenLaktoseUnvertraeglichkeit = copy.isEssenLaktoseUnvertraeglichkeit();
        builder.essenEinerUnvertraeglichkeit = copy.isEssenEinerUnvertraeglichkeit();
        builder.essenWeitereLimitierungen = copy.getEssenWeitereLimitierungen();
        builder.krankenkasse = copy.getKrankenkasse();
        builder.arzt = copy.getArzt();
        builder.liegtBehinderungVor = copy.isLiegtBehinderungVor();
        builder.behinderung = copy.getBehinderung();
        builder.gewuenschteProjekte = copy.getGewuenschteProjekte();
        return builder;
    }

    public long getId() {
        return id;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public LocalDate getRegistrierungsdatum() {
        return registrierungsdatum;
    }

    public boolean isBezahlt() {
        return bezahlt;
    }

    public boolean isSchulkind() {
        return schulkind;
    }

    public boolean isDatenschutzErklaerungAkzeptiert() {
        return datenschutzErklaerungAkzeptiert;
    }

    public boolean isTeilnahmeBedingungAkzeptiert() {
        return teilnahmeBedingungAkzeptiert;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public String getWohnort() {
        return wohnort;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public boolean isDarfErmaessigtenPreisZahlen() {
        return darfErmaessigtenPreisZahlen;
    }

    public Boolean getDarfBehandeltWerden() {
        return darfBehandeltWerden;
    }

    public Boolean getDarfAlleinNachHause() {
        return darfAlleinNachHause;
    }

    public Boolean getDarfReiten() {
        return darfReiten;
    }

    public Boolean getDarfSchwimmen() {
        return darfSchwimmen;
    }

    public String getSchwimmAbzeichen() {
        return schwimmAbzeichen;
    }

    public Kontakt getNotfallKontakt() {
        return notfallKontakt;
    }

    public String getAllergien() {
        return allergien;
    }

    public String getKrankheiten() {
        return krankheiten;
    }

    public String getMedikamente() {
        return medikamente;
    }

    public boolean isHitzeempfindlich() {
        return hitzeempfindlich;
    }

    public boolean isEssenVegetarier() {
        return essenVegetarier;
    }

    public boolean isEssenLaktoseUnvertraeglichkeit() {
        return essenLaktoseUnvertraeglichkeit;
    }

    public boolean isEssenEinerUnvertraeglichkeit() {
        return essenEinerUnvertraeglichkeit;
    }

    public String getEssenWeitereLimitierungen() {
        return essenWeitereLimitierungen;
    }

    public String getKrankenkasse() {
        return krankenkasse;
    }

    public Arzt getArzt() {
        return arzt;
    }

    public boolean isLiegtBehinderungVor() {
        return liegtBehinderungVor;
    }

    public Behinderung getBehinderung() {
        return behinderung;
    }

    public List<Long> getGewuenschteProjekte() {
        return gewuenschteProjekte;
    }

    @Override
    public String toString() {
        return "Teilnehmer{" +
                "id=" + id +
                ", aktiv=" + aktiv +
                ", registrierungsdatum=" + registrierungsdatum +
                ", bezahlt=" + bezahlt +
                ", schulkind=" + schulkind +
                ", datenschutzErklaerungAkzeptiert=" + datenschutzErklaerungAkzeptiert +
                ", teilnahmeBedingungAkzeptiert=" + teilnahmeBedingungAkzeptiert +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                ", strasse='" + strasse + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                ", wohnort='" + wohnort + '\'' +
                ", postleitzahl='" + postleitzahl + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", darfErmaessigtenPreisZahlen=" + darfErmaessigtenPreisZahlen +
                ", darfBehandeltWerden=" + darfBehandeltWerden +
                ", darfAlleinNachHause=" + darfAlleinNachHause +
                ", darfReiten=" + darfReiten +
                ", darfSchwimmen=" + darfSchwimmen +
                ", schwimmAbzeichen='" + schwimmAbzeichen + '\'' +
                ", notfallKontakt=" + notfallKontakt +
                ", allergien='" + allergien + '\'' +
                ", krankheiten='" + krankheiten + '\'' +
                ", medikamente='" + medikamente + '\'' +
                ", hitzeempfindlich=" + hitzeempfindlich +
                ", essenVegetarier=" + essenVegetarier +
                ", essenLaktoseUnvertraeglichkeit=" + essenLaktoseUnvertraeglichkeit +
                ", essenEinerUnvertraeglichkeit=" + essenEinerUnvertraeglichkeit +
                ", essenWeitereLimitierungen='" + essenWeitereLimitierungen + '\'' +
                ", krankenkasse='" + krankenkasse + '\'' +
                ", arzt=" + arzt +
                ", liegtBehinderungVor=" + liegtBehinderungVor +
                ", behinderung=" + behinderung +
                ", gewuenschteProjekte=" + gewuenschteProjekte +
                '}';
    }

    public static final class Builder {

        private long id;
        private boolean aktiv = true;
        private LocalDate registrierungsdatum = LocalDate.now();
        private boolean bezahlt = false;
        private boolean schulkind;
        private boolean datenschutzErklaerungAkzeptiert;
        private boolean teilnahmeBedingungAkzeptiert;
        private String vorname;
        private String nachname;
        private LocalDate geburtsdatum;
        private String strasse;
        private String hausnummer;
        private String wohnort;
        private String postleitzahl;
        private String telefon;
        private String email;
        private boolean darfErmaessigtenPreisZahlen;
        private Boolean darfBehandeltWerden;
        private Boolean darfAlleinNachHause;
        private Boolean darfReiten;
        private Boolean darfSchwimmen;
        private String schwimmAbzeichen;
        private Kontakt notfallKontakt;
        private String allergien;
        private String krankheiten;
        private String medikamente;
        private boolean hitzeempfindlich;
        private boolean essenVegetarier;
        private boolean essenLaktoseUnvertraeglichkeit;
        private boolean essenEinerUnvertraeglichkeit;
        private String essenWeitereLimitierungen;
        private String krankenkasse;
        private Arzt arzt;
        private boolean liegtBehinderungVor;
        private Behinderung behinderung;
        private List<Long> gewuenschteProjekte = new ArrayList<>();

        private Builder() {
        }

        public Builder aktiv(boolean aktiv) {
            this.aktiv = aktiv;
            return this;
        }

        public Builder registrierungsdatum(LocalDate registrierungsdatum) {
            this.registrierungsdatum = registrierungsdatum;
            return this;
        }

        public Builder bezahlt(boolean bezahlt) {
            this.bezahlt = bezahlt;
            return this;
        }

        public Builder schulkind(boolean schulkind) {
            this.schulkind = schulkind;
            return this;
        }

        public Builder datenschutzErklaerungAkzeptiert(boolean datenschutzErklaerungAkzeptiert) {
            this.datenschutzErklaerungAkzeptiert = datenschutzErklaerungAkzeptiert;
            return this;
        }

        public Builder teilnahmeBedingungAkzeptiert(boolean teilnahmeBedingungAkzeptiert) {
            this.teilnahmeBedingungAkzeptiert = teilnahmeBedingungAkzeptiert;
            return this;
        }

        public Builder vorname(String vorname) {
            this.vorname = vorname;
            return this;
        }

        public Builder nachname(String nachname) {
            this.nachname = nachname;
            return this;
        }

        public Builder geburtsdatum(LocalDate geburtsdatum) {
            this.geburtsdatum = geburtsdatum;
            return this;
        }

        public Builder strasse(String strasse) {
            this.strasse = strasse;
            return this;
        }

        public Builder hausnummer(String hausnummer) {
            this.hausnummer = hausnummer;
            return this;
        }

        public Builder wohnort(String wohnort) {
            this.wohnort = wohnort;
            return this;
        }

        public Builder postleitzahl(String postleitzahl) {
            this.postleitzahl = postleitzahl;
            return this;
        }

        public Builder telefon(String telefon) {
            this.telefon = telefon;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder darfErmaessigtenPreisZahlen(boolean darfErmaessigtenPreisZahlen) {
            this.darfErmaessigtenPreisZahlen = darfErmaessigtenPreisZahlen;
            return this;
        }

        public Builder darfBehandeltWerden(Boolean darfBehandeltWerden) {
            this.darfBehandeltWerden = darfBehandeltWerden;
            return this;
        }

        public Builder darfAlleinNachHause(Boolean darfAlleinNachHause) {
            this.darfAlleinNachHause = darfAlleinNachHause;
            return this;
        }

        public Builder darfReiten(Boolean darfReiten) {
            this.darfReiten = darfReiten;
            return this;
        }

        public Builder darfSchwimmen(Boolean darfSchwimmen) {
            this.darfSchwimmen = darfSchwimmen;
            return this;
        }

        public Builder schwimmAbzeichen(String schwimmAbzeichen) {
            this.schwimmAbzeichen = schwimmAbzeichen;
            return this;
        }

        public Builder notfallKontakt(Kontakt notfallKontakt) {
            this.notfallKontakt = notfallKontakt;
            return this;
        }

        public Builder allergien(String allergien) {
            this.allergien = allergien;
            return this;
        }

        public Builder krankheiten(String krankheiten) {
            this.krankheiten = krankheiten;
            return this;
        }

        public Builder medikamente(String medikamente) {
            this.medikamente = medikamente;
            return this;
        }

        public Builder hitzeempfindlich(boolean hitzeempfindlich) {
            this.hitzeempfindlich = hitzeempfindlich;
            return this;
        }

        public Builder essenVegetarier(boolean essenVegetarier) {
            this.essenVegetarier = essenVegetarier;
            return this;
        }

        public Builder essenLaktoseUnvertraeglichkeit(boolean essenLaktoseUnvertraeglichkeit) {
            this.essenLaktoseUnvertraeglichkeit = essenLaktoseUnvertraeglichkeit;
            return this;
        }

        public Builder essenEinerUnvertraeglichkeit(boolean essenEinerUnvertraeglichkeit) {
            this.essenEinerUnvertraeglichkeit = essenEinerUnvertraeglichkeit;
            return this;
        }

        public Builder essenWeitereLimitierungen(String essenWeitereLimitierungen) {
            this.essenWeitereLimitierungen = essenWeitereLimitierungen;
            return this;
        }

        public Builder krankenkasse(String krankenkasse) {
            this.krankenkasse = krankenkasse;
            return this;
        }

        public Builder arzt(Arzt arzt) {
            this.arzt = arzt;
            return this;
        }

        public Builder liegtBehinderungVor(boolean liegtBehinderungVor) {
            this.liegtBehinderungVor = liegtBehinderungVor;
            return this;
        }

        public Builder behinderung(Behinderung behinderung) {
            this.behinderung = behinderung;
            return this;
        }

        public Builder gewuenschteProjekte(List<Long> gewuenschteProjekte) {
            this.gewuenschteProjekte = gewuenschteProjekte;
            return this;
        }

        public Teilnehmer build() {
            return new Teilnehmer(this);
        }
    }
}
