package ru.madwey.LibraryCrudBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.madwey.LibraryCrudBoot.dao.BookDAO;
import ru.madwey.LibraryCrudBoot.models.Book;
import ru.madwey.LibraryCrudBoot.models.Person;
import ru.madwey.LibraryCrudBoot.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository,
                         BookDAO bookDAO) {
        this.peopleRepository = peopleRepository;
        this.bookDAO = bookDAO;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> person = peopleRepository.findById(id);

        return person.orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setPersonId(id);

        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    @Transactional
    public List<Book> findBooks(int id){
        Optional<Person> person = peopleRepository.findById(id);

        List<Book> books = person.get().getBooks();

        System.out.println(new Date().getDate());

        for (Book book : books){
            System.out.println(book.getReceivedAt().getDate());
            Date receivedAt = book.getReceivedAt();

            book.setOverdue((new Date().getDate() - receivedAt.getDate() > 5)
                            || (new Date().getMonth() != receivedAt.getMonth()));
        }

        return books;
    }

    public List<Book> findOverdueBooks(int id) {
        return bookDAO.findOverdueBooks(id);
    }
}
