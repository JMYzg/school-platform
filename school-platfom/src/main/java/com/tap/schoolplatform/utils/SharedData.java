/**
 * TODO
 * 1. Connect User list with all User lists (Admin, Teacher and Student).
 * 2. Remove addUser and removeUser methods when the last task is ready.
 * */

package com.tap.schoolplatfom.utils;

import com.tap.schoolplatfom.models.academic.Degree;
import com.tap.schoolplatfom.models.academic.Group;
import com.tap.schoolplatfom.models.academic.Semester;
import com.tap.schoolplatfom.models.academic.Subject;
import com.tap.schoolplatfom.models.academic.enums.Shift;
import com.tap.schoolplatfom.models.users.Admin;
import com.tap.schoolplatfom.models.users.Student;
import com.tap.schoolplatfom.models.users.Teacher;
import com.tap.schoolplatfom.models.users.User;
import com.tap.schoolplatfom.models.users.enums.Gender;
import com.tap.schoolplatfom.models.users.enums.Role;
import com.tap.schoolplatfom.models.users.shared.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SharedData {

    private static SharedData instance;

    private final Map<Role, ObservableList<User>> users;
    private final ObservableList<Degree> degrees;

    private SharedData() {
        users = new HashMap<>();
        degrees = FXCollections.observableArrayList();
    }

    private void initialize() {
        Admin admin =
                new Admin(
                        "Jeremy",
                        "Zarate",
                        "jmy_zg@icloud.com",
                        "123",
                        "624-227-5260",
                        new Address(
                                "Sago Palm",
                                "23477",
                                "The Palms",
                                "C.S.L.",
                                "B.C.S.",
                                "Mexico"
                        ),
                        LocalDate.of(2004, 2, 23),
                        Gender.MALE
                );
        users.put(Role.ADMIN, FXCollections.observableArrayList(admin));

        Degree SE = new Degree("Software Engineering");
        Semester first = new Semester(SE, 1);
        Group G1M = new Group(first, Shift.MORNINGS);
        Subject IC = new Subject(first, "Integral Calculus");
        first.addGroup(G1M);
        first.addSubject(IC);
        SE.addSemester(first);
        degrees.add(SE);

        Teacher teacher =
                new Teacher(
                        "Gary",
                        "Juarez",
                        "gry_jp@gmail.com",
                        "456",
                        "624-243-8558",
                        new Address(
                                "Dolomite",
                                "23477",
                                "Saint Bernard",
                                "S.J.D.",
                                "B.C.S.",
                                "Mexico"
                        ),
                        LocalDate.of(2005, 9, 5),
                        Gender.MALE
                );
        SE.addTeacher(teacher);
        users.put(Role.TEACHER, FXCollections.observableArrayList(teacher));

        Student student =
                new Student(
                        "Brisa",
                        "Bautista",
                        "brs_bc@hotmail.com",
                        "789",
                        "624-160-8038",
                        new Address(
                                "Pango St.",
                                "23477",
                                "Cabo Valley",
                                "C.S.L.",
                                "B.C.S.",
                                "Mexico"
                        ),
                        LocalDate.of(2004, 2, 14),
                        Gender.FEMALE
                );
        G1M.addStudent(student);
        users.put(Role.STUDENT, FXCollections.observableArrayList(student));
    }

    public static SharedData getInstance() {
        if (instance == null) instance = new SharedData();
        return instance;
    }

    public void addUser(Role role, User user) {
        users.put(role, FXCollections.observableArrayList(user));
    }

    public void removeUser(Role role, User user) {
        users.get(role).remove(user);
    }

    public ObservableList<User> getUsers(Role role) {
        return FXCollections.unmodifiableObservableList(users.get(role));
    }

    public ObservableList<Degree> getDegrees() {
        return FXCollections.unmodifiableObservableList(degrees);
    }
}