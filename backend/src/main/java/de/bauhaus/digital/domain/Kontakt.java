package de.bauhaus.digital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Kontakt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String anschrift;

    @NotBlank(message = "Notfallkontakt-Telefonnummer muss angegeben werden.")
    private String telefon;

    public Kontakt(String name, String anschrift, String telefon) {
        this.name = name;
        this.anschrift = anschrift;
        this.telefon = telefon;
    }

    protected Kontakt() {}

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

}
