package ru.madwey.LibraryCrudBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.madwey.LibraryCrudBoot.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> { }
