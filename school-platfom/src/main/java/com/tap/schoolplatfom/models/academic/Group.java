package com.tap.schoolplatfom.models.academic;

import com.tap.schoolplatfom.models.academic.enums.Shift;
import com.tap.schoolplatfom.models.users.Student;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Group {

    private StringProperty ID;
    private Shift shift;
    private ObservableList<Student> students;


}
