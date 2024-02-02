package ru.madwey.LibraryCrudBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.madwey.LibraryCrudBoot.dao.BookDAO;
import ru.madwey.LibraryCrudBoot.models.Book;
import ru.madwey.LibraryCrudBoot.models.Person;
import ru.madwey.LibraryCrudBoot.services.PeopleService;
import ru.madwey.LibraryCrudBoot.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator validator;

    @Autowired
    public PeopleController(PeopleService peopleService,
                            PersonValidator validator) {
        this.peopleService = peopleService;
        this.validator = validator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));

        List<Book> personBooks = peopleService.findBooks(id);
        List<Book> overdueBooks = peopleService.findOverdueBooks(id);

        model.addAttribute("personBooks", personBooks);
        model.addAttribute("overdueBooks", overdueBooks);

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                      BindingResult bindingResult){
        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        person.setPersonId(id);

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
