package com.tap.schoolplatfom.utils;

public class SharedData {

    private static SharedData instance;

    private SharedData() {}

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }
}