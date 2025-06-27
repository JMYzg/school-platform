package com.tap.schoolplatform.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Hibernate {
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("com.tap.schoolplatform");
        }
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }
}