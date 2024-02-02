package ru.madwey.LibraryCrudBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.madwey.LibraryCrudBoot.dao.BookDAO;
import ru.madwey.LibraryCrudBoot.models.Book;
import ru.madwey.LibraryCrudBoot.models.Person;
import ru.madwey.LibraryCrudBoot.repositories.BooksRepository;
import ru.madwey.LibraryCrudBoot.repositories.PeopleRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository,
                        PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(int page, int size, boolean year){
        if (year){
            return booksRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
        }

        return booksRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Book findOne(int id){
        Optional<Book> book = booksRepository.findById(id);

        return book.orElse(null);
    }

    public Optional<Object> findOne(String title) {
        Book book =  booksRepository.findBookByTitle(title);

        return Optional.ofNullable(book);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Optional<Person> findBookOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);

        return Optional.ofNullable(book.get().getOwner());
    }

    @Transactional
    public void appoint(int personId, int bookId) {
        Person person = peopleRepository.findById(personId).get();
        Book book = booksRepository.findById(bookId).get();

        person.setBooks(new ArrayList<>(Collections.singletonList(book)));

        book.setOwner(person);

        book.setReceivedAt(new Date());
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).get();

        book.getOwner().getBooks().remove(book);
        book.setOwner(null);

        book.setOverdue(false);
    }

    public Book search(String search) {
        Optional<Book> book = booksRepository.findByTitleStartingWithIgnoreCase(search);

        return book.orElse(null);
    }

    @Transactional
    public void extend(int id) {
        Book book = booksRepository.findById(id).get();

        book.setReceivedAt(new Date());

        book.setOverdue(false);
    }
}
