package library.services;

import library.models.Person;
import library.repositories.PersonRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> findAll(String page, String person_on_page) {
        if ((page == null)|| (person_on_page == null)) {
            return personRepository.findAll();
        } else {
            return personRepository.findAll(PageRequest.of(Integer.valueOf(page), Integer.valueOf(person_on_page))).getContent();
        }
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Person findById(int id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            if (person.getBookList().size() == 0) {
                person.setBookList(null);
                personRepository.save(person);
            }
        }
        return person;
    }
    @Transactional(readOnly = true)
    public Person findByName(String name) {
        return personRepository.findByName(name).orElse(null);
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    public void update(int id, Person personUpdated) {
        Person person = personRepository.findById(id).orElse(null);
        person.setName(personUpdated.getName());
        person.setYear_of_birth(personUpdated.getYear_of_birth());

        personRepository.save(person);
    }

}
