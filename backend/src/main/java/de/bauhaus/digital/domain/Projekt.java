package de.bauhaus.digital.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bauhaus.digital.validation.ProjektValidation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@ProjektValidation
public class Projekt {

    // The id of a Projekt is not internal, because we also need it in the public API, so users can register
    // for specific projects
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean aktiv = true;

    @NotBlank(message = "Name darf nicht leer sein.")
    private String name;

    @NotNull(message = "Beginndatum muss angegeben werden.")
    private LocalDate datumBeginn;

    @NotNull(message = "Enddatum muss angegeben werden.")
    private LocalDate datumEnde;

    @PositiveOrZero(message = "Mindestalter darf nicht < 0 sein.")
    private int mindestAlter;

    @PositiveOrZero(message = "Höchstalter darf nicht < 0 sein.")
    private int hoechstAlter;

    @Positive(message = "Gesamtplätze dürfen nicht <= 0 sein.")
    private int plaetzeGesamt;

    @PositiveOrZero(message = "Reservierte Plätze dürfen nicht < 0 sein.")
    private int plaetzeReserviert;

    private String gruppe;

    // we have an explicit API to handle users of a project, so we will not expose these lists
    @JsonIgnore
    // do NOT automatically delete Teilnehmer when the Projekt gets deleted!
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<Teilnehmer> angemeldeteTeilnehmer = new ArrayList<>();

    // we have an explicit API to handle users of a project, so we will not expose these lists
    @JsonIgnore
    // do NOT automatically delete Teilnehmer when the Projekt gets deleted!
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<Teilnehmer> stornierteTeilnehmer = new ArrayList<>();

    protected Projekt() {}

    private Projekt(Builder builder) {
        id = builder.id;
        aktiv = builder.aktiv;
        name = builder.name;
        datumBeginn = builder.datumBeginn;
        datumEnde = builder.datumEnde;
        mindestAlter = builder.mindestAlter;
        hoechstAlter = builder.hoechstAlter;
        plaetzeGesamt = builder.plaetzeGesamt;
        plaetzeReserviert = builder.plaetzeReserviert;
        gruppe = builder.gruppe;
        angemeldeteTeilnehmer = builder.angemeldeteTeilnehmer;
        stornierteTeilnehmer = builder.stornierteTeilnehmer;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Projekt copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.aktiv = copy.isAktiv();
        builder.name = copy.getName();
        builder.datumBeginn = copy.getDatumBeginn();
        builder.datumEnde = copy.getDatumEnde();
        builder.mindestAlter = copy.getMindestAlter();
        builder.hoechstAlter = copy.getHoechstAlter();
        builder.plaetzeGesamt = copy.getPlaetzeGesamt();
        builder.plaetzeReserviert = copy.getPlaetzeReserviert();
        builder.gruppe = copy.getGruppe();
        builder.angemeldeteTeilnehmer = copy.getAngemeldeteTeilnehmer();
        builder.stornierteTeilnehmer = copy.getStornierteTeilnehmer();
        return builder;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    public int getPlaetzeFrei() {
        return Math.max(0, this.plaetzeGesamt - this.getPlaetzeBelegt());
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    public int getPlaetzeBelegt() {
        return this.plaetzeReserviert + this.angemeldeteTeilnehmer.size();
    }

    /**
     * Meldet Teilnehmer an. Wenn der Teilnehmer storniert war, wird seine Stornierung aufgehoben
     *
     * @param anzumeldenderTeilnehmer Teilnehmer der angemeldet werden soll
     */
    public void meldeTeilnehmerAn(Teilnehmer anzumeldenderTeilnehmer) {
        // Note: no check, we just remove the cancellation
        this.stornierteTeilnehmer.remove(anzumeldenderTeilnehmer);

        this.angemeldeteTeilnehmer.add(anzumeldenderTeilnehmer);
    }

    /**
     * Storniere Teilnehmer von der Veranstaltung.
     *
     * @param zuStornierenderTeilnehmer Teilnehmer der storniert werden soll
     * @return true, wenn der Teilnehmer tatsaechlich fuer diese Veranstaltung angemeldet wear
     */
    public boolean storniereTeilnehmer(Teilnehmer zuStornierenderTeilnehmer) {
        boolean warAngemeldet = this.angemeldeteTeilnehmer.remove(zuStornierenderTeilnehmer);
        if (warAngemeldet)
        {
            this.stornierteTeilnehmer.add(zuStornierenderTeilnehmer);
        }
        return warAngemeldet;
    }

    /**
     * Entfernt den Teilnehmer komplett von diesem Projekt (also Anmeldung und Stornierung, sofern vorhanden)
     * @param zuEntfernenderTeilnehmer
     * @return true, wenn der Teilnehmer für dieses Projekt angemeldet oder storniert war
     */
    public boolean entferneTeilnehmerVonProjekt(Teilnehmer zuEntfernenderTeilnehmer) {
        boolean warAngemeldet = this.angemeldeteTeilnehmer.remove(zuEntfernenderTeilnehmer);
        boolean warStorniert = this.stornierteTeilnehmer.remove(zuEntfernenderTeilnehmer);
        return warAngemeldet || warStorniert;
    }

    /**
     * @param teilnehmer
     * @return true, wenn der Teilnehmer schon für dieses Projekt angemeldet ist
     */
    public boolean istTeilnehmerAngemeldet(Teilnehmer teilnehmer) {
        return this.angemeldeteTeilnehmer.contains(teilnehmer);
    }

    /**
     * @return true, wenn in diesem Projekte noch freie Plätze verügbar sind
     */
    public boolean hatProjektFreiePlaetze() {
        return getPlaetzeFrei() > 0;
    }

    public long getId() {
        return id;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDatumBeginn() {
        return datumBeginn;
    }

    public LocalDate getDatumEnde() {
        return datumEnde;
    }

    public int getMindestAlter() {
        return mindestAlter;
    }

    public int getHoechstAlter() {
        return hoechstAlter;
    }

    public int getPlaetzeGesamt() {
        return plaetzeGesamt;
    }

    public int getPlaetzeReserviert() {
        return plaetzeReserviert;
    }

    public String getGruppe() {
        return gruppe;
    }

    public List<Teilnehmer> getAngemeldeteTeilnehmer() {
        return angemeldeteTeilnehmer;
    }

    public List<Teilnehmer> getStornierteTeilnehmer() {
        return stornierteTeilnehmer;
    }

    @Override
    public String toString() {
        return "Projekt{" +
                "id=" + id +
                ", aktiv=" + aktiv +
                ", name='" + name + '\'' +
                ", datumBeginn=" + datumBeginn +
                ", datumEnde=" + datumEnde +
                ", mindestAlter=" + mindestAlter +
                ", hoechstAlter=" + hoechstAlter +
                ", plaetzeGesamt=" + plaetzeGesamt +
                ", plaetzeReserviert=" + plaetzeReserviert +
                ", gruppe=" + gruppe +
                ", angemeldeteTeilnehmer=" + angemeldeteTeilnehmer +
                ", stornierteTeilnehmer=" + stornierteTeilnehmer +
                '}';
    }

    public static final class Builder {

        private long id;
        private boolean aktiv = true;
        private String name;
        private LocalDate datumBeginn;
        private LocalDate datumEnde;
        private int mindestAlter;
        private int hoechstAlter;
        private int plaetzeGesamt;
        private int plaetzeReserviert;
        private String gruppe;
        private List<Teilnehmer> angemeldeteTeilnehmer = new ArrayList<>();
        private List<Teilnehmer> stornierteTeilnehmer = new ArrayList<>();

        private Builder() {
        }

        public Builder aktiv(boolean aktiv) {
            this.aktiv = aktiv;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder datumBeginn(LocalDate datumBeginn) {
            this.datumBeginn = datumBeginn;
            return this;
        }

        public Builder datumEnde(LocalDate datumEnde) {
            this.datumEnde = datumEnde;
            return this;
        }

        public Builder mindestAlter(int mindestAlter) {
            this.mindestAlter = mindestAlter;
            return this;
        }

        public Builder hoechstAlter(int hoechstAlter) {
            this.hoechstAlter = hoechstAlter;
            return this;
        }

        public Builder plaetzeGesamt(int plaetzeGesamt) {
            this.plaetzeGesamt = plaetzeGesamt;
            return this;
        }

        public Builder plaetzeReserviert(int plaetzeReserviert) {
            this.plaetzeReserviert = plaetzeReserviert;
            return this;
        }

        public Builder gruppe(String gruppe) {
            this.gruppe = gruppe;
            return this;
        }

        public Builder angemeldeteTeilnehmer(List<Teilnehmer> angemeldeteTeilnehmer) {
            this.angemeldeteTeilnehmer = angemeldeteTeilnehmer;
            return this;
        }

        public Builder stornierteTeilnehmer(List<Teilnehmer> stornierteTeilnehmer) {
            this.stornierteTeilnehmer = stornierteTeilnehmer;
            return this;
        }

        public Projekt build() {
            return new Projekt(this);
        }
    }
}
