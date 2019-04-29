package de.jonashackt.springbootvuejs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties(value= {"angemeldeteProjekte","stornierungen"})
public class Teilnehmer {
    // PrimaryKey
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String vorname;
    private String nachname;
    private LocalDate geburtsdatum;

    private LocalDate registrierungsdatum;

    private String strasse;
    private String stadt;
    private String postleitzahl;
    private String telefon;

    private String email;

    private String krankenkasse;
    private boolean erlaubeMedikamentation;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="kontakt_id")
    private Kontakt notfallKontakt;

    private boolean darfAlleinNachHause;
    private boolean darfReiten;
    private boolean darfSchwimmen;
    private String schwimmAbzeichen;
    private boolean bezahlt;
    private boolean aktiv;
    private boolean darfBehandeltWerden;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="arzt_id")
    private Arzt arzt;

    private String allergien;

    private String medikamente;

    private String hitzeempfindlichkeiten;

    private String krankheiten;

    private String essenLimitierungen;

    private boolean liegtBehinderungVor;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="behinderung_id")
    private Behinderung behinderung;

    @Override
    public String toString() {
        return "Teilnehmer{" +
                "id=" + getId() +
                ", Vorname='" + getVorname() + '\'' +
                ", Nachname='" + getNachname() + '\'' +
                ", Geburtsdatum=" + getGeburtsdatum() +
                ", Registrierungsdatum=" + getRegistrierungsdatum() + '\'' +
                ", Straße='" + getStrasse() + '\'' +
                ", Stadt='" + getStadt() + '\'' +
                ", Postleitzahl='" + getPostleitzahl() + '\'' +
                ", Telefon='" + getTelefon() + '\'' +
                ", Notrufnummer='" + getKrankenkasse() + '\'' +
                ", erlaube Medikamentation=" + isErlaubeMedikamentation() + '\'' +
                ", Notfallkontakt='" + getNotfallKontakt() + '\'' +
                ", darf allein nach Hause=" + isDarfAlleinNachHause() + '\'' +
                ", darf Reiten=" + isDarfReiten() + '\'' +
                ", darf Schwimmen=" + isDarfSchwimmen() + '\'' +
                ", Schwimmabzeichen=" +getSchwimmAbzeichen() + '\'' +
                ", darf behandelt werden=" + isDarfBehandeltWerden() + '\'' +
                ", bezahlt=" + isBezahlt() + '\'' +
                ", Arzt=" + getArzt()+ '\'' +
                ", liegt Beeinträchtigung vor=" + isLiegtBehinderungVor()+ '\'' +
                ", Behinderung=" + getBehinderung()+ '\'' +
                ", Krankheiten=" + getKrankheiten()+ '\'' +
                ", Essenslimitierungen=" + getEssenLimitierungen()+ '\'' +
                ", Allergien=" + getAllergien()+ '\'' +
                ", Medikamente=" + getMedikamente()+ '\'' +
                ", Hitzeempfindlichkeiten=" + getHitzeempfindlichkeiten() + '\'' +
                '}';
    }

    public Teilnehmer() {}

    public Teilnehmer(String firstName, String lastName, LocalDate birthDate, LocalDate registerDate, String street, String city, String postcode, String telephone, String krankenkasse,
                      boolean allowTreatment, Kontakt emergencyContact, boolean allowHomeAlone, boolean allowRiding, boolean allowSwimming, String schwimmAbzeichen, boolean hasPayed,
                      boolean darfBehandeltWerden, Arzt doctor,
                      String allergien, String essenLimitierungen, String krankheiten, boolean beeintraechtigt, Behinderung behinderung,
                      String hitzempfindlichkeiten, String medikamente) {

        this.setVorname(firstName);
        this.setNachname(lastName);
        this.setGeburtsdatum(birthDate);
        this.setRegistrierungsdatum(registerDate);
        this.setStrasse(street);
        this.setStadt(city);
        this.setPostleitzahl(postcode);
        this.setTelefon(telephone);
        this.setKrankenkasse(krankenkasse);
        this.setErlaubeMedikamentation(allowTreatment);
        this.setNotfallKontakt(emergencyContact);
        this.setDarfAlleinNachHause(allowHomeAlone);
        this.setDarfReiten(allowRiding);
        this.setDarfSchwimmen(allowSwimming);
        this.setSchwimmAbzeichen(schwimmAbzeichen);
        this.setBezahlt(hasPayed);
        this.setArzt(doctor);
        this.setAllergien(allergien);
        this.setEssenLimitierungen(essenLimitierungen);
        this.setKrankheiten(krankheiten);
        this.setLiegtBehinderungVor(beeintraechtigt);
        this.setBehinderung(behinderung);
        this.setAktiv(true);
        this.setMedikamente(medikamente);
        this.setHitzeempfindlichkeiten(hitzempfindlichkeiten);
        this.setDarfBehandeltWerden(darfBehandeltWerden);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public LocalDate getRegistrierungsdatum() {
        return registrierungsdatum;
    }

    public void setRegistrierungsdatum(LocalDate registrierungsdatum) {
        this.registrierungsdatum = registrierungsdatum;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKrankenkasse() {
        return krankenkasse;
    }

    public void setKrankenkasse(String krankenkasse) {
        this.krankenkasse = krankenkasse;
    }

    public boolean isErlaubeMedikamentation() {
        return erlaubeMedikamentation;
    }

    public void setErlaubeMedikamentation(boolean erlaubeMedikamentation) {
        this.erlaubeMedikamentation = erlaubeMedikamentation;
    }

    public Kontakt getNotfallKontakt() {
        return notfallKontakt;
    }

    public void setNotfallKontakt(Kontakt notfallKontakt) {
        this.notfallKontakt = notfallKontakt;
    }

    public boolean isDarfAlleinNachHause() {
        return darfAlleinNachHause;
    }

    public void setDarfAlleinNachHause(boolean darfAlleinNachHause) {
        this.darfAlleinNachHause = darfAlleinNachHause;
    }

    public boolean isDarfReiten() {
        return darfReiten;
    }

    public void setDarfReiten(boolean darfReiten) {
        this.darfReiten = darfReiten;
    }

    public boolean isDarfSchwimmen() {
        return darfSchwimmen;
    }

    public void setDarfSchwimmen(boolean darfSchwimmen) {
        this.darfSchwimmen = darfSchwimmen;
    }

    public boolean isBezahlt() {
        return bezahlt;
    }

    public void setBezahlt(boolean bezahlt) {
        this.bezahlt = bezahlt;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public Arzt getArzt() {
        return arzt;
    }

    public void setArzt(Arzt arzt) {
        this.arzt = arzt;
    }

    public String getAllergien() {
        return allergien;
    }

    public void setAllergien(String allergien) {
        this.allergien = allergien;
    }

    public String getKrankheiten() {
        return krankheiten;
    }

    public void setKrankheiten(String krankheiten) {
        this.krankheiten = krankheiten;
    }

    public void setBehinderung(Behinderung behinderung) {
        this.behinderung = behinderung;
    }

    public Behinderung getBehinderung() {

        return behinderung;
    }

    public String getEssenLimitierungen() {
        return essenLimitierungen;
    }

    public void setEssenLimitierungen(String essenLimitierungen) {
        this.essenLimitierungen = essenLimitierungen;
    }

    public boolean isLiegtBehinderungVor() {
        return liegtBehinderungVor;
    }

    public void setLiegtBehinderungVor(boolean liegtBehinderungVor) {
        this.liegtBehinderungVor = liegtBehinderungVor;
    }

    public String getSchwimmAbzeichen() {
        return schwimmAbzeichen;
    }

    public void setSchwimmAbzeichen(String schwimmAbzeichen) {
        this.schwimmAbzeichen = schwimmAbzeichen;
    }

    public String getMedikamente() {
        return medikamente;
    }

    public void setMedikamente(String medikamente) {
        this.medikamente = medikamente;
    }

    public String getHitzeempfindlichkeiten() {
        return hitzeempfindlichkeiten;
    }

    public void setHitzeempfindlichkeiten(String hitzeempfindlichkeiten) {
        this.hitzeempfindlichkeiten = hitzeempfindlichkeiten;
    }

    public boolean isDarfBehandeltWerden() {
        return darfBehandeltWerden;
    }

    public void setDarfBehandeltWerden(boolean darfBehandeltWerden) {
        this.darfBehandeltWerden = darfBehandeltWerden;
    }
}
