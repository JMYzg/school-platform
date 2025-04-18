package com.tap.schoolplatform.models.users.shared;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Address {

    private final StringProperty street;
    private final StringProperty postalCode;
    private final StringProperty colony;
    private final StringProperty city;
    private final StringProperty state;
    private final StringProperty country;

    public Address(String street, String postalCode, String colony, String city, String state, String country) {
        this.street = new SimpleStringProperty(street);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.colony = new SimpleStringProperty(colony);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.country = new SimpleStringProperty(country);
    }

    public String getStreet() {
        return street.get();
    }
    public StringProperty streetProperty() {
        return street;
    }
    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getPostalCode() {
        return postalCode.get();
    }
    public StringProperty postalCodeProperty() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getColony() {
        return colony.get();
    }
    public StringProperty colonyProperty() {
        return colony;
    }
    public void setColony(String colony) {
        this.colony.set(colony);
    }

    public String getCity() {
        return city.get();
    }
    public StringProperty cityProperty() {
        return city;
    }
    public void setCity(String city) {
        this.city.set(city);
    }

    public String getState() {
        return state.get();
    }
    public StringProperty stateProperty() {
        return state;
    }
    public void setState(String state) {
        this.state.set(state);
    }

    public String getCountry() {
        return country.get();
    }
    public StringProperty countryProperty() {
        return country;
    }
    public void setCountry(String country) {
        this.country.set(country);
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
