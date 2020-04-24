package de.bauhaus.digital.domain;

import com.fasterxml.jackson.annotation.JsonView;
import de.bauhaus.digital.controller.Views;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Arzt {

    // the id of a Arzt is not part of the public API, this way a malicious user cannot
    // manipulate the id in the JSON-Body when registering a Teilnehmer to override the data of another Teilnehmer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Internal.class)
    private long id;

    private String name;

    private String anschrift;

    private String telefon;

    protected Arzt() {}

    private Arzt(Builder builder) {
        id = builder.id;
        name = builder.name;
        anschrift = builder.anschrift;
        telefon = builder.telefon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Arzt copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.anschrift = copy.getAnschrift();
        builder.telefon = copy.getTelefon();
        return builder;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public String getTelefon() {
        return telefon;
    }

    @Override
    public String toString() {
        return "Arzt{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", anschrift='" + anschrift + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }

    public static final class Builder {

        private long id;
        private String name;
        private String anschrift;
        private String telefon;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder anschrift(String anschrift) {
            this.anschrift = anschrift;
            return this;
        }

        public Builder telefon(String telefon) {
            this.telefon = telefon;
            return this;
        }

        public Arzt build() {
            return new Arzt(this);
        }
    }
}
