package ru.madwey.LibraryCrudBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.madwey.LibraryCrudBoot.models.Book;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Book findBookByTitle(String title);
    Optional<Book> findByTitleStartingWithIgnoreCase(String s);
}
