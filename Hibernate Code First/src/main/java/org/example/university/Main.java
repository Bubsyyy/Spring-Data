package org.example.university;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("h_code_first_ex");

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
    }
}
