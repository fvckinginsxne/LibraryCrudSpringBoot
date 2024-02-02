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
import ru.madwey.LibraryCrudBoot.services.BooksService;
import ru.madwey.LibraryCrudBoot.services.PeopleService;
import ru.madwey.LibraryCrudBoot.util.BookValidator;


import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator validator;

    @Autowired
    public BooksController(BooksService booksService,
                           PeopleService peopleService,
                           BookValidator validator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.validator = validator;
    }

    @GetMapping()
    public String index(@RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "100") int books_per_page,
                        @RequestParam(required = false, defaultValue = "false") boolean sort_by_year,
                        Model model){

        model.addAttribute("books", booksService.findAll(page, books_per_page, sort_by_year));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> owner = booksService.findBookOwner(id);

        if (owner.isPresent())
            model.addAttribute("owner", owner.get());
        else
            model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        validator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){
        validator.validate(book, bindingResult);

        book.setBookId(id);

        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/appoint")
    public String appoint(@ModelAttribute("person") Person person, @PathVariable("id") int bookId){
        booksService.appoint(person.getPersonId(), bookId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "search", required = false) String search,
                       Model model){

        Book book = booksService.search(search);

        model.addAttribute("book", book);
        model.addAttribute("search", search);

        if (book != null)
            model.addAttribute("owner", book.getOwner());

        return "books/search";
    }

    @GetMapping("/{id}/extend")
    public String extend(@PathVariable("id") int id){

        booksService.extend(id);

        return "redirect:/books/{id}";
    }
}
