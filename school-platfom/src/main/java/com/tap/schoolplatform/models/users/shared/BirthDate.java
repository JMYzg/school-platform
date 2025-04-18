package com.tap.schoolplatfom.models.users.shared;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BirthDate {

    private final IntegerProperty day;
    private final IntegerProperty month;
    private final IntegerProperty year;

    public BirthDate(int day, int month, int year) {
        this.day = new SimpleIntegerProperty(day);
        this.month = new SimpleIntegerProperty(month);
        this.year = new SimpleIntegerProperty(year);
    }

    public int getDay() {
        return day.get();
    }
    public IntegerProperty dayProperty() {
        return day;
    }
    public void setDay(int day) {
        this.day.set(day);
    }

    public int getMonth() {
        return month.get();
    }
    public IntegerProperty monthProperty() {
        return month;
    }
    public void setMonth(int month) {
        this.month.set(month);
    }

    public int getYear() {
        return year.get();
    }
    public IntegerProperty yearProperty() {
        return year;
    }
    public void setYear(int year) {
        this.year.set(year);
    }

}
