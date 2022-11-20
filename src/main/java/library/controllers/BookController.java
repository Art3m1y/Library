package library.controllers;

import library.daos.BookDAO;
import library.daos.PersonDAO;
import library.models.Book;
import library.models.Person;
import library.utils.BookValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    private final BookValidator bookValidator;

    public BookController(PersonDAO personDAO, BookDAO bookDAO, BookValidator bookValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping("/list")
    public String listWithBooks(Model model) {
        model.addAttribute("books", bookDAO.getListBook());

        return "book/list";
    }

    @GetMapping("/list/{id}")
    public String bookById(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBookByID(id);
        Book bookPerson = new Book();
        Person person = null;
        List<Person> personList = personDAO.getAllPerson();

        model.addAttribute("book", book);
        model.addAttribute("bookPerson", bookPerson);
        model.addAttribute("personList", personList);

        if (book.getPerson_id() == null) {
            model.addAttribute("person", person);
        } else {
            person = personDAO.getPersonByID(book.getPerson_id());
            model.addAttribute("person", person);
        }

        return "book/book";
    }

    @GetMapping("/list/new")
    public String newGetBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping("/list")
    public String newPostBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        bookDAO.saveBook(book);

        return "redirect:/book/list";
    }

    @DeleteMapping("/list/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);

        return "redirect:/book/list";
    }

    @GetMapping("/list/{id}/edit")
    public String editGetPerson(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBookByID(id);

        model.addAttribute("book", book);

        return "book/edit";
    }

    @PatchMapping("/list/{id}")
    public String editPatchPerson(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }

        bookDAO.editBook(id, book);

        return "redirect:/book/list/{id}";
    }

    @PatchMapping("/list/{id}/assign")
    public String assignBook(@PathVariable int id, @ModelAttribute Book book) {
        bookDAO.assignBook(id, book);

        return "redirect:/book/list/{id}";
    }

}
