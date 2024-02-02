package ru.madwey.LibraryCrudBoot.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

}
