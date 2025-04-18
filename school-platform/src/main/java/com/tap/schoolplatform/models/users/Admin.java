package com.tap.schoolplatform.models.users;

import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Address;

import java.time.LocalDate;

public class Admin extends User {

    private final Role role;

    public Admin(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender) {
        super(name, lastName, email, password, phone, address, birthDate, gender);
        this.role = Role.ADMIN;
    }

    public Role getRole() {
        return role;
    }
}
