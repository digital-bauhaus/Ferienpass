package de.bauhaus.digital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Kontakt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long kontakt_id;
    private String name;
    private String address;
    private String telephone;

    public Kontakt(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    protected  Kontakt() {}

    @Override
    public String toString() {
        return String.format(
                "Contact[id=%d, name='%s', address='%s', telephone='%s']",
                kontakt_id, name, address, telephone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getId() {
        return kontakt_id;
    }

    public void setId(long id) {
        this.kontakt_id = id;
    }
}
