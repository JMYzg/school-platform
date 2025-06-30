package com.tap.schoolplatform.models.users;

import com.mysql.cj.protocol.ColumnDefinition;
import com.tap.schoolplatform.models.users.enums.Type;
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.shared.Membership;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(name = "profile_picture", columnDefinition = "LONGBLOB")
    private byte[] profilePicture;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name; //name

    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password; // password

    @NotBlank
    private String phone;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Membership> memberships = new ArrayList<>();

    public User() {

    }

    public User(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender, Type type) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public Image getProfilePictureImage() {
        if (profilePicture == null || profilePicture.length == 0) {
            return null;
        } else {
            return new Image(new ByteArrayInputStream(profilePicture));
        }
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Transient
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}