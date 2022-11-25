package library.controllers;

import library.models.Person;
import library.services.BookService;
import library.services.PersonService;
import library.utils.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    private final PersonValidator personValidator;

    private final BookService bookService;

    public PersonController(PersonService personService, PersonValidator personValidator, BookService bookService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.bookService = bookService;
    }


    @GetMapping("/list")
    public String listWithPersons(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("persons", personService.findAll(httpServletRequest.getParameter("page"), httpServletRequest.getParameter("person_on_page")));

        return "person/list";
    }

    @GetMapping("/list/{id}")
    public String personById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findById(id));

        return "person/person";
    }

    @GetMapping("/list/new")
    public String newGetPerson(@ModelAttribute("person") Person person) {
        return "person/new";
    }

    @PostMapping("/list")
    public String newPostPerson(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "person/new";
        }

        personService.save(person);

        return "redirect:/person/list";
    }

    @DeleteMapping("/list/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.deleteById(id);

        return "redirect:/person/list";
    }

    @GetMapping("/list/{id}/edit")
    public String editGetPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findById(id));

        return "person/edit";
    }

    @PatchMapping("/list/{id}")
    public String editPatchPerson(@PathVariable("id") int id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person/edit";
        }

        personService.update(id, person);

        return "redirect:/person/list/{id}";
    }

}
