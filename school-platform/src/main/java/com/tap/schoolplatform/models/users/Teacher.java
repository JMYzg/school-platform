package com.tap.schoolplatform.models.users;

import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Address;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Teacher extends User {

    private StringProperty license;
    private StringProperty specialization;
    private Degree degree;

    public Teacher(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender) {
        super(name, lastName, email, password, phone, address, birthDate, gender, Role.TEACHER);
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
