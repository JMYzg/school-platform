package com.tap.schoolplatform.models.users;

import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.utils.SharedData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.time.LocalDate;

public class Student extends User {

    private final StringProperty ID;
    private Image profilePicture;
    private Group group;

    public Student(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender) {
        super(name, lastName, email, password, phone, address, birthDate, gender, Role.STUDENT);
        ID = new SimpleStringProperty();
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) { // Only used by Group in addStudent and removeStudent
        this.group = group;
        ID.set(Integer.toString(SharedData.getInstance().getUsers(this.role).indexOf(this) + 1));
    }

    public Semester getSemester() {
        return this.group.getSemester();
    }

    public Degree getDegree() {
        return this.group.getSemester().getDegree();
    }

    public void copyFrom(Student tempStudent) {

    }
}
