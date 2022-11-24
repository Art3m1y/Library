package library.daos;

import library.models.Book;
import library.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class BookDAO {

    private final SessionFactory sessionFactory;

    private final PersonDAO personDAO;

    public BookDAO(SessionFactory sessionFactory, PersonDAO personDAO) {
        this.sessionFactory = sessionFactory;
        this.personDAO = personDAO;
    }

    @Transactional(readOnly = true)
    public List<Book> getListBook() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
    @Transactional(readOnly = true)
    public List<Book> getListBookByPersonID(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p WHERE id = :id", Person.class).setParameter("id", id).uniqueResultOptional().orElse(new Person()).getBookList();
    }
    @Transactional(readOnly = true)
    public Book getBookByID(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b WHERE id = :id", Book.class).setParameter("id", id).uniqueResultOptional().orElse(new Book());
    }
    @Transactional(readOnly = true)
    public Book getBookByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b WHERE name = :name", Book.class).setParameter("name", name).uniqueResultOptional().orElse(null);
    }

    public void saveBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.createQuery("SELECT b FROM Book b WHERE id = :id", Book.class).setParameter("id", id).uniqueResultOptional().orElse(new Book());
        book.getPerson().getBookList().removeIf(bookEach -> bookEach.getBook_id() == book.getBook_id());
        session.remove(book);
    }

    public void editBook(int id, Book bookUpdated) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setPerson(bookUpdated.getPerson());
        book.setAuthor(bookUpdated.getAuthor());
        book.setYear(bookUpdated.getYear());
    }

    public void assignBook(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        if (person.getPerson_id() == 0) {
            getBookByID(id).getPerson().getBookList().removeIf(book -> book.getBook_id() == id);
            session.get(Book.class, id).setPerson(null);
        } else {
            session.get(Book.class, id).setPerson(personDAO.getPersonByID(person.getPerson_id()));
            personDAO.getPersonByID(person.getPerson_id()).getBookList().add(getBookByID(id));
        }
    }
}
