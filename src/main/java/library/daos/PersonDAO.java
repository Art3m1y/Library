package library.daos;

import library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPerson() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPersonByID(int id) {
        List<Person> personList = jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));

        if (personList.size() == 1) {
            return personList.get(0);
        } else {
            return null;
        }
    }

    public Person getPersonByName(String name) {
        List<Person> personList = jdbcTemplate.query("SELECT * FROM Person WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class));

        if (personList.size() == 1) {
            return personList.get(0);
        } else {
            return null;
        }
    }

    public void savePerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES (?, ?)", person.getName(), person.getYear_of_birth());
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);
    }

    public void editPerson(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name = ?, year_of_birth = ? WHERE person_id = ?", person.getName(), person.getYear_of_birth(), id);
    }

}
