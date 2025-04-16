package com.tap.schoolplatfom.models.academic;

import com.tap.schoolplatfom.models.academic.enums.Shift;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Semester {

    private final IntegerProperty number;
    private Map<Shift, ObservableList<Group>> groups = new HashMap<>();

    public Semester(int number) {
        this.number = new SimpleIntegerProperty(number);
    }

    public int getNumber() {
        return number.get();
    }

    public void addGroup(Group group) {

    }
}
