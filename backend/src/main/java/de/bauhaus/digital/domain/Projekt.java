package de.bauhaus.digital.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Projekt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projekt_id;
    private String name;
    private LocalDate datum;
    private LocalDate datumEnde;
    private int mindestAlter;
    private int hoechstAlter;
    //private String organisation;
    private int kosten;
    private int slotsGesamt;
    private int slotsReserviert;
    private String traeger;
    private String webLink;
    private boolean aktiv;

    @ManyToMany(cascade= CascadeType.ALL)
    private List<Teilnehmer> anmeldungen = new ArrayList<>();

    @ManyToMany(cascade= CascadeType.ALL)
    private List<Teilnehmer> stornierteTeilnehmer = new ArrayList<>();

    protected Projekt() {}

    public Projekt(String name, LocalDate datum, LocalDate datumEnde,
                   int mindestAlter, int hoechstAlter, int kosten,
                   int slotsGesamt, int slotsReserviert, String traeger,
                   String webLink) {
        setName(name);
        setDatum(datum);
        setDatumEnde(datumEnde);
        setMindestAlter(mindestAlter);
        setHoechstAlter(hoechstAlter);
        setKosten(kosten);
        setSlotsGesamt(slotsGesamt);
        setSlotsReserviert(slotsReserviert);
        setTraeger(traeger);
        setWebLink(webLink);
        setAktiv(true);
    }



    @Override
    public String toString() {
        return String.format(
                "Projekt[id=%d, Name='%s', Datum='%s', mindestAlter='%d', " +
                        "hoechstAlter='%d', Kosten='%d' Slots Gesamt='%d', " +
                        "Frei Slots='%d', Reservierte Slots='%d', Weblink='%s']",
                getId(), getName(), getDatum(), getMindestAlter(),
                getHoechstAlter(), getKosten(), getSlotsGesamt(),
                getSlotsFrei(),
                getSlotsReserviert(), getWebLink());
    }

    public long getId() {
        return projekt_id;
    }

    public void setId(long id) {
        this.projekt_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getMindestAlter() {
        return mindestAlter;
    }

    public void setMindestAlter(int mindestAlter) {
        this.mindestAlter = mindestAlter;
    }

    public int getHoechstAlter() {
        return hoechstAlter;
    }

    public void setHoechstAlter(int hoechstAlter) {
        this.hoechstAlter = hoechstAlter;
    }


    public int getKosten() {
        return kosten;
    }

    public void setKosten(int kosten) {
        this.kosten = kosten;
    }

    public int getSlotsGesamt() {
        return slotsGesamt;
    }

    public void setSlotsGesamt(int slotsGesamt) {
        this.slotsGesamt = slotsGesamt;
    }

    public int getSlotsReserviert() {
        return slotsReserviert;
    }

    public void setSlotsReserviert(int slotsReserviert) {
        this.slotsReserviert = slotsReserviert;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public List<Teilnehmer> getAnmeldungen() {
        return anmeldungen;
    }

    public String getTraeger() {
        return traeger;
    }

    public void setTraeger(String traeger) {
        this.traeger = traeger;
    }

    public LocalDate getDatumEnde() {
        return datumEnde;
    }

    public void setDatumEnde(LocalDate datumEnde) {
        this.datumEnde = datumEnde;
    }

    public void addAnmeldung(Teilnehmer teilnehmer) {
        this.anmeldungen.add(teilnehmer);
        this.setSlotsReserviert(this.slotsReserviert + 1);
    }

    public List<Teilnehmer> getStornierteTeilnehmer() {
        return stornierteTeilnehmer;
    }

    public void setStornierteTeilnehmer(List<Teilnehmer> stornierteTeilnehmer) {
        this.stornierteTeilnehmer = stornierteTeilnehmer;
    }

    public void addStornierterTeilnehmer(Teilnehmer stornierterTeilnehmer) {
        this.stornierteTeilnehmer.add(stornierterTeilnehmer);
        this.anmeldungen.remove(stornierterTeilnehmer);
    }

    public boolean isTeilnehmerNotAlreadyAsignedToProjekt(Teilnehmer teilnehmer) {
        return !this.anmeldungen.contains(teilnehmer);
    }

    public boolean hasProjektFreeSlots() {
        return getSlotsFrei() > 0;
    }

    public int getSlotsFrei() {
        return Math.max(0, this.slotsGesamt - this.slotsReserviert);
    }

}
