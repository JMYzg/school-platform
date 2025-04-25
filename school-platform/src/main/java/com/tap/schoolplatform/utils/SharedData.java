/**
 * TODO
 * 1. Connect User list with all User lists (Admin, Teacher and Student).
 * 2. Remove addUser and removeUser methods when the last task is ready.
 * */

package com.tap.schoolplatform.utils;

import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.academic.enums.Shift;
import com.tap.schoolplatform.models.users.Admin;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SharedData {

    private static SharedData instance;

    private final Map<Role, ObservableList<? extends User>> users;
    private final ObservableList<Degree> degrees;

    private SharedData() {
        users = new HashMap<>();
        users.put(Role.ADMIN, FXCollections.<Admin>observableArrayList());
        users.put(Role.TEACHER, FXCollections.<Teacher>observableArrayList());
        users.put(Role.STUDENT, FXCollections.<Student>observableArrayList());
        degrees = FXCollections.observableArrayList();
    }

    public void initialize() {
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
        addUser(admin);

        Degree SE = new Degree("Software Engineering", 9);
        Semester first = SE.getSemester(1);
        Semester third = SE.getSemester(3);
        new Subject(third, "Object Oriented Programming");
        Group G1M = new Group(first, Shift.MORNINGS);
        Group G2M = new Group(first, Shift.MORNINGS);
        //Make Changes
        Subject IC = new Subject(first, "Integral Calculus");
        Subject P = new Subject(third, "Psychology");
        Subject POO = new Subject(first, "Political Communication");
        degrees.add(SE);

        Teacher teacher = getTeacher();
        //Make Modifications
        teacher.addSubject(IC, P, POO);
        SE.addTeacher(teacher);

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

        Student student1 =
                new Student(
                        "Luis",
                        "Zatarain",
                        "thatB@Outlook.com",
                        "101112",
                        "624-169-6968",
                        new Address(
                                "Cabo Avenue",
                                "23477",
                                "Las Palmas",
                                "C.S.L.",
                                "B.C.S.",
                                "México"
                        ),
                        LocalDate.of(2005, 1, 29),
                        Gender.OTHER
                );

    }

    private static Teacher getTeacher() {
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
        teacher.setLicense("123-LICENSE-456");
        teacher.setSpecialization("Mathematics");
        return teacher;
    }

    public static SharedData getInstance() {
        if (instance == null) instance = new SharedData();
        return instance;
    }

    @SuppressWarnings("unchecked")
    public void addUser(User user) {
        Role role = user.getRole();
        Class<? extends User> expectedType = role.getUserClass();

        if (expectedType.isInstance(user)) {
            ObservableList<? extends User> list = users.get(role);
            if (list != null && !list.contains(user)) {
                ((ObservableList<User>) list).add(user);
            }
        }
    }

    public void removeUser(User user) {
        Role role = user.getRole();
        Class<? extends User> expectedType = role.getUserClass();

        if (expectedType.isInstance(user)) {
            ObservableList<? extends User> list = users.get(role);
            if (list != null) {
                list.remove(user);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends User> ObservableList<T> getUsers(Role role) {
        return FXCollections.unmodifiableObservableList((ObservableList<T>) users.get(role));
    }

    public void addDegree(Degree degree) {
        degrees.add(degree);
    }

    public void removeDegree(Degree degree) {
        degrees.remove(degree);
    }

    public ObservableList<Degree> getDegrees() {
        return FXCollections.unmodifiableObservableList(degrees);
    }
}