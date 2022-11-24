package library.daos;

import library.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPerson() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }
    @Transactional(readOnly = true)
    public Person getPersonByID(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p WHERE id = :id", Person.class).setParameter("id", id).uniqueResultOptional().orElse(new Person());
    }

    @Transactional(readOnly = true)
    public Person getPersonByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p WHERE name = :name", Person.class).setParameter("name", name).uniqueResultOptional().orElse(null);
    }

    public void savePerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    public void deletePerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        getPersonByID(id).getBookList().clear();
        session.remove(session.createQuery("SELECT p FROM Person p WHERE id = :id", Person.class).setParameter("id", id).uniqueResultOptional().orElse(new Person()));
    }

    public void editPerson(int id, Person personUpdated) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.setName(personUpdated.getName());
        person.setYear_of_birth(personUpdated.getYear_of_birth());
    }

}
