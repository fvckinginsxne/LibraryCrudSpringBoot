package ru.madwey.LibraryCrudBoot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.madwey.LibraryCrudBoot.models.Person;
import ru.madwey.LibraryCrudBoot.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (person.getYearOfBirth() == null){
            errors.rejectValue("yearOfBirth", "", "Вы не ввели дату рождения!");
        }
    }
}
