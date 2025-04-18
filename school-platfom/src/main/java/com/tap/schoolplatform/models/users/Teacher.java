package com.tap.schoolplatfom.models.users;

import com.tap.schoolplatfom.models.academic.Degree;
import com.tap.schoolplatfom.models.users.enums.Gender;
import com.tap.schoolplatfom.models.users.enums.Role;
import com.tap.schoolplatfom.models.users.shared.Address;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Teacher extends User {

    private final Role role;
    private StringProperty license;
    private StringProperty specialization;
    private Degree degree;

    public Teacher(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender) {
        super(name, lastName, email, password, phone, address, birthDate, gender);
        role = Role.TEACHER;
    }

    public Role getRole() {
        return role;
    }

    public String getLicense() {
        return license.get();
    }
    public StringProperty licenseProperty() {
        return license;
    }
    public void setLicense(String license) {
        this.license.set(license);
    }

    public String getSpecialization() {
        return specialization.get();
    }
    public StringProperty specializationProperty() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization.set(specialization);
    }
}
