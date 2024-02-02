package ru.madwey.LibraryCrudBoot.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.madwey.LibraryCrudBoot.models.Book;
import ru.madwey.LibraryCrudBoot.models.Person;

import java.util.List;

@Component
public class BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Book> findOverdueBooks(int id){
        Session session = entityManager.unwrap(Session.class);

        List<Book> overdueBooks = session.createQuery("select b from Book b where b.isOverdue=true and b.owner=:owner", Book.class)
                .setParameter("owner", session.get(Person.class, id)).getResultList();

        Hibernate.initialize(overdueBooks);

        return overdueBooks;
    }
}
