package de.bauhaus.digital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Arzt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String anschrift;

    private String telefon;

    public Arzt(String name, String anschrift, String telefon) {
        this.name = name;
        this.anschrift = anschrift;
        this.telefon = telefon;
    }

    protected Arzt() {}

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

}
