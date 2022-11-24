package library.controllers;

import library.daos.BookDAO;
import library.daos.PersonDAO;
import library.models.Person;
import library.utils.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    private final PersonValidator personValidator;

    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping("/list")
    public String listWithPersons(Model model) {
        List<Person> personList = personDAO.getAllPerson();

        model.addAttribute("persons", personList);

        return "person/list";
    }

    @GetMapping("/list/{id}")
    public String personById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPersonByID(id));

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

        personDAO.savePerson(person);

        return "redirect:/person/list";
    }

    @DeleteMapping("/list/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);

        return "redirect:/person/list";
    }

    @GetMapping("/list/{id}/edit")
    public String editGetPerson(@PathVariable("id") int id, Model model) {
        Person person = personDAO.getPersonByID(id);

        model.addAttribute("person", person);

        return "person/edit";
    }

    @PatchMapping("/list/{id}")
    public String editPatchPerson(@PathVariable("id") int id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person/edit";
        }

        personDAO.editPerson(id, person);

        return "redirect:/person/list/{id}";
    }

}
