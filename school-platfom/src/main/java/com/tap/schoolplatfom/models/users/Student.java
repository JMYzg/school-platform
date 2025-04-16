package com.tap.schoolplatfom.models.users;

import com.tap.schoolplatfom.models.users.enums.Gender;
import com.tap.schoolplatfom.models.users.shared.Address;

import java.time.LocalDate;

public class Student extends User{

    public Student(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender) {
        super(name, lastName, email, password, phone, address, birthDate, gender);
    }
}
