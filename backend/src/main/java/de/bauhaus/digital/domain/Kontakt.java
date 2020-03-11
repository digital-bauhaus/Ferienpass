package de.bauhaus.digital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Kontakt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String anschrift;

    @NotBlank(message = "Notfallkontakt-Telefonnummer muss angegeben werden.")
    private String telefon;

    protected Kontakt() {}

    private Kontakt(Builder builder) {
        id = builder.id;
        name = builder.name;
        anschrift = builder.anschrift;
        telefon = builder.telefon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Kontakt copy) {
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
        return "Kontakt{" +
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

        public Kontakt build() {
            return new Kontakt(this);
        }
    }
}
