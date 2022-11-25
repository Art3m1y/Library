package library.controllers;

import library.models.Book;
import library.models.Person;
import library.services.BookService;
import library.services.PersonService;
import library.utils.BookValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private final PersonService personService;

    private final BookService bookService;

    private final BookValidator bookValidator;

    public BookController(PersonService personService, BookService bookService, BookValidator bookValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @GetMapping("/list")
    public String listWithBooks(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("books", bookService.findAll(httpServletRequest.getParameter("page"), httpServletRequest.getParameter("book_on_page"), httpServletRequest.getParameter("sort_by_year")));

        return "book/list";
    }

    @GetMapping("/list/{id}")
    public String bookById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("person", new Person());
        model.addAttribute("personList", personService.findAll());

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

        bookService.save(book);

        return "redirect:/book/list";
    }

    @DeleteMapping("/list/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        bookService.deleteById(id);

        return "redirect:/book/list";
    }

    @GetMapping("/list/{id}/edit")
    public String editGetPerson(@PathVariable("id") int id, Model model) {
        Book book = bookService.findById(id);

        model.addAttribute("book", book);

        return "book/edit";
    }

    @PatchMapping("/list/{id}")
    public String editPatchPerson(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }

        bookService.update(id, book);

        return "redirect:/book/list/{id}";
    }

    @PatchMapping("/list/{id}/assign")
    public String assignBook(@PathVariable int id, @ModelAttribute Person person) {
        bookService.assign(id, person);

        return "redirect:/book/list/{id}";
    }

    @GetMapping("/list/search")
    public String searchBook(@RequestParam Optional<String> name, Model model) {
        model.addAttribute("book", new Book());

        if (name.isPresent()) {
            Book book = bookService.searchBook(name.get());
            if (book != null) {
                model.addAttribute("searchedBook", book);
            }
            return "book/search";
        } else {
            return "book/search";
        }
    }

}

