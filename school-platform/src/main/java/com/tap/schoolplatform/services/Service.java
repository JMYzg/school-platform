package com.tap.schoolplatform.services;

import com.tap.schoolplatform.utils.Hibernate;
import com.tap.schoolplatform.utils.ValidationManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Service {

    public static <T> void add(T model) {
        ValidationManager.validateModel(model);
        EntityManagerFactory emf = Hibernate.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(model);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static <T> void update(T model) {
        ValidationManager.validateModel(model);
        EntityManagerFactory emf = Hibernate.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(model);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static <T> T find(int id, Class<T> entity) {
        EntityManagerFactory emf = Hibernate.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        T model = em.find(entity, id);

        em.getTransaction().commit();
        em.close();
        return model;
    }

    public static <T> T findWithMemberships(int id, Class<T> clazz) {
        EntityManager em = Hibernate.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.memberships WHERE u.id = :id";
            return em.createQuery(jpql, clazz)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public static <T> List<T> getEvery(Class<T> entity) {
        EntityManagerFactory emf = Hibernate.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<T> models = em.createQuery("from " + entity.getSimpleName(), entity).getResultList();
        em.getTransaction().commit();
        em.close();
        return models;
    }

    public static <T> void delete(T model) {
        ValidationManager.validateModel(model);
        EntityManagerFactory emf = Hibernate.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            T managed = em.merge(model);
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
