package com.tap.schoolplatform.models.users.shared;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String street;
    private String postalCode;
    private String colony;
    private String city;
    private String state;
    private String country;

    public Address() {}

    public Address(String street, String postalCode, String colony, String city, String state, String country) {
        this.street = street;
        this.postalCode = postalCode;
        this.colony = colony;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public int getID() {
        return ID;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getColony() {
        return colony;
    }
    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) &&
                postalCode.equals(address.postalCode) &&
                colony.equals(address.colony) &&
                city.equals(address.city) &&
                state.equals(address.state) &&
                country.equals(address.country);
    }
}
