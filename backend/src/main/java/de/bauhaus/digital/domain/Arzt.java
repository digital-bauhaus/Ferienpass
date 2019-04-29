package de.bauhaus.digital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Arzt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long arzt_id;
    private String name;
    private String address;
    private String telephone;

    public Arzt(String name, String address, String telephone) {
        this.setName(name);
        this.setAddress(address);
        this.setTelephone(telephone);
    }

    protected Arzt(){}

    @Override
    public String toString() {
        return String.format(
                "Arzt[id=%d, Name='%s', Adresse='%s', Telefon='%s']",
                arzt_id, name, address, telephone);
    }

    public long getId() {
        return arzt_id;
    }

    public void setId(long id) {
        this.arzt_id = id;
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
}
